package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PriceStaffelModel implements Parcelable {
    @SerializedName("Staffel")
    private String staffel;
    @SerializedName("Einheit")
    private String einheit;
    @SerializedName("AbEinheit")
    private String abEinheit;
    @SerializedName("BisEinheit")
    private String bisEinheit;
    @SerializedName("Bezeichnung")
    private String bezeichnung;
    @SerializedName("StandardStaffel")
    private String standardStaffel;
    @SerializedName("Aktiv")
    private String aktiv;
    @SerializedName("Sprache")
    private String sprache;
    @SerializedName("Angebotstext")
    private String angebotstext;

    public String getStaffel() {
        return staffel;
    }

    public void setStaffel(String staffel) {
        this.staffel = staffel;
    }

    public String getEinheit() {
        return einheit;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    public String getAbEinheit() {
        return abEinheit;
    }

    public void setAbEinheit(String abEinheit) {
        this.abEinheit = abEinheit;
    }

    public String getBisEinheit() {
        return bisEinheit;
    }

    public void setBisEinheit(String bisEinheit) {
        this.bisEinheit = bisEinheit;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getStandardStaffel() {
        return standardStaffel;
    }

    public void setStandardStaffel(String standardStaffel) {
        this.standardStaffel = standardStaffel;
    }

    public String getAktiv() {
        return aktiv;
    }

    public void setAktiv(String aktiv) {
        this.aktiv = aktiv;
    }

    public String getSprache() {
        return sprache;
    }

    public void setSprache(String sprache) {
        this.sprache = sprache;
    }

    public String getAngebotstext() {
        return angebotstext;
    }

    public void setAngebotstext(String angebotstext) {
        this.angebotstext = angebotstext;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.staffel);
        dest.writeString(this.einheit);
        dest.writeString(this.abEinheit);
        dest.writeString(this.bisEinheit);
        dest.writeString(this.bezeichnung);
        dest.writeString(this.standardStaffel);
        dest.writeString(this.aktiv);
        dest.writeString(this.sprache);
        dest.writeString(this.angebotstext);
    }

    public PriceStaffelModel() {
    }

    protected PriceStaffelModel(Parcel in) {
        this.staffel = in.readString();
        this.einheit = in.readString();
        this.abEinheit = in.readString();
        this.bisEinheit = in.readString();
        this.bezeichnung = in.readString();
        this.standardStaffel = in.readString();
        this.aktiv = in.readString();
        this.sprache = in.readString();
        this.angebotstext = in.readString();
    }

    public static final Parcelable.Creator<PriceStaffelModel> CREATOR = new Parcelable.Creator<PriceStaffelModel>() {
        public PriceStaffelModel createFromParcel(Parcel source) {
            return new PriceStaffelModel(source);
        }

        public PriceStaffelModel[] newArray(int size) {
            return new PriceStaffelModel[size];
        }
    };
}
