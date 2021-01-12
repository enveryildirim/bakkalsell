package com.company.services;

import com.company.dal.IRepository;
import com.company.models.user.User;

public class UserService {

    protected IRepository<User> userRepository;
    public UserService(IRepository<User> userRepository){
        this.userRepository=userRepository;
    }

    public int updateProfile(User user){
        return 0;
    }

    public int changePassword(User user){
        return 0;
    }

    public int getUser(int id){
        return 0;
    }


}
