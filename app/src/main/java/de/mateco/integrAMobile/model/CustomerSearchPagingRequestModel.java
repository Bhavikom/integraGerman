package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerSearchPagingRequestModel implements Parcelable {
    private String KundenNr;
    private String KaNr;
    private String MatchCode;
    private String Name1;
    private String Strasse;
    private String PLZ;
    private String Ort;
    private String Telefon;
    private String PageNumber;
    private String PageSize;

    public String getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }

    private String Mitarbeiter;

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
        dest.writeString(this.KundenNr);
        dest.writeString(this.KaNr);
        dest.writeString(this.MatchCode);
        dest.writeString(this.Name1);
        dest.writeString(this.Strasse);
        dest.writeString(this.PLZ);
        dest.writeString(this.Ort);
        dest.writeString(this.Telefon);
        dest.writeString(this.PageNumber);
        dest.writeString(this.PageSize);
    }

    public CustomerSearchPagingRequestModel() {
    }

    private CustomerSearchPagingRequestModel(Parcel in) {
        this.KundenNr = in.readString();
        this.KaNr = in.readString();
        this.MatchCode = in.readString();
        this.Name1 = in.readString();
        this.Strasse = in.readString();
        this.PLZ = in.readString();
        this.Ort = in.readString();
        this.Telefon = in.readString();
        this.PageNumber = in.readString();
        this.PageSize = in.readString();
    }

    public static final Parcelable.Creator<CustomerSearchPagingRequestModel> CREATOR = new Parcelable.Creator<CustomerSearchPagingRequestModel>() {
        public CustomerSearchPagingRequestModel createFromParcel(Parcel source) {
            return new CustomerSearchPagingRequestModel(source);
        }

        public CustomerSearchPagingRequestModel[] newArray(int size) {
            return new CustomerSearchPagingRequestModel[size];
        }
    };
}
