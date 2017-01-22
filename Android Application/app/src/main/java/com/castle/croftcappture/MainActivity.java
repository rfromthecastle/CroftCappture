package com.castle.croftcappture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.ordnancesurvey.android.maps.BitmapDescriptorFactory;
import uk.co.ordnancesurvey.android.maps.CameraPosition;
import uk.co.ordnancesurvey.android.maps.GridPoint;
import uk.co.ordnancesurvey.android.maps.MapProjection;
import uk.co.ordnancesurvey.android.maps.Marker;
import uk.co.ordnancesurvey.android.maps.MarkerOptions;
import uk.co.ordnancesurvey.android.maps.OSMap;
import uk.co.ordnancesurvey.android.maps.OSTileSource;
import uk.co.ordnancesurvey.android.maps.MapFragment;
import uk.co.ordnancesurvey.android.maps.PolygonOptions;

public class MainActivity extends AppCompatActivity {

    //Enter API key for OS OpenSpace Free
    private final static String OS_API_KEY = "2F6DEC09B8965C07E0530B6CA40AD1A2";
    private final static boolean OS_IS_PRO = false;

    //Assign a variable to the map interface
    private OSMap mMap;

    //Assign variables to buttons and TextViews
    private Button mDigitiserButton, mFinishButton, mSubmitButton;
    private ImageButton mMyLocationButton, mCameraButton, mTextButton, mHelp;
    private TextView mGPSon;

    //Create empty lists for collected GPS GridPoints and coordinates
    private final ArrayList<GridPoint> points = new ArrayList<GridPoint>();
    private final ArrayList<String> collectedPoints = new ArrayList();
    private final ArrayList<Location> locations = new ArrayList<Location>();

    private int REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Load the initial view and toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //The OS Map will act as a fragment that can be placed in the app
        MapFragment mf = ((MapFragment) getFragmentManager().findFragmentById(R.id.os_map_fragment));
        mMap = mf.getMap();

        //Create an empty list of tile sources
        ArrayList<OSTileSource> sources = new ArrayList<OSTileSource>();

        //Create a web tile source with the API details
        sources.add(mMap.webTileSource(OS_API_KEY, OS_IS_PRO, null));
        mMap.setTileSources(sources);//Define what happens when the app is opened

        //Add a current location indicator to the map
        mMap.setMyLocationEnabled(true);

        //Set the initial camera position to the extent of Scotland
        GridPoint scotland = new GridPoint(256798,827985);
        float mpp = 750;
        CameraPosition cameraPosition = new CameraPosition(scotland,mpp);
        mMap.moveCamera(cameraPosition,true);

