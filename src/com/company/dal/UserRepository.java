package com.company.dal;

import com.company.models.User;

import java.util.List;

/**
 * Kullanıcı veritabanı ile olan işlerin yapıldığı sınıf
 */
public class UserRepository implements IRepository<User> {

    @Override
    public void create(User user) {

        if (DB.users.isEmpty()) {
            user.setId(0);
        }
        else {
            int userListSize =DB.users.size();
            User lastUser=DB.users.get(userListSize-1);
            int newID =  lastUser.getId() + 1;
            user.setId(newID);
        }

        DB.users.add(user);

    }

    @Override
    public void update(User user) {
        User u = this.getById(user.getId());
        u.setNameSurname(user.getNameSurname());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());

    }

    @Override
    public void delete(User user) {
        DB.users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return DB.users;
    }

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
