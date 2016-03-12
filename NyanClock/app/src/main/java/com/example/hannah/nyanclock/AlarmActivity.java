package com.example.hannah.nyanclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

public class AlarmActivity extends AppCompatActivity {

    ImageButton buttonBack, buttonAdd;
    RecyclerView lvAlarms;

    DatabaseOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Variables
        lvAlarms = (RecyclerView) findViewById(R.id.lv_Alarms);
        buttonBack = (ImageButton) findViewById(R.id.button_Back);
        buttonAdd = (ImageButton) findViewById(R.id.button_AddAlarm);

        // Goes back to CatActivity
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Goes to AddAlarmActivity to add an alarm
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), AddAlarmActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
