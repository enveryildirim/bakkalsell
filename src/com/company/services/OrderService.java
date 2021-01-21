package com.company.services;

import com.company.dal.DB;
import com.company.dal.IRepository;
import com.company.models.CartItem;
import com.company.models.Order;
import com.company.models.Product;
import com.company.models.User;

import java.util.List;

public class OrderService {
    private IRepository<Product> productRepository;
    private IRepository<CartItem> cartItemIRepository;
    private IRepository<Order> orderIRepository;
    private IRepository<User> userIRepository;


    public OrderService(IRepository<User> userIRepository,IRepository<Product> productRepository, IRepository<CartItem> cartItemIRepository, IRepository<Order> orderIRepository) {
        this.productRepository = productRepository;
        this.cartItemIRepository = cartItemIRepository;
        this.orderIRepository = orderIRepository;
        this.userIRepository=userIRepository;
    }

    public void addOrder(){

        //todo logined userı getirme işini repository sınıfnda yapılacak
        orderIRepository.create(new Order(DB.currentLoginedUser,cartItemIRepository.getAll()));
        List<Order> orderList= DB.orders;

    }
    static String durum="";
    public String listOrder(){
        durum="";
        Order order = orderIRepository.getById(DB.currentLoginedUser.getId());
        if(order==null)
            return "Sepetiniz boş";
        durum=durum+String.format("%s isimli kullanıcının şiparişleri \n",order.customer.getNameSurname());
        order.orders.forEach(o->durum=durum+String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", o.product.getId(),o.product.getName(),o.product.getPrice(),o.getQuantity()));
        return durum;
    }

    static String allOrder="";
    public String getAllOrder(){
        allOrder="";
        orderIRepository.getAll()
                .forEach(o->{
                    allOrder=allOrder+String.format("%d ID'ye sahip %s isimli kullanıcının şiparişleri \n",o.customer.getId(),o.customer.getNameSurname());
                    o.orders.forEach(c->{
                        allOrder=allOrder+String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", c.product.getId(),c.product.getName(),c.product.getPrice(),c.getQuantity());
                    });
                });

        return allOrder;
    }


}
