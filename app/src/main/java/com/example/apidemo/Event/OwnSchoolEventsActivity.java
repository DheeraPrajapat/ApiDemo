package com.example.apidemo.Event;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Event.OwnEvent.OwnSchoolEventsModel;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.TeamService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnSchoolEventsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TeamService teamService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_school_events);
        recyclerView=findViewById(R.id.ownSchoolEventList);
        getSchoolEvents();
    }

    private void getSchoolEvents() {
        ProgressDialog progressDialog=new ProgressDialog(OwnSchoolEventsActivity.this);
        progressDialog.setMessage("Please wait we are retrive your events...");
        progressDialog.show();
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String UserId=sharedPreferences.getString("id","0");
        String token=sharedPreferences.getString("token","");

        teamService= ApiClient.getClientTokn(token).create(TeamService.class);
        teamService.callOwnSchoolEventModel(UserId).enqueue(new Callback<OwnSchoolEventsModel>() {
            @Override
            public void onResponse(Call<OwnSchoolEventsModel> call, Response<OwnSchoolEventsModel> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    OwnSchoolEventsModel ownEventModel=response.body();
                    OwnSchoolEventAdapter ownSchoolEventAdapter=new OwnSchoolEventAdapter(ownEventModel.getBody(),OwnSchoolEventsActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(OwnSchoolEventsActivity.this));
                    recyclerView.setAdapter(ownSchoolEventAdapter);
                }
            }
            @Override
            public void onFailure(Call<OwnSchoolEventsModel> call, Throwable t) {
                    progressDialog.dismiss();
                Toast.makeText(OwnSchoolEventsActivity.this, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}