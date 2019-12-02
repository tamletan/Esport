package com.example.esport;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEdtEmail, mEdtPassword, mEdtConfirm, mEdtNickname;
    private Button mBtnSignUp, mBtnCancel;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addComponents();
        addEventListener();
    }

    private void addComponents() {
        mEdtEmail = findViewById(R.id.signup_input_email);
        mEdtPassword = findViewById(R.id.signup_input_pwd);
        mEdtConfirm = findViewById(R.id.signup_input_confirm_pwd);
        mEdtNickname = findViewById(R.id.signup_input_user);
        mBtnSignUp = findViewById(R.id.register);
        mBtnCancel = findViewById(R.id.lin);
        auth = FirebaseAuth.getInstance();
    }

    private void addEventListener() {
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEdtEmail.getText().toString();
                String password = mEdtPassword.getText().toString();
                String confirm = mEdtConfirm.getText().toString();
                String nickname = mEdtNickname.getText().toString();
                if (validation(email, password, confirm, nickname)) {
                    createUser(email, password, nickname);
                }
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean validation(String email, String password, String confirm, String nickname) {
        boolean result = false;
        String error = getResources().getString(R.string.inputBlank);
        if (email.equals("")) {
            mEdtEmail.setError(error);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEdtPassword.setError("Please input email");
        } else if (password.equals("")) {
            mEdtPassword.setError(error);
        } else if (confirm.equals("")) {
            mEdtConfirm.setError(error);
        } else if (nickname.equals("")) {
            mEdtNickname.setError(error);
        } else if (!confirm.equals(password)) {
            mEdtConfirm.setError("Password confirm doesn't match");
        } else {
            result = true;
        }
        return result;
    }

    public void createUser(final String email, final String password, final String nickname) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    createUserNode(auth.getCurrentUser().getUid(), nickname, email, password);
                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                String status = "Register Successfully. Please check your email for verification!";
                                Toast.makeText(RegisterActivity.this, status, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this,
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this,
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createUserNode(final String userId, final String nickname, final String email, final String password) {
        final DatabaseReference mDbRoot = FirebaseDatabase.getInstance().getReference();
        mDbRoot.child(userId)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    UserInfo usr = new UserInfo(email, "user", nickname, "link to avatar");
                                    usr.setId(userId);
                                    mDbRoot.child("users").child(userId).child("userInfo").setValue(usr)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("regisacc","create node success");
                                                    } else {
                                                        Log.d("regisacc","cannot create node");
                                                    }
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }
                );
    }
}
