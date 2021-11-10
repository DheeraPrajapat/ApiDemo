package com.example.apidemo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Activities.MainActivity;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.PojoClasses.GetPost.GetAllPostBody;
import com.example.apidemo.R;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.SignUpPojo.LikeModel;

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

//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int status=getAllPostBodies.get(position).getIsLike();
//                if(status == 1){
//                                setLike(feedId,userId,0,R.drawable.ic_round_thumb_up_24);
//                            }else if (status==0){
//                                setLike(feedId,userId,1,R.drawable.ic_like_24);
//                            }
//            }
//            private void setLike(int feedId,int userId , int status,int thumb) {
//                userService.callbackLike(feedId,userId,status).enqueue(new Callback<LikeModel>() {
//                    @Override
//                    public void onResponse(@NonNull Call<LikeModel> call, @NonNull Response<LikeModel> response) {
//                        if(response.isSuccessful()){
//                            holder.imageView.setImageResource(thumb);
//                            UserPostAdapter.this.notifyDataSetChanged();
//                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    @Override
//                    public void onFailure(@NonNull Call<LikeModel> call, @NonNull Throwable t) {
//                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        int status=getAllPostBodies.get(position).getIsLike();
        if(status == 1) {
            holder.postUnlikeLikeButton.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.postUnlikeLikeButton.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
        }

        holder.postUnlikeLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLike(feedId,userId,1,"0",holder);
                holder.postUnlikeLikeButton.setVisibility(View.GONE);
                holder.imageView.setVisibility(View.VISIBLE);

            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLike(feedId,userId,0,"1",holder);
                holder.imageView.setVisibility(View.GONE);
                holder.postUnlikeLikeButton.setVisibility(View.VISIBLE);

            }
        });
    }

    private void setLike(int feedId, int userId, int status, String s, PostViewHolder holder) {
        userService.callbackLike(feedId,userId,status).enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(@NonNull Call<LikeModel> call, @NonNull Response<LikeModel> response) {
                if(response.isSuccessful()){
//                    if (s.equalsIgnoreCase("0")){
//                        holder.postUnlikeLikeButton.setVisibility(View.VISIBLE);
//                        holder.imageView.setVisibility(View.GONE);
////                        UserPostAdapter.this.notifyDataSetChanged();
//
//                    }else {
//                        holder.postUnlikeLikeButton.setVisibility(View.GONE);
//                        holder.imageView.setVisibility(View.VISIBLE);
////                        UserPostAdapter.this.notifyDataSetChanged();

//                    }
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
        ImageView imageView,postUnlikeLikeButton;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.getPostUserName);
            content=itemView.findViewById(R.id.getPostContent);
            date=itemView.findViewById(R.id.getPostDate);
            imageView=itemView.findViewById(R.id.postLikeButton);
            postUnlikeLikeButton=itemView.findViewById(R.id.postUnlikeLikeButton);
        }
    }
}
