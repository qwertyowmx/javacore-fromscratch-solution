package io.qwertyowrmx.jvm.replicator.tests.application;

import io.qwertyowrmx.jvm.replicator.exceptions.JvmLaunchRuntimeException;
import lombok.SneakyThrows;

public class FailedHelloWorldApplication {
    @SneakyThrows
    public static void main(String[] args) {
        System.out.printf("Hello, World : current process PID: %s\n",
                ProcessHandle.current().pid());
        Thread.sleep(3000);

        throw new JvmLaunchRuntimeException("Fail");
    }
}
