package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
import com.company.models.User;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class UserListPage extends PageBase {

    public UserListPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    @Override
    public PageName render() {

        System.out.printf("Kullanıcı Listeleme\n");
        userService.getAll().forEach(u ->
                System.out.printf("ID:%d -- Ad Soyad: %s -- Username:%s -- Password:%s -- Tipi:%s\n",
                        u.getId(), u.getNameSurname(), u.getUsername(), u.getPassword(), u.getUserType().toString()));


        String msj = "1-Kullanıcı Düzenleme\n2-KUllanıcı Silme\n0-Anasayfa";
        boolean isRequiredCommand = true;
        Input inCommand = new Input(msj, Constant.USER_LIST_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inCommand.renderAndGetText();

        if (command.equals("1")) {
            renderUpdate();
        } else if (command.equals("2")) {
            renderDelete();
        } else if (command.equals("0")) {
            return PageName.HOME;
        } else {
            System.out.printf("Yanlış giriş yapmdınız");
        }

        return PageName.USER_LIST;
    }

    void renderUpdate() {
        String labelID = "Kullanıcı İd giriniz";
        boolean isRequiredID = true;
        Input inID = new Input(labelID, Constant.ONLY_DIGIT, isRequiredID);
        String id = inID.renderAndGetText();
        int idInt = inID.getTextAfterConvertToInt();

        User updatingUser = userService.getUser(idInt);
        if (updatingUser == null) {
            System.out.printf("İd uygun Ürün Yok\n");
            return;
        }

        String msjName = String.format("Şimdiki İsim: %s\n", updatingUser.getNameSurname());
        boolean isRequiredName = true;
        Input inName = new Input(msjName, Constant.NAME_SURNAME_TR, isRequiredName);
        String newName = inName.renderAndGetText();

        String msjUsername = String.format("Şimdiki Kullanıcı Adı: %s\n", updatingUser.getUsername());
        boolean isRequiredUsername = true;
        Input inUsername = new Input(msjUsername, Constant.USERNAME, isRequiredUsername);
        String newUsername = inUsername.renderAndGetText();

        String msjPassword = String.format("Şimdiki Şifre:%f\n", updatingUser.getPassword());
        boolean isRequiredPassword = true;
        Input inPassword = new Input(msjPassword, Constant.PASSWORD, isRequiredPassword);
        String newPassword = inPassword.renderAndGetText();

        String labelConfirm = "Onaylamak için evet iptal için hayır yazın";
        boolean isRequiredConfirm = true;
        Input inConfirm = new Input(labelConfirm, Constant.COMMAND_YES_NO, isRequiredConfirm);
        String confirm = inConfirm.renderAndGetText();

        if (confirm.equals("evet")) {

            updatingUser.setNameSurname(newName);
            updatingUser.setUsername(newUsername);
            updatingUser.setPassword(newPassword);
            userService.updateUser(updatingUser);

            System.out.printf("Güncelendi");
        } else {
            System.out.printf("İptal Edildi");
            return;
        }

    }

    void renderDelete() {
        String labelID = "Kullanıcı İd giriniz";
        boolean isRequiredID = true;
        Input inID = new Input(labelID, Constant.ONLY_DIGIT, isRequiredID);
        String id = inID.renderAndGetText();
        int idInt = inID.getTextAfterConvertToInt();

        User user = userService.getUser(idInt);
        if (user == null) {
            System.out.printf("İd uygun Ürün Yok\n");
            return;
        }
        System.out.printf("%d %s silinecek\n", user.getId(), user.getNameSurname());

        String labelConfirm = "Onaylamak için evet iptal için hayır yazın";
        boolean isRequiredCommand = true;
        Input inConfirm = new Input(labelConfirm, Constant.COMMAND_YES_NO, isRequiredCommand);
        String confirm = inConfirm.renderAndGetText();

        if (confirm.equals("evet")) {
            userService.deleteUser(user);
            System.out.printf("Silindi");
        } else {
            System.out.printf("İptal Edildi");
            return;
        }
    }
}
