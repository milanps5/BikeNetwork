package com.milanps.bikenetwork;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String name;
    private String city;
    private String country;
    private Float longitude;
    private Float latitude;
    Intent intent;
    private MarkerOptions marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        intent = getIntent();
        name = intent.getStringExtra("name");
        city = intent.getStringExtra("city");
        country = intent.getStringExtra("country");
        latitude = intent.getFloatExtra("latitude", 0);
        longitude = intent.getFloatExtra("longitude", 0);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bikeCompany = new LatLng(latitude, longitude);
        marker = new MarkerOptions();
        marker.position(bikeCompany).title(name).snippet(city + country);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bikeCompany));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                openDialog();
                return false;
            }
        });


    }

    public void openDialog() {

        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);

        alertDialog2.setTitle("Company Details:");

        StringBuilder sb = new StringBuilder();
        sb.append("Company name: ").append(name).append("\n");
        sb.append("City: ").append(city).append("\n");
        sb.append("Country:  ").append(country);

        alertDialog2.setMessage(sb.toString());

        alertDialog2.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialog2.show();
    }

}
