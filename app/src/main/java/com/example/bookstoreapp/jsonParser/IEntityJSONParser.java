package com.example.bookstoreapp.jsonParser;

import java.util.List;

/**
 * Created by Паша on 05.07.2016.
 */
public interface IEntityJSONParser<T>{
    List<T> parse(String url);
}
