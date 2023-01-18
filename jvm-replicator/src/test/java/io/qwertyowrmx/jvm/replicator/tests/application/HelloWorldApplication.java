package io.qwertyowrmx.jvm.replicator.tests.application;

import lombok.SneakyThrows;

public class HelloWorldApplication {
    @SneakyThrows
    public static void main(String[] args) {
        System.out.printf("Hello, World : current process PID: %s\n",
                ProcessHandle.current().pid());
        Thread.sleep(3000);
    }
}
