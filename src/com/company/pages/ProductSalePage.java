package com.company.pages;

import com.company.dal.DB;
import com.company.models.CartItem;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.services.ProductService;
import com.company.services.UserService;

public class ProductSalePage extends PageBase{
    public ProductSalePage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return false;    }

    @Override
    public PageName render() {


        productService.getAll().forEach(p-> System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(),p.getName(),p.getPrice(),p.getQuantity()));
        while(true){
            System.out.println("Ürünlerin Kodlarını Giriniz ve tamam yazın veya iptal yazıp çıkın");
            String productCode =in.nextLine();
            if (productCode.equals("iptal"))
                return PageName.LOGIN;
            if(productCode.equals("tamam")){
                break;
            }
            System.out.println("Ürünlerin Miktarını Giriniz");
            String productQuantity =in.nextLine();

            Product product=productService.getAll().stream().filter(p->p.getId()==Integer.parseInt(productCode))
                    .findFirst()
                    .get();
            if(product.getQuantity()>Integer.parseInt(productQuantity)){
                DB.cart.add(new CartItem(product,Integer.parseInt(productQuantity)));
            }
            else
                System.out.println("Yeterli stok yok");

        }

        DB.cart.forEach(c-> System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f}  Alınan Miktar:{%d} Tutar:{%f} \n", c.product.getId(),c.product.getName(),c.product.getPrice(),c.quantity,c.product.getPrice()*c.quantity));
        float toplamFiyat =0;
        for (CartItem item:DB.cart) {
            toplamFiyat+=item.quantity*item.product.getPrice();

        }

        System.out.printf("Toplam Tutar:{%f}",toplamFiyat);
        System.out.println("\nÜrünlerin Onaylıyor musunuz? evet için e hayır için h");
        if(in.nextLine().equals("e")){
            System.out.println("Satış yapıldı\n Devam etmek için d basın veya çıkmak için çık yazın ");
            if(DB.currentLoginedUser.getUserType()==0){
                System.out.println("Anasayfa için home yazın");
                if(in.nextLine().equals("home"))
                    return PageName.HOME;
            }else{
               if(in.nextLine().equals("çık"))
                   return PageName.LOGIN;
            }

        }

        return PageName.PRODUCT_SALE;
    }
}