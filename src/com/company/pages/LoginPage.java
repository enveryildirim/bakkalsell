package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class LoginPage extends PageBase{
    public LoginPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return false;
    }

    @Override
    public PageName render() {

        Input inUsername=new Input(null,"Kullanıcı Adı", Constant.USERNAME,true);
        String username=inUsername.render();

        Input inPassword=new Input(null,"Parola", Constant.PASSWORD,true);
        String password=inPassword.render();

        if(this.userService.login(username,password))
            return PageName.HOME;
        System.out.println("!!!! Lütfen bilgilerinizi doğru giriniz Bilgilerinizi unuttuysanız Adminle görüşün\n Devam etmek için bir tuşa basın");
        in.nextLine();
        return PageName.LOGIN;
    }
}
