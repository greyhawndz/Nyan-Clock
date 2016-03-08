/*
This is where the main Cat related activities happen

TODO:
- Put the Hunger meter (counter?)
- Put the Happy meter (counter?)
- Put the Emotion state
- Put the Cat asset
- Connect to AlarmActivity
 */

package com.example.hannah.nyanclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CatActivity extends AppCompatActivity {

    ImageButton ibButtonFeed, ibButtonAlarm;
    final static int REQUEST_TIME = 0;
    final static String KEY_TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        ibButtonFeed = (ImageButton) findViewById(R.id.ib_ButtonFeed);
        ibButtonAlarm = (ImageButton) findViewById(R.id.ib_ButtonAlarm);

        ibButtonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ibButtonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), AlarmActivity.class);
                startActivityForResult(i, REQUEST_TIME);
            }
        });
    }


}
