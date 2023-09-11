package ru.aston.oshchepkov_aa.task4.order;

import ru.aston.oshchepkov_aa.task4.DaoDataEntityLayer;

import java.util.List;

public interface OrderRepository extends DaoDataEntityLayer<Order> {
    List<Order> getUserOrders(int userId);
    boolean assignOrderToUserByIds(int userId, int orderId);
    boolean deleteOrderUserPairByIds(int userId, int orderId);
}
