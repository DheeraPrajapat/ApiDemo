package com.example.apidemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignUpPojo.Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextEmail,editTextDeviceType,editTextCPassword,editTextAdress;
    Button buttonSignUp;
    ProgressDialog progressDialog;
    UserService userService;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();

        buttonSignUp.setOnClickListener(v->{
           if(editTextEmail.getText().equals("")
                ||editTextCPassword.getText().equals("")
                ||editTextDeviceType.getText().equals("")){
               Toast.makeText(SignUpActivity.this, "Fill the all field!", Toast.LENGTH_SHORT).show();
           }else {
               String email=editTextEmail.getText().toString();
               String address=editTextAdress.getText().toString();
               String password=editTextCPassword.getText().toString();
              registerUser(email,address,password);
           }
        });
    }

    private void initViews() {
        editTextEmail=findViewById(R.id.et_Email);
        editTextDeviceType=findViewById(R.id.et_Type);
        editTextAdress=findViewById(R.id.et_Adress);
        editTextCPassword=findViewById(R.id.et_CTPass);
        editTextDeviceType.setText("Android");
        alertDialog=new AlertDialog.Builder(this);
        buttonSignUp=findViewById(R.id.button);
        progressDialog=new ProgressDialog(this);
        userService = ApiClient.getClient().create(UserService.class);
    }

    public void registerUser(String email, String address, String password){
        progressDialog.show();

        userService.callbackCheckEmail(email).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    userService.callbackRegister(email,password,address).enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, Response<Model> response) {
                            if(response.isSuccessful()){
                                Model model = response.body();
                                Toast.makeText(SignUpActivity.this, "Registration successfully..."+ model.getBody().getEmail(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                finish();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Model> call, Throwable t) {
                            progressDialog.dismiss();
                            alertDialog.setMessage(t.getMessage());
                            alertDialog.show();
                            alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(SignUpActivity.this,"Okey",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Email already exists....", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}