package com.goldenxtime.com.goldenxtime.view.adapter;

import android.support.v4.app.Fragment;

public abstract class ViewPageFragment extends Fragment {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
