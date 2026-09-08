package tech.oliver.ecommerce.service;

import tech.oliver.ecommerce.Order;
import tech.oliver.ecommerce.repository.OrderRepository;

public class OrderService {
    private OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void placeOrder(Order order) {
        if (order.getTotal() <= 0) {
            throw new IllegalArgumentException("Total do pedido deve ser positivo");
        }
        repository.save(order);
    }

    public Order getOrder(int id) {
        return repository.findById(id);
    }
}
