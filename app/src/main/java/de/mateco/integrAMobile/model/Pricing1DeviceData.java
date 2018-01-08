package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Pricing1DeviceData implements Parcelable {
    @SerializedName("bezeichnung")
    private String designation;
    @SerializedName("Hoehenhauptgruppe")
    private int height_main_group;

    public Pricing1DeviceData()
    {

    }

    public Pricing1DeviceData(String designation, int height_main_group)
    {
        this.designation = designation;
        this.height_main_group = height_main_group;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getHeight_main_group() {
        return height_main_group;
    }

    public void setHeight_main_group(int height_main_group) {
        this.height_main_group = height_main_group;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.designation);
        dest.writeInt(this.height_main_group);
    }

    private Pricing1DeviceData(Parcel in) {
        this.designation = in.readString();
        this.height_main_group = in.readInt();
    }

    public static final Creator<Pricing1DeviceData> CREATOR = new Creator<Pricing1DeviceData>() {
        public Pricing1DeviceData createFromParcel(Parcel source) {
            return new Pricing1DeviceData(source);
        }

        public Pricing1DeviceData[] newArray(int size) {
            return new Pricing1DeviceData[size];
        }
    };
}
