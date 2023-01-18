package io.qwertyowrmx.rpc.client.interceptors;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.rpc.client.network.Network;
import io.qwertyowrmx.rpc.common.descriptor.RemoteMethodDescriptor;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;

@AllArgsConstructor
public class DpgInvocationHandler implements Interceptor {
    private final String ip;
    private final int port;

    @Override
    public Object intercept(Object instance, Method method, Object[] args) {
        var descriptor = new RemoteMethodDescriptor(method.getReturnType(), method.getName(), args);
        return new Network(ip, port).communicate(descriptor);
    }
}
