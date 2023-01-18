/*
 * MIT License
 *
 * Copyright (c) 2023 qwertyowmx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.qwertyowmx.rpc.integration.tests;

import io.qwertyowmx.rpc.client.StubBuilder;
import io.qwertyowmx.rpc.integration.tests.utils.OrderServiceContract;
import io.qwertyowmx.rpc.integration.tests.utils.RpcServerOperations;
import io.qwertyowmx.rpc.integration.tests.utils.pojo.Order;
import io.qwertyowmx.rpc.integration.tests.utils.pojo.OrderStatus;
import io.qwertyowmx.rpc.integration.tests.utils.pojo.Product;
import io.qwertyowmx.rpc.integration.tests.utils.pojo.ProductPrice;
import io.qwertyowmx.rpc.integration.tests.utils.provider.ComplexInteractionArgsProvider;
import io.qwertyowmx.rpc.server.RpcServer;
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
