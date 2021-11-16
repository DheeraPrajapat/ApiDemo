package com.example.apidemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Adapter.CommentsAdapter;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.PojoClasses.CreatePostComment.CommentModel;
import com.example.apidemo.PojoClasses.GetAllComments.GetAllCommentsModel;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {
    UserService userService;
    String token="",id="";
    TextInputEditText content;
    ImageView postButton;
    View parentLayout;

    RecyclerView commentRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initViews();
        postButton.setOnClickListener(v->{
            String con=content.getText().toString().trim();
            if(con.equals("")){
                Snackbar.make(parentLayout,"Please write the comment!..",Snackbar.LENGTH_SHORT).show();
            }else {
                postTheUserComment(con);
            }
        });
    }

    private void postTheUserComment(String content)
    {
        int feed_id=getIntent().getIntExtra("feed_id",0);
        int user_id=Integer.parseInt(id);
        userService.callbackPostComment(feed_id,user_id,content).enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                if(response.isSuccessful()){
                    Snackbar.make(parentLayout,"Comment Posted...",Snackbar.LENGTH_SHORT).show();
                    getAllComments();
                }
            }
            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                Snackbar.make(parentLayout,t.getMessage(),Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllComments()
    {
        int feed_id=getIntent().getIntExtra("feed_id",0);
        userService.callbackGetAllComments(feed_id,1,100).enqueue(new Callback<GetAllCommentsModel>() {
            @Override
            public void onResponse(Call<GetAllCommentsModel> call, Response<GetAllCommentsModel> response) {
                if(response.isSuccessful()){
                    GetAllCommentsModel getAllCommentsBody=response.body();
                    assert getAllCommentsBody != null;
                    CommentsAdapter commentsAdapter=new CommentsAdapter(CommentsActivity.this,getAllCommentsBody.body);
                    commentRecyclerView.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
                    commentRecyclerView.setAdapter(commentsAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetAllCommentsModel> call, @NonNull Throwable t) {
                Snackbar.make(parentLayout,t.getMessage(),Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        id=sharedPreferences.getString("id","");
        userService= ApiClient.getClientTokn(token).create(UserService.class);
        content=findViewById(R.id.commentContent);
        postButton=findViewById(R.id.commentButton);
        commentRecyclerView=findViewById(R.id.commentRecyclerView);
        parentLayout = findViewById(android.R.id.content);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CommentsActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllComments();
    }
}