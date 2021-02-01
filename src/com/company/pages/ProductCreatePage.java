package com.company.pages;

import com.company.RegexConstant;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.pages.components.Input;
import com.company.services.ProductService;

/**
 * Ürüün oluşturma işlemleri ekranı
 */
public class ProductCreatePage extends PageBase {
    private ProductService productService;

    public ProductCreatePage(ProductService productService) {
        this.productService = productService;
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
        Input inputName = new Input(labelName, RegexConstant.CUMLE_TR, isRequiredName);
        String name = inputName.renderAndGetText();

        String labelPrice = "FİYAT giriniz";
        boolean isRequiredPrice = true;
        Input inputPrice = new Input(labelPrice, RegexConstant.ONLY_DIGIT, isRequiredPrice);
        String priceString = inputPrice.renderAndGetText();

        String labelQuantity = "STOK giriniz";
        boolean isRequiredQuantity = true;
        Input inputQuantity = new Input(labelQuantity, RegexConstant.ONLY_DIGIT, isRequiredQuantity);
        String quantityString = inputQuantity.renderAndGetText();


        if (name.length() == 0 || priceString.length() == 0 || quantityString.length() == 0) {
            System.out.println("Lütfen boş alan bırakmayınız \n devam etmek için bir tuşa basınız");
            inputData.nextLine();
            return PageName.PRODUCT_CREATE;
        }

        float price = inputPrice.getTextAfterConvertToFloat();
        int quantity = inputQuantity.getTextAfterConvertToInt();
        if (price <= 0 || quantity <= 0) {
            System.out.println("Lütfen değerleri 0 veya eksi değer girmeyiniz \n devam etmek için bir tuşa basınız.");
            inputData.nextLine();
            return PageName.PRODUCT_CREATE;
        }

        Product newProduct = new Product(name, price, quantity);
        productService.createProduct(newProduct);
        System.out.println("Eklendi Ürün Başarılı");

        return PageName.HOME;
    }

}
