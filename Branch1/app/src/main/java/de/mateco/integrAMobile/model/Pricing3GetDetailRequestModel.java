package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mmehta on 05.11.2015.
 */
public class Pricing3GetDetailRequestModel implements Parcelable {
    private String kontakt, kanr, deviceType, zipCode, mandant;
    private String GeratetypeId;

    public String getGeratetypeId() {
        return GeratetypeId;
    }

    public void setGeratetypeId(String geratetypeId) {
        GeratetypeId = geratetypeId;
    }

    public String getMandant() {
        return mandant;
    }

    public void setMandant(String mandant) {
        this.mandant = mandant;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getKanr() {
        return kanr;
    }

    public void setKanr(String kanr) {
        this.kanr = kanr;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public Pricing3GetDetailRequestModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kontakt);
        dest.writeString(this.kanr);
        dest.writeString(this.deviceType);
        dest.writeString(this.zipCode);
        dest.writeString(this.mandant);
        dest.writeString(this.GeratetypeId);
    }

    protected Pricing3GetDetailRequestModel(Parcel in) {
        this.kontakt = in.readString();
        this.kanr = in.readString();
        this.deviceType = in.readString();
        this.zipCode = in.readString();
        this.mandant = in.readString();
        this.GeratetypeId = in .readString();
    }

    public static final Creator<Pricing3GetDetailRequestModel> CREATOR = new Creator<Pricing3GetDetailRequestModel>() {
        public Pricing3GetDetailRequestModel createFromParcel(Parcel source) {
            return new Pricing3GetDetailRequestModel(source);
        }

        public Pricing3GetDetailRequestModel[] newArray(int size) {
            return new Pricing3GetDetailRequestModel[size];
        }
    };
}
