package com.company.dal;
import com.company.models.user.User;

import java.util.List;

/**
 * Kullanıcı veritabanı ile olan işleri yapıldığı sınıf
 */
public class UserRepository implements IRepository<User>{

    /**
     * Kullanıcı oluşturma
     * @param user
     */
    @Override
    public void create(User user) {
        DB.users.add(user);
        System.out.println("User oluşturuldu = "+user.getProfile().getName());

    }

    /**
     * Kullanıcı güncelleme
     * @param user
     */
    @Override
    public void update(User user) {

    }

    /**
     * Kullanıcı Silme
     * @param user
     */
    @Override
    public void delete(User user) {

    }

    /**
     * Tüm Kullancılar
     * @return
     */
    @Override
    public List<User> getAll() {
        return null;
    }

    /**
     * id ile Kullanıcı bilgileri getirir
     * @param id
     * @return
     */
    @Override
    public User getById(int id) {
        return null;
    }
}
