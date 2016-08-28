package com.example.bookstoreapp.loaders;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.jsonParser.IEntityJSONParser;
import com.example.bookstoreapp.jsonParser.JSONBookParser;

import java.util.List;

/**
 * Created by Паша on 28.08.2016.
 */
public class BookLoader<T> implements IEntityLoader<T> {
    @Override
    public List<T> load() {
        String json = ConnectToNetwork.loadDataFromServer(URL_KEY.COLLECTIONS_BOOK);
        IEntityJSONParser<BookItem> parser = new JSONBookParser<>();
        return (List<T>)parser.parse(json);
    }
}
