package com.example.apidemo.Friend.FriendList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apidemo.Friend.FriendList.Pojo.FriendListBody;
import com.example.apidemo.Friend.FriendList.Pojo.FriendListModel;
import com.example.apidemo.Friend.FriendList.RequestListPojo.RequestBody;
import com.example.apidemo.Friend.FriendList.RequestListPojo.RequestModel;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.PojoClasses.RequestClasses.AcceptRequestModel;
import com.example.apidemo.R;
import com.example.apidemo.Service.RequestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RequestService requestService;
    String token="";
    ImageView friendMenu;
    int user_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        initViews();
        getTheFriendList();
    }

    private void getTheFriendList()
    {
        requestService.callbackGetFriendList(user_id).enqueue(new Callback<FriendListModel>() {
            @Override
            public void onResponse(Call<FriendListModel> call, Response<FriendListModel> response) {
                if (response.isSuccessful()){
                    FriendListModel friendListModel=response.body();
                    assert friendListModel != null;
                    FriendListAdapter friendListAdapter=new FriendListAdapter(friendListModel.getBody(),FriendListActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(FriendListActivity.this));
                    recyclerView.setAdapter(friendListAdapter);
                }
            }
            @Override
            public void onFailure(Call<FriendListModel> call, Throwable t) {
                Toast.makeText(FriendListActivity.this, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        friendMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopUpBox();
            }
        });
    }

    private void openPopUpBox()
    {
        PopupMenu popupMenu=new PopupMenu(FriendListActivity.this,friendMenu);
        popupMenu.getMenuInflater().inflate(R.menu.friend_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.requestList){
                    showAllRequest();
                    getTheFriendList();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void initViews()
    {
        recyclerView=findViewById(R.id.friendListRecyclerView);
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        requestService= ApiClient.getClientTokn(token).create(RequestService.class);
        user_id=Integer.parseInt(sharedPreferences.getString("id","0"));
        friendMenu=findViewById(R.id.threeDots);
    }

    private void showAllRequest() {
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        View view=LayoutInflater.from(FriendListActivity.this).inflate(R.layout.request_list,null,false);
        alertDialog.setView(view);
        alertDialog.show();
        RecyclerView listRecyclerView=view.findViewById(R.id.requestList);
        requestService.callbackGetAllRequest(user_id).enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, Response<RequestModel> response) {
                if(response.isSuccessful()){
                    RequestModel requestModel=response.body();
                    FriendRquestListAdapter friendRquestListAdapter=new FriendRquestListAdapter(FriendListActivity.this,requestModel.body);
                    listRecyclerView.setLayoutManager(new LinearLayoutManager(FriendListActivity.this));
                    listRecyclerView.setAdapter(friendRquestListAdapter);
                }
            }
            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                Toast.makeText(FriendListActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ListViewHolder>
{

    Context context;
    List<FriendListBody> bodies;

    public FriendListAdapter(List<FriendListBody> body, FriendListActivity friendListActivity)
    {
        this.bodies=body;
        this.context=friendListActivity;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_view,null,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.name.setText(bodies.get(position).getUser().getUsername());
        holder.email.setText(bodies.get(position).getUser().getEmail());
    }

    @Override
    public int getItemCount() {
        return bodies.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,email;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.friendName);
            email=itemView.findViewById(R.id.friendEmail);
        }
    }
}



class FriendRquestListAdapter extends RecyclerView.Adapter<FriendRquestListAdapter.ViewHolder>
{
    Context context;
    List<RequestBody> bodies;
    public FriendRquestListAdapter(FriendListActivity friendListActivity, List<RequestBody> body) {
        this.bodies=body;
        this.context=friendListActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.acceptRequest.setVisibility(View.VISIBLE);
        holder.sendRequest.setVisibility(View.GONE);
        holder.dType.setText(bodies.get(position).getUser().getEmail());
        holder.name.setText(bodies.get(position).getUser().getUsername());
        holder.acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(context);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                SharedPreferences sh=context.getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
                String token=sh.getString("token","");
                int user_id=Integer.parseInt(sh.getString("id","0"));
                RequestService requestService= ApiClient.getClientTokn(token).create(RequestService.class);
                int friend_id=bodies.get(position).get_id();
                int accept=0;
                requestService.callbackAcceptRequest(user_id,friend_id,accept).enqueue(new Callback<AcceptRequestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<AcceptRequestModel> call, @NonNull Response<AcceptRequestModel> response) {
                        if(response.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(context, "Request accepted...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<AcceptRequestModel> call, @NonNull Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return bodies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,dType;
        ImageView sendRequest,acceptRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.userFullName);
            dType=itemView.findViewById(R.id.userDeviceType);
            acceptRequest= itemView.findViewById(R.id.acceptRequestButton);
            sendRequest= itemView.findViewById(R.id.sendRequestButton);
        }
    }
}