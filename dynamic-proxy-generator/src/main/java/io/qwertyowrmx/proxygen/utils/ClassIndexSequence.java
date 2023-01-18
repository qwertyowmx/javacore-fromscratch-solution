package io.qwertyowrmx.proxygen.utils;

public class ClassIndexSequence {
    private static volatile int classIndex = 0;

    public static int nextIndex() {
        synchronized (ClassIndexSequence.class) {
            return classIndex++;
        }
    }
}
