package com.company.test;

import com.company.models.Result;
import com.company.models.User;

interface IResult<T> {
    Result<T> run(T model);
}

public class TestMain {
    public static void main(String[] args) {
        IResult<User> checkUsername = (model) -> {
            if (model.getUsername().isEmpty()) {
                return new Result<>(false, "Kullanıcı Adı Boş", null);
            } else
                return new Result<>(true, "Kullanıcı Adı Doğru", null);
        };

        checkUsername.run(null);
    }
}





