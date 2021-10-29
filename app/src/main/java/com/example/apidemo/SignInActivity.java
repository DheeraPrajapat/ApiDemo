package com.example.apidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {
    Button btn1,btn2;
    EditText edit1,edit2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
    }

    private void initViews() {
        btn1=findViewById(R.id.loginButton);
        btn2=findViewById(R.id.loginSingUpButton);
        edit1=findViewById(R.id.loginEmail);
        edit2=findViewById(R.id.loginPassword);
    }
}