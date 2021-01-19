package com.company.pages;

import com.company.dal.DB;
import com.company.models.PageName;
import com.company.services.ProductService;
import com.company.services.UserService;

import java.util.Scanner;

public class HomePage extends PageBase{
    public HomePage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.println("Anasayfa");
        if(DB.currentLoginedUser.getUserType()==1)
            return PageName.PRODUCT_SALE;

        System.out.println("İşleminizi Seçiniz");
        System.out.println("1-Satış");
        System.out.println("2-Ürün Listele");
        Scanner in = new Scanner(System.in);
        String islem =in.nextLine();


        if(islem.equals("0"))
            return PageName.LOGIN;

        if(islem.equals("1"))
            return PageName.PRODUCT_SALE;


        return PageName.HOME;

    }

    void renderUser(){
        System.out.println("İşleminizi Seçiniz");
        System.out.println("1-Satış");
        System.out.println("2-Ürün Listele");
    }
}
