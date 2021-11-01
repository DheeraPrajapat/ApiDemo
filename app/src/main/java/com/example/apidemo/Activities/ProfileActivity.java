package com.example.apidemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        getAllInformationOfUser();
        editBtn.setOnClickListener(v->{
            email.setEnabled(true);
            dType.setEnabled(true);
            region.setEnabled(true);
            updateBtn.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.GONE);
        });

        updateBtn.setOnClickListener(v->{
            email.setEnabled(false);
            dType.setEnabled(false);
            region.setEnabled(false);
            updateBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.VISIBLE);
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
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        id=sharedPreferences.getString("id","");
        userService= ApiClient.getClientTokn(token).create(UserService.class);
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
                    Toast.makeText(ProfileActivity.this, "Error occured...", Toast.LENGTH_SHORT).show();
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
}