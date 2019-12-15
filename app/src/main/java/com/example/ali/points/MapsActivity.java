package com.example.ali.points;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    public double points=0.0;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;

  public GoogleMap mMap;
    int curser_location;
    // public   int Time=5;
    //public   int distance=0;
    DatabaseHelper databaseHelper;
   public LocationListener locationListener;
   public LocationManager locationManager;
    Button Start; // get start button
    Button Stop;  // get stop button

    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_maps );
        t1=(TextView )findViewById ( R.id.textView1 );
        t2=(TextView )findViewById ( R.id.textView2 );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ( SupportMapFragment )getSupportFragmentManager ( )
                .findFragmentById ( R.id.map );
        mapFragment.getMapAsync ( this );
        databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.isMasterEmpty()) {
            curser_location = 1;
        }

        Start = (Button ) findViewById(R.id.start);
        Stop = (Button) findViewById(R.id.stop);
        //Button settings = (Button) findViewById(R.id.setting);
        Button show = (Button) findViewById(R.id.show);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);


        locationListener = new LocationListener () {
            @Override
            public void onLocationChanged(Location location) {
               String x1,x2;
               x1=String.valueOf (location.getLatitude ());
                x2=String.valueOf (location.getLongitude ());
                //Toast.makeText(MapsActivity.this, "long=", Toast.LENGTH_SHORT).show();
                t1.setText (x1);
                t2.setText (x2);
               //t1.setText ("...");
               // t2.setText ("..");


              LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
                mMap.animateCamera(cameraUpdate);
                 //   locationManager.removeUpdates(this);
                mMap.addMarker ( new MarkerOptions ( ).position ( latLng ).title ( "Marker in Sydney" ) );
                mMap.moveCamera ( CameraUpdateFactory.newLatLng ( latLng ) );
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

                //latLng = new LatLng(location.getLatitude(), location.getLongitude());
                boolean isinsertted = databaseHelper.insertData(location.getLongitude(), location.getLatitude(), curser_location);



                if (isinsertted == true) {

                } else {

                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {

                Toast.makeText(getApplicationContext(), "GPS is Enabled", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onProviderDisabled(String provider) {

                Toast.makeText(getApplicationContext(), "GPS is disabled", Toast.LENGTH_SHORT).show();
            }
        };

        // start geetings points from user
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager ) getSystemService(LOCATION_SERVICE);

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
                Intent it=new Intent(MapsActivity.this,listview.class);
                startActivity(it);
            }
        });
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                centreMapOnLocation(location,"Your Location");
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }
            @Override
            public void onProviderEnabled(String s) {
            }
            @Override
            public void onProviderDisabled(String s) {
            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            centreMapOnLocation(lastKnownLocation,"Your Location");
        } else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }


    }

    public void centreMapOnLocation(Location location, String title){

        LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,12));

    }
    }

