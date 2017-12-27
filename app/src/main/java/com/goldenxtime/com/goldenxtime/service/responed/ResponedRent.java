package com.goldenxtime.com.goldenxtime.service.responed;

import com.goldenxtime.com.goldenxtime.model.Rent;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponedRent {

    @SerializedName("current_rent")
    ArrayList<Rent> rents;

    public Rent getRent() {
        try {
            if (rents != null)
                return rents.get(0);

        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
