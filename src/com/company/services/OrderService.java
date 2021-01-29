package com.company.services;

import com.company.dal.IRepository;
import com.company.dal.UserRepository;
import com.company.models.CartItem;
import com.company.models.Order;
import com.company.models.Product;
import com.company.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 *Kullanıcıdan gelen veriler ve talimatlara göre Siparişle ile alakalı işlerin yapıldığı sınıf
 */
public class OrderService {

    static String listOrderString = "";
    static String allOrderString = "";
    private IRepository<Product> productRepository;
    private IRepository<CartItem> cartItemIRepository;
    private IRepository<Order> orderIRepository;
    private IRepository<User> userIRepository;

    public OrderService(IRepository<User> userIRepository, IRepository<Product> productRepository, IRepository<CartItem> cartItemIRepository, IRepository<Order> orderIRepository) {
        this.productRepository = productRepository;
        this.cartItemIRepository = cartItemIRepository;
        this.orderIRepository = orderIRepository;
        this.userIRepository = userIRepository;
    }

    /**
     * Kullanıcının siparişine ürün eklediği fonksiyon
     * @param product siparişi verilen ürün
     * @param quantity şipariş miktarı
     */
    public void addProductToOrder(Product product, int quantity) {
        User user = ((UserRepository) userIRepository).getLoginedUser();

        Order order = orderIRepository.getAll()
                .stream()
                .filter(o -> o.customer.getId() == user.getId())
                .findFirst()
                .orElse(null);

        if (order == null) {
            List<CartItem> cartItemList = new ArrayList<>();
            cartItemList.add(new CartItem(product, quantity));
            Order newOrder = new Order(user, cartItemList);
            orderIRepository.create(newOrder);
            return;
        }
        CartItem newCartItem = new CartItem(product, quantity);
        order.orders.add(newCartItem);

    }

    /**
     * Kullanıcının siparişinden istenilen ürünü siler
     * @param id ürününü silineceği şiparişin id'si
     * @param cartItem silinecek ürün item'ı
     */
    public void deleteProductFromOrder(int id, CartItem cartItem) {
        Order order = this.getOrder(id);
        if (order == null)
            return;

        if (order.orders.size() == 0) {
            orderIRepository.getAll().remove(order);
            return;
        }
        order.orders.remove(cartItem);

    }

    /**
     * Kullanıcının id'si verilen şiparişini siler
     * @param id silinecek siparişin id'si
     */
    public void deleteOrder(int id) {
        Order order = orderIRepository.getAll()
                .stream()
                .filter(o -> o.customer.getId() == id)
                .findFirst()
                .orElse(null);

        if (order != null)
            orderIRepository.getAll().remove(order);
    }

    /**
     * Giriş yapan kullanıcının siparişlerini String olarak döner
     * @return String oalrak siparişleri formatlanmış bir şekilde döner
     */
    public String getUserOrderListConvertToString() {
        listOrderString = "";
        User user = ((UserRepository) userIRepository).getLoginedUser();
        Order order = orderIRepository.getById(user.getId());
        if (order == null)
            return "Sepetiniz boş";

        listOrderString = listOrderString + String.format("%s isimli kullanıcının şiparişleri \n", order.customer.getNameSurname());
        order.orders.forEach(orderItem -> listOrderString = listOrderString + String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", orderItem.getProduct().getId(), orderItem.getProduct().getName(), orderItem.getProduct().getPrice(), orderItem.getQuantity()));
        return listOrderString;
    }

    /**
     * veritabanındaki tüm siparişleri string e cevirip döner
     * @return String siparişleri  döner
     */
    public String getAllOrderConvertToString() {
        allOrderString = "";
        orderIRepository.getAll()
                .forEach(order -> {
                    allOrderString = allOrderString + String.format("%d ID'ye sahip %s isimli kullanıcının şiparişleri \n", order.customer.getId(), order.customer.getNameSurname());
                    order.orders.forEach(c -> {
                        allOrderString = allOrderString + String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", c.getProduct().getId(), c.getProduct().getName(), c.getProduct().getPrice(), c.getQuantity());
                    });
                });

        return allOrderString;
    }

    /**
     * ID si verilen Siparişi getirir
     * @param id istenen siparişin id'si
     * @return Order nesnesi döner
     */
    public Order getOrder(int id) {
        return orderIRepository.getAll()
                .stream()
                .filter(order -> order.customer.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Siparişleri satar
     */
    public void saleOrder() {
        orderIRepository.getAll().clear();
    }

}
