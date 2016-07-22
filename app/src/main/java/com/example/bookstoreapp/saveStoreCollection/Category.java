package com.example.bookstoreapp.saveStoreCollection;

import com.example.bookstoreapp.items.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 06.07.2016.
 */
public class Category {
    private int id;
    private String title;
    private int parentId;

    List<TypeItems> typeCollection;

    public Category() {
        typeCollection = new ArrayList<TypeItems>();
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

    public List<TypeItems> getTypeCollection() {
        return typeCollection;
    }
}
