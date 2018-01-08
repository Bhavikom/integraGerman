package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BuheneartModel implements Parcelable{

    private String Bezeichnung,BuehnenArt,Sort,Sprache;

    public String getBezeichnung() {
        return Bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        Bezeichnung = bezeichnung;
    }

    public String getBuehnenArt() {
        return BuehnenArt;
    }

    public void setBuehnenArt(String buehnenArt) {
        BuehnenArt = buehnenArt;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public String getSprache() {
        return Sprache;
    }

    public void setSprache(String sprache) {
        Sprache = sprache;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Bezeichnung);
        dest.writeString(this.BuehnenArt);
        dest.writeString(this.Sort);
        dest.writeString(this.Sprache);
    }

    public BuheneartModel() {
    }

    private BuheneartModel(Parcel in) {
        this.Bezeichnung = in.readString();
        this.BuehnenArt = in.readString();
        this.Sort = in.readString();
        this.Sprache = in.readString();
    }

    public static final Parcelable.Creator<BuheneartModel> CREATOR = new Parcelable.Creator<BuheneartModel>() {
        public BuheneartModel createFromParcel(Parcel source) {
            return new BuheneartModel(source);
        }

        public BuheneartModel[] newArray(int size) {
            return new BuheneartModel[size];
        }
    };
}
