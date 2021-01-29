package com.company.pages;


import com.company.Constant;
import com.company.models.*;
import com.company.pages.components.Input;
import com.company.services.CartService;
import com.company.services.OrderService;
import com.company.services.ProductService;
import com.company.services.UserService;

public class TestPage extends PageBase {
    private UserService userService;
    private ProductService productService;
    private CartService cartService;
    private OrderService orderService;
    public TestPage(UserService userService, ProductService productService, CartService cartService, OrderService orderService) {
        this.userService=userService;
        this.productService=productService;
        this.cartService=cartService;
        this.orderService=orderService;
    }

    @Override
    public boolean isRequiredAuth() {
        return false;
    }

    @Override
    public PageName render() {

        return PageName.TEST;
    }



}