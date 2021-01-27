package com.company.dal;

import com.company.models.Order;
import com.company.models.Product;

import java.util.List;

public class OrderRepository implements IRepository<Order> {
    @Override
    public void create(Order order) {
        DB.orders.add(order);
    }

    @Override
    public void update(Order order) {
        Order updatingOrder = this.getById(order.customer.getId());
        updatingOrder.orders = order.orders;
    }

    @Override
    public void delete(Order order) {
        DB.orders.remove(order);
    }

    @Override
    public List<Order> getAll() {
        return DB.orders;
    }

    @Override
    public Order getById(int id) {
        return DB.orders.stream()
                .filter(p -> p.customer.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
