package com.company.dal;
import com.company.models.User;

import java.util.List;

/**
 * Kullanıcı veritabanı ile olan işleri yapıldığı sınıf
 */
public class UserRepository implements IRepository<User>{

    /**
     * Kullanıcı oluşturma
     * @param user
     * @return
     */
    @Override
    public boolean create(User user) {

        System.out.println("User oluşturuldu = "+user.getNameSurname());

        return DB.users.add(user);

    }

    /**
     * Kullanıcı güncelleme
     * @param user
     * @return
     */

    @Override
    public boolean update(User user) {
        User u = this.getById(user.getId());
        u.setNameSurname(user.getNameSurname());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
       return true;
    }

    /**
     * Kullanıcı Silme
     * @param user
     * @return
     */
    @Override
    public boolean delete(User user) {
       return DB.users.remove(user);
    }

    /**
     * Tüm Kullancılar
     * @return
     */
    @Override
    public List<User> getAll() {
        return DB.users;
    }

    /**
     * id ile Kullanıcı bilgileri getirir
     * @param id
     * @return
     */
    @Override
    public User getById(int id) {
        return DB.users.stream()
                .filter(p-> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
