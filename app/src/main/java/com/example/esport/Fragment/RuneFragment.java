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

public class RuneFragment extends Fragment {
    ArrayAdapter adapter;
    ArrayList<E_News> runesList;
    ArrayList<String> namesList;
    private DatabaseReference mDbRoot, mDbRunes;
    private FirebaseDatabase firebaseDb;
    ListView mLvNewsList;
    View root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_runes, container, false);

        addComponent();
        addListener();
        loadRuneFrag();
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
                intent.putExtra("id", runesList.get(position).getId());
                intent.putExtra("title", runesList.get(position).getTitle());
                intent.putExtra("content", runesList.get(position).getContent());
                getActivity().startActivity(intent);
            }
        });
    }

    private void addComponent() {
        firebaseDb = FirebaseDatabase.getInstance();
        mDbRoot = firebaseDb.getReference();
        runesList = new ArrayList<>();
        namesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, namesList);
        mLvNewsList = root.findViewById(R.id.lv_runes);
        mLvNewsList.setAdapter(adapter);
    }

    private void loadRuneFrag() {
        mDbRunes = mDbRoot.child("guide").child("rune");
        mDbRunes.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    E_News item1 = new E_News("Chuẩn xác",
                                            "https://i.imgur.com/PV6wiTD.png", "", Calendar.getInstance().getTime());
                                    item1.setId("0");
                                    runesList.add(item1);
                                    mDbRunes.setValue(runesList)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        runesList.clear();
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
        mDbRunes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                runesList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    E_News runes = d.getValue(E_News.class);
                    runesList.add(runes);
                    namesList.add(runes.getTitle());
                }
                mDbRunes.removeEventListener(this);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
