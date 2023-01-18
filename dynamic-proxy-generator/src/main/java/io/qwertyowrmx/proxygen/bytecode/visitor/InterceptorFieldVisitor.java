package io.qwertyowrmx.proxygen.bytecode.visitor;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.proxygen.bytecode.BytecodeConst;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

@Slf4j
public class InterceptorFieldVisitor extends FieldVisitor {

    private final ProxyBytecodeWriter writer;

    protected InterceptorFieldVisitor(ProxyBytecodeWriter writer, int api) {
        super(api);
        this.writer = writer;
    }

    @Override
    public void visitEnd() {
        LOG.debug("Generating interceptor field for class {}", writer.getProxyName());
        createInterceptorField();
    }

    private void createInterceptorField() {
        String descriptor = Type.getDescriptor(Interceptor.class);
        FieldVisitor fieldVisitor = writer.visitField(
                ACC_PUBLIC + ACC_STATIC, BytecodeConst.INTERCEPTOR_FIELD_NAME,
                descriptor,
                null, null);

        fieldVisitor.visitEnd();
    }
}
