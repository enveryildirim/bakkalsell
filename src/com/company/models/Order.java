package com.company.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Sipariş verileri tutan sınıf
 */
public class Order {

    public User customer;
    public List<CartItem> orders;

    public Order(User customer, List<CartItem> orders) {
        this.customer = customer;
        this.orders = orders;
    }

}
