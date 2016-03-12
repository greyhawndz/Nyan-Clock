package com.example.hannah.nyanclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AddAlarmActivity extends AppCompatActivity {

    ImageButton buttonCancel, buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        // Variables
        buttonCancel = (ImageButton) findViewById(R.id.button_Cancel);
        buttonSave = (ImageButton) findViewById(R.id.button_Save);

        // Goes back to AlarmActivity
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Add alarm to database
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Save the alarm, add alarm to database

            }
        });
    }
}
