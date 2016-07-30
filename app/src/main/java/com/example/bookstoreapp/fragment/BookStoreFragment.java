package com.example.bookstoreapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.MainActivity;
import com.example.bookstoreapp.R;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.items.DictionaryMagazine;
import com.example.bookstoreapp.items.MagazineItem;
import com.example.bookstoreapp.observer.ItemObserver;
import com.example.bookstoreapp.observer.UpdateItemObservable;
import com.example.bookstoreapp.parser.XMLParser;
import com.example.bookstoreapp.saveStoreCollection.AllCollection;
import com.example.bookstoreapp.saveStoreCollection.Category;
import com.example.bookstoreapp.saveStoreCollection.TypeItems;
import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.typeFragment.BookFragment;
import com.example.bookstoreapp.typeFragment.MagazineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 01.07.2016.
 */
public class BookStoreFragment extends Fragment {
    private static final String TAG = "BookStoreFragment";
    boolean networkState;

    List<ItemObserver> observers ;
    public BookStoreFragment(){
        observers = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "ONCREATE");
        new StoreItemsTask().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Книги"));
        tabLayout.addTab(tabLayout.newTab().setText("Журналы"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }



    private class StoreItemsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "EXECUTE");
            networkState = ConnectToNetwork.hasConnection(getContext());
            Log.i(TAG, "Connection is - " + networkState);
            if(networkState == true) {
                XMLParser parser = new XMLParser();
                parser.downloadCollection(URL_KEY.COLLECTIONS);
                Log.i(TAG, "DownloadCollections");
                for (int i = 0; i < AllCollection.allCategory.size(); i++) {
                    for (int y = 0; y < AllCollection.allCategory.get(i).getTypeCollection().size(); y++) {
                        TypeItems type = AllCollection.allCategory.get(i).getTypeCollection().get(y);
                        Log.i(TAG, "Download item");
                        parser.downloadItems(URL_KEY.PRODUCT, type.getId());
                    }
                }
            }

