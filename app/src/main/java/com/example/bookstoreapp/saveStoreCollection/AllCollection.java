package com.example.bookstoreapp.saveStoreCollection;


import android.util.Log;

import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.MagazineItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 05.07.2016.
 */
public class AllCollection {
    private static final String TAG = "AllCollection";

    private static int idParent;
    public static List<Category> allCategory = new ArrayList<Category>();

    public static void sortCollection(){
        Category category;
        for(int i = 0; i < allCategory.size(); i++){
            category = allCategory.get(i);
            if(category.getParentId() != idParent){
                if(searchParentIndexOnCategory(category.getParentId()) != -1){
                    if(category.getParentId() == URL_KEY.BOOK_COLLECTION_ID){
                        TypeItems<BookItem> type = new TypeItems();
                        type.setId(category.getId());
                        type.setTitle(category.getTitle());
                        type.setParentId(category.getParentId());
                        allCategory.get(searchParentIndexOnCategory(category.getParentId())).getTypeCollection().add(type);
                        allCategory.remove(i);
                    }else {
                        TypeItems<MagazineItem> type = new TypeItems();
                        type.setId(category.getId());
                        type.setTitle(category.getTitle());
                        type.setParentId(category.getParentId());
                        allCategory.get(searchParentIndexOnCategory(category.getParentId())).getTypeCollection().add(type);
                        allCategory.remove(i);
                    }
                }
            }
        }
    }

    public static int searchParentIndexOnCategory(int id){
        for(int i = 0; i < allCategory.size(); i++){
            if(id == allCategory.get(i).getId()){
               return i;
            }
        }
        return -1;
    }

    public static int searchIdType(int id){
        for(int i = 0; i < allCategory.size(); i++){
            for(int j = 0; j < allCategory.get(i).getTypeCollection().size(); j++){
                    if(id == allCategory.get(i).getTypeCollection().get(j).getId()){
                        return j;
                    }
                }
            }
        return -1;
    }

    public static int searchParentIndexOnTypeItems(int id){
        for(int i = 0; i < allCategory.size(); i++){
            for (int j = 0; j < allCategory.get(i).getTypeCollection().size(); j++){
                if (id == allCategory.get(i).getTypeCollection().get(j).getId()){
                    return j;
                }
            }
        }
        return -1;
    }

    public static int searchParentIdOnTypeItems(int id){
        for(int i = 0; i < allCategory.size(); i++){
            for (int j = 0; j < allCategory.get(i).getTypeCollection().size(); j++){
                if (id == allCategory.get(i).getTypeCollection().get(j).getId()){
                    return allCategory.get(i).getTypeCollection().get(j).getParentId();
                }
            }
            }
        return -1;
    }

    public static void setIdParent(int id){
        idParent = id;
    }

    public static int getIdParent(){
        return idParent;
    }

    public static void showLogCategory(){
        for(int i = 0; i < allCategory.size(); i++){
            Category category = allCategory.get(i);
            Log.i(TAG, "id: " + category.getId() +
                    " title: " + category.getTitle() +
                    " parent-id: " + category.getParentId());
            List<TypeItems> typeList = category.getTypeCollection();
            for(int y = 0; y < typeList.size(); y++){
                Log.i(TAG, "..........................type: " + typeList.get(y).getTitle()+
                " type id: " + typeList.get(y).getId() + " parent id " + typeList.get(y).getParentId());
            }
        }
    }

    public static void showLogAllTypeItemsEntity(){
        for(int i = 0; i < allCategory.size(); i++){
            for (int j = 0; j < allCategory.get(i).getTypeCollection().size(); j++){
                for(int y = 0; y < allCategory.get(i).getTypeCollection().get(j).getItemCollection().size(); y++){
                    if(allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y).getClass() == BookItem.class){
                        BookItem book = (BookItem)allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        book.showLogEntity();
                    }else if(allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y).getClass() == MagazineItem.class){
                        MagazineItem magazineItem = (MagazineItem)allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        magazineItem.showLogEntity();
                    }
                }
            }
        }
    }

    public static void showLogBook(BookItem book){

    }




}

