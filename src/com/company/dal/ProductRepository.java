package com.company.dal;

import com.company.models.Product;
import com.company.models.User;

import java.util.List;
import java.util.UUID;

public class ProductRepository implements IRepository<Product>{
    @Override
    public boolean create(Product product) {
        return DB.products.add(product);
    }


    @Override
    public boolean update(Product product) {
        Product p = this.getById(product.getId());
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setQuantity(product.getQuantity());
        return true;
    }

    @Override
    public boolean delete(Product product) {
        return DB.products.remove(product);
    }

    @Override
    public List<Product> getAll() {
        return DB.products;
    }

    @Override
    public Product getById(int id) {
        return DB.products.stream()
                .filter(p->p.getId()==id)
                .findFirst()
                .orElse(null);
    }


}
