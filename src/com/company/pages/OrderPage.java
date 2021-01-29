package com.company.pages;

import com.company.Constant;
import com.company.models.*;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

/**
 * müşterinin sipariş işlemlerini yapar
 */
public class OrderPage extends PageBase {

    public OrderPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    /**
     * Ürünlerin listelendiği ve şipariş verilir
     *
     * @return PageName
     */
    @Override
    public PageName render() {
        System.out.println("--------------SİPARİŞ SAYFASI-------------");
        System.out.println("------------ÜRÜN LİSTESİ----------");
        System.out.println(orderService.getAllProductString());
        System.out.println(orderService.getUserOrderListConvertToString());

        String labelCommand = "Sepete Ürün Ekleme=1\nSepete Ürün Silme=2\nSepette Ürün Güncelleme=3\nGeri Dön=0";
        boolean isRequiredCommand = true;
        Input inCommand = new Input(labelCommand, Constant.ORDER_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inCommand.renderAndGetText();

        if (command.equals("1")) {
            this.renderAddCommandContent();
        } else if (command.equals("2")) {
            this.renderDeleteCommandContent();
        } else if (command.equals("3")) {
            this.renderUpdateCommandContent();
        } else {
            boolean isAdminLoginedUser = userService.getLoginedUser().getUserType() == UserType.ADMIN;
            if (isAdminLoginedUser)
                return PageName.HOME;
            else
                return PageName.LOGIN;

        }

        return PageName.ORDER;
    }

    /**
     * Siparişi ürün ekleme ile alakalı kısın,mlarıını ekrana basılır.
     */
    void renderAddCommandContent() {
        String productId;
        Product addingProduct;
        while (true) {
            String labelID = "Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredID = true;
            Input inID = new Input(labelID, Constant.ONLY_DIGIT, isRequiredID);
            productId = inID.renderAndGetText();

            if (productId.equals("0"))
                return;
            addingProduct = productService.getProductById(inID.getTextAfterConvertToInt());
            if (addingProduct == null) {
                System.out.println("ID göre ürün bulunamadı tekrar deneyiniz");
            } else
                break;
        }

        String quantity;
        while (true) {
            String labelQuantity = "Ürün miktar giriniz veya çıkmak için 0'a basın";
            boolean isRequiredQuantity = true;
            Input inQuantity = new Input(labelQuantity, Constant.ONLY_DIGIT, isRequiredQuantity);
            quantity = inQuantity.renderAndGetText();

            int quantityInt = inQuantity.getTextAfterConvertToInt();

            if (quantity.equals("0"))
                return;

            if (addingProduct.getQuantity() < quantityInt || quantityInt == -1) {
                System.out.println("Yeterli stok yok tekrar deneyiniz");
            } else {
                orderService.addProductToOrder(addingProduct, quantityInt);
                break;
            }

        }

    }

    /**
     * Siparişten ürün silme ile alakalı işlemlerin ekrana yansıltıldığı fonksiyon
     */
    void renderDeleteCommandContent() {
        String cartItemid;
        User loginedUser = userService.getLoginedUser();
        Order order = orderService.getOrder(loginedUser.getId());

        if (order == null)
            return;

        boolean isEmptyCart = order.orders.size() == 0;
        if (isEmptyCart) {
            System.out.println("Sepet Boş Ürün Silinemez");
            return;
        }

        CartItem cartItem;
        while (true) {
            String labelID = "Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredLabel = true;
            Input inID = new Input(labelID, Constant.ONLY_DIGIT, isRequiredLabel);
            cartItemid = inID.renderAndGetText();
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
                orderService.deleteProductFromOrder(loginedUser.getId(), cartItem);
                break;
            }else{
                System.out.println("Uygun ID giriniz");
            }

        }
    }

    void renderUpdateCommandContent() {
        String productId;
        Product updatingProduct;
        while (true) {
            String labelID = "Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredID = true;
            Input inID = new Input(labelID, Constant.ONLY_DIGIT, isRequiredID);
            productId = inID.renderAndGetText();

            if (productId.equals("0"))
                return;

            updatingProduct = productService.getProductById(inID.getTextAfterConvertToInt());
            if (updatingProduct == null) {
                System.out.println("ID göre ürün bulunamadı tekrar deneyiniz");
            } else
                break;
        }

        String quantity;
        while (true) {
            String labelQuantity = "Yeni Ürün miktar giriniz veya çıkmak için 0'a basın";
            boolean isRequiredQuantity = true;
            Input inQuantity = new Input(labelQuantity, Constant.ONLY_DIGIT, isRequiredQuantity);
            quantity = inQuantity.renderAndGetText();

            int quantityInt = inQuantity.getTextAfterConvertToInt();

            if (quantity.equals("0"))
                return;

            if (updatingProduct.getQuantity() < quantityInt || quantityInt == -1) {
                System.out.println("Yeterli stok yok tekrar deneyiniz");
            } else {
                orderService.updateCartItemInOrder(updatingProduct.getId(), quantityInt);
                break;
            }

        }
    }
}
