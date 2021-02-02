package com.company.dal;

import com.company.models.Product;

import java.util.List;

/**
 * Ürünler ile alakalı veritabanı işlemlerinin yapıldığı sınıf
 */
public class ProductRepository implements IRepository<Product> {

    @Override
    public boolean create(Product product) {

        if (DB.products.isEmpty()) {
            product.setId(1);
        } else {
            int productListSize = DB.products.size();
            Product lastProduct = DB.products.get(productListSize - 1);
            int newID = lastProduct.getId() + 1;
            product.setId(newID);
        }

        return DB.products.add(product);
    }

    @Override
    public boolean update(Product product) {

        Product updatingProduct = this.getById(product.getId());
        updatingProduct.setName(product.getName());
        updatingProduct.setPrice(product.getPrice());
        updatingProduct.setQuantity(product.getQuantity());
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
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
