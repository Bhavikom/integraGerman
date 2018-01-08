package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SiteInspectionNewModel implements Parcelable {
    private String Kennung;
    private String Kontakt;
    private String MatchCode;
    private String KontaktStrasse;
    private String KontaktPLZ;
    private String KontaktOrt;
    private String KundeName;
    private String Besteller,BestellerTelefon,BestellerEMail;
    private String AVO,AVOName,AVOTelefon,AVOEMail;
    private String Einsatzstrasse,EinsatzPLZ,Einsatzort,Einsatzdatum,Einsatzdauer;
    private String Bauvorhaben,BauvorhabenText,Zugang;
    private int Anmeldung;

    public String getGeoLaengeFormate() {
        return GeoLaengeFormate;
    }

    public void setGeoLaengeFormate(String geoLaengeFormate) {
        GeoLaengeFormate = geoLaengeFormate;
    }

    public String getGeoBreiteFormate() {
        return GeoBreiteFormate;
    }

    public void setGeoBreiteFormate(String geoBreiteFormate) {
        GeoBreiteFormate = geoBreiteFormate;
    }

    private String Arbeit,Knickpunkt,Stoerkante;
    private String Notiz;
    private String GeoLaenge,GeoBreite,GeoLaengeFormate,GeoBreiteFormate;
    private String Mandant;
    private String Ersteller;


    public String getMandant() {
        return Mandant;
    }

    public void setMandant(String mandant) {
        Mandant = mandant;
    }

    public String getErsteller() {
        return Ersteller;
    }

    public void setErsteller(String ersteller) {
        Ersteller = ersteller;
    }

    public String getGeoLaenge() {
        return GeoLaenge;
    }

    public void setGeoLaenge(String geoLaenge) {
        GeoLaenge = geoLaenge;
    }

    public String getGeoBreite() {
        return GeoBreite;
    }

    public void setGeoBreite(String geoBreite) {
        GeoBreite = geoBreite;
    }
    public String getKundeName() {
        return KundeName;
    }

    public void setKundeName(String kundeName) {
        KundeName = kundeName;
    }

    public String getEinsatzstrasse() {
        return Einsatzstrasse;
    }

    public void setEinsatzstrasse(String einsatzstrasse) {
        Einsatzstrasse = einsatzstrasse;
    }

    public String getKennung() {
        return Kennung;
    }

    public void setKennung(String kennung) {
        Kennung = kennung;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getMatchCode() {
        return MatchCode;
    }

    public void setMatchCode(String matchCode) {
        MatchCode = matchCode;
    }

    public String getKontaktStrasse() {
        return KontaktStrasse;
    }

    public void setKontaktStrasse(String kontaktStrasse) {
        KontaktStrasse = kontaktStrasse;
    }

    public String getKontaktPLZ() {
        return KontaktPLZ;
    }

    public void setKontaktPLZ(String kontaktPLZ) {
        KontaktPLZ = kontaktPLZ;
    }

    public String getKontaktOrt() {
        return KontaktOrt;
    }

    public void setKontaktOrt(String kontaktOrt) {
        KontaktOrt = kontaktOrt;
    }

    public String getBesteller() {
        return Besteller;
    }

    public void setBesteller(String besteller) {
        Besteller = besteller;
    }

    public String getBestellerTelefon() {
        return BestellerTelefon;
    }

    public void setBestellerTelefon(String bestellerTelefon) {
        BestellerTelefon = bestellerTelefon;
    }

    public String getBestellerEMail() {
        return BestellerEMail;
    }

    public void setBestellerEMail(String bestellerEMail) {
        BestellerEMail = bestellerEMail;
    }

    public String getAVO() {
        return AVO;
    }

    public void setAVO(String AVO) {
        this.AVO = AVO;
    }

    public String getAVOName() {
        return AVOName;
    }

    public void setAVOName(String AVOName) {
        this.AVOName = AVOName;
    }

    public String getAVOTelefon() {
        return AVOTelefon;
    }

    public void setAVOTelefon(String AVOTelefon) {
        this.AVOTelefon = AVOTelefon;
    }

    public String getAVOEMail() {
        return AVOEMail;
    }

    public void setAVOEMail(String AVOEMail) {
        this.AVOEMail = AVOEMail;
    }

    public String getEinsatzPLZ() {
        return EinsatzPLZ;
    }

    public void setEinsatzPLZ(String einsatzPLZ) {
        EinsatzPLZ = einsatzPLZ;
    }

    public String getEinsatzort() {
        return Einsatzort;
    }

    public void setEinsatzort(String einsatzort) {
        Einsatzort = einsatzort;
    }

    public String getEinsatzdatum() {
        return Einsatzdatum;
    }

    public void setEinsatzdatum(String einsatzdatum) {
        Einsatzdatum = einsatzdatum;
    }

    public String getEinsatzdauer() {
        return Einsatzdauer;
    }

    public void setEinsatzdauer(String einsatzdauer) {
        Einsatzdauer = einsatzdauer;
    }

    public String getBauvorhaben() {
        return Bauvorhaben;
    }

    public void setBauvorhaben(String bauvorhaben) {
        Bauvorhaben = bauvorhaben;
    }

    public String getBauvorhabenText() {
        return BauvorhabenText;
    }

    public void setBauvorhabenText(String bauvorhabenText) {
        BauvorhabenText = bauvorhabenText;
    }

    public String getZugang() {
        return Zugang;
    }

    public void setZugang(String zugang) {
        Zugang = zugang;
    }

    public int getAnmeldung() {
        return Anmeldung;
    }

    public void setAnmeldung(int anmeldung) {
        Anmeldung = anmeldung;
    }

    public String getArbeit() {
        return Arbeit;
    }

    public void setArbeit(String arbeit) {
        Arbeit = arbeit;
    }

    public String getKnickpunkt() {
        return Knickpunkt;
    }

    public void setKnickpunkt(String knickpunkt) {
        Knickpunkt = knickpunkt;
    }

    public String getStoerkante() {
        return Stoerkante;
    }

    public void setStoerkante(String stoerkante) {
        Stoerkante = stoerkante;
    }

    public String getNotiz() {
        return Notiz;
    }

    public void setNotiz(String notiz) {
        Notiz = notiz;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Kennung);
        dest.writeString(this.Kontakt);
        dest.writeString(this.MatchCode);
        dest.writeString(this.KontaktStrasse);
        dest.writeString(this.KontaktPLZ);
        dest.writeString(this.KontaktOrt);
        dest.writeString(this.KundeName);
        dest.writeString(this.Besteller);
        dest.writeString(this.BestellerTelefon);
        dest.writeString(this.BestellerEMail);
        dest.writeString(this.AVO);
        dest.writeString(this.AVOName);
        dest.writeString(this.AVOTelefon);
        dest.writeString(this.AVOEMail);
        dest.writeString(this.Einsatzstrasse);
        dest.writeString(this.EinsatzPLZ);
        dest.writeString(this.Einsatzort);
        dest.writeString(this.Einsatzdatum);
        dest.writeString(this.Einsatzdauer);
        dest.writeString(this.Bauvorhaben);
        dest.writeString(this.BauvorhabenText);
        dest.writeString(this.Zugang);
        dest.writeInt(this.Anmeldung);
        dest.writeString(this.Arbeit);
        dest.writeString(this.Knickpunkt);
        dest.writeString(this.Stoerkante);
        dest.writeString(this.Notiz);
        dest.writeString(this.GeoBreite);
        dest.writeString(this.GeoLaenge);
        dest.writeString(this.GeoLaengeFormate);
        dest.writeString(this.GeoBreiteFormate);
    }

    public SiteInspectionNewModel() {
    }

    private SiteInspectionNewModel(Parcel in) {
        this.Kennung = in.readString();
        this.Kontakt = in.readString();
        this.MatchCode = in.readString();
        this.KontaktStrasse = in.readString();
        this.KontaktPLZ = in.readString();
        this.KontaktOrt = in.readString();
        this.KundeName = in.readString();
        this.Besteller = in.readString();
        this.BestellerTelefon = in.readString();
        this.BestellerEMail = in.readString();
        this.AVO = in.readString();
        this.AVOName = in.readString();
        this.AVOTelefon = in.readString();
        this.AVOEMail = in.readString();
        this.Einsatzstrasse = in.readString();
        this.EinsatzPLZ = in.readString();
        this.Einsatzort = in.readString();
        this.Einsatzdatum = in.readString();
        this.Einsatzdauer = in.readString();
        this.Bauvorhaben = in.readString();
        this.BauvorhabenText = in.readString();
        this.Zugang = in.readString();
        this.Anmeldung = in.readInt();
        this.Arbeit = in.readString();
        this.Knickpunkt = in.readString();
        this.Stoerkante = in.readString();
        this.Notiz = in.readString();
        this.GeoBreite = in.readString();
        this.GeoLaenge = in.readString();
        this.GeoBreiteFormate = in.readString();
        this.GeoLaengeFormate = in.readString();
    }

    public static final Creator<SiteInspectionNewModel> CREATOR = new Creator<SiteInspectionNewModel>() {
        public SiteInspectionNewModel createFromParcel(Parcel source) {
            return new SiteInspectionNewModel(source);
        }

        public SiteInspectionNewModel[] newArray(int size) {
            return new SiteInspectionNewModel[size];
        }
    };
}
