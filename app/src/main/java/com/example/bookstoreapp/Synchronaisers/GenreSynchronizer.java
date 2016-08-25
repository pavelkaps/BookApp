package com.example.bookstoreapp.Synchronaisers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.jsonParser.JSONBookParser;
import com.example.bookstoreapp.jsonParser.JSONGenreParser;
import com.example.bookstoreapp.jsonParser.JSONParser;
import com.example.bookstoreapp.repository.DictionaryBookRepository;
import com.example.bookstoreapp.repository.MagazineRepository;

import java.util.List;

/**
 * Created by Паша on 23.08.2016.
 */
public class GenreSynchronizer<T> implements SynchronizeItems<T> {
    public static final String TAG = "GenreSynchronizer";

    @Override
    public void load() {
        new LoadItemsTask().execute();
    }

    @Override
    public void addToData(List<T> items) {
        DictionaryBookRepository<T> repository = new DictionaryBookRepository<>();
        DictionaryBook newDictionary;

        for(T item : items){
            newDictionary = new DictionaryBook();
            DictionaryBook dictionary = (DictionaryBook) item;
            newDictionary.Id = dictionary.Id;
            newDictionary.Title = dictionary.Title;
            repository.insert((T)newDictionary);

            for(BookItem book : dictionary.listItem){
                newDictionary.listItem.add(book);
                repository.update((T)newDictionary);
            }
        }
    }

    @Override
    public void deleteData() {
        DictionaryBookRepository<T> repository = new DictionaryBookRepository<>();
        repository.allDelete();
    }

    private class LoadItemsTask extends AsyncTask<Void, Void, List<T>> {
        @Override
        protected List<T> doInBackground(Void... params) {
            JSONParser<T> parser = new JSONGenreParser<>();
            return parser.downloadCollection(URL_KEY.COLLECTIONS_GENRE);
        }

        @Override
        public void onPostExecute(List<T> result){
            super.onPostExecute(result);
            deleteData();
            addToData(result);
            Log.i(TAG, "GenreSynchroniser item: "+ result.size());
        }
    }

}
