package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Instructions9 extends AppCompatActivity {

    private Button mButtonBack9;
    private Button mButtonGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions9);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mButtonGo = (Button) findViewById(R.id.button_go);
        mButtonGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start Form activity
                Intent i = new Intent(Instructions9.this, Form.class);
                startActivity(i);

            }

        });

        mButtonBack9 = (Button) findViewById(R.id.back9);
        mButtonBack9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous instructions screen
                Intent i = new Intent(Instructions9.this, Instructions8.class);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
