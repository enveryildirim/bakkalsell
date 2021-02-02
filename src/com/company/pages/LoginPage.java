package com.company.pages;

import com.company.RegexConstant;
import com.company.models.PageName;
import com.company.pages.components.Input;
import com.company.services.UserService;

/**
 * Kullanıcı login işlemlerini ekrana basar
 */
public class LoginPage extends PageBase {
    private UserService userService;

    public LoginPage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isRequiredAuth() {
        return false;
    }

    @Override
    public PageName render() {

        String labelUsernameInput = "Kullanıcı Adı";
        boolean isRequiredUsername = true;
        Input inputUsername = new Input(labelUsernameInput, RegexConstant.USERNAME, isRequiredUsername);
        String username = inputUsername.renderAndGetText();

        String labelPassword = "Parola";
        boolean isRequiredPassword = true;
        Input inputPassword = new Input(labelPassword, RegexConstant.PASSWORD, isRequiredPassword);
        String password = inputPassword.renderAndGetText();

        boolean isSuccessLogin = this.userService.login(username, password);
        if (isSuccessLogin)
            return PageName.HOME;

        System.out.println("!!!! Lütfen bilgilerinizi doğru giriniz Bilgilerinizi unuttuysanız Adminle görüşün\n " +
                "Devam etmek için bir tuşa basın");
        inputData.nextLine();

        return PageName.LOGIN;
    }
}
