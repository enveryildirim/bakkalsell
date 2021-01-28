package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class ProductCreatePage extends PageBase {

    public ProductCreatePage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.println("Ürün oluşturma sayfası lütfen bilgileri giriniz ***Boş alan bırakmayınız");

        System.out.println("**Ürün miktarı ve fiyatı eksi  değer olamaz \n Aynı ürün iki defa eklenemez. ");

        String labelName = "AD giriniz";
        boolean isRequiredName = true;
        Input inName = new Input(labelName, Constant.CUMLE_TR, isRequiredName);
        String name = inName.renderAndGetText();

        String labelPrice = "FİYAT giriniz";
        boolean isRequiredPrice = true;
        Input inPrice = new Input(labelPrice, Constant.ONLY_DIGIT, isRequiredPrice);
        String priceString = inPrice.renderAndGetText();

        String labelQuantity = "STOK giriniz";
        boolean isRequiredQuantity = true;
        Input inQuantity = new Input(labelQuantity, Constant.ONLY_DIGIT, isRequiredQuantity);
        String quantityString = inQuantity.renderAndGetText();


        if (name.length() == 0 || priceString.length() == 0 || quantityString.length() == 0) {
            System.out.println("Lütfen boş alan bırakmayınız \n devam etmek için bir tuşa basınız");
            in.nextLine();
            return PageName.PRODUCT_CREATE;
        }

        float price = inPrice.getTextAfterConvertToFloat();
        int quantity = inPrice.getTextAfterConvertToInt();
        if (price <= 0 || quantity <= 0) {
            System.out.println("Lütfen değerleri 0 veya eksi değer girmeyiniz \n devam etmek için bir tuşa basınız.");
            in.nextLine();
            return PageName.PRODUCT_CREATE;
        }

        Product newProduct = new Product(name, price, quantity);
        productService.createProduct(newProduct);
        System.out.println("Eklendi Ürün Başarılı");

        return PageName.HOME;
    }

}
