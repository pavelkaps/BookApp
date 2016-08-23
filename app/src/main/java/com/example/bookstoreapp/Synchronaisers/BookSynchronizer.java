package com.example.bookstoreapp.Synchronaisers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.parser.JSONBookParse;
import com.example.bookstoreapp.parser.JSONParser;
import com.example.bookstoreapp.parser.XMLParser;
import com.example.bookstoreapp.repository.BookRepository;
import com.example.bookstoreapp.saveStoreCollection.Category;
import com.example.bookstoreapp.saveStoreCollection.TypeItems;
import com.example.bookstoreapp.saveStoreCollection.aboutCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 23.08.2016.
 */
public class BookSynchronizer<T> implements SynchronizeItems<T> {
    public static final String TAG = "BookSynchronizer";

    @Override
    public void load() {
        new LoadItemsTask().execute();
    }

    @Override
    public void addToData(List<T> items) {
        BookRepository<T> repository = new BookRepository<>();
        for(T item : items){
            repository.insert(item);
        }
    }

    @Override
    public void deleteData() {
        BookRepository<T> repository = new BookRepository<>();
        repository.allDelete();
    }

    private class LoadItemsTask extends AsyncTask<Void, Void, List<T>> {
        @Override
        protected List<T> doInBackground(Void... params) {
            JSONParser<T> parser = new JSONBookParse<>();
            return parser.downloadCollection();
        }

        @Override
        public void onPostExecute(List<T> result){
            super.onPostExecute(result);
            deleteData();
            addToData(result);
            Log.i(TAG, "BookSynchroniser item: "+ result.size());
        }
    }


}
