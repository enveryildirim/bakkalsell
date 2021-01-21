package com.company.pages;

import com.company.dal.DB;
import com.company.models.CartItem;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.services.ProductService;
import com.company.services.UserService;

public class OrderPage extends PageBase{
    public OrderPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return true;
    }
//TODO ürünler,n sepetten çıkarılması işleminin yapılması
    @Override
    public PageName render() {
        while(true){
            System.out.printf("------------ÜRÜN LİSTESİ----------\n");
            System.out.printf("----------------------------------\n");
            System.out.println(productService.getAllProductForCart());
            System.out.println(orderService.listOrder());
            System.out.println("Ürünlerin Kodlarını Giriniz ve tamam yazın veya iptal yazıp çıkın");

            String productCode =in.nextLine();
            if (productCode.equals("iptal")) {
                productService.clearCart();
                return PageName.LOGIN;
            }

            if(productCode.equals("tamam")){
                break;
            }
            if(!this.isInt(productCode)){
                System.out.printf("Uygun bir Id giriniz\n devam etmek için bir tuşa basınız");
                in.nextLine();
                return PageName.ORDER;
            }

            System.out.println("Ürünlerin Miktarını Giriniz");
            String productQuantity =in.nextLine();
            if(!this.isInt(productQuantity) || Integer.parseInt(productQuantity)<=0){
                System.out.printf("Uygun bir miktar\n devam etmek için bir tuşa basınız");
                in.nextLine();
                return PageName.ORDER;
            }

            Product product=productService.getProductById(Integer.parseInt(productCode));
            if(product==null){
                System.out.printf("Uygun bir Id giriniz \n devam etmek için bir tuşa basınız");
                in.nextLine();
                return PageName.ORDER;
            }
            if(product.getQuantity()>Integer.parseInt(productQuantity)){

                productService.insertProductToCart(product,Integer.parseInt(productQuantity));
                orderService.addOrder();
            }
            else
                System.out.println("Yeterli stok yok");

        }

        productService.getCart().forEach(c-> System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f}  Alınan Miktar:{%d} Tutar:{%f} \n", c.product.getId(),c.product.getName(),c.product.getPrice(),c.quantity,c.product.getPrice()*c.quantity));

        float toplamFiyat =0;
        for (CartItem item: DB.cart) {
            toplamFiyat+=item.quantity*item.product.getPrice();

        }

        System.out.printf("Toplam Tutar:{%f}",toplamFiyat);
        System.out.println("\nÜrünlerin Onaylıyor musunuz? evet için e hayır için h");
        if(in.nextLine().equals("e")){
            System.out.println("Kayıt yapıldı\n Devam etmek için d basın veya çıkmak için çık yazın ");
        }else
            return PageName.LOGIN;

        return PageName.ORDER;
    }
}
