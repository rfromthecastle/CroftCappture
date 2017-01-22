package com.castle.croftcappture;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import uk.co.ordnancesurvey.android.maps.GridPoint;
import uk.co.ordnancesurvey.android.maps.MapProjection;

public class Description extends AppCompatActivity {

    private Button mDone;
    private EditText mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        mDescription = (EditText) findViewById(R.id.editText);

        mDone = (Button) findViewById(R.id.text_done);
        mDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //AlertDialog to ask the user whether they are done writing their description
                new AlertDialog.Builder(Description.this)
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setTitle("Save Description")
                        .setMessage("Are you done writing your description?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                doneWithText();

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

            }

        });

    }

    //When the user is done writing the description, store it as a text file in the right folder
    public void doneWithText() {

        String text = mDescription.getText().toString();
        Intent data = getIntent();
        String name = (String) data.getSerializableExtra("name");
        String number = (String) data.getSerializableExtra("number");

        //Geotag the file
        GridPoint geotag = myGridPoint();

        try {

            //Place the file in the right folder
            File textFolder = new File(Environment.getExternalStorageDirectory(), "/CroftCappture/ " + name + "/Croft " + number + "/");

            if (!textFolder.exists()) {

                textFolder.mkdirs();

            }

            //Write the text file
            String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File destination = new File(textFolder, "description"+geotag.x+"-"+geotag.y+"-"+date+".txt");
            FileWriter writer = new FileWriter(destination);
            writer.append("Location: " + Math.round(geotag.x) + "," + Math.round(geotag.y) + " ");
            writer.append(text);
            writer.flush();
            writer.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        //Notify the user that the description is saved
        Toast.makeText(Description.this, "Description saved!", Toast.LENGTH_SHORT).show();
        Description.this.finish();

    }

    //Location listener that fetches the current location and updates the latitude and longitude
    public Location myLocation() {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {



            }

            public void onStatusChanged(String provider, int status, Bundle extras) {



            }

            public void onProviderEnabled(String provider) {



            }

            public void onProviderDisabled(String provider) {



            }

        };
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        if (myLocation != null) {

            double latitude = myLocation.getLatitude();
            double longitude = myLocation.getLongitude();
            MapProjection mapProjection = MapProjection.getDefault();
            GridPoint gp = mapProjection.toGridPoint(latitude, longitude);
            return myLocation;

        } else {

            Toast.makeText(Description.this,"Turn on GPS.",Toast.LENGTH_SHORT).show();
            return null;

        }

    }

    //Converts the current location into a GridPoint and warns the user if the GPS is turned off
    public GridPoint myGridPoint(){

        Location location = myLocation();
        if (location != null){

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            MapProjection mapProjection = MapProjection.getDefault();
            GridPoint gp = mapProjection.toGridPoint(latitude,longitude);
            return gp;

        } else {

            Toast.makeText(Description.this,"Turn on GPS.",Toast.LENGTH_SHORT).show();
            return null;

        }

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}
