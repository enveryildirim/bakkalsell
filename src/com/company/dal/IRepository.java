package com.company.dal;

import java.util.List;

public interface IRepository<T> {
    int create(T t);
    int update(T t);
    int delete(T t);
    List<T> getAll();
    T getById(int id);
}
