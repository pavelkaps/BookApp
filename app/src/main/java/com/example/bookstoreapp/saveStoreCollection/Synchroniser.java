package com.example.bookstoreapp.saveStoreCollection;


import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.MainActivity;
import com.example.bookstoreapp.Synchronaisers.BookSynchronizer;
import com.example.bookstoreapp.Synchronaisers.SynchronizeItems;
import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.items.DictionaryMagazine;
import com.example.bookstoreapp.items.MagazineItem;
import com.example.bookstoreapp.observer.ItemObserver;
import com.example.bookstoreapp.observer.Subject;
import com.example.bookstoreapp.parser.XMLParser;
import com.example.bookstoreapp.repository.BookRepository;
import com.example.bookstoreapp.repository.DictionaryBookRepository;
import com.example.bookstoreapp.repository.DictionaryMagazineRepository;
import com.example.bookstoreapp.repository.MagazineRepository;
import com.example.bookstoreapp.typeFragment.BookFragment;
import com.example.bookstoreapp.typeFragment.MagazineFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.BookItemRealmProxy;
import io.realm.MagazineItemRealmProxy;

/**
 * Created by Паша on 05.07.2016.
 */
public class Synchroniser implements Subject{
    private static final String TAG = "Synchroniser";

    private boolean networkState;
    private Context mContext;
    private List<ItemObserver> mItemObservers;

    private static Synchroniser ourInstance = new Synchroniser();

    public static Synchroniser getInstance() {
        return ourInstance;
    }

    private Synchroniser() {
        mItemObservers = new ArrayList<>();
    }

    public void registerObserver(ItemObserver itemObserver){
        mItemObservers.add(itemObserver);
    }

    public void notifyObserver(){
        for(ItemObserver observer : mItemObservers){
            observer.update();
        }
    }

    public void load(Context context){
        List<SynchronizeItems> synchronizeItemsList = new ArrayList<>();
        BookSynchronizer<BookItem> synchronizer = new BookSynchronizer<>();
        synchronizeItemsList.add(synchronizer);

        mContext = context;
        networkState = ConnectToNetwork.hasConnection(mContext);
        if(networkState == true) {
            for (SynchronizeItems item : synchronizeItemsList){
                item.load();
            }
        }
    }

    /*private class LoadItemsTask extends AsyncTask<Void, Void, List<Category>> {
        @Override
        protected List<Category> doInBackground(Void... params) {
            List<Category> allCategory;
            allCategory = new ArrayList<>();

            XMLParser parser = new XMLParser();
            parser.downloadCollection(URL_KEY.COLLECTIONS, allCategory);
            aboutCollection.getInstance().sortCollection(allCategory);
            aboutCollection.getInstance().showLogCategory(allCategory);

                for (int i = 0; i < allCategory.size(); i++) {
                    for (int y = 0; y < allCategory.get(i).getTypeCollection().size(); y++) {
                        TypeItems type = allCategory.get(i).getTypeCollection().get(y);
                        parser.downloadItems(URL_KEY.PRODUCT, type.getId(), type, allCategory);
                    }
                }

            return allCategory;
        }

        @Override
        public void onPostExecute(List<Category> result){
            super.onPostExecute(result);
            deleteAllData();
            //addAllToData(result);

            Log.i(TAG, "Synchroniser item: "+ result.size());
            notifyObserver();
            aboutCollection.getInstance().showLogAllTypeItemsEntity(result);
        }
    }
    */



    /*
    public void addAllToData(List<Category> allCategory){
        for(int i = 0; i < allCategory.size(); i++){
            for (int j = 0; j < allCategory.get(i).getTypeCollection().size(); j++){
                if(allCategory.get(i).getTypeCollection().get(j).getParentId() == URL_KEY.BOOK_COLLECTION_ID) {

                    DictionaryBook itemDictionary = new DictionaryBook();
                    itemDictionary.Title = allCategory.get(i).getTypeCollection().get(j).getTitle();
                    itemDictionary.Id = allCategory.get(i).getTypeCollection().get(j).getId();

                    if(mDictionaryBookRepository.findById(itemDictionary.Id )== null){
                        Log.i(TAG, "..................IS NULL");
                        mDictionaryBookRepository.insert(itemDictionary);
                    }else{
                        Log.i(TAG, "..................NOT NULL");
                    }
                    for (int y = 0; y < allCategory.get(i).getTypeCollection().get(j).getItemCollection().size(); y++) {
                        BookItem storeItem = (BookItem) allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        mBookRepository.insert(storeItem);
                        try {
                            DictionaryBook realmItem = (DictionaryBook) mDictionaryBookRepository.findById(itemDictionary.Id);
                            realmItem.listItem.add(storeItem);
                            mDictionaryBookRepository.update(realmItem);
                        }catch (Exception ioe){
                            Log.i(TAG, ioe.toString());
                        }
                        Log.i(TAG, "Book add to Realm =)");
                    }
                }
                else if(allCategory.get(i).getTypeCollection().get(j).getParentId() == URL_KEY.MAGAZINE_COLLECTION_ID) {
                    DictionaryMagazine itemDictionary = new DictionaryMagazine();
                    itemDictionary.Title = allCategory.get(i).getTypeCollection().get(j).getTitle();
                    itemDictionary.Id = allCategory.get(i).getTypeCollection().get(j).getId();
                    if(mDictionaryMagazine.findById(itemDictionary.Id )== null){
                        Log.i(TAG, "..................IS NULL");
                        mDictionaryMagazine.insert(itemDictionary);
                    }else{
                        Log.i(TAG, "..................NOT NULL");
                    }
                    for (int y = 0; y < allCategory.get(i).getTypeCollection().get(j).getItemCollection().size(); y++) {
                        MagazineItem storeItem = (MagazineItem) allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        mMagazineRepository.insert(storeItem);
                        try {
                            DictionaryMagazine realmItem = (DictionaryMagazine) mDictionaryMagazine.findById(itemDictionary.Id);
                            realmItem.listItem.add(storeItem);
                            mDictionaryMagazine.update(realmItem);
                        }catch (Exception ioe){
                            Log.i(TAG, ioe.toString());
                        }
                        Log.i(TAG, "Magazine add to Realm =)");
                    }
                }
            }
        }
    }
    */

