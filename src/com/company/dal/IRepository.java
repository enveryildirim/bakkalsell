package com.company.dal;

import java.util.List;
import java.util.UUID;

public interface IRepository<T> {

    void create(T t);
    void update(T t);
    void delete(T t);
    List<T> getAll();
    T getById(int id);
}
