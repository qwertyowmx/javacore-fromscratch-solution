package io.qwertyowrmx.rpc.integration.tests.utils;

public class SimpleService implements SimpleServiceContract {
    @Override
    public int add(int x, int y) {
        return x + y;
    }

    @Override
    public int multiply(int x, int y) {
        return x * y;
    }
}
