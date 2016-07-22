package com.example.bookstoreapp.typeFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookstoreapp.ConnectToNetwork;
import com.example.bookstoreapp.R;
import com.example.bookstoreapp.items.BookItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Паша on 14.07.2016.
 */
public class BookFragment extends Fragment {
    private static final String TAG = "BookFragment";
    boolean network;
    private RecyclerView mRecyclerView;
    private List<BookItem> items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_book_layout, container,                                                                false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_horizontal);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        network = ConnectToNetwork.hasConnection(getContext());

        BookItem bookItem = new BookItem();
        bookItem.setTitle("Кобзарь");
        bookItem.setYear(1789);
        items.add(bookItem);
        mRecyclerView.setAdapter(new RecyclerAdapter(items));
        return v;
    }

    private class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mYearTextView;

        public RecyclerHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.small_text_view_title);
            mYearTextView = (TextView) itemView.findViewById(R.id.small_text_view_year);
        }

        public void bindGalleryItem(BookItem item) {
            mTitleTextView.setText(item.getTitle());
            mYearTextView.setText(Integer.toString(item.getYear()));
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
