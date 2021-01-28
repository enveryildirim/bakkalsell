package com.company.pages;

import com.company.Constant;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.models.User;
import com.company.pages.components.Input;
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
        String msj="1-Ürün Düzenleme\n2-Ürün Silme\n0-Anasayfa";

        Input inCommand= new Input(null,msj,"[012]",true);
        String command=inCommand.render();
        if(command.equals("1")){
            renderUpdate();
        }else if(command.equals("2")){
            renderDelete();
        }else if(command.equals("0")){
            return PageName.HOME;
        }else{
            System.out.printf("Yanlış giriş yapmdınız");
            return PageName.TEST;
        }


        return PageName.PRODUCT_LIST;
    }

    void renderUpdate(){

        Input inID =new Input(null,"Ürün İd giriniz", Constant.ONLY_DIGIT,true);
        String id = inID.render();
        int idInt=inID.getInt();
        Product product=productService.getProductById(idInt);
        if(product==null){
            System.out.printf("İd uygun Ürün Yok\n");
            return;
        }
        String msjName=String.format("Şimdiki İsim:%s\n",product.getName());
        Input inName=new Input(null,msjName,"[a-zA-Z]",true);
        String newName=inName.render();

        String msjPrice=String.format("Şimdiki Fiyat:%f\n",product.getPrice());
        Input inPrice=new Input(null,msjName,Constant.ONLY_DIGIT,true);
        float newPrice=Float.parseFloat(inPrice.render());

        String msjQuantity=String.format("Şimdiki Miktar:%f\n",product.getPrice());
        Input inQuantity=new Input(null,msjName,Constant.ONLY_DIGIT,true);
        String strQuantity=inQuantity.render();
        int newQuantity=inQuantity.getInt();

        Input inConfirm=new Input(null,"Onaylamak için evet iptal için hayır yazın","(evet|hayır)",true);
        String confirm=inConfirm.render();
        if(confirm.equals("evet")){
            //güncelleme
            System.out.printf("Güncelendi");

            product.setName(newName);
            product.setPrice(newPrice);
            product.setQuantity(newQuantity);

            productService.updateProduct(product);
        }else{
            System.out.printf("İptal Edildi");
            return;
        }

    }
    void renderDelete(){

        Input inID =new Input(null,"Ürün İd giriniz",Constant.ONLY_DIGIT,true);
        String id = inID.render();
        int idInt=inID.getInt();
        Product product=productService.getProductById(idInt);
        if(product==null){
            System.out.printf("İd uygun Ürün Yok\n");
            return;
        }
        System.out.printf("%d %s silinecek\n",product.getId(),product.getName());
        Input inConfirm=new Input(null,"Onaylamak için evet iptal için hayır yazın","(evet|hayır)",true);
        String confirm=inConfirm.render();
        if(confirm.equals("evet")){
            //silme işlemi
            System.out.printf("Silindi");
            productService.deleteProduct(product);
        }else{
            System.out.printf("İptal Edildi");
            return;
        }
    }
}
