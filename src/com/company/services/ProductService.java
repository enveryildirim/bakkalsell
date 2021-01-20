package com.company.services;

import com.company.dal.IRepository;
import com.company.dal.ProductRepository;
import com.company.models.CartItem;
import com.company.models.Product;
import com.company.models.User;

import java.util.List;

public class ProductService {
    private IRepository<Product> productRepository;

    public ProductService(IRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product){
        int size=productRepository.getAll().size();
        int newID=productRepository.getAll().get(size-1).getId()+1;
        product.setId(newID);
        productRepository.create(product);
    }
    public void updateProduct(Product product){
        productRepository.update(product);
    }
    public void deleteProduct(Product product){
        productRepository.delete(product);
    }
    public Product getProductById(int id){
        return (Product) productRepository.getById(id);

    }
    public List<Product> getAll(){
        return this.productRepository.getAll();
    }
    public void Sales(){

    }
    public void insertProductToCart(Product product){
        //CartItem.productsList.add(product);
    }
}
