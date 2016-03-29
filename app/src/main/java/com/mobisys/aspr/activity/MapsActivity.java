package com.mobisys.aspr.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobisys.aspr.util.ApplicationConstant;
import com.mobisys.aspr.util.Utils;
import com.pktworld.aspr.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, Animation.AnimationListener {

    private GoogleMap mMap;
    private TextView txtAddress;
    private Animation fadeIn,fadeOut;
    private String Address = null;
    private boolean isAddressVisible = false;
    private Double latitude = 0.00;
    private Double longitude = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        latitude = Double.parseDouble(getIntent().getStringExtra(ApplicationConstant.LATITUDE));
        longitude = Double.parseDouble(getIntent().getStringExtra(ApplicationConstant.LONGITUDE));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.txtToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtAddress.setVisibility(View.GONE);

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(800);
        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(100);
        fadeOut.setDuration(800);
        fadeIn.setAnimationListener(this);
        fadeOut.setAnimationListener(this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
        mMap.setOnMapLongClickListener(this);
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        //mMap.setMyLocationEnabled(true);
        LatLng latlng = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.
                fromBitmap(resizeMapIcons("ic_marker_red_big", 50, 70))));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), 16));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Address = Utils.getAddress(Double.toString(marker.getPosition().latitude)
                        ,Double.toString(marker.getPosition().longitude),getApplicationContext());
                txtAddress.setText(Address);
                if (!isAddressVisible){
                    txtAddress.startAnimation(fadeIn);
                    txtAddress.setVisibility(View.VISIBLE);
                    isAddressVisible = true;
                }
                return true;
            }
        });


    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (mMap != null){
            mMap.clear();
        }
        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.
                fromBitmap(resizeMapIcons("ic_marker_red_big", 50, 70))));
        Address = Utils.getAddress(Double.toString(latLng.latitude),Double.toString(latLng.longitude),this);
        txtAddress.setText(Address);
        if (!isAddressVisible){
            txtAddress.startAnimation(fadeIn);
            txtAddress.setVisibility(View.VISIBLE);
            isAddressVisible = true;
        }
    }

    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (animation == fadeIn){
            Handler threadHandler = new Handler(Looper.getMainLooper());
            threadHandler.postDelayed(new Runnable() {

                public void run() {
                    // TODO Auto-generated method stub
                   txtAddress.startAnimation(fadeOut);
                    txtAddress.setVisibility(View.GONE);
                    isAddressVisible = false;
                }
            }, 5000L);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
