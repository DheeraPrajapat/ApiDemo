package com.example.apidemo.Team;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.Event.AdminCompetition.AdminCompetitionBody;
import com.example.apidemo.Event.AdminCompetition.AdminCompetitionModel;
import com.example.apidemo.Package.ApiClient;
import com.example.apidemo.PojoClasses.GetProfile.GetProfileModel;
import com.example.apidemo.R;
import com.example.apidemo.Service.TeamService;
import com.example.apidemo.Service.UserService;
import com.example.apidemo.Team.CreateTeamPojo.CreateTeamModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateEventActivity extends AppCompatActivity {
    ImageView eventImage;
    EditText eventTitle,eventDescription;
    Button startBtn,endBtn,colorBtn,createEventButton;
    RadioButton schoolRadioBtn,teamRadioButton;
    int  mHour, mMinute,defaultColor;
    RelativeLayout layout,CreateEventRelative;
    UserService userService;
    TeamService teamService;
    RecyclerView eventRecyclerView;
    int user_id,team_id,event_type=0,viewShow=0;
    String token,startTime="",endTime="";
    View snackBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        snackBarView=findViewById(R.id.content);
        initViews();
        schoolRadioBtn.setOnClickListener(v -> {
            event_type=0;
            schoolRadioBtn.setChecked(true);
            teamRadioButton.setChecked(false);
        });
        teamRadioButton.setOnClickListener(v -> {
            event_type=1;
            schoolRadioBtn.setChecked(false);
            teamRadioButton.setChecked(true);
        });
        startBtn.setOnClickListener(v-> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            @SuppressLint("SetTextI18n")
            TimePickerDialog timePickerDialog=new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                startBtn.setText(hourOfDay+":"+minute);
                startTime=hourOfDay+":"+minute;
            },mHour,mMinute,false);
            timePickerDialog.show();
        });
        endBtn.setOnClickListener(v-> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            @SuppressLint("SetTextI18n")
            TimePickerDialog timePickerDialog=new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                endBtn.setText(hourOfDay+":"+minute);
                endTime=hourOfDay+":"+minute;
            },mHour,mMinute,false);
            timePickerDialog.show();
        });
        colorBtn.setOnClickListener(v-> showColorPicker(colorBtn));
        eventImage.setOnClickListener(v -> {
            if(viewShow==0){
                viewShow=1;
                CreateEventRelative.setVisibility(View.GONE);
                eventRecyclerView.setVisibility(View.VISIBLE);
            }else if(viewShow==1){
                viewShow=0;
                CreateEventRelative.setVisibility(View.VISIBLE);
                eventRecyclerView.setVisibility(View.GONE);
            }
        });

        createEventButton.setOnClickListener(v->{
            String et_itle=eventTitle.getText().toString().trim();
            String et_Description=eventDescription.getText().toString().trim();
            if(et_Description.equals("") || et_itle.equals("")){
                Toast.makeText(this, "Fill the all required field!", Toast.LENGTH_SHORT).show();
            }else if(startTime.equals("") || endTime.equals("")){
                Toast.makeText(this, "Please select the particular time!", Toast.LENGTH_SHORT).show();
            }else {
                createTimeByTeam(et_itle,et_Description,event_type,defaultColor,startTime,endTime);
            }
        });
    }

    private void showColorPicker(Button button)
    {
        AmbilWarnaDialog colorPicker=new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor=color;
                button.setBackgroundColor(color);
            }
        });
        colorPicker.show();
    }

    private void showTimePicker(Button Btn)
    {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        @SuppressLint("SetTextI18n")
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> Btn.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void initViews() {
        eventImage=findViewById(R.id.createNewEvent);
        eventTitle=findViewById(R.id.et_EventTitle);
        eventDescription=findViewById(R.id.et_EventDescription);
        startBtn=findViewById(R.id.startTime);
        endBtn=findViewById(R.id.endTime);
        colorBtn=findViewById(R.id.selectColor);
        schoolRadioBtn=findViewById(R.id.schoolEvent);
        teamRadioButton=findViewById(R.id.teamEvent);
        layout= findViewById(R.id.layout);
        defaultColor= ContextCompat.getColor(CreateEventActivity.this,R.color.black);
        eventRecyclerView=findViewById(R.id.eventList);
        CreateEventRelative=findViewById(R.id.CreateEventRelative);
        createEventButton=findViewById(R.id.createEventButton);
        SharedPreferences sharedPreferences=getSharedPreferences("MysharedPref",MODE_PRIVATE);
        user_id=Integer.parseInt(sharedPreferences.getString("id","0"));
        token=sharedPreferences.getString("token","");
        userService= ApiClient.getClientTokn(token).create(UserService.class);
        teamService=ApiClient.getClientTokn(token).create(TeamService.class);
        setLayout();
        getAndSetInfo();
    }
    private void getAndSetInfo() {
        userService.callGetUserInformation(String.valueOf(user_id)).enqueue(new Callback<GetProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<GetProfileModel> call, @NonNull Response<GetProfileModel> response) {
                if(response.isSuccessful()){
                    GetProfileModel getProfileModel=response.body();
                    assert getProfileModel != null;
                    team_id=Integer.parseInt(getProfileModel.body.getTeam_id());
                    Log.d("value","Team id :-"+team_id);
                 }
            }
            @Override
            public void onFailure(@NonNull Call<GetProfileModel> call, @NonNull Throwable t) {
                Toast.makeText(CreateEventActivity.this, "Failed :- "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLayout(){
        if(viewShow==0){
            CreateEventRelative.setVisibility(View.VISIBLE);
            eventRecyclerView.setVisibility(View.GONE);
        }else if(viewShow==1){
            CreateEventRelative.setVisibility(View.GONE);
            eventRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void createTimeByTeam(String et_itle, String et_Description, int event_type, int defaultColor, String startTime, String endTime)
    {
        getAndSetInfo();
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        user_id=Integer.parseInt(sharedPreferences.getString("id","0"));
        token=sharedPreferences.getString("token","");
        getAndSetInfo();
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        teamService.callbackCreateEvent(et_itle,user_id,team_id,startTime,endTime,et_Description,event_type,String.valueOf(defaultColor))
                .enqueue(new Callback<CreateTeamModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CreateTeamModel> call, @NonNull Response<CreateTeamModel> response) {
                        if(response.isSuccessful()){
                            progressDialog.dismiss();
                            AlertDialog.Builder alertBuilder=new AlertDialog.Builder(CreateEventActivity.this);
                            alertBuilder.setMessage("Event created successfully...");
                            alertBuilder.setCancelable(false);
                            alertBuilder.setPositiveButton("Okay", (dialog, which) -> {
                                Toast.makeText(CreateEventActivity.this, "Thank you!"+"\uD83D", Toast.LENGTH_SHORT).show();
                                        clearValues();
                            });
                            alertBuilder.show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<CreateTeamModel> call, @NonNull Throwable t) {
                        progressDialog.dismiss();
                        Snackbar.make(snackBarView,"Failed :- "+t.getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void clearValues(){
        eventTitle.setText("");
        eventDescription.setText("");
        startTime="";
        endTime="";
        startBtn.setText("Start time");
        endBtn.setText("End time");
        schoolRadioBtn.setChecked(false);
        teamRadioButton.setChecked(false);
    }
}
