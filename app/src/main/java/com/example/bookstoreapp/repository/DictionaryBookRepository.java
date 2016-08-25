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
public class DictionaryBookRepository<T> implements IEntityRepository<T> {
    public static final String TAG = "DictionaryBookRepository";

    Realm realm;

    public DictionaryBookRepository(Realm realm){
        this.realm = realm;
    }
    public DictionaryBookRepository(){
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void insert(final T entity) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.i(TAG, "Create");
                DictionaryBook newDictionaryBook = realm.createObject(DictionaryBook.class);
                DictionaryBook item = (DictionaryBook) entity;
                newDictionaryBook.Id = item.Id;
                newDictionaryBook.Title = item.Title;
                Log.i(TAG, "Commit");
            }
        });

    }

    @Override
    public T findById(int id) {
            DictionaryBook results = realm.where(DictionaryBook.class).equalTo("Id", id).findFirst();
            if(results != null) {
                DictionaryBook item = new DictionaryBook();
                item.Id = results.Id;
                item.Title = results.Title;

                for (BookItem bookItem : results.listItem) {
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
        RealmResults<DictionaryBook> resultsDictionary = realm.where(DictionaryBook.class).findAll();
        List<DictionaryBook> allDictionaryItem = new ArrayList<>();
        for(DictionaryBook item: resultsDictionary){
            allDictionaryItem.add(item);
        }
        return (List<T>) allDictionaryItem;
    }

    @Override
    public void allDelete() {

        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                realm.delete(DictionaryBook.class);

            };
        });

    }

    @Override
    public void update(final T item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DictionaryBook bookItem = (DictionaryBook) item;
                DictionaryBook result = realm.where(DictionaryBook.class).equalTo("Id", bookItem.Id).findFirst();
                Log.i(TAG,bookItem.listItem.last().getTitle() );
                result.listItem.add(bookItem.listItem.last());
            }
        });

    }
}
