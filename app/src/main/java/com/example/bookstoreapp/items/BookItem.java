package com.example.bookstoreapp.items;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Паша on 02.07.2016.
 */
public class BookItem extends RealmObject{
    @Ignore
    private static final String TAG = "BookItem";
    public String Author;
    public int Year;
    @PrimaryKey
    private String Id;
    private String Title;
    private String Publisher;
    private String Description;
    private int Сirculation;
    private int Rating;
    private String ImageUrl;


    public String getId() {
        return Id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public int getRating() {
        return Rating;
    }

    public int getСirculation() {
        return Сirculation;
    }

    public String getDescription() {
        return Description;
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getTitle() {
        return Title;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public void setСirculation(int сirculation) {
        Сirculation = сirculation;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public int getYear() {
        return Year;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setYear(int year) {
        Year = year;
    }

    public void showLogEntity() {
        Log.i(TAG, "id: " + getId());
        Log.i(TAG, "title: " + getTitle());
        Log.i(TAG, "publisher: "+ getPublisher());
        Log.i(TAG, "description: " + getDescription());
        Log.i(TAG, "circulation: " + getСirculation());
        Log.i(TAG, "rating: "+ getRating());
        Log.i(TAG, "one image url: " + getImageUrl());
        Log.i(TAG, "author: " + Author);
        Log.i(TAG, "year: " + Year);

    }
}
