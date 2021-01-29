package com.company.dal;

import com.company.models.Product;

import java.util.List;

/**
 * Ürünler ile alakalı veritabanı işlemlerinin yapıldığı sınıf
 */
public class ProductRepository implements IRepository<Product> {

    @Override
    public void create(Product product) {

        if (DB.products.isEmpty()) {
            product.setId(1);
        } else {
            int productListSize = DB.products.size();
            Product lastProduct = DB.products.get(productListSize - 1);
            int newID = lastProduct.getId() + 1;
            product.setId(newID);
        }

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
