package com.company.services;

import com.company.dal.CartItemRepository;
import com.company.dal.ProductRepository;
import com.company.models.Product;

import java.util.List;

/**
 * Kullanıcıdan gelen veriler ve talimatlara göre  Ürün ve Sepet ile alakalı işlemlerin yapıldığı sıınf
 */
public class ProductService {

    private ProductRepository productRepository;
    private CartItemRepository cartItemIRepository;

    public ProductService(ProductRepository productRepository, CartItemRepository cartItemIRepository) {
        this.productRepository = productRepository;
        this.cartItemIRepository = cartItemIRepository;
    }

    public void createProduct(Product product) {
        productRepository.create(product);
    }

    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public Product getProductById(int id) {
        return productRepository.getById(id);

    }

    public List<Product> getAll() {
        return this.productRepository.getAll();
    }


}
