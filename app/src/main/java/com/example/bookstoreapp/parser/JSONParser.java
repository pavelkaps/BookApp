package com.example.bookstoreapp.parser;

/**
 * Created by Паша on 05.07.2016.
 */
public class JSONParser extends ConnectionParser {
    public static final String TAG ="ConnectionParser";

    @Override
    public void downloadCollection(String url) {
        //List<T> items = new ArrayList<>();

            //String jsonString = ConnectToNetwork.loadDataFromServer();
            //Log.i(TAG, "Received JSON: " + jsonString);
            //JSONObject jsonBody = new JSONObject(jsonString);

            //JSONObject photosJsonObject /= jsonBody.getJSONObject("photos");
            //JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

            //for (int i = 0; i < photoJsonArray.length(); i++) {
                //JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
               /* T item = (T) object;*/

               /* item.setId(photoJsonObject.getString("id"));
                item.setCaption(photoJsonObject.getString("title"));

                if (!photoJsonObject.has("url_s")) {
                    continue;
                }

                item.setUrl(photoJsonObject.getString("url_s"));
                item.setOwner(photoJsonObject.getString("owner"));
                items.add(item);*/



       /* } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }*///} catch (JSONException je) {
           // Log.e(TAG, "Failed to parse JSON", je);
    }

    @Override
    public void downloadItems(String url, int id) {

    }



}
