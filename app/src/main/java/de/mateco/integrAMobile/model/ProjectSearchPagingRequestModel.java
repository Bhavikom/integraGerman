package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectSearchPagingRequestModel implements Parcelable {
    private String Matchcode, Beschreibung, ObjektTyp, Adresse, Ort, Beginn, Ende, Mitrabeiter, PageNumber, PageSize;

    public String getMatchcode() {
        return Matchcode;
    }

    public void setMatchcode(String matchcode) {
        Matchcode = matchcode;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public String getObjektTyp() {
        return ObjektTyp;
    }

    public void setObjektTyp(String objektTyp) {
        ObjektTyp = objektTyp;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getBeginn() {
        return Beginn;
    }

    public void setBeginn(String beginn) {
        Beginn = beginn;
    }

    public String getEnde() {
        return Ende;
    }

    public void setEnde(String ende) {
        Ende = ende;
    }

    public String getMitrabeiter() {
        return Mitrabeiter;
    }

    public void setMitrabeiter(String mitrabeiter) {
        Mitrabeiter = mitrabeiter;
    }

    public String getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(String pageNumber) {
        PageNumber = pageNumber;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Matchcode);
        dest.writeString(this.Beschreibung);
        dest.writeString(this.ObjektTyp);
        dest.writeString(this.Adresse);
        dest.writeString(this.Ort);
        dest.writeString(this.Beginn);
        dest.writeString(this.Ende);
        dest.writeString(this.Mitrabeiter);
        dest.writeString(this.PageNumber);
        dest.writeString(this.PageSize);
    }

    public ProjectSearchPagingRequestModel() {
    }

    private ProjectSearchPagingRequestModel(Parcel in) {
        this.Matchcode = in.readString();
        this.Beschreibung = in.readString();
        this.ObjektTyp = in.readString();
        this.Adresse = in.readString();
        this.Ort = in.readString();
        this.Beginn = in.readString();
        this.Ende = in.readString();
        this.Mitrabeiter = in.readString();
        this.PageNumber = in.readString();
        this.PageSize = in.readString();
    }

    public static final Creator<ProjectSearchPagingRequestModel> CREATOR = new Creator<ProjectSearchPagingRequestModel>() {
        public ProjectSearchPagingRequestModel createFromParcel(Parcel source) {
            return new ProjectSearchPagingRequestModel(source);
        }

        public ProjectSearchPagingRequestModel[] newArray(int size) {
            return new ProjectSearchPagingRequestModel[size];
        }
    };
}
