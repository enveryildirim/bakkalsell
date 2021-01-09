package com.company.models;

import com.company.models.user.Account;
import com.company.models.user.Profile;
import com.company.models.user.User;

public class Employee extends User {

    public Employee(int id, Profile profile, Account account) {
        super(id, profile, account);
    }
}
