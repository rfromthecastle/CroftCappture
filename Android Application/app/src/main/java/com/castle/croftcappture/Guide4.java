package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Guide4 extends AppCompatActivity {

    private Button mNext4;
    private Button mBack4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide4);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mNext4 = (Button) findViewById(R.id.next4_guide);
        mNext4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next guide screen and destroy previous
                Intent i = new Intent(Guide4.this, Guide5.class);
                startActivity(i);
                Guide4.this.finish();

            }

        });

        mBack4 = (Button) findViewById(R.id.back4_guide);
        mBack4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous guide screen and destroy current
                Intent i = new Intent(Guide4.this, Guide3.class);
                startActivity(i);
                Guide4.this.finish();

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }


}
