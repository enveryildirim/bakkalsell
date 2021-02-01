package com.company.pages;

import com.company.models.PageName;

import java.util.Scanner;

/**
 * Kullanıcıdan veri almak oluşturulacak PAge sınıflarının miras alacağı
 * Bağımlılıkların yönetildiği
 * Page sınıflarında olması gereken işlemlerin tanımlandığı sınıf
 */
public abstract class PageBase {

    protected Scanner inputData = new Scanner(System.in);


    /**
     * Sayfa kullanıcının giriş yapması  gerekip gerekmediğini döner
     *
     * @return boolean giriş gerekiyorsa true gerekmiyorsa false
     */
    public abstract boolean isRequiredAuth();

    /**
     * Kullanıcıdan veri almak, veri göstermek ve Sayfa yönlendirmeleri yapar
     *
     * @return PageName gidilecek sayfasıyı döner
     */
    public abstract PageName render();

}
