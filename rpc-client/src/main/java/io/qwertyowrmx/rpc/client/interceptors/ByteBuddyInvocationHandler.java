package io.qwertyowrmx.rpc.client.interceptors;

import io.qwertyowrmx.rpc.client.network.Network;
import io.qwertyowrmx.rpc.common.descriptor.RemoteMethodDescriptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

@Slf4j
@AllArgsConstructor
public class ByteBuddyInvocationHandler {
    private final String ip;
    private final int port;

    @RuntimeType
    @SuppressWarnings("unused")
    public Object intercept(@This Object instance, @Origin Method method, @AllArguments Object[] args) {
        var descriptor = new RemoteMethodDescriptor(method.getReturnType(), method.getName(), args);
        return new Network(ip, port).communicate(descriptor);
    }
}
