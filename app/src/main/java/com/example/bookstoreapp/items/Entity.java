package com.example.bookstoreapp.items;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Паша on 05.07.2016.
 */
public abstract class Entity {

    private int Id;

    private String Title;

    private String Publisher;

    private String Description;

    private int Сirculation;

    private int Rating;

    private List<String> ImageUrl;

    public Entity(){
        ImageUrl = new ArrayList<String>();
    }

    private DictionaryBook dictionary;

    public int getId() {
        return Id;
    }

    public List<String> getImageUrl() {
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

    public void setId(int id) {
        Id = id;
    }

    public void setImageUrl(List<String> imageUrl) {
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

    public abstract void showLogEntity();
}
