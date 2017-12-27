package com.goldenxtime.com.goldenxtime.view.adapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Property;
import com.goldenxtime.com.goldenxtime.service.LoginPref;
import com.goldenxtime.com.goldenxtime.view.ProperityActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.card_view)
    CardView view;
    @BindView(R.id.propertyIV)
    ImageView propertyIV;
    @BindView(R.id.propertyNameTV)
    TextView propertyNameTV;

    Activity activity;

    public PropertyViewHolder(View itemView, Activity activity) {
        super(itemView);
        this.activity = activity;
        ButterKnife.bind(this, itemView);
    }

    public void setPropertyView(final Property property) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setElevation(new Float(12.0));
                }

                Intent intent = new Intent(activity, ProperityActivity.class);
                intent.putExtra("content", property);
                activity.startActivity(intent);
            }
        });

        if (property.getPhotos() != null) {
            setImage(property);
        }

        propertyNameTV.setText(property.getName());
    }

    private void setImage(Property property) {
        try {
            LoginPref loginPref = new LoginPref(activity);
            String accessToken = loginPref.getAccessToken();
            if (property.getPhotos().get(0) != null && property.getPhotos().get(0).getFile() != null) {
                GlideUrl glideUrl = new GlideUrl(property.getPhotos().get(0).getPictureUrl(), new LazyHeaders.Builder()
                        .addHeader("x-access-token", accessToken)
                        .build());

                Glide.with(activity).load(glideUrl).into(propertyIV);
            }
        } catch (Exception e) {}
    }
}
