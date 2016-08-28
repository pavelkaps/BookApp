package com.example.bookstoreapp.Synchronaisers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bookstoreapp.items.DictionaryMagazine;
import com.example.bookstoreapp.items.MagazineItem;
import com.example.bookstoreapp.loaders.IEntityLoader;
import com.example.bookstoreapp.loaders.TypeLoader;
import com.example.bookstoreapp.repository.DictionaryMagazineRepository;

import java.util.List;

/**
 * Created by Паша on 23.08.2016.
 */
public class TypeSynchronizer<T> implements SynchronizeItems<T> {
    public static final String TAG = "TypeSynchronizer";

    @Override
    public void load() {
        new LoadItemsTask().execute();
    }

    @Override
    public void addToDataBase(List<T> items) {
        DictionaryMagazineRepository<T> repository = new DictionaryMagazineRepository<>();
        repository.allDelete();
        DictionaryMagazine newDictionary;

        for(T item : items){
            newDictionary = new DictionaryMagazine();
            DictionaryMagazine dictionary = (DictionaryMagazine) item;
            newDictionary.Id = dictionary.Id;
            newDictionary.Title = dictionary.Title;
            repository.insert((T)newDictionary);

            for(MagazineItem book : dictionary.listItem){
                newDictionary.listItem.add(book);
                repository.update((T)newDictionary);
            }
        }
    }

    private class LoadItemsTask extends AsyncTask<Void, Void, List<T>> {
        @Override
        protected List<T> doInBackground(Void... params) {
            IEntityLoader<DictionaryMagazine> loader = new TypeLoader<>();
            return (List<T>)loader.load();
        }

        @Override
        public void onPostExecute(List<T> result){
            super.onPostExecute(result);
            addToDataBase(result);
            Log.i(TAG, "TypeSynchroniser item: "+ result.size());
        }
    }


}
