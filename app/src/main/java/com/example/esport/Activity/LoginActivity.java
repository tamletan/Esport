package com.example.esport.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.esport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText email_edt, pass_edt;
    TextInputLayout email_til, pass_til;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_edt = findViewById(R.id.signin_input_email);
        pass_edt = findViewById(R.id.signin_input_pwd);
        email_til = findViewById(R.id.signin_input_layout_email);
        pass_til = findViewById(R.id.signin_input_layout_pwd);
        MainActivity.auth = FirebaseAuth.getInstance();
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        findViewById(R.id.sup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void submitForm() {
        String email = email_edt.getText().toString().trim();
        String password = pass_edt.getText().toString().trim();

        if (!checkEmail()) {
            return;
        }
        if (!checkPassword()) {
            return;
        }

        email_til.setErrorEnabled(false);
        pass_til.setErrorEnabled(false);
        // authentication user
        MainActivity.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    "Authentication error", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent();
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }
                });

    }

    private boolean checkPassword() {
        String password = pass_edt.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {
            pass_til.setErrorEnabled(true);
            pass_til.setError(getString(R.string.err_msg_password));
            pass_edt.setError(getString(R.string.err_msg_required));
            requestFocus(pass_edt);
            return false;
        }
        pass_til.setErrorEnabled(false);
        return true;
    }

    private boolean checkEmail() {
        String email = email_edt.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {
            email_til.setErrorEnabled(true);
            email_til.setError(getString(R.string.err_msg_email));
            email_edt.setError(getString(R.string.err_msg_required));
            requestFocus(email_edt);
            return false;
        }
        email_til.setErrorEnabled(false);
        return true;
    }

    private static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(String password)  {
        return (password.length() >= 6);
    }

    private void requestFocus(View view) {
        if(view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
