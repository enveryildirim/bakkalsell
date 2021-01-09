package com.company.models;

import com.company.models.user.Account;
import com.company.models.user.Profile;
import com.company.models.user.User;

public class Admin extends User {

    public Admin(int id, Profile profile, Account account) {
        super(id, profile, account);
    }
}
