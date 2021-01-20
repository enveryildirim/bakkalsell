package com.company.dal;

import com.company.models.CartItem;
import com.company.models.Product;
import com.company.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * veri tutan sınıf
 * */

public class DB {
    public static List<User> users = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    public static User currentLoginedUser =null;
    public static List<CartItem> cart = new ArrayList<>();
}
