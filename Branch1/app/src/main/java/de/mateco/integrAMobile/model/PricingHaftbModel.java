package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PricingHaftbModel implements Parcelable {
    private String name, value, selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
        dest.writeString(this.selected);
    }

    public PricingHaftbModel() {
    }

    private PricingHaftbModel(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
        this.selected = in.readString();
    }

    public static final Parcelable.Creator<PricingHaftbModel> CREATOR = new Parcelable.Creator<PricingHaftbModel>() {
        public PricingHaftbModel createFromParcel(Parcel source) {
            return new PricingHaftbModel(source);
        }

        public PricingHaftbModel[] newArray(int size) {
            return new PricingHaftbModel[size];
        }
    };
}
