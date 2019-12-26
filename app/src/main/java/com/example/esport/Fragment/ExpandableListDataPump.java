package com.example.esport.Fragment;

import androidx.annotation.NonNull;

import com.example.esport.E_News;
import com.example.esport.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    static FirebaseDatabase firebaseDb = FirebaseDatabase.getInstance();
    static HashMap<String, String> UidList = new HashMap<>();
    static ArrayList<UserInfo> userInfos = new ArrayList<>();
    static ArrayList<E_News> postInfos = new ArrayList<>();
    public static HashMap<String, List<String>> getDataUser() {
        userInfos.clear();
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
        final List<String> admin = new ArrayList<>();
        final List<String> employee = new ArrayList<>();
        final List<String> user = new ArrayList<>();
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
                            userInfos.add(usr);
                            UidList.put(usr.getEmail(), usr.getId());
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
    public static HashMap<String, List<String>> getDataPost() {
        postInfos.clear();
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
        final List<String> news = new ArrayList<>();
        final List<String> champs = new ArrayList<>();
        final List<String> items = new ArrayList<>();
        final List<String> runes = new ArrayList<>();
        final DatabaseReference mDbRoot = firebaseDb.getReference();

        mDbRoot.child("news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    mDbRoot.child("news").child(d.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot d) {
                            E_News post = d.getValue(E_News.class);
                            postInfos.add(post);
                            UidList.put(post.getTitle(), post.getId());
                            news.add(post.getTitle());
                            mDbRoot.child("news").child(d.getKey()).removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                mDbRoot.child("news").removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        mDbRoot.child("guide").child("champ")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    mDbRoot.child("guide").child("champ").child(d.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot d) {
                            E_News post = d.getValue(E_News.class);
                            postInfos.add(post);
                            UidList.put(post.getTitle(), post.getId());
                            champs.add(post.getTitle());
                            mDbRoot.child("guide").child("champ").child(d.getKey()).removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                mDbRoot.child("guide").child("champ").removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        mDbRoot.child("guide").child("item")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            mDbRoot.child("guide").child("item").child(d.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot d) {
                                    E_News post = d.getValue(E_News.class);
                                    postInfos.add(post);
                                    UidList.put(post.getTitle(), post.getId());
                                    items.add(post.getTitle());
                                    mDbRoot.child("guide").child("item").child(d.getKey()).removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        mDbRoot.child("guide").child("item").removeEventListener(this);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

        mDbRoot.child("guide").child("rune")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            mDbRoot.child("guide").child("rune").child(d.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot d) {
                                    E_News post = d.getValue(E_News.class);
                                    postInfos.add(post);
                                    UidList.put(post.getTitle(), post.getId());
                                    runes.add(post.getTitle());
                                    mDbRoot.child("guide").child("rune").child(d.getKey()).removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        mDbRoot.child("guide").child("rune").removeEventListener(this);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });

        expandableListDetail.put("News", news);
        expandableListDetail.put("Champs", champs);
        expandableListDetail.put("Items", items);
        expandableListDetail.put("Runes", runes);
        return expandableListDetail;
    }
    public static UserInfo findUserByUID(String uid){
        for (UserInfo u : userInfos) {
            if(u.getId().equals(uid))
                return u;
        }
        return null;
    }
    public static E_News findPostByUID(String uid){
        for (E_News u : postInfos) {
            if(u.getId().equals(uid))
                return u;
        }
        return null;
    }
}
