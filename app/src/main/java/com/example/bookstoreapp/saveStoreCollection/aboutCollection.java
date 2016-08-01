package com.example.bookstoreapp.saveStoreCollection;

import android.util.Log;

import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.MagazineItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.BookItemRealmProxy;
import io.realm.MagazineItemRealmProxy;

/**
 * Created by Паша on 01.08.2016.
 */
public class aboutCollection {
    private static final String TAG = "aboutCollection";

    public int idParent;
    private static aboutCollection ourInstance = new aboutCollection();

    public static aboutCollection getInstance() {
        return ourInstance;
    }

    public void sortCollection(List<Category> allCategory){
        Category category;
        List<Category> removeList = new ArrayList<>();
        int count = allCategory.size();
        for(int i = 0; i < count; i++){
            category = allCategory.get(i);
            if(category.getParentId() != idParent){
                if(searchParentIndexOnCategory(category.getParentId(), allCategory) != -1){
                    if(category.getParentId() == URL_KEY.BOOK_COLLECTION_ID){
                        TypeItems<BookItem> type = new TypeItems();
                        type.setId(category.getId());
                        type.setTitle(category.getTitle());
                        type.setParentId(category.getParentId());
                        allCategory.get(searchParentIndexOnCategory(category.getParentId(), allCategory)).getTypeCollection().add(type);
                        removeList.add(category);
                    }else if(category.getParentId() == URL_KEY.MAGAZINE_COLLECTION_ID){
                        TypeItems<MagazineItem> type = new TypeItems();
                        type.setId(category.getId());
                        type.setTitle(category.getTitle());
                        type.setParentId(category.getParentId());
                        allCategory.get(searchParentIndexOnCategory(category.getParentId(), allCategory)).getTypeCollection().add(type);
                        removeList.add(category);
                    }
                }
            }
        }

        for(Category i: removeList){
            allCategory.remove(i);
        }
    }

    public int searchParentIndexOnCategory(int id, List<Category> allCategory){
        for(int i = 0; i < allCategory.size(); i++){
            if(id == allCategory.get(i).getId()){
                return i;
            }
        }
        return -1;
    }

    public int searchIdType(int id, List<Category> allCategory){
        for(int i = 0; i < allCategory.size(); i++){
            for(int j = 0; j < allCategory.get(i).getTypeCollection().size(); j++){
                if(id == allCategory.get(i).getTypeCollection().get(j).getId()){
                    return j;
                }
            }
        }
        return -1;
    }

    public int searchParentIndexOnTypeItems(int id, List<Category> allCategory){
        for(int i = 0; i < allCategory.size(); i++){
            for (int j = 0; j < allCategory.get(i).getTypeCollection().size(); j++){
                if (id == allCategory.get(i).getTypeCollection().get(j).getId()){
                    return j;
                }
            }
        }
        return -1;
    }

    public int searchParentIdOnTypeItems(int id, List<Category> allCategory){
        for(int i = 0; i < allCategory.size(); i++){
            for (int j = 0; j < allCategory.get(i).getTypeCollection().size(); j++){
                if (id == allCategory.get(i).getTypeCollection().get(j).getId()){
                    return allCategory.get(i).getTypeCollection().get(j).getParentId();
                }
            }
        }
        return -1;
    }

    public void setIdParent(int id){
        idParent = id;
    }

    public int getIdParent(){
        return idParent;
    }

    public void showLogCategory(List<Category> allCategory){
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

    public void showLogAllTypeItemsEntity(List<Category> allCategory){
        Log.i(TAG,"start");
        for(int i = 0; i < allCategory.size(); i++){
            for (int j = 0; j < allCategory.get(i).getTypeCollection().size(); j++){
                for(int y = 0; y < allCategory.get(i).getTypeCollection().get(j).getItemCollection().size(); y++){
                    Log.i(TAG, allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y).getClass().toString());
                    if(allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y).getClass() == BookItem.class
                            || allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y).getClass() == BookItemRealmProxy.class){
                        Log.i(TAG,"it*s book");
                        BookItem book = (BookItem)allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        book.showLogEntity();
                    }else if(allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y).getClass() == MagazineItem.class
                            || allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y).getClass() == MagazineItemRealmProxy.class){
                        Log.i(TAG,"it*s magazine");
                        MagazineItem magazineItem = (MagazineItem)allCategory.get(i).getTypeCollection().get(j).getItemCollection().get(y);
                        magazineItem.showLogEntity();
                    }
                }
            }
        }
    }
}
