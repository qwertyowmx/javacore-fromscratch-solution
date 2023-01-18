package io.qwertyowrmx.proxygen.interceptor;

import io.qwertyowrmx.proxygen.Interceptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
public class ConstantInterceptor<T> implements Interceptor {

    private T returnValue;

    @Override
    public Object intercept(Object instance, Method method, Object[] args) {
        LOG.info("Instance: {}", instance);
        LOG.info("Intercepting method: {}", method);
        LOG.info("Args: {}", Arrays.toString(args));
        return returnValue;
    }
}
