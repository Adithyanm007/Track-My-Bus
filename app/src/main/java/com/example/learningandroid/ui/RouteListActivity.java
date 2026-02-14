package com.example.learningandroid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learningandroid.R;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class RouteListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> routeIds = new ArrayList<>();
    ArrayList<String> routeNames = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private DatabaseReference routesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        routesRef= FirebaseDatabase.getInstance().getReference("routes");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        listView = findViewById(R.id.routeListView);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                routeNames);

        listView.setAdapter(adapter);

        routesRef = FirebaseDatabase.getInstance().getReference("routes");

        routesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                routeIds.clear();
                routeNames.clear();

                for (DataSnapshot routeSnap : snapshot.getChildren()) {

                    String id = routeSnap.getKey();
                    String name = routeSnap.child("name").getValue(String.class);

                    routeIds.add(id);
                    routeNames.add(name);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent = new Intent(this, RouteDetailsActivity.class);

            intent.putExtra("routeNumber", routeIds.get(position));

            startActivity(intent);
        });
    }
}