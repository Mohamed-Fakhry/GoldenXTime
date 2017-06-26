package com.goldenxtime.com.goldenxtime.view.custom.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.model.Property;
import com.goldenxtime.com.goldenxtime.view.custom.adapter.viewholder.PropertyViewHolder;

import java.util.ArrayList;

public class PropertiesAdapter extends RecyclerView.Adapter<PropertyViewHolder> {

    ArrayList<Property> properties = new ArrayList();
    Activity activity;

    public PropertiesAdapter(ArrayList<Property> properties, Activity activity) {
        super();
        this.properties = properties;
        this.activity = activity;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PropertyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.propert_row, null), activity);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, int position) {
        holder.setPropertyView(properties.get(position));
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }
}