            while (BookFragment.onCreate == false){};
            while (MagazineFragment.onCreate == false){};
            return null;
        }

        @Override
        public void onPostExecute(Void result){
            super.onPostExecute(result);
            if(networkState == false){
                fromRealmInAllCollection();
            }else {
                deleteAllData();
                addAllToData();
            }
            Log.i(TAG, "AllCollection item: "+AllCollection.allCategory.size());
            UpdateItemObservable.getInstance().notifyObserver();
            AllCollection.showLogAllTypeItemsEntity();
        }
    }

    public void addAllToData(){
        for(int i = 0; i < AllCollection.allCategory.size(); i++){
            for (int j = 0; j < AllCollection.allCategory.get(i).getTypeCollection().size(); j++){
                if(AllCollection.allCategory.get(i).getTypeCollection().get(j).getParentId() == URL_KEY.BOOK_COLLECTION_ID) {

                    DictionaryBook itemDictionary = new DictionaryBook();
                    itemDictionary.Title = AllCollection.allCategory.get(i).getTypeCollection().get(j).getTitle();
                    itemDictionary.Id = AllCollection.allCategory.get(i).getTypeCollection().get(j).getId();

                    if(MainActivity.sDictionaryBookRepository.findById(itemDictionary.Id )== null){
                        Log.i(TAG, "..................IS NULL");
                        MainActivity.sDictionaryBookRepository.insert(itemDictionary);
                    }else{
                        Log.i(TAG, "..................NOT NULL");
                    }
                    for (int y = 0; y < AllCollection.allCategory.get(i).getTypeCollection().get(j).getItemCollection().size(); y++) {
                        BookItem storeItem = (BookItem) AllCollection.allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        MainActivity.sBookItemBookRepository.insert(storeItem);
                        try {
                            DictionaryBook realmItem = MainActivity.sDictionaryBookRepository.findById(itemDictionary.Id);
                            realmItem.listItem.add(storeItem);
                            MainActivity.sDictionaryBookRepository.update(realmItem);
                        }catch (Exception ioe){
                            Log.i(TAG, ioe.toString());
                        }
                        Log.i(TAG, "Book add to Realm =)");
                    }
                }
               else if(AllCollection.allCategory.get(i).getTypeCollection().get(j).getParentId() == URL_KEY.MAGAZINE_COLLECTION_ID) {
                    DictionaryMagazine itemDictionary = new DictionaryMagazine();
                    itemDictionary.Title = AllCollection.allCategory.get(i).getTypeCollection().get(j).getTitle();
                    itemDictionary.Id = AllCollection.allCategory.get(i).getTypeCollection().get(j).getId();
                    if(MainActivity.sDictionaryMagazineRepository.findById(itemDictionary.Id )== null){
                        Log.i(TAG, "..................IS NULL");
                        MainActivity.sDictionaryMagazineRepository.insert(itemDictionary);
                    }else{
                        Log.i(TAG, "..................NOT NULL");
                    }
                    for (int y = 0; y < AllCollection.allCategory.get(i).getTypeCollection().get(j).getItemCollection().size(); y++) {
                        MagazineItem storeItem = (MagazineItem) AllCollection.allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        MainActivity.sMagazineRepository.insert(storeItem);
                        try {
                            DictionaryMagazine realmItem = MainActivity.sDictionaryMagazineRepository.findById(itemDictionary.Id);
                            realmItem.listItem.add(storeItem);
                            MainActivity.sDictionaryMagazineRepository.update(realmItem);
                        }catch (Exception ioe){
                            Log.i(TAG, ioe.toString());
                        }
                        Log.i(TAG, "Magazine add to Realm =)");
                    }
                }
            }
        }
    }

    public void deleteAllData(){
        MainActivity.sBookItemBookRepository.allDelete();
        MainActivity.sDictionaryBookRepository.allDelete();
        MainActivity.sDictionaryMagazineRepository.allDelete();
        MainActivity.sMagazineRepository.allDelete();
        Log.i(TAG, "Storage realm is clear =(((");
    }

    public void fromRealmInAllCollection(){
        Log.i(TAG, "fromRealmInAllCollection()");
        List<DictionaryBook> resultsDictionaryBook = MainActivity.sDictionaryBookRepository.allRead();
        List<DictionaryMagazine> resultsDictionaryMagazine = MainActivity.sDictionaryMagazineRepository.allRead();

        Category categoryOfBook= new Category();
        categoryOfBook.setId(URL_KEY.BOOK_COLLECTION_ID);
        categoryOfBook.setTitle("Книги");

        Category categoryOfMagazine= new Category();
        categoryOfMagazine.setId(URL_KEY.MAGAZINE_COLLECTION_ID);
        categoryOfMagazine.setTitle("Журналы");

        AllCollection.allCategory.add(categoryOfBook);
        AllCollection.allCategory.add(categoryOfMagazine);

        for(int i = 0; i < AllCollection.allCategory.size(); i++){
            if (AllCollection.allCategory.get(i).getId() == URL_KEY.BOOK_COLLECTION_ID){

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
                    AllCollection.allCategory.get(i).getTypeCollection().add(newType);

                }
            }else if (AllCollection.allCategory.get(i).getId() == URL_KEY.MAGAZINE_COLLECTION_ID){
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
                    AllCollection.allCategory.get(i).getTypeCollection().add(newType);
                }
            }
        }
        //Log.i(TAG, "Size AllCollection: " + AllCollection.allCategory.size());
        //AllCollection.showLogAllTypeItemsEntity();
    }

    public void logAllDataInRealm(){
        Log.i(TAG, "logAllDataInRealm()");
        List<BookItem> resultsBook = MainActivity.sBookItemBookRepository.allRead();
        List<MagazineItem> resultsMagazine = MainActivity.sMagazineRepository.allRead();
        List<DictionaryBook> resultsDictionaryBook = MainActivity.sDictionaryBookRepository.allRead();
        List<DictionaryMagazine> resultsDictionaryMagazine = MainActivity.sDictionaryMagazineRepository.allRead();

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

}
