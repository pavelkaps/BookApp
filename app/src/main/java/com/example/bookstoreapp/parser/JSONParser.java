package com.example.bookstoreapp.parser;

import android.util.Log;

import com.example.bookstoreapp.ConnectToNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 05.07.2016.
 */
public interface JSONParser<T>{
    List<T> downloadCollection();
}
