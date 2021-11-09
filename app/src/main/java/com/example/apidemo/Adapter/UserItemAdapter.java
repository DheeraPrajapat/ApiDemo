package com.example.apidemo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dType.setText(arrayList.get(position).getDevice_type());
        holder.name.setText(arrayList.get(position).getEmail());
        holder.itemView.setOnClickListener(v -> {
            AlertDialog alert = new AlertDialog.Builder(context, R.style.verification_done).create();
            View view=LayoutInflater.from(context).inflate(R.layout.user_item_info,null,false);
            alert.setView(view);
            alert.show();
            alert.setCancelable(false);
            TextView name,dType,email,address;
            Button closeAlertBg;
            closeAlertBg=view.findViewById(R.id.close_alert_box);
            name=view.findViewById(R.id.user_item_fullname);
            dType=view.findViewById(R.id.user_item_device);
            email=view.findViewById(R.id.user_item_email);
            address=view.findViewById(R.id.user_item_addess);
            name.setText(arrayList.get(position).getFull_name());
            dType.setText(arrayList.get(position).getDevice_type());
            email.setText(arrayList.get(position).getEmail());
            address.setText(arrayList.get(position).getAddress());
            closeAlertBg.setOnClickListener(v1 -> alert.dismiss());
        });
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
