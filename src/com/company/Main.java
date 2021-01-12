package com.company;

import com.company.dal.UserRepository;
import com.company.models.Admin;
import com.company.models.user.Account;
import com.company.models.user.Profile;
import com.company.models.user.User;
import com.company.services.AdminService;

public class Main {


    public static void main(String[] args) {
	// write your code here
        //System.out.printf("Deneme branchi için yazıldı");

        //UserRepository userRepository= new UserRepository();
        //userRepository.Create(new User(1));

        AdminService adminService = new AdminService(new UserRepository());
        Account account=new Account();
        account.setUsername("username");
        account.setPassword("password");
        Profile profile =new Profile();
        profile.setEmail("user@mail.com");
        profile.setName("name");
        profile.setSurname("surname");
        User user = new Admin(0,profile,account);

        adminService.createUser(user);


        account.setUsername("admin");
        account.setPassword("password");
        profile.setEmail("admin@mail.com");
        profile.setName("admin");
        profile.setSurname("surname");
        Admin admin = new Admin(1,profile,account);

        adminService.createUser(admin);
        
    }
}
