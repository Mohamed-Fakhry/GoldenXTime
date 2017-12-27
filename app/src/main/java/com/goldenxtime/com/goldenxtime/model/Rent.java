package com.goldenxtime.com.goldenxtime.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rent {

    @SerializedName("renter_name")
    private String renter;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    private String type;
    private int value;
    @SerializedName("type_display_name")
    private String typeDisplayName;
    @SerializedName("case")
    private ArrayList<Case> caseRent;
    private ArrayList<Property.Photo> photos;
    private ArrayList<Collection> collections;

    public String getRenter() {
        return renter;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public String getTypeDisplayName() {
        return typeDisplayName;
    }

    public ArrayList<Collection> getCollections() {
        return collections;
    }

    public ArrayList<Property.Photo> getPhotos() {
        return photos;
    }

    public ArrayList<Case> getCaseRent() {
        return caseRent;
    }

    public class Case {
        private String reason;
        @SerializedName("created_at")
        private String createdAt;

        public String getReasone() {
            return reason;
        }

        public void setReasone(String resone) {
            this.reason = resone;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        @Override
        public String toString() {
            return "Case{" +
                    "resone='" + reason + '\'' +
                    ", created_at='" + createdAt + '\'' +
                    '}';
        }
    }

    public class Collection {
        private String type;
        private int given;
        @SerializedName("created_at")
        private String at;
        @SerializedName("check")
        private ArrayList<Check> checks;
        @SerializedName("type_display_name")
        private String typeDisplayName;

        public String getType() {
            return type;
        }

        public int getGiven() {
            return given;
        }

        public String getAt() {
            return at;
        }

        public ArrayList<Check> getChecks() {
            return checks;
        }

        public String getTypeDisplayName() {
            return typeDisplayName;
        }

        public class Check {
            private String number;
            @SerializedName("due_date")
            private String dueDate;
            @SerializedName("from_bank_name")
            private String fromBank;
            @SerializedName("to_bank_name")
            private String toBank;
            private int value;

            public String getNumber() {
                return number;
            }

            public String getDueDate() {
                return dueDate;
            }

            public String getFromBank() {
                return fromBank;
            }

            public String getToBank() {
                return toBank;
            }

            public int getValue() {
                return value;
            }

            @Override
            public String toString() {
                return "Check{" +
                        "number='" + number + '\'' +
                        ", dueDate='" + dueDate + '\'' +
                        ", fromBank='" + fromBank + '\'' +
                        ", toBank='" + toBank + '\'' +
                        ", value=" + value +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Collection{" +
                    "type='" + type + '\'' +
                    ", given=" + given +
                    ", at='" + at + '\'' +
                    ", checks=" + checks +
                    ", typeDisplayName='" + typeDisplayName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Rent{" +
                "renter='" + renter + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", type='" + type + '\'' +
                ", value=" + value +
                ", typeDisplayName='" + typeDisplayName + '\'' +
                ", caseRent=" + caseRent +
                ", photos=" + photos +
                ", collections=" + collections +
                '}';
    }
}
