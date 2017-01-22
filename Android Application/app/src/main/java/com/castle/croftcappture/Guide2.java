package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Guide2 extends AppCompatActivity {

    private Button mNext2;
    private Button mBack2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide2);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mNext2 = (Button) findViewById(R.id.next2_guide);
        mNext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next guide screen and destroy the previous
                Intent i = new Intent(Guide2.this, Guide3.class);
                startActivity(i);
                Guide2.this.finish();

            }

        });

        mBack2 = (Button) findViewById(R.id.back2_guide);
        mBack2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to previous guide screen and destroy the current
                Intent i = new Intent(Guide2.this, Guide.class);
                startActivity(i);
                Guide2.this.finish();

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
