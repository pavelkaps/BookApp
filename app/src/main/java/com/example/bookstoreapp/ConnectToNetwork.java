package com.example.bookstoreapp;

/**
 * Created by Паша on 04.07.2016.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


import org.apache.commons.codec.binary.Base64;

public class ConnectToNetwork {
    private static final String TAG = "ConnectToUrl";
    public static String loadDataFromServer(String stringUrl){
        try {
            Log.i(TAG , "Start");

            /*String authString = URL_KEY.API_NUMBER + ":" + URL_KEY.PASSWORD;
            Log.i(TAG,"auth string: " + authString);
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);
            Log.i(TAG,"Base64 encoded auth string: " + authStringEnc);*/

            URL url = new URL(stringUrl);
            URLConnection urlConnection = url.openConnection();
            //urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();

            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }

            String result = sb.toString();
            Log.i(TAG, result);
            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean hasConnection(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }
}
