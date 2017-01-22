package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Instructions2 extends AppCompatActivity {

    private Button mButtonNext2;
    private Button mButtonBack2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions2);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mButtonNext2 = (Button) findViewById(R.id.next2);
        mButtonNext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next instructions screen
                Intent i = new Intent(Instructions2.this, Instructions3.class);
                startActivity(i);

            }

        });

        mButtonBack2 = (Button) findViewById(R.id.back2);
        mButtonBack2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous instructions screen
                Intent i = new Intent(Instructions2.this, Instructions.class);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}