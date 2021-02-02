package com.company.services;

import com.company.dal.CartItemRepository;
import com.company.dal.ProductRepository;
import com.company.models.ICheckable;
import com.company.models.IResult;
import com.company.models.Product;
import com.company.models.Result;

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

    public Result<Product> createProduct(Product product) {
        Result<Product> validationResult = this.isValidProductResult(product);
        if (validationResult.isSuccess()) {
            boolean isSuccessful = productRepository.create(product);
            Result<Product> creationResult = new Result<>(isSuccessful, "Ürün Başarıyla Kayıt Edildi", product);
            return creationResult;
        }
        return validationResult;

    }

    public Result<Product> updateProduct(Product product) {
        Result<Product> result = this.isValidProductResult(product);
        if (result.isSuccess()) {
            boolean isSuccessful = productRepository.update(product);
            Result<Product> updatingResult = new Result<>(isSuccessful, "Ürün Başarıyla Güncellendi", product);
            return updatingResult;
        }
        return result;
    }

    public Result<Product> deleteProduct(Product product) {
        boolean isSuccessful = productRepository.delete(product);
        Result<Product> deletingResult = new Result<>(isSuccessful, "Ürün başarılı şekilde silindi", product);
        return deletingResult;
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

    public Result<Product> isValidProductResult(Product product) {

        List<IResult<Product>> checkList = new ArrayList<>();

        IResult<Product> checkEmpty = (model) -> {
            Result<Product> emptyResult = new Result<>(true, "Kontrol Edildi", model);
            if (model.getName().isEmpty()) {
                emptyResult.setSuccess(false);
                emptyResult.setMessage("Ürün ismi girilmesi gerekli");
            }
            return emptyResult;
        };

        IResult<Product> checkPrice = (model) -> {
            Result<Product> productEmptyResult = new Result<>(true, "Fiyat Uygun", model);
            float max = 1000;
            if (model.getPrice() <= 0 || model.getPrice() > 1000) {
                productEmptyResult.setSuccess(false);
                productEmptyResult.setMessage("Ürün fiyatı 0-1000 tl arasında olabilir.");
            }
            return productEmptyResult;
        };

        IResult<Product> checkQuantity = (model) -> {
            Result<Product> productEmptyResult = new Result<>(true, "Stok Sayısı Uygun", model);
            if (model.getQuantity() <= 0 || model.getQuantity() > 1000) {
                productEmptyResult.setSuccess(false);
                productEmptyResult.setMessage("Stok sayısı sadece 0-1000 arasında olabilir.");
            }
            return productEmptyResult;
        };


        checkList.add(checkEmpty);
        checkList.add(checkPrice);
        checkList.add(checkQuantity);

        Result<Product> productEmptyResult = new Result<>(true, "Model Uygun", product);
        for (IResult<Product> checker : checkList) {
            Result<Product> sonuc = checker.run(product);
            if (sonuc.isSuccess())
                continue;
            else {
                return checker.run(product);
            }
        }
        return productEmptyResult;
    }
}
