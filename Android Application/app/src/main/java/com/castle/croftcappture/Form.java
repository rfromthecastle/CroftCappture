package com.castle.croftcappture;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Form extends AppCompatActivity {

    private EditText mName, mNumber;
    private Button mStartMap, mSaveDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mName = (EditText) findViewById(R.id.name);
        mNumber = (EditText) findViewById(R.id.croft_number);

        mSaveDetails = (Button) findViewById(R.id.button_save_details);
        mSaveDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Read form input
                String name = mName.getText().toString();
                String number = mNumber.getText().toString();
                String information = "Name: " + name + " Croft number:" + number;

                try {

                    //If the full name is entered
                    if (!name.matches("")) {

                        //If the croft number is entered
                        if (!number.matches("")) {

                            //Create a new folder with the form details
                            File textFolder = new File(Environment.getExternalStorageDirectory(), "/CroftCappture/ " + name + "/Croft " + number + "/");

                            if (!textFolder.exists()) {

                                //If no such folder exists yet, create it
                                textFolder.mkdirs();

                            }

                            //Create a timestamped file with the form information
                            String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                            File destination = new File(textFolder, "info" + date + ".txt");
                            FileWriter writer = new FileWriter(destination);
                            writer.append(information);
                            writer.flush();
                            writer.close();

                            //Notify the user that the details are saved
                            Toast.makeText(Form.this, "Details saved!", Toast.LENGTH_SHORT).show();

                            //Allow the user to move to the screen to collect evidence
                            mStartMap.setVisibility(View.VISIBLE);
                            mSaveDetails.setVisibility(View.INVISIBLE);

                        } else {

                            //Notify the user to enter their croft number
                            Toast.makeText(Form.this, "Please enter croft number.", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        //Notify the user to enter their full name
                        Toast.makeText(Form.this, "Please enter full name.", Toast.LENGTH_SHORT).show();

                    }

                } catch (IOException e) {

                    e.printStackTrace();

                }


            }

        });

        mStartMap = (Button) findViewById(R.id.button_start_map);
        mStartMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = mName.getText().toString();
                String number = mNumber.getText().toString();

                //Bundle the intent with the details from the form and pass it onto the evidence collection screen (activity will start)
                Intent i = new Intent(Form.this, MainActivity.class);
                i.putExtra("name", name);
                i.putExtra("number", number);
                startActivity(i);

            }

        });

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
