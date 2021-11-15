package com.example.apidemo.Friend.FriendList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apidemo.Friend.FriendList.Pojo.FriendListBody;
import com.example.apidemo.Friend.FriendList.Pojo.FriendListModel;
import com.example.apidemo.Package.ApiClient;
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
    }

    private void initViews()
    {
        recyclerView=findViewById(R.id.friendListRecyclerView);
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        requestService= ApiClient.getClientTokn(token).create(RequestService.class);
        user_id=Integer.parseInt(sharedPreferences.getString("id","0"));
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