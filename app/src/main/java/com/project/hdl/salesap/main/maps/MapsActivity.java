package com.project.hdl.salesap.main.maps;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalData;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    //constant
    public static final String LAT_MAP_KEY = "LATITUDE";
    public static final String LON_MAP_KEY = "LONGITUDE";

    //layout component
    TextView addressLabel, localityLabel;

    GoogleMap mMap;
    Double lat, lon;
    List<Address> addressList;
    Address address;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity);
        activity = this;

        //initiate layout component
        addressLabel = (TextView) findViewById(R.id.addressLabel);
        localityLabel = (TextView) findViewById(R.id.localityLabel);

        lat = getIntent().getExtras().getDouble(LAT_MAP_KEY);
        lon = getIntent().getExtras().getDouble(LON_MAP_KEY);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Get the address
        try{
            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
            addressList = geocoder.getFromLocation(lat, lon, 1);
            address = addressList.get(0);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        // Add a marker in the location
        LatLng custLoct = new LatLng(lat, lon);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(custLoct);
        markerOptions.title(activity.getString(R.string.label_marker));

        // Point to location
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(custLoct, GlobalData.MAPS_ZOOM));

        addressLabel.setText(address.getAddressLine(0));
        localityLabel.setText(address.getLocality());
    }
}
