package com.company.pages;

import com.company.Constant;
import com.company.dal.DB;
import com.company.models.PageName;
import com.company.models.UserType;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class HomePage extends PageBase {
    public HomePage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.println("Anasayfa");

        //todo logineduserların düzenlenmesi her sınıftan
        if (DB.currentLoginedUser.getUserType() == UserType.EMPLOYEE)
            return PageName.PRODUCT_SALE;

        if (DB.currentLoginedUser.getUserType() == UserType.CUSTOMER) {
            return PageName.ORDER;
        }

        System.out.println("1-Satış");
        System.out.println("2-Yeni Ürün Ekle");
        System.out.println("3-Yeni Kullanıcı Ekle");
        System.out.println("4-Kullanıcılar ");
        System.out.println("5-Ürünler ");
        System.out.println("6-Siparişler ");
        System.out.println("0-Çıkış");

        String labelCommand = "İşleminizi Seçiniz";
        boolean isRequiredCommand = true;
        Input inCommand = new Input(labelCommand, Constant.HOME_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inCommand.render();

        if (command.equals("0"))
            return PageName.LOGIN;

        if (command.equals("1"))
            return PageName.PRODUCT_SALE;

        if (command.equals("2"))
            return PageName.PRODUCT_CREATE;

        if (command.equals("3"))
            return PageName.USER_CREATE;

        if (command.equals("4"))
            return PageName.USER_LIST;

        if (command.equals("5"))
            return PageName.PRODUCT_LIST;

        if (command.equals("6"))
            return PageName.ORDER_VIEW;

        return PageName.HOME;

    }


}
