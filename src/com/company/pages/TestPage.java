package com.company.pages;

import com.company.dal.DB;
import com.company.models.CartItem;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.services.ProductService;
import com.company.services.UserService;

public class TestPage extends PageBase{
    public TestPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return false;
    }

    @Override
    public PageName render() {
        //productService.getAll().forEach(p-> System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(),p.getName(),p.getPrice(),p.getQuantity()));

        while(true){
            productService.getAll().forEach(p-> {
                    CartItem cartItem= productService.getCart().stream()
                                         .filter(c->c.product.getId()==p.getId())
                                         .findFirst()
                                         .orElse(null);
                    if(cartItem!=null)
                        System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(),p.getName(),p.getPrice(),(p.getQuantity()-cartItem.quantity));
                    else
                        System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(),p.getName(),p.getPrice(),p.getQuantity());
            });
            System.out.printf("Yeni Tasarlanış \n");

            System.out.println("Ürünlerin Kodlarını Giriniz ve tamam yazın veya iptal yazıp çıkın sat yazın");
            String productCode =in.nextLine();
            if(productCode.equals("sat"))
                break;
            System.out.println("Ürünlerin Miktarını Giriniz");
            String productQuantity =in.nextLine();

            Product product=productService.getProductById(Integer.parseInt(productCode));
            if(product==null){
                System.out.printf("Uygun bir Id giriniz \n devam etmek için bir tuşa basınız");
                in.nextLine();
                return PageName.PRODUCT_SALE;
            }
            if(product.getQuantity()>Integer.parseInt(productQuantity)){


                productService.insertProductToCart(product,Integer.parseInt(productQuantity));
                continue;
            }
            else
                System.out.println("Yeterli stok yok");

        }

        System.out.printf("--eklenene\n");
        productService.getCart().forEach(c-> System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f}  Alınan Miktar:{%d} Tutar:{%f} \n", c.product.getId(),c.product.getName(),c.product.getPrice(),c.quantity,c.product.getPrice()*c.quantity));

        float toplamFiyat =0;
        for (CartItem item:DB.cart) {
            toplamFiyat+=item.quantity*item.product.getPrice();

        }

        System.out.printf("Toplam Tutar:{%f}",toplamFiyat);
        System.out.println("\nÜrünlerin Onaylıyor musunuz? evet için e hayır için h");
        if(in.nextLine().equals("e")){
            productService.saleCart();
            System.out.println("Satış yapıldı\n Devam etmek için d basın veya çıkmak için çık yazın ");
            productService.getAll().forEach(p-> System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(),p.getName(),p.getPrice(),p.getQuantity()));
            in.nextLine();

        }

        System.out.println("Tekrar için bir tuşa basın ");
        in.nextLine();
        return PageName.TEST;
    }
}
