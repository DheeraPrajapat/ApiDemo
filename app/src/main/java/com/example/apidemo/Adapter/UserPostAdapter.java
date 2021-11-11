package com.example.apidemo.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Activities.CommentsActivity;
import com.example.apidemo.Activities.MainActivity;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.PojoClasses.GetPost.GetAllPostBody;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignUpPojo.DeletePostModel;
import com.example.apidemo.SignUpPojo.LikeModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.PostViewHolder>
{
    private Context context;
    private List<GetAllPostBody> getAllPostBodies;
    UserService userService;
    public UserPostAdapter(List<GetAllPostBody> body, MainActivity mainActivity) {
        this.context=mainActivity;
        this.getAllPostBodies=body;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.show_all_user_posts,null,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        userService= ApiClient.getClientTokn(getAllPostBodies.get(position).getUser().token).create(UserService.class);
        int feedId=getAllPostBodies.get(position).get_id();
        int userId=getAllPostBodies.get(position).getUser_id();
        int status1=getAllPostBodies.get(position).getIsLike();
        holder.name.setText(getAllPostBodies.get(position).getUser().getEmail());
        holder.date.setText(getAllPostBodies.get(position).getCreatedAt().toString());
        holder.content.setText(getAllPostBodies.get(position).getText());
        if(status1==0){
            holder.imageView.setImageResource(R.drawable.ic_round_thumb_up_24);
        }else if(status1==1){
            holder.imageView.setImageResource(R.drawable.ic_like_24);
        }
        int status=getAllPostBodies.get(position).getIsLike();
        if(status == 1) {
            holder.postUnlikeLikeButton.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.postUnlikeLikeButton.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
        }

        holder.postUnlikeLikeButton.setOnClickListener(v -> {
            setLike(feedId,userId,1,"0",holder);
            holder.postUnlikeLikeButton.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);

        });
        holder.imageView.setOnClickListener(v -> {
            setLike(feedId,userId,0,"1",holder);
            holder.imageView.setVisibility(View.GONE);
            holder.postUnlikeLikeButton.setVisibility(View.VISIBLE);
        });

        holder.postCommentButton.setOnClickListener(v->{
            Intent intent=new Intent(context, CommentsActivity.class);
            intent.putExtra("feed_id",feedId);
            context.startActivity(intent);
        });

        holder.deletePostButton.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Do you want this post ?");
            alertDialog.setPositiveButton("Yes", (dialog, which) -> userService.callbackDeletePostModel(feedId).enqueue(new Callback<DeletePostModel>() {
                @Override
                public void onResponse(Call<DeletePostModel> call, Response<DeletePostModel> response) {
                    notifyItemRemoved(position);
                    getAllPostBodies.remove(position);
                    Toast.makeText(context, "Post successfully deleted...", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<DeletePostModel> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })).setNegativeButton("No",null);
            alertDialog.show();
        });
    }

    private void setLike(int feedId, int userId, int status, String s, PostViewHolder holder) {
        userService.callbackLike(feedId,userId,status).enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(@NonNull Call<LikeModel> call, @NonNull Response<LikeModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<LikeModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getAllPostBodies.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,date,content;
        ImageView imageView,postUnlikeLikeButton,postCommentButton,deletePostButton;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.getPostUserName);
            content=itemView.findViewById(R.id.getPostContent);
            date=itemView.findViewById(R.id.getPostDate);
            imageView=itemView.findViewById(R.id.postLikeButton);
            postCommentButton=itemView.findViewById(R.id.postCommentButton);
            deletePostButton=itemView.findViewById(R.id.deletePostButton);
            postUnlikeLikeButton=itemView.findViewById(R.id.postUnlikeLikeButton);
        }
    }
}
