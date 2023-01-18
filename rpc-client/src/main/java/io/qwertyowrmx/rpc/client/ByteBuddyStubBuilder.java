package io.qwertyowrmx.rpc.client;

import io.qwertyowrmx.rpc.client.exceptions.StubCreationException;
import io.qwertyowrmx.rpc.client.interceptors.ByteBuddyInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ByteBuddyStubBuilder<T> extends AbstractStubBuilder<T> {

    private final ByteBuddy byteBuddy;

    public ByteBuddyStubBuilder() {
        this.byteBuddy = new ByteBuddy();
    }

    @Override
    protected T createStubInternal() {

        Class<?> generatedClass;
        try {
            DynamicType.Builder<? extends T> subclass = byteBuddy.subclass(stubClass);

            for (Method method : List.of(stubClass.getDeclaredMethods())) {
                var handler = new ByteBuddyInvocationHandler(ipAddress, port);
                subclass = subclass.define(method)
                        .intercept(MethodDelegation.to(handler));
            }
            generatedClass = subclass.make()
                    .load(this.getClass().getClassLoader())
                    .getLoaded();

            LOG.info("Generated stub class {}", generatedClass);
            LOG.info("Generated stub class methods: {}", Arrays
                    .stream(generatedClass.getDeclaredMethods())
                    .collect(Collectors.toList()));

        } catch (SecurityException e) {
            throw new StubCreationException("Unable to generate stub class", e);
        }
        return createNewInstance(generatedClass);
    }

    @SuppressWarnings("unchecked")
    private T createNewInstance(Class<?> generatedClass) {
        try {
            return (T) generatedClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new StubCreationException("Unable to create new stub instance", e);
        }
    }
}
