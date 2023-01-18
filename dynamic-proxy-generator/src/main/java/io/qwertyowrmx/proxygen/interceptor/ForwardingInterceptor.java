/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowrmx
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
