package com.example.esport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity {
    ImageView content_img;
    FloatingActionButton fab;
    ScrollView scrollView;
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
        fab = findViewById(R.id.fab_top);
        scrollView = findViewById(R.id.scrollView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0,0);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
