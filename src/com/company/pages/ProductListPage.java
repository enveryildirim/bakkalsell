package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

/**
 * ürün işlemleirnin yapıldığı sınıf
 */
public class ProductListPage extends PageBase {

    public ProductListPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean isRequiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.printf("Ürün Listeleme\n");
        productService.getAll().forEach(p ->
                System.out.printf("ID:%d -- Ad: %s -- Fiyat:%f -- Stok:%d\n", p.getId(), p.getName(), p.getPrice(), p.getQuantity()));

        String msjCommand = "1-Ürün Düzenleme\n2-Ürün Silme\n0-Anasayfa";
        boolean isRequiredCommand = true;
        Input inCommand = new Input(msjCommand, Constant.PRODUCT_LIST_PAGE_COMMAND_LIST, isRequiredCommand);
        String command = inCommand.renderAndGetText();

        if (command.equals("1")) {
            renderUpdateCommandContent();
        } else if (command.equals("2")) {
            renderDeleteCommandContent();
        } else if (command.equals("0")) {
            return PageName.HOME;
        } else {
            System.out.printf("Yanlış giriş yaptınız");
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
        Input inID = new Input(labelId, Constant.ONLY_DIGIT, isRequiredID);
        String idStr = inID.renderAndGetText();

        int idInt = inID.getTextAfterConvertToInt();
        Product product = productService.getProductById(idInt);
        if (product == null) {
            System.out.printf("İd uygun Ürün Yok\n");
            return;
        }

        String msjName = String.format("Şimdiki İsim:%s\n", product.getName());
        boolean isRequiredName = true;
        Input inName = new Input(msjName, "[a-zA-Z]", isRequiredName);
        String newName = inName.renderAndGetText();

        String msjPrice = String.format("Şimdiki Fiyat:%f\n", product.getPrice());
        boolean isRequiredPrice = true;
        Input inPrice = new Input(msjPrice, Constant.ONLY_DIGIT, isRequiredPrice);
        float newPrice = Float.parseFloat(inPrice.renderAndGetText());

        String msjQuantity = String.format("Şimdiki Miktar:%f\n", product.getQuantity());
        boolean isRequiredQuantity = true;
        Input inQuantity = new Input(msjQuantity, Constant.ONLY_DIGIT, isRequiredQuantity);
        String strQuantity = inQuantity.renderAndGetText();
        int newQuantity = inQuantity.getTextAfterConvertToInt();

        String labelConfirm = "Onaylamak için evet iptal için hayır yazın";
        boolean isRequiredConfirm = true;
        Input inConfirm = new Input(labelConfirm, Constant.COMMAND_YES_NO, isRequiredConfirm);
        String confirm = inConfirm.renderAndGetText();

        if (confirm.equals("evet")) {

            product.setName(newName);
            product.setPrice(newPrice);
            product.setQuantity(newQuantity);
            productService.updateProduct(product);
            System.out.printf("Güncelendi");

        } else {
            System.out.printf("İptal Edildi");
            return;
        }

    }

    /**
     * ürün silme işlemlerini ekrana basar
     */
    void renderDeleteCommandContent() {

        Input inID = new Input("Ürün İd giriniz", Constant.ONLY_DIGIT, true);
        String id = inID.renderAndGetText();
        int idInt = inID.getTextAfterConvertToInt();
        Product product = productService.getProductById(idInt);
        if (product == null) {
            System.out.printf("İd uygun Ürün Yok\n");
            return;
        }

        System.out.printf("%d %s silinecek\n", product.getId(), product.getName());
        Input inConfirm = new Input("Onaylamak için evet iptal için hayır yazın", "(evet|hayır)", true);
        String confirm = inConfirm.renderAndGetText();

        if (confirm.equals("evet")) {
            productService.deleteProduct(product);
            System.out.printf("Silindi");
        } else {
            System.out.printf("İptal Edildi");
            return;
        }
    }

}
