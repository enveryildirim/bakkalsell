package com.company.dal;

import com.company.models.CartItem;

import java.util.List;

public class CartItemRepository implements IRepository<CartItem>{
    @Override
    public boolean create(CartItem cartItem) {
        DB.cart.add(cartItem);
        return true;
    }

    @Override
    public boolean update(CartItem cartItem) {
        return false;
    }

    @Override
    public boolean delete(CartItem cartItem) {
        return DB.cart.remove(cartItem);
    }

    @Override
    public List<CartItem> getAll() {
        return DB.cart;
    }

    @Override
    public CartItem getById(int id) {
        return DB.cart.stream()
                .filter(p->p.product.getId()==id)
                .findFirst()
                .orElse(null);
    }
}
