package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Finish extends AppCompatActivity {

    private Button mFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mFinish = (Button) findViewById(R.id.button_finalfinish);
        mFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Return to the Welcome activity
                Intent i = new Intent(Finish.this,Welcome.class);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
