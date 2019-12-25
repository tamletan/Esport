package com.example.esport.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.esport.Activity.PostActivity;
import com.example.esport.E_News;
import com.example.esport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ChampFragment extends Fragment {
    ArrayAdapter adapter;
    ArrayList<E_News> champsList;
    ArrayList<String> namesList;
    private DatabaseReference mDbRoot, mDbChamps;
    private FirebaseDatabase firebaseDb;
    ListView mLvNewsList;
    View root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_champs, container, false);

        addComponent();
        addListener();
        loadChampFrag();
        return root;
    }

    private void addListener() {
        root.findViewById(R.id.fab_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLvNewsList.smoothScrollToPosition(0);
            }
        });
        root.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GuideFragment()).commit();
            }
        });
        mLvNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), PostActivity.class);
                intent.putExtra("id", champsList.get(position).getId());
                intent.putExtra("title", champsList.get(position).getTitle());
                intent.putExtra("content", champsList.get(position).getContent());
                getActivity().startActivity(intent);
            }
        });
    }

    private void addComponent() {
        firebaseDb = FirebaseDatabase.getInstance();
        mDbRoot = firebaseDb.getReference();
        champsList = new ArrayList<>();
        namesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, namesList);
        mLvNewsList = root.findViewById(R.id.lv_champs);
        mLvNewsList.setAdapter(adapter);
    }

    private void loadChampFrag() {
        mDbChamps = mDbRoot.child("guide").child("champ");
        mDbChamps.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    E_News item1 = new E_News("Ashe",
                                            "https://i.imgur.com/HctTyC3.png", "", Calendar.getInstance().getTime());
                                    item1.setId("0");
                                    champsList.add(item1);
                                    mDbChamps.setValue(champsList)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        champsList.clear();
                                                    } else {
                                                    }
                                                }
                                            });
                                } else {
                                    Log.d("dataexist", "data exist");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }
                );
        mDbChamps.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                champsList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    E_News champs = d.getValue(E_News.class);
                    champsList.add(champs);
                    namesList.add(champs.getTitle());
                }
                mDbChamps.removeEventListener(this);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
