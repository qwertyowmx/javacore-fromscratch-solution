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

package io.qwertyowmx.eventrouter;

import io.qwertyowmx.eventrouter.context.FireEventContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventRouter {
    private final List<Object> subscribers;

    public EventRouter() {
        subscribers = new CopyOnWriteArrayList<>();
    }

    private static <T> void processMethod(FireEventContext<T> ctx) {
        Method method = ctx.getMethod();
        if (method.isAnnotationPresent(Subscribe.class)) {
            if (method.getParameterCount() == 1) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?> firstParameterType = parameterTypes[0];
                if (ctx.getArg() != null && ctx.getArg().getClass().equals(firstParameterType)) {
                    invokeMethod(ctx, method);
                }
            } else {
                throw new IllegalArgumentException("Invalid subscriber method," +
                        " it should be with only one parameter");
            }
        }
    }

    private static <T> void invokeMethod(FireEventContext<T> ctx, Method method) {
        try {
            method.invoke(ctx.getSubscriber(), ctx.getArg());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void addSubscriber(T subscriber) {
        subscribers.add(subscriber);
    }

    public <T> void removeSubscriber(T subscriber) {
        subscribers.remove(subscriber);
    }

    public <T> void fireEvent(T arg) {
        for (Object subscriber : subscribers) {
            if (subscriber != null) {
                Method[] methods = subscriber
                        .getClass()
                        .getMethods();

                Arrays.stream(methods)
                        .map((method) -> new FireEventContext<T>(subscriber, method, arg))
                        .forEach(EventRouter::processMethod);
            }
        }
    }
}
