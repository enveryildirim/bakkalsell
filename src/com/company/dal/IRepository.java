package com.company.dal;

import java.util.List;
import java.util.UUID;

public interface IRepository<T> {
    //todo gerekli olmayan fonksiyonlar return değerleri void  olarak güncelenecek
    boolean create(T t);
    boolean update(T t);
    boolean delete(T t);
    List<T> getAll();
    T getById(int id);
}
