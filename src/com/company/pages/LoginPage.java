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

    //todo fiil olarak isimlendirilecek
    @Override
    public boolean requiredAuth() {
        return false;
    }

    @Override
    public PageName render() {
        //todo Input classa Field constructor atama clean code standartlarına uygun şekilde revize edilecek
        Input inUsername = new Input("Kullanıcı Adı", Constant.USERNAME, true);
        String username = inUsername.render();

        Input inPassword = new Input("Parola", Constant.PASSWORD, true);
        String password = inPassword.render();

        if (this.userService.login(username, password))
            return PageName.HOME;

        System.out.println("!!!! Lütfen bilgilerinizi doğru giriniz Bilgilerinizi unuttuysanız Adminle görüşün\n Devam etmek için bir tuşa basın");
        in.nextLine();

        return PageName.LOGIN;
    }
}
