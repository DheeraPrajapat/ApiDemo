package com.example.apidemo.Team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.apidemo.R;

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
                    }
                    return false;
                }
            });
            popupMenu.show();
    }

    private void initViews() {
            optionsImage=findViewById(R.id.thereedots);
    }
}