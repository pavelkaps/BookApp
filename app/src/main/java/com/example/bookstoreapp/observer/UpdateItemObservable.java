package com.example.bookstoreapp.observer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 24.07.2016.
 */
public class UpdateItemObservable {
    public static final String TAG = "UpdateItemObservable";
    private List<ItemObserver> mItemObservers;

    private static UpdateItemObservable ourInstance = new UpdateItemObservable();

    public static UpdateItemObservable getInstance() {
        return ourInstance;
    }

    private UpdateItemObservable() {
        mItemObservers = new ArrayList<>();
    }

    public void registerObserver(ItemObserver itemObserver){
        mItemObservers.add(itemObserver);
    }

    public void notifyObserver(){
        Log.i(TAG, "Notify item: "+ mItemObservers.size());
        for(ItemObserver observer : mItemObservers){
            observer.update();
        }
    }
}
