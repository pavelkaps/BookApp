package com.example.bookstoreapp.repository;


import android.content.Context;
import android.util.Log;

import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.items.DictionaryMagazine;
import com.example.bookstoreapp.items.MagazineItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Паша on 09.07.2016.
 */
public class BookRepository<T> implements IEntityRepository<T> {
    public static final String TAG = "BookRepository";

    Realm realm;

    public BookRepository(Realm realm){
        this.realm = realm;
    }

    @Override
    public void insert(final T entity) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.i(TAG, "bEGIN");
                Log.i(TAG, "Create");
                BookItem newBook = realm.createObject(BookItem.class);
                BookItem item = (BookItem) entity;
                newBook.setId(item.getId());
                newBook.setTitle(item.getTitle());
                newBook.setDescription(item.getDescription());
                newBook.setYear(item.getYear());
                newBook.setСirculation(item.getСirculation());
                newBook.setAuthor(item.Author);
                newBook.setImageUrl(item.getImageUrl());
                newBook.setPublisher(item.getPublisher());
                newBook.setRating(item.getRating());
                Log.i(TAG, "Commit");
            }
        });
    }

    @Override
    public T findById(int id) {
        BookItem results = realm.where(BookItem.class).equalTo("Id", id).findFirst();
        return (T) results;
    }

    @Override
    public List<T> allRead() {
        RealmResults<BookItem> resultsBook = realm.where(BookItem.class).findAll();
        List<BookItem> allBookItem = new ArrayList<>();
        for(BookItem item: resultsBook){
            allBookItem.add(item);
        }
        return (List<T>) allBookItem;
    }

    @Override
    public void allDelete() {
        realm.executeTransaction(new Realm.Transaction() {

            @Override
                public void execute(Realm realm) {
                    realm.delete(BookItem.class);

                };
            });
    }

    @Override
    public void update(T item) {

    }
}
