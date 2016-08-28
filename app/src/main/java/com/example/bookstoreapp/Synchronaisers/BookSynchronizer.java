package com.example.bookstoreapp.Synchronaisers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.loaders.BookLoader;
import com.example.bookstoreapp.loaders.IEntityLoader;
import com.example.bookstoreapp.repository.BookRepository;

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
    public void addToDataBase(List<T> items) {
        BookRepository<T> repository = new BookRepository<>();
        repository.allDelete();
        for(T item : items){
            repository.insert(item);
        }
    }

    private class LoadItemsTask extends AsyncTask<Void, Void, List<T>> {
        @Override
        protected List<T> doInBackground(Void... params) {
            IEntityLoader<BookItem> loader = new BookLoader<>();
            return (List<T>)loader.load();
        }

        @Override
        public void onPostExecute(List<T> result){
            super.onPostExecute(result);
            addToDataBase(result);
            Log.i(TAG, "BookSynchroniser item: "+ result.size());
        }
    }


}
