package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SiteInspectionAdditionalMobileWindPowerModel implements Parcelable {
    private int Stomerdung, Spannbaender, Kabelverlegung, Grundwerksplan, Mastabstand, WKAVerbolzung, AbschaltungRichtfunk,
            AbschaltungStrommast, AbschaltungWindkraft;
    private String ID, Netzbetreiber, Antennen_1, Antennen_2, Antennen_3, GewichtStahlbau, WKANr, WKATyp, RichtfunkAnsp, RichtfunkTelefon,
            StrommastAnsp, StrommastTelefon, WindkraftAnsp, WindkraftTelefon, Arbeit;

    public int getStomerdung() {
        return Stomerdung;
    }

    public void setStomerdung(int stomerdung) {
        Stomerdung = stomerdung;
    }

    public int getSpannbaender() {
        return Spannbaender;
    }

    public void setSpannbaender(int spannbaender) {
        Spannbaender = spannbaender;
    }

    public int getKabelverlegung() {
        return Kabelverlegung;
    }

    public void setKabelverlegung(int kabelverlegung) {
        Kabelverlegung = kabelverlegung;
    }

    public int getGrundwerksplan() {
        return Grundwerksplan;
    }

    public void setGrundwerksplan(int grundwerksplan) {
        Grundwerksplan = grundwerksplan;
    }

    public int getMastabstand() {
        return Mastabstand;
    }

    public void setMastabstand(int mastabstand) {
        Mastabstand = mastabstand;
    }

    public int getWKAVerbolzung() {
        return WKAVerbolzung;
    }

    public void setWKAVerbolzung(int WKAVerbolzung) {
        this.WKAVerbolzung = WKAVerbolzung;
    }

    public int getAbschaltungRichtfunk() {
        return AbschaltungRichtfunk;
    }

    public void setAbschaltungRichtfunk(int abschaltungRichtfunk) {
        AbschaltungRichtfunk = abschaltungRichtfunk;
    }

    public int getAbschaltungStrommast() {
        return AbschaltungStrommast;
    }

    public void setAbschaltungStrommast(int abschaltungStrommast) {
        AbschaltungStrommast = abschaltungStrommast;
    }

    public int getAbschaltungWindkraft() {
        return AbschaltungWindkraft;
    }

    public void setAbschaltungWindkraft(int abschaltungWindkraft) {
        AbschaltungWindkraft = abschaltungWindkraft;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNetzbetreiber() {
        return Netzbetreiber;
    }

    public void setNetzbetreiber(String netzbetreiber) {
        Netzbetreiber = netzbetreiber;
    }

    public String getAntennen_1() {
        return Antennen_1;
    }

    public void setAntennen_1(String antennen_1) {
        Antennen_1 = antennen_1;
    }

    public String getAntennen_2() {
        return Antennen_2;
    }

    public void setAntennen_2(String antennen_2) {
        Antennen_2 = antennen_2;
    }

    public String getAntennen_3() {
        return Antennen_3;
    }

    public void setAntennen_3(String antennen_3) {
        Antennen_3 = antennen_3;
    }

    public String getGewichtStahlbau() {
        return GewichtStahlbau;
    }

    public void setGewichtStahlbau(String gewichtStahlbau) {
        GewichtStahlbau = gewichtStahlbau;
    }

    public String getWKANr() {
        return WKANr;
    }

    public void setWKANr(String WKANr) {
        this.WKANr = WKANr;
    }

    public String getWKATyp() {
        return WKATyp;
    }

    public void setWKATyp(String WKATyp) {
        this.WKATyp = WKATyp;
    }

    public String getRichtfunkAnsp() {
        return RichtfunkAnsp;
    }

    public void setRichtfunkAnsp(String richtfunkAnsp) {
        RichtfunkAnsp = richtfunkAnsp;
    }

    public String getRichtfunkTelefon() {
        return RichtfunkTelefon;
    }

    public void setRichtfunkTelefon(String richtfunkTelefon) {
        RichtfunkTelefon = richtfunkTelefon;
    }

    public String getStrommastAnsp() {
        return StrommastAnsp;
    }

    public void setStrommastAnsp(String strommastAnsp) {
        StrommastAnsp = strommastAnsp;
    }

    public String getStrommastTelefon() {
        return StrommastTelefon;
    }

    public void setStrommastTelefon(String strommastTelefon) {
        StrommastTelefon = strommastTelefon;
    }

    public String getWindkraftAnsp() {
        return WindkraftAnsp;
    }

    public void setWindkraftAnsp(String windkraftAnsp) {
        WindkraftAnsp = windkraftAnsp;
    }

    public String getWindkraftTelefon() {
        return WindkraftTelefon;
    }

    public void setWindkraftTelefon(String windkraftTelefon) {
        WindkraftTelefon = windkraftTelefon;
    }

    public String getArbeit() {
        return Arbeit;
    }

    public void setArbeit(String arbeit) {
        Arbeit = arbeit;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Stomerdung);
        dest.writeInt(this.Spannbaender);
        dest.writeInt(this.Kabelverlegung);
        dest.writeInt(this.Grundwerksplan);
        dest.writeInt(this.Mastabstand);
        dest.writeInt(this.WKAVerbolzung);
        dest.writeInt(this.AbschaltungRichtfunk);
        dest.writeInt(this.AbschaltungStrommast);
        dest.writeInt(this.AbschaltungWindkraft);
        dest.writeString(this.ID);
        dest.writeString(this.Netzbetreiber);
        dest.writeString(this.Antennen_1);
        dest.writeString(this.Antennen_2);
        dest.writeString(this.Antennen_3);
        dest.writeString(this.GewichtStahlbau);
        dest.writeString(this.WKANr);
        dest.writeString(this.WKATyp);
        dest.writeString(this.RichtfunkAnsp);
        dest.writeString(this.RichtfunkTelefon);
        dest.writeString(this.StrommastAnsp);
        dest.writeString(this.StrommastTelefon);
        dest.writeString(this.WindkraftAnsp);
        dest.writeString(this.WindkraftTelefon);
        dest.writeString(this.Arbeit);
    }

    public SiteInspectionAdditionalMobileWindPowerModel() {
    }

    private SiteInspectionAdditionalMobileWindPowerModel(Parcel in) {
        this.Stomerdung = in.readInt();
        this.Spannbaender = in.readInt();
        this.Kabelverlegung = in.readInt();
        this.Grundwerksplan = in.readInt();
        this.Mastabstand = in.readInt();
        this.WKAVerbolzung = in.readInt();
        this.AbschaltungRichtfunk = in.readInt();
        this.AbschaltungStrommast = in.readInt();
        this.AbschaltungWindkraft = in.readInt();
        this.ID = in.readString();
        this.Netzbetreiber = in.readString();
        this.Antennen_1 = in.readString();
        this.Antennen_2 = in.readString();
        this.Antennen_3 = in.readString();
        this.GewichtStahlbau = in.readString();
        this.WKANr = in.readString();
        this.WKATyp = in.readString();
        this.RichtfunkAnsp = in.readString();
        this.RichtfunkTelefon = in.readString();
        this.StrommastAnsp = in.readString();
        this.StrommastTelefon = in.readString();
        this.WindkraftAnsp = in.readString();
        this.WindkraftTelefon = in.readString();
        this.Arbeit = in.readString();
    }

    public static final Creator<SiteInspectionAdditionalMobileWindPowerModel> CREATOR = new Creator<SiteInspectionAdditionalMobileWindPowerModel>() {
        public SiteInspectionAdditionalMobileWindPowerModel createFromParcel(Parcel source) {
            return new SiteInspectionAdditionalMobileWindPowerModel(source);
        }

        public SiteInspectionAdditionalMobileWindPowerModel[] newArray(int size) {
            return new SiteInspectionAdditionalMobileWindPowerModel[size];
        }
    };
}
