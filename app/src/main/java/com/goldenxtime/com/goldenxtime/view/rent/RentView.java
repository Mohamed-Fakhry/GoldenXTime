package com.goldenxtime.com.goldenxtime.view.rent;

import com.goldenxtime.com.goldenxtime.model.Rent;

public interface RentView  {
    void showRentDetails(Rent rent);

    void showNoRent();

    void onAccountStopped();

    void setProgressB(boolean status);

    boolean isNetworkAvailable();

    void noInternetConnection();
}
