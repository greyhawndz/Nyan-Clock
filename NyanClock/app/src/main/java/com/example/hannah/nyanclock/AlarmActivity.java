package com.example.hannah.nyanclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    ImageButton buttonBack, buttonAdd;
    RecyclerView lvAlarms;

    DatabaseOpenHelper dbHelper;
    AlarmCursorAdapter alarmCursorAdapter;
    AlarmManager alarmManager;
    Calendar retrievedCalendar;
    PendingIntent pendingIntent;

    static int broadcastCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Variables
        lvAlarms = (RecyclerView) findViewById(R.id.lv_Alarms);
        buttonBack = (ImageButton) findViewById(R.id.button_Back);
        buttonAdd = (ImageButton) findViewById(R.id.button_AddAlarm);
        dbHelper = new DatabaseOpenHelper(getBaseContext());

        /*// For the alarm clock itself
        alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

        //Alarm selectedAlarm = dbHelper.getAlarm(1);
        retrievedCalendar = Calendar.getInstance();
        retrievedCalendar.setTimeInMillis(System.currentTimeMillis());
        retrievedCalendar.set(Calendar.MINUTE, 37);
        retrievedCalendar.set(Calendar.SECOND, 0);
        Log.i("TAG", "calendar is " + retrievedCalendar.toString());
        Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, retrievedCalendar.getTimeInMillis(), pendingIntent);*/

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

        // So we can click the whole container of each RecyclerView
        alarmCursorAdapter = new AlarmCursorAdapter(getBaseContext(), null);
        alarmCursorAdapter.setmOnItemClickListener(new AlarmCursorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                //id = id of the note clicked
                Intent i = new Intent();
                i.setClass(getBaseContext(), EditAlarmActivity.class);
                //putExtra - the same concept as ContentValues
                //you're putting id to a variable Alarm.COLUMN_ID; we wanna send id to EditAlarmActivity
                // so we can view it
                i.putExtra(Alarm.COLUMN_ID, id);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //getting all the updated notes
        Cursor cursor = dbHelper.queryAlarms();
        // swap = think as setCursor
        alarmCursorAdapter.swapCursor(cursor);
        //noteCursorAdapter has the updated notes

        lvAlarms.setAdapter(alarmCursorAdapter);
        lvAlarms.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }
}
