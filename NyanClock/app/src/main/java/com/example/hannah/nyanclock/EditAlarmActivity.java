package com.example.hannah.nyanclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
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

public class EditAlarmActivity extends AppCompatActivity {

    ImageButton buttonDelete, buttonSave, buttonCancel;
    TimePicker timePicker;
    CheckBox cbSunday, cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday;


    DatabaseOpenHelper dbHelper;
    Alarm currentAlarm;
    ArrayList<CheckBox> listDays;
    AlarmManager alarmManager;
    Calendar retrievedCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        // Variables
        buttonCancel = (ImageButton) findViewById(R.id.button_Cancel);
        buttonSave = (ImageButton) findViewById(R.id.button_Save);
        buttonDelete = (ImageButton) findViewById(R.id.button_Delete);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        cbSunday = (CheckBox) findViewById(R.id.cb_Sunday);
        cbMonday = (CheckBox) findViewById(R.id.cb_Monday);
        cbTuesday = (CheckBox) findViewById(R.id.cb_Tuesday);
        cbWednesday = (CheckBox) findViewById(R.id.cb_Wednesday);
        cbThursday = (CheckBox) findViewById(R.id.cb_Thursday);
        cbFriday = (CheckBox) findViewById(R.id.cb_Friday);
        cbSaturday = (CheckBox) findViewById(R.id.cb_Saturday);
        dbHelper = new DatabaseOpenHelper(getBaseContext());

        // So that we only check this in buttonSave OnClickListener
        listDays = new ArrayList<CheckBox>();
        listDays.add(cbSunday);
        listDays.add(cbMonday);
        listDays.add(cbTuesday);
        listDays.add(cbWednesday);
        listDays.add(cbThursday);
        listDays.add(cbFriday);
        listDays.add(cbSaturday);

        // get the id of the selected alarm
        int id = getIntent().getIntExtra(Alarm.COLUMN_ID, 0);

        //get the alarm manager
        alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

        //get current note
        // the id in the param is the ID we passed from AlarmActivity
        currentAlarm = dbHelper.getAlarm(id);
        System.out.println("HOUR: " + currentAlarm.getTime().substring(0, 2));
        System.out.println("MINUTE: " + currentAlarm.getTime().substring(3, 5));
        System.out.println("CLOCK: " + currentAlarm.getTime().substring(6, 8));

        // displaying of data: TimePicker and Checkboxes
        String hour = currentAlarm.getTime().substring(0, 2);
        String minute = currentAlarm.getTime().substring(3, 5);
        String clock = currentAlarm.getTime().substring(6, 8);

        if(clock == "PM")
        {
            timePicker.setCurrentHour(Integer.parseInt(hour) + 12); // 24 hour format
        }
        else
        {
            timePicker.setCurrentHour(Integer.parseInt(hour));
        }
        timePicker.setCurrentMinute(Integer.parseInt(minute));

        // setChecked checks the Checkboxes that are true in the database
        System.out.println("SUNDAY is " + currentAlarm.isSun());
        if(currentAlarm.isSun())
        {
            cbSunday.post(new Runnable() {
                @Override
                public void run() {
                    cbSunday.setChecked(true);
                }
            });
        }
        System.out.println("MONDAY is " + currentAlarm.isMon());
        if(currentAlarm.isMon())
        {
            cbMonday.post(new Runnable() {
                @Override
                public void run() {
                    cbMonday.setChecked(true);
                }
            });
        }
        System.out.println("TUESDAY is " + currentAlarm.isTues());
        if(currentAlarm.isTues())
        {
            cbTuesday.post(new Runnable() {
                @Override
                public void run() {
                    cbTuesday.setChecked(true);
                }
            });
        }
        System.out.println("WEDNESDAY is " + currentAlarm.isWed());
        if(currentAlarm.isWed())
        {
            cbWednesday.post(new Runnable() {
                @Override
                public void run() {
                    cbWednesday.setChecked(true);
                }
            });
        }
        System.out.println("THURSDAY is " + currentAlarm.isThurs());
        if(currentAlarm.isThurs())
        {
            cbThursday.post(new Runnable() {
                @Override
                public void run() {
                    cbThursday.setChecked(true);
                }
            });
        }
        System.out.println("FRIDAY is " + currentAlarm.isFri());
        if(currentAlarm.isFri())
        {
            cbFriday.post(new Runnable() {
                @Override
                public void run() {
                    cbFriday.setChecked(true);
                }
            });
        }
        System.out.println("SATURDAY is " + currentAlarm.isSat());
        if(currentAlarm.isSat())
        {
            cbSaturday.post(new Runnable() {
                @Override
                public void run() {
                    cbSaturday.setChecked(true);
                }
            });
        }


