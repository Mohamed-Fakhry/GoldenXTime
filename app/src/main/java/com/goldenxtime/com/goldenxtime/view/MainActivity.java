package com.goldenxtime.com.goldenxtime.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.view.custom.adapter.ViewPagerAdapter;
import com.goldenxtime.com.goldenxtime.view.custom.ui.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager container;
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initContainer();
    }

    private void initContainer() {
        slidingTabLayout.setCustomTabView(R.layout.custom_tab, R.id.tabTV);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });
        container.setOffscreenPageLimit(4);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        container.setAdapter(viewPagerAdapter);
        slidingTabLayout.setViewPager(container);
    }
}
