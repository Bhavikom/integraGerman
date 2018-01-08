package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerSearchRequestModel implements Parcelable {
    private String KundenNr, KaNr,MatchCode, Name1, Strasse, PLZ, Ort, Telefon;

    public String getKundenNr() {
        return KundenNr;
    }

    public void setKundenNr(String kundenNr) {
        KundenNr = kundenNr;
    }

    public String getKaNr() {
        return KaNr;
    }

    public void setKaNr(String kaNr) {
        KaNr = kaNr;
    }

    public String getMatchCode() {
        return MatchCode;
    }

    public void setMatchCode(String matchCode) {
        MatchCode = matchCode;
    }

    public String getName1() {
        return Name1;
    }

    public void setName1(String name1) {
        Name1 = name1;
    }

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String strasse) {
        Strasse = strasse;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.KundenNr);
        dest.writeString(this.KaNr);
        dest.writeString(this.MatchCode);
        dest.writeString(this.Name1);
        dest.writeString(this.Strasse);
        dest.writeString(this.PLZ);
        dest.writeString(this.Ort);
        dest.writeString(this.Telefon);
    }

    public CustomerSearchRequestModel() {
    }

    private CustomerSearchRequestModel(Parcel in) {
        this.KundenNr = in.readString();
        this.KaNr = in.readString();
        this.MatchCode = in.readString();
        this.Name1 = in.readString();
        this.Strasse = in.readString();
        this.PLZ = in.readString();
        this.Ort = in.readString();
        this.Telefon = in.readString();
    }

    public static final Parcelable.Creator<CustomerSearchRequestModel> CREATOR = new Parcelable.Creator<CustomerSearchRequestModel>() {
        public CustomerSearchRequestModel createFromParcel(Parcel source) {
            return new CustomerSearchRequestModel(source);
        }

        public CustomerSearchRequestModel[] newArray(int size) {
            return new CustomerSearchRequestModel[size];
        }
    };
}
