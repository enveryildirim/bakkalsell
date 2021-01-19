package com.company.pages;

import com.company.models.PageName;
import com.company.services.ProductService;
import com.company.services.UserService;

public class LoginPage extends PageBase{
    public LoginPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return false;
    }

    @Override
    public PageName render() {
        System.out.println("Kullanıcı Adı");
        String username=in.nextLine();

        System.out.println("Şifre");
        String password=in.nextLine();

        if(this.userService.login(username,password))
            return PageName.HOME;

        return PageName.LOGIN;
    }
}
