package com.company;

import com.company.dal.*;
import com.company.models.CartItem;
import com.company.models.PageName;
import com.company.models.Product;

import com.company.models.User;
import com.company.pages.*;
import com.company.services.ProductService;
import com.company.services.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {

    public static Map<PageName, PageBase> pages=new HashMap<>();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        init();

        PageName current=PageName.TEST;
        while(true){

           //Eğer kullanıcı giriş yapmadıysa Login Sayfasına Gönder
           /* if(DB.currentLoginedUser==null)
                current=PageName.LOGIN;
*/
            //Yüklenecek sayfanın gösterilmesi
            PageBase page =pages.get(current);


            //Gidelecek Sayfanın Yetki isteyip istemediği ve Kullanıcının bu yetkiye sahip olup olmadığı kotrolü yapılıyor.
            if(page.requiredAuth()){
                if(DB.currentLoginedUser.getUserType()==1)
                    current =PageName.PRODUCT_SALE;
            }

            current= page.render();

        }

    }



    /**
     * Gerekli işlemlerin yapılması
     */
    static void init(){
        /**
         * Bazı ürünlerin girilmesi
         * */
        DB.products.add(new Product(1,"gofret",2,50));
        DB.products.add(new Product(2,"gazoz",1,50));
        DB.products.add(new Product(3,"pirinç",10,20));
        DB.products.add(new Product(4,"ekmek",2,30));
        DB.products.add(new Product(5,"kek",2,60));
        DB.products.add(new Product(6,"mercimek",14,20));

        /**
         * Dependcy injection constructor kullanıldı
         * bağımlılıklar bir yerden oluşturulup dağıtılıyor.
         * Veritabanı katmanı sadece Servis katmanı ile iletişime geçiyor.
         * */
        IRepository<User> userRepository=new UserRepository();
        IRepository<Product> productRepository= new ProductRepository();
        IRepository<CartItem> cartItemIRepository = new CartItemRepository();
        /**
         * Burada da Page katmanını Dependency injection constructor methodu kullandık
         * Page katmanı sadece servis katmanı ile haberleşiyor. Veritabını katmanı ile haberleşmiyor.
         * */
        UserService userService=new UserService(userRepository);
        ProductService productService=new ProductService(productRepository,cartItemIRepository);


        /**
         * Sayfaların ayarlanması
         * */
        pages.put(PageName.LOGIN,new LoginPage(userService,productService));
        pages.put(PageName.HOME,new HomePage(userService,productService));
        pages.put(PageName.PRODUCT_SALE,new ProductSalePage(userService,productService));
        pages.put(PageName.USER_CREATE,new UserCreatePage(userService,productService));
        pages.put(PageName.PRODUCT_CREATE,new ProductCreatePage(userService,productService));
        pages.put(PageName.USER_LIST,new UserListPage(userService,productService));
        pages.put(PageName.PRODUCT_LIST,new ProductListPage(userService,productService));
        pages.put(PageName.TEST,new TestPage(userService,productService));

        /**
         * Varsayılan kullanıcıların bilgilerinin oluşturulması
         */
        //User type 0 =Admin yetkisi 1 =kasiyer
        User userAdmin= new User(1,"admin admin","admin","admin",0);
        User user= new User(2,"user user","user","user",1);

        userService.createUser(userAdmin);
        userService.createUser(user);


    }



}
