package com.goldenxtime.com.goldenxtime.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Property;
import com.goldenxtime.com.goldenxtime.service.SetupService;
import com.goldenxtime.com.goldenxtime.view.adapter.BaseView;
import com.goldenxtime.com.goldenxtime.view.adapter.TextSliderView;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPageFragment;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPagerAdapter;
import com.goldenxtime.com.goldenxtime.view.rent.RentFragment;
import com.goldenxtime.com.goldenxtime.view.rent.RentsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

public class ProperityActivity extends AppCompatActivity {

    @BindView(R.id.tabTL)
    TabLayout tabL;
    @BindView(R.id.containerVP)
    ViewPager containerVP;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.nestedScrollV)
    NestedScrollView nestedScrollV;
    @BindView(R.id.properityIV)
    ImageView properityIV;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Property property;
        if (intent != null) {
            if (intent.getExtras().get("content") != null) {
                property = (Property) intent.getExtras().get("content");
                setView(property);
            } else if (intent.getExtras().get("id") != null) {
                int id = (int) intent.getExtras().get("id");
                SetupService.getService.getProperity(id).enqueue(new Callback<Property>() {
                    @Override
                    public void onResponse(Call<Property> call, Response<Property> response) {
                        if (response.isSuccessful())
                            setView(response.body());
                    }

                    @Override
                    public void onFailure(Call<Property> call, Throwable t) {}
                });
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setView(Property property) {
        collapsing.setTitle(property.getName());
        nestedScrollV.setFillViewport(true);

        try {
            if (property.getPhotos() != null && property.getPhotos().size() > 1) {
                if (properityIV.getVisibility() == View.VISIBLE)
                    properityIV.setVisibility(View.GONE);
                for (int i = 0; i < property.getPhotos().size(); i++) {
                    BaseView baseSliderView = new TextSliderView(this);
                    baseSliderView.image(property.getPhotos().get(i).getPictureUrl());
                    slider.addSlider(baseSliderView);
                }
            } else {
                if (property.getPhotos() != null && property.getPhotos().size() == 1) {
                    Glide.with(this)
                            .load(property.getPhotos().get(0).getPictureUrl())
                            .into(properityIV);
                } else {
                    Glide.with(this)
                            .load(R.drawable.property_image)
                            .into(properityIV);
                }
            }
        } catch (Exception e) {
            if (slider.getVisibility() == View.VISIBLE)
                slider.setVisibility(View.GONE);

            Glide.with(this)
                    .load(R.drawable.property_image)
                    .into(properityIV);
        }

        ArrayList<ViewPageFragment> fragments = new ArrayList<>();

        TabLayout.Tab tab1;

        int currentItem = 0;

        if (property.getRented() == 1) {
            TabLayout.Tab tab2 = tabL.newTab().setText("التحصيبل");
            fragments.add(RentsFragment.newInstance(property.getId()));
            tabL.addTab(tab2);
            currentItem++;
        }
        if (property.getRentable() == 1) {
            tab1 = tabL.newTab().setText("تفاصيل الايجار");
            fragments.add(RentFragment.newInstance(property.getId()));
            tabL.addTab(tab1);
            currentItem++;
        }
        if (property.getChilds() != null && !property.getChilds().isEmpty()) {
            tab1 = tabL.newTab().setText("تحتوى على");
            fragments.add(SubPropertiesFragment.newInstance(property.getChilds()));
            tabL.addTab(tab1);
            currentItem++;
        }

        TabLayout.Tab tab2 = tabL.newTab().setText("تفاصيل العقار");

        tabL.addTab(tab2);

        fragments.add(PropertyDetailsFragment.newInstance(property));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        containerVP.setAdapter(viewPagerAdapter);
        containerVP.setCurrentItem(currentItem);
        tabL.setupWithViewPager(containerVP);
        changeFontInViewGroup(tabL);
        final Typeface tf = Typeface.createFromAsset(getAssets(), getString(R.string.font_name));
        collapsing.setCollapsedTitleTypeface(tf);
        collapsing.setExpandedTitleTypeface(tf);

    }

    void changeFontInViewGroup(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (TextView.class.isAssignableFrom(child.getClass())) {
                CalligraphyUtils.applyFontToTextView(child.getContext(), (TextView) child, getString(R.string.font_name));
            } else if (ViewGroup.class.isAssignableFrom(child.getClass())) {
                changeFontInViewGroup((ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }

}