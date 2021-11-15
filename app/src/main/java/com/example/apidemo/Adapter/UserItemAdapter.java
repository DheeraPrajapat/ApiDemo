package com.example.apidemo.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Activities.SearchUserActivity;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.PojoClasses.RequestClasses.SendRequestModel;
import com.example.apidemo.R;
import com.example.apidemo.PojoClasses.SearchUser.SearchBody;
import com.example.apidemo.Service.RequestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ViewHolder>
{
    List<SearchBody> arrayList;
    Context context;
    public UserItemAdapter(List<SearchBody> body, SearchUserActivity mainActivity) {
        this.arrayList = body;
        this.context = mainActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view= LayoutInflater.from(context).inflate(R.layout.user_item,null,false);
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
        holder.sendRequest.setOnClickListener(v -> {
            ProgressDialog progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            SharedPreferences sh=context.getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
            String token=sh.getString("token","");
            int user_id=Integer.parseInt(sh.getString("id","0"));
            RequestService requestService= ApiClient.getClientTokn(token).create(RequestService.class);
            int friend_id=arrayList.get(position).get_id();
            requestService.callbackSendRequest(user_id,friend_id).enqueue(new Callback<SendRequestModel>() {
                @Override
                public void onResponse(@NonNull Call<SendRequestModel> call, @NonNull Response<SendRequestModel> response) {
                    if(response.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(context, "friend request successfully sent...", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<SendRequestModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,dType;
        ImageView sendRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.userFullName);
            dType=itemView.findViewById(R.id.userDeviceType);
            sendRequest= itemView.findViewById(R.id.sendRequestButton);
        }
    }
}
