package com.example.bookstoreapp.jsonParser;

import android.util.Log;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.items.MagazineItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 23.08.2016.
 */
public class JSONMagazineParser<T> implements IEntityJSONParser<T> {
    private static final String TAG = "JSONMagazineParser";

    @Override
    public List parce(String jsonString) {
        List<MagazineItem> items = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject magazineJsonObject = jsonArray.getJSONObject(i);
                MagazineItem item = new MagazineItem();

                item.setId(magazineJsonObject.getString("_id"));
                item.setTitle(magazineJsonObject.getString("title"));
                item.setEdition(Integer.parseInt(magazineJsonObject.getString("edition")));
                item.setPublisher(magazineJsonObject.getString("publisher"));
                item.setDescription(magazineJsonObject.getString("description"));
                item.setСirculation(Integer.parseInt(magazineJsonObject.getString("circulation")));
                item.setRating(Integer.parseInt(magazineJsonObject.getString("rating")));
                item.setImageUrl(magazineJsonObject.getString("imageurl"));
                Log.i(TAG, "addItem");
                items.add(item);
            }
        }catch(JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return (List<T>) items;
    }
}
