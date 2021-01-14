package com.company.services;

import com.company.dal.IRepository;
import com.company.models.user.User;

/**
 * Kasiyer ve Admin kullanıcısının yapacağı işlemerin yapıldığı sınıf
 */
public class UserService {

    protected IRepository<User> userRepository;

    public UserService(IRepository<User> userRepository){
        this.userRepository=userRepository;
    }

    /**
     * Kullanıcı profil bilgilerini güncelleme
     * @param user
     */
    public void updateProfile(User user){

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
