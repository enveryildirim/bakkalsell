package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

//todo çizgiyi araştıralacak
public class LoginPage extends PageBase {
    public LoginPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }


    @Override
    public boolean isRequiredAuth() {
        return false;
    }

    @Override
    public PageName render() {
        //todo Input classa Field constructor atama clean code standartlarına uygun şekilde revize edilecek
        String labelUsernameInput = "Kullanıcı Adı";
        boolean isRequiredUsername = true;
        Input inUsername = new Input(labelUsernameInput, Constant.USERNAME, isRequiredUsername);
        String username = inUsername.render();

        String labelPassword = "Parola";
        boolean isRequiredPassword = true;
        Input inPassword = new Input(labelPassword, Constant.PASSWORD, isRequiredPassword);
        String password = inPassword.render();


        boolean isSuccuesLogin = this.userService.login(username, password);
        if (isSuccuesLogin)
            return PageName.HOME;

        System.out.println("!!!! Lütfen bilgilerinizi doğru giriniz Bilgilerinizi unuttuysanız Adminle görüşün\n Devam etmek için bir tuşa basın");
        in.nextLine();

        return PageName.LOGIN;
    }
}
