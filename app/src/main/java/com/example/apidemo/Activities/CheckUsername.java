package com.example.apidemo.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignUpPojo.Model;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckUsername extends AppCompatActivity {
    EditText checkUsername;
    Button checkButton;
    ProgressDialog progressDialog;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_username);
        View parentLayout = findViewById(android.R.id.content);
        initViews();
        checkButton.setOnClickListener(v -> {
            if(checkUsername.getText().toString().equals("")){
//                Toast.makeText(CheckUsername.this, "Fill the blank", Toast.LENGTH_SHORT).show();
                Snackbar.make(parentLayout,"Fill the blanck",Snackbar.LENGTH_SHORT).show();
            }
            if(!checkUsername.getText().toString().equals("")){
                checkUsernameMethod(checkUsername.getText().toString());
            }
        });
    }

    private void checkUsernameMethod(String username)
    {
        progressDialog.show();
        userService.callbackCheckUsername(username).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    AlertDialog.Builder alert=new AlertDialog.Builder(CheckUsername.this);
                    alert.setMessage("Do you want to set this username own profile?");
                    alert.setPositiveButton("Yes", (dialog, which) -> Toast.makeText
                            (CheckUsername.this, "Successfully set the username!", Toast.LENGTH_SHORT).show())
                            .setNegativeButton("No", (dialog, which) -> Toast.makeText(CheckUsername.this, "Thank you", Toast.LENGTH_SHORT).show());
                    alert.show();
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CheckUsername.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(){
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String token;
        token=sharedPreferences.getString("token","");
        userService= ApiClient.getClientTokn(token).create(UserService.class);
        checkUsername=findViewById(R.id.edittext_Username);
        checkButton=findViewById(R.id.checkButton);
        progressDialog=new ProgressDialog(this);
    }
}