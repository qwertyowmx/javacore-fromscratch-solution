/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowmx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.qwertyowmx.proxygen.utils;

import io.qwertyowmx.proxygen.exceptions.ProxyGenerationReflectionException;

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
