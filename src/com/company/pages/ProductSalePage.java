package com.company.pages;

import com.company.RegexConstant;
import com.company.models.CartItem;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.models.UserType;
import com.company.pages.components.Input;
import com.company.services.CartService;
import com.company.services.ProductService;
import com.company.services.UserService;

public class ProductSalePage extends PageBase {
    private UserService userService;
    private ProductService productService;
    private CartService cartService;
    public ProductSalePage(UserService userService,ProductService productService,CartService cartService) {
        this.userService=userService;
        this.productService=productService;
        this.cartService=cartService;
    }

    @Override
    public boolean isRequiredAuth() {
        return false;
    }

    @Override
    public PageName render() {

        System.out.println("------------ÜRÜN LİSTESİ----------");
        System.out.println("----------------------------------");
        System.out.println(cartService.getAllProductConvertToString());
        System.out.println(cartService.getCartListConvertToString());
        String labelCommand = "Sepete Ürün Ekleme=1\nSepete Ürün Silme=2\nSepeti Satış=3\nGeri Dön=0";
        boolean isRequiredCommand = true;
        Input inputCommand = new Input(labelCommand, RegexConstant.PRODUCT_SALE_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inputCommand.renderAndGetText();

        if (command.equals("1")) {
            this.renderAddCommandContent();
        } else if (command.equals("2")) {
            this.renderDeleteCommandContent();
        } else if (command.equals("3")) {
            this.renderSaleContent();
        } else {
            boolean isAdminLoggedUser = userService.getLoggedUser().getUserType() == UserType.ADMIN;
            if (isAdminLoggedUser)
                return PageName.HOME;
            else
                return PageName.LOGIN;
        }

        return PageName.PRODUCT_SALE;
    }

    void renderAddCommandContent() {
        String productId;
        Product product;
        while (true) {
            String labelID = "Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredID = true;
            Input inputID = new Input(labelID, RegexConstant.ONLY_DIGIT, isRequiredID);
            productId = inputID.renderAndGetText();

            if (productId.equals("0"))
                return;
            product = productService.getProductById(inputID.getTextAfterConvertToInt());
            if (product == null) {
                System.out.println("ID göre ürün bulunamadı tekrar deneyiniz");
            } else
                break;
        }

        String productQuantity;
        while (true) {
            String labelQuantity = "Ürün miktar giriniz veya çıkmak için 0'a basın";
            boolean isRequiredQuantity = true;
            Input inputQuantity = new Input(labelQuantity, RegexConstant.ONLY_DIGIT, isRequiredQuantity);
            productQuantity = inputQuantity.renderAndGetText();
            int quantityInt = inputQuantity.getTextAfterConvertToInt();

            if (productQuantity.equals("0"))
                return;
            CartItem cartItem=cartService.getCartItemByProductID(product.getId());

            boolean stateOverload = (cartItem != null) && (product.getQuantity() - quantityInt - cartItem.getQuantity()) < 0;
            if (product.getQuantity() < quantityInt|| stateOverload ||quantityInt == -1 ) {
                System.out.println("Yeterli stok yok tekrar deneyiniz");
            } else {
                cartService.insertProductToCart(product, quantityInt);
                break;
            }

        }


    }

    void renderDeleteCommandContent() {
        String productId;
        CartItem cartItem;
        if (cartService.isEmptyCart()) {
            System.out.print("Sepet Boş Ürün Silinemez \n");
            return;
        }

        while (true) {
            String labelID="Ürün ID giriniz veya çıkmak için 0'a basın ";
            boolean isRequiredID=true;
            Input inputID = new Input(labelID, RegexConstant.ONLY_DIGIT, isRequiredID);
            productId = inputID.renderAndGetText();

            int idInt = inputID.getTextAfterConvertToInt();

            if (productId.equals("0"))
                return;

            cartItem = cartService.getCartAll()
                    .stream()
                    .filter(c -> c.getProduct().getId() == idInt)
                    .findFirst()
                    .orElse(null);
            if(cartItem!=null)
                cartService.deleteProductFromCart(cartItem);
            break;

        }
    }

    void renderSaleContent() {

        if (cartService.isEmptyCart()) {
            System.out.println("Sepette ürün yok satmak için ürün ekleyiniz!!!\n");
            return;
        }

        System.out.println(cartService.getCartListConvertToString());
        float sumPrice = 0;
        for (CartItem item : cartService.getCartAll()) {
            sumPrice += item.getQuantity() * item.getProduct().getPrice();
        }
        System.out.println("Toplam Fiyat:" + sumPrice);

        String labelConfirm="Satışı onaylıyor musunuz evet yoksa hayır";
        boolean isRequiredConfirm=true;
        Input inputConfirm = new Input(labelConfirm, RegexConstant.COMMAND_YES_NO, isRequiredConfirm);
        String confirm = inputConfirm.renderAndGetText();

        if (confirm.equals("evet")) {
            cartService.saleCart();
            System.out.println("Ürünler satıldı ");
        } else {
            cartService.clearCart();
            System.out.println("Satış iptal edildi sepet boşaltıldı");
        }
    }

}