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

package io.qwertyowmx.proxygen;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.qwertyowmx.proxygen.bytecode.ByteCodeGen;
import io.qwertyowmx.proxygen.exceptions.ProxyGenerationException;

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

