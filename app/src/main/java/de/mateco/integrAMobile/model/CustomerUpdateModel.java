package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerUpdateModel implements Parcelable {
    private String MATCHCODE, NAME1, NAME2, NAME3, Strasse,PLZ, Ort, Land, Telefon, Telefax, EMAil, www, Rechtsform,
            Inhaber, USTIDNR, JbestNr, AenderungMitarbeiter, Umsatzpotenzial, Kontakt, Branche;

    public String getMATCHCODE() {
        return MATCHCODE;
    }

    public void setMATCHCODE(String MATCHCODE) {
        this.MATCHCODE = MATCHCODE;
    }

    public String getNAME1() {
        return NAME1;
    }

    public void setNAME1(String NAME1) {
        this.NAME1 = NAME1;
    }

    public String getNAME2() {
        return NAME2;
    }

    public void setNAME2(String NAME2) {
        this.NAME2 = NAME2;
    }

    public String getNAME3() {
        return NAME3;
    }

    public void setNAME3(String NAME3) {
        this.NAME3 = NAME3;
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

    public String getEMAil() {
        return EMAil;
    }

    public void setEMAil(String EMAil) {
        this.EMAil = EMAil;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
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

    public String getUSTIDNR() {
        return USTIDNR;
    }

    public void setUSTIDNR(String USTIDNR) {
        this.USTIDNR = USTIDNR;
    }

    public String getJbestNr() {
        return JbestNr;
    }

    public void setJbestNr(String jbestNr) {
        JbestNr = jbestNr;
    }

    public String getAenderungMitarbeiter() {
        return AenderungMitarbeiter;
    }

    public void setAenderungMitarbeiter(String aenderungMitarbeiter) {
        AenderungMitarbeiter = aenderungMitarbeiter;
    }

    public String getUmsatzpotenzial() {
        return Umsatzpotenzial;
    }

    public void setUmsatzpotenzial(String umsatzpotenzial) {
        Umsatzpotenzial = umsatzpotenzial;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getBranche() {
        return Branche;
    }

    public void setBranche(String branche) {
        Branche = branche;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.MATCHCODE);
        dest.writeString(this.NAME1);
        dest.writeString(this.NAME2);
        dest.writeString(this.NAME3);
        dest.writeString(this.Strasse);
        dest.writeString(this.PLZ);
        dest.writeString(this.Ort);
        dest.writeString(this.Land);
        dest.writeString(this.Telefon);
        dest.writeString(this.Telefax);
        dest.writeString(this.EMAil);
        dest.writeString(this.www);
        dest.writeString(this.Rechtsform);
        dest.writeString(this.Inhaber);
        dest.writeString(this.USTIDNR);
        dest.writeString(this.JbestNr);
        dest.writeString(this.AenderungMitarbeiter);
        dest.writeString(this.Umsatzpotenzial);
        dest.writeString(this.Kontakt);
        dest.writeString(this.Branche);
    }

    public CustomerUpdateModel() {
    }

    private CustomerUpdateModel(Parcel in) {
        this.MATCHCODE = in.readString();
        this.NAME1 = in.readString();
        this.NAME2 = in.readString();
        this.NAME3 = in.readString();
        this.Strasse = in.readString();
        this.PLZ = in.readString();
        this.Ort = in.readString();
        this.Land = in.readString();
        this.Telefon = in.readString();
        this.Telefax = in.readString();
        this.EMAil = in.readString();
        this.www = in.readString();
        this.Rechtsform = in.readString();
        this.Inhaber = in.readString();
        this.USTIDNR = in.readString();
        this.JbestNr = in.readString();
        this.AenderungMitarbeiter = in.readString();
        this.Umsatzpotenzial = in.readString();
        this.Kontakt = in.readString();
        this.Branche = in.readString();
    }

    public static final Creator<CustomerUpdateModel> CREATOR = new Creator<CustomerUpdateModel>() {
        public CustomerUpdateModel createFromParcel(Parcel source) {
            return new CustomerUpdateModel(source);
        }

        public CustomerUpdateModel[] newArray(int size) {
            return new CustomerUpdateModel[size];
        }
    };
}
