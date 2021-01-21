package com.company.pages;

import com.company.models.PageName;
import com.company.models.User;
import com.company.services.ProductService;
import com.company.services.UserService;

public class UserCreatePage extends PageBase{
    public UserCreatePage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.println("kullanıcı oluşturma sayfası lütfen bilgileri giriniz Boş alan bırakmayınız");
        System.out.println("Kullanıcı Adı en az 5 haneli ve Şifre 6 haneli olmalı");

        System.out.println("Ad Soyad girin");
        String nameSurname=in.nextLine();

        System.out.println("Kullanıcı Adı girin");
        String username=in.nextLine();

        System.out.println("Şifre girin");
        String password = in.nextLine();

        System.out.println("Kullanıcının Türünü giriniz 1 == Kasiyer 2 == Müşteri");
        String typeUser = in.nextLine();

        if(nameSurname.length()==0 || username.length()==0||password.length()==0||username.length()<5||password.length()<6||!typeUser.equals("1")||!typeUser.equals("2"))
            return PageName.USER_CREATE;

        System.out.println("Ad Soyad:"+nameSurname+" Username: "+username+" Password: "+password);
        System.out.printf("Onay için evet iptal için hayır yazın ");
        if(in.nextLine().equals("evet")) {
            System.out.printf("Eklendi");
            userService.createUser(nameSurname,username,password,typeUser);
        }
        else
            System.out.printf("İptal edildi");

        return PageName.HOME;
    }
}
