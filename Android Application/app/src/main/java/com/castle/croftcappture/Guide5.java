package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Guide5 extends AppCompatActivity {

    private Button mNext5;
    private Button mBack5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide5);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mNext5 = (Button) findViewById(R.id.next5_guide);
        mNext5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next guide screen and destroy previous
                Intent i = new Intent(Guide5.this, Guide6.class);
                startActivity(i);
                Guide5.this.finish();

            }

        });

        mBack5 = (Button) findViewById(R.id.back5_guide);
        mBack5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous guide screen and destroy current
                Intent i = new Intent(Guide5.this, Guide4.class);
                startActivity(i);
                Guide5.this.finish();

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
