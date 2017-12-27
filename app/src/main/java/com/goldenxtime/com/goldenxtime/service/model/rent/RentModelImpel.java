package com.goldenxtime.com.goldenxtime.service.model.rent;

import com.goldenxtime.com.goldenxtime.service.SetupService;
import com.goldenxtime.com.goldenxtime.service.responed.ResponedRent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentModelImpel implements RentModel {

    @Override
    public void getRent(final String id, final OnGetRentFinish listener) {
        SetupService.getService.getRent(id).enqueue(new Callback<ResponedRent>() {
            @Override
            public void onResponse(Call<ResponedRent> call, Response<ResponedRent> response) {
                if (response.isSuccessful())
                    listener.onSuccess(response.body().getRent());
                else if (response.code() == 401)
                    listener.onAccountStopped();
            }

            @Override
            public void onFailure(Call<ResponedRent> call, Throwable t) {
                listener.onCanceled();
            }
        });
    }
}
