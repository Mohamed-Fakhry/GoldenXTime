package com.goldenxtime.com.goldenxtime.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.service.SetupService;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPageFragment;
import com.goldenxtime.com.goldenxtime.view.adapter.ViewPagerAdapter;
import com.goldenxtime.com.goldenxtime.view.notification.NotificationsFragment;
import com.goldenxtime.com.goldenxtime.view.profile.ProfileFragment;
import com.goldenxtime.com.goldenxtime.view.properities.PropertiesFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager container;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;

    MenuItem prevMenuItem;

    public static Intent startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_name))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getString(R.string.font_name), true);

        sendDeviceID();
        initContainer();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void sendDeviceID() {
        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            JsonObject token = new JsonObject();
            token.addProperty("device_type", "android");
            token.addProperty("device_token", refreshedToken);
            SetupService.getService.sendToken(token).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {}

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
        } catch (Exception e) {}
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void initContainer() {
        container.setOffscreenPageLimit(4);
        ArrayList<ViewPageFragment> fragments = new ArrayList<>();
        fragments.add(NotificationsFragment.newInstance());
        fragments.add(PropertiesFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());

        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.notification:
                                container.setCurrentItem(0);
                                break;
                            case R.id.property:
                                container.setCurrentItem(1);
                                break;
                            case R.id.profile:
                                container.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigation.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        container.setAdapter(viewPagerAdapter);
        container.setCurrentItem(1);
    }
}