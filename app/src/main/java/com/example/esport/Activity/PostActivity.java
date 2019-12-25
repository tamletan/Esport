package com.example.esport.Activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.esport.Adapter.CommentAdapter;
import com.example.esport.Comment;
import com.example.esport.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    ImageView content_img;
    TextView post_title, comment_option_btn;
    FloatingActionButton fab;
    ScrollView scrollView;
    Button btnAddComment;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    EditText editTextComment;
    String PostKey;
    static String COMMENT_KEY = "Comment";
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        String img = intent.getStringExtra("content");
        content_img = findViewById(R.id.content_img);
        Picasso.get().load(img).into(content_img);
        post_title = findViewById(R.id.post_title);
        fab = findViewById(R.id.fab_top);
        scrollView = findViewById(R.id.scrollView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0,0);
            }
        });

        RvComment = findViewById(R.id.rv_comment);
        btnAddComment = findViewById(R.id.post_add_comment_btn);
        editTextComment = findViewById(R.id.post_comment);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        // move add_cmt_btn up when typing comment
        editTextComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "translationY", -100f);
                animator.setDuration(2000);
                animator.start();
            }
        });
        // add Comment button listener

            btnAddComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (firebaseUser != null) {
                        //btnAddComment.setVisibility(View.INVISIBLE);
                        if (editTextComment.getText().toString().trim().length() != 0) {
                            Log.d(">>>>>", "onClick: "+editTextComment.getText());
                            DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey).push();
                            String comment_content = editTextComment.getText().toString();
                            String uid = firebaseUser.getUid();
                            String uname = MainActivity.getUsername();
                            Comment comment = new Comment(comment_content, uid, uname);

                            commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    showMessage("comment added");
                                    editTextComment.setText("");
                                    btnAddComment.setVisibility(View.VISIBLE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showMessage("fail to add comment : " + e.getMessage());
                                }
                            });
                        } else {
                            showMessage("Please type something");
                        }
                    } else {
                        showMessage("Please login to comment");
                    }
                }
            });

        comment_option_btn = findViewById(R.id.option_comment_btn);
//        comment_option_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        String picture = getIntent().getExtras().getString("picture");
        Glide.with(this).load(picture).into(content_img);
        String title = getIntent().getExtras().getString("title");
        post_title.setText(title);
        PostKey = getIntent().getExtras().getString("id");
        Log.d("postkey", "onCreate: "+PostKey);
        iniRvComment();
    }

    private void iniRvComment() {
        RvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Comment comment = snapshot.getValue(Comment.class);
                    listComment.add(comment);
                }

                commentAdapter = new CommentAdapter(getApplicationContext(), listComment);
                RvComment.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
//        setResult(RESULT_CANCELED);
        finish();
        return true;
    }

    private void showMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }
}
