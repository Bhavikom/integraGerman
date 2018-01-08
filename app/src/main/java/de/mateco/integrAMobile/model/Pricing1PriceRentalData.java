package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Pricing1PriceRentalData implements Parcelable {

    @SerializedName("bezeichnung")
    private String designation;
    @SerializedName("einheit")
    private int unit;

    public Pricing1PriceRentalData() {

    }

    public Pricing1PriceRentalData(String designation, int unit) {
        this.designation = designation;
        this.unit = unit;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.designation);
        dest.writeInt(this.unit);
    }

    private Pricing1PriceRentalData(Parcel in) {
        this.designation = in.readString();
        this.unit = in.readInt();
    }

    public static final Creator<Pricing1PriceRentalData> CREATOR = new Creator<Pricing1PriceRentalData>() {
        public Pricing1PriceRentalData createFromParcel(Parcel source) {
            return new Pricing1PriceRentalData(source);
        }

        public Pricing1PriceRentalData[] newArray(int size) {
            return new Pricing1PriceRentalData[size];
        }
    };
}
