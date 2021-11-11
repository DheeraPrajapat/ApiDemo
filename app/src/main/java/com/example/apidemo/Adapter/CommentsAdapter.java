package com.example.apidemo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.PojoClasses.GetAllComments.GetAllCommentsBody;
import com.example.apidemo.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>
{
    Context context;
    List<GetAllCommentsBody> getAllCommentsBodies;

    public CommentsAdapter(Context context, List<GetAllCommentsBody> getAllCommentsBodies) {
        this.context = context;
        this.getAllCommentsBodies = getAllCommentsBodies;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_views,null,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.userId.setText(String.valueOf(getAllCommentsBodies.get(position).getCommenter_id()));
        holder.commentContent.setText(getAllCommentsBodies.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return getAllCommentsBodies.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder
    {
        TextView userId,commentContent;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            userId=itemView.findViewById(R.id.commentUserId);
            commentContent=itemView.findViewById(R.id.commentUserContent);
        }
    }
}
