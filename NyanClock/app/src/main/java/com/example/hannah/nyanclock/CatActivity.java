/*
This is where the main Cat related activities happen
Feed Button and Set Alarm Button
 */

package com.example.hannah.nyanclock;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CatActivity extends AppCompatActivity {

    ImageButton ibButtonFeed, ibButtonAlarm;
    TextView tvHungerCount, tvHappyCount;
    ImageView ivCat, ivState;
    final static int REQUEST_TIME = 0;
    final static String KEY_TIME = "time";

    DatabaseOpenHelper dbHelper;
    Cat cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        // Variables
        ibButtonFeed = (ImageButton) findViewById(R.id.ib_ButtonFeed);
        ibButtonAlarm = (ImageButton) findViewById(R.id.ib_ButtonAlarm);
        tvHungerCount = (TextView) findViewById(R.id.tv_HungerCount);
        tvHappyCount = (TextView) findViewById(R.id.tv_HappyCount);
        ivCat = (ImageView) findViewById(R.id.iv_Cat);
        ivState = (ImageView) findViewById(R.id.iv_state);
        dbHelper = new DatabaseOpenHelper(getBaseContext());
        cat = dbHelper.getCat(1);


        // Feeds the cat -> increases hunger meter and happy meter
        ibButtonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Increase hunger meter value
                //TODO: Increases happy meter value

                //Update cat in db
                dbHelper.updateCat(cat);
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

        // Swipe Left/Right on cat
        ivCat.setOnTouchListener(new OnSwipeTouchListener(CatActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(CatActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                // TODO: connect to database to (if happy meter < 100) increase the happiness
                // TODO: after increasing happiness, reflect new happiness to happy meter
                Toast.makeText(CatActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                // TODO: connect to database to (if happy meter < 100) increase the happiness
                // TODO: after increasing happiness, reflect new happiness to happy meter
                Toast.makeText(CatActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(CatActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

    }

}
