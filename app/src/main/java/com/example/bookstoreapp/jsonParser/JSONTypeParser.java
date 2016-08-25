package com.example.bookstoreapp.jsonParser;

import android.util.Log;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.items.DictionaryMagazine;
import com.example.bookstoreapp.items.MagazineItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 23.08.2016.
 */
public class JSONTypeParser<T> implements JSONParser<T>{
    private static final String TAG = "JSONTypeParser";

    @Override
    public List<T> downloadCollection(String url) {
        List<DictionaryMagazine> items = new ArrayList<>();
        try {
            String jsonString = ConnectToNetwork.loadDataFromServer(url);
            Log.i(TAG, jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject typeJsonObject = jsonArray.getJSONObject(i);
                DictionaryMagazine item = new DictionaryMagazine();

                item.Id = typeJsonObject.getString("_id");
                item.Title = typeJsonObject.getString("title");

                JSONArray magazineArray = typeJsonObject.getJSONArray("magazine");

                for(int j=0;i< magazineArray.length();i++) {
                    String magazineId = magazineArray.getString(i);
                    JSONParser<MagazineItem> magazineParser = new JSONMagazineParser<>();
                    List<MagazineItem> oneMagazine = magazineParser.downloadCollection(URL_KEY.COLLECTIONS_MAGAZINE + "/" + magazineId);
                    item.listItem.add(oneMagazine.get(0));
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
