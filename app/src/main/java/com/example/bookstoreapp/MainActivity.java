package com.example.bookstoreapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.bookstoreapp.fragment.BookStoreFragment;
import com.example.bookstoreapp.fragment.LocalLibraryFragment;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.items.DictionaryMagazine;
import com.example.bookstoreapp.items.MagazineItem;
import com.example.bookstoreapp.repository.BookRepository;
import com.example.bookstoreapp.repository.DictionaryBookRepository;
import com.example.bookstoreapp.repository.DictionaryMagazineRepository;
import com.example.bookstoreapp.repository.MagazineRepository;
import com.example.bookstoreapp.saveStoreCollection.Synchroniser;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "BookStoreActivity";
    private static final String FIRST_START = "firstStartApp";

    RealmConfiguration realmConfig;
    Realm realm;

    public static BookRepository<BookItem> sBookItemBookRepository;
    public static DictionaryBookRepository<DictionaryBook> sDictionaryBookRepository;
    public static DictionaryMagazineRepository<DictionaryMagazine> sDictionaryMagazineRepository;
    public static MagazineRepository<MagazineItem> sMagazineRepository;

    private boolean isFirstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            isFirstStart = savedInstanceState.getBoolean(FIRST_START);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(isFirstStart == true){
            Fragment fragment = new BookStoreFragment();
            onReplaceFragment(fragment);
            navigationView.getMenu().getItem(0).setChecked(true);
            isFirstStart = false;
        }

        realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();

        sBookItemBookRepository = new BookRepository<>(realm);
        sDictionaryBookRepository = new DictionaryBookRepository<>(realm);
        sDictionaryMagazineRepository = new DictionaryMagazineRepository<>(realm);
        sMagazineRepository = new MagazineRepository<>(realm);
        Log.i(TAG, "Realm");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void onReplaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.main_container, fragment)
                .commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        if (id == R.id.nav_camera) {
            fragment = new BookStoreFragment();
            onReplaceFragment(fragment);
        } else if (id == R.id.nav_gallery) {
            fragment = new LocalLibraryFragment();
            onReplaceFragment(fragment);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.synchronized_items) {
            Synchroniser.getInstance().load(getApplicationContext());
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(FIRST_START, isFirstStart);
    }

}
