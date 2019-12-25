package com.example.esport.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.esport.Fragment.ExpandableListDataPump;
import com.example.esport.R;
import com.example.esport.UserInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditUserActivity extends AppCompatActivity {
    EditText username, email, role;
    FloatingActionButton delete_fab;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        String Uid = intent.getStringExtra("user");
        UserInfo userInfo = ExpandableListDataPump.findUserByUID(Uid);
        username = findViewById(R.id.edt_username);
        email = findViewById(R.id.edt_email);
        role = findViewById(R.id.edt_role);
        username.setText(userInfo.getUsername());
        email.setText(userInfo.getEmail());
        role.setText(userInfo.getRole());
        delete_fab = findViewById(R.id.fab_del);
        delete_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserRecord userRecord = MainActivity.auth.getUser(uid);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
