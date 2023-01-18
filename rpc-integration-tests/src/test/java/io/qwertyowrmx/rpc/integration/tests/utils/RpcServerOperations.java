package io.qwertyowrmx.rpc.integration.tests.utils;

import io.qwertyowrmx.rpc.server.RpcServer;
import org.awaitility.Awaitility;
import org.slf4j.Logger;

import java.time.Duration;

import static org.slf4j.LoggerFactory.getLogger;

public interface RpcServerOperations {

    Logger LOGGER = getLogger(RpcServerOperations.class);

    default void waitForServerToStop(RpcServer rpcServer) {
        Awaitility.with().pollInterval(Duration.ofSeconds(1))
                .pollInSameThread()
                .await()
                .atMost(Duration.ofMinutes(1))
                .until(() -> {
                    LOGGER.info("Waiting for server to stop");
                    return !rpcServer.isRunning();
                });
    }

    default void waitForServerToStart(RpcServer rpcServer) {
        Awaitility.with().pollInterval(Duration.ofSeconds(1))
                .pollInSameThread()
                .await()
                .atMost(Duration.ofMinutes(1))
                .until(() -> {
                    LOGGER.info("Waiting for server to start");
                    return rpcServer.isRunning();
                });
    }
}