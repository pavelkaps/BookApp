package com.example.bookstoreapp.saveStoreCollection;

import com.example.bookstoreapp.items.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 05.07.2016.
 */
public class TypeItems<T> {
    private int id;
    private String title;
    private int parentId;

    List<T> itemCollection;

    public TypeItems() {
        itemCollection = new ArrayList<T>();
        parentId = 0;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<T> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(List<T> itemCollection) {
        this.itemCollection = itemCollection;
    }

}
