package com.example.bookstoreapp.repository;

import java.util.List;

/**
 * Created by Паша on 09.07.2016.
 */
interface IEntityRepository<T> {
    void insert(T entity);
    T findById(int id);
    List<T> allRead();
    void allDelete();
    void update(T item);

}

