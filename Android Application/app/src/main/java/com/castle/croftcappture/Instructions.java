package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Instructions extends AppCompatActivity {

    private Button mButtonNext1;
    private Button mButtonBack1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mButtonNext1 = (Button) findViewById(R.id.next1);
        mButtonNext1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //Start next instructions screen
                Intent i = new Intent(Instructions.this, Instructions2.class);
                startActivity(i);

            }

        });

        mButtonBack1 = (Button) findViewById(R.id.back1);
        mButtonBack1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //Return to welcome screen
                Intent i = new Intent(Instructions.this, Welcome.class);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
