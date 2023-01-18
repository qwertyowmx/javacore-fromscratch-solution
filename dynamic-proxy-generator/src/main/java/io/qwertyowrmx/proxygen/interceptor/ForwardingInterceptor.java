package io.qwertyowrmx.proxygen.interceptor;

import io.qwertyowrmx.proxygen.Interceptor;
import io.qwertyowrmx.proxygen.utils.ProxyFactoryUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;


@Slf4j
public class ForwardingInterceptor<T> implements Interceptor {
    private final T classInstance;

    @SuppressWarnings("unchecked")
    public ForwardingInterceptor(Class<T> aClass) {
        this.classInstance = (T) ProxyFactoryUtils.newInstance(aClass);
    }

    public ForwardingInterceptor(T classInstance) {
        this.classInstance = classInstance;
    }

    @SneakyThrows
    @Override
    public Object intercept(Object instance, Method method, Object[] args) {
        LOG.info("Intercepting method: {}", method);
        LOG.info("Instance: {}", instance);
        LOG.info("Args: {}", Arrays.toString(args));
        return method.invoke(classInstance, args);
    }
}
