package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Instructions5 extends AppCompatActivity {

    private Button mButtonNext5;
    private Button mButtonBack5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions5);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mButtonNext5 = (Button) findViewById(R.id.next5);
        mButtonNext5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next instructions screen
                Intent i = new Intent(Instructions5.this, Instructions6.class);
                startActivity(i);

            }

        });

        mButtonBack5 = (Button) findViewById(R.id.back5);
        mButtonBack5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous instructions screen
                Intent i = new Intent(Instructions5.this, Instructions4.class);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
