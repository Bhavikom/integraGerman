package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerNewModel implements Parcelable
{
    private String Matchcode, Name1, Name2, Name3, Strasse, PLZ, Ort, Land, Telefon, Telefax, EMail, WWW, UstidNr, Mitarbeiter;
    private String Rechtsform, Inhaber, JBestNr, Umsatzpotenzial, ErfMitarbeiter, Branche;
    private String AenderungMitarbeiter, UserID;

    public String getStreetOrIndustry() {
        return StreetOrIndustry;
    }

    public void setStreetOrIndustry(String streetOrIndustry) {
        StreetOrIndustry = streetOrIndustry;
    }

    private String StreetOrIndustry;
    //Gebiet, ErfDatum, Aenderungsstatus, Geloescht, LetzteAenderung


    public String getMatchcode() {
        return Matchcode;
    }

    public void setMatchcode(String matchcode) {
        Matchcode = matchcode;
    }

    public String getName1() {
        return Name1;
    }

    public void setName1(String name1) {
        Name1 = name1;
    }

    public String getName2() {
        return Name2;
    }

    public void setName2(String name2) {
        Name2 = name2;
    }

    public String getName3() {
        return Name3;
    }

    public void setName3(String name3) {
        Name3 = name3;
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

    public String getLand() {
        return Land;
    }

    public void setLand(String land) {
        Land = land;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getTelefax() {
        return Telefax;
    }

    public void setTelefax(String telefax) {
        Telefax = telefax;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getWWW() {
        return WWW;
    }

    public void setWWW(String WWW) {
        this.WWW = WWW;
    }

    public String getUstidNr() {
        return UstidNr;
    }

    public void setUstidNr(String ustidNr) {
        UstidNr = ustidNr;
    }

    public String getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }

    public String getRechtsform() {
        return Rechtsform;
    }

    public void setRechtsform(String rechtsform) {
        Rechtsform = rechtsform;
    }

    public String getInhaber() {
        return Inhaber;
    }

    public void setInhaber(String inhaber) {
        Inhaber = inhaber;
    }

    public String getJBestNr() {
        return JBestNr;
    }

    public void setJBestNr(String JBestNr) {
        this.JBestNr = JBestNr;
    }

    public String getUmsatzpotenzial() {
        return Umsatzpotenzial;
    }

    public void setUmsatzpotenzial(String umsatzpotenzial) {
        Umsatzpotenzial = umsatzpotenzial;
    }

    public String getErfMitarbeiter() {
        return ErfMitarbeiter;
    }

    public void setErfMitarbeiter(String erfMitarbeiter) {
        ErfMitarbeiter = erfMitarbeiter;
    }

    public String getBranche() {
        return Branche;
    }

    public void setBranche(String branche) {
        Branche = branche;
    }

    public String getAenderungMitarbeiter() {
        return AenderungMitarbeiter;
    }

    public void setAenderungMitarbeiter(String aenderungMitarbeiter) {
        AenderungMitarbeiter = aenderungMitarbeiter;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Matchcode);
        dest.writeString(this.Name1);
        dest.writeString(this.Name2);
        dest.writeString(this.Name3);
        dest.writeString(this.Strasse);
        dest.writeString(this.PLZ);
        dest.writeString(this.Ort);
        dest.writeString(this.Land);
        dest.writeString(this.Telefon);
        dest.writeString(this.Telefax);
        dest.writeString(this.EMail);
        dest.writeString(this.WWW);
        dest.writeString(this.UstidNr);
        dest.writeString(this.Mitarbeiter);
        dest.writeString(this.Rechtsform);
        dest.writeString(this.Inhaber);
        dest.writeString(this.JBestNr);
        dest.writeString(this.Umsatzpotenzial);
        dest.writeString(this.ErfMitarbeiter);
        dest.writeString(this.Branche);
        dest.writeString(this.AenderungMitarbeiter);
        dest.writeString(this.UserID);
    }

    public CustomerNewModel() {
    }

    private CustomerNewModel(Parcel in) {
        this.Matchcode = in.readString();
        this.Name1 = in.readString();
        this.Name2 = in.readString();
        this.Name3 = in.readString();
        this.Strasse = in.readString();
        this.PLZ = in.readString();
        this.Ort = in.readString();
        this.Land = in.readString();
        this.Telefon = in.readString();
        this.Telefax = in.readString();
        this.EMail = in.readString();
        this.WWW = in.readString();
        this.UstidNr = in.readString();
        this.Mitarbeiter = in.readString();
        this.Rechtsform = in.readString();
        this.Inhaber = in.readString();
        this.JBestNr = in.readString();
        this.Umsatzpotenzial = in.readString();
        this.ErfMitarbeiter = in.readString();
        this.Branche = in.readString();
        this.AenderungMitarbeiter = in.readString();
        this.UserID = in.readString();
    }

    public static final Creator<CustomerNewModel> CREATOR = new Creator<CustomerNewModel>() {
        public CustomerNewModel createFromParcel(Parcel source) {
            return new CustomerNewModel(source);
        }

        public CustomerNewModel[] newArray(int size) {
            return new CustomerNewModel[size];
        }
    };
}
