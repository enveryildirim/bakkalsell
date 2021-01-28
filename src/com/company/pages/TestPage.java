package com.company.pages;


import com.company.Constant;
import com.company.models.*;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class TestPage extends PageBase {
    public TestPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean isRequiredAuth() {
        return false;
    }

    @Override
    public PageName render() {
        //System.out.println(UserType.values()[0]);
        String labelName="Ad Soyad: ";
        boolean isRequiredName=true;
        Input inName = new Input(labelName, Constant.NAME_SURNAME_TR, isRequiredName);
        String nameSurname = inName.renderAndGetText();
        System.out.printf("%s\n",nameSurname);
        return PageName.TEST;
    }

}