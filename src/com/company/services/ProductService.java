package com.company.services;

import com.company.dal.CartItemRepository;
import com.company.dal.ProductRepository;
import com.company.models.ICheckable;
import com.company.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Ürün ile alakalı işlemlerin yapıldığı sıınf
 */
public class ProductService {

    private ProductRepository productRepository;
    private CartItemRepository cartItemIRepository;

    public ProductService(ProductRepository productRepository, CartItemRepository cartItemIRepository) {
        this.productRepository = productRepository;
        this.cartItemIRepository = cartItemIRepository;
    }

    public boolean createProduct(Product product) {
        if (this.isValidProduct(product)) {
            productRepository.create(product);
            return true;
        } else {
            return false;
        }

    }

    public boolean updateProduct(Product product) {
        if (this.isValidProduct(product)) {
            productRepository.update(product);
            return true;
        } else {
            return false;
        }
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

    public boolean isValidProduct(Product product) {
        List<ICheckable<Product>> checkList = new ArrayList<>();
        ICheckable<Product> checkNameEmpty = (model) -> !model.getName().isEmpty();
        ICheckable<Product> checkPrice = (model) -> model.getPrice() > 0 && model.getPrice() < 1000;
        ICheckable<Product> checkQuantity = (model) -> model.getQuantity() > 0 && model.getQuantity() < 1000;

        checkList.add(checkNameEmpty);
        checkList.add(checkPrice);
        checkList.add(checkQuantity);

        AtomicBoolean isChecked = new AtomicBoolean(false);
        for (ICheckable<Product> checker : checkList) {
            if (checker.chech(product))
                isChecked.set(true);
            else {
                isChecked.set(false);
                break;
            }
        }
        return isChecked.get();
    }

}
