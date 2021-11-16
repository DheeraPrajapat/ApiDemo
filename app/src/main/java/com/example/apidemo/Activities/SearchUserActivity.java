package com.example.apidemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Adapter.UserItemAdapter;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.PojoClasses.SearchUser.SearchModel;
import com.example.apidemo.Service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserActivity extends AppCompatActivity {
    EditText searchEditor;
    String id="" ,token="";
    ImageView backBtn;
    RecyclerView recyclerView;
    Toolbar mainActivityToolbar,searchActivityToolbar;
    UserService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        initViews();
        searchEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    Toast.makeText(SearchUserActivity.this, "Enter the name...", Toast.LENGTH_SHORT).show();
                }else{
                setTheSearchItem(s.toString());}
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(SearchUserActivity.this,MainActivity.class)
                         .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
               finish();
            }
        });
    }

    private void initViews() {
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        id=sharedPreferences.getString("id","");
        service= ApiClient.getClientTokn(token).create(UserService.class);
        mainActivityToolbar=findViewById(R.id.main_activity_toolbar);
        searchActivityToolbar=findViewById(R.id.search_activity_toolbar);
        searchActivityToolbar.setTitle("");
        setSupportActionBar(searchActivityToolbar);
        backBtn=searchActivityToolbar.findViewById(R.id.backButton);
        recyclerView=findViewById(R.id.userAllPosts);
        searchEditor=searchActivityToolbar.findViewById(R.id.searchEditor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchActivityToolbar.setVisibility(View.VISIBLE);
        mainActivityToolbar.setVisibility(View.GONE);

    }
    private void setTheSearchItem(String name)
    {
        service.callbackGetUserByName(name).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if(response.isSuccessful()){
                    SearchModel searchModel=response.body();
                    UserItemAdapter adapter = new UserItemAdapter(searchModel.getBody(),SearchUserActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchUserActivity.this));
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                Toast.makeText(SearchUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}