package com.goldenxtime.com.goldenxtime.service.model.properities;

import com.goldenxtime.com.goldenxtime.model.Property;

import java.util.ArrayList;

public interface PropertiesModel {

    interface OnGetUserFinish {
        void onCanceled();

        void onSuccess(ArrayList<Property> properties);

        void onAccountStopped();
    }

    void getProperties(OnGetUserFinish onGetPropertiesFinish);
}
