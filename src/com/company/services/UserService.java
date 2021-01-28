package com.company.services;

import com.company.dal.DB;
import com.company.dal.IRepository;
import com.company.dal.UserRepository;
import com.company.models.User;


import java.util.List;


public class UserService {

    protected IRepository<User> userRepository;

    public UserService(IRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Kullanıcın sisteme giriş yapmasını sağlar. Eğer başarılı ise true değlse false değer döner
     * @param username kullanıcı adı
     * @param password şifre
     * @return boolean işlem başarı durumunu döner.
     */
    public boolean login(String username, String password) {

        User user = this.userRepository.getAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        DB.currentLoginedUser = user;
        return user != null;
    }

    /**
     * Giriş yapan kullanıcı çıkış yapar
     */
    public void logout() {
        DB.currentLoginedUser = null;
    }

    public void createUser(User user) {
        userRepository.create(user);
    }


    public void updateUser(User user) {
        this.userRepository.update(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }


    public User getUser(int id) {
        return userRepository.getById(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    /**
     * Sisteme giriş yapan kullanıcının bilgilerini getirir. Eğer giriş yapan kullanınıcı yoksa null değer döner
     * @return User login olmuş kullanıcı
     */
    public User getLoginedUser() {
        return ((UserRepository) userRepository).getLoginedUser();
    }

}
