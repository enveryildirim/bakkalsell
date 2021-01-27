package com.company.pages;

import com.company.models.PageName;
import com.company.models.Product;
import com.company.services.ProductService;
import com.company.services.UserService;

public class ProductCreatePage extends PageBase{
    public ProductCreatePage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.println("Ürün oluşturma sayfası lütfen bilgileri giriniz ***Boş alan bırakmayınız");

        System.out.println("**Ürün miktarı ve fiyatı eksi  değer olamaz \n Aynı ürün iki defa eklenemez. ");

        System.out.println("Ad girin");
        String name=in.nextLine();

        System.out.println("Fiyat");
        String price=in.nextLine();

        System.out.println("Stok girin");
        String quantity = in.nextLine();
        float deger =0;
        int stok=0;

        if(name.length()==0||price.length()==0||quantity.length()==0) {
            System.out.printf("Lütfen boş alan bırakmayınız \n devam etmek için bir tuşa basınız");
            in.nextLine();
            return PageName.PRODUCT_CREATE;
        }
        try {
             deger = Float.parseFloat(price);
             stok=Integer.parseInt(quantity);
             if(deger<=0 ||stok<=0){
                 System.out.printf("Lütfen değerleri eksi girmeyiniz \n devam etmek için bir tuşa basınız.");
                    in.nextLine();
                    return PageName.PRODUCT_CREATE;
             }
        }catch(Exception e){
            System.out.printf("Lütfen değerleri sayı formatında giriniz \n devam etmek için bir tuşa basınız.");
            in.nextLine();
            return PageName.PRODUCT_CREATE;
        }

        productService.createProduct(new Product(0,name,deger,stok));
        System.out.printf("Eklendi Ürün Başarılı");
        return PageName.HOME;
    }
}
