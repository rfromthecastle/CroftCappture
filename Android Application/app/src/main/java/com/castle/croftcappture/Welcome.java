package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    private Button mWelcomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mWelcomeButton = (Button) findViewById(R.id.welcome_button);
        mWelcomeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //Start the Instructions activity
                Intent i = new Intent(Welcome.this, Instructions.class);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
