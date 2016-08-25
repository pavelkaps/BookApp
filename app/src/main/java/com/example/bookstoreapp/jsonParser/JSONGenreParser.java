package com.example.bookstoreapp.jsonParser;

import android.util.Log;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.items.DictionaryMagazine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 23.08.2016.
 */
public class JSONGenreParser<T> implements JSONParser<T> {
    private static final String TAG = "JSONGenreParser";

    @Override
    public List<T> downloadCollection(String url) {
        List<DictionaryBook> items = new ArrayList<>();
        try {
            String jsonString = ConnectToNetwork.loadDataFromServer(url);
            Log.i(TAG, jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject genreJsonObject = jsonArray.getJSONObject(i);
                DictionaryBook item = new DictionaryBook();

                item.Id = genreJsonObject.getString("_id");
                item.Title = genreJsonObject.getString("title");

                JSONArray bookArray = genreJsonObject.getJSONArray("books");

                for(int j=0;i< bookArray.length();i++) {
                    String bookId = bookArray.getString(i);
                    JSONParser<BookItem> bookParser = new JSONBookParser<>();
                    List<BookItem> oneBook = bookParser.downloadCollection(URL_KEY.COLLECTIONS_BOOK + "/" + bookId);
                    item.listItem.add(oneBook.get(0));
                }
                Log.i(TAG, "addItem");
                items.add(item);
            }
        }catch(JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return (List<T>) items;
    }
}
