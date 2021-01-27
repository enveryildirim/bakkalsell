package com.company.dal;

import com.company.models.CartItem;

import java.util.List;

public class CartItemRepository implements IRepository<CartItem>{
    @Override
    public void  create(CartItem cartItem) {
        DB.cart.add(cartItem);

    }

    @Override
    public void update(CartItem cartItem) {

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
                .filter(p->p.product.getId()==id)
                .findFirst()
                .orElse(null);
    }
}
