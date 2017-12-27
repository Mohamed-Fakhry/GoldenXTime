package com.goldenxtime.com.goldenxtime.model;

import com.google.gson.annotations.SerializedName;

public class User {

    private int id;
    private String name;
    private String email;
    @SerializedName("email_verified")
    private int emailVerified;
    private String phone;
    @SerializedName("phone_verified")
    private int phoneVerified;
    private String landline;
    private String address;
    private String picture;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getEmailVerified() {
        return emailVerified;
    }

    public String getPhone() {
        return phone;
    }

    public int getPhoneVerified() {
        return phoneVerified;
    }

    public String getLandline() {
        return landline;
    }

    public String getAddress() {
        return address;
    }

    public String getPicture() {
        return picture;
    }

    public String getPictureUrl() {
        return "http://www.goldenxtime.com/static/" + getPicture();
    }

    public void setPhoneVerified(int phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", phone='" + phone + '\'' +
                ", phoneVerified=" + phoneVerified +
                ", landline='" + landline + '\'' +
                ", address='" + address + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
