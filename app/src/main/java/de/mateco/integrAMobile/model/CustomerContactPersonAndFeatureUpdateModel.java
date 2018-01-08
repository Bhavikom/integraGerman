package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerContactPersonAndFeatureUpdateModel implements Parcelable
{
    private String Kontakt, Titel, Anrede, Vorname, Nachname, Funktion, Telefon, Telefax, Mobil, Email, AnsPartner, AenderungMitarbeiter,
            Entscheider, Zusatzinfo;
    @SerializedName("Merkmal")
    private ArrayList<String> Merkmal;

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getTitel() {
        return Titel;
    }

    public void setTitel(String titel) {
        Titel = titel;
    }

    public String getAnrede() {
        return Anrede;
    }

    public void setAnrede(String anrede) {
        Anrede = anrede;
    }

    public String getVorname() {
        return Vorname;
    }

    public void setVorname(String vorname) {
        Vorname = vorname;
    }

    public String getNachname() {
        return Nachname;
    }

    public void setNachname(String nachname) {
        Nachname = nachname;
    }

    public String getFunktion() {
        return Funktion;
    }

    public void setFunktion(String funktion) {
        Funktion = funktion;
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

    public String getMobil() {
        return Mobil;
    }

    public void setMobil(String mobil) {
        Mobil = mobil;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAnsPartner() {
        return AnsPartner;
    }

    public void setAnsPartner(String ansPartner) {
        AnsPartner = ansPartner;
    }

    public String getAenderungMitarbeiter() {
        return AenderungMitarbeiter;
    }

    public void setAenderungMitarbeiter(String aenderungMitarbeiter) {
        AenderungMitarbeiter = aenderungMitarbeiter;
    }

    public String getEntscheider() {
        return Entscheider;
    }

    public void setEntscheider(String entscheider) {
        Entscheider = entscheider;
    }

    public String getZusatzinfo() {
        return Zusatzinfo;
    }

    public void setZusatzinfo(String zusatzinfo) {
        Zusatzinfo = zusatzinfo;
    }

    public ArrayList<String> getMerkmal() {
        return Merkmal;
    }

    public void setMerkmal(ArrayList<String> merkmal) {
        Merkmal = merkmal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Kontakt);
        dest.writeString(this.Titel);
        dest.writeString(this.Anrede);
        dest.writeString(this.Vorname);
        dest.writeString(this.Nachname);
        dest.writeString(this.Funktion);
        dest.writeString(this.Telefon);
        dest.writeString(this.Telefax);
        dest.writeString(this.Mobil);
        dest.writeString(this.Email);
        dest.writeString(this.AnsPartner);
        dest.writeString(this.AenderungMitarbeiter);
        dest.writeString(this.Entscheider);
        dest.writeString(this.Zusatzinfo);
        dest.writeSerializable(this.Merkmal);
    }

    public CustomerContactPersonAndFeatureUpdateModel() {
    }

    private CustomerContactPersonAndFeatureUpdateModel(Parcel in) {
        this.Kontakt = in.readString();
        this.Titel = in.readString();
        this.Anrede = in.readString();
        this.Vorname = in.readString();
        this.Nachname = in.readString();
        this.Funktion = in.readString();
        this.Telefon = in.readString();
        this.Telefax = in.readString();
        this.Mobil = in.readString();
        this.Email = in.readString();
        this.AnsPartner = in.readString();
        this.AenderungMitarbeiter = in.readString();
        this.Entscheider = in.readString();
        this.Zusatzinfo = in.readString();
        this.Merkmal = (ArrayList<String>) in.readSerializable();
    }

    public static final Creator<CustomerContactPersonAndFeatureUpdateModel> CREATOR = new Creator<CustomerContactPersonAndFeatureUpdateModel>() {
        public CustomerContactPersonAndFeatureUpdateModel createFromParcel(Parcel source) {
            return new CustomerContactPersonAndFeatureUpdateModel(source);
        }

        public CustomerContactPersonAndFeatureUpdateModel[] newArray(int size) {
            return new CustomerContactPersonAndFeatureUpdateModel[size];
        }
    };
}
