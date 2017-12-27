package com.goldenxtime.com.goldenxtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Property implements Parcelable {

    private int id;
    private String name;
    @SerializedName("unit_id")
    private int unitId;
    @SerializedName("unit_type")
    private String unitYype;
    @SerializedName("class")
    private String classProperty;
    private String area;
    private String address;
    private String notes;
    private int rented;
    private int rentable;
    @SerializedName("top_level")
    private int topLevel;
    @SerializedName("parent_property_id")
    private int parentPropertyId;
    @SerializedName("owner_id")
    private int ownerId;
    ArrayList<Photo> photos;
    ArrayList<Property> childs;
    ArrayList<Detail> details;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUnitId() {
        return unitId;
    }

    public String getUnitYype() {
        return unitYype;
    }

    public String getClassProperty() {
        return classProperty;
    }

    public String getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    public int getRented() {
        return rented;
    }

    public int getRentable() {
        return rentable;
    }

    public int getTopLevel() {
        return topLevel;
    }

    public int getParentPropertyId() {
        return parentPropertyId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public ArrayList<Property> getChilds() {
        return childs;
    }

    public ArrayList<Detail> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitId=" + unitId +
                ", unitYype='" + unitYype + '\'' +
                ", classProperty='" + classProperty + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", notes='" + notes + '\'' +
                ", rented=" + rented +
                ", rentable=" + rentable +
                ", topLevel=" + topLevel +
                ", parentPropertyId=" + parentPropertyId +
                ", owner_id=" + ownerId +
                ", childs=" + childs +
                '}';
    }

    public static class Photo implements Parcelable {
        private int id;
        private String file;


        public String getFile() {
            return file;
        }

        public String getPictureUrl() {
            return "http://www.goldenxtime.com/static/" + getFile();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(file);
        }

        private Photo(Parcel in) {
            id = in.readInt();
            file = in.readString();
        }

        public static final Creator<Photo> CREATOR = new Creator<Photo>() {
            @Override
            public Photo createFromParcel(Parcel in) {
                return new Photo(in);
            }

            @Override
            public Photo[] newArray(int size) {
                return new Photo[size];
            }
        };
    }

    public static class Detail implements Parcelable {
        private String key;
        private int value;

        public String getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(key);
            parcel.writeInt(value);
        }

        private Detail(Parcel in) {
            key = in.readString();
            value = in.readInt();
        }

        public static final Creator<Detail> CREATOR = new Creator<Detail>() {
            @Override
            public Detail createFromParcel(Parcel in) {
                return new Detail(in);
            }

            @Override
            public Detail[] newArray(int size) {
                return new Detail[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.unitId);
        dest.writeString(this.unitYype);
        dest.writeString(this.classProperty);
        dest.writeString(this.area);
        dest.writeString(this.address);
        dest.writeString(this.notes);
        dest.writeInt(this.rented);
        dest.writeInt(this.rentable);
        dest.writeInt(this.topLevel);
        dest.writeInt(this.parentPropertyId);
        dest.writeInt(this.ownerId);
        dest.writeTypedList(this.photos);
        dest.writeTypedList(this.childs);
        dest.writeTypedList(this.details);
    }

    protected Property(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.unitId = in.readInt();
        this.unitYype = in.readString();
        this.classProperty = in.readString();
        this.area = in.readString();
        this.address = in.readString();
        this.notes = in.readString();
        this.rented = in.readInt();
        this.rentable = in.readInt();
        this.topLevel = in.readInt();
        this.parentPropertyId = in.readInt();
        this.ownerId = in.readInt();
        this.photos = in.createTypedArrayList(Photo.CREATOR);
        this.childs = in.createTypedArrayList(Property.CREATOR);
        this.details = in.createTypedArrayList(Detail.CREATOR);
    }

    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel source) {
            return new Property(source);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };
}
