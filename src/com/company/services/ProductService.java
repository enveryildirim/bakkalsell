package com.company.services;

import com.company.dal.IRepository;
import com.company.dal.ProductRepository;
import com.company.models.CartItem;
import com.company.models.Product;
import com.company.models.User;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private IRepository<Product> productRepository;
    private IRepository<CartItem> cartItemIRepository;

    public ProductService(IRepository productRepository,IRepository cartItemIRepository)
    {
        this.productRepository = productRepository;
        this.cartItemIRepository=cartItemIRepository;
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


    /**
     * Cart ile alakalı kısım
     */

    public void insertProductToCart(Product product,int quantity){
        CartItem cartItem=cartItemIRepository.getById(product.getId());
        if(cartItem==null)
            cartItemIRepository.create(new CartItem(product,quantity));
        else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
            cartItemIRepository.update(cartItem);
        }
    }
    public void clearCart(){
        cartItemIRepository.getAll().clear();
    }
    static String durum="";
    public String getAllProductForCart(){
        durum="";
        productRepository.getAll().stream().forEach(p-> {

            CartItem cartItem= cartItemIRepository.getAll().stream()
                    .filter(c->c.product.getId()==p.getId())
                    .findFirst()
                    .orElse(null);
            if(cartItem!=null)
               durum =durum+String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(),p.getName(),p.getPrice(),(p.getQuantity()-cartItem.quantity));
            else
                durum=durum+String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(),p.getName(),p.getPrice(),p.getQuantity());

        });

        return durum;
    }
    public List<CartItem> getCart(){

        return cartItemIRepository.getAll();
    }

    public void saleCart(){
        cartItemIRepository.getAll().forEach(c->{
                Product product=productRepository.getAll().stream().filter(p->p.getId()==c.product.getId())
                        .findFirst()
                        .get();
                product.setQuantity(product.getQuantity()-c.getQuantity());

        });

        cartItemIRepository.getAll().clear();
    }

}
