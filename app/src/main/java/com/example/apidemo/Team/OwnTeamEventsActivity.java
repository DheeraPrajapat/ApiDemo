package com.example.apidemo.Team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.apidemo.Event.OwnEvent.OwnEventModel;
import com.example.apidemo.Event.OwnEvent.OwnTeamEventModel;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.PojoClasses.GetProfile.GetProfileModel;
import com.example.apidemo.R;
import com.example.apidemo.Service.TeamService;
import com.example.apidemo.Service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnTeamEventsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TeamService teamService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_team_events);
        recyclerView=findViewById(R.id.ownTeamEventList);
        getTeamId();
    }

    private void getTeamEvents(int teamId)
    {
        ProgressDialog progressDialog=new ProgressDialog(OwnTeamEventsActivity.this);
        progressDialog.setMessage("Please wait we are retrive your events...");
        progressDialog.show();
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String token=sharedPreferences.getString("token","");
        teamService=ApiClient.getClientTokn(token).create(TeamService.class);
        teamService.callOwnTeamEventModel(teamId).enqueue(new Callback<OwnTeamEventModel>() {
            @Override
            public void onResponse(Call<OwnTeamEventModel> call, Response<OwnTeamEventModel> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    OwnTeamEventModel ownEventModel=response.body();
                    OwnTeamEventAdapter ownTeamEventAdapte=new OwnTeamEventAdapter(ownEventModel.getBody(),OwnTeamEventsActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(OwnTeamEventsActivity.this));
                    recyclerView.setAdapter(ownTeamEventAdapte);
                }
            }

            @Override
            public void onFailure(Call<OwnTeamEventModel> call, Throwable t) {
                Log.d("error",t.getMessage());
                Toast.makeText(OwnTeamEventsActivity.this, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTeamId()
    {
        UserService userService;
        final int[] teamId = {0};
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String token=sharedPreferences.getString("token","");
        String user_id= sharedPreferences.getString("id","");
        userService= ApiClient.getClientTokn(token).create(UserService.class);
        userService.callGetUserInformation(user_id).enqueue(new Callback<GetProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<GetProfileModel> call, Response<GetProfileModel> response) {
                if(response.isSuccessful()){
                    GetProfileModel getProfileModel=response.body();
                    assert getProfileModel != null;
                    teamId[0] = Integer.parseInt(getProfileModel.body.team_id);
                    getTeamEvents(teamId[0]);
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetProfileModel> call, @NonNull Throwable t) {
                Toast.makeText(OwnTeamEventsActivity.this, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {

    }
}