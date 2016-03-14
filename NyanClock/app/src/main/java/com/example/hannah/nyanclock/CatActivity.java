/*
This is where the main Cat related activities happen
Feed Button and Set Alarm Button
 */

package com.example.hannah.nyanclock;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

        // hunger and happy meter decrease by 10 every minute
        final Handler handler = new Handler();
        //update current time view after every 60 seconds
        final Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                int newHunger;
                int newHappy;
                cat = dbHelper.getCat(1);

                System.out.println("INSIDE RUN");

                // Decrease hunger and happiness
                newHunger = cat.getHunger() - 10;
                newHappy = cat.getHappiness() - 10;
                if(newHunger > 0)
                {
                    cat.setHunger(newHunger);
                }
                else
                {
                    cat.setHunger(0);
                }
                if(newHappy > 0)
                {
                    cat.setHappiness(newHappy);
                }
                else
                {
                    cat.setHappiness(0);
                }

                // Put newHunger and newHappy in database
                dbHelper.updateCat(cat);
                cat = dbHelper.getCat(1);

                // Display the updated hunger and happiness
                tvHungerCount.setText(String.valueOf(cat.getHunger()));
                tvHappyCount.setText(String.valueOf(cat.getHappiness()));

                handler.postDelayed(this, 60000);
            }
        };
        // Will call updateTask after 1 minute into opening the app -> does ONCE
        handler.postDelayed(updateTask, 60000);


        // Feeds the cat -> increases hunger meter and happy meter
        // happy meter doesn't increase if hunger is 100 already
        ibButtonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat = dbHelper.getCat(1);

                // increase happiness by 10
                int newHunger = cat.getHunger() + 10;
                int newHappy = 0;

                // if hunger meter is  less than 100, happy meter can increase
                if (newHunger < 100) {
                    newHappy = cat.getHappiness() + 5;
                } else {
                    newHappy = cat.getHappiness();
                }

                if (newHunger > 100) {
                    cat.setHunger(100);
                } else {
                    cat.setHunger(newHunger);
                }
                if (newHappy > 100) {
                    cat.setHappiness(100);
                } else {
                    cat.setHappiness(newHappy);
                }

                dbHelper.updateCat(cat);
                cat = dbHelper.getCat(1);

                tvHungerCount.setText(String.valueOf(cat.getHunger()));
                tvHappyCount.setText(String.valueOf(cat.getHappiness()));
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
                //Toast.makeText(CatActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                cat = dbHelper.getCat(1);

                // increase happiness by 10
                int newHappy = cat.getHappiness() + 10;

                if (newHappy > 100) {
                    cat.setHappiness(100);
                } else {
                    cat.setHappiness(newHappy);
                }

                dbHelper.updateCat(cat);
                cat = dbHelper.getCat(1);

                tvHappyCount.setText(String.valueOf(cat.getHappiness()));
            }

            public void onSwipeLeft() {
                cat = dbHelper.getCat(1);

                // increase happiness by 10
                int newHappy = cat.getHappiness() + 10;

                if (newHappy > 100) {
                    cat.setHappiness(100);
                } else {
                    cat.setHappiness(newHappy);
                }

                dbHelper.updateCat(cat);
                cat = dbHelper.getCat(1);

                tvHappyCount.setText(String.valueOf(cat.getHappiness()));
            }

            public void onSwipeBottom() {
                //Toast.makeText(CatActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

    }

}
