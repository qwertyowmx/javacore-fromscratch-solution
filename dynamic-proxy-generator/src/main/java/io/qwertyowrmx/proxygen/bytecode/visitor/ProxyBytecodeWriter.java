package io.qwertyowrmx.proxygen.bytecode.visitor;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.proxygen.bytecode.BytecodeConst;
import io.qwertyowrmx.proxygen.utils.ClassIndexSequence;
import lombok.Getter;
import org.objectweb.asm.ClassWriter;

public class ProxyBytecodeWriter extends ClassWriter {
    @Getter
    private final Class<?> source;
    @Getter
    private final String proxyName;
    @Getter
    private final Interceptor interceptor;

    public ProxyBytecodeWriter(Class<?> source, Interceptor interceptor, int flags) {
        super(flags);
        this.source = source;
        this.proxyName = source.getName() + BytecodeConst.PROXY_CLASS_PREFIX + ClassIndexSequence.nextIndex();
        this.interceptor = interceptor;
    }

    public byte[] emitByteCode() {
        RootClassVisitor visitor = new RootClassVisitor(this, this.api, interceptor);
        visitor.visitEnd();
        return toByteArray();
    }
}
