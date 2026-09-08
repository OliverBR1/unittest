package tech.oliver.ecommerce.repository;

import tech.oliver.ecommerce.Order;

public interface OrderRepository {

    void save(Order order);
    Order findById(int id);
}
