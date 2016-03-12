package com.example.hannah.nyanclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EditAlarmActivity extends AppCompatActivity {

    ImageButton buttonDelete, buttonSave, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        // Variables
        buttonCancel = (ImageButton) findViewById(R.id.button_Cancel);
        buttonSave = (ImageButton) findViewById(R.id.button_Save);
        buttonDelete = (ImageButton) findViewById(R.id.button_Delete);

        // Goes back to AddAlarmActivity
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
