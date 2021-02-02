package com.company.pages;


import com.company.dal.DB;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.models.User;
import com.company.models.UserType;
import com.company.services.CartService;
import com.company.services.OrderService;
import com.company.services.ProductService;
import com.company.services.UserService;
import com.company.models.Result;

import java.util.List;

public class TestPage extends PageBase {
    private UserService userService;
    private ProductService productService;
    private CartService cartService;
    private OrderService orderService;

    public TestPage(UserService userService, ProductService productService, CartService cartService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @Override
    public boolean isRequiredAuth() {
        return false;
    }

    @Override
    public PageName render() {
        User user=new User("dgdfgdfg","12lşilşi36","1234", UserType.EMPLOYEE);
        Product product=new Product("elma",1,10);

        Result<Product> result=productService.createProductResult(product);

        List<Product> products= DB.products;
        System.out.println(result.getMessage());
        inputData.nextLine();
        return PageName.TEST;
    }


}