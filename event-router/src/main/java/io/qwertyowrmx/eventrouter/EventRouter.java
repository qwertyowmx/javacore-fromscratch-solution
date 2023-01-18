package io.qwertyowrmx.eventrouter;

import io.qwertyowrmx.eventrouter.context.FireEventContext;

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
