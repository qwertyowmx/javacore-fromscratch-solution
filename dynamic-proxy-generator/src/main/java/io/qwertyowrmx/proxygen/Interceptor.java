package io.qwertyowrmx.proxygen;

import java.lang.reflect.Method;

public interface Interceptor {
    Object intercept(Object instance, Method method, Object[] args);
}
