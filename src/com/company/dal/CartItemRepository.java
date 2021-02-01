package com.company.dal;

import com.company.models.CartItem;

import java.util.List;

/**
 * Sepet ile alakalı veritabanı işlemlerinin yapıldığı sınıf
 */
public class CartItemRepository implements IRepository<CartItem> {

    @Override
    public void create(CartItem cartItem) {
        DB.cart.add(cartItem);
    }

    @Override
    public void update(CartItem cartItem) {
        CartItem updatingCartItem = this.getById(cartItem.getProduct().getId());
        updatingCartItem.setProduct(cartItem.getProduct());
        updatingCartItem.setQuantity(cartItem.getQuantity());
    }

    @Override
    public void delete(CartItem cartItem) {
        DB.cart.remove(cartItem);
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
