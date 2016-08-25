package com.example.bookstoreapp.Synchronaisers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.items.DictionaryMagazine;
import com.example.bookstoreapp.items.MagazineItem;
import com.example.bookstoreapp.jsonParser.JSONGenreParser;
import com.example.bookstoreapp.jsonParser.JSONParser;
import com.example.bookstoreapp.jsonParser.JSONTypeParser;
import com.example.bookstoreapp.repository.DictionaryBookRepository;
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
    public void addToData(List<T> items) {
        DictionaryMagazineRepository<T> repository = new DictionaryMagazineRepository<>();
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

    @Override
    public void deleteData() {
        DictionaryMagazineRepository<T> repository = new DictionaryMagazineRepository<>();
        repository.allDelete();
    }

    private class LoadItemsTask extends AsyncTask<Void, Void, List<T>> {
        @Override
        protected List<T> doInBackground(Void... params) {
            JSONParser<T> parser = new JSONTypeParser<>();
            return parser.downloadCollection(URL_KEY.COLLECTIONS_TYPE);
        }

        @Override
        public void onPostExecute(List<T> result){
            super.onPostExecute(result);
            deleteData();
            addToData(result);
            Log.i(TAG, "TypeSynchroniser item: "+ result.size());
        }
    }


}
