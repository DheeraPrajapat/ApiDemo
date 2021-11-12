package com.example.apidemo.Team;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.TeamService;
import com.example.apidemo.Team.GetAllTeams.GetAllTeamBody;
import com.example.apidemo.Team.GetAllTeams.GetAllTeamModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTeamFragment extends Fragment {
    TextView unAvailable,createTeam;
    RecyclerView recyclerView;
    String token="",id="";
    TeamService teamService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_team, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getUserTeams(view);
        final NavController navController = Navigation.findNavController(view);
        createTeam.setOnClickListener(v -> navController.navigate(R.id.action_userTeamFragment_to_createTeamFragment));
    }

    private void initViews(View view){
        recyclerView=view.findViewById(R.id.userTeamRecycle);
        //optionsImage=view.findViewById(R.id.fragmentOptionsImage);
        createTeam=view.findViewById(R.id.createTeam);
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        id=sharedPreferences.getString("id","");
        teamService= ApiClient.getClientTokn(token).create(TeamService.class);
        unAvailable=view.findViewById(R.id.noTeamAvailable);
    }

    private void getUserTeams(View view){
        teamService.callbackGetAllTeam().enqueue(new Callback<GetAllTeamModel>() {
            @Override
            public void onResponse(Call<GetAllTeamModel> call, Response<GetAllTeamModel> response) {
                if(response.isSuccessful()){
                    GetAllTeamModel getAllTeamModel=response.body();
                    assert getAllTeamModel != null;
                    if(!getAllTeamModel.body.isEmpty()){
                        AllTeamAdapter allTeamAdapter=new AllTeamAdapter(getAllTeamModel.body,getActivity());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(allTeamAdapter);
                    }else {
                        recyclerView.setVisibility(View.GONE);
                        unAvailable.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<GetAllTeamModel> call, Throwable t) {
                View view1=view.findViewById(R.id.content);
                Snackbar.make(view1,
                        t.getMessage(),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}

class AllTeamAdapter extends RecyclerView.Adapter<AllTeamAdapter.ViewHolder> {
    List<GetAllTeamBody> userTeamBodies;
    Activity context;
    String baseUrl="http://18.168.7.167:3004/";

    public AllTeamAdapter(List<GetAllTeamBody> body, FragmentActivity activity) {
        this.context=activity;
        this.userTeamBodies=body;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.team_view,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.creatorName.setText(userTeamBodies.get(position).getUser().getUsername());
            holder.teamName.setText(userTeamBodies.get(position).getTitle());
        Glide.with(context).load(baseUrl+userTeamBodies.get(position).getImage()).into(holder.teamImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Team Information");
                alert.setCancelable(false).setMessage("Team name :- "+userTeamBodies.get(position).getTitle()+"\n"
                                +"Creator name :- "+userTeamBodies.get(position).getUser().getUsername()+"\n"
                                +"Region :- "+userTeamBodies.get(position).getRegion()+"\n"
                                +"Description :- "+userTeamBodies.get(position).getDescription()
                ).setPositiveButton("Ok",null);
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userTeamBodies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamName,creatorName;
        ImageView teamImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamImage=itemView.findViewById(R.id.teamImage);
            teamName=itemView.findViewById(R.id.teamName);
            creatorName=itemView.findViewById(R.id.creatorName);
        }
    }
}

