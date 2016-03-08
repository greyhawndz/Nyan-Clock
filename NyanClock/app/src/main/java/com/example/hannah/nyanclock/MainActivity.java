/*
This is the main menu, where the user clicks the START button to play the app
 */

package com.example.hannah.nyanclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView ivTextTitle;
    ImageButton ibButtonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variables
        ivTextTitle = (ImageView) findViewById(R.id.iv_TextTitle);
        ibButtonStart = (ImageButton) findViewById(R.id.ib_ButtonStart);

        ibButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), CatActivity.class);
                startActivity(i);
            }
        });
    }


}
