package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EmployeeModel implements Parcelable
{
    private String Aktiv, Anrede, Ausgeschieden, Benutzername, EMail, Funktion, Gebiet, Geschuetzt, Hierarchie, Mitarbeiter, Mobil, NL,
            Nachname, Ort, Personalnummer, RowID, SVNummer, Telefax, Telefon, Vorgesetzter, Vorname, Zeichnung;

    public String getAktiv() {
        return Aktiv;
    }

    public void setAktiv(String aktiv) {
        Aktiv = aktiv;
    }

    public String getAnrede() {
        return Anrede;
    }

    public void setAnrede(String anrede) {
        Anrede = anrede;
    }

    public String getAusgeschieden() {
        return Ausgeschieden;
    }

    public void setAusgeschieden(String ausgeschieden) {
        Ausgeschieden = ausgeschieden;
    }

    public String getBenutzername() {
        return Benutzername;
    }

    public void setBenutzername(String benutzername) {
        Benutzername = benutzername;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getFunktion() {
        return Funktion;
    }

    public void setFunktion(String funktion) {
        Funktion = funktion;
    }

    public String getGebiet() {
        return Gebiet;
    }

    public void setGebiet(String gebiet) {
        Gebiet = gebiet;
    }

    public String getGeschuetzt() {
        return Geschuetzt;
    }

    public void setGeschuetzt(String geschuetzt) {
        Geschuetzt = geschuetzt;
    }

    public String getHierarchie() {
        return Hierarchie;
    }

    public void setHierarchie(String hierarchie) {
        Hierarchie = hierarchie;
    }

    public String getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }

    public String getMobil() {
        return Mobil;
    }

    public void setMobil(String mobil) {
        Mobil = mobil;
    }

    public String getNL() {
        return NL;
    }

    public void setNL(String NL) {
        this.NL = NL;
    }

    public String getNachname() {
        return Nachname;
    }

    public void setNachname(String nachname) {
        Nachname = nachname;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getPersonalnummer() {
        return Personalnummer;
    }

    public void setPersonalnummer(String personalnummer) {
        Personalnummer = personalnummer;
    }

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getSVNummer() {
        return SVNummer;
    }

    public void setSVNummer(String SVNummer) {
        this.SVNummer = SVNummer;
    }

    public String getTelefax() {
        return Telefax;
    }

    public void setTelefax(String telefax) {
        Telefax = telefax;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getVorgesetzter() {
        return Vorgesetzter;
    }

    public void setVorgesetzter(String vorgesetzter) {
        Vorgesetzter = vorgesetzter;
    }

    public String getVorname() {
        return Vorname;
    }

    public void setVorname(String vorname) {
        Vorname = vorname;
    }

    public String getZeichnung() {
        return Zeichnung;
    }

    public void setZeichnung(String zeichnung) {
        Zeichnung = zeichnung;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Aktiv);
        dest.writeString(this.Anrede);
        dest.writeString(this.Ausgeschieden);
        dest.writeString(this.Benutzername);
        dest.writeString(this.EMail);
        dest.writeString(this.Funktion);
        dest.writeString(this.Gebiet);
        dest.writeString(this.Geschuetzt);
        dest.writeString(this.Hierarchie);
        dest.writeString(this.Mitarbeiter);
        dest.writeString(this.Mobil);
        dest.writeString(this.NL);
        dest.writeString(this.Nachname);
        dest.writeString(this.Ort);
        dest.writeString(this.Personalnummer);
        dest.writeString(this.RowID);
        dest.writeString(this.SVNummer);
        dest.writeString(this.Telefax);
        dest.writeString(this.Telefon);
        dest.writeString(this.Vorgesetzter);
        dest.writeString(this.Vorname);
        dest.writeString(this.Zeichnung);
    }

    public EmployeeModel() {
    }

    private EmployeeModel(Parcel in) {
        this.Aktiv = in.readString();
        this.Anrede = in.readString();
        this.Ausgeschieden = in.readString();
        this.Benutzername = in.readString();
        this.EMail = in.readString();
        this.Funktion = in.readString();
        this.Gebiet = in.readString();
        this.Geschuetzt = in.readString();
        this.Hierarchie = in.readString();
        this.Mitarbeiter = in.readString();
        this.Mobil = in.readString();
        this.NL = in.readString();
        this.Nachname = in.readString();
        this.Ort = in.readString();
        this.Personalnummer = in.readString();
        this.RowID = in.readString();
        this.SVNummer = in.readString();
        this.Telefax = in.readString();
        this.Telefon = in.readString();
        this.Vorgesetzter = in.readString();
        this.Vorname = in.readString();
        this.Zeichnung = in.readString();
    }

    public static final Creator<EmployeeModel> CREATOR = new Creator<EmployeeModel>() {
        public EmployeeModel createFromParcel(Parcel source) {
            return new EmployeeModel(source);
        }

        public EmployeeModel[] newArray(int size) {
            return new EmployeeModel[size];
        }
    };
}