    /*
    public void fromRealmInAllCollection(List<Category> allCategory){
        Log.i(TAG, "fromRealmInAllCollection()");
        List<DictionaryBook> resultsDictionaryBook = mDictionaryBookRepository.allRead();
        List<DictionaryMagazine> resultsDictionaryMagazine = mDictionaryMagazine.allRead();

        Category categoryOfBook= new Category();
        categoryOfBook.setId(URL_KEY.BOOK_COLLECTION_ID);
        categoryOfBook.setTitle("Книги");

        Category categoryOfMagazine= new Category();
        categoryOfMagazine.setId(URL_KEY.MAGAZINE_COLLECTION_ID);
        categoryOfMagazine.setTitle("Журналы");

        allCategory.add(categoryOfBook);
        allCategory.add(categoryOfMagazine);

        for(int i = 0; i < allCategory.size(); i++){
            if (allCategory.get(i).getId() == URL_KEY.BOOK_COLLECTION_ID){

                for(DictionaryBook dictionaryBook : resultsDictionaryBook){
                    TypeItems<BookItem> newType = new TypeItems<>();
                    newType.setId(dictionaryBook.Id);
                    newType.setTitle(dictionaryBook.Title);
                    newType.setParentId(URL_KEY.BOOK_COLLECTION_ID);
                    Log.i(TAG, dictionaryBook.Title);

                    for(int j = 0; j < dictionaryBook.listItem.size(); j++){
                        Log.i(TAG, dictionaryBook.listItem.get(j).getTitle());
                        newType.getItemCollection().add((BookItem)dictionaryBook.listItem.get(j));
                    }
                    allCategory.get(i).getTypeCollection().add(newType);

                }
            }else if (allCategory.get(i).getId() == URL_KEY.MAGAZINE_COLLECTION_ID){
                for(DictionaryMagazine dictionaryMagazine : resultsDictionaryMagazine){

                    TypeItems<MagazineItem> newType = new TypeItems<>();
                    newType.setId(dictionaryMagazine.Id);
                    newType.setTitle(dictionaryMagazine.Title);
                    newType.setParentId(URL_KEY.MAGAZINE_COLLECTION_ID);
                    Log.i(TAG, dictionaryMagazine.Title);

                    for(int j = 0; j < dictionaryMagazine.listItem.size(); j++){
                        Log.i(TAG, dictionaryMagazine.listItem.get(j).getClass().toString());
                        newType.getItemCollection().add(dictionaryMagazine.listItem.get(j));
                    }
                    allCategory.get(i).getTypeCollection().add(newType);
                }
            }
        }
    }
    */

/*    public void logAllDataInRealm(){
        Log.i(TAG, "logAllDataInRealm()");
        List<BookItem> resultsBook = mBookRepository.allRead();
        List<MagazineItem> resultsMagazine = mMagazineRepository.allRead();
        List<DictionaryBook> resultsDictionaryBook = mDictionaryBookRepository.allRead();
        List<DictionaryMagazine> resultsDictionaryMagazine = mDictionaryMagazine.allRead();

        for (BookItem item : resultsBook) {
            item.showLogEntity();
        }
        for (MagazineItem item : resultsMagazine) {
            item.showLogEntity();
        }
        for (DictionaryBook item : resultsDictionaryBook) {
            for(BookItem book : item.listItem){
                Log.i(TAG, item.Title + " has " + book.getTitle());
            }
        }
        for (DictionaryMagazine item : resultsDictionaryMagazine) {
            for(MagazineItem book : item.listItem){
                Log.i(TAG, item.Title + " has " + book.getTitle());
            }
        }

    }
    */

}

