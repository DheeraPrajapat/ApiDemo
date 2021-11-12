package com.example.apidemo.Team;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.R;
import com.example.apidemo.Service.TeamService;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.Team.CreateTeamPojo.CreateTeamModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTeamFragment extends Fragment {
    TextView textView,createTeamBtn;
    EditText teamName;
    CheckBox checkBox;
    int visibility=1;
    View snackBarView;
    TextInputEditText teamDescription;
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_team,null,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        final NavController navController = Navigation.findNavController(view);
        textView.setOnClickListener(v->{
            navController.navigate(R.id.action_createTeamFragment_to_userTeamFragment);
        });
        createTeamBtn.setOnClickListener(v -> {
            String title=teamName.getText().toString();
            String desc=teamDescription.getText().toString();
            if (title.isEmpty() && desc.isEmpty()){
                Snackbar.make(snackBarView,"Please fill the required information!",Snackbar.LENGTH_SHORT).show();
            }else {
                createTeam(title,desc);
            }
        });
    }

    private void createTeam(String title,String desc)
    {
        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("MysharedPref", Context.MODE_PRIVATE);
        String token=sharedPreferences.getString("token","");
        int id=Integer.parseInt(sharedPreferences.getString("id","0"));
        TeamService teamService= ApiClient.getClientTokn(token).create(TeamService.class);

        teamService.callbackCreateTeam(title,id,desc).enqueue(new Callback<CreateTeamModel>() {
            @Override
            public void onResponse(Call<CreateTeamModel> call, Response<CreateTeamModel> response) {
                if(response.isSuccessful()){
                    teamName.setText("");
                    teamDescription.setText("");
                    progressDialog.dismiss();
                    Snackbar.make(snackBarView,response.message(),Snackbar.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CreateTeamModel> call, Throwable t) {
                progressDialog.dismiss();
                    Snackbar.make(snackBarView,t.getMessage(),Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(View view) {
        textView=view.findViewById(R.id.createTeamBackbtn);
        createTeamBtn=view.findViewById(R.id.txt_createTeamBtn);
        teamName=view.findViewById(R.id.createTeamName);
        teamDescription=view.findViewById(R.id.createDescription);
        checkBox=view.findViewById(R.id.cbPrivate);
        snackBarView=view.findViewById(R.id.content);
    }
}