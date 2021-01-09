package com.company.dal;

import java.util.List;

public interface IRepository<T> {
    int Create(T t);
    int Update(T t);
    int Delete(T t);
    List<T> GetAll();
    T GetById(int id);
}
