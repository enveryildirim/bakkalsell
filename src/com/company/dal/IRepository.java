package com.company.dal;

import java.util.List;

/**
 * Uygulamamızın Veritabanı ile işlem yapacak sınıfların işlemlerinin belirlendiği ve implemente edecekleri interface
 *
 * @param <T> Veritabanında Hangi veri model sınıfı kullanacaksa o parametre olarak verilir
 */
public interface IRepository<T> {

    boolean create(T t);

    boolean update(T t);

    boolean delete(T t);

    List<T> getAll();

    T getById(int id);
}
