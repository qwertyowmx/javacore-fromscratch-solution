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
