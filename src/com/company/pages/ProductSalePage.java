package com.company.pages;

import com.company.Constant;
import com.company.models.CartItem;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.models.UserType;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class ProductSalePage extends PageBase {
    public ProductSalePage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean isRequiredAuth() {
        return false;
    }

    @Override
    public PageName render() {

        System.out.printf("------------ÜRÜN LİSTESİ----------\n");
        System.out.printf("----------------------------------\n");
        System.out.println(productService.getAllProductForCart());
        System.out.println(productService.getCartToString());
        String labelCommand = "Sepete Ürün Ekleme=1\nSepete Ürün Silme=2\nSepeti Satış=3\nGeri Dön=0";
        boolean isRequiredCommand = true;
        Input inCommand = new Input(labelCommand, Constant.PRODUCT_SALE_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inCommand.render();

        if (command.equals("1")) {
            this.renderEkleme();
        } else if (command.equals("2")) {
            this.renderSilme();
        } else if (command.equals("3")) {
            this.renderSale();
        } else {
            boolean isAdminLoginedUser = userService.getLoginedUser().getUserType() == UserType.ADMIN;
            if (isAdminLoginedUser)
                return PageName.HOME;
            else
                return PageName.LOGIN;

        }


        return PageName.PRODUCT_SALE;
    }

    void renderEkleme() {
        String productId, productQuantity = "";
        Product product;
        while (true) {
            String labelID = "Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredID = true;
            Input inID = new Input(labelID, Constant.ONLY_DIGIT, isRequiredID);
            productId = inID.render();

            if (productId.equals("0"))
                return;
            product = productService.getProductById(inID.getTextAfterConvertToInt());
            if (product == null) {
                System.out.printf("ID göre ürün bulunamadı tekrar deneyiniz");
            } else
                break;
        }
        while (true) {
            String labelQuantity = "Ürün miktar giriniz veya çıkmak için 0'a basın";
            boolean isRequiredQuantity = true;
            Input inQuantity = new Input(labelQuantity, Constant.ONLY_DIGIT, isRequiredQuantity);
            productQuantity = inQuantity.render();
            int quantityInt = inQuantity.getTextAfterConvertToInt();

            if (productQuantity.equals("0"))
                return;

            if (product.getQuantity() < quantityInt || quantityInt == -1) {
                System.out.printf("Yeterli stok yok tekrar deneyiniz");
            } else {
                productService.insertProductToCart(product, quantityInt);
                break;
            }


        }


    }

    void renderSilme() {
        String productId = "";
        CartItem cartItem;
        if (productService.isEmptyCart()) {
            System.out.printf("Sepet Boş Ürün Silinemez \n");
            return;
        }
        while (true) {
            String labelID="Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredID=true;
            Input inID = new Input(labelID, Constant.ONLY_DIGIT, isRequiredID);
            productId = inID.render();

            int idInt = inID.getTextAfterConvertToInt();

            if (productId.equals("0"))
                return;

            cartItem = productService.getCart()
                    .stream()
                    .filter(c -> c.getProduct().getId() == idInt)
                    .findFirst()
                    .get();

            if (cartItem != null) {
                productService.deleteProductFromCart(cartItem);
                break;
            }

        }
    }

    void renderSale() {

        if (productService.isEmptyCart()) {
            System.out.printf("Sepette ürün yok satmak için ürün ekleyiniz!!!\n");
            return;
        }

        System.out.println(productService.getCartToString());
        float sumPrice = 0;
        for (CartItem item : productService.getCart()) {
            sumPrice += item.getQuantity() * item.getProduct().getPrice();
        }
        System.out.println("Toplam Fiyat:" + sumPrice);

        String labelConfirm="Satışı onaylıyor musunuz evet yoksa hayır";
        boolean isRequiredConfirm=true;
        Input inConfirm = new Input(labelConfirm, Constant.COMMAND_YES_NO, isRequiredConfirm);
        String confirm = inConfirm.render();

        if (confirm.equals("evet")) {
            productService.saleCart();
            System.out.println("Ürünler satıldı ");
        } else {
            productService.clearCart();
            System.out.println("Satış iptal edildi sepet boşaltıldı");

        }
    }
}