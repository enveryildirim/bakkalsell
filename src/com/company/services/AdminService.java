package com.company.services;

import com.company.dal.IRepository;
import com.company.dal.UserRepository;
import com.company.models.user.User;

public class AdminService extends UserService{

    public AdminService(IRepository<User> userRepository){
        super(userRepository);
    }
    public int CreateUser(User user){
        userRepository.Create(user);
        return 0;
    }

    public int UpdateUser(User user){
        return 0;
    }
    public int DeleteUser(User user){
        return 0;
    }
    public int GetAllUser(){
        return 0;
    }
    public int GetUserById(int id){
        return 0;
    }


}
