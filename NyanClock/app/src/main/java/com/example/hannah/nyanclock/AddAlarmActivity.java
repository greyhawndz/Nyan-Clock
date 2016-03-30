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
    public int broadcastCode = 0;

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
                    // We changed the hour to 12 hour format
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

                    // We identify whether hour is AM or PM
                    String AM_PM ;
                    if(hour < 12) {
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }

                    // Add to database
                    Alarm newAlarm = new Alarm(strHour, strMinute, selectedDays, AM_PM);
                    DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(getBaseContext());
                    dbHelper.addAlarm(newAlarm);

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
                            retrievedCalendar.set(Calendar.HOUR_OF_DAY, hour);
                            retrievedCalendar.set(Calendar.MINUTE, minute);
                            retrievedCalendar.set(Calendar.SECOND, 0);
                            Log.i("TAG", "calendar is " + retrievedCalendar.toString());
                            Log.i("BROADCAST CODE", "Code is "+broadcastCode);
                            Intent myIntent = new Intent(AddAlarmActivity.this, AlarmReceiver.class);
                            pendingIntent = PendingIntent.getBroadcast(getBaseContext(), broadcastCode, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            broadcastCode++;
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, retrievedCalendar.getTimeInMillis(), 7*24*60*60*1000, pendingIntent);
                        }
                    }

                    startActivity(new Intent(AddAlarmActivity.this, AlarmActivity.class));

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
