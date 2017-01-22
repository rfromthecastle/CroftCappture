package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Instructions8 extends AppCompatActivity {

    private Button mButtonNext8;
    private Button mButtonBack8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions8);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mButtonNext8 = (Button) findViewById(R.id.next8);
        mButtonNext8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next instructions screen
                Intent i = new Intent(Instructions8.this, Instructions9.class);
                startActivity(i);

            }

        });

        mButtonBack8 = (Button) findViewById(R.id.back8);
        mButtonBack8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous instructions screen
                Intent i = new Intent(Instructions8.this, Instructions7.class);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
