package io.qwertyowrmx.rpc.integration.tests.utils;

import io.qwertyowrmx.rpc.integration.tests.utils.pojo.Order;
import io.qwertyowrmx.rpc.integration.tests.utils.pojo.OrderStatus;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderService implements OrderServiceContract {

    private final List<Order> orders;

    public OrderService() {
        this.orders = new CopyOnWriteArrayList<>();
    }

    @Override
    public void submitOrder(Order order) {
        orders.add(order);
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order updateOrderStatus(BigInteger orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return order;
    }

    @Override
    public Order getOrderById(BigInteger id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst().get();
    }
}
