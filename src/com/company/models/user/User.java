package com.company.models.user;

public abstract class User {

    public User(int id, Profile profile, Account account) {
        this.id = id;
        this.profile = profile;
        this.account = account;
    }

    private int id;
    private Profile profile;
    private Account account;

    public int getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
