package com.example.hannah.nyanclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class AlarmActivity extends AppCompatActivity {

    Button buttonSetTime, buttonBack;
    TimePicker timePicker;
    Alarm alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        buttonSetTime = (Button) findViewById(R.id.button_SetTime);
        buttonBack = (Button) findViewById(R.id.button_Back);
        timePicker = (TimePicker) findViewById(R.id.tp_TimePicker);

        final DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(getBaseContext());
        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Create new alarm and set Time, day and clock(AM/PM) of alarm

                //Add Alarm to db
                dbHelper.addAlarm(alarm);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
