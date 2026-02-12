package com.example.learningandroid.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.widget.TextView;

import com.example.learningandroid.R;


public class RouteDetailsActivity extends AppCompatActivity {
    private Marker busMarker;
    private List<GeoPoint> routePoints;
    private int currentIndex = 0;
    private Handler handler = new Handler();

    @SuppressLint("SetTextI18n")


    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        routePoints = new ArrayList<>();

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



        routePoints.add(new GeoPoint(12.9141, 74.8560));
        routePoints.add(new GeoPoint(12.9150, 74.8570));
        routePoints.add(new GeoPoint(12.9160, 74.8580));
        routePoints.add(new GeoPoint(12.9170, 74.8590));
        routePoints.add(new GeoPoint(12.9180, 74.8600));
        busMarker.setPosition(routePoints.get(0));



    }
    private void updateBusLocation(double lat, double lon) {
        GeoPoint newLocation = new GeoPoint(lat, lon);
        busMarker.setPosition(newLocation);
        mapView.getController().animateTo(newLocation);
        mapView.invalidate();
    }
    private Runnable moveBusRunnable = new Runnable() {
        @Override
        public void run() {



            currentIndex = (currentIndex + 1) % routePoints.size();
            GeoPoint point = routePoints.get(currentIndex);
            updateBusLocation(point.getLatitude(), point.getLongitude());


            handler.postDelayed(this, 2000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        handler.postDelayed(moveBusRunnable, 2000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        handler.removeCallbacks(moveBusRunnable);
    }


}