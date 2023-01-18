package io.qwertyowrmx.rpc.client;

import io.qwertyowrmx.proxygen.ProxyPool;
import io.qwertyowrmx.rpc.client.interceptors.DpgInvocationHandler;

public class DpgStubBuilder<T> extends AbstractStubBuilder<T> {
    @Override
    protected T createStubInternal() {
        return ProxyPool.of(stubClass)
                .interceptor(new DpgInvocationHandler(ipAddress, port))
                .generateInstance();
    }
}
