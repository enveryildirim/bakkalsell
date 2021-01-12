package com.company.dal;
import com.company.models.user.User;

import java.util.List;

public class UserRepository implements IRepository<User>{

    @Override
    public int create(User user) {
        DB.users.add(user);
        System.out.println("User oluşturuldu = "+user.getProfile().getName());
        return 0;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(User user) {
        return 0;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }
}
