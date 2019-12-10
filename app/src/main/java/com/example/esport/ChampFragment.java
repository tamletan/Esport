package com.example.esport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChampFragment extends Fragment {
    ArrayAdapter adapter;
    ArrayList<E_Champs> champsList;
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
        mDbRoot.child("guide").child("champ")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    E_Champs item1 = new E_Champs("Ashe",
                                            "https://i.imgur.com/HctTyC3.png");
                                    item1.setId("0");
                                    champsList.add(item1);
                                    champsList.add(item1);
                                    champsList.add(item1);
                                    champsList.add(item1);
                                    mDbRoot.child("guide").child("champ").setValue(champsList)
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
        mDbChamps = mDbRoot.child("guide").child("champ");
        mDbChamps.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                champsList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    E_Champs champs = d.getValue(E_Champs.class);
                    champsList.add(champs);
                    namesList.add(champs.getName());
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
