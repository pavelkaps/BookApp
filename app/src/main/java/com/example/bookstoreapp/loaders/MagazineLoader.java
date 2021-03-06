package com.example.bookstoreapp.loaders;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.MagazineItem;
import com.example.bookstoreapp.jsonParser.IEntityJSONParser;
import com.example.bookstoreapp.jsonParser.JSONMagazineParser;

import java.util.List;

/**
 * Created by Паша on 28.08.2016.
 */
public class MagazineLoader<T> implements IEntityLoader<T> {
    @Override
    public List<T> load() {
        String json = ConnectToNetwork.loadDataFromServer(URL_KEY.COLLECTIONS_MAGAZINE);
        IEntityJSONParser<MagazineItem> parser = new JSONMagazineParser<>();
        return (List<T>)parser.parse(json);
    }
}
