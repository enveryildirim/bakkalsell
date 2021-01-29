package com.company.services;

import com.company.dal.IRepository;
import com.company.models.CartItem;
import com.company.models.Product;

import java.util.List;

/**
 * Kullanıcıdan gelen veriler ve talimatlara göre  Ürün ve Sepet ile alakalı işlemlerin yapıldığı sıınf
 */
public class ProductService {

    static String cartListString = "";
    static String productListString = "";
    private IRepository<Product> productRepository;
    private IRepository<CartItem> cartItemIRepository;

    public ProductService(IRepository<Product> productRepository, IRepository<CartItem>  cartItemIRepository) {
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

    /**
     * Sepete ürün ekler
     * @param product sepete eklenecek ürün
     * @param quantity sepete eklenecek ürün miktarı
     */
    public void insertProductToCart(Product product, int quantity) {
        CartItem cartItem = cartItemIRepository.getById(product.getId());
        if (cartItem == null)
            cartItemIRepository.create(new CartItem(product, quantity));
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemIRepository.update(cartItem);
        }
    }

    /**
     * Sepeti temizler
     */
    public void clearCart() {
        cartItemIRepository.getAll().clear();
    }

    public List<CartItem> getCartAll() {

        return cartItemIRepository.getAll();
    }

    /**
     * Tüm sepeti string döner
     * @return String sepeti nesnesini string formatında döner
     */
    public String getCartListConvertToString() {
        cartListString = "";
        if(cartItemIRepository.getAll().isEmpty())
            return cartListString;

        cartItemIRepository.getAll().forEach(cartItem -> {
            cartListString = cartListString + String.format("Kod:{%d} Ad:{%s} Fiyat:{%f}  Alınan Miktar:{%d} Tutar:{%f} \n",
                    cartItem.getProduct().getId(), cartItem.getProduct().getName(), cartItem.getProduct().getPrice(),
                    cartItem.getQuantity(), cartItem.getProduct().getPrice() * cartItem.getQuantity());
        });
        int cartSize=cartItemIRepository.getAll().size();
        float sumPrice= (float) cartItemIRepository.getAll()
                .stream()
                .mapToDouble(cartItem ->cartItem.getProduct().getPrice()*cartItem.getQuantity())
                .sum();
        String summaryCartString=String.format("\nSepetteki Ürün Sayısı: %d\nToplam tutar: %f\n",cartSize,sumPrice);
        cartListString+=summaryCartString;
        return cartListString;
    }

    /**
     * İstenilen ürünü sepetten siler
     * @param cartItem silenmesi istenen ürün
     */
    public void deleteProductFromCart(CartItem cartItem) {
        cartItemIRepository.delete(cartItem);
    }

    /**
     * Tüm ürünlerin string formatında döner
     * @return String ürünleri string formatında döner
     */
    public String getAllProductConvertToString() {
        productListString = "";
        productRepository.getAll().forEach(product -> {
            CartItem cartItem = cartItemIRepository.getAll().stream()
                    .filter(c -> c.getProduct().getId() == product.getId())
                    .findFirst()
                    .orElse(null);

            if (cartItem != null)
                productListString = productListString + String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n",
                        product.getId(), product.getName(), product.getPrice(), (product.getQuantity() - cartItem.getQuantity()));
            else
                productListString = productListString + String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n",
                        product.getId(), product.getName(), product.getPrice(), product.getQuantity());

        });

        return productListString;
    }

    /**
     * Sepeti satar
     */
    public void saleCart() {
        cartItemIRepository.getAll().forEach(cartItem -> {
            Product product = productRepository.getAll().stream().filter(p -> p.getId() == cartItem.getProduct().getId())
                    .findFirst()
                    .orElse(null);
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());

        });

        cartItemIRepository.getAll().clear();
    }

    /**
     * Sepette ürün olup olmadıpıını kontrol eder
     * @return boolean döner
     */
    public boolean isEmptyCart() {
        return cartItemIRepository.getAll().size() == 0;
    }

}
