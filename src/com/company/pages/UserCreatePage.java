package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
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
    public boolean requiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.println("kullanıcı oluşturma sayfası lütfen bilgileri giriniz Boş alan bırakmayınız");
        System.out.println("Kullanıcı Adı en az 5 haneli ve Şifre 6 haneli olmalı");

        Input inName = new Input("Ad Soyad: ", "[a-zA-Z]", true);
        String nameSurname = inName.render();

        Input inUsername = new Input("Kullanıcı Adı", Constant.USERNAME, true);
        String username = inUsername.render();

        Input inPassword = new Input("Parola", Constant.PASSWORD, true);
        String password = inPassword.render();

        Input inType = new Input("Kullanıcının Türünü giriniz 1 == Kasiyer 2 == Müşteri", "[12]", true);
        String typeUser = inType.render();

        System.out.println("Ad Soyad:" + nameSurname + " Username: " + username + " Password: " + password);
        System.out.printf("Onay için evet iptal için hayır yazın ");

        Input inConfirm = new Input("Onay için evet iptal için hayır yazın", "(evet|hayır)$/", true);
        String confirm = inConfirm.render();


        if (confirm.equals("evet")) {
            System.out.printf("Eklendi");
            //todo usertype güncelenecek müşteri için kullanıcndan alıncack
            userService.createUser(nameSurname, username, password, UserType.EMPLOYEE);
        } else
            System.out.printf("İptal edildi");

        return PageName.HOME;
    }
}
