package com.company.dal;

import java.util.List;
import java.util.UUID;

public interface IRepository<T> {
    boolean create(T t);
    boolean update(T t);
    boolean delete(T t);
    List<T> getAll();
    T getById(int id);
}
