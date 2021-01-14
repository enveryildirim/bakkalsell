package com.company.services;

import com.company.dal.IRepository;
import com.company.models.user.User;

import java.util.List;

/**
 * Admin yapabilceği işleri yapıldığı sınıf
 */
public class AdminService extends UserService{

    public AdminService(IRepository<User> userRepository){
        super(userRepository);
    }

    /**
     *Kullanıcı Oluşturma
     * @param user
     * @return
     */
    public void createUser(User user){
        this.userRepository.create(user);

    }

    /**
     * Kullanıcı Profil ve Account  bilgilerinin güncelleme
     * @param user
     */
    public void updateUser(User user){

    }

    /**
     * Kullanıcı Silme işlemi
     * @param user
     */
    public void deleteUser(User user){

    }

    /**
     * Tüm kullanıcıların listelenmesi
     * @return
     */
    public List<User> getAllUser(){
        return null;
    }

    /**
     * İd ile Kullanıcı bilgileri getirir
     * @param id
     * @return
     */
    public User getUserById(int id){
        return null;
    }


}
