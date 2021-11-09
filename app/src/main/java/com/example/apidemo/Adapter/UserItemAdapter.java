package com.example.apidemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Activities.MainActivity;
import com.example.apidemo.R;
import com.example.apidemo.SearchUser.SearchBody;
import com.example.apidemo.SearchUser.SearchModel;
import com.example.apidemo.SignUpPojo.GetProfileBody;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ViewHolder>
{
    List<SearchBody> arrayList;
    Context context;

    public UserItemAdapter(List<SearchBody> body, MainActivity mainActivity) {
        this.arrayList = body;
        this.context = mainActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dType.setText(arrayList.get(position).getDevice_type());
        holder.name.setText(arrayList.get(position).getEmail());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context,GetUserInformation.class);
//                intent.putExtra("name",arrayList.get(position).getFull_name().toString());
//                intent.putExtra("email",arrayList.get(position).getEmail().toString());
//                intent.putExtra("address",arrayList.get(position).getAddress().toString());
//                intent.putExtra("Device Type",arrayList.get(position).getDevice_type().toString());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,dType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.userFullName);
            dType=itemView.findViewById(R.id.userDeviceType);
        }
    }
}
