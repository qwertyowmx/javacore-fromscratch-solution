package io.qwertyowrmx.proxygen.utils;

import io.qwertyowrmx.proxygen.exceptions.ProxyGenerationReflectionException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProxyFactoryUtils {

    public static Object newInstance(Class<?> aClass) {
        try {
            return aClass.getConstructor().newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new ProxyGenerationReflectionException("Unable to create new instance", e);
        }
    }

    public static Method getMethod(Class<?> aClass, String method, Class<?>... parameters) {
        try {
            return aClass.getMethod(method, parameters);
        } catch (NoSuchMethodException e) {
            throw new ProxyGenerationReflectionException("Unable to get method", e);
        }
    }

    public static void setStaticField(Class<?> aClass, String fieldName, Object value) {
        try {
            Field field = aClass.getField(fieldName);
            field.set(null, value);
        } catch (Exception e) {
            throw new ProxyGenerationReflectionException("Unable to set static field", e);
        }
    }

    public static org.objectweb.asm.commons.Method createAsmMethod(Method method) {
        return org.objectweb.asm.commons.Method.getMethod(method);
    }

    public static String replaceDotsToSlashes(String className) {
        return className.replace(".", "/");
    }
}
