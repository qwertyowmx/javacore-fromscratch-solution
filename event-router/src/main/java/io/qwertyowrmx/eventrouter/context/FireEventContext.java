package io.qwertyowrmx.eventrouter.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;


@AllArgsConstructor
public class FireEventContext<T> {

    @Getter
    private Object subscriber;
    @Getter
    private Method method;
    @Getter
    private T arg;
}
