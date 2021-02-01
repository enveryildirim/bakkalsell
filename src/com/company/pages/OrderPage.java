package com.company.pages;

import com.company.RegexConstant;
import com.company.models.*;
import com.company.pages.components.Input;
import com.company.services.OrderService;
import com.company.services.ProductService;
import com.company.services.UserService;

/**
 * müşterinin sipariş işlemlerini yapar
 */
public class OrderPage extends PageBase {
    private UserService userService;
    private ProductService productService;
    private OrderService orderService;
    public OrderPage(UserService userService, ProductService productService,OrderService orderService) {
        this.userService=userService;
        this.productService=productService;
        this.orderService=orderService;
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
        Input inputCommand = new Input(labelCommand, RegexConstant.ORDER_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inputCommand.renderAndGetText();

        if (command.equals("1")) {
            this.renderContentOfAddCommand();
        } else if (command.equals("2")) {
            this.renderContentOfDeleteCommand();
        } else if (command.equals("3")) {
            this.renderContentOfUpdateCommand();
        } else {
            boolean isAdminLoggedUser = userService.getLoggedUser().getUserType() == UserType.ADMIN;
            if (isAdminLoggedUser)
                return PageName.HOME;
            else
                return PageName.LOGIN;

        }

        return PageName.ORDER;
    }

    /**
     * Siparişi ürün ekleme ile alakalı kısın,mlarıını ekrana basılır.
     */
    void renderContentOfAddCommand() {
        String productId;
        Product addingProduct;
        while (true) {
            String labelID = "Ürün ID giriniz veya çıkmak için 0'a basın\n ";
            boolean isRequiredID = true;
            Input inputID = new Input(labelID, RegexConstant.ONLY_DIGIT, isRequiredID);
            productId = inputID.renderAndGetText();

            if (productId.equals("0"))
                return;
            addingProduct = productService.getProductById(inputID.getTextAfterConvertToInt());
            if (addingProduct == null) {
                System.out.println("ID göre ürün bulunamadı tekrar deneyiniz");
            } else
                break;
        }

        String quantity;
        while (true) {
            String labelQuantity = "Ürün miktar giriniz veya çıkmak için 0'a basın";
            boolean isRequiredQuantity = true;
            Input inputQuantity = new Input(labelQuantity, RegexConstant.ONLY_DIGIT, isRequiredQuantity);
            quantity = inputQuantity.renderAndGetText();

            int quantityInt = inputQuantity.getTextAfterConvertToInt();

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
    void renderContentOfDeleteCommand() {
        String cartItemid;
        User loggedUser = userService.getLoggedUser();
        Order order = orderService.getOrder(loggedUser.getId());

        boolean isEmptyCart = order==null || order.orders.size() == 0;
        if (isEmptyCart) {
            System.out.println("Sepet Boş Ürün Silinemez");
            return;
        }

        CartItem cartItem;
        while (true) {
            String labelID = "Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredLabel = true;
            Input inputID = new Input(labelID, RegexConstant.ONLY_DIGIT, isRequiredLabel);
            cartItemid = inputID.renderAndGetText();
            int cartItemidInt = inputID.getTextAfterConvertToInt();

            if (cartItemid.equals("0"))
                return;

            cartItem = order
                    .orders
                    .stream()
                    .filter(c -> c.getProduct().getId() == cartItemidInt)
                    .findFirst()
                    .orElse(null);

            if (cartItem != null) {
                orderService.deleteProductFromOrder(loggedUser.getId(), cartItem);
                break;
            }else{
                System.out.println("Uygun ID giriniz");
            }

        }
    }

    void renderContentOfUpdateCommand() {
        String productId;
        Product updatingProduct;

        User loggedUser = userService.getLoggedUser();
        Order order = orderService.getOrder(loggedUser.getId());
        boolean isEmptyCart = order==null || order.orders.size() == 0;
        if (isEmptyCart) {
            System.out.println("Sepet Boş Ürün Silinemez");
            return;
        }

        while (true) {
            String labelID = "Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredID = true;
            Input inputID = new Input(labelID, RegexConstant.ONLY_DIGIT, isRequiredID);
            productId = inputID.renderAndGetText();

            if (productId.equals("0"))
                return;

            updatingProduct = productService.getProductById(inputID.getTextAfterConvertToInt());
            if (updatingProduct == null) {
                System.out.println("ID göre ürün bulunamadı tekrar deneyiniz");
            } else
                break;
        }

        String quantity;
        while (true) {
            String labelQuantity = "Yeni Ürün miktar giriniz veya çıkmak için 0'a basın";
            boolean isRequiredQuantity = true;
            Input inputQuantity = new Input(labelQuantity, RegexConstant.ONLY_DIGIT, isRequiredQuantity);
            quantity = inputQuantity.renderAndGetText();

            int quantityInt = inputQuantity.getTextAfterConvertToInt();

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
