package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CustomerActivityUpdateModel implements Parcelable {
    private String Aktivitaet, Kontakt, Objekt, Angebot, Akttyp, Aktthema, Notiz, Startdatum, Startzeit, Endzeit, realisiert, Fest, Geloescht, AenderungMitarbeiter;

    private ArrayList<String> Mitarbeiter;

    private ArrayList<String> Anspartner;

    public String getAktivitaet() {
        return Aktivitaet;
    }

    public void setAktivitaet(String aktivitaet) {
        Aktivitaet = aktivitaet;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getObjekt() {
        return Objekt;
    }

    public void setObjekt(String objekt) {
        Objekt = objekt;
    }

    public String getAngebot() {
        return Angebot;
    }

    public void setAngebot(String angebot) {
        Angebot = angebot;
    }

    public String getAkttyp() {
        return Akttyp;
    }

    public void setAkttyp(String akttyp) {
        Akttyp = akttyp;
    }

    public String getAktthema() {
        return Aktthema;
    }

    public void setAktthema(String aktthema) {
        Aktthema = aktthema;
    }

    public String getNotiz() {
        return Notiz;
    }

    public void setNotiz(String notiz) {
        Notiz = notiz;
    }

    public String getStartdatum() {
        return Startdatum;
    }

    public void setStartdatum(String startdatum) {
        Startdatum = startdatum;
    }

    public String getStartzeit() {
        return Startzeit;
    }

    public void setStartzeit(String startzeit) {
        Startzeit = startzeit;
    }

    public String getEndzeit() {
        return Endzeit;
    }

    public void setEndzeit(String endzeit) {
        Endzeit = endzeit;
    }

    public String getRealisiert() {
        return realisiert;
    }

    public void setRealisiert(String realisiert) {
        this.realisiert = realisiert;
    }

    public String getFest() {
        return Fest;
    }

    public void setFest(String fest) {
        Fest = fest;
    }

    public String getGeloescht() {
        return Geloescht;
    }

    public void setGeloescht(String geloescht) {
        Geloescht = geloescht;
    }

    public String getAenderungMitarbeiter() {
        return AenderungMitarbeiter;
    }

    public void setAenderungMitarbeiter(String aenderungMitarbeiter) {
        AenderungMitarbeiter = aenderungMitarbeiter;
    }

    public ArrayList<String> getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(ArrayList<String> mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }

    public ArrayList<String> getAnspartner() {
        return Anspartner;
    }

    public void setAnspartner(ArrayList<String> anspartner) {
        Anspartner = anspartner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Aktivitaet);
        dest.writeString(this.Kontakt);
        dest.writeString(this.Objekt);
        dest.writeString(this.Angebot);
        dest.writeString(this.Akttyp);
        dest.writeString(this.Aktthema);
        dest.writeString(this.Notiz);
        dest.writeString(this.Startdatum);
        dest.writeString(this.Startzeit);
        dest.writeString(this.Endzeit);
        dest.writeString(this.realisiert);
        dest.writeString(this.Fest);
        dest.writeString(this.Geloescht);
        dest.writeString(this.AenderungMitarbeiter);
        dest.writeSerializable(this.Mitarbeiter);
        dest.writeSerializable(this.Anspartner);
    }

    public CustomerActivityUpdateModel() {
    }

    private CustomerActivityUpdateModel(Parcel in) {
        this.Aktivitaet = in.readString();
        this.Kontakt = in.readString();
        this.Objekt = in.readString();
        this.Angebot = in.readString();
        this.Akttyp = in.readString();
        this.Aktthema = in.readString();
        this.Notiz = in.readString();
        this.Startdatum = in.readString();
        this.Startzeit = in.readString();
        this.Endzeit = in.readString();
        this.realisiert = in.readString();
        this.Fest = in.readString();
        this.Geloescht = in.readString();
        this.AenderungMitarbeiter = in.readString();
        this.Mitarbeiter = (ArrayList<String>) in.readSerializable();
        this.Anspartner = (ArrayList<String>) in.readSerializable();
    }

    public static final Creator<CustomerActivityUpdateModel> CREATOR = new Creator<CustomerActivityUpdateModel>() {
        public CustomerActivityUpdateModel createFromParcel(Parcel source) {
            return new CustomerActivityUpdateModel(source);
        }

        public CustomerActivityUpdateModel[] newArray(int size) {
            return new CustomerActivityUpdateModel[size];
        }
    };
}
