package com.company.services;

import com.company.dal.IRepository;
import com.company.dal.ProductRepository;
import com.company.models.Product;

import java.util.List;

public class ProductService {
    private IRepository productRepository;

    public ProductService(IRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product){
        productRepository.create(product);
    }
    public void updateProduct(Product product){
        productRepository.update(product);
    }
    public void deleteProduct(Product product){
        productRepository.delete(product);
    }
    public Product getProductById(int id){
        ProductRepository pr =new ProductRepository();

        return (Product) productRepository.getById(1);

    }
    public List<Product> getAll(){
        return this.productRepository.getAll();
    }
    public void Sales(){

    }
}
