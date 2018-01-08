package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Pricing1BranchData implements Parcelable {
    @SerializedName("Bezeichnung")
    private String designation;
    @SerializedName("Mandant")
    private int clientId;


    @SerializedName("Ort")
    private String Ort;

    public String getPlz() {
        return Plz;
    }

    public void setPlz(String plz) {
        Plz = plz;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String strasse) {
        Strasse = strasse;
    }

    @SerializedName("Plz")

    private String Plz;
    @SerializedName("Strasse")
    private String Strasse;

    public Pricing1BranchData() {

    }

    public Pricing1BranchData(String designation, int clientId,String ort,String plz,String strasse) {
        this.designation = designation;
        this.clientId = clientId;
        this.Ort = ort;
        this.Plz = plz;
        this.Strasse = strasse;
    }
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.designation);
        dest.writeInt(this.clientId);
        dest.writeString(this.Ort);
        dest.writeString(this.Plz);
        dest.writeString(this.Strasse);

    }

    private Pricing1BranchData(Parcel in) {
        this.designation = in.readString();
        this.clientId = in.readInt();
        this.Ort=in.readString();
        this.Plz=in.readString();
        this.Strasse=in.readString();
    }

    public static final Creator<Pricing1BranchData> CREATOR = new Creator<Pricing1BranchData>() {
        public Pricing1BranchData createFromParcel(Parcel source) {
            return new Pricing1BranchData(source);
        }

        public Pricing1BranchData[] newArray(int size) {
            return new Pricing1BranchData[size];
        }
    };
}
