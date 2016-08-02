package com.example.bookstoreapp.repository;

import android.util.Log;

import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.items.DictionaryMagazine;
import com.example.bookstoreapp.items.MagazineItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Паша on 09.07.2016.
 */
public class MagazineRepository<T> implements IEntityRepository<T> {
    public static final String TAG = "MagazineRepository";

    Realm realm;

    public MagazineRepository(Realm realm){
        this.realm = realm;
    }
    public MagazineRepository(){
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void insert(final T entity) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.i(TAG, "bEGIN");
                Log.i(TAG, "Create");
                MagazineItem newMagazine = realm.createObject(MagazineItem.class);
                MagazineItem item = (MagazineItem) entity;
                newMagazine.setId(item.getId());
                newMagazine.setTitle(item.getTitle());
                newMagazine.setDescription(item.getDescription());
                newMagazine.setEdition(item.getEdition());
                newMagazine.setСirculation(item.getСirculation());
                newMagazine.setImageUrl(item.getImageUrl());
                newMagazine.setPublisher(item.getPublisher());
                newMagazine.setRating(item.getRating());
                Log.i(TAG, "Commit");
            }
        });
    }

    @Override
    public T findById(int id) {
        MagazineItem results = realm.where(MagazineItem.class).equalTo("id", id).findFirst();
        return (T) results;
    }

    @Override
    public List<T> allRead() {
        RealmResults<MagazineItem> resultsDictionary = realm.where(MagazineItem.class).findAll();
        List<MagazineItem> allItem = new ArrayList<>();
        for(MagazineItem item: resultsDictionary){
            allItem.add(item);
        }
        return (List<T>) allItem;
    }

    @Override
    public void allDelete() {
        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                realm.delete(MagazineItem.class);

            };
        });
    }

    @Override
    public void update(T item) {

    }
}
