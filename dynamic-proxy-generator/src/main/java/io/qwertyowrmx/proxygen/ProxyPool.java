package io.qwertyowrmx.proxygen;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.qwertyowrmx.proxygen.bytecode.ByteCodeGen;
import io.qwertyowrmx.proxygen.exceptions.ProxyGenerationException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public class ProxyPool<T> implements ProxyFactory<T> {

    private Cache<Class<?>, Class<T>> proxyCache;

    private Class<?> sourceClass;

    private Interceptor interceptor;

    public ProxyPool() {

    }

    public static <T> ProxyFactory<T> of(Class<T> sourceClass) {
        return new ProxyPool<T>().sourceClass(sourceClass);
    }

    @Override
    public ProxyFactory<T> sourceClass(Class<?> aClass) {
        this.sourceClass = aClass;
        return this;
    }

    @Override
    public ProxyFactory<T> enableCache() {
        this.proxyCache = CacheBuilder.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build();
        return this;
    }

    @Override
    public ProxyFactory<T> interceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    private void verifyProperties() {
        if (sourceClass == null) {
            throw new ProxyGenerationException("Unable to create proxy factory, source class is null");
        }

        if (interceptor == null) {
            throw new ProxyGenerationException("Unable to create proxy factory, interceptor is null");
        }
    }

    @Override
    public Class<T> generateClass() {
        verifyProperties();
        try {
            if (proxyCache != null) {
                return getCachedClass();
            } else {
                return generateClassInternal();
            }
        } catch (IOException e) {
            throw new ProxyGenerationException("Unable to generate proxy class", e);
        }
    }

    private Class<T> getCachedClass() throws IOException {
        Class<T> proxy = proxyCache.getIfPresent(sourceClass);

        if (proxy != null) {
            return proxy;
        }

        Class<T> generatedClass = generateClassInternal();

        proxyCache.put(sourceClass, generatedClass);
        return generatedClass;
    }

    @Override
    public T generateInstance() {
        try {
            return this.generateClass().getConstructor().newInstance();
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {

            throw new ProxyGenerationException("Unable to instantiate proxy", e);
        }
    }

    private Class<T> generateClassInternal() throws IOException {
        ByteCodeGen codeGen = new ByteCodeGen(sourceClass, interceptor);
        @SuppressWarnings("unchecked") Class<T> aClass = (Class<T>) codeGen.emitClass();
        return aClass;
    }
}

