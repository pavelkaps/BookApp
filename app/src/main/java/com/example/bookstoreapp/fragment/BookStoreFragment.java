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
import com.example.bookstoreapp.observer.Subject;
import com.example.bookstoreapp.parser.XMLParser;
import com.example.bookstoreapp.saveStoreCollection.Synchroniser;
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

    List<ItemObserver> observers ;
    public BookStoreFragment(){
        observers = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "ONCREATE");

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


}
