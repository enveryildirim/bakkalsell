package com.company.pages;

import com.company.RegexConstant;
import com.company.models.PageName;
import com.company.models.User;
import com.company.models.UserType;
import com.company.pages.components.Input;
import com.company.services.UserService;
import com.company.models.Result;

/**
 * Kullanıcı oluşturma ekranı
 */
public class UserCreatePage extends PageBase {
    private UserService userService;

    public UserCreatePage(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    @Override
    public PageName render() {

        System.out.println("kullanıcı oluşturma sayfası lütfen bilgileri giriniz Boş alan bırakmayınız");
        System.out.println("Kullanıcı Adı en az 5 haneli ve Şifre 6 haneli olmalı");

        String labelName = "Ad Soyad: ";
        boolean isRequiredName = true;
        Input inputName = new Input(labelName, RegexConstant.NAME_SURNAME_TR, isRequiredName);
        String nameSurname = inputName.renderAndGetText();

        String labelUsername = "Kullanıcı Adı";
        boolean isRequiredUsername = true;
        Input inputUsername = new Input(labelUsername, RegexConstant.USERNAME, isRequiredUsername);
        String username = inputUsername.renderAndGetText();

        String labelPassword = "Parola";
        boolean isRequiredPassword = true;
        Input inputPassword = new Input(labelPassword, RegexConstant.PASSWORD, isRequiredPassword);
        String password = inputPassword.renderAndGetText();

        String labelUserType = "Kullanıcının Türünü giriniz 1 == Kasiyer 2 == Müşteri";
        boolean isRequiredUserType = true;
        Input inputType = new Input(labelUserType, RegexConstant.USER_CREATE_PAGE_USER_TYPE, isRequiredUserType);
        inputType.renderAndGetText();
        int typeUserIndex = inputType.getTextAfterConvertToInt();

        System.out.println("Ad Soyad:" + nameSurname + " Username: " + username + " Password: " + password);

        Input inputConfirm = new Input("Onay için evet iptal için hayır yazın", RegexConstant.COMMAND_YES_NO, true);
        String confirm = inputConfirm.renderAndGetText();

        if (confirm.equals("evet")) {
            User newUser = new User(nameSurname, username, password, UserType.values()[typeUserIndex]);
            Result<User> resultCreateAction = userService.createUser(newUser);
            System.out.println(resultCreateAction.getMessage());
        } else
            System.out.println("İptal edildi");

        return PageName.HOME;
    }

}