        //Guide button
        mHelp = (ImageButton) findViewById(R.id.help);
        mHelp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Start the guide
                Intent i = new Intent(MainActivity.this, Guide.class);
                startActivity(i);

            }

        });

        //Location button: zoom to current location
        mMyLocationButton = (ImageButton) findViewById(R.id.imagebutton_mylocation);
        mMyLocationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                myLocationClick();

            }
        });

        //Digitiser button: place a marker at the current location
        mDigitiserButton = (Button) findViewById(R.id.button_digitise);
        mDigitiserButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //AlertDialog to ask whether the user wants to collect coordinates at a location
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_myplaces)
                        .setTitle("Collect GPS Point")
                        .setMessage("Is this the location where you would like to collect a GPS point?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                digitiseMarker(digitise());

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

            }

        });

        //Finish button: complete boundary
        mFinishButton = (Button) findViewById(R.id.button_finish);
        mFinishButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //AlertDialog to ask whether the user wants to complete their boundary
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_gallery)
                        .setTitle("Complete Boundary")
                        .setMessage("Are you sure you want to complete your croft boundary?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                completePolygon();

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });

        //End button: finish evidence collection
        mSubmitButton = (Button) findViewById(R.id.button_submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //AlertDialog to ask whether the user wants to stop collecting evidence
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_save)
                        .setTitle("End Evidence Collection")
                        .setMessage("Are you sure you want to end collecting evidence?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                submit();

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

            }

        });

        //Camera button
        mCameraButton = (ImageButton) findViewById(R.id.image_button_camera);
        mCameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //AlertDialog to ask whether the user wants to take a picture at a location
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_camera)
                        .setTitle("Take Picture")
                        .setMessage("Is this the location where you would like to take a picture?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                takePicture();

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

            }

        });

        //Description button
        mTextButton = (ImageButton) findViewById(R.id.describe);
        mTextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //AlertDialog to ask whether the user wants to write a description at a location
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setTitle("Textual Description")
                        .setMessage("Is this the location where you would like to describe something?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                GridPoint gp = myGridPoint();
                                MarkerOptions markerOptions = new MarkerOptions().gridPoint(gp);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                Marker marker = mMap.addMarker(markerOptions);
                                describe();

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

            }

        });

        //Real-time latitude and longitude box
        mGPSon = (TextView) findViewById(R.id.gps_on);

        //Add an X and Y title to the collectedPoints list (for reading in a CSV file)
        collectedPoints.add("\nX,Y");

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

            //When the location of the device changes
            public void onLocationChanged(Location location) {

                //Display the current location
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                MapProjection mapProjection = MapProjection.getDefault();
                GridPoint gp = mapProjection.toGridPoint(latitude,longitude);
                mGPSon.setText("Latitude:    "+Math.round(gp.x)+"\nLongitude: "+Math.round(gp.y));

            }

            //When the status of the GNSS receiver changes
            public void onStatusChanged(String provider, int status, Bundle extras) {



            }

            //When the location provider is enabled
            public void onProviderEnabled(String provider) {



            }

            //When the location provider is disabled
            public void onProviderDisabled(String provider) {



            }

        };
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        // Obtain the current location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        //Only if the current location is passed
        if (myLocation != null) {

            //Project the Location object to a GridPoint object
            double latitude = myLocation.getLatitude();
            double longitude = myLocation.getLongitude();
            MapProjection mapProjection = MapProjection.getDefault();
            GridPoint gp = mapProjection.toGridPoint(latitude, longitude);
            //Update the real-time latitude and longitude
            mGPSon.setText("Latitude:    " + Math.round(gp.x) + "\nLongitude: " + Math.round(gp.y));
            return myLocation;

        } else {

            //If GPS is off, notify the user to turn it on
            Toast.makeText(MainActivity.this,"Turn on GPS.",Toast.LENGTH_SHORT).show();
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

            //If GPS is off, notify the user to turn it on
            Toast.makeText(MainActivity.this,"Turn on GPS.",Toast.LENGTH_SHORT).show();
            return null;

        }

    }

    //Zooms into the current location
    public void myLocationClick() {

        //Set the initial camera position to the current location
        GridPoint gridPoint = myGridPoint();
        float mpp = 1;

        if (gridPoint != null) {

            CameraPosition cameraPosition = new CameraPosition(gridPoint, mpp);
            mMap.moveCamera(cameraPosition, true);

        } else {

            //If GPS is off, notify the user to turn it on
            Toast.makeText(MainActivity.this,"Turn on GPS.",Toast.LENGTH_SHORT).show();

        }

    }

    //Get the current location GridPoint and add the coordinates to a list
    public GridPoint digitise() {

        Location myLocation = myLocation();
        GridPoint myGP = myGridPoint();
        //myLocationClick();
        points.add(myGridPoint());
        if (points.size() > 2) {


            mFinishButton.setVisibility(View.VISIBLE);

        }
        double latitude = myGridPoint().x;
        double longitude = myGridPoint().y;
        String stringLat = Double.toString(latitude);
        String stringLong = Double.toString(longitude);
        String coordPair = " " + stringLat + "," + stringLong;
        collectedPoints.add(coordPair);
        locations.add(myLocation);

        return myGP;

    }

    //Places a marker on top of the current location
    public Marker digitiseMarker(GridPoint gridPoint){

        MarkerOptions markerOptions = new MarkerOptions().gridPoint(gridPoint);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        Marker marker = mMap.addMarker(markerOptions);
        return marker;

    }

    //Deletes markers and creates a polygon from the digitised GridPoints
    public void completePolygon(){

        PolygonOptions polygonOptions = new PolygonOptions()
                .addAll(points)
                .strokeColor(Color.GREEN)
                .fillColor(Color.TRANSPARENT);

        mMap.addPolygon(polygonOptions);

        //Add the coordinates of the first GridPoint to the end of the list to complete the polygon
        collectedPoints.add(collectedPoints.get(1));
        collectedPoints.add("\n");
        String CPString = collectedPoints.toString();

        //Obtain the right folder path from the intent passed on from the Form activity
        Intent data = getIntent();
        String name = (String) data.getSerializableExtra("name");
        String number = (String) data.getSerializableExtra("number");

        try {

            File textFolder = new File(Environment.getExternalStorageDirectory(), "/CroftCappture/ " + name + "/Croft " + number + "/");

            if (!textFolder.exists()) {

                textFolder.mkdirs();

            }

            String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File destination = new File(textFolder, "croft"+date+".txt");
            FileWriter writer = new FileWriter(destination);
            writer.append(CPString);
            writer.flush();
            writer.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        mSubmitButton.setVisibility(View.VISIBLE);

        Toast.makeText(MainActivity.this, "Finished mapping croft!", Toast.LENGTH_SHORT).show();

    }

    //Start the Finish activity and pass on the information from the Form activity
    public void submit(){

        Intent intentFinish = new Intent(MainActivity.this,Finish.class);
        Intent data = getIntent();
        String name = (String) data.getSerializableExtra("name");
        String number = (String) data.getSerializableExtra("number");
        intentFinish.putExtra("name", name);
        intentFinish.putExtra("number", number);
        startActivity(intentFinish);

    }

    //Place a marker where the description is written and start the Description activity, passing on the right folder information
    private void describe() {

        GridPoint geotag = myGridPoint();

        MarkerOptions marker = new MarkerOptions().gridPoint(geotag);
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(marker);

        Intent data = getIntent();
        String name = (String) data.getSerializableExtra("name");
        String number = (String) data.getSerializableExtra("number");
        Intent intent = new Intent(MainActivity.this,Description.class);
        intent.putExtra("name", name);
        intent.putExtra("number", number);
        startActivityForResult(intent,101);

    }

    //Implicit intent to start the camera
    private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    //Once the picture is taken...
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

                onCaptureImageResult(data);

            }

        }

    }

    //...
    private void onCaptureImageResult(Intent data) {

        //Compress the bitmap image to a JPEG and output an array of bytes
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        Bitmap icon = Bitmap.createScaledBitmap(thumbnail,45,80,false);

        //Store the image in the right folder
        Intent data2 = getIntent();
        String name = (String) data2.getSerializableExtra("name");
        String number = (String) data2.getSerializableExtra("number");
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "/CroftCappture/ " + name + "/Croft " + number + "/");
        if (!imagesFolder.exists()) {

            imagesFolder.mkdirs();

        }

        //Geotag the image and write the file
        GridPoint geotag = myGridPoint();
        String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        File destination = new File(imagesFolder, "image"+geotag.x+"-"+geotag.y+"-"+date+".jpg");

        FileOutputStream fo;

        try {

            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        //Place the image on the map where it was taken
        MarkerOptions marker = new MarkerOptions().gridPoint(geotag);
        marker.icon(BitmapDescriptorFactory.fromBitmap(icon));
        mMap.addMarker(marker);

    }

    //Block back button
    @Override
    public void onBackPressed() {



    }

}