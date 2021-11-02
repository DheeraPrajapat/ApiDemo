package com.example.apidemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignUpPojo.ChangePasswordModel;
import com.example.apidemo.SignUpPojo.GetProfileModel;
import com.example.apidemo.SignUpPojo.Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    EditText dType,email,userId,region;
    Button editBtn,updateBtn;
    UserService userService;
    String token,id;
    ProgressDialog progressDialog;
    AlertDialog.Builder alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        id=sharedPreferences.getString("id","");

        Log.d("info","token :-"+token);
        Log.d("info","id :-"+id);
        initViews();
        getAllInformationOfUser();
        editBtn.setOnClickListener(v->{
            dType.setEnabled(true);
            region.setEnabled(true);
            updateBtn.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.GONE);
        });

        updateBtn.setOnClickListener(v->{
            String device =dType.getText().toString();
            String Region=region.getText().toString();
            if(device.equalsIgnoreCase("") || Region.equalsIgnoreCase("")){
                Toast.makeText(ProfileActivity.this, "Field cannot be blank...", Toast.LENGTH_SHORT).show();
            }else {
                updateTheUserData(device,Region);
            }
        });
    }

    private void updateTheUserData(String device, String address)
    {
        progressDialog.show();
        userService.callUpdateTheUserInformation(address,device).enqueue(new Callback<GetProfileModel>() {
            @Override
            public void onResponse(Call<GetProfileModel> call, Response<GetProfileModel> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    dType.setEnabled(false);
                    region.setEnabled(false);
                    updateBtn.setVisibility(View.GONE);
                    editBtn.setVisibility(View.VISIBLE);
                    getAllInformationOfUser();
                }
            }

            @Override
            public void onFailure(Call<GetProfileModel> call, Throwable t) {
                progressDialog.dismiss();
                alert.setMessage(t.getMessage());
                alert.setPositiveButton("Ok", (dialog, which) -> Toast.makeText(ProfileActivity.this, "Thank you...", Toast.LENGTH_SHORT).show());
            }
        });

    }

    private void initViews()
    {
        dType=findViewById(R.id.profileDType);
        email=findViewById(R.id.profileEmail);
        userId=findViewById(R.id.profileUserId);
        region=findViewById(R.id.profileRegion);
        editBtn=findViewById(R.id.editProfile);
        updateBtn=findViewById(R.id.updateProfile);
        userService= ApiClient.getClientTokn(token).create(UserService.class);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait , we are update your information...");
        alert=new AlertDialog.Builder(this);
    }

    private void getAllInformationOfUser(){
        userService.callGetUserInformation(id).enqueue(new Callback<GetProfileModel>() {
            @Override
            public void onResponse(Call<GetProfileModel> call, Response<GetProfileModel> response) {
                if(response.isSuccessful()){
                    GetProfileModel profileModel=response.body();
                    email.setText(profileModel.body.getEmail());
                    dType.setText(profileModel.body.getDevice_type());
                    userId.setText(id);
                    region.setText(profileModel.body.getAddress());
                }else{
                    Toast.makeText(ProfileActivity.this, "Error occured :-"+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetProfileModel> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error :-"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        email.setEnabled(false);
        dType.setEnabled(false);
        userId.setEnabled(false);
        region.setEnabled(false);
        updateBtn.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.change_password){
            ChangePassword();
            return true;
        }
        return false;
    }

    private void ChangePassword()
    {
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        View view= LayoutInflater.from(this).inflate(R.layout.change_password,null,false);
        alertDialog.setView(view);
        alertDialog.show();
        Button submitBtn,cancelBtn;
        EditText oldPs,newPs;
        submitBtn=view.findViewById(R.id.submitButton);
        cancelBtn=view.findViewById(R.id.cancelButton);
        oldPs=view.findViewById(R.id.oldPassword);
        newPs=view.findViewById(R.id.newPassword);
        submitBtn.setOnClickListener(v -> {
            String old,New;
            old=oldPs.getText().toString();
            New=newPs.getText().toString();
            if(old.equals("") || New.equals("")){
                Toast.makeText(ProfileActivity.this, "Fill the password...", Toast.LENGTH_SHORT).show();
            }
            userService.callChangePassword(old,New).enqueue(new Callback<ChangePasswordModel>() {
                @Override
                public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                    if(response.isSuccessful()){
                        alertDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Password changed successfully...", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                    alertDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Error occured :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            cancelBtn.setOnClickListener(v1 -> alertDialog.dismiss());
        });

    }
}