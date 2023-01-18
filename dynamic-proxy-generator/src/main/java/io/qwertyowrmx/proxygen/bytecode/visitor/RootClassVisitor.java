package io.qwertyowrmx.proxygen.bytecode.visitor;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.proxygen.utils.ProxyFactoryUtils;
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
