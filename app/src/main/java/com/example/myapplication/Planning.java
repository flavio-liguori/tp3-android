package com.example.myapplication;


import android.os.Parcel;
import android.os.Parcelable;

public class Planning implements Parcelable {
    private long id;
    private long userId;
    private String creneau1;
    private String creneau2;
    private String creneau3;
    private String creneau4;

    public Planning() {
        this.creneau1 = "";
        this.creneau2 = "";
        this.creneau3 = "";
        this.creneau4 = "";
    }

    protected Planning(Parcel in) {
        id = in.readLong();
        userId = in.readLong();
        creneau1 = in.readString();
        creneau2 = in.readString();
        creneau3 = in.readString();
        creneau4 = in.readString();
    }

    public static final Creator<Planning> CREATOR = new Creator<Planning>() {
        @Override
        public Planning createFromParcel(Parcel in) {
            return new Planning(in);
        }

        @Override
        public Planning[] newArray(int size) {
            return new Planning[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCreneau1() {
        return creneau1;
    }

    public void setCreneau1(String creneau1) {
        this.creneau1 = creneau1;
    }

    public String getCreneau2() {
        return creneau2;
    }

    public void setCreneau2(String creneau2) {
        this.creneau2 = creneau2;
    }

    public String getCreneau3() {
        return creneau3;
    }

    public void setCreneau3(String creneau3) {
        this.creneau3 = creneau3;
    }

    public String getCreneau4() {
        return creneau4;
    }

    public void setCreneau4(String creneau4) {
        this.creneau4 = creneau4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(userId);
        dest.writeString(creneau1);
        dest.writeString(creneau2);
        dest.writeString(creneau3);
        dest.writeString(creneau4);
    }
}