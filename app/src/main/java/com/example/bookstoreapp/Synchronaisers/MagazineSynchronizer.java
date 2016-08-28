package com.example.bookstoreapp.Synchronaisers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bookstoreapp.items.MagazineItem;
import com.example.bookstoreapp.loaders.IEntityLoader;
import com.example.bookstoreapp.loaders.MagazineLoader;
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
    public void addToDataBase(List<T> items) {
        MagazineRepository<T> repository = new MagazineRepository<>();
        repository.allDelete();
        for(T item : items){
            repository.insert(item);
        }
    }

    private class LoadItemsTask extends AsyncTask<Void, Void, List<T>> {
        @Override
        protected List<T> doInBackground(Void... params) {
            IEntityLoader<MagazineItem> loader = new MagazineLoader<>();
            return (List<T>)loader.load();
        }

        @Override
        public void onPostExecute(List<T> result){
            super.onPostExecute(result);
            addToDataBase(result);
            Log.i(TAG, "MagazineSynchroniser item: "+ result.size());
        }
    }

}
