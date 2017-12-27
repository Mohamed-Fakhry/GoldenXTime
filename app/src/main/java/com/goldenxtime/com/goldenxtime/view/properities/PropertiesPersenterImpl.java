package com.goldenxtime.com.goldenxtime.view.properities;

import com.goldenxtime.com.goldenxtime.model.Property;
import com.goldenxtime.com.goldenxtime.service.model.properities.PropertiesModel;
import com.goldenxtime.com.goldenxtime.service.model.properities.PropertiesModelImpl;

import java.util.ArrayList;

public class PropertiesPersenterImpl implements ProperitiesPresenter, PropertiesModel.OnGetUserFinish {

    PropertiesView propertiesView;
    PropertiesModel propertiesModel;

    public PropertiesPersenterImpl(PropertiesView propertiesView) {
        this.propertiesView = propertiesView;
        this.propertiesModel = new PropertiesModelImpl();
    }

    @Override
    public void getProperties() {
        if (propertiesView.isNetworkAvailable()) {
            propertiesView.setProgressB(true);
            propertiesModel.getProperties(this);
        } else {
            propertiesView.setProgressB(false);
            propertiesView.noInternetConnection();
        }
    }

    @Override
    public void onCanceled() {
        propertiesView.setProgressB(false);
    }

    @Override
    public void onSuccess(ArrayList<Property> properties) {
        propertiesView.setProgressB(false);
        propertiesView.showProperties(properties);
    }

    @Override
    public void onAccountStopped() {
        propertiesView.setProgressB(false);
        propertiesView.onAccountStopped();
    }

}