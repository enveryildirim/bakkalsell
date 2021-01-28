package com.company.services;

import com.company.dal.DB;
import com.company.dal.IRepository;
import com.company.dal.UserRepository;
import com.company.models.CartItem;
import com.company.models.Order;
import com.company.models.Product;
import com.company.models.User;

import java.util.ArrayList;
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

    public void addOrder(Product product,int quantity){
        User user=((UserRepository)userIRepository).getLoginedUser();;

        Order order=orderIRepository.getAll()
                .stream()
                .filter(o->o.customer.getId()==user.getId())
                .findFirst()
                .orElse(null);

        if(order==null){
            List<CartItem> cartItemList = new ArrayList<>();
            cartItemList.add(new CartItem(product,quantity));
            Order newOrder=new Order(user,cartItemList);
            orderIRepository.create(newOrder);
            return;
        }

        order.orders.add(new CartItem(product,quantity));

    }
    public void deleteCartItem(int id,CartItem cartItem){
       Order order= this.getOrder(id);
       if(order==null)
           return;

       if(order.orders.size()==0){
           orderIRepository.getAll().remove(order);
           return;
       }
       order.orders.remove(cartItem);


    }
    public void deleteOrder(int id){
        Order order= orderIRepository.getAll()
                .stream()
                .filter(o->o.customer.getId()==id)
                .findFirst()
                .orElse(null);
        if(order!=null)
            orderIRepository.getAll().remove(order);
    }

    static String listOrderText="";
    public String listOrder(){
        listOrderText="";
        Order order = orderIRepository.getById(DB.currentLoginedUser.getId());
        if(order==null)
            return "Sepetiniz boş";
        listOrderText=listOrderText+String.format("%s isimli kullanıcının şiparişleri \n",order.customer.getNameSurname());
        order.orders.forEach(o->listOrderText=listOrderText+String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", o.getProduct().getId(),o.getProduct().getName(),o.getProduct().getPrice(),o.getQuantity()));
        return listOrderText;
    }

    static String allOrderText="";
    public String getAllOrder(){
        allOrderText="";
        orderIRepository.getAll()
                .forEach(o->{
                    allOrderText=allOrderText+String.format("%d ID'ye sahip %s isimli kullanıcının şiparişleri \n",o.customer.getId(),o.customer.getNameSurname());
                    o.orders.forEach(c->{
                        allOrderText=allOrderText+String.format("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", c.getProduct().getId(),c.getProduct().getName(),c.getProduct().getPrice(),c.getQuantity());
                    });
                });

        return allOrderText;
    }


    public Order getOrder(int id){
        return orderIRepository.getAll()
                .stream()
                .filter(o->o.customer.getId()==id)
                .findFirst()
                .orElse(null);
    }

    public void saleOrder(){
        orderIRepository.getAll().clear();
    }

}
