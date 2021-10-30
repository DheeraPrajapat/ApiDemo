package com.example.apidemo.SignUpPojo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignInActivity;
import com.example.apidemo.SignUpActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllApiMethods
{

    UserService userService= ApiClient.getClient().create(UserService.class);


    public void registerUser(String email, String password, Context context){
        userService.callbackRegister(email,password).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    Model model = response.body();
                    Toast.makeText(context, "Registration successfully..."+ model.getBody().getEmail(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Registration successfully...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void loginUser(String email, String password, Context context){
        userService.callbackLogin(email,password).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    Model model = response.body();
                    Toast.makeText(context, "Registration successfully..."+ model.getBody().getEmail(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Registration successfully...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void forgetUserEmailPassword(String email, String password, Context context){
        userService.callbackRegister(email,password).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    Model model = response.body();
                    Toast.makeText(context, "Registration successfully..."+ model.getBody().getEmail(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Registration successfully...", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
