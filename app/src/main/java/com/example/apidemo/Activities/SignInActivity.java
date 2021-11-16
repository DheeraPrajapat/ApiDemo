package com.example.apidemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignUpPojo.Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    Button btn1,btn2;
    EditText edit1,edit2;
    ProgressDialog progressDialog;
    UserService userService;
    TextView forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        btn1.setOnClickListener(v->{
            if(edit1.getText().toString().trim().equals("") || edit2.getText().toString().trim().equals("")){
                Toast.makeText(SignInActivity.this, "Fill the blank", Toast.LENGTH_SHORT).show();
            }else{
                String email,password;
                email=edit1.getText().toString();
                password=edit2.getText().toString();
                loginUser(email,password);
            }
        });
        btn2.setOnClickListener(v->{
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });
        forgetPassword.setOnClickListener(v->{
            startActivity(new Intent(SignInActivity.this,ForgetPasswordActivity.class));
        });
    }

    private void initViews() {
        btn1=findViewById(R.id.loginButton);
        btn2=findViewById(R.id.loginSingUpButton);
        edit1=findViewById(R.id.loginEmail);
        edit2=findViewById(R.id.loginPassword);
        forgetPassword=findViewById(R.id.forgetPasswordButton);
        progressDialog=new ProgressDialog(this);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String status = sh.getString("token", "");
        userService = ApiClient.getClientTokn(status).create(UserService.class);
    }

    public void loginUser(String email,String password){
        progressDialog.show();
        userService.callbackLogin(email,password).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    Model model = response.body();
                    Toast.makeText(SignInActivity.this, "Login successfully..."+ model.getBody().getEmail(), Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    String status="login";
                    myEdit.putString("status", status);
                    myEdit.putString("token",model.getBody().token);
                    myEdit.putString("id", String.valueOf(model.getBody()._id));
                    myEdit.apply();
                    progressDialog.dismiss();
                    Intent intent=new Intent(SignInActivity.this, MainActivity.class);
                    intent.putExtra("id",model.getBody()._id);
                    intent.putExtra("token",model.getBody().token);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(SignInActivity.this,"Error occured....", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}