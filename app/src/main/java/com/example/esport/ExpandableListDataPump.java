package com.example.esport;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
        final List<String> admin = new ArrayList<>();
        final List<String> employee = new ArrayList<>();
        final List<String> user = new ArrayList<>();
        FirebaseDatabase firebaseDb = FirebaseDatabase.getInstance();
        final DatabaseReference mDbRoot = firebaseDb.getReference().child("users");

        mDbRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    final DatabaseReference mDbInfo = mDbRoot.child(d.getKey()).child("userInfo");
                    mDbInfo.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot d) {
                            UserInfo usr = d.getValue(UserInfo.class);
                            switch (usr.getRole()){
                                case "admin":
                                    admin.add(usr.getEmail());
                                    break;
                                case "employee":
                                    employee.add(usr.getEmail());
                                    break;
                                case "user":
                                    user.add(usr.getEmail());
                                    break;
                            }
                            mDbInfo.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                mDbRoot.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        expandableListDetail.put("ADMIN", admin);
        expandableListDetail.put("EMPLOYEE", employee);
        expandableListDetail.put("USER", user);
        return expandableListDetail;
    }
}
