package com.example.bookstoreapp.Synchronaisers;

import java.util.List;

/**
 * Created by Паша on 23.08.2016.
 */
public interface SynchronizeItems<T> {
    void load();
    void addToDataBase(List<T> items);
}
