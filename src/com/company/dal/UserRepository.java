package com.company.dal;

import com.company.models.User;

import java.util.List;

/**
 * Kullanıcı veritabanı ile olan işlerin yapıldığı sınıf
 */
public class UserRepository implements IRepository<User> {

    @Override
    public boolean create(User user) {

        if (DB.users.isEmpty()) {
            user.setId(1);
        } else {
            int userListSize = DB.users.size();
            User lastUser = DB.users.get(userListSize - 1);
            int newID = lastUser.getId() + 1;
            user.setId(newID);
        }

        return DB.users.add(user);

    }

    @Override
    public boolean update(User user) {
        User updatingUser = this.getById(user.getId());
        updatingUser.setNameSurname(user.getNameSurname());
        updatingUser.setUsername(user.getUsername());
        updatingUser.setPassword(user.getPassword());
        return true;

    }

    @Override
    public boolean delete(User user) {
        return DB.users.remove(user);
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

    public User getLoggedUser() {
        return DB.currentLoginedUser;
    }
}
