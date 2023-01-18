package io.qwertyowrmx.proxygen;

public interface ProxyFactory<T> {
    ProxyFactory<T> sourceClass(Class<?> aClass);

    ProxyFactory<T> enableCache();

    ProxyFactory<T> interceptor(Interceptor interceptor);

    Class<?> generateClass();

    T generateInstance();
}
