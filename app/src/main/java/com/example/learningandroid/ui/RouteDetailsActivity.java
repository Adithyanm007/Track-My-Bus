package com.example.learningandroid.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.TextView;

import com.example.learningandroid.R;

public class RouteDetailsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_route_details); // REQUIRED
        mapView = findViewById(R.id.osmMap);

        mapView.setMultiTouchControls(true);

        GeoPoint mangalore = new GeoPoint(12.9141, 74.8560);
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