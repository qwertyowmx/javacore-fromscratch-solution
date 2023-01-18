package io.qwertyowrmx.rpc.integration.tests;

import io.qwertyowrmx.rpc.client.StubFactory;
import io.qwertyowrmx.rpc.integration.tests.utils.RpcServerOperations;
import io.qwertyowrmx.rpc.integration.tests.utils.SimpleServiceContract;
import io.qwertyowrmx.rpc.integration.tests.utils.provider.SimpleArgsProvider;
import io.qwertyowrmx.rpc.server.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class SimpleInteractionTest implements RpcServerOperations {

    @ArgumentsSource(SimpleArgsProvider.class)
    @ParameterizedTest
    public void testCallRemoteMethods(RpcServer server,
                                      StubFactory<SimpleServiceContract> builder) {

        new Thread(server::start).start();
        waitForServerToStart(server);

        SimpleServiceContract service = builder.createStub();

        assertEquals(10, service.add(5, 5));
        assertEquals(30, service.add(12, 18));
        assertEquals(5, service.add(-10, 15));
        assertEquals(40, service.multiply(4, 10));
        assertEquals(100, service.multiply(20, 5));

        server.shutdown();

        waitForServerToStop(server);
    }
}
