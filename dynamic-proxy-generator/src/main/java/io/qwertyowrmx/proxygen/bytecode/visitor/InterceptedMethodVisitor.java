package io.qwertyowrmx.proxygen.bytecode.visitor;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.proxygen.bytecode.BytecodeConst;
import io.qwertyowrmx.proxygen.utils.ProxyFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;

import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

@Slf4j
public class InterceptedMethodVisitor extends MethodVisitor {
    private final Method sourceMethod;
    private final ProxyBytecodeWriter writer;
    private final Interceptor interceptor;

    protected InterceptedMethodVisitor(Interceptor interceptor,
                                       Method sourceMethod,
                                       ProxyBytecodeWriter writer,
                                       int api) {
        super(api);
        this.interceptor = interceptor;
        this.sourceMethod = sourceMethod;
        this.writer = writer;
    }

    @Override
    public void visitEnd() {
        LOG.debug("Generating intercepted method for class {}", writer.getProxyName());
        createInterceptedMethodField();
        LOG.debug("Inlining interceptor call for class {}", writer.getProxyName());
        inlineInterceptorCall();
    }

    private void createInterceptedMethodField() {

        String descriptor = Type.getDescriptor(Method.class);
        String methodName = ProxyFactoryUtils.replaceDotsToSlashes(getProxyMethodName());

        FieldVisitor fieldVisitor = writer.visitField(
                ACC_PUBLIC + ACC_STATIC, methodName,
                descriptor, null, null);

        fieldVisitor.visitEnd();
    }

    private void inlineInterceptorCall() {

        GeneratorAdapter adapter = new GeneratorAdapter(ACC_PUBLIC,
                ProxyFactoryUtils.createAsmMethod(sourceMethod),
                null, null, writer);

        adapter.visitFieldInsn(GETSTATIC,
                ProxyFactoryUtils.replaceDotsToSlashes(writer.getProxyName()),
                BytecodeConst.INTERCEPTOR_FIELD_NAME,
                Type.getDescriptor(Interceptor.class)); // Load interceptor

        adapter.loadThis(); // load Object

        adapter.visitFieldInsn(GETSTATIC, // Load method
                ProxyFactoryUtils.replaceDotsToSlashes(writer.getProxyName()),
                getProxyMethodName(),
                Type.getDescriptor(Method.class));


        adapter.loadArgArray(); // load args

        adapter.invokeInterface(
                Type.getType(Interceptor.class),
                ProxyFactoryUtils.createAsmMethod(getInterceptorMethod())
        );

        adapter.checkCast(adapter.getReturnType());
        adapter.returnValue();
        adapter.endMethod();
        adapter.visitEnd();
    }

    private String getProxyMethodName() {
        return sourceMethod.getName() + BytecodeConst.PROXY_METHOD_PREFIX;
    }

    public Method getInterceptorMethod() {
        return ProxyFactoryUtils.getMethod(
                interceptor.getClass(),
                "intercept",
                Object.class,
                Method.class,
                Object[].class
        );
    }
}
