package com.company.pages;

import com.company.dal.DB;
import com.company.models.PageName;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

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

        if(DB.currentLoginedUser.getUserType()==2){
            return PageName.ORDER;
        }

        System.out.println("1-Satış");
        System.out.println("2-Yeni Ürün Ekle");
        System.out.println("3-Yeni Kullanıcı Ekle");
        System.out.println("4-Kullanıcılar ");
        System.out.println("5-Ürünler ");
        System.out.println("6-Siparişler ");
        System.out.println("0-Çıkış");

        Input inIslem=new Input(null,"İşleminizi Seçiniz","[0123456]",true);
        String islem =inIslem.render();

        if(islem.equals("0"))
            return PageName.LOGIN;

        if(islem.equals("1"))
            return PageName.PRODUCT_SALE;

        if(islem.equals("2"))
            return PageName.PRODUCT_CREATE;

        if(islem.equals("3"))
            return PageName.USER_CREATE;

        if(islem.equals("4"))
            return PageName.USER_LIST;

        if(islem.equals("5"))
            return PageName.PRODUCT_LIST;

        if (islem.equals("6"))
            return PageName.ORDER_VIEW;

        return PageName.HOME;

    }


}
