package com.company.dal;

import com.company.models.Order;

import java.util.List;

/**
 * Siparişler ile alakalı veritabanı işlemlerinin yapıldığı sınıf
 */
public class OrderRepository implements IRepository<Order> {

    @Override
    public boolean create(Order order) {
        return DB.orders.add(order);
    }

    @Override
    public boolean update(Order order) {
        Order updatingOrder = this.getById(order.customer.getId());
        updatingOrder.orders = order.orders;
        return true;
    }

    @Override
    public boolean delete(Order order) {
        return DB.orders.remove(order);
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
