package com.company.dal;
import com.company.models.user.User;

import java.util.List;

public class UserRepository implements IRepository<User>{

    @Override
    public int Create(User user) {
        System.out.printf("User oluşturuldu");
        return 0;
    }

    @Override
    public int Update(User user) {
        return 0;
    }

    @Override
    public int Delete(User user) {
        return 0;
    }

    @Override
    public List<User> GetAll() {
        return null;
    }

    @Override
    public User GetById(int id) {
        return null;
    }
}