        // Goes back to AlarmActivity
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Deletes alarm from database then goes back to AlarmActivity
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete from database
                int broadcastID;
                Cursor c = dbHelper.queryBroadcasts(currentAlarm.getId());
                if(c.moveToFirst()){
                    broadcastID = c.getInt(1);
                    Intent myIntent = new Intent(EditAlarmActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), broadcastID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                    while(c.moveToNext()){
                        broadcastID = c.getInt(1);
                        myIntent = new Intent(EditAlarmActivity.this, AlarmReceiver.class);
                        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), broadcastID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.cancel(pendingIntent);

                    }

                }

                dbHelper.deleteAlarm(currentAlarm.getId());
                finish();
            }
        });

        // Saves the changes done then goes back to AlarmActivity
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean[] selectedDays = new boolean[7]; // days with checks
                int trueDaysCount = 0;
                final int hour = timePicker.getCurrentHour();
                final int minute = timePicker.getCurrentMinute();

                // Check if the checkbox is checked, if yes then add to selectedDays
                for(int i = 0; i < listDays.size(); i++)
                {
                    if(listDays.get(i).isChecked())
                    {
                        selectedDays[i] = true;
                        trueDaysCount ++;
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

                    int broadcastID;
                    //delete currentPending intents in alarmManager
                    Cursor c = dbHelper.queryBroadcasts(currentAlarm.getId());
                    if(c.moveToFirst()){
                        broadcastID = c.getInt(1);
                        Intent myIntent = new Intent(EditAlarmActivity.this, AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), broadcastID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.cancel(pendingIntent);
                        while(c.moveToNext()){
                            broadcastID = c.getInt(1);
                            myIntent = new Intent(EditAlarmActivity.this, AlarmReceiver.class);
                            pendingIntent = PendingIntent.getBroadcast(getBaseContext(), broadcastID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.cancel(pendingIntent);

                        }

                    }


                    // We pass the ID of currentAlarm so we will edit that alarm
                    Alarm newAlarm = new Alarm(currentAlarm.getId(), strHour, strMinute, selectedDays, AM_PM);

                    //add new alarms in alarmManager
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
                            broadcastID = (int) System.currentTimeMillis();
                            Log.i("TAG", "calendar is " + retrievedCalendar.toString());
                            Log.i("BROADCAST CODE", "Code is " + broadcastID);
                            dbHelper.addBroadCast(newAlarm.getId(), broadcastID);
                            Intent myIntent = new Intent(EditAlarmActivity.this, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), broadcastID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, retrievedCalendar.getTimeInMillis(), 7*24*60*60*1000, pendingIntent);
                        }
                    }
                    // we pass the newAlarm so that DatabaseOpenHelper can update
                    dbHelper.editAlarm(newAlarm);

                    Log.i("HOUR", String.valueOf(hour));
                    Log.i("MINUTE", String.valueOf(minute));
                    Log.i("CLOCK", AM_PM);

                    finish();
                }
                else
                {
                    Toast.makeText(EditAlarmActivity.this, "Must select at least one day.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
