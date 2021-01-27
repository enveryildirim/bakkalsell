package com.company.pages;

import com.company.models.PageName;
import com.company.models.Product;
import com.company.models.User;
import com.company.services.ProductService;
import com.company.services.UserService;

public class ProductListPage extends PageBase{
    public ProductListPage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return true;
    }

    @Override
    public PageName render() {
        System.out.printf("Ürün Listeleme\n");
        productService.getAll().forEach(p->
                System.out.printf("ID:%d -- Ad: %s -- Fiyat:%f -- Stok:%d\n",p.getId(),p.getName(),p.getPrice(),p.getQuantity()));
        System.out.printf("1-Ürün Düzenleme\n");
        System.out.printf("2-Ürün Silme\n");
        String command=in.nextLine();
        if(command.equals("iptal"))
            return PageName.HOME;

        if(command.equals("1")){
            System.out.printf("Ürün İd giriniz \n");
            int id = in.nextInt();
            Product product=productService.getProductById(id);
            if(product==null){
                System.out.printf("İd uygun Ürün Yok\n");
                return PageName.PRODUCT_LIST;
            }

            while(true){
                System.out.printf("Şimdiki İsim:%s\n",product.getName());
                String ad=in.nextLine();
                String newName=in.nextLine();
                System.out.printf("Şimdiki Fiyat:%f\n",product.getPrice());
                float newPrice=in.nextFloat();
                System.out.printf("Şimdiki Stok:%d\n",product.getQuantity());
                int newQuantity=in.nextInt();

                if(newName.length()==0||newPrice<=0||newQuantity<=00){
                    System.out.printf("\nBilgileri kurallara uygun giriniz lütfen, devam etmek için bir tuşa basınız\n");
                    in.nextLine();
                    in.nextLine();
                    break;
                }
                System.out.printf("\n Onaylamak için evet iptal için hayır yazın\n");
                String sda=in.nextLine();
                String onay=in.nextLine();
                if(onay.equals("evet")){
                    System.out.printf("Güncelendi");

                    product.setName(newName);
                    product.setPrice(newPrice);
                    product.setQuantity(newQuantity);

                    productService.updateProduct(product);

                    return PageName.PRODUCT_LIST;
                }else
                    return PageName.PRODUCT_LIST;


            }
        }
        if(command.equals("2")){
            System.out.printf("Ürün İd giriniz \n");
            int id = in.nextInt();
            Product product=productService.getProductById(id);
            if(product==null){
                System.out.printf("İd uygun Ürün Yok\n");
                return PageName.PRODUCT_LIST;
            }
            System.out.printf("\n Onaylamak için evet iptal için hayır yazın\n");
            String adasd=in.nextLine();
            String onay=in.nextLine();
            if(onay.equals("evet")){
                System.out.printf("Silindi");
                productService.deleteProduct(product);
                return PageName.PRODUCT_LIST;
            }else
                return PageName.PRODUCT_LIST;

        }


        return PageName.PRODUCT_LIST;
    }
}
