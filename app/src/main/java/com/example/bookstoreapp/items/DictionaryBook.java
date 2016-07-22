package com.example.bookstoreapp.items;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Паша on 18.07.2016.
 */
public class DictionaryBook extends RealmObject {
        @PrimaryKey
        public int Id;
        public String Title;

        public RealmList<BookItem> listItem;

        public DictionaryBook(){
            listItem = new RealmList<>();
        }
    }

