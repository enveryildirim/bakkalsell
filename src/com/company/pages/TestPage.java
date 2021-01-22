package com.company.pages;

import com.company.Constant;
import com.company.dal.DB;
import com.company.models.CartItem;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.pages.components.Input;
import com.company.services.ProductService;
import com.company.services.UserService;

public class TestPage extends PageBase {
    public TestPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return false;
    }

    @Override
    public PageName render() {

        System.out.printf("------------ÜRÜN LİSTESİ----------\n");
        System.out.printf("----------------------------------\n");
        System.out.println(productService.getAllProductForCart());
        System.out.println(productService.getCartToString());

        Input inCommand = new Input(null, "Sepete Ürün Ekleme=1\nSepete Ürün Silme=2\nSepeti Satış=3\nAnasayfa=0", "[0123]", true);
        String command = inCommand.render();
        if (command.equals("1")) {
            this.renderEkleme();
        } else if (command.equals("2")) {
            this.renderSilme();
        } else if (command.equals("3")) {
            //satış işlemleri
        } else {
            //Anasayfa yönlendirme işlemleri

        }

        return PageName.TEST;
    }

    void renderEkleme() {
        String id, quantity = "";
        Product product;
        while (true) {
            Input inID = new Input(null, "Ürün ID giriniz veya çıkmak için 0'a basın ", Constant.ONLY_DIGIT, true);
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
            Input inQuantity = new Input(null, "Ürün miktar giriniz veya çıkmak için 0'a basın", Constant.ONLY_DIGIT, true);
            quantity = inQuantity.render();
            int quantityInt = inQuantity.getInt();
            if (quantity.equals("0"))
                return;

            if (product.getQuantity() < quantityInt) {
                System.out.printf("Yeterli stok yok tekrar deneyiniz");
            } else{
                productService.insertProductToCart(product, quantityInt);
                break;
            }


        }


    }
    void renderSilme(){
        String id = "";
        CartItem cartItem;
        if(productService.getCart().size()==0){
            System.out.printf("Sepet Boş Ürün Silinemez \n");
            return;
        }
        while (true) {
            Input inID = new Input(null, "Ürün ID giriniz veya çıkmak için 0'a basın ", Constant.ONLY_DIGIT, true);
            id = inID.render();

            int idInt=inID.getInt();

            if (id.equals("0"))
                return;

            cartItem=productService.getCart()
                    .stream()
                    .filter(c->c.product.getId()==idInt)
                    .findFirst()
                    .get();

            if(cartItem!=null){
                productService.deleteProductFromCart(cartItem);
                break;

            }

        }
    }
}