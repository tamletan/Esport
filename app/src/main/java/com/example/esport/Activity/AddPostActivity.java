package com.example.esport.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.esport.E_News;
import com.example.esport.R;
import com.example.esport.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddPostActivity extends AppCompatActivity {
    ImageButton submit_btn;
    Spinner cata_spin;
    TextView title, content, picture;
    private DatabaseReference mDbRoot, mDbPost;
    private FirebaseDatabase firebaseDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cata_spin = findViewById(R.id.spinner_catalog);
        title = findViewById(R.id.edt_title);
        content = findViewById(R.id.edt_content);
        picture = findViewById(R.id.edt_picture);
        submit_btn = findViewById(R.id.btn_submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });
        firebaseDb = FirebaseDatabase.getInstance();
        mDbRoot = firebaseDb.getReference();
    }

    private void addPost() {
        String catalog = String.valueOf(cata_spin.getSelectedItem());
        switch (catalog){
            case "News":
                mDbPost = mDbRoot.child("news");
                break;
            case "Champions":
                mDbPost = mDbRoot.child("guide").child("champ");
                break;
            case "Items":
                mDbPost = mDbRoot.child("guide").child("item");
                break;
            case "Runes":
                mDbPost = mDbRoot.child("guide").child("rune");
                break;
        }
        E_News post = new E_News(title.getText().toString(),
                                content.getText().toString(),
                                picture.getText().toString(),
                                Calendar.getInstance().getTime());
        String id = mDbPost.push().getKey();
        post.setId(id);
        mDbPost.child(id).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddPostActivity.this,
                            "Add Post Complete", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddPostActivity.this,
                            "Add failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
