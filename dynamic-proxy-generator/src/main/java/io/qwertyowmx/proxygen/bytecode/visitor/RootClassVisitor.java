/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowmx
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

package io.qwertyowmx.proxygen.bytecode.visitor;

import io.qwertyowmx.proxygen.Interceptor;
import io.qwertyowmx.proxygen.utils.ProxyFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassVisitor;

import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.V1_8;

@Slf4j
public class RootClassVisitor extends ClassVisitor {

    private final ProxyBytecodeWriter writer;
    private final Interceptor interceptor;

    protected RootClassVisitor(ProxyBytecodeWriter writer, int api, Interceptor interceptor) {
        super(api);
        this.writer = writer;
        this.interceptor = interceptor;
    }

    @Override
    public void visitEnd() {
        Class<?> source = writer.getSource();
        if (source.isInterface()) {
            LOG.trace("Creating proxy {} using interface", source);
            createRootClassUsingInterface();
        } else {
            LOG.trace("Creating proxy {} using class", source);
            createRootClass();
        }
        createDefaultConstructor();
        createInterceptorField();
        createInterceptedMethods();
    }

    private void createDefaultConstructor() {
        DefaultConstructorVisitor visitor = new DefaultConstructorVisitor(writer, api);
        visitor.visitEnd();
    }

    private void createInterceptorField() {
        InterceptorFieldVisitor interceptorFieldVisitor = new InterceptorFieldVisitor(writer, api);
        interceptorFieldVisitor.visitEnd();
    }

    private void createRootClassUsingInterface() {
        writer.visit(V1_8, ACC_PUBLIC, ProxyFactoryUtils.replaceDotsToSlashes(writer.getProxyName()), null,
                ProxyFactoryUtils.replaceDotsToSlashes(Object.class.getName()), new String[]{ProxyFactoryUtils.replaceDotsToSlashes(writer.getSource().getName())});
    }

    private void createRootClass() {
        writer.visit(V1_8, ACC_PUBLIC, ProxyFactoryUtils.replaceDotsToSlashes(writer.getProxyName()), null,
                ProxyFactoryUtils.replaceDotsToSlashes(writer.getSource().getName()), null);
    }

    private void createInterceptedMethods() {
        for (Method classMethod : writer.getSource().getDeclaredMethods()) {
            InterceptedMethodVisitor visitor = new InterceptedMethodVisitor(interceptor, classMethod, writer, api);
            visitor.visitEnd();
        }
    }
}
