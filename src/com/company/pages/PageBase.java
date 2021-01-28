package com.company.pages;

import com.company.models.PageName;
import com.company.services.OrderService;
import com.company.services.ProductService;
import com.company.services.UserService;

import java.util.Scanner;

/**
 * Kullanıcıdan veri almak oluşturulacak PAge sınıflarının miras alacağı
 * Bağımlılıkların yönetildiği
 * Page sınıflarında olması gereken işlemlerin tanımlandığı sınıf
 */
public abstract class PageBase {

    protected UserService userService;
    protected ProductService productService;
    protected Scanner in = new Scanner(System.in);
    protected OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public PageBase(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    /**
     * Sayfa kullanıcının giriş yapması  gerekip gerekmediğini döner
     * @return boolean giriş gerekiyorsa true gerekmiyorsa false
     */
    public abstract boolean isRequiredAuth();

    /**
     * Kullanıcıdan veri almak, veri göstermek ve Sayfa yönlendirmeleri yapar
     * @return PageName gidilecek sayfasıyı döner
     */
    public abstract PageName render();

}
