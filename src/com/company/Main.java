package com.company;

import com.company.dal.*;
import com.company.models.PageName;
import com.company.models.Product;
import com.company.models.User;
import com.company.models.UserType;
import com.company.pages.*;
import com.company.services.CartService;
import com.company.services.OrderService;
import com.company.services.ProductService;
import com.company.services.UserService;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Map<PageName, PageBase> pages = new HashMap<>();

    public static void main(String[] args) {
        init();

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);

        PageName currentPage = PageName.LOGIN;
        while (true) {

            //Eğer kullanıcı giriş yapmadıysa Login Sayfasına Gönder
            if (DB.currentLoginedUser == null)
                currentPage = PageName.LOGIN;

            //Yüklenecek sayfanın gösterilmesi
            PageBase page = pages.get(currentPage);

            //Gidelecek Sayfanın Yetki isteyip istemediği ve Kullanıcının bu yetkiye sahip olup olmadığı kotrolü yapılıyor.
            boolean isEmployeeAndRequiredAuth = page.isRequiredAuth() &&
                    (userService.getLoggedUser().getUserType() == UserType.EMPLOYEE);
            if (isEmployeeAndRequiredAuth) {
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
         * Dependcy injection constructor kullanıldı
         * bağımlılıklar bir yerden oluşturulup dağıtılıyor.
         * Veritabanı katmanı sadece Servis katmanı ile iletişime geçiyor.
         * */
        UserRepository userRepository = new UserRepository();
        ProductRepository productRepository = new ProductRepository();
        CartItemRepository cartItemRepository = new CartItemRepository();
        OrderRepository orderIRepository = new OrderRepository();

        /**
         * Burada da Page katmanını Dependency injection constructor methodu kullandık
         * ProductService productServicee katmanı sadece servis katmanı ile haberleşiyor. Veritabını katmanı ile haberleşmiyor.
         * */
        UserService userService = new UserService(userRepository);
        ProductService productService = new ProductService(productRepository, cartItemRepository);
        OrderService orderService = new OrderService(userRepository, productRepository, cartItemRepository, orderIRepository);
        CartService cartService = new CartService(productRepository, cartItemRepository);
        /**
         * Sayfaların Adresleri ile Gösterilecek Sınıfın bağlanması
         * */
        pages.put(PageName.LOGIN, new LoginPage(userService));
        pages.put(PageName.HOME, new HomePage(userService));
        pages.put(PageName.PRODUCT_SALE, new ProductSalePage(userService, productService, cartService));
        pages.put(PageName.USER_CREATE, new UserCreatePage(userService));
        pages.put(PageName.PRODUCT_CREATE, new ProductCreatePage(productService));
        pages.put(PageName.USER_LIST, new UserListPage(userService));
        pages.put(PageName.PRODUCT_LIST, new ProductListPage(productService));
        pages.put(PageName.TEST, new TestPage(userService, productService, cartService, orderService));
        pages.put(PageName.ORDER, new OrderPage(userService, productService, orderService,cartService));
        pages.put(PageName.ORDER_VIEW, new OrderViewPage(userService, orderService));

        /**
         * Varsayılan ürünlerin girilmesi
         */
        productService.createProduct(new Product("gofret", 2, 50));
        productService.createProduct(new Product("gazoz", 1, 50));
        productService.createProduct(new Product("pirinç", 10, 20));
        productService.createProduct(new Product("ekmek", 2, 30));
        productService.createProduct(new Product("kek", 2, 60));
        productService.createProduct(new Product("mercimek", 14, 20));

        /**
         * Varsayılan kullanıcıların bilgilerinin oluşturulması
         */

        User userAdmin = new User("admin admin", "admin", "adminadmin", UserType.ADMIN);
        User user = new User("user user", "useruser", "useruser", UserType.EMPLOYEE);
        User userCustomer = new User("customer customer", "custom", "custom", UserType.CUSTOMER);

        userService.createUser(userAdmin);
        userService.createUser(user);
        userService.createUser(userCustomer);

    }

}
