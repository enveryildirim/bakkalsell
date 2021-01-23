package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
import com.company.models.User;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class UserListPage extends PageBase{
    public UserListPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return true;
    }
    String typeToString(int durum){
        if(durum==0)
            return "Admin";
        else if(durum==1)
            return "Kasiyer";
        else if(durum==2)
            return "Müşteri";
        else
            return "Bilinmiyor";
    }
    @Override
    public PageName render() {

        System.out.printf("Kullanıcı Listeleme\n");
        userService.getAll().forEach(u->
                System.out.printf("ID:%d -- Ad Soyad: %s -- Username:%s -- Password:%s -- Tipi:%s\n",u.getId(),u.getNameSurname(),u.getUsername(),u.getPassword(),this.typeToString(u.getUserType())));


        String msj="1-Kullanıcı Düzenleme\n2-KUllanıcı Silme\n0-Anasayfa";

        Input inCommand= new Input(null,msj,"[012]",true);
        String command=inCommand.render();
        if(command.equals("1")){
            renderUpdate();
        }else if(command.equals("2")){
            renderDelete();
        }else if(command.equals("0")){
            return PageName.HOME;
        }else{
            System.out.printf("Yanlış giriş yapmdınız");
            return PageName.USER_LIST;
        }




        return PageName.USER_LIST;
    }

    void renderUpdate(){

        Input inID =new Input(null,"Kullanıcı İd giriniz", Constant.ONLY_DIGIT,true);
        String id = inID.render();
        int idInt=inID.getInt();
        User user=userService.getUser(idInt);
        if(user==null){
            System.out.printf("İd uygun Ürün Yok\n");
            return;
        }
        String msjName=String.format("Şimdiki İsim: %s\n",user.getNameSurname());
        Input inName=new Input(null,msjName,"[a-zA-Z]",true);
        String newName=inName.render();

        String msjUsername=String.format("Şimdiki Kullanıcı Adı: %s\n",user.getUsername());
        Input inUsername=new Input(null,msjName,Constant.USERNAME,true);
        String newUsername=inUsername.render();

        String msjPassword=String.format("Şimdiki Şifre:%f\n",user.getPassword());
        Input inPassword=new Input(null,msjName,Constant.PASSWORD,true);
        String newPassword=inPassword.render();

        Input inConfirm=new Input(null,"Onaylamak için evet iptal için hayır yazın","(evet|hayır)",true);
        String confirm=inConfirm.render();
        if(confirm.equals("evet")){
            //güncelleme
            System.out.printf("Güncelendi");

            user.setNameSurname(newName);
            user.setUsername(newUsername);
            user.setPassword(newPassword);

            userService.updateUser(user);
        }else{
            System.out.printf("İptal Edildi");
            return;
        }

    }
    void renderDelete(){

        Input inID =new Input(null,"Kullanıcı İd giriniz",Constant.ONLY_DIGIT,true);
        String id = inID.render();
        int idInt=inID.getInt();
        User user=userService.getUser(idInt);
        if(user==null){
            System.out.printf("İd uygun Ürün Yok\n");
            return;
        }
        System.out.printf("%d %s silinecek\n",user.getId(),user.getNameSurname());
        Input inConfirm=new Input(null,"Onaylamak için evet iptal için hayır yazın","(evet|hayır)",true);
        String confirm=inConfirm.render();
        if(confirm.equals("evet")){
            //silme işlemi
            System.out.printf("Silindi");
            userService.deleteUser(user);
        }else{
            System.out.printf("İptal Edildi");
            return;
        }
    }
}
