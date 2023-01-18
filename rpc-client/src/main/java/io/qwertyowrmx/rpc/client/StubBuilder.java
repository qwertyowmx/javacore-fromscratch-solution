package io.qwertyowrmx.rpc.client;

public interface StubBuilder<T> extends StubFactory<T> {
    StubBuilder<T> stubClass(Class<T> aClass);

    StubBuilder<T> ip(String ip);

    StubBuilder<T> port(int port);
}
