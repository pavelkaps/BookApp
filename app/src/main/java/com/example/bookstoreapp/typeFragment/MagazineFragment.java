package com.example.bookstoreapp.typeFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookstoreapp.R;

/**
 * Created by Паша on 14.07.2016.
 */
public class MagazineFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_magazine_layout, container, false);
        return view;
    }
}
