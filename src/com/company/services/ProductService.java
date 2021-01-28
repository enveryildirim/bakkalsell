package com.company.services;

import com.company.dal.IRepository;
import com.company.models.CartItem;
import com.company.models.Product;

import java.util.List;

public class ProductService {
    private IRepository<Product> productRepository;
    private IRepository<CartItem> cartItemIRepository;

    public ProductService(IRepository productRepository, IRepository cartItemIRepository) {
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
        return (Product) productRepository.getById(id);

    }

    public List<Product> getAll() {
        return this.productRepository.getAll();
    }

    //todo javadoc bilgilendirmeler eklenecek ne iş yapar ne fonsiyon döner.
    public void insertProductToCart(Product product, int quantity) {
        CartItem cartItem = cartItemIRepository.getById(product.getId());
        if (cartItem == null)
            cartItemIRepository.create(new CartItem(product, quantity));
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemIRepository.update(cartItem);
        }
    }

    public void clearCart() {
        cartItemIRepository.getAll().clear();
    }

    public List<CartItem> getCart() {

        return cartItemIRepository.getAll();
    }

    static String allCartString = "";

    public String getCartToString() {
        allCartString = "";
        cartItemIRepository.getAll().forEach(c -> {
            allCartString = allCartString + String.format("Kod:{%d} Ad:{%s} Fiyat:{%f}  Alınan Miktar:{%d} Tutar:{%f} \n", c.getProduct().getId(), c.getProduct().getName(), c.getProduct().getPrice(), c.getQuantity(), c.getProduct().getPrice() * c.getQuantity());
        });

        return allCartString;
    }


    public void deleteProductFromCart(CartItem cartItem) {
        cartItemIRepository.delete(cartItem);
    }

    static String getAllProductForCartText = "";

    public String getAllProductForCart() {
        getAllProductForCartText = "";
        productRepository.getAll().stream().forEach(p -> {

            CartItem cartItem = cartItemIRepository.getAll().stream()
                    .filter(c -> c.getProduct().getId() == p.getId())
                    .findFirst()
                    .orElse(null);

            if (cartItem != null)
                getAllProductForCartText = getAllProductForCartText + String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(), p.getName(), p.getPrice(), (p.getQuantity() - cartItem.getQuantity()));
            else
                getAllProductForCartText = getAllProductForCartText + String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(), p.getName(), p.getPrice(), p.getQuantity());

        });

        return getAllProductForCartText;
    }

    public void saleCart() {
        cartItemIRepository.getAll().forEach(c -> {
            Product product = productRepository.getAll().stream().filter(p -> p.getId() == c.getProduct().getId())
                    .findFirst()
                    .orElse(null);
            product.setQuantity(product.getQuantity() - c.getQuantity());

        });

        cartItemIRepository.getAll().clear();
    }

    public boolean isEmptyCart(){
        return cartItemIRepository.getAll().size() == 0;
    }

}
