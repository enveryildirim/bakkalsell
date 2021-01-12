package com.company.dal;

import com.company.models.user.Account;
import com.company.models.user.User;

public class AccountRepository<T extends User>{

    public User login(Account account){
        System.out.printf("Login işlemi yapıldı");
        return null;
    };

    public int logout(Account account){
        System.out.printf("Logout işlemi yapıldı");
        return 0;
    }
}
