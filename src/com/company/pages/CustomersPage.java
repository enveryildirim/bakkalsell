package com.company.pages;

import com.company.models.PageName;
import com.company.models.User;
import com.company.services.ProductService;
import com.company.services.UserService;

public class CustomersPage extends PageBase{
    public CustomersPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.printf("kullanıcı Listeleme\n");
        userService.getAll().forEach(u->
                System.out.printf("ID:%d -- Ad Soyad: %s -- Username:%s -- Password:%s\n",u.getId(),u.getNameSurname(),u.getUsername(),u.getPassword()));
        System.out.printf("1-Kullanıcı Düzenleme\n");
        System.out.printf("2-KUllanıcı Silme\n");
        String command=in.nextLine();
        if(command.equals("1")){
            System.out.printf("Kullanıcı İd giriniz \n");
            int id = in.nextInt();

            User user=userService.getUser(id);
            if(user==null){
                System.out.printf("Id uygun Kullanıcı Yok\n");
                return PageName.USER_LIST;
            }

            while(true){
                System.out.printf("\nŞimdiki İsim:%s",user.getNameSurname());
                String newName2=in.nextLine();
                String newName=in.nextLine();
                System.out.printf("\nŞimdiki Kullanıcı Adı:%s",user.getUsername());
                String newUsername=in.nextLine();
                System.out.printf("\nŞimdiki Şifre:%s",user.getPassword());
                String newPassword=in.nextLine();
                if(newName.length()==0||newUsername.length()==0||newPassword.length()==00||newUsername.length()<5||newPassword.length()<6){
                    System.out.printf("\nBilgileri kurallara uygun giriniz lütfen, devam etmek için bir tuşa basınız\n");
                    in.nextLine();
                    in.nextLine();
                    break;
                }
                System.out.printf("\n Onaylamak için evet iptal için hayır yazın\n");
                String dneme=in.nextLine();
                String onay=in.nextLine();
                if(onay.equals("evet")){
                    System.out.printf("Güncelendi");

                    user.setNameSurname(newName);
                    user.setUsername(newUsername);
                    user.setPassword(newPassword);

                    userService.updateUser(user);

                    return PageName.CUSTOMER_LIST;
                }else
                    return PageName.CUSTOMER_LIST;


            }
        }
        if(command.equals("2")){
            System.out.printf("Kullanıcı İd giriniz \n");
            int id = in.nextInt();

            User user=userService.getUser(id);
            if(user==null ||user.getUserType()==1){
                System.out.printf("İd uygun Kullanıcı Yok\n");
                return PageName.CUSTOMER_LIST;
            }

            System.out.printf("\n Onaylamak için evet iptal için hayır yazın\n");
            String onay=in.nextLine();
            String dneme=in.nextLine();
            if(dneme.equals("evet")){
                System.out.printf("Silindi");

                userService.deleteUser(user);
                return PageName.CUSTOMER_LIST;
            }else
                return PageName.CUSTOMER_LIST;

        }if(command.equals("0"))
            return PageName.HOME;


        return PageName.CUSTOMER_LIST;
    }
}
