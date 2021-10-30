package com.example.apidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Handler handler=new Handler();
        handler.postDelayed(() -> {
            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String status = sh.getString("status", "");
            if(status.equalsIgnoreCase("login")) {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } startActivity(new Intent(WelcomeActivity.this,SignInActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        },1200);
    }
}