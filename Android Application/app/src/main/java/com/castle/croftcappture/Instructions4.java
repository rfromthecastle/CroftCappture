package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Instructions4 extends AppCompatActivity {

    private Button mButtonNext4;
    private Button mButtonBack4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions4);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mButtonNext4 = (Button) findViewById(R.id.next4);
        mButtonNext4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next instructions screen
                Intent i = new Intent(Instructions4.this, Instructions5.class);
                startActivity(i);

            }

        });

        mButtonBack4 = (Button) findViewById(R.id.back4);
        mButtonBack4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous instructions screen
                Intent i = new Intent(Instructions4.this, Instructions3.class);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
