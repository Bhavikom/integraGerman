package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SiteInspectionOperationalDataPermitsModel implements Parcelable {
    private int Eigentuemer, Kommune, OEPNV, Taxiverband, Absperrmass, Skizze, Verkehrzeichenplan, Halteverbotsschilder,
            VerlegungBushaltestelle, VerlegungTaxi, VerlegungBehi;

    private String EingetuemerName, EigentuemerAnsPartner, EigentuemerTelefon, EigentuemerTelefax, Angefordert, Hinweis, Richtung1,Richtung2,
            Fahrbahnbreite1, Fahrbahnbreite2, Gehwegbreite1, Gehwegbreite2, Radwegbreite1, Radwegbreite2,FBBauzeit1, FBBauzeit2, GWBauzeit1, GWBauzeit2, RWBauzeit1, RWBauzeit2,
            FahrstreifenAnzahl1, FahrstreifenBauzeit1, FahrstreifenAnzahl2, FahrstreifenBauzeit2, Regelplaene;

    public String getRegelplaene() {
        return Regelplaene;
    }

    public void setRegelplaene(String regelplaene) {
        Regelplaene = regelplaene;
    }

    public int getEigentuemer() {
        return Eigentuemer;
    }

    public void setEigentuemer(int eigentuemer) {
        Eigentuemer = eigentuemer;
    }

    public int getKommune() {
        return Kommune;
    }

    public void setKommune(int kommune) {
        Kommune = kommune;
    }

    public int getOEPNV() {
        return OEPNV;
    }

    public void setOEPNV(int OEPNV) {
        this.OEPNV = OEPNV;
    }

    public int getTaxiverband() {
        return Taxiverband;
    }

    public void setTaxiverband(int taxiverband) {
        Taxiverband = taxiverband;
    }

    public int getAbsperrmass() {
        return Absperrmass;
    }

    public void setAbsperrmass(int absperrmass) {
        Absperrmass = absperrmass;
    }

    public int getSkizze() {
        return Skizze;
    }

    public void setSkizze(int skizze) {
        Skizze = skizze;
    }

    public int getVerkehrzeichenplan() {
        return Verkehrzeichenplan;
    }

    public void setVerkehrzeichenplan(int verkehrzeichenplan) {
        Verkehrzeichenplan = verkehrzeichenplan;
    }

    public int getHalteverbotsschilder() {
        return Halteverbotsschilder;
    }

    public void setHalteverbotsschilder(int halteverbotsschilder) {
        Halteverbotsschilder = halteverbotsschilder;
    }

    public int getVerlegungBushaltestelle() {
        return VerlegungBushaltestelle;
    }

    public void setVerlegungBushaltestelle(int verlegungBushaltestelle) {
        VerlegungBushaltestelle = verlegungBushaltestelle;
    }

    public int getVerlegungTaxi() {
        return VerlegungTaxi;
    }

    public void setVerlegungTaxi(int verlegungTaxi) {
        VerlegungTaxi = verlegungTaxi;
    }

    public int getVerlegungBehi() {
        return VerlegungBehi;
    }

    public void setVerlegungBehi(int verlegungBehi) {
        VerlegungBehi = verlegungBehi;
    }

    public String getEingetuemerName() {
        return EingetuemerName;
    }

    public void setEingetuemerName(String eingetuemerName) {
        EingetuemerName = eingetuemerName;
    }

    public String getEigentuemerAnsPartner() {
        return EigentuemerAnsPartner;
    }

    public void setEigentuemerAnsPartner(String eigentuemerAnsPartner) {
        EigentuemerAnsPartner = eigentuemerAnsPartner;
    }

    public String getEigentuemerTelefon() {
        return EigentuemerTelefon;
    }

    public void setEigentuemerTelefon(String eigentuemerTelefon) {
        EigentuemerTelefon = eigentuemerTelefon;
    }

    public String getEigentuemerTelefax() {
        return EigentuemerTelefax;
    }

    public void setEigentuemerTelefax(String eigentuemerTelefax) {
        EigentuemerTelefax = eigentuemerTelefax;
    }

    public String getAngefordert() {
        return Angefordert;
    }

    public void setAngefordert(String angefordert) {
        Angefordert = angefordert;
    }

    public String getHinweis() {
        return Hinweis;
    }

    public void setHinweis(String hinweis) {
        Hinweis = hinweis;
    }

    public String getRichtung1() {
        return Richtung1;
    }

    public void setRichtung1(String richtung1) {
        Richtung1 = richtung1;
    }

    public String getRichtung2() {
        return Richtung2;
    }

    public void setRichtung2(String richtung2) {
        Richtung2 = richtung2;
    }

    public String getFahrbahnbreite1() {
        return Fahrbahnbreite1;
    }

    public void setFahrbahnbreite1(String fahrbahnbreite1) {
        Fahrbahnbreite1 = fahrbahnbreite1;
    }

    public String getFahrbahnbreite2() {
        return Fahrbahnbreite2;
    }

    public void setFahrbahnbreite2(String fahrbahnbreite2) {
        Fahrbahnbreite2 = fahrbahnbreite2;
    }

    public String getGehwegbreite1() {
        return Gehwegbreite1;
    }

    public void setGehwegbreite1(String gehwegbreite1) {
        Gehwegbreite1 = gehwegbreite1;
    }

    public String getGehwegbreite2() {
        return Gehwegbreite2;
    }

    public void setGehwegbreite2(String gehwegbreite2) {
        Gehwegbreite2 = gehwegbreite2;
    }

    public String getRadwegbreite1() {
        return Radwegbreite1;
    }

    public void setRadwegbreite1(String radwegbreite1) {
        Radwegbreite1 = radwegbreite1;
    }

    public String getRadwegbreite2() {
        return Radwegbreite2;
    }

    public void setRadwegbreite2(String radwegbreite2) {
        Radwegbreite2 = radwegbreite2;
    }

    public String getFahrstreifenAnzahl1() {
        return FahrstreifenAnzahl1;
    }

    public void setFahrstreifenAnzahl1(String fahrstreifenAnzahl1) {
        FahrstreifenAnzahl1 = fahrstreifenAnzahl1;
    }

    public String getFahrstreifenBauzeit1() {
        return FahrstreifenBauzeit1;
    }

    public void setFahrstreifenBauzeit1(String fahrstreifenBauzeit1) {
        FahrstreifenBauzeit1 = fahrstreifenBauzeit1;
    }

    public String getFahrstreifenAnzahl2() {
        return FahrstreifenAnzahl2;
    }

    public void setFahrstreifenAnzahl2(String fahrstreifenAnzahl2) {
        FahrstreifenAnzahl2 = fahrstreifenAnzahl2;
    }

    public String getFahrstreifenBauzeit2() {
        return FahrstreifenBauzeit2;
    }

    public void setFahrstreifenBauzeit2(String fahrstreifenBauzeit2) {
        FahrstreifenBauzeit2 = fahrstreifenBauzeit2;
    }

    public String getFBBauzeit1() {
        return FBBauzeit1;
    }

    public void setFBBauzeit1(String FBBauzeit1) {
        this.FBBauzeit1 = FBBauzeit1;
    }

    public String getFBBauzeit2() {
        return FBBauzeit2;
    }

    public void setFBBauzeit2(String FBBauzeit2) {
        this.FBBauzeit2 = FBBauzeit2;
    }

    public String getGWBauzeit1() {
        return GWBauzeit1;
    }

    public void setGWBauzeit1(String GWBauzeit1) {
        this.GWBauzeit1 = GWBauzeit1;
    }

    public String getGWBauzeit2() {
        return GWBauzeit2;
    }

    public void setGWBauzeit2(String GWBauzeit2) {
        this.GWBauzeit2 = GWBauzeit2;
    }

    public String getRWBauzeit1() {
        return RWBauzeit1;
    }

    public void setRWBauzeit1(String RWBauzeit1) {
        this.RWBauzeit1 = RWBauzeit1;
    }

    public String getRWBauzeit2() {
        return RWBauzeit2;
    }

    public void setRWBauzeit2(String RWBauzeit2) {
        this.RWBauzeit2 = RWBauzeit2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Eigentuemer);
        dest.writeInt(this.Kommune);
        dest.writeInt(this.OEPNV);
        dest.writeInt(this.Taxiverband);
        dest.writeInt(this.Absperrmass);
        dest.writeInt(this.Skizze);
        dest.writeInt(this.Verkehrzeichenplan);
        dest.writeInt(this.Halteverbotsschilder);
        dest.writeInt(this.VerlegungBushaltestelle);
        dest.writeInt(this.VerlegungTaxi);
        dest.writeInt(this.VerlegungBehi);
        dest.writeString(this.EingetuemerName);
        dest.writeString(this.EigentuemerAnsPartner);
        dest.writeString(this.EigentuemerTelefon);
        dest.writeString(this.EigentuemerTelefax);
        dest.writeString(this.Angefordert);
        dest.writeString(this.Hinweis);
        dest.writeString(this.Richtung1);
        dest.writeString(this.Richtung2);
        dest.writeString(this.Fahrbahnbreite1);
        dest.writeString(this.Fahrbahnbreite2);
        dest.writeString(this.Gehwegbreite1);
        dest.writeString(this.Gehwegbreite2);
        dest.writeString(this.Radwegbreite1);
        dest.writeString(this.Radwegbreite2);
        dest.writeString(this.FahrstreifenAnzahl1);
        dest.writeString(this.FahrstreifenBauzeit1);
        dest.writeString(this.FahrstreifenAnzahl2);
        dest.writeString(this.FahrstreifenBauzeit2);
        dest.writeString(this.FBBauzeit1);
        dest.writeString(this.FBBauzeit2);
        dest.writeString(this.GWBauzeit1);
        dest.writeString(this.GWBauzeit2);
        dest.writeString(this.RWBauzeit1);
        dest.writeString(this.RWBauzeit2);
    }

    public SiteInspectionOperationalDataPermitsModel() {
    }

    private SiteInspectionOperationalDataPermitsModel(Parcel in) {
        this.Eigentuemer = in.readInt();
        this.Kommune = in.readInt();
        this.OEPNV = in.readInt();
        this.Taxiverband = in.readInt();
        this.Absperrmass = in.readInt();
        this.Skizze = in.readInt();
        this.Verkehrzeichenplan = in.readInt();
        this.Halteverbotsschilder = in.readInt();
        this.VerlegungBushaltestelle = in.readInt();
        this.VerlegungTaxi = in.readInt();
        this.VerlegungBehi = in.readInt();
        this.EingetuemerName = in.readString();
        this.EigentuemerAnsPartner = in.readString();
        this.EigentuemerTelefon = in.readString();
        this.EigentuemerTelefax = in.readString();
        this.Angefordert = in.readString();
        this.Hinweis = in.readString();
        this.Richtung1 = in.readString();
        this.Richtung2 = in.readString();
        this.Fahrbahnbreite1 = in.readString();
        this.Fahrbahnbreite2 = in.readString();
        this.Gehwegbreite1 = in.readString();
        this.Gehwegbreite2 = in.readString();
        this.Radwegbreite1 = in.readString();
        this.Radwegbreite2 = in.readString();
        this.FahrstreifenAnzahl1 = in.readString();
        this.FahrstreifenBauzeit1 = in.readString();
        this.FahrstreifenAnzahl2 = in.readString();
        this.FahrstreifenBauzeit2 = in.readString();
        this.FBBauzeit1 = in.readString();
        this.FBBauzeit2 = in.readString();
        this.GWBauzeit1 = in.readString();
        this.GWBauzeit2 = in.readString();
        this.RWBauzeit1 = in.readString();
        this.RWBauzeit2 = in.readString();
    }

    public static final Creator<SiteInspectionOperationalDataPermitsModel> CREATOR = new Creator<SiteInspectionOperationalDataPermitsModel>() {
        public SiteInspectionOperationalDataPermitsModel createFromParcel(Parcel source) {
            return new SiteInspectionOperationalDataPermitsModel(source);
        }

        public SiteInspectionOperationalDataPermitsModel[] newArray(int size) {
            return new SiteInspectionOperationalDataPermitsModel[size];
        }
    };
}