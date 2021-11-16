package com.example.apidemo.Team;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemo.R;
import java.util.Calendar;
import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateEventActivity extends AppCompatActivity {
    ImageView eventImage;
    EditText eventTitle,eventDescription;
    Button startBtn,endBtn,colorBtn;
    RadioButton schoolRadioBtn,teamRadioButton;
    int  mHour, mMinute,defaultColor;
    RelativeLayout layout,CreateEventRelative;
    RecyclerView eventRecyclerView;
    int selectedRadio=1;
    int viewShow=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        initViews();
        schoolRadioBtn.setOnClickListener(v -> {
            selectedRadio=0;
            schoolRadioBtn.setChecked(true);
            teamRadioButton.setChecked(false);
        });
        teamRadioButton.setOnClickListener(v -> {
            selectedRadio=1;
            schoolRadioBtn.setChecked(false);
            teamRadioButton.setChecked(true);
        });
        if(viewShow==0){
            CreateEventRelative.setVisibility(View.VISIBLE);
            eventRecyclerView.setVisibility(View.GONE);
        }else if(viewShow==1){
            CreateEventRelative.setVisibility(View.GONE);
            eventRecyclerView.setVisibility(View.VISIBLE);
        }
        startBtn.setOnClickListener(v-> showTimePicker(startBtn));
        endBtn.setOnClickListener(v-> showTimePicker(endBtn));
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
        @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(this,
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
    }
}