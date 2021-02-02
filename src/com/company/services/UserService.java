package com.company.services;

import com.company.RegexConstant;
import com.company.dal.DB;
import com.company.dal.UserRepository;
import com.company.models.ICheckable;
import com.company.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Kullanıcılar ile alakalı işlemlerin yapıldığı sınıf
 */
public class UserService {

    protected UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Kullanıcın sisteme giriş yapmasını sağlar. Eğer başarılı ise true değlse false değer döner
     *
     * @param username kullanıcı adı
     * @param password şifre
     * @return boolean işlem başarı durumunu döner.
     */
    public boolean login(String username, String password) {
        boolean isSuccessful = false;
        User user = this.userRepository.getAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        DB.currentLoginedUser = user;

        if (user != null)
            isSuccessful = true;

        return isSuccessful;
    }

    /**
     * Giriş yapan kullanıcı çıkış yapar
     */
    public void logout() {
        DB.currentLoginedUser = null;
    }

    public boolean createUser(User user) {
        if (this.isValidUser(user)) {
            userRepository.create(user);
            return true;
        }else
            return false;
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
     *
     * @return User login olmuş kullanıcı
     */
    public User getLoggedUser() {
        return userRepository.getLoggedUser();
    }

    public boolean isValidUser(User user) {
        List<ICheckable<User>> checkList = new ArrayList<>();
        ICheckable<User> checkEmpty = (model) -> !model.getNameSurname().isEmpty() && !model.getUsername().isEmpty()
                && !model.getPassword().isEmpty();

        ICheckable<User> checkUsername = (model) -> model.getUsername().length() >= 5;
        ICheckable<User> checkPassword = (model) -> model.getPassword().length() >= 6;
        ICheckable<User> checkPasswordRegex = (model) -> model.getPassword().matches(RegexConstant.PASSWORD);

        checkList.add(checkEmpty);
        checkList.add(checkUsername);
        checkList.add(checkPassword);
        checkList.add(checkPasswordRegex);

        AtomicBoolean isChecked = new AtomicBoolean(false);
        for (ICheckable<User> checker : checkList) {
            if (checker.chech(user))
                isChecked.set(true);
            else {
                isChecked.set(false);
                break;
            }
        }
        return isChecked.get();
    }
}
