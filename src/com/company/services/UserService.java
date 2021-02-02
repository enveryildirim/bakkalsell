package com.company.services;

import com.company.RegexConstant;
import com.company.dal.DB;
import com.company.dal.UserRepository;
import com.company.models.ICheckable;
import com.company.models.IResult;
import com.company.models.User;
import com.company.models.Result;

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
        } else
            return false;
    }

    public Result<User> createUserResult(User user) {
        Result<User> result = this.isValidUserResult(user);
        if (result.isSuccess()) {
            userRepository.create(user);
            result.setMessage("Kullanıcı Başarıyla Kayıt Edildi");
            result.setSuccess(true);
        }
        return result;
    }

    public void updateUser(User user) {
        this.userRepository.update(user);
    }

    public Result<User> updateUserResult(User user) {
        Result<User> result = this.isValidUserResult(user);
        if (result.isSuccess()) {
            userRepository.update(user);
            result.setMessage("Kullanıcı Başarıyla Güncellendi");
            result.setSuccess(true);
        }
        return result;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public Result<User> deleteUserResult(User user) {
        userRepository.delete(user);
        Result<User> result = new Result<>(true, "Kullanıcı başarılı şekilde silindi", user);
        return result;
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

    public Result<User> isValidUserResult(User user) {
        List<IResult<User>> checkList = new ArrayList<>();

        IResult<User> checkEmpty = (model) -> {
            Result<User> userEmptyResult = new Result<>(true, "Kontrol Edildi", model);
            if (model.getNameSurname().isEmpty()) {
                userEmptyResult.setSuccess(false);
                userEmptyResult.setMessage("Kullanıcı Adı Boş olamaz");
            }
            if (model.getUsername().isEmpty()) {
                userEmptyResult.setSuccess(false);
                userEmptyResult.setMessage("Kullanıcı Adı Boş olamaz");
            }
            if (model.getPassword().isEmpty()) {
                userEmptyResult.setSuccess(false);
                userEmptyResult.setMessage("Şifre Boş olamaz");
            }
            return userEmptyResult;
        };

        IResult<User> checkUsername = (model) -> {
            Result<User> userEmptyResult = new Result<>(true, "Kullanıcı Adı Uygun", model);
            if (model.getUsername().length() < 5) {
                userEmptyResult.setSuccess(false);
                userEmptyResult.setMessage("Kullanıcı Adı 5 karakterden az olamaz");
            }
            return userEmptyResult;
        };

        IResult<User> checkPassword = (model) -> {
            Result<User> userEmptyResult = new Result<>(true, "Şifre Uygun", model);
            if (model.getPassword().length() < 6) {
                userEmptyResult.setSuccess(false);
                userEmptyResult.setMessage("Şifre 6 karakterden az olamaz");
            }
            return userEmptyResult;
        };


        checkList.add(checkEmpty);
        checkList.add(checkUsername);
        checkList.add(checkPassword);

        Result<User> userEmptyResult = new Result<>(true, "Model Uygun", user);
        for (IResult<User> checker : checkList) {
            Result<User> sonuc = checker.run(user);
            if (sonuc.isSuccess())
                continue;
            else {
                return checker.run(user);
            }
        }
        return userEmptyResult;
    }
}
