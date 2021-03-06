package com.example.hannah.nyanclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAlarmActivity extends AppCompatActivity {

    ImageButton buttonCancel, buttonSave;
    TimePicker timePicker;
    CheckBox cbSunday, cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday;
    ArrayList<CheckBox> listDays;
    AlarmManager alarmManager;
    Calendar retrievedCalendar;
    PendingIntent pendingIntent;

    public Calendar calendar;
    public int hour;
    public int minute;
    public int broadcastCode;

    public int[] timeArr = {12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        // Variables
        buttonCancel = (ImageButton) findViewById(R.id.button_Cancel);
        buttonSave = (ImageButton) findViewById(R.id.button_Save);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        cbSunday = (CheckBox) findViewById(R.id.cb_Sunday);
        cbMonday = (CheckBox) findViewById(R.id.cb_Monday);
        cbTuesday = (CheckBox) findViewById(R.id.cb_Tuesday);
        cbWednesday = (CheckBox) findViewById(R.id.cb_Wednesday);
        cbThursday = (CheckBox) findViewById(R.id.cb_Thursday);
        cbFriday = (CheckBox) findViewById(R.id.cb_Friday);
        cbSaturday = (CheckBox) findViewById(R.id.cb_Saturday);

        // So that we only check this in buttonSave OnClickListener
        listDays = new ArrayList<>(0);
        listDays.add(cbSunday);
        listDays.add(cbMonday);
        listDays.add(cbTuesday);
        listDays.add(cbWednesday);
        listDays.add(cbThursday);
        listDays.add(cbFriday);
        listDays.add(cbSaturday);

        // Goes back to AlarmActivity
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Add alarm to database then go back to AlarmActivity
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the alarm, add alarm to database

                boolean[] selectedDays = new boolean[7]; // days with checks
                int trueDaysCount = 0;
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();

                // We identify whether hour is AM or PM
                String AM_PM ;
                if(hour < 12) {
                    AM_PM = "AM";
                } else {
                    AM_PM = "PM";
                }

                Log.i("BHOUR", String.valueOf(hour));
                //Convert to 12 hour format
                hour = timeArr[hour];
                Log.i("AHOUR", String.valueOf(hour));

                // Check if the checkbox is checked, if yes then add to selectedDays
                for(int i = 0; i < listDays.size(); i++)
                {
                    if(listDays.get(i).isChecked())
                    {
                        selectedDays[i] = true;
                        trueDaysCount++;
                    }
                    else
                    {
                        selectedDays[i] = false;
                    }
                    System.out.println(selectedDays[i]);
                }

                // if selectedDays has at least one day, then user can save alarm
                // else, Toast
                if(trueDaysCount > 0)
                {
                    // We make hour into 2-digit format
                    String strHour = "";
                    if(hour < 10)
                    {
                        strHour = "0" + hour;
                    }
                    else
                    {
                        strHour = "" + hour;
                    }

                    // We make minutes into 2-digit format
                    String strMinute = "";
                    if(minute < 10)
                    {
                        strMinute = "0" + minute;
                    }
                    else
                    {
                        strMinute = "" + minute;
                    }

                    // Add to database
                    Alarm newAlarm = new Alarm(strHour, strMinute, selectedDays, AM_PM);
                    DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(getBaseContext());


                    Log.i("HOUR", String.valueOf(hour));
                    Log.i("MINUTE", String.valueOf(minute));
                    Log.i("CLOCK", AM_PM);

                    // For the alarm clock itself
                    alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

                    for(int i = 0; i < 7; i++)
                    {
                        if(selectedDays[i])
                        {
                            //Alarm selectedAlarm = dbHelper.getAlarm(1);
                            retrievedCalendar = Calendar.getInstance();
                            retrievedCalendar.set(Calendar.DAY_OF_WEEK, i+1);
                            retrievedCalendar.set(Calendar.HOUR, hour);
                            retrievedCalendar.set(Calendar.MINUTE, minute);
                            retrievedCalendar.set(Calendar.SECOND, 0);
                            if(AM_PM == "AM"){
                                retrievedCalendar.set(Calendar.AM_PM, Calendar.AM);
                            }
                            else{
                                retrievedCalendar.set(Calendar.AM_PM, Calendar.PM);
                            }
                            Log.i("TAG", "calendar is " + retrievedCalendar.toString());
                            Log.i("BROADCAST CODE", "Code is " + broadcastCode);
                            broadcastCode = (int) System.currentTimeMillis();
                            dbHelper.addBroadCast(newAlarm.getId(), broadcastCode);
                            Intent myIntent = new Intent(AddAlarmActivity.this, AlarmReceiver.class);
                            pendingIntent = PendingIntent.getBroadcast(getBaseContext(), broadcastCode, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, retrievedCalendar.getTimeInMillis(), retrievedCalendar.getTimeInMillis() - 7*24*60*60*1000, pendingIntent);
                        }
                    }

                    dbHelper.addAlarm(newAlarm);

//                    startActivity(new Intent(AddAlarmActivity.this, AlarmActivity.class));

                    finish();
                }
                else
                {
                    Toast.makeText(AddAlarmActivity.this, "Must select at least one day.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Calendar getCalendar()
    {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar;
    }
}
