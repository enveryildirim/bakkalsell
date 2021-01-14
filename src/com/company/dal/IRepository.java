package com.company.dal;

import java.util.List;

public interface IRepository<T> {
    void create(T t);
    void update(T t);
    void delete(T t);
    List<T> getAll();
    T getById(int id);
}
