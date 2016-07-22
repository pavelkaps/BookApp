package com.example.bookstoreapp.parser;

import java.util.List;

/**
 * Created by Паша on 05.07.2016.
 */
public abstract class ConnectionParser {
   public abstract void downloadCollection(String url);
   public abstract void downloadItems(String url, int id);
}
