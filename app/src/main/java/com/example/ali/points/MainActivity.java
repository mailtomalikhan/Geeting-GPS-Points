package com.example.ali.points;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    int curser_location;
    // public   int Time=5;
    //public   int distance=0;
    DatabaseHelper databaseHelper;
    private LocationListener locationListener;
    private LocationManager locationManager;
    Button Start; // get start button
    Button Stop;  // get stop button
    // Button settings;  // get stop button
    TextView t1,t2; // get text view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.isMasterEmpty()) {
            curser_location = 1;
        }




        Start = (Button) findViewById(R.id.start);
        Stop = (Button) findViewById(R.id.stop);
        Button show = (Button) findViewById(R.id.show);
        t1=(TextView )findViewById ( R.id.latitude );
        t2=(TextView )findViewById ( R.id.longitude );
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
              String x1,x2;

                x1= String.valueOf ( location.getLatitude() );
                x2= String.valueOf ( location.getLongitude () );
                t1.setText (x1);
                t2.setText (x2);

                boolean isinsertted = databaseHelper.insertData(location.getLongitude(), location.getLatitude(), curser_location);
                if (isinsertted == true) {

                    Toast.makeText (MainActivity.this,  "datainserted ",Toast.LENGTH_LONG);
                } else {
                    Toast.makeText (MainActivity.this,  "not inserted ",Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {




            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                try {
                    if (databaseHelper.isMasterEmpty()) {
                        curser_location = 1;
                        databaseHelper.insertarea(0, curser_location);
                    } else {
                        curser_location = databaseHelper.last_location();
                        curser_location++;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 0, locationListener);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });

        // stop geting points from user
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                try {

                    locationManager.removeUpdates(locationListener);
                    if (curser_location == databaseHelper.last_location()) {

                    } else {
                        databaseHelper.insertarea(0, curser_location);
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            Intent it=new Intent(MainActivity.this,listview.class);
                            startActivity(it);
            }
        });
    }



}
