package com.example.apidemo.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Adapter.UserPostAdapter;
import com.example.apidemo.Friend.FriendList.FriendListActivity;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.PojoClasses.GetPost.GetAllPostModel;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignUpPojo.LogoutModel;
import com.example.apidemo.PojoClasses.CreatePost.UserPostModel;
import com.example.apidemo.Team.TeamActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    UserService userService;
    AlertDialog.Builder alert;
    RecyclerView recyclerView;
    String token="",userId="";
    ProgressDialog progressDialog;
    ImageView imageView,searchIcon;
    FloatingActionButton fbBtn;
    Toolbar mainActivityToolbar,searchActivityToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getAllFeeds();
        imageView.setOnClickListener(v-> setUpPopMenu());
        fbBtn.setOnClickListener(v->createPostAlertBox());
        searchIcon.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,SearchUserActivity.class)
                  .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
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
        mainActivityToolbar=findViewById(R.id.main_activity_toolbar);
        searchActivityToolbar=findViewById(R.id.search_activity_toolbar);
        setSupportActionBar(mainActivityToolbar);
        mainActivityToolbar.inflateMenu(R.menu.user_profile);
        imageView=findViewById(R.id.clcik_main_menu);
        searchIcon=findViewById(R.id.searchIcon);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userId = sh.getString("id", "");
        token=sh.getString("token","");
        recyclerView=findViewById(R.id.searchRecycler);
        fbBtn=findViewById(R.id.createPost);
        alert=new AlertDialog.Builder(this);
        userService= ApiClient.getClientTokn(token).create(UserService.class);
        progressDialog=new ProgressDialog(this);
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpPopMenu(){
        PopupMenu popupMenu=new PopupMenu(this,imageView);
        popupMenu.getMenuInflater().inflate(R.menu.user_profile,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
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

                case R.id.teamOperation:
                    startActivity(new Intent(MainActivity.this, TeamActivity.class));
                    return true;
                case R.id.friendList:
                    startActivity(new Intent(MainActivity.this, FriendListActivity.class));
                    return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void createPostAlertBox()
    {
        AlertDialog alertDialog=new AlertDialog.Builder(this,R.style.verification_done).create();
        View view= LayoutInflater.from(this).inflate(R.layout.create_post_view,null,false);
        alertDialog.setView(view);
        alertDialog.show();
        TextInputEditText editText=view.findViewById(R.id.postContent);
        //ImageView post_Image=view.findViewById(R.id.postImage);
        Button submitButton=view.findViewById(R.id.postButton);
        Button cancelButton=view.findViewById(R.id.postCancelButton);
        cancelButton.setOnClickListener(v->alertDialog.dismiss());
        submitButton.setOnClickListener(v->{
            String content=editText.getText().toString().trim();
            if(content.isEmpty()){
                Toast.makeText(MainActivity.this,"Write something...",Toast.LENGTH_SHORT).show();
            }else{
                userService.callbackCreatePost(userId,content).enqueue(new Callback<UserPostModel>() {
                    @Override
                    public void onResponse(Call<UserPostModel> call, Response<UserPostModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            alertDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Post Uploaded...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UserPostModel> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchActivityToolbar.setVisibility(View.GONE);
        mainActivityToolbar.setVisibility(View.VISIBLE);
    }

    private void getAllFeeds()
    {
        userService.callbackGetAllPost().enqueue(new Callback<GetAllPostModel>() {
            @Override
            public void onResponse(Call<GetAllPostModel> call, Response<GetAllPostModel> response) {
                GetAllPostModel getAllPostModel=response.body();
                UserPostAdapter userPostAdapter=new UserPostAdapter(getAllPostModel.getBody(),MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(userPostAdapter);
            }
            @Override
            public void onFailure(Call<GetAllPostModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}