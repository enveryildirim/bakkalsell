package com.company.pages;

import com.company.Constant;
import com.company.dal.DB;
import com.company.models.*;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class OrderPage extends PageBase {
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

        Input inCommand = new Input("Sepete Ürün Ekleme=1\nSepete Ürün Silme=2\nGeri Dön=0", "[0123]", true);
        String command = inCommand.render();
        if (command.equals("1")) {
            this.renderEkleme();
        } else if (command.equals("2")) {
            this.renderSilme();
        } else {
            if (userService.getLoginedUser().getUserType() == UserType.ADMIN)
                return PageName.HOME;
            else
                return PageName.LOGIN;

        }

        return PageName.ORDER;
    }

    void renderEkleme() {
        String id, quantity = "";
        Product product;
        while (true) {
            Input inID = new Input("Ürün ID giriniz veya çıkmak için 0'a basın ", Constant.ONLY_DIGIT, true);
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
            Input inQuantity = new Input("Ürün miktar giriniz veya çıkmak için 0'a basın", Constant.ONLY_DIGIT, true);
            quantity = inQuantity.render();
            int quantityInt = inQuantity.getInt();
            if (quantity.equals("0"))
                return;

            if (product.getQuantity() < quantityInt || quantityInt == -1) {
                System.out.printf("Yeterli stok yok tekrar deneyiniz");
            } else {
                orderService.addOrder(product, quantityInt);
                break;
            }


        }


    }

    void renderSilme() {
        String id = "";
        User user = userService.getLoginedUser();
        Order order = orderService.getOrder(user.getId());
        CartItem cartItem;
        if (order == null)
            return;

        if (order.orders.size() == 0) {
            System.out.printf("Sepet Boş Ürün Silinemez \n");
            return;
        }
        while (true) {
            Input inID = new Input("Ürün ID giriniz veya çıkmak için 0'a basın ", Constant.ONLY_DIGIT, true);
            id = inID.render();

            int idInt = inID.getInt();

            if (id.equals("0"))
                return;

            cartItem = order
                    .orders
                    .stream()
                    .filter(c -> c.getProduct().getId() == idInt)
                    .findFirst()
                    .orElse(null);

            if (cartItem != null) {
                orderService.deleteCartItem(user.getId(), cartItem);
                break;
            }

        }
    }

}
