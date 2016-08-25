package com.example.bookstoreapp.items;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Паша on 18.07.2016.
 */
public class DictionaryMagazine extends RealmObject {
    @PrimaryKey
    public String Id;
    public String Title;
    public RealmList<MagazineItem> listItem;
    public DictionaryMagazine(){
        listItem = new RealmList<>();
    }
}
