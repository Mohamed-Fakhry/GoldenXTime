package com.goldenxtime.com.goldenxtime.view.adapter.viewholder;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Property;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.valueTV)
    TextView valueTV;
    @BindView(R.id.keyTV)
    TextView keyTV;
    @BindView(R.id.layout)
    LinearLayout layout;

    public DetailViewHolder(View itemView, Activity activity) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDetailView(Property.Detail detail, int postion) {
        if (detail.getValue() != 0) {
            keyTV.setText(detail.getKey());
            valueTV.setText(String.valueOf(detail.getValue()));
            if (postion % 2 == 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layout.setBackgroundColor(itemView.getResources().getColor(R.color.colorAccent, null));
                } else {
                    layout.setBackgroundColor(itemView.getResources().getColor(R.color.colorAccent));
                }
            }
        } else {
            layout.setVisibility(View.GONE);
        }
    }
}