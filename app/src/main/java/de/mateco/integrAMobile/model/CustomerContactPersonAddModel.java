package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerContactPersonAddModel implements Parcelable {
    private String Kontakt, Titel, Anrede, Vorname, Nachname, Funktion, Telefon, Telefax, Mobil, Email;

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
    }

    public CustomerContactPersonAddModel() {
    }

    private CustomerContactPersonAddModel(Parcel in) {
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
    }

    public static final Parcelable.Creator<CustomerContactPersonAddModel> CREATOR = new Parcelable.Creator<CustomerContactPersonAddModel>() {
        public CustomerContactPersonAddModel createFromParcel(Parcel source) {
            return new CustomerContactPersonAddModel(source);
        }

        public CustomerContactPersonAddModel[] newArray(int size) {
            return new CustomerContactPersonAddModel[size];
        }
    };
}
