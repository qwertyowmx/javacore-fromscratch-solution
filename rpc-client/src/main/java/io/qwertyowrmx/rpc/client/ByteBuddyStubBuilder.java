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

package io.qwertyowrmx.rpc.client;

import io.qwertyowrmx.rpc.client.exceptions.StubCreationException;
import io.qwertyowrmx.rpc.client.interceptors.ByteBuddyInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ByteBuddyStubBuilder<T> extends AbstractStubBuilder<T> {

    private final ByteBuddy byteBuddy;

    public ByteBuddyStubBuilder() {
        this.byteBuddy = new ByteBuddy();
    }

    @Override
    protected T createStubInternal() {

        Class<?> generatedClass;
        try {
            DynamicType.Builder<? extends T> subclass = byteBuddy.subclass(stubClass);

            for (Method method : List.of(stubClass.getDeclaredMethods())) {
                var handler = new ByteBuddyInvocationHandler(ipAddress, port);
                subclass = subclass.define(method)
                        .intercept(MethodDelegation.to(handler));
            }
            generatedClass = subclass.make()
                    .load(this.getClass().getClassLoader())
                    .getLoaded();

            LOG.info("Generated stub class {}", generatedClass);
            LOG.info("Generated stub class methods: {}", Arrays
                    .stream(generatedClass.getDeclaredMethods())
                    .collect(Collectors.toList()));

        } catch (SecurityException e) {
            throw new StubCreationException("Unable to generate stub class", e);
        }
        return createNewInstance(generatedClass);
    }

    @SuppressWarnings("unchecked")
    private T createNewInstance(Class<?> generatedClass) {
        try {
            return (T) generatedClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new StubCreationException("Unable to create new stub instance", e);
        }
    }
}
