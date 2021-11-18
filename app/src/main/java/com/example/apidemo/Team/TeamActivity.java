package com.example.apidemo.Team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apidemo.Event.AdminCompetition.AdminCompetitionBody;
import com.example.apidemo.Event.AdminCompetition.AdminCompetitionModel;
import com.example.apidemo.Event.OwnEvent.OwnEventModel;
import com.example.apidemo.Friend.FriendList.FriendListActivity;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.PojoClasses.GetProfile.GetProfileModel;
import com.example.apidemo.R;
import com.example.apidemo.Service.TeamService;
import com.example.apidemo.Service.UserService;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamActivity extends AppCompatActivity {
    ImageView optionsImage;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        initViews();
        optionsImage.setOnClickListener(v -> openPopUpBox());
    }

    private void openPopUpBox() {
            PopupMenu popupMenu=new PopupMenu(this,optionsImage);
            popupMenu.getMenuInflater().inflate(R.menu.team_menu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId()==R.id.CreateEvent){
                        startActivity(new Intent(TeamActivity.this,CreateEventActivity.class)
                               .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else if(item.getItemId()==R.id.Admin_competition){
                        showAdminCompetitions();
                    }else if(item.getItemId()==R.id.OwnTeamEvent){
                        showOwnTeamEvent("team");
                    }else if(item.getItemId()==R.id.OwnSchoolEvent){
                        showOwnTeamEvent("school");
                    }
                    return false;
                }
            });
            popupMenu.show();
    }

    private void showAdminCompetitions()
    {
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.request_list,null,false);
        alertDialog.setView(view);
        alertDialog.show();
        RecyclerView listRecyclerView=view.findViewById(R.id.requestList);
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String token=sharedPreferences.getString("token","");
        TeamService teamService= ApiClient.getClientTokn(token).create(TeamService.class);
        teamService.callbackGetAdminCompetition().enqueue(new Callback<AdminCompetitionModel>() {
            @Override
            public void onResponse(Call<AdminCompetitionModel> call, Response<AdminCompetitionModel> response) {
                if(response.isSuccessful()){
                    AdminCompetitionModel adminCompetitionModel=response.body();
                    AdminCompetitionsAdapter adminCompetitionsAdapter=new AdminCompetitionsAdapter(adminCompetitionModel.getBody(),TeamActivity.this);
                    listRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    listRecyclerView.setAdapter(adminCompetitionsAdapter);
                }
            }
            @Override
            public void onFailure(Call<AdminCompetitionModel> call, Throwable t) {
                Toast.makeText(TeamActivity.this, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
            optionsImage=findViewById(R.id.thereedots);
    }
    private void showOwnTeamEvent(String type) {
            SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
            String token=sharedPreferences.getString("token","");
            String userId=sharedPreferences.getString("id","");
            TeamService teamService=ApiClient.getClientTokn(token).create(TeamService.class);
            AlertDialog alertDialog=new AlertDialog.Builder(this).create();
            View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.event_list,null,false);
            alertDialog.setView(view);
            RecyclerView recyclerView=view.findViewById(R.id.ownEventList);
            if (type.equals("team")){
                teamService.callOwnSchoolEventModel(userId).enqueue(new Callback<OwnEventModel>() {
                    @Override
                    public void onResponse(Call<OwnEventModel> call, Response<OwnEventModel> response) {
                        if(response.isSuccessful()){
                            OwnEventModel ownEventModel=response.body();
                            OwnSchoolEventAdapter ownSchoolEventAdapter=new OwnSchoolEventAdapter(ownEventModel.getBody().getSchool_event(), TeamActivity.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(TeamActivity.this));
                            recyclerView.setAdapter(ownSchoolEventAdapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<OwnEventModel> call, Throwable t) {
                        Toast.makeText(TeamActivity.this, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(type.equalsIgnoreCase("school")){
                final String[] teamId = {""};
                UserService userService=ApiClient.getClientTokn(token).create(UserService.class);
                userService.callGetUserInformation(userId).enqueue(new Callback<GetProfileModel>() {
                    @Override
                    public void onResponse(Call<GetProfileModel> call, Response<GetProfileModel> response) {
                        if(response.isSuccessful()){
                            GetProfileModel getProfileModel=response.body();
                            teamId[0] =getProfileModel.getBody().getTeam_id();
                        }
                    }
                    @Override
                    public void onFailure(Call<GetProfileModel> call, Throwable t) {
                        Toast.makeText(TeamActivity.this, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                teamService.callOwnTeamEventModel(Integer.parseInt(Arrays.toString(teamId))).enqueue(new Callback<OwnEventModel>() {
                    @Override
                    public void onResponse(Call<OwnEventModel> call, Response<OwnEventModel> response) {
                        if(response.isSuccessful()){
                            OwnEventModel ownEventModel=response.body();
                            OwnTeamEventAdapter ownSchoolEventAdapter=new OwnTeamEventAdapter(ownEventModel.getBody().getTeam_event(), TeamActivity.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(TeamActivity.this));
                            recyclerView.setAdapter(ownSchoolEventAdapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<OwnEventModel> call, Throwable t) {
                        Toast.makeText(TeamActivity.this, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            alertDialog.show();
    }

}


class AdminCompetitionsAdapter extends RecyclerView.Adapter<AdminCompetitionsAdapter.ViewHolder>
{

    List<AdminCompetitionBody> bodyList;
    Context context;

    public AdminCompetitionsAdapter(List<AdminCompetitionBody> bodyList, Context context) {
        this.bodyList = bodyList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.friend_event_view,null,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getEt_name.setText(bodyList.get(position).getTitle());
        holder.getGetEt_description.setText(bodyList.get(position).getDescription());
        holder.getGetEt_startTime.setText(String.format("Start Time: %s", bodyList.get(position).getStart_time()));
        holder.getGetGetEt_endTime.setText(String.format("End Time: %s",bodyList.get(position).getEnd_time()));
        if(bodyList.get(position).getEvent_type()==0){
            holder.getGetEt_Type.setText("Team");
        }else if(bodyList.get(position).getEvent_type()==2){
            holder.getGetEt_Type.setText("School");
        }else {
            holder.getGetEt_Type.setText("Team");
        }
    }

    @Override
    public int getItemCount() {
        return bodyList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView getEt_name,getGetEt_description,getGetEt_startTime,getGetGetEt_endTime,getGetEt_Type;
        RelativeLayout friendItem,eventItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            getEt_name=itemView.findViewById(R.id.getEventName);
            getGetEt_description=itemView.findViewById(R.id.getEventDescription);
            getGetEt_startTime=itemView.findViewById(R.id.getEventStartTime);
            getGetGetEt_endTime=itemView.findViewById(R.id.getEventEndTime);
            getGetEt_Type=itemView.findViewById(R.id.getEventType);
            friendItem=itemView.findViewById(R.id.friendListItemLayout);
            eventItem=itemView.findViewById(R.id.eventListItemLayout);

            friendItem.setVisibility(View.GONE);
            eventItem.setVisibility(View.VISIBLE);
        }
    }
}