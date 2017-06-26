package com.goldenxtime.com.goldenxtime.view.custom.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.goldenxtime.com.goldenxtime.view.NotificationsFragment;
import com.goldenxtime.com.goldenxtime.view.ProfileFragment;
import com.goldenxtime.com.goldenxtime.view.PropertiesFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Activity activity;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<Integer> icons = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;

        fragments.add(ProfileFragment.newInstance());
        icons.add(ProfileFragment.icon);
        fragments.add(PropertiesFragment.newInstance());
        icons.add(PropertiesFragment.icon);
        fragments.add(NotificationsFragment.newInstance());
        icons.add(NotificationsFragment.icon);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        Drawable drawable = activity.getResources().getDrawable(icons.get(position));
        drawable.setBounds(0,0,110,100);      // tabs size
        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
