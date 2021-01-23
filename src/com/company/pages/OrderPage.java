package com.company.pages;

import com.company.Constant;
import com.company.dal.DB;
import com.company.models.*;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class OrderPage extends PageBase{
    public OrderPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.printf("--------------SİPARİŞ SAYFASI-------------\n");
        System.out.printf("------------ÜRÜN LİSTESİ----------\n");
        System.out.println(productService.getAllProductForCart());
        System.out.println(orderService.listOrder());

        Input inCommand = new Input(null, "Sepete Ürün Ekleme=1\nSepete Ürün Silme=2\nSepeti Satış=3\nGeri Dön=0", "[0123]", true);
        String command = inCommand.render();
        if (command.equals("1")) {
            this.renderEkleme();
        } else if (command.equals("2")) {
            this.renderSilme();
        } else if (command.equals("3")) {
            this.renderSale();
        } else {
            if(userService.getLoginedUser().getUserType()==0)
                return PageName.HOME;
            else
                return PageName.TEST;

        }

        return PageName.TEST;
    }
    void renderEkleme() {
        String id, quantity = "";
        Product product;
        while (true) {
            Input inID = new Input(null, "Ürün ID giriniz veya çıkmak için 0'a basın ", Constant.ONLY_DIGIT, true);
            id = inID.render();
            if (id.equals("0"))
                return;
            product = productService.getProductById(inID.getInt());
            if (product == null) {
                System.out.printf("ID göre ürün bulunamadı tekrar deneyiniz");
            } else
                break;
        }
        while (true) {
            Input inQuantity = new Input(null, "Ürün miktar giriniz veya çıkmak için 0'a basın", Constant.ONLY_DIGIT, true);
            quantity = inQuantity.render();
            int quantityInt = inQuantity.getInt();
            if (quantity.equals("0"))
                return;

            if (product.getQuantity() < quantityInt) {
                System.out.printf("Yeterli stok yok tekrar deneyiniz");
            } else{
                orderService.addOrder(product,quantityInt);
                break;
            }


        }


    }
    void renderSilme(){
        String id = "";
        User user=userService.getLoginedUser();
        Order order=orderService.getOrder(user.getId());
        CartItem  cartItem;
        if (order==null)
            return;

        if(order.orders.size()==0){
            System.out.printf("Sepet Boş Ürün Silinemez \n");
            return;
        }
        while (true) {
            Input inID = new Input(null, "Ürün ID giriniz veya çıkmak için 0'a basın ", Constant.ONLY_DIGIT, true);
            id = inID.render();

            int idInt=inID.getInt();

            if (id.equals("0"))
                return;

            cartItem=order
                    .orders
                    .stream()
                    .filter(c->c.product.getId()==idInt)
                    .findFirst()
                    .orElse(null);

            if(cartItem!=null){
                orderService.deleteCartItem(user.getId(),cartItem);
                break;
            }

        }
    }
    void renderSale(){
        User user=userService.getLoginedUser();
        if(orderService.getOrder(user.getId()).orders.size()==0){
            System.out.printf("Sepette ürün yok satmak için ürün ekleyiniz!!!\n");
            return;
        }

        System.out.println(orderService.listOrder());
        float toplamFiyat =0;
        for (CartItem item: orderService.getOrder(user.getId()).orders) {
            toplamFiyat+=item.quantity*item.product.getPrice();
        }
        System.out.println("Toplam Fiyat:"+toplamFiyat);

        Input inConfirm=new Input(null,"Siparişi onaylıyor musunuz evet yoksa hayır","(evet|hayır)",true);
        String confirm=inConfirm.render();
        if(confirm.equals("evet")){
            System.out.println("Ürünler sepete eklendi");
        }else{
            orderService.deleteOrder(user.getId());
            System.out.println(" İşlem iptal edildi sepet boşaltıldı");

        }
    }
}
