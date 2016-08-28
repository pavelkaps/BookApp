package com.example.bookstoreapp.loaders;

import java.util.List;

/**
 * Created by Паша on 28.08.2016.
 */
public interface IEntityLoader<T> {
    List<T> load();
}
