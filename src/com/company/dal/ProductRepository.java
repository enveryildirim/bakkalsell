package com.company.dal;

import com.company.models.Product;

import java.util.List;

public class ProductRepository implements IRepository<Product> {
    @Override
    public void create(Product product) {
        int size = DB.products.size();
        int newID = size == 0 ? 0 : DB.products.get(size - 1).getId() + 1;
        product.setId(newID);
        DB.products.add(product);
    }


    @Override
    public void update(Product product) {
        Product p = this.getById(product.getId());
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setQuantity(product.getQuantity());

    }

    @Override
    public void delete(Product product) {
        DB.products.remove(product);
    }

    @Override
    public List<Product> getAll() {
        return DB.products;
    }

    @Override
    public Product getById(int id) {
        return DB.products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }


}
