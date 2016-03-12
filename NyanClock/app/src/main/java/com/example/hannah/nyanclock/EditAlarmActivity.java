package com.example.hannah.nyanclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class EditAlarmActivity extends AppCompatActivity {

    ImageButton buttonDelete, buttonSave, buttonCancel;
    TimePicker timePicker;
    CheckBox cbSunday, cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday;

    DatabaseOpenHelper dbHelper;
    Alarm currentAlarm;
    ArrayList<CheckBox> listDays;

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
        listDays = new ArrayList<>(0);
        listDays.add(cbSunday);
        listDays.add(cbMonday);
        listDays.add(cbTuesday);
        listDays.add(cbWednesday);
        listDays.add(cbThursday);
        listDays.add(cbFriday);
        listDays.add(cbSaturday);

        // get the id of the selected alarm
        int id = getIntent().getIntExtra(Alarm.COLUMN_ID, 0);

        //get current note
        // the id in the param is the ID we passed from AlarmActivity
        currentAlarm = dbHelper.getAlarm(id);

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

        // TODO: Fix the setChecked for the Checkbox
        if(currentAlarm.isSun())
        {
            cbSunday.post(new Runnable() {
                @Override
                public void run() {

                    cbSunday.setChecked(true);
                }
            });
        }
        if(currentAlarm.isMon())
        {
            cbMonday.post(new Runnable() {
                @Override
                public void run() {
                    cbMonday.setChecked(true);
                }
            });
        }
        if(currentAlarm.isTues())
        {
            cbTuesday.post(new Runnable() {
                @Override
                public void run() {
                    cbTuesday.setChecked(true);
                }
            });
        }
        if(currentAlarm.isWed())
        {
            cbWednesday.post(new Runnable() {
                @Override
                public void run() {
                    cbWednesday.setChecked(true);
                }
            });
        }
        if(currentAlarm.isThurs())
        {
            cbThursday.post(new Runnable() {
                @Override
                public void run() {
                    cbThursday.setChecked(true);
                }
            });
        }
        if(currentAlarm.isFri())
        {
            cbFriday.post(new Runnable() {
                @Override
                public void run() {
                    cbFriday.setChecked(true);
                }
            });
        }
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
                dbHelper.deleteAlarm(currentAlarm.getId());
                finish();
            }
        });

        // Saves the changes done then goes back to AlarmActivity
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean[] selectedDays = new boolean[7]; // days with checks
                final int hour = timePicker.getCurrentHour();
                final int minute = timePicker.getCurrentMinute();

                // Check if the checkbox is checked, if yes then add to selectedDays
                for(int i = 0; i < listDays.size(); i++)
                {
                    if(listDays.get(i).isChecked())
                    {
                        selectedDays[i] = true;
                    }
                    else
                    {
                        selectedDays[i] = false;
                    }
                }

                // if selectedDays has at least one day, then user can save alarm
                // else, Toast
                if(selectedDays.length > 0)
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

                    // We pass the ID of currentAlarm so we will edit that alarm
                    Alarm newAlarm = new Alarm(currentAlarm.getId(), strHour, strMinute, selectedDays, AM_PM);
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
