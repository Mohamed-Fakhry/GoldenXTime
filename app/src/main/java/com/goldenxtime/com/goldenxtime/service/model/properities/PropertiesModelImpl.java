package com.goldenxtime.com.goldenxtime.service.model.properities;

import com.goldenxtime.com.goldenxtime.model.Property;
import com.goldenxtime.com.goldenxtime.service.SetupService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertiesModelImpl implements PropertiesModel {

    @Override
    public void getProperties(final OnGetUserFinish listner) {

        SetupService.getService.getProperties().enqueue(new Callback<ArrayList<Property>>() {
            @Override
            public void onResponse(Call<ArrayList<Property>> call, Response<ArrayList<Property>> response) {
                if (response.isSuccessful()) {
                    listner.onSuccess(response.body());
                } else if (response.code() == 401)
                    listner.onAccountStopped();
            }

            @Override
            public void onFailure(Call<ArrayList<Property>> call, Throwable t) {
                listner.onCanceled();
            }
        });
    }
}
