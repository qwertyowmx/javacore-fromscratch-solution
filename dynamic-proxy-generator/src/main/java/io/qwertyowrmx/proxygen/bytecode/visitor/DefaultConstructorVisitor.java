package io.qwertyowrmx.proxygen.bytecode.visitor;

import io.qwertyowrmx.proxygen.utils.ProxyFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

@Slf4j
public class DefaultConstructorVisitor extends MethodVisitor {

    private final ProxyBytecodeWriter writer;

    protected DefaultConstructorVisitor(ProxyBytecodeWriter writer, int api) {
        super(api);
        this.writer = writer;
    }

    @Override
    public void visitEnd() {
        LOG.debug("Generating default constructor for class {}", writer.getProxyName());
        MethodVisitor visitor = writer.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        visitor.visitCode();
        visitor.visitVarInsn(ALOAD, 0); // load "this"

        Class<?> source = writer.getSource();
        if (source.isInterface()) {
            visitor.visitMethodInsn(INVOKESPECIAL, // call superclass constructor
                    ProxyFactoryUtils.replaceDotsToSlashes(Object.class.getName()),
                    "<init>", "()V",
                    false);
        } else {
            visitor.visitMethodInsn(INVOKESPECIAL, // call superclass constructor
                    ProxyFactoryUtils.replaceDotsToSlashes(source.getName()),
                    "<init>", "()V",
                    false);
        }

        visitor.visitInsn(RETURN);
        visitor.visitMaxs(1, 1);
        visitor.visitEnd();
    }
}
