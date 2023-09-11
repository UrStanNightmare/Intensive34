package ru.aston.oshchepkov_aa.task4.order;

import ru.aston.oshchepkov_aa.task4.cli.exception.ValidationException;

import java.util.List;

public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteOrderById(int id) {
        return orderRepository.deleteById(id);
    }

    @Override
    public boolean createOrder(Order order) {
        if (validate(order)){
            return orderRepository.create(order);
        }
        throw new ValidationException("Fields not valid!");
    }

    @Override
    public Order updateOrder(Order order) {
        if (validate(order)){
            return orderRepository.update(order).orElse(null);
        }
        throw new ValidationException("Fields not valid!");
    }

    @Override
    public List<Order> getUserOrders(int id) {
        return orderRepository.getUserOrders(id);
    }

    @Override
    public boolean assignOrderToUserByIds(int userId, int orderId) {
        return orderRepository.assignOrderToUserByIds(userId, orderId);
    }

    @Override
    public boolean deleteOrderUserPairByIds(int userId, int orderId) {
        return orderRepository.deleteOrderUserPairByIds(userId, orderId);
    }

    private boolean validate(Order order){
        var description = order.getDescription();
        if (null == description || description.isBlank()){
            return false;
        }
        var amount = order.getAmount();
        return amount >= 1;
    }
}
