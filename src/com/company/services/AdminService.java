package com.company.services;

import com.company.dal.IRepository;
import com.company.models.user.User;

public class AdminService extends UserService{

    public AdminService(IRepository<User> userRepository){
        super(userRepository);
    }
    public int createUser(User user){
        this.userRepository.create(user);
        return 0;
    }

    public int updateUser(User user){
        return 0;
    }
    public int deleteUser(User user){
        return 0;
    }
    public int getAllUser(){
        return 0;
    }
    public int getUserById(int id){
        return 0;
    }


}
