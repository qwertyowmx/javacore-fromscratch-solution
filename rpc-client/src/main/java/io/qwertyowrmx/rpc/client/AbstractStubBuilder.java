package io.qwertyowrmx.rpc.client;


import io.qwertyowrmx.rpc.client.exceptions.StubCreationException;

import java.util.Objects;


// TODO: add javadocs

/**
 * @param <T>
 */
public abstract class AbstractStubBuilder<T> implements StubBuilder<T> {
    protected String ipAddress;
    protected Class<T> stubClass;
    protected int port;

    @Override
    public StubBuilder<T> stubClass(Class<T> aClass) {
        this.stubClass = aClass;
        return this;
    }

    @Override
    public StubBuilder<T> port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public T createStub() {

        if (Objects.isNull(ipAddress)) {
            throw new StubCreationException("Unable to create stub, ip address is null");
        }

        if (Objects.isNull(stubClass)) {
            throw new StubCreationException("Unable to create stub, class is null");
        }

        return createStubInternal();
    }

    @Override
    public StubBuilder<T> ip(String ip) {
        this.ipAddress = ip;
        return this;
    }

    protected abstract T createStubInternal();
}
