package com.goldenxtime.com.goldenxtime.view.properities;

import com.goldenxtime.com.goldenxtime.model.Property;

import java.util.ArrayList;

public interface PropertiesView {
    void showProperties(ArrayList<Property> properties);

    void onAccountStopped();

    void setProgressB(boolean status);

    boolean isNetworkAvailable();

    void noInternetConnection();
}
