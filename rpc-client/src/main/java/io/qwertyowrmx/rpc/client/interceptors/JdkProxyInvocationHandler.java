package io.qwertyowrmx.rpc.client.interceptors;

import io.qwertyowrmx.rpc.client.network.Network;
import io.qwertyowrmx.rpc.common.descriptor.RemoteMethodDescriptor;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@AllArgsConstructor
public class JdkProxyInvocationHandler implements InvocationHandler {

    private final String ip;
    private final int port;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var descriptor = new RemoteMethodDescriptor(method.getReturnType(), method.getName(), args);
        return new Network(ip, port).communicate(descriptor);
    }
}
