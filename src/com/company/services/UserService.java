package com.company.services;

import com.company.dal.DB;
import com.company.dal.IRepository;
import com.company.models.User;

/**
 * Kasiyer ve Admin kullanıcısının yapacağı işlemerin yapıldığı sınıf
 */
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

    public boolean logout(){
        return true;
    }

    public boolean createUser(User user){

        userRepository.create(user);
        return false;
    }
    public boolean createUser(String nameSurname,String username,String password){
        int size=userRepository.getAll().size();
        int newID=userRepository.getAll().get(size-1).getId()+1;
        User user = new User(newID,nameSurname,username,password,0);
        userRepository.create(user);
        return false;
    }
    /**
     * Kullanıcı  bilgilerini güncelleme
     * @param user
     */

    public boolean updateUser(User user){
        this.userRepository.update(user);
        return false;
    }

    public boolean deleteUser(User user){
        return false;
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
        return null;
    }


}
