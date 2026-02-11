package com.example.learningandroid;
import android.content.Intent;

import com.example.learningandroid.ui.RouteDetailsActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;




import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningandroid.adapter.RouteAdapter;
import com.example.learningandroid.model.Route;

import java.util.ArrayList;
import java.util.List;






public class MainActivity extends AppCompatActivity {
    int clickcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.routeRecyclerView);

// 1. LayoutManager (vertical list)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

// 2. Dummy route data
        List<Route> routes = new ArrayList<>();
        routes.add(new Route("1", "State Bank", "Surathkal"));
        routes.add(new Route("2", "Pumpwell", "KSRTC"));
        routes.add(new Route("3", "Hampankatta", "NITK"));
        routes.add(new Route("4", "Bejai", "Ullal"));

// 3. Adapter
        RouteAdapter adapter = new RouteAdapter(routes, route -> {
            Intent intent = new Intent(this, RouteDetailsActivity.class);
            intent.putExtra("routeNumber", route.routeNumber);
            intent.putExtra("startPoint", route.startPoint);
            intent.putExtra("endPoint", route.endPoint);
            startActivity(intent);
        });


        recyclerView.setAdapter(adapter);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}