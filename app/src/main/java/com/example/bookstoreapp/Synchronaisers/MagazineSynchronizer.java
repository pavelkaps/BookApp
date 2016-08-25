package com.example.bookstoreapp.Synchronaisers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.jsonParser.JSONMagazineParser;
import com.example.bookstoreapp.jsonParser.JSONParser;
import com.example.bookstoreapp.repository.MagazineRepository;

import java.util.List;

/**
 * Created by Паша on 23.08.2016.
 */
public class MagazineSynchronizer<T> implements SynchronizeItems<T> {
    private static final String TAG = "MagazineSynchronizer";

    @Override
    public void load() {
        new LoadItemsTask().execute();
    }

    @Override
    public void addToData(List<T> items) {
        MagazineRepository<T> repository = new MagazineRepository<>();
        for(T item : items){
            repository.insert(item);
        }
    }

    @Override
    public void deleteData() {
        MagazineRepository<T> repository = new MagazineRepository<>();
        repository.allDelete();
    }

    private class LoadItemsTask extends AsyncTask<Void, Void, List<T>> {
        @Override
        protected List<T> doInBackground(Void... params) {
            JSONParser<T> parser = new JSONMagazineParser<>();
            return parser.downloadCollection(URL_KEY.COLLECTIONS_MAGAZINE);
        }

        @Override
        public void onPostExecute(List<T> result){
            super.onPostExecute(result);
            deleteData();
            addToData(result);
            Log.i(TAG, "MagazineSynchroniser item: "+ result.size());
        }
    }

}
