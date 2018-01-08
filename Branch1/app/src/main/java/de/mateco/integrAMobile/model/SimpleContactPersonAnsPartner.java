package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleContactPersonAnsPartner implements Parcelable {
    private String AnsPartner;

    public String getAnsPartner() {
        return AnsPartner;
    }

    public void setAnsPartner(String ansPartner) {
        AnsPartner = ansPartner;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AnsPartner);
    }

    public SimpleContactPersonAnsPartner() {
    }

    private SimpleContactPersonAnsPartner(Parcel in) {
        this.AnsPartner = in.readString();
    }

    public static final Parcelable.Creator<SimpleContactPersonAnsPartner> CREATOR = new Parcelable.Creator<SimpleContactPersonAnsPartner>() {
        public SimpleContactPersonAnsPartner createFromParcel(Parcel source) {
            return new SimpleContactPersonAnsPartner(source);
        }

        public SimpleContactPersonAnsPartner[] newArray(int size) {
            return new SimpleContactPersonAnsPartner[size];
        }
    };
}
