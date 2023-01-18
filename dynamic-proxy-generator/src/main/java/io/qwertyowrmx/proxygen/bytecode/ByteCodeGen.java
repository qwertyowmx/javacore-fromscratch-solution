/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowrmx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.qwertyowrmx.proxygen.bytecode;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.proxygen.bytecode.visitor.ProxyBytecodeWriter;
import io.qwertyowrmx.proxygen.classloader.ClassPoolClassLoader;
import io.qwertyowrmx.proxygen.utils.ProxyFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassWriter;

import java.lang.reflect.Method;

@Slf4j
public class ByteCodeGen {
    private final Class<?> sourceClass;
    private final Interceptor interceptor;


    public ByteCodeGen(Class<?> sourceClass, Interceptor interceptor) {
        this.sourceClass = sourceClass;
        this.interceptor = interceptor;
    }

    public Class<?> emitClass() {
        ProxyBytecodeWriter writer = new ProxyBytecodeWriter(sourceClass, interceptor, ClassWriter.COMPUTE_MAXS);
        byte[] byteCode = writer.emitByteCode();
        return loadProxyClass(writer, byteCode);
    }

    private Class<?> loadProxyClass(ProxyBytecodeWriter writer, byte[] byteCode) {
        ClassPoolClassLoader classLoader = new ClassPoolClassLoader();
        String proxyName = writer.getProxyName();
        Class<?> proxyClass = classLoader.defineClass(proxyName, byteCode);
        setupInterceptorField(proxyClass);
        setupMethodsFields(writer.getSource(), proxyClass);
        return proxyClass;
    }

    private void setupInterceptorField(Class<?> proxyClass) {
        ProxyFactoryUtils.setStaticField(proxyClass, BytecodeConst.INTERCEPTOR_FIELD_NAME, interceptor);
    }

    private void setupMethodsFields(Class<?> sourceClass, Class<?> proxyClass) {
        for (Method method : sourceClass.getDeclaredMethods()) {
            String proxyMethodName = method.getName() + BytecodeConst.PROXY_METHOD_PREFIX;
            ProxyFactoryUtils.setStaticField(proxyClass, proxyMethodName, method);
        }
    }
}
