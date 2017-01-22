package com.castle.croftcappture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Guide extends AppCompatActivity {

    private Button mNext1;
    private Button mBack1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mNext1 = (Button) findViewById(R.id.next1_guide);
        mNext1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start next guide screen and destroy previous
                Intent i = new Intent(Guide.this, Guide2.class);
                startActivity(i);
                Guide.this.finish();

            }

        });

        mBack1 = (Button) findViewById(R.id.back1_guide);
        mBack1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Destroy current guide screen
                Guide.this.finish();

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
