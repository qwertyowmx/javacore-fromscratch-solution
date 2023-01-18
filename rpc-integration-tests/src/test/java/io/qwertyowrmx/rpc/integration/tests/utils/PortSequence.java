package io.qwertyowrmx.rpc.integration.tests.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortSequence {
    private static volatile int currentPort = 8700;

    public static int nextPort() {
        synchronized (PortSequence.class) {
            int port = ++currentPort;
            LOG.info("Next port: {}", port);
            return port;
        }
    }
}
