package com.example.bookstoreapp.xmlParser;

import android.net.Uri;
import android.util.Log;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.MagazineItem;
import com.example.bookstoreapp.saveStoreCollection.Category;
import com.example.bookstoreapp.saveStoreCollection.TypeItems;
import com.example.bookstoreapp.saveStoreCollection.aboutCollection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Паша on 05.07.2016.
 */
public class XMLParser{
    private static final String TAG = "XMLParser";

    public void downloadCollection(String urls, List<Category> allCategory) {
        try {
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(ConnectToNetwork.loadDataFromServer(urls)));
            doc = db.parse(is);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("collection");

            Category category;
            int parentId = 0;
            boolean hasParent = true;

            for (int i = 0; i < nodeList.getLength(); i++) {
                category = new Category();
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                try {
                    parentId = Integer.parseInt(getElementByTag(fstElmnt,"id"));
                    category.setId(parentId);
                    category.setTitle(getElementByTag(fstElmnt,"title"));
                    category.setParentId(Integer.parseInt(getElementByTag(fstElmnt,"parent-id")));

                    Log.i(TAG, "id: " + category.getId() +
                            " title: " + category.getTitle() +
                            " parent-id: " + category.getParentId());

            }catch (Exception ioe){
                    aboutCollection.getInstance().idParent = parentId;
                    hasParent = false;
            }
                if(hasParent == true){
                    allCategory.add(category);
                }
                hasParent = true;
            }

        } catch (Exception e) {
            Log.i(TAG, "XML Parsing Exception " + e.toString());
        }
    }

    public void downloadItems(String _url, int id, TypeItems type, List<Category> allCollection) {
       try {
           Uri ENDPOINT = Uri
                   .parse(_url)
                   .buildUpon()
                   .appendQueryParameter("collection_id", String.valueOf(id))
                   .build();

           try {
               Document doc = null;
               DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
               DocumentBuilder db = dbf.newDocumentBuilder();
               InputSource is = new InputSource();
               is.setCharacterStream(new StringReader(ConnectToNetwork.loadDataFromServer(ENDPOINT.toString())));
               doc = db.parse(is);
               doc.getDocumentElement().normalize();

               NodeList nodeList = doc.getElementsByTagName("product");

               Log.i(TAG,"Start for");

               int idParent  = aboutCollection.getInstance().searchParentIdOnTypeItems(id, allCollection);

               boolean isBook = false;
               boolean isMagazine = false;

               if(idParent == URL_KEY.BOOK_COLLECTION_ID){
                   isBook = true;
               }else{
                   isMagazine = true;
               }

               Log.i(TAG, "Book =" + isBook);
               Log.i(TAG, "Magazine =" + isMagazine);
               for (int i = 0; i < nodeList.getLength(); i++) {
                   int idItems = 0;
                   String titleItems = "N/A";
                   String publisherItems = "N/A";
                   String descriptionItems = "N/A";
                   int ratingItems = 0;
                   int circulationItems = 0;
                   String imageUrl = "N/A";
                   String authorItems = "N/A";
                   int yearItems = 0;
                   int editionItems = 0;

                   Node node = nodeList.item(i);
                   Element fstElmnt = (Element) node;

                   try {
                       idItems = Integer.parseInt(getElementByTag(fstElmnt, "id"));
                       titleItems = getElementByTag(fstElmnt,"title");
                       descriptionItems = getElementByTag(fstElmnt,"description");

                       NodeList characteristics = fstElmnt.getElementsByTagName("characteristic");
                       if (characteristics != null && characteristics.getLength() > 0) {
                           for (int j = 0; j < characteristics.getLength(); j++) {
                               Node nodeS = characteristics.item(j);
                               Element fstElmntS = (Element) nodeS;
                               String titleCharacter = getElementByTag(fstElmntS, "title");
                               int propertyId = Integer.parseInt(getElementByTag(fstElmntS, "property-id"));

                               switch (propertyId) {
                                   case URL_KEY.PUBLISHER_ID:
                                       publisherItems = titleCharacter;
                                       break;
                                   case URL_KEY.CIRCULATION_ID:
                                       circulationItems = Integer.parseInt(titleCharacter);
                                       break;
                                   case URL_KEY.RATING_ID:
                                       ratingItems = Integer.parseInt(titleCharacter);
                                       break;
                                   case URL_KEY.AUTHOR_ID:
                                       authorItems = titleCharacter;
                                       break;
                                   case URL_KEY.YEAR_ID:
                                       yearItems = Integer.parseInt(titleCharacter);
                                       break;
                                   case URL_KEY.EDITION_ID:
                                       editionItems = Integer.parseInt(titleCharacter);
                                       break;
                               }
                           }
                       }
                       Log.i(TAG, "...................CHARACTER");
                        try {
                            NodeList images = fstElmnt.getElementsByTagName("image");
                            if (images != null && images.getLength() > 0) {
                                for (int j = 0; j < images.getLength(); j++) {
                                    Node nodeS = images.item(j);
                                    Element fstElmntS = (Element) nodeS;
                                    imageUrl = getElementByTag(fstElmntS,"original-url");
                                }
                            }
                        }catch (Exception ioe){

                        }
                       Log.i(TAG, "...................AFTER IMAGE");
                       BookItem storeBookItem;
                       MagazineItem storeMagazineItem;
                       if(isBook == true){
                           storeBookItem = new BookItem();
                           Log.i(TAG,"1");
                           //storeBookItem.setId(idItems);
                           Log.i(TAG,"2");
                           storeBookItem.setTitle(titleItems);
                           Log.i(TAG,"3");
                           storeBookItem.setPublisher(publisherItems);
                           Log.i(TAG,"4");
                           storeBookItem.setDescription(descriptionItems);
                           Log.i(TAG,"5");
                           storeBookItem.setСirculation(circulationItems);
                           Log.i(TAG,"6");
                           storeBookItem.setRating(ratingItems);
                           Log.i(TAG,"7");
                           storeBookItem.setImageUrl(imageUrl);
                           Log.i(TAG,"8");
                           storeBookItem.setAuthor(authorItems);
                           Log.i(TAG,"9");
                           storeBookItem.setYear(yearItems);
                           Log.i(TAG,"10");
                           type.getItemCollection().add(storeBookItem);
                           Log.i(TAG, "Add book to local");

                       }else{
                           storeMagazineItem = new MagazineItem();
                          // storeMagazineItem.setId(idItems);
                           storeMagazineItem.setTitle(titleItems);
                           storeMagazineItem.setPublisher(publisherItems);
                           storeMagazineItem.setDescription(descriptionItems);
                           storeMagazineItem.setСirculation(circulationItems);
                           storeMagazineItem.setRating(ratingItems);
                           storeMagazineItem.setImageUrl(imageUrl);
                           storeMagazineItem.setEdition(editionItems);
                           type.getItemCollection().add(storeMagazineItem);
                           Log.i(TAG, "Add magazine");
                       }
                   }catch (Exception ioe){
                       Log.e(TAG, "EXCEPTION"+ ioe);
                   }
               }
           } catch (Exception e) {
               Log.i(TAG, "XML Parsing Exception");
           }
       }catch (Exception i){
           Log.i(TAG,"URL Exception");
       }
    }

    public String getElementByTag(Element element, String tag){
        NodeList idList = element.getElementsByTagName(tag);
        Element idElement = (Element) idList.item(0);
        idList = idElement.getChildNodes();
        Log.i(TAG, ((Node) idList.item(0)).getNodeValue());
        String item = ((Node) idList.item(0)).getNodeValue();
        return item;
    }
}
