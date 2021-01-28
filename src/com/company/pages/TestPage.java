package com.company.pages;


import com.company.models.*;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

import java.util.List;

public class TestPage extends PageBase {
    public TestPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean isRequiredAuth() {
        return false;
    }

    @Override
    public PageName render() {
        System.out.println(UserType.values()[0]);
        Input inName = new Input("Type: ", "^[a-zA-Z0-9ğüşöçİĞÜŞÖÇ_ ]+$", true);
        String nameSurname = inName.render();

        return PageName.TEST;
    }

}