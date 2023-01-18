package io.qwertyowrmx.rpc.integration.tests.utils;


import io.qwertyowrmx.rpc.integration.tests.utils.pojo.Order;
import io.qwertyowrmx.rpc.integration.tests.utils.pojo.OrderStatus;

import java.math.BigInteger;
import java.util.List;

public interface OrderServiceContract {
    void submitOrder(Order order);

    List<Order> getOrders();

    Order updateOrderStatus(BigInteger orderId, OrderStatus status);

    Order getOrderById(BigInteger id);
}
