package com.example.apidemo.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignUpPojo.Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText editText;
    AlertDialog.Builder alertDialog;
    Button button;
    ProgressDialog progressDialog;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        button.setOnClickListener(v -> {
            if(editText.getText().toString().equals("")){
                Toast.makeText(ForgetPasswordActivity.this, "Fill the blank...", Toast.LENGTH_SHORT).show();
            }
            sendForgetPasswordLink(editText.getText().toString());
        });
        initViews();
    }

    private void sendForgetPasswordLink(String toString)
    {
        progressDialog.show();
        userService.callbackForget(toString).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    alertDialog.show();
                    alertDialog.setMessage("Please check the your email / spam box.");
                    alertDialog.setPositiveButton("Ok", (dialog, which) ->
                            startActivity(new Intent(ForgetPasswordActivity.this,SignInActivity.class))
                            );
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                alertDialog.show();
                alertDialog.setMessage(t.getMessage());
                alertDialog.setPositiveButton("Ok", (dialog, which) -> Toast.makeText(ForgetPasswordActivity.this, "Thank you..", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void initViews() {
        editText=findViewById(R.id.forgetActivity);
        button=findViewById(R.id.forgetButton);
        progressDialog=new ProgressDialog(this);
        userService= ApiClient.getClient().create(UserService.class);
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
    }
}