package com.company.services;

import com.company.dal.DB;
import com.company.dal.UserRepository;
import com.company.models.IResult;
import com.company.models.Result;
import com.company.models.User;

import java.util.ArrayList;
import java.util.List;

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

    public Result<User> createUser(User user) {
        Result<User> validationResult = this.isValidUser(user);
        if (validationResult.isSuccess()) {
            boolean isSuccessful = userRepository.create(user);
            Result<User> creationResult = new Result<>(isSuccessful, "Kullanıcı Başarıyla Kayıt Edildi", user);
            return creationResult;
        }
        return validationResult;
    }

    public Result<User> updateUser(User user) {
        Result<User> validationResult = this.isValidUser(user);
        if (validationResult.isSuccess()) {
            boolean isSuccessful = userRepository.update(user);
            Result<User> updatingResult = new Result<>(isSuccessful, "Kullanıcı Başarıyla Güncellendi", user);
            return updatingResult;
        }
        return validationResult;
    }


    public Result<User> deleteUser(User user) {
        boolean isSuccessful = userRepository.delete(user);
        Result<User> deletingResult = new Result<>(isSuccessful, "Kullanıcı başarılı şekilde silindi", user);
        return deletingResult;
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

    /**
     * Kullanıcı verilerinin önceden yazılan kurallara uygun olup olmadığı komtrol eder.
     * @param user  User tipinde kontrol edilecek nesneyi alır
     * @return Result<User> işlem ile alakalı bilgileri döner(Başarılı mı,Mesaj,Model ) bilgilerini
     */
    public Result<User> isValidUser(User user) {
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
