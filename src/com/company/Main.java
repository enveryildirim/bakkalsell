package com.company;

import com.company.dal.*;
import com.company.models.*;

import com.company.pages.*;
import com.company.services.OrderService;
import com.company.services.ProductService;
import com.company.services.UserService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    public static Map<PageName, PageBase> pages = new HashMap<>();

    public static void main(String[] args) {
        init();
        PageName currentPage = PageName.LOGIN;
        while (true) {

            //Eğer kullanıcı giriş yapmadıysa Login Sayfasına Gönder
            if (DB.currentLoginedUser == null)
                currentPage = PageName.LOGIN;

            //Yüklenecek sayfanın gösterilmesi
            PageBase page = pages.get(currentPage);


            //Gidelecek Sayfanın Yetki isteyip istemediği ve Kullanıcının bu yetkiye sahip olup olmadığı kotrolü yapılıyor.
            if (page.requiredAuth()) {
                if (DB.currentLoginedUser.getUserType() == UserType.EMPLOYEE)
                    currentPage = PageName.PRODUCT_SALE;
            }

            currentPage = page.render();

        }

    }


    /**
     * Gerekli işlemlerin yapılması
     */
    static void init() {
        /**
         * Bazı ürünlerin girilmesi
         * */
        DB.products.add(new Product(1, "gofret", 2, 50));
        DB.products.add(new Product(2, "gazoz", 1, 50));
        DB.products.add(new Product(3, "pirinç", 10, 20));
        DB.products.add(new Product(4, "ekmek", 2, 30));
        DB.products.add(new Product(5, "kek", 2, 60));
        DB.products.add(new Product(6, "mercimek", 14, 20));

        /**
         * Dependcy injection constructor kullanıldı
         * bağımlılıklar bir yerden oluşturulup dağıtılıyor.
         * Veritabanı katmanı sadece Servis katmanı ile iletişime geçiyor.
         * */
        IRepository<User> userRepository = new UserRepository();
        IRepository<Product> productRepository = new ProductRepository();
        IRepository<CartItem> cartItemIRepository = new CartItemRepository();
        IRepository<Order> orderIRepository = new OrderRepository();
        /**
         * Burada da Page katmanını Dependency injection constructor methodu kullandık
         * Page katmanı sadece servis katmanı ile haberleşiyor. Veritabını katmanı ile haberleşmiyor.
         * */
        UserService userService = new UserService(userRepository);
        ProductService productService = new ProductService(productRepository, cartItemIRepository);
        OrderService orderService = new OrderService(userRepository, productRepository, cartItemIRepository, orderIRepository);

        /**
         * Sayfaların ayarlanması
         * */
        pages.put(PageName.LOGIN, new LoginPage(userService, productService));
        pages.put(PageName.HOME, new HomePage(userService, productService));
        pages.put(PageName.PRODUCT_SALE, new ProductSalePage(userService, productService));
        pages.put(PageName.USER_CREATE, new UserCreatePage(userService, productService));
        pages.put(PageName.PRODUCT_CREATE, new ProductCreatePage(userService, productService));
        pages.put(PageName.USER_LIST, new UserListPage(userService, productService));
        pages.put(PageName.PRODUCT_LIST, new ProductListPage(userService, productService));

        TestPage testPage = new TestPage(userService, productService);
        testPage.setOrderService(orderService);
        pages.put(PageName.TEST, testPage);

        OrderPage orderPage = new OrderPage(userService, productService);
        orderPage.setOrderService(orderService);
        pages.put(PageName.ORDER, orderPage);
        OrderViewPage orderViewPage = new OrderViewPage(userService, productService);
        orderViewPage.setOrderService(orderService);
        pages.put(PageName.ORDER_VIEW, orderViewPage);
        /**
         * Varsayılan kullanıcıların bilgilerinin oluşturulması
         */
        //User type 0 =Admin yetkisi 1 =kasiyer
        User userAdmin = new User(1, "admin admin", "admin", "adminadmin", UserType.ADMIN);
        User user = new User(2, "user user", "useruser", "useruser", UserType.EMPLOYEE);
        User userCustomer = new User(3, "customer customer", "custom", "custom", UserType.CUSTOMER);

        userService.createUser(userAdmin);
        userService.createUser(user);
        userService.createUser(userCustomer);
        DB.currentLoginedUser = userCustomer;

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(new CartItem(new Product(1, "gofret", 2, 50), 10));
        cartItemList.add(new CartItem(new Product(2, "gazoz", 1, 50), 15));
        DB.orders.add(new Order(userCustomer, cartItemList));

    }


}
