package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
import com.company.models.User;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class OrderViewPage extends PageBase{
    public OrderViewPage(UserService userService, ProductService productService) {
        super(userService, productService);


    }

    @Override
    public boolean requiredAuth() {
        return true;
    }

    @Override
    public PageName render() {

        System.out.printf("--------------SİPARİŞ SAYFASI-------------\n");
        System.out.printf(orderService.getAllOrder());


        Input inCommand = new Input(null, "Satış=1\nGeri Dön=0", "[01]", true);
        String command = inCommand.render();
        if (command.equals("1")) {
            this.renderSale();
        }  else {
            if(userService.getLoginedUser().getUserType()==0)
                return PageName.HOME;
            else
                return PageName.LOGIN;

        }

        return PageName.ORDER_VIEW;
    }
    void renderSale(){
        Input inID = new Input(null,"Kullanıcının ID'sini girin", Constant.ONLY_DIGIT,true);
        String id = inID.render();
        User user = userService.getUser(inID.getInt());
        if(user==null || user.getUserType()!=2){
            System.out.println("Uygun kullanıcı bulunamadı !!!");
            return;
        }
        String msj=String.format("%s ID'li %s İsimli ---- siparişi onaylamak için evet iptal için hayır giriniz",user.getId(),user.getNameSurname());
        Input inCommand=new Input(null,msj,"(evet|hayır)",true);
        String cevap=inCommand.render();
        if(cevap.equals("evet")){
            System.out.println("Satış yapıldı");
            orderService.saleOrder();
        }
        else{
            System.out.println("Satış iptal");
        }
    }
}
