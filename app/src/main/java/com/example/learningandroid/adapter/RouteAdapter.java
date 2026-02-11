package com.example.learningandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningandroid.R;
import com.example.learningandroid.model.Route;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    public interface OnRouteClickListener {
        void onRouteClick(Route route);
    }

    private List<Route> routeList;
    private OnRouteClickListener listener;

    public RouteAdapter(List<Route> routeList, OnRouteClickListener listener) {
        this.routeList = routeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        Route route = routeList.get(position);
        holder.routeText.setText(
                route.routeNumber + " : " + route.startPoint + " → " + route.endPoint
        );

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRouteClick(route);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    static class RouteViewHolder extends RecyclerView.ViewHolder {

        TextView routeText;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeText = itemView.findViewById(R.id.routeText);
        }
    }
}
