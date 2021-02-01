package com.company.services;

import com.company.dal.CartItemRepository;
import com.company.dal.IRepository;
import com.company.dal.ProductRepository;
import com.company.models.CartItem;
import com.company.models.Product;

import java.util.List;

public class CartService {
    static String cartListString = "";
    static String productListString = "";
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;
    public CartService(ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Sepete ürün ekler
     * @param product sepete eklenecek ürün
     * @param quantity sepete eklenecek ürün miktarı
     */

    public void insertProductToCart(Product product, int quantity) {
        CartItem cartItem = this.getCartItemByProductID(product.getId());
        if (cartItem == null)
            cartItemRepository.create(new CartItem(product, quantity));
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.update(cartItem);
        }
    }

    /**
     * Sepeti temizler
     */
    public void clearCart() {
        cartItemRepository.getAll().clear();
    }

    public List<CartItem> getCartAll() {

        return cartItemRepository.getAll();
    }

    /**
     * Tüm sepeti string döner
     * @return String sepeti nesnesini string formatında döner
     */
    public String getCartListConvertToString() {
        cartListString = "";
        if(cartItemRepository.getAll().isEmpty())
            return cartListString;

        cartItemRepository.getAll().forEach(cartItem -> cartListString =
                cartListString + String.format("Kod:{%d} Ad:{%s} Fiyat:{%.02f}  Alınan Miktar:{%d} Tutar:{%f} \n",
                cartItem.getProduct().getId(), cartItem.getProduct().getName(), cartItem.getProduct().getPrice(),
                cartItem.getQuantity(), cartItem.getProduct().getPrice() * cartItem.getQuantity()));

        int cartSize=cartItemRepository.getAll().size();
        float sumPrice= (float) cartItemRepository.getAll()
                .stream()
                .mapToDouble(cartItem ->cartItem.getProduct().getPrice()*cartItem.getQuantity())
                .sum();
        int sumQuantity=(int) cartItemRepository.getAll()
                .stream()
                .mapToDouble(CartItem::getQuantity)
                .sum();
        String summaryCartString = String.format("\nSepetteki Ürün Sayısı: %d  Miktarı: %d \nToplam tutar: %.02f tl\n",
                cartSize,sumQuantity, sumPrice);
        cartListString+=summaryCartString;

        return cartListString;
    }

    /**
     * İstenilen ürünü sepetten siler
     * @param cartItem silenmesi istenen ürün
     */
    public void deleteProductFromCart(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    /**
     * Tüm ürünlerin string formatında döner
     * @return String ürünleri string formatında döner
     */
    public String getAllProductConvertToString() {
        productListString = "";
        productRepository.getAll().forEach(product -> {
            CartItem cartItem = cartItemRepository.getAll().stream()
                    .filter(c -> c.getProduct().getId() == product.getId())
                    .findFirst()
                    .orElse(null);

            if (cartItem != null)
                productListString = productListString + String.format("Kod:{%d} Ad:{%s} Fiyat:{%.02f} Kalan:{%d} \n",
                        product.getId(), product.getName(), product.getPrice(), (product.getQuantity() - cartItem.getQuantity()));
            else
                productListString = productListString + String.format("Kod:{%d} Ad:{%s} Fiyat:{%.02f} Kalan:{%d} \n",
                        product.getId(), product.getName(), product.getPrice(), product.getQuantity());

        });

        return productListString;
    }

    /**
     * Sepeti satar
     */
    public void saleCart() {
        cartItemRepository.getAll().forEach(cartItem -> productRepository.getAll()
                .stream()
                .filter(p -> p.getId() == cartItem.getProduct().getId())
                .findFirst()
                .ifPresent(product -> product.setQuantity(product.getQuantity() - cartItem.getQuantity())));

        cartItemRepository.getAll().clear();
    }

    /**
     * Sepette ürün olup olmadıpıını kontrol eder
     * @return boolean döner
     */
    public boolean isEmptyCart() {
        return cartItemRepository.getAll().size() == 0;
    }

    public CartItem getCartItemByProductID(int productID){
        return cartItemRepository.getAll()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId()==productID)
                .findFirst()
                .orElse(null);
    }

}
