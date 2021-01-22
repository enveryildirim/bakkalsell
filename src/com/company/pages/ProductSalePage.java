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

        while(true){
            System.out.printf("------------ÜRÜN LİSTESİ----------\n");
            System.out.printf("----------------------------------\n");
            System.out.println(productService.getAllProductForCart());
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
                return PageName.PRODUCT_SALE;
            }
            System.out.println("Ürünlerin Miktarını Giriniz");
            String productQuantity =in.nextLine();
            if(!this.isInt(productQuantity) || Integer.parseInt(productQuantity)<=0){
                System.out.printf("Uygun bir miktar\n devam etmek için bir tuşa basınız");
                in.nextLine();
                return PageName.PRODUCT_SALE;
            }
            Product product=productService.getProductById(Integer.parseInt(productCode));
            if(product==null){
                System.out.printf("Uygun bir Id giriniz \n devam etmek için bir tuşa basınız");
                in.nextLine();
                return PageName.PRODUCT_SALE;
            }
            if(product.getQuantity()>Integer.parseInt(productQuantity)){

                productService.insertProductToCart(product,Integer.parseInt(productQuantity));

            }
            else
                System.out.println("Yeterli stok yok");

            System.out.printf("---Sepettekiler---\n");
            productService.getCart().forEach(c-> System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f}  Alınan Miktar:{%d} Tutar:{%f} \n", c.product.getId(),c.product.getName(),c.product.getPrice(),c.quantity,c.product.getPrice()*c.quantity));

        }
        //todo sepetten ürün silme işlemi yapıldı güncellenmesi lazım veri akışının
        while(true){
            //sepet duzenleme
            System.out.println("Düzenlemek için d yazın");
            String commandUpdate=in.nextLine();
            if(commandUpdate.equals("d")){
                System.out.println("id giriniz çıkarmak istediğiniz ");
                String id=in.nextLine();
                if(!this.isInt(id)){
                    System.out.printf("Uygun bir Id giriniz\n devam etmek için bir tuşa basınız");
                    in.nextLine();
                    continue;
                }
               CartItem cartItem=productService.getCart()
                       .stream()
                       .filter(c->c.product.getId()==Integer.parseInt(id))
                       .findFirst()
                       .get();
                if(cartItem==null){
                    System.out.println("id bulunamadı");
                }

                productService.deleteProductFromCart(cartItem);

            }else
                break;
        }

        productService.getCart().forEach(c-> System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f}  Alınan Miktar:{%d} Tutar:{%f} \n", c.product.getId(),c.product.getName(),c.product.getPrice(),c.quantity,c.product.getPrice()*c.quantity));

        float toplamFiyat =0;
        for (CartItem item:DB.cart) {
            toplamFiyat+=item.quantity*item.product.getPrice();

        }

        System.out.printf("Toplam Tutar:{%f}",toplamFiyat);

        System.out.println("\nÜrünlerin Onaylıyor musunuz? evet için e hayır için h ");
        if(in.nextLine().equals("e")){
            System.out.println("Satış yapıldı\n Devam etmek için d basın veya çıkmak için çık yazın ");
            productService.saleCart();
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
