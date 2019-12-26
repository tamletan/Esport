package com.example.esport.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esport.Adapter.ManageCommentAdapter;
import com.example.esport.Comment;
import com.example.esport.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends Fragment {
    View root;
    List<Comment> list_comment;
    RecyclerView recyclerView;
    ArrayList<String> PostId;
    FirebaseDatabase firebaseDatabase;
    ManageCommentAdapter adapter;
    private String TAG = ">>>>>>>>>>>";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_comments, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        list_comment = new ArrayList<>();
        recyclerView = root.findViewById(R.id.list_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ManageCommentAdapter(getActivity(), this, list_comment);
        recyclerView.setAdapter(adapter);
        readData(new MyCallBack() {
            @Override
            public void onCallBack(Comment comment) {
                list_comment.add(comment);
                adapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    public void deleteComment(Comment comment) {
        firebaseDatabase.getReference("Comment").child(comment.getPid()).child(comment.getCid()).setValue(null);
        list_comment.remove(comment);
        adapter.notifyDataSetChanged();
    }

    public interface MyCallBack {
        void onCallBack(Comment comment);
    }

    public void readData(final MyCallBack myCallBack) {
        final DatabaseReference dbRef = firebaseDatabase.getReference().child("Comment");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    final DatabaseReference postIdRef = dbRef.child(snapshot.getKey());
                    postIdRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d: dataSnapshot.getChildren()) {
                                Comment comment = d.getValue(Comment.class);
                                comment.setCid(d.getKey());
                                //list_comment.add(comment);
                                myCallBack.onCallBack(comment);
                            }
                            postIdRef.removeEventListener(this);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                dbRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
