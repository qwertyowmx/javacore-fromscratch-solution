package io.qwertyowrmx.rpc.integration.tests;

import io.qwertyowrmx.rpc.client.StubBuilder;
import io.qwertyowrmx.rpc.integration.tests.utils.OrderServiceContract;
import io.qwertyowrmx.rpc.integration.tests.utils.RpcServerOperations;
import io.qwertyowrmx.rpc.integration.tests.utils.pojo.Order;
import io.qwertyowrmx.rpc.integration.tests.utils.pojo.OrderStatus;
import io.qwertyowrmx.rpc.integration.tests.utils.pojo.Product;
import io.qwertyowrmx.rpc.integration.tests.utils.pojo.ProductPrice;
import io.qwertyowrmx.rpc.integration.tests.utils.provider.ComplexInteractionArgsProvider;
import io.qwertyowrmx.rpc.server.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
public class ComplexInteractionTest implements RpcServerOperations {

    @ParameterizedTest
    @ArgumentsSource(ComplexInteractionArgsProvider.class)
    public void testComplexInteraction(RpcServer rpcServer, StubBuilder<OrderServiceContract> orderBuilder) {

        CompletableFuture.runAsync(rpcServer::start);

        waitForServerToStart(rpcServer);

        OrderServiceContract service = orderBuilder.createStub();

        Order testOrder = new Order(BigInteger.ONE,
                OrderStatus.IN_PROGRESS,
                List.of(createTestProduct()));

        assertEquals(0, service.getOrders().size());

        service.submitOrder(testOrder);

        assertEquals(1, service.getOrders().size());


        Order found = service.getOrderById(BigInteger.ONE);

        LOG.info("Found order: {}", found);

        assertEquals(found.getStatus(), OrderStatus.IN_PROGRESS);

        Order updated = service.updateOrderStatus(BigInteger.ONE, OrderStatus.PURCHASED);

        assertEquals(updated.getStatus(), OrderStatus.PURCHASED);

        rpcServer.shutdown();

        waitForServerToStop(rpcServer);
    }

    private Product createTestProduct() {
        return new Product(
                new ProductPrice(150.0),
                new BigInteger("12411"),
                "Laptop");
    }
}
