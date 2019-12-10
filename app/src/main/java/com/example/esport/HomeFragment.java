package com.example.esport;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    MyListAdapter adapter;
    ArrayList<E_News> newsList;
    private DatabaseReference mDbRoot, mDbNews;
    private FirebaseDatabase firebaseDb;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRvNewsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        firebaseDb = FirebaseDatabase.getInstance();
        mDbRoot = firebaseDb.getReference();
        newsList = new ArrayList<>();
        loadHomeFrag();
        mRvNewsList = root.findViewById(R.id.rv_news);
        adapter = new MyListAdapter(getActivity(), newsList);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRvNewsList.setLayoutManager(mLayoutManager);
        mRvNewsList.setAdapter(adapter);
        root.findViewById(R.id.fab_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRvNewsList.smoothScrollToPosition(0);
            }
        });
        root.findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRvNewsList.smoothScrollToPosition(0);
            }
        });
        if (MainActivity.info!=null && MainActivity.info.getRole().equals("employee"))
            root.findViewById(R.id.add_btn).setVisibility(View.VISIBLE);
        else
            root.findViewById(R.id.add_btn).setVisibility(View.INVISIBLE);
        return root;
    }

    private void loadHomeFrag() {
        mDbRoot.child("news")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    E_News item1 = new E_News("Chi tiết kỹ năng của Aphelios, Song Nguyệt Chiến Binh",
                                            "https://i.imgur.com/gs82vtt.png",
                                            "https://lienminh.garena.vn/images/Lan_h3lpm3/11_2019/Aphelios/lo_dien/Aphelios%20Ability%20Article.jpg",
                                            Calendar.getInstance().getTime());
                                    item1.setId("0");
                                    newsList.add(item1);
                                    mDbRoot.child("news").setValue(newsList)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        newsList.clear();
                                                    } else {
                                                    }
                                                }
                                            });
                                } else {
                                    Log.d("dataexist", "Hello 123");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }
                );
        mDbNews = mDbRoot.child("news");
        if(mDbNews == null)
            Log.d("loaddata", "nullllllllll");
        else
            Log.d("loaddata", "nott null");
        mDbNews.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newsList.clear();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    E_News tD = d.getValue(E_News.class);
                    newsList.add(tD);
                }
                mDbNews.removeEventListener(this);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}