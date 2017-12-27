package com.goldenxtime.com.goldenxtime.view.rent;

import com.goldenxtime.com.goldenxtime.model.Rent;
import com.goldenxtime.com.goldenxtime.service.model.rent.RentModel;
import com.goldenxtime.com.goldenxtime.service.model.rent.RentModelImpel;

public class RentPersenterImpl implements RentPresenter, RentModel.OnGetRentFinish {

    RentModel rentModel;
    RentView rentView;

    public RentPersenterImpl(RentView rentView) {
        this.rentView = rentView;
        this.rentModel = new RentModelImpel();
    }

    @Override
    public void getRent(String id) {
        if (rentView.isNetworkAvailable()) {
            rentView.setProgressB(true);
            rentModel.getRent(id, this);
        } else {
            rentView.setProgressB(false);
            rentView.noInternetConnection();
        }
    }

    @Override
    public void onSuccess(Rent rent) {
        rentView.setProgressB(false);
        if (rent != null) {
            rentView.showRentDetails(rent);
        } else {
            rentView.showNoRent();
        }
    }

    @Override
    public void onAccountStopped() {
        rentView.setProgressB(false);
        rentView.onAccountStopped();
    }

    @Override
    public void onCanceled() {
        rentView.setProgressB(false);
        rentView.showNoRent();
    }
}