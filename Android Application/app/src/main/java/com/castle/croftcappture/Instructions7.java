package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Instructions7 extends AppCompatActivity {

    private Button mButtonNext7;
    private Button mButtonBack7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions7);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mButtonNext7 = (Button) findViewById(R.id.next7);
        mButtonNext7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next instructions screen
                Intent i = new Intent(Instructions7.this, Instructions8.class);
                startActivity(i);

            }

        });

        mButtonBack7 = (Button) findViewById(R.id.back7);
        mButtonBack7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous instructions screen
                Intent i = new Intent(Instructions7.this, Instructions6.class);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
