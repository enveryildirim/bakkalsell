package com.company.pages;

import com.company.models.PageName;
import com.company.services.OrderService;
import com.company.services.ProductService;
import com.company.services.UserService;

import java.util.Scanner;

public abstract class PageBase {
    protected UserService userService;
    protected ProductService productService;
    protected Scanner in = new Scanner(System.in);
    protected OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    public PageBase(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }
    public boolean isInt(String deger){
        try {
            int a=Integer.parseInt(deger);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public abstract boolean requiredAuth();
    public abstract PageName render();
}
