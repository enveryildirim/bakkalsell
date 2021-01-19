package com.company.pages;

import com.company.dal.DB;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.services.ProductService;
import com.company.services.UserService;

import java.util.Arrays;
import java.util.Scanner;

public class ProductSalePage extends PageBase{
    public ProductSalePage(UserService userService, ProductService productService) {
        super(userService, productService);
    }

    @Override
    public boolean requiredAuth() {
        return false;    }

    @Override
    public PageName render() {
        Scanner in = new Scanner(System.in);

        DB.products.forEach(p-> System.out.printf("Kod:{%d} Ad:{%s} Fiyat:{%f} Kalan:{%d} \n", p.getId(),p.getName(),p.getPrice(),p.getQuantity()));
        System.out.println("Ürünlerin Kodlarını Giriniz");
        String productCodes =in.nextLine();

        Arrays.stream(productCodes.split(","))
                .forEach(c->{
                    Product product=  DB.products.stream().filter(p->p.getId()==Integer.parseInt(c))
                            .findFirst()
                            .get();
                    System.out.println("Ad: "+product.getName()+"  Fiyat: "+product.getPrice());
                });

        System.out.println("Ürünlerin Onaylıyor musunuz");
        if(in.nextLine().equals("y")){
            System.out.println("Satış yapıldı");
        }

        return PageName.PRODUCT_SALE;
    }
}
