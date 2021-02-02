package com.company.pages;

import com.company.RegexConstant;
import com.company.models.PageName;
import com.company.models.User;
import com.company.pages.components.Input;
import com.company.services.UserService;
import com.company.models.Result;

/**
 * Kullanıcı işlemleri yapar
 */
public class UserListPage extends PageBase {
    private UserService userService;

    public UserListPage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    @Override
    public PageName render() {

        System.out.println("Kullanıcı Listeleme\n");
        userService.getAll().forEach(u ->
                System.out.printf("ID:%d -- Ad Soyad: %s -- Username:%s -- Password:%s -- Tipi:%s\n",
                        u.getId(), u.getNameSurname(), u.getUsername(), u.getPassword(), u.getUserType().toString()));


        String msj = "1-Kullanıcı Düzenleme\n2-KUllanıcı Silme\n0-Anasayfa";
        boolean isRequiredCommand = true;
        Input inCommand = new Input(msj, RegexConstant.USER_LIST_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inCommand.renderAndGetText();

        if (command.equals("1")) {
            renderUpdateCommandContent();
        } else if (command.equals("2")) {
            renderDeleteCommandContent();
        } else if (command.equals("0")) {
            return PageName.HOME;
        } else {
            System.out.println("Yanlış giriş yapmdınız");
        }

        return PageName.USER_LIST;
    }

    /**
     * Kullanıcı güncelleme işlemlerini ekrana basar
     */
    void renderUpdateCommandContent() {
        String labelID = "Kullanıcı İd giriniz";
        boolean isRequiredID = true;
        Input inID = new Input(labelID, RegexConstant.ONLY_DIGIT, isRequiredID);
        String id = inID.renderAndGetText();
        int idInt = inID.getTextAfterConvertToInt();

        User updatingUser = userService.getUser(idInt);
        if (updatingUser == null) {
            System.out.println("İd uygun Ürün Yok\n");
            return;
        }

        String msjName = String.format("Şimdiki İsim: %s\n", updatingUser.getNameSurname());
        boolean isRequiredName = true;
        Input inName = new Input(msjName, RegexConstant.NAME_SURNAME_TR, isRequiredName);
        String newName = inName.renderAndGetText();

        String msjUsername = String.format("Şimdiki Kullanıcı Adı: %s\n", updatingUser.getUsername());
        boolean isRequiredUsername = true;
        Input inUsername = new Input(msjUsername, RegexConstant.USERNAME, isRequiredUsername);
        String newUsername = inUsername.renderAndGetText();

        String msjPassword = String.format("Şimdiki Şifre:%s\n", updatingUser.getPassword());
        boolean isRequiredPassword = true;
        Input inPassword = new Input(msjPassword, RegexConstant.PASSWORD, isRequiredPassword);
        String newPassword = inPassword.renderAndGetText();

        String labelConfirm = "Onaylamak için evet iptal için hayır yazın";
        boolean isRequiredConfirm = true;
        Input inConfirm = new Input(labelConfirm, RegexConstant.COMMAND_YES_NO, isRequiredConfirm);
        String confirm = inConfirm.renderAndGetText();

        if (confirm.equals("evet")) {

            updatingUser.setNameSurname(newName);
            updatingUser.setUsername(newUsername);
            updatingUser.setPassword(newPassword);
            userService.updateUser(updatingUser);
            Result<User> result = userService.updateUserResult(updatingUser);
            System.out.println(result.getMessage());
        } else {
            System.out.println("İptal Edildi");
        }

    }

    /**
     * Kullanıcı silme işlemlerini ekrana basar
     */
    void renderDeleteCommandContent() {
        String labelID = "Kullanıcı İd giriniz";
        boolean isRequiredID = true;
        Input inputID = new Input(labelID, RegexConstant.ONLY_DIGIT, isRequiredID);
        inputID.renderAndGetText();
        int idInt = inputID.getTextAfterConvertToInt();

        User user = userService.getUser(idInt);
        if (user == null) {
            System.out.println("İd uygun Ürün Yok\n");
            return;
        }
        System.out.printf("%d %s silinecek\n", user.getId(), user.getNameSurname());

        String labelConfirm = "Onaylamak için evet iptal için hayır yazın";
        boolean isRequiredCommand = true;
        Input inputConfirm = new Input(labelConfirm, RegexConstant.COMMAND_YES_NO, isRequiredCommand);
        String confirm = inputConfirm.renderAndGetText();

        if (confirm.equals("evet")) {
            Result<User> result = userService.deleteUserResult(user);
            System.out.println(result);
        } else {
            System.out.println("İptal Edildi");
        }
    }
}
