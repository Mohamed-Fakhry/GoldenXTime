package com.goldenxtime.com.goldenxtime.service.model.rent;

import com.goldenxtime.com.goldenxtime.model.Rent;

import java.util.ArrayList;

public interface RentModel {

    interface OnGetRentFinish {
        void onCanceled();

        void onSuccess(Rent rent);

        void onAccountStopped();
    }

    void getRent(String id, OnGetRentFinish listener);
}