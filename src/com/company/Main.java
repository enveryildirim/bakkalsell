package com.company;

import com.company.dal.UserRepository;
import com.company.models.user.User;
import com.company.services.AdminService;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //System.out.printf("Deneme branchi için yazıldı");

        //UserRepository userRepository= new UserRepository();
        //userRepository.Create(new User(1));

        AdminService adminService = new AdminService(new UserRepository());

    }
}
