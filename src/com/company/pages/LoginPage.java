package com.company.pages;

import com.company.models.PageName;
import com.company.services.ProductService;
import com.company.services.UserService;

import java.io.Console;

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
        System.out.println("!!!! Lütfen bilgilerinizi doğru giriniz Bilgilerinizi unuttuysanız Adminle görüşün\n Devam etmek için bir tuşa basın");
        in.nextLine();
        return PageName.LOGIN;
    }
}
