package com.company.dal;

import com.company.models.CartItem;

import java.util.List;

/**
 * Sepet ile alakalı veritabanı işlemlerinin yapıldığı sınıf
 */
public class CartItemRepository implements IRepository<CartItem> {

    @Override
    public boolean create(CartItem cartItem) {
        return DB.cart.add(cartItem);
    }

    @Override
    public boolean update(CartItem cartItem) {
        CartItem updatingCartItem = this.getById(cartItem.getProduct().getId());
        updatingCartItem.setProduct(cartItem.getProduct());
        updatingCartItem.setQuantity(cartItem.getQuantity());
        return true;
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
                .filter(p -> p.getProduct().getId() == id)
                .findFirst()
                .orElse(null);
    }
}
