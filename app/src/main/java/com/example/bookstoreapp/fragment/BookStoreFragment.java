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

import com.example.bookstoreapp.MainActivity;
import com.example.bookstoreapp.R;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.items.DictionaryMagazine;
import com.example.bookstoreapp.items.MagazineItem;
import com.example.bookstoreapp.parser.XMLParser;
import com.example.bookstoreapp.saveStoreCollection.AllCollection;
import com.example.bookstoreapp.saveStoreCollection.TypeItems;
import com.example.bookstoreapp.URL_KEY;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Паша on 01.07.2016.
 */
public class BookStoreFragment extends Fragment {
    private static final String TAG = "BookStoreFragment";
    Realm realm;

    boolean networkState;

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


    private class StoreItemsTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "EXECUTE");

            XMLParser parser = new XMLParser();
            Log.i(TAG, "Start Parse");
            parser.downloadCollection(URL_KEY.COLLECTIONS);
            Log.i(TAG, "DownloadCollections");

            for(int i = 0; i < AllCollection.allCategory.size(); i++){
                for(int y = 0; y < AllCollection.allCategory.get(i).getTypeCollection().size(); y++){
                    TypeItems type = AllCollection.allCategory.get(i).getTypeCollection().get(y);
                    Log.i(TAG, "Download item");
                    parser.downloadItems(URL_KEY.PRODUCT, type.getId());
                }
            }
            AllCollection.showLogAllTypeItemsEntity();
            return null;
        }

        @Override
        public void onPostExecute(Void result){
            super.onPostExecute(result);
            deleteAllData();
            addAllToData();
            logAllDataInRealm();
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
                        Log.i(TAG, "1");
                        BookItem storeItem = (BookItem) AllCollection.allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        Log.i(TAG, "2");

                        Log.i(TAG, "3");
                        MainActivity.sBookItemBookRepository.insert(storeItem);
                        Log.i(TAG, "4");
                        try {
                            DictionaryBook realmItem = MainActivity.sDictionaryBookRepository.findById(itemDictionary.Id);
                            Log.i(TAG, "6");
                            realmItem.listItem.add(storeItem);
                            Log.i(TAG, "7");
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
                        Log.i(TAG, "1");
                        MagazineItem storeItem = (MagazineItem) AllCollection.allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        Log.i(TAG, "2");
                        Log.i(TAG, "3");
                        MainActivity.sMagazineRepository.insert(storeItem);
                        Log.i(TAG, "4");
                        try {
                            DictionaryMagazine realmItem = MainActivity.sDictionaryMagazineRepository.findById(itemDictionary.Id);
                            Log.i(TAG, "6");
                            realmItem.listItem.add(storeItem);
                            Log.i(TAG, "7");
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
