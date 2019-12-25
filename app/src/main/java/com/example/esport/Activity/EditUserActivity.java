package com.example.esport.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.esport.E_News;
import com.example.esport.Fragment.ExpandableListDataPump;
import com.example.esport.R;
import com.example.esport.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUserActivity extends AppCompatActivity {
    EditText username, email;
    ImageButton submit_btn;
    Spinner role_spin;
    FloatingActionButton delete_fab;
    private DatabaseReference mDbRoot, mDbUser;
    private FirebaseDatabase firebaseDb;
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
        firebaseDb = FirebaseDatabase.getInstance();
        mDbRoot = firebaseDb.getReference();
        mDbUser = mDbRoot.child("users");
        Intent intent = getIntent();
        final String Uid = intent.getStringExtra("user");
        final UserInfo userInfo = ExpandableListDataPump.findUserByUID(Uid);
        username = findViewById(R.id.edt_username);
        email = findViewById(R.id.edt_email);
        role_spin = findViewById(R.id.spinner_role);
        username.setText(userInfo.getUsername());
        email.setText(userInfo.getEmail());
        if(userInfo.getRole().equals("admin"))
            role_spin.setSelection(0);
        else if(userInfo.getRole().equals("eployee"))
            role_spin.setSelection(1);
        else if(userInfo.getRole().equals("user"))
            role_spin.setSelection(2);
        delete_fab = findViewById(R.id.fab_del);
        delete_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbUser.child(Uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditUserActivity.this,
                                    "Delete User Complete", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditUserActivity.this,
                                    "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        submit_btn = findViewById(R.id.btn_submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo info = new UserInfo(email.getText().toString(), role_spin.getSelectedItem().toString(),
                        username.getText().toString(), userInfo.getAvatar());
                info.setId(Uid);
                mDbUser.child(Uid).child("userInfo").setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditUserActivity.this,
                                    "Edit User Complete", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditUserActivity.this,
                                    "Edit failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
