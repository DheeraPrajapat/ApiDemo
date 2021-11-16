package com.example.apidemo.Team;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.TeamService;
import com.example.apidemo.Team.CreateTeamPojo.CreateTeamModel;
import com.example.apidemo.Team.DeleteTeamPojo.DeleteModel;
import com.example.apidemo.Team.UpdateTeamInfo.UpdateModel;
import com.example.apidemo.Team.UserTeamPojo.UserTeamModel;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTeamFragment extends Fragment {
    TextView textView,createTeamBtn,userTeamInfo;
    EditText teamName;
    ImageView userTeamImage,deleteUserTeam,editTeamInformation;
    RelativeLayout relativeLayout1,relativeLayout2;
    CheckBox checkBox;
    TeamService teamService;
    String token,team_id;
    int id,admin_Id;
    TextInputEditText teamDescription;
        @SuppressLint("InflateParams")
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_team,null,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getUserTeam();
        final NavController navController = Navigation.findNavController(view);
        textView.setOnClickListener(v->{
            navController.navigate(R.id.action_createTeamFragment_to_userTeamFragment);
        });
        createTeamBtn.setOnClickListener(v -> {
            String title=teamName.getText().toString().trim();
            String desc=teamDescription.getText().toString().trim();
            if (title.isEmpty() && desc.isEmpty()){
                Toast.makeText(getActivity(), "Fill the all required field", Toast.LENGTH_SHORT).show();
//                Snackbar.make(snackBarView,"Please fill the required information!",Snackbar.LENGTH_SHORT).show();
            }else {
                createTeam(title,desc);
            }
        });
        deleteUserTeam.setOnClickListener(v -> DeleteUserTeam());
        editTeamInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getContext().getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
                String updateToken=sharedPreferences.getString("token","");
                int updateUserId=Integer.parseInt(sharedPreferences.getString("id","0"));
                TeamService service=ApiClient.getClientTokn(updateToken).create(TeamService.class);
                AlertDialog alertDialog=new AlertDialog.Builder(getContext(),R.style.verification_done).create();
                View view1=LayoutInflater.from(getContext()).inflate(R.layout.edit_team_information,null,false);
                alertDialog.setView(view1);
                alertDialog.setCancelable(false);
                alertDialog.show();
                EditText title,desc;
                Button update;
                title=view1.findViewById(R.id.editInfoTitle);
                desc=view1.findViewById(R.id.editInfoDescription);
                update=view1.findViewById(R.id.teamUpdateBtn);
                final int[] team_id = {0};
                service.callbackGetUserTeam(String.valueOf(updateUserId)).enqueue(new Callback<UserTeamModel>() {
                    @Override
                    public void onResponse(Call<UserTeamModel> call, Response<UserTeamModel> response) {
                        if(response.isSuccessful()){
                            UserTeamModel userTeamModel=response.body();
                            assert userTeamModel != null;
                            team_id[0] =userTeamModel.body.get_id();
                            title.setText(userTeamModel.body.getTitle());
                            desc.setText(userTeamModel.body.getDescription());
                        }
                    }
                    @Override
                    public void onFailure(Call<UserTeamModel> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                update.setOnClickListener(v1->{
                    if(title.getText().toString().trim().equals("") || desc.getText().toString().trim().equals("")){
                        Toast.makeText(getContext(), "Fill the all required field....", Toast.LENGTH_SHORT).show();
                    }else{
                        ProgressDialog progressDialog=new ProgressDialog(getContext());
                        progressDialog.setMessage("Please wait.....");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        service.callUpdateTeam(team_id[0],title.getText().toString(),desc.getText().toString()).enqueue(new Callback<UpdateModel>() {
                            @Override
                            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                                if(response.isSuccessful()){
                                    progressDialog.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(getContext(), "Team updated....", Toast.LENGTH_SHORT).show();
                                    getUserTeam();
                                }
                            }
                            @Override
                            public void onFailure(Call<UpdateModel> call, Throwable t) {
                                progressDialog.dismiss();
                                alertDialog.dismiss();
                                Toast.makeText(getContext(), "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void DeleteUserTeam()
    {
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
        String deleteToken=sharedPreferences.getString("token","");
        String deleteAdminId=sharedPreferences.getString("id","");
        TeamService team=ApiClient.getClientTokn(deleteToken).create(TeamService.class);
        team.callbackGetUserTeam(deleteAdminId).enqueue(new Callback<UserTeamModel>() {
            @Override
            public void onResponse(Call<UserTeamModel> call, Response<UserTeamModel> response) {
                if(response.isSuccessful()){
                    UserTeamModel userTeamModel=response.body();
                    String teamId= String.valueOf(userTeamModel.body.get_id());
                    int adminId=Integer.parseInt(deleteAdminId);
                    team.callDeleteTeam(teamId,adminId).enqueue(new Callback<DeleteModel>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<DeleteModel> call, Response<DeleteModel> response) {
                            if(response.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Team deleted....", Toast.LENGTH_SHORT).show();
                                userTeamInfo.setText("No team available!");
                            }
                        }
                        @Override
                        public void onFailure(Call<DeleteModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<UserTeamModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserTeam()
    {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
        String userId=sharedPreferences.getString("id","");
        String token=sharedPreferences.getString("token","");
        TeamService teamService1=ApiClient.getClientTokn(token).create(TeamService.class);
        teamService1.callbackGetUserTeam(userId).enqueue(new Callback<UserTeamModel>() {
             @SuppressLint("SetTextI18n")
             @Override
             public void onResponse(@NonNull Call<UserTeamModel> call, @NonNull Response<UserTeamModel> response) {
                 if(response.isSuccessful()){
                     UserTeamModel userTeamModel=response.body();
                     assert userTeamModel != null;
                     team_id= String.valueOf(userTeamModel.body.get_id());
                     admin_Id=userTeamModel.body.admin_id;
                     if(userTeamModel.body.getImage()==null){
                         userTeamImage.setImageResource(R.drawable.ic_psychology);
                     }else {
                         Glide.with(getContext()).load(userTeamModel.body.getImage()).into(userTeamImage);
                     }
                     userTeamInfo.setText("Title :- "+userTeamModel.body.getTitle()+"\n"+
                              "Admin name :- "+userTeamModel.getBody().getAdmin_id()+"\n"+"Region :- "+ userTeamModel.body.getRegion()+"\n"+"Description :- "+userTeamModel.body.getDescription());
                 }
             }
             @Override
             public void onFailure(Call<UserTeamModel> call, Throwable t) {
                 Toast.makeText(getContext(), "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });
    }

    private void createTeam(String title,String desc)
    {
        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
        int createUserserId=Integer.parseInt(sharedPreferences.getString("id","0"));
        String createUsertoken=sharedPreferences.getString("token","");
        TeamService service=ApiClient.getClientTokn(createUsertoken).create(TeamService.class);
        service.callbackCreateTeam(title,createUserserId,desc).enqueue(new Callback<CreateTeamModel>() {
            @Override
            public void onResponse(@NonNull Call<CreateTeamModel> call, @NonNull Response<CreateTeamModel> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    teamName.setText("");
                    teamDescription.setText("");
                    getUserTeam();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<CreateTeamModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void initViews(View view) {
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("MysharedPref", Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        Log.d("information",token);
        id=Integer.parseInt(sharedPreferences.getString("id","0"));
        Log.d("information",String.valueOf(id));
        teamService= ApiClient.getClientTokn(token).create(TeamService.class);
        textView=view.findViewById(R.id.createTeamBackbtn);
        deleteUserTeam=view.findViewById(R.id.deleteUserTeam);
        createTeamBtn=view.findViewById(R.id.txt_createTeamBtn);
        teamName=view.findViewById(R.id.createTeamName);
        teamDescription=view.findViewById(R.id.createDescription);
        checkBox=view.findViewById(R.id.cbPrivate);
        relativeLayout1=view.findViewById(R.id.createTeamRelativeRelative);
        relativeLayout2=view.findViewById(R.id.getUserTeamRelative);
        userTeamImage=view.findViewById(R.id.getUserTeamImage);
        userTeamInfo=view.findViewById(R.id.getUserTeamInformation);
        editTeamInformation=view.findViewById(R.id.editTeamInformation);
    }
}