package com.company.services;

import com.company.dal.IRepository;
import com.company.models.user.User;

public class UserService {

    protected IRepository<User> userRepository;
    public UserService(IRepository<User> userRepository){
        userRepository=userRepository;
    }

    public int UpdateProfile(User user){
        return 0;
    }

    public int ChangePassword(User user){
        return 0;
    }

    public int GetUser(int id){
        return 0;
    }


}
