package com.example.learningandroid.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import com.google.firebase.database.DatabaseError;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;




import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.widget.TextView;

import com.example.learningandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RouteDetailsActivity extends AppCompatActivity {
    private Marker busMarker;


    @SuppressLint("SetTextI18n")


    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());


        setContentView(R.layout.activity_route_details); // REQUIRED
        mapView = findViewById(R.id.osmMap);

        mapView.setMultiTouchControls(true);

        GeoPoint mangalore = new GeoPoint(12.9241, 74.8360);
        mapView.getController().setZoom(14.0);
        mapView.getController().setCenter(mangalore);

        TextView routeTitle = findViewById(R.id.routeTitle);
        TextView routePath  = findViewById(R.id.routePath);

        Intent intent = getIntent();
        String routeNumber = intent.getStringExtra("routeNumber");
        String startPoint  = intent.getStringExtra("startPoint");
        String endPoint    = intent.getStringExtra("endPoint");

        routeTitle.setText("Route " + routeNumber);
        routePath.setText(startPoint + " → " + endPoint);
        busMarker = new Marker(mapView);
        busMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        busMarker.setTitle("Bus Location");

        mapView.getOverlays().add(busMarker);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bus);

        assert drawable != null;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

// Resize (example: 100x100)
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);

        BitmapDrawable scaledDrawable = new BitmapDrawable(getResources(), scaledBitmap);

        busMarker.setIcon(scaledDrawable);

        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference("routes")
                .child(routeNumber)
                .child("currentLocation");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Double lat = snapshot.child("lat").getValue(Double.class);
                    Double lng = snapshot.child("lng").getValue(Double.class);

                    if (lat != null && lng != null) {
                        updateBusLocation(lat, lng);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });




    }
    private void updateBusLocation(double lat, double lon) {
        GeoPoint newLocation = new GeoPoint(lat, lon);
        busMarker.setPosition(newLocation);
        mapView.getController().animateTo(newLocation);
        mapView.invalidate();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }


}