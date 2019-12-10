package com.example.esport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ItemFragment extends Fragment {
    ListView mLvNewsList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.guide_items, container, false);
        root.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GuideFragment()).commit();
            }
        });
        mLvNewsList = root.findViewById(R.id.lv_items);
        root.findViewById(R.id.fab_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLvNewsList.smoothScrollToPosition(0);
            }
        });
        return root;
    }
}
