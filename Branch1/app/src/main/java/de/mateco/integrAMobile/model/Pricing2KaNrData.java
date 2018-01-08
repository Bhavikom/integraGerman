package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BrijeshChavda on 2/17/2015.
 */
public class Pricing2KaNrData implements Parcelable {

    int KaNr;
    String Name;

    public Pricing2KaNrData() {
    }

    public Pricing2KaNrData(int kaNr, String name) {
        KaNr = kaNr;
        Name = name;
    }

    public int getKaNr() {
        return KaNr;
    }

    public void setKaNr(int kaNr) {
        KaNr = kaNr;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.KaNr);
        dest.writeString(this.Name);
    }

    private Pricing2KaNrData(Parcel in) {
        this.KaNr = in.readInt();
        this.Name = in.readString();
    }

    public static final Creator<Pricing2KaNrData> CREATOR = new Creator<Pricing2KaNrData>() {
        public Pricing2KaNrData createFromParcel(Parcel source) {
            return new Pricing2KaNrData(source);
        }

        public Pricing2KaNrData[] newArray(int size) {
            return new Pricing2KaNrData[size];
        }
    };
}
