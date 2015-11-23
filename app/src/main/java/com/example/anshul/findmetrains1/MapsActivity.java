package com.example.anshul.findmetrains1;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import java.lang.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import android.content.Intent;
import android.net.Uri;
import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText location_tf;
    Button b1; int i=-1;
    String a[]=new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener(){

        public void onClick(View v) {
            // Perform action on click
            onSearch(v);

        }});

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng dun = new LatLng(30.315630, 78.034985);
        mMap.addMarker(new MarkerOptions().position(dun).title("Marker in Dehradun"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dun));
        mMap.setMyLocationEnabled(true);
        //addingmarker();
    }

    public void onSearch(View view)
    {
        hide_soft_keyboard(view);
        i++;
        location_tf=(EditText)findViewById(R.id.et);
        String location= location_tf.getText().toString();
        if(i!=2){
            a[i]=location;
        }
        else
        {
            Bundle b=new Bundle();
            b.putStringArray("key", a);
            Intent intent=new Intent(MapsActivity.this,RailwayActivity.class);
            intent.putExtras(b);
        }
        List<Address> addressList=null;
        if(location!=null || !location.equals(" "))
        {
            Geocoder geocoder=new Geocoder(this);
            try {
                addressList= geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address= addressList.get(0);
            LatLng latLng= new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker").draggable(true));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
           // addingmarker();
        }


    }
    public void changeType(View view)
    {
if (mMap.getMapType()== GoogleMap.MAP_TYPE_NORMAL)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else
    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

public  void onZoom(View view)
    {
        if(view.getId()== R.id.Bzoomin)
        {
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId()==R.id.Bzoomout)
        {
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }
    private void hide_soft_keyboard(View view)
    {
        InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

/*public void addingmarker()
{
    mMap.setOnMapLongClickListener(new OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker").draggable(true));
        }
    });

}*/


}
