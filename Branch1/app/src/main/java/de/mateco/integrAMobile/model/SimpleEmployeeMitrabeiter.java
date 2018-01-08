package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleEmployeeMitrabeiter implements Parcelable {
    private String Mitarbeiter;

    public String getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Mitarbeiter);
    }

    public SimpleEmployeeMitrabeiter() {
    }

    private SimpleEmployeeMitrabeiter(Parcel in) {
        this.Mitarbeiter = in.readString();
    }

    public static final Creator<SimpleEmployeeMitrabeiter> CREATOR = new Creator<SimpleEmployeeMitrabeiter>() {
        public SimpleEmployeeMitrabeiter createFromParcel(Parcel source) {
            return new SimpleEmployeeMitrabeiter(source);
        }

        public SimpleEmployeeMitrabeiter[] newArray(int size) {
            return new SimpleEmployeeMitrabeiter[size];
        }
    };
}
