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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        buttonSetTime = (Button) findViewById(R.id.button_SetTime);
        buttonBack = (Button) findViewById(R.id.button_Back);
        timePicker = (TimePicker) findViewById(R.id.tp_TimePicker);

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
