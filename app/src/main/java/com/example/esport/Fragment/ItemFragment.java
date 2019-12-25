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

public class ItemFragment extends Fragment {
    ArrayAdapter adapter;
    ArrayList<E_News> itemsList;
    ArrayList<String> namesList;
    private DatabaseReference mDbRoot, mDbItems;
    private FirebaseDatabase firebaseDb;
    ListView mLvNewsList;
    View root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_items, container, false);

        addComponent();
        addListener();
        loadItemFrag();
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
                intent.putExtra("id", itemsList.get(position).getId());
                intent.putExtra("title", itemsList.get(position).getTitle());
                intent.putExtra("content", itemsList.get(position).getContent());
                getActivity().startActivity(intent);
            }
        });
    }

    private void addComponent() {
        firebaseDb = FirebaseDatabase.getInstance();
        mDbRoot = firebaseDb.getReference();
        itemsList = new ArrayList<>();
        namesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, namesList);
        mLvNewsList = root.findViewById(R.id.lv_items);
        mLvNewsList.setAdapter(adapter);
    }

    private void loadItemFrag() {
        mDbItems = mDbRoot.child("guide").child("item");
        mDbItems.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    E_News item1 = new E_News("B.F",
                                            "https://i.imgur.com/HctTyC3.png", "", Calendar.getInstance().getTime());
                                    item1.setId("0");
                                    itemsList.add(item1);
                                    mDbItems.setValue(itemsList)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        itemsList.clear();
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
        mDbItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemsList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    E_News items = d.getValue(E_News.class);
                    itemsList.add(items);
                    namesList.add(items.getTitle());
                }
                mDbItems.removeEventListener(this);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
