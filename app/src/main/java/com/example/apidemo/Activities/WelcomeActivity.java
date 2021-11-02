package com.example.apidemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.apidemo.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler handler=new Handler();
        handler.postDelayed(() -> {
            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String status = sh.getString("status", "");
            Log.d("check","login");
            if(status.equals("login")) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }else{ startActivity(new Intent(WelcomeActivity.this, SignInActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();}
        },1200);
    }
}