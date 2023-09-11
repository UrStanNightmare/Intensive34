package ru.aston.oshchepkov_aa.task4.order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    Order findOrderById(int id);
    boolean deleteOrderById(int id);
    boolean createOrder(Order user);
    Order updateOrder(Order user);
    List<Order> getUserOrders(int id);
    boolean assignOrderToUserByIds(int userId, int orderId);
    boolean deleteOrderUserPairByIds(int userId, int orderId);
}
