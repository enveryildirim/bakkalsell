package com.company.pages;

import com.company.models.PageName;
import com.company.services.ProductService;
import com.company.services.UserService;

public class OrderViewPage extends PageBase{
    public OrderViewPage(UserService userService, ProductService productService) {
        super(userService, productService);


    }

    @Override
    public boolean requiredAuth() {
        return true;
    }

    @Override
    public PageName render() {

        System.out.printf(orderService.getAllOrder());
        System.out.println("Satmak istediğiniz siparişin kullanıcının ıd sini giriniz ");
        String id = in.nextLine();
        //todo id sorgulaması ve doğrulması yapılacack
        System.out.printf("%s id li -- isimli siparişi onayla",id);
        String cevap=in.nextLine();
        if(cevap.equals("evet")){
            System.out.println("Satış yapıldı");
            //todo satış işlemi yapılacaak
        }else if(cevap.equals("iptal")){
         return PageName.HOME;
        }
        else{
            System.out.println("Satış iptal");
        }

        return PageName.ORDER_VIEW;
    }
}
