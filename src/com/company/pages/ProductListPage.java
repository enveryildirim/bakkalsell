package com.company.pages;

import com.company.RegexConstant;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.models.Result;

/**
 * ürün işlemleirnin yapıldığı sınıf
 */
public class ProductListPage extends PageBase {

    private ProductService productService;

    public ProductListPage(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.println("Ürün Listeleme");
        productService.getAll().forEach(p ->
                System.out.printf("ID:%d -- Ad: %s -- Fiyat:%.02f -- Stok:%d\n", p.getId(), p.getName(), p.getPrice(), p.getQuantity()));

        String msjCommand = "1-Ürün Düzenleme\n2-Ürün Silme\n0-Anasayfa";
        boolean isRequiredCommand = true;
        Input inputCommand = new Input(msjCommand, RegexConstant.PRODUCT_LIST_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inputCommand.renderAndGetText();

        if (command.equals("1")) {
            renderUpdateCommandContent();
        } else if (command.equals("2")) {
            renderDeleteCommandContent();
        } else if (command.equals("0")) {
            return PageName.HOME;
        } else {
            System.out.println("Yanlış giriş yaptınız");
            return PageName.TEST;
        }

        return PageName.PRODUCT_LIST;
    }

    /**
     * Ürün güncelleme ko9mutu işlemlerini ekrana basar
     */
    void renderUpdateCommandContent() {
        String labelId = "Ürün İd giriniz";
        boolean isRequiredID = true;
        Input inputID = new Input(labelId, RegexConstant.ONLY_DIGIT, isRequiredID);
        inputID.renderAndGetText();

        int idInt = inputID.getTextAfterConvertToInt();
        Product product = productService.getProductById(idInt);
        if (product == null) {
            System.out.println("İd uygun Ürün Yok\n");
            return;
        }

        String msjName = String.format("Şimdiki İsim:%s\n", product.getName());
        boolean isRequiredName = true;
        Input inputName = new Input(msjName, RegexConstant.NAME_SURNAME_TR, isRequiredName);
        String newName = inputName.renderAndGetText();

        String msjPrice = String.format("Şimdiki Fiyat:%f\n", product.getPrice());
        boolean isRequiredPrice = true;
        Input inputPrice = new Input(msjPrice, RegexConstant.ONLY_DIGIT, isRequiredPrice);
        float newPrice = Float.parseFloat(inputPrice.renderAndGetText());

        String msjQuantity = String.format("Şimdiki Miktar:%s\n", product.getQuantity());
        boolean isRequiredQuantity = true;
        Input inputQuantity = new Input(msjQuantity, RegexConstant.ONLY_DIGIT, isRequiredQuantity);
        inputQuantity.renderAndGetText();
        int newQuantity = inputQuantity.getTextAfterConvertToInt();

        String labelConfirm = "Onaylamak için evet iptal için hayır yazın";
        boolean isRequiredConfirm = true;
        Input inputConfirm = new Input(labelConfirm, RegexConstant.COMMAND_YES_NO, isRequiredConfirm);
        String confirm = inputConfirm.renderAndGetText();

        if (confirm.equals("evet")) {

            product.setName(newName);
            product.setPrice(newPrice);
            product.setQuantity(newQuantity);
            Result<Product> result = productService.updateProductResult(product);
            System.out.println(result.getMessage());

        } else {
            System.out.println("İptal Edildi");
        }

    }

    /**
     * ürün silme işlemlerini ekrana basar
     */
    void renderDeleteCommandContent() {

        Input inputID = new Input("Ürün İd giriniz", RegexConstant.ONLY_DIGIT, true);
        inputID.renderAndGetText();
        int idInt = inputID.getTextAfterConvertToInt();
        Product product = productService.getProductById(idInt);
        if (product == null) {
            System.out.println("İd uygun Ürün Yok");
            return;
        }

        System.out.printf("%d %s silinecek\n", product.getId(), product.getName());
        Input inConfirm = new Input("Onaylamak için evet iptal için hayır yazın", "(evet|hayır)", true);
        String confirm = inConfirm.renderAndGetText();

        if (confirm.equals("evet")) {
            Result<Product> result = productService.deleteProductResult(product);
            System.out.println(result.getMessage());
        } else {
            System.out.println("İptal Edildi");
        }
    }

}
