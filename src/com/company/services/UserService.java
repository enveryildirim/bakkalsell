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

    //todo fonksiyonların javadoc ne için yazıldığı ne döndüğü nsaıl döndüğü yazılacak niçin true dönüyor niye false geliyor new mana işfade ediyor
    public boolean login(String username, String password) {

        User user = this.userRepository.getAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        DB.currentLoginedUser = user;
        return user != null;
    }

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

    public User getLoginedUser() {
        return ((UserRepository) userRepository).getLoginedUser();
    }

}
