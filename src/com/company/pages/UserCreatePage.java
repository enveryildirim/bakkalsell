package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
import com.company.models.User;
import com.company.models.UserType;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class UserCreatePage extends PageBase {
    public UserCreatePage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    //todo kullanıcnı adı girerken kabul etmiyor
    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.println("kullanıcı oluşturma sayfası lütfen bilgileri giriniz Boş alan bırakmayınız");
        System.out.println("Kullanıcı Adı en az 5 haneli ve Şifre 6 haneli olmalı");

        String labelName="Ad Soyad: ";
        boolean isRequiredName=true;
        Input inName = new Input(labelName, Constant.NAME_SURNAME_TR, isRequiredName);
        String nameSurname = inName.render();

        String labelUsername="Kullanıcı Adı";
        boolean isRequiredUsername=true;
        Input inUsername = new Input(labelUsername, Constant.USERNAME, isRequiredUsername);
        String username = inUsername.render();

        String labelPassword="Parola";
        boolean isRequiredPassword=true;
        Input inPassword = new Input(labelPassword, Constant.PASSWORD, isRequiredPassword);
        String password = inPassword.render();

        String labelUserType="Kullanıcının Türünü giriniz 1 == Kasiyer 2 == Müşteri";
        boolean isRequiredUserType=true;
        Input inType = new Input(labelUserType, Constant.USER_CREATE_PAGE_USER_TYPE, isRequiredUserType);
        String typeUser = inType.render();
        int typeUserIndex=inType.getTextAfterConvertToInt();

        System.out.println("Ad Soyad:" + nameSurname + " Username: " + username + " Password: " + password);
        System.out.printf("Onay için evet iptal için hayır yazın ");

        Input inConfirm = new Input("Onay için evet iptal için hayır yazın", "(evet|hayır)$/", true);
        String confirm = inConfirm.render();


        if (confirm.equals("evet")) {
            System.out.printf("Eklendi");
            User newUser= new User(nameSurname,username,password,UserType.values()[typeUserIndex]);
            userService.createUser(newUser);
        } else
            System.out.printf("İptal edildi");

        return PageName.HOME;
    }
}
