package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SiteInspectionPracticabilityModel implements Parcelable {
    private String Befahrbarkeit, BefahrbarkeitText;

    public String getBefahrbarkeit() {
        return Befahrbarkeit;
    }

    public void setBefahrbarkeit(String befahrbarkeit) {
        Befahrbarkeit = befahrbarkeit;
    }

    public String getBefahrbarkeitText() {
        return BefahrbarkeitText;
    }

    public void setBefahrbarkeitText(String befahrbarkeitText) {
        BefahrbarkeitText = befahrbarkeitText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Befahrbarkeit);
        dest.writeString(this.BefahrbarkeitText);
    }

    public SiteInspectionPracticabilityModel() {
    }

    private SiteInspectionPracticabilityModel(Parcel in) {
        this.Befahrbarkeit = in.readString();
        this.BefahrbarkeitText = in.readString();
    }

    public static final Creator<SiteInspectionPracticabilityModel> CREATOR = new Creator<SiteInspectionPracticabilityModel>() {
        public SiteInspectionPracticabilityModel createFromParcel(Parcel source) {
            return new SiteInspectionPracticabilityModel(source);
        }

        public SiteInspectionPracticabilityModel[] newArray(int size) {
            return new SiteInspectionPracticabilityModel[size];
        }
    };
}
