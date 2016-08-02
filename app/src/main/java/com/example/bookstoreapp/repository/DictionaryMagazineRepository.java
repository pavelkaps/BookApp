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
 * Created by Паша on 19.07.2016.
 */
public class DictionaryMagazineRepository<T> implements IEntityRepository<T> {
    public static final String TAG = "DictionaryMagazineRepository";

    Realm realm;

    public DictionaryMagazineRepository(Realm realm){
        this.realm = realm;
    }
    public DictionaryMagazineRepository(){
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void insert(final T entity) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.i(TAG, "Create");
                DictionaryMagazine newDictionaryMagazine = realm.createObject(DictionaryMagazine.class);
                DictionaryMagazine item = (DictionaryMagazine) entity;
                newDictionaryMagazine.Id = item.Id;
                newDictionaryMagazine.Title = item.Title;
                Log.i(TAG, "Commit");
            }
        });
    }

    @Override
    public T findById(int id) {
        DictionaryMagazine results = realm.where(DictionaryMagazine.class).equalTo("Id", id).findFirst();
        if(results != null) {
            DictionaryMagazine item = new DictionaryMagazine();
            item.Id = results.Id;
            item.Title = results.Title;

            for (MagazineItem bookItem : results.listItem) {
                item.listItem.add(bookItem);
            }

            Log.i(TAG, "It*s OK");
            return (T) item;
        }
        Log.i(TAG, "return RESULT");
        return (T) results;
    }

    @Override
    public List<T> allRead() {
        RealmResults<DictionaryMagazine> resultsDictionary = realm.where(DictionaryMagazine.class).findAll();
        List<DictionaryMagazine> allDictionaryItem = new ArrayList<>();
        for(DictionaryMagazine item: resultsDictionary){
            allDictionaryItem.add(item);
        }
        return (List<T>) allDictionaryItem;
    }

    @Override
    public void allDelete() {
        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                realm.delete(DictionaryMagazine.class);

            };
        });

    }

    @Override
    public void update(final T item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DictionaryMagazine magazineItem = (DictionaryMagazine) item;
                DictionaryMagazine result = realm.where(DictionaryMagazine.class).equalTo("Id", magazineItem.Id).findFirst();

                Log.i(TAG, magazineItem.listItem.last().getTitle() );
                result.listItem.add(magazineItem.listItem.last());
            }
        });
    }
}

