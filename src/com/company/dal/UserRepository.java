package com.company.dal;

import com.company.models.User;

import java.util.List;
import java.util.UUID;

/**
 * Kullanıcı veritabanı ile olan işleri yapıldığı sınıf
 */
public class UserRepository implements IRepository<User> {

    /**
     * Kullanıcı oluşturma
     *
     * @param user fdgdg
     * @return
     */
    @Override
    public void create(User user) {
        int size = DB.users.size();
        int newID = size == 0 ? 0 : DB.users.get(size - 1).getId() + 1;
        user.setId(newID);
        DB.users.add(user);

    }

    /**
     * Kullanıcı güncelleme
     *
     * @param user
     * @return
     */

    @Override
    public void update(User user) {
        User u = this.getById(user.getId());
        u.setNameSurname(user.getNameSurname());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());

    }

    /**
     * Kullanıcı Silme
     *
     * @param user
     * @return
     */
    @Override
    public void delete(User user) {
        DB.users.remove(user);
    }

    /**
     * Tüm Kullancılar
     *
     * @return
     */
    @Override
    public List<User> getAll() {
        return DB.users;
    }

    /**
     * id ile Kullanıcı bilgileri getirir
     *
     * @param id
     * @return
     */
    @Override
    public User getById(int id) {
        return DB.users.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public User getLoginedUser() {
        return DB.currentLoginedUser;
    }
}
