package com.goldenxtime.com.goldenxtime.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Property;
import com.goldenxtime.com.goldenxtime.view.adapter.viewholder.DetailViewHolder;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailViewHolder> {

    ArrayList<Property.Detail> details = new ArrayList<>();
    Activity activity;

    public DetailsAdapter(ArrayList<Property.Detail> details, Activity activity) {
        this.details = details;
        this.activity = activity;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DetailViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.detail_row, null), activity);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        holder.setDetailView(details.get(position), position);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
