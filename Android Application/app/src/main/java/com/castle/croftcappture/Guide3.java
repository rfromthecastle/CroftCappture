package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Guide3 extends AppCompatActivity {

    private Button mNext3;
    private Button mBack3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide3);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mNext3 = (Button) findViewById(R.id.next3_guide);
        mNext3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next guide screen and destroy previous
                Intent i = new Intent(Guide3.this, Guide4.class);
                startActivity(i);
                Guide3.this.finish();

            }

        });

        mBack3 = (Button) findViewById(R.id.back3_guide);
        mBack3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous guide screen and destroy current
                Intent i = new Intent(Guide3.this, Guide2.class);
                startActivity(i);
                Guide3.this.finish();

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
