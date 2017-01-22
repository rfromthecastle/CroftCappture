package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Guide6 extends AppCompatActivity {

    private Button mNext6;
    private Button mBack6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide6);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mNext6 = (Button) findViewById(R.id.next6_guide);
        mNext6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Destroy current screen and return to MainActivity
                Guide6.this.finish();

            }

        });

        mBack6 = (Button) findViewById(R.id.back6_guide);
        mBack6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous guide screen and destroy current
                Intent i = new Intent(Guide6.this, Guide5.class);
                startActivity(i);
                Guide6.this.finish();

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
