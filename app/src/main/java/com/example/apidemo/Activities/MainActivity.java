package com.example.apidemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apidemo.Adapter.UserItemAdapter;
import com.example.apidemo.Adapter.UserItemClass;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.SearchUser.SearchBody;
import com.example.apidemo.SearchUser.SearchModel;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignUpPojo.GetProfileBody;
import com.example.apidemo.SignUpPojo.GetProfileModel;
import com.example.apidemo.SignUpPojo.LogoutModel;
import com.example.apidemo.SignUpPojo.Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button serachButton;
    UserService userService;
    AlertDialog.Builder alert;
    String userId="";
    RecyclerView recyclerView;
    String token="";
    ArrayList<UserItemClass> arrayList;
    EditText searchEditor;
    UserItemAdapter userItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        serachButton.setOnClickListener(v -> {
            String name=searchEditor.getText().toString();
            if(name.isEmpty()){
                Toast.makeText(MainActivity.this, "Enter the name...", Toast.LENGTH_SHORT).show();
            }
            setTheSearchItem(name);
        });
    }

    private void setTheSearchItem(String name)
    {
        userService.callbackGetUserByName(name).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if(response.isSuccessful()){
                    SearchModel searchModel=response.body();
                    UserItemAdapter adapter = new UserItemAdapter(searchModel.getBody(),MainActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter);
                }

            }
            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutUserId() {
        userService.callLogoutUser(userId).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                if(response.isSuccessful()){
                    SharedPreferences sh=getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor s=sh.edit();
                    s.putString("status","");
                    startActivity(new Intent(MainActivity.this, SignInActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                }else{
                    Toast.makeText(MainActivity.this, "Error occured :-"+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
                alert.setMessage(t.getMessage());
                alert.setPositiveButton("Ok", (dialog, which) -> Toast.makeText(MainActivity.this, "Thank you...", Toast.LENGTH_SHORT).show());
            }
        });
    }
    private void initViews() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userId = sh.getString("id", "");
        token=sh.getString("token","");
        serachButton= findViewById(R.id.searchMainButton);
        searchEditor=findViewById(R.id.searchName);
        recyclerView=findViewById(R.id.searchRecycler);
        alert=new AlertDialog.Builder(this);
        userService= ApiClient.getClientTokn(token).create(UserService.class);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_profile,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutButton:
                logoutUserId();
                return true;

            case R.id.Profile:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                return true;

            case R.id.checkUsername:
                startActivity(new Intent(MainActivity.this,CheckUsername.class));
                return true;
        }
        return false;
    }
}