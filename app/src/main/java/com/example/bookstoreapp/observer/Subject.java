package com.example.bookstoreapp.observer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 24.07.2016.
 */
public interface Subject {
    void registerObserver(ItemObserver itemObserver);
    void notifyObserver();
}
