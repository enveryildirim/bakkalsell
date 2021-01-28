package com.company.pages;

import com.company.Constant;
import com.company.models.*;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class OrderPage extends PageBase {
    public OrderPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    //todo commandları genel enum yapısı oluşturulup iflerin düzenlemesi
    @Override
    public PageName render() {
        System.out.printf("--------------SİPARİŞ SAYFASI-------------\n");
        System.out.printf("------------ÜRÜN LİSTESİ----------\n");
        System.out.println(productService.getAllProductForCart());
        System.out.println(orderService.listOrder());

        String labelCommand = "Sepete Ürün Ekleme=1\\nSepete Ürün Silme=2\\nGeri Dön=0";
        boolean isRequiredCommand = true;
        Input inCommand = new Input(labelCommand, Constant.ORDER_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inCommand.render();

        if (command.equals("1")) {
            this.renderAddCommandContent();
        } else if (command.equals("2")) {
            this.renderDeleteCommandContent();
        } else {
            boolean isAdminLoginedUser = userService.getLoginedUser().getUserType() == UserType.ADMIN;
            if (isAdminLoginedUser)
                return PageName.HOME;
            else
                return PageName.LOGIN;

        }

        return PageName.ORDER;
    }

    void renderAddCommandContent() {
        String id;
        Product updatingProduct;
        while (true) {
            String labelID = "Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredID = true;
            Input inID = new Input(labelID, Constant.ONLY_DIGIT, isRequiredID);
            id = inID.render();

            if (id.equals("0"))
                return;

            updatingProduct = productService.getProductById(inID.getTextAfterConvertToInt());
            if (updatingProduct == null) {
                System.out.printf("ID göre ürün bulunamadı tekrar deneyiniz");
            } else
                break;
        }

        String quantity = "";
        while (true) {
            String labelQuantity="Ürün miktar giriniz veya çıkmak için 0'a basın";
            boolean isRequiredQuantity=true;
            Input inQuantity = new Input(labelQuantity, Constant.ONLY_DIGIT, isRequiredQuantity);
            quantity = inQuantity.render();

            int quantityInt = inQuantity.getTextAfterConvertToInt();

            if (quantity.equals("0"))
                return;

            if (updatingProduct.getQuantity() < quantityInt || quantityInt == -1) {
                System.out.printf("Yeterli stok yok tekrar deneyiniz");
            } else {
                orderService.addOrder(updatingProduct, quantityInt);
                break;
            }


        }


    }

    void renderDeleteCommandContent() {
        String cartItemid = "";
        User loginedUser = userService.getLoginedUser();
        Order order = orderService.getOrder(loginedUser.getId());
        CartItem cartItem;
        if (order == null)
            return;

        boolean isEmptyCart=order.orders.size() == 0;
        if (isEmptyCart) {
            System.out.printf("Sepet Boş Ürün Silinemez \n");
            return;
        }
        while (true) {
            String labelID="Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredLabel=true;
            Input inID = new Input(labelID, Constant.ONLY_DIGIT, isRequiredLabel);
            cartItemid = inID.render();
            int cartItemidInt = inID.getTextAfterConvertToInt();

            if (cartItemid.equals("0"))
                return;

            cartItem = order
                    .orders
                    .stream()
                    .filter(c -> c.getProduct().getId() == cartItemidInt)
                    .findFirst()
                    .orElse(null);

            if (cartItem != null) {
                orderService.deleteCartItem(loginedUser.getId(), cartItem);
                break;
            }

        }
    }

}
