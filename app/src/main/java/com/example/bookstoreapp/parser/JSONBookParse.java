package com.example.bookstoreapp.parser;

import android.util.Log;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 23.08.2016.
 */
public class JSONBookParse<T> implements JSONParser<T>{
    private static final String TAG = "JSONBookParse";

    public List<T> downloadCollection() {
        List<BookItem> items = new ArrayList<>();
        try {
            String jsonString = ConnectToNetwork.loadDataFromServer(URL_KEY.COLLECTIONS_BOOK);
            Log.i(TAG, jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);

            JSONObject jsonObject = jsonBody.getJSONObject("");
            JSONArray jsonArray = jsonObject.getJSONArray("");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bookJsonObject = jsonArray.getJSONObject(i);
                BookItem item = new BookItem();

                item.setId(bookJsonObject.getString("_id"));
                item.setTitle(bookJsonObject.getString("title"));
                item.setAuthor(bookJsonObject.getString("author"));
                item.setYear(Integer.parseInt(bookJsonObject.getString("year")));
                item.setPublisher(bookJsonObject.getString("publisher"));
                item.setDescription(bookJsonObject.getString("description"));
                item.setСirculation(Integer.parseInt(bookJsonObject.getString("circulation")));
                item.setRating(Integer.parseInt(bookJsonObject.getString("rating")));
                item.setImageUrl(bookJsonObject.getString("imageurl"));
                Log.i(TAG, "addItem");
                items.add(item);
            }
            }catch(JSONException je){
                Log.e(TAG, "Failed to parse JSON", je);
            }
        return (List<T>) items;
    }

    }
