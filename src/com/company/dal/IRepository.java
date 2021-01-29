package com.company.dal;

import java.util.List;
import java.util.UUID;

/**
 * Uygulamamızın Veritabanı ile işlem yapacak sınıfların işlemlerinin belirlendiği ve implemente edecekleri interface
 * @param <T> Veritabanında Hangi veri model sınıfı kullanacaksa o parametre olarak verilir
 */
public interface IRepository<T> {

    void create(T t);

    void update(T t);

    void delete(T t);

    List<T> getAll();

    T getById(int id);
}
