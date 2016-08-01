package com.example.bookstoreapp.typeFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstoreapp.MainActivity;
import com.example.bookstoreapp.R;
import com.example.bookstoreapp.URL_KEY;
import com.example.bookstoreapp.items.BookItem;
import com.example.bookstoreapp.items.DictionaryBook;
import com.example.bookstoreapp.observer.ItemObserver;
import com.example.bookstoreapp.observer.Subject;
import com.example.bookstoreapp.saveStoreCollection.Synchroniser;
import com.example.bookstoreapp.saveStoreCollection.TypeItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 14.07.2016.
 */
public class BookFragment extends Fragment implements ItemObserver {
    private static final String TAG = "BookFragment";
    private RecyclerView mRecyclerViewBook;
    private List<BookItem> items = new ArrayList<>();

    static public boolean onCreate = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Synchroniser.getInstance().registerObserver(this);
        onCreate = true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_list_fragment, container,false);
        mRecyclerViewBook = (RecyclerView) v.findViewById(R.id.recycler_view_vertical_book_elements);
        mRecyclerViewBook.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();
        return v;
    }

    public void setupAdapter(){
        List<DictionaryBook> allTypeItems = MainActivity.sDictionaryBookRepository.allRead();
        mRecyclerViewBook.setAdapter(new RecyclerParentAdapter(allTypeItems));
        }

    @Override
    public void update() {
        Toast.makeText(getActivity(), "I am notified", Toast.LENGTH_LONG).show();
        setupAdapter();
    }

    private class RecyclerParentHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        private RecyclerView mRecyclerView;

        public RecyclerParentHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.book_genre);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view_horizontal);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        }

        public void bindGalleryItem(DictionaryBook item) {
            mTextView.setText(item.Title);
            mRecyclerView.setAdapter(new RecyclerAdapter(item.listItem));
        }
    }

    private class RecyclerParentAdapter extends RecyclerView.Adapter<RecyclerParentHolder>{
        private List<DictionaryBook> mItems;
        public RecyclerParentAdapter(List<DictionaryBook> items) {
            mItems = items;
        }

        @Override
        public RecyclerParentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.main_list_recycler_view, parent, false);
            return new RecyclerParentHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerParentHolder holder, int position) {
            holder.bindGalleryItem(mItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mYearTextView;
        private ImageView mImageView;
        public RecyclerHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.small_text_view_title);
            mYearTextView = (TextView) itemView.findViewById(R.id.small_text_view_year);
            mImageView = (ImageView) itemView.findViewById(R.id.small_image_content);
        }

        public void bindGalleryItem(BookItem item) {
            mTitleTextView.setText(item.getTitle());
            mYearTextView.setText(Integer.toString(item.getYear()));
            Picasso.with(getActivity()).setIndicatorsEnabled(true);
            Picasso.with(getActivity()).load(item.getImageUrl()).fit().centerCrop().into(mImageView);
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
        private List<BookItem> mItems;
        public RecyclerAdapter(List<BookItem> items) {
            mItems = items;
        }

        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.small_simple_list_item, viewGroup, false);
            return new RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerHolder photoHolder, int position) {
            BookItem galleryItem = mItems.get(position);
            photoHolder.bindGalleryItem(galleryItem);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}
