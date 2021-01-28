package com.company;

import java.security.PublicKey;

/**
 * Uygulama geneli sabitlerin tanımlandığı sınıf
 */
public class Constant {
    //REGEX FORMATLARI
    public static String ONLY_DIGIT = "^[0-9]+$";
    public static String PASSWORD = "[A-Za-z0-9]{6,}$";
    public static String USERNAME = "[A-Za-z0-9]{5,}$";
    public static String NAME_SURNAME_TR="[a-zA-ZğüşöçıİĞÜŞÖÇ_ ]+$";
    public static String HOME_PAGE_COMMAND_LIST="[0123456]";
    public static String ORDER_PAGE_COMMAND_LIST="[0123]";
    public static String ORDER_PAGE_VIEW_COMMAND_LIST="[01]";
    public static String COMMAND_YES_NO="(evet|hayır)";
    public static String CUMLE_TR="[a-zA-ZğüşöçıİĞÜŞÖÇ_ ]+$";
    public static String PRODUCT_LIST_PAGE_COMMAND_LIST="[012]";
    public static String PRODUCT_SALE_PAGE_COMMAND_LIST="[0123]";
    public static String USER_CREATE_PAGE_USER_TYPE="[12]";
    public static String USER_LIST_PAGE_COMMAND_LIST="[012]";



    //
}
