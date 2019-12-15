package com.example.ali.points;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_maps3 );
        Button Send = (Button) findViewById(R.id.send);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ( SupportMapFragment )getSupportFragmentManager ( ).findFragmentById ( R.id.map );
        mapFragment.getMapAsync ( this );


        databaseHelper = new DatabaseHelper(this);
        final String getdata[][]=databaseHelper.readpoints(getIntent().getStringExtra("name"));



        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MapsActivity2.this,listview.class);
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

         mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng latLng = new LatLng ( 31.5799244, 74.3563183 );
        mMap.addMarker ( new MarkerOptions ( ).position ( latLng ).title ( "Marker in Sydney" ) );
        mMap.moveCamera ( CameraUpdateFactory.newLatLng ( latLng ) );
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
    }
}
