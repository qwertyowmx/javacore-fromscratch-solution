package io.qwertyowrmx.rpc.client;

import io.qwertyowrmx.rpc.client.exceptions.StubCreationException;
import io.qwertyowrmx.rpc.client.interceptors.JdkProxyInvocationHandler;

import java.lang.reflect.Proxy;

public class JdkStubBuilder<T> extends AbstractStubBuilder<T> {
    @Override
    protected T createStubInternal() {
        if (!stubClass.isInterface()) {
            throw new StubCreationException("Unable to create JDK proxy, " +
                    stubClass + " is not interface!");
        }

        Class<?>[] interfaces = {stubClass};

        Object proxyInstance = Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                interfaces,
                new JdkProxyInvocationHandler(ipAddress, port)
        );

        @SuppressWarnings("unchecked")
        T proxyInstance1 = (T) proxyInstance;

        return proxyInstance1;
    }
}
