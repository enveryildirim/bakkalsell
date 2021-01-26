package com.company.services;

import com.company.dal.DB;
import com.company.dal.IRepository;
import com.company.dal.UserRepository;
import com.company.models.User;

import java.util.List;
import java.util.UUID;

public class UserService {

    protected IRepository<User> userRepository;

    public UserService(IRepository<User> userRepository){
        this.userRepository=userRepository;
    }

    public boolean login(String username,String password){
       User user = this.userRepository.getAll().stream()
                .filter(u->u.getUsername().equals(username) && u.getPassword().equals(password))
               .findFirst()
               .orElse(null);
        DB.currentLoginedUser=user;
        return user==null?false:true;
    }

    public void logout(){

    }

    public void createUser(User user){
        userRepository.create(user);

    }
    public void createUser(String nameSurname,String username,String password,String typeUser){
        int size=userRepository.getAll().size();
        int newID=userRepository.getAll().get(size-1).getId()+1;
        User user = new User(newID,nameSurname,username,password,Integer.parseInt(typeUser));
        userRepository.create(user);

    }
    /**
     * Kullanıcı  bilgilerini güncelleme
     * @param user
     */

    public void updateUser(User user){
        this.userRepository.update(user);

    }

    public void deleteUser(User user){
         userRepository.delete(user);
    }


    /**
     * Kullanıcı şifre değiştirme
     * @param user
     */
    public void changePassword(User user){

    }

    /**
     * Kullanıcı İd ye göre Kullanıcı bilgilerini döndürür
     * @param id
     * @return
     */
    public User getUser(int id){
        return userRepository.getById(id);
    }

    public List<User> getAll(){
        return userRepository.getAll();
    }

    public User getLoginedUser(){
        return new UserRepository().getLoginedUser();
    }

}
