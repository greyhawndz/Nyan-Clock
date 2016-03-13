/*
This is where the main Cat related activities happen
Feed Button and Set Alarm Button
 */

package com.example.hannah.nyanclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class CatActivity extends AppCompatActivity {

    ImageButton ibButtonFeed, ibButtonAlarm;
    final static int REQUEST_TIME = 0;
    final static String KEY_TIME = "time";
    final static int RECEIVER = 1;
    DatabaseOpenHelper dbHelper;
    Cat cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);


        // Variables
        ibButtonFeed = (ImageButton) findViewById(R.id.ib_ButtonFeed);
        ibButtonAlarm = (ImageButton) findViewById(R.id.ib_ButtonAlarm);
        dbHelper = new DatabaseOpenHelper(getBaseContext());
        cat = dbHelper.getCat(1);

        // Feeds the cat -> increases hunger meter and happy meter
        ibButtonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Increase hunger meter value
                //TODO: Increases happy meter value


            }
        });

        // Goes to the AlarmActivity -> where user can set and edit alarms
        ibButtonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), AlarmActivity.class);
                startActivityForResult(i, REQUEST_TIME);
            }
        });





    }

    @Override
    protected void onPause() {
        super.onPause();

        dbHelper.updateCat(cat);

    }



    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.updateCat(cat);


        int hungerVal = cat.getHunger();
        int happyVal = cat.getHappiness();
        if(happyVal < 50 || hungerVal < 50){


            Intent broadcastIntent = new Intent(getBaseContext(), CatReceiver.class);
            PendingIntent pendingIntent
                    = PendingIntent.getBroadcast(getBaseContext(),
                    RECEIVER,
                    broadcastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            ((AlarmManager)getSystemService(Service.ALARM_SERVICE))
                    .set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime(),
                            pendingIntent);
        }




    }
}
