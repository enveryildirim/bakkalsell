package com.company.models;

import java.util.ArrayList;
import java.util.List;

public class CartItem {
    public Product product=null;
    public int quantity=0;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }



}
