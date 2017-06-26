package com.goldenxtime.com.goldenxtime.view.custom.adapter.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goldenxtime.com.goldenxtime.model.model.Property;

import butterknife.ButterKnife;

public class PropertyViewHolder extends RecyclerView.ViewHolder {

    public PropertyViewHolder(View itemView, Activity activity) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setPropertyView(Property property) {

    }
}
