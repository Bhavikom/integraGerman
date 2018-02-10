package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonFeatureListItem;

public class ContactPersonModel implements Parcelable
{
    private String Anrede, AnredeID, Anspartner, Ausgeschieden, Belegsprache, Email, Entscheider, EntscheiderID, Funktion, FunktionID,
            Kontakt, Mobil, Nachname, Telefax, Telefon, Titel, Vorname, Zusatzinfo;

    private ArrayList<CustomerContactPersonFeatureListItem> Merkmal = new ArrayList<>();

    public ArrayList<CustomerContactPersonFeatureListItem> getFeatureList() {
        return Merkmal;
    }

    public void setFeatureList(ArrayList<CustomerContactPersonFeatureListItem> Merkmal) {
        this.Merkmal = Merkmal;
    }

    public String getAnrede() {
        return Anrede;
    }

    public void setAnrede(String anrede) {
        Anrede = anrede;
    }

    public String getAnredeID() {
        return AnredeID;
    }

    public void setAnredeID(String anredeID) {
        AnredeID = anredeID;
    }

    public String getAnspartner() {
        return Anspartner;
    }

    public void setAnspartner(String anspartner) {
        Anspartner = anspartner;
    }

    public String getAusgeschieden() {
        return Ausgeschieden;
    }

    public void setAusgeschieden(String ausgeschieden) {
        Ausgeschieden = ausgeschieden;
    }

    public String getBelegsprache() {
        return Belegsprache;
    }

    public void setBelegsprache(String belegsprache) {
        Belegsprache = belegsprache;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEntscheider() {
        return Entscheider;
    }

    public void setEntscheider(String entscheider) {
        Entscheider = entscheider;
    }

    public String getEntscheiderID() {
        return EntscheiderID;
    }

    public void setEntscheiderID(String entscheiderID) {
        EntscheiderID = entscheiderID;
    }

    public String getFunktion() {
        return Funktion;
    }

    public void setFunktion(String funktion) {
        Funktion = funktion;
    }

    public String getFunktionID() {
        return FunktionID;
    }

    public void setFunktionID(String funktionID) {
        FunktionID = funktionID;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getMobil() {
        return Mobil;
    }

    public void setMobil(String mobil) {
        Mobil = mobil;
    }

    public String getNachname() {
        return Nachname;
    }

    public void setNachname(String nachname) {
        Nachname = nachname;
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

    public String getTitel() {
        return Titel;
    }

    public void setTitel(String titel) {
        Titel = titel;
    }

    public String getVorname() {
        return Vorname;
    }

    public void setVorname(String vorname) {
        Vorname = vorname;
    }

    public String getZusatzinfo() {
        return Zusatzinfo;
    }

    public void setZusatzinfo(String zusatzinfo) {
        Zusatzinfo = zusatzinfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Anrede);
        dest.writeString(this.AnredeID);
        dest.writeString(this.Anspartner);
        dest.writeString(this.Ausgeschieden);
        dest.writeString(this.Belegsprache);
        dest.writeString(this.Email);
        dest.writeString(this.Entscheider);
        dest.writeString(this.EntscheiderID);
        dest.writeString(this.Funktion);
        dest.writeString(this.FunktionID);
        dest.writeString(this.Kontakt);
        dest.writeString(this.Mobil);
        dest.writeString(this.Nachname);
        dest.writeString(this.Telefax);
        dest.writeString(this.Telefon);
        dest.writeString(this.Titel);
        dest.writeString(this.Vorname);
        dest.writeString(this.Zusatzinfo);
        dest.writeTypedList(this.Merkmal);
    }

    public ContactPersonModel() {
    }

    private ContactPersonModel(Parcel in) {
        this.Anrede = in.readString();
        this.AnredeID = in.readString();
        this.Anspartner = in.readString();
        this.Ausgeschieden = in.readString();
        this.Belegsprache = in.readString();
        this.Email = in.readString();
        this.Entscheider = in.readString();
        this.EntscheiderID = in.readString();
        this.Funktion = in.readString();
        this.FunktionID = in.readString();
        this.Kontakt = in.readString();
        this.Mobil = in.readString();
        this.Nachname = in.readString();
        this.Telefax = in.readString();
        this.Telefon = in.readString();
        this.Titel = in.readString();
        this.Vorname = in.readString();
        this.Zusatzinfo = in.readString();
        in.readTypedList(this.Merkmal, CustomerContactPersonFeatureListItem.CREATOR);
    }

    public static final Creator<ContactPersonModel> CREATOR = new Creator<ContactPersonModel>() {
        public ContactPersonModel createFromParcel(Parcel source) {
            return new ContactPersonModel(source);
        }

        public ContactPersonModel[] newArray(int size) {
            return new ContactPersonModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<ContactPersonModel> contactPersons)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<ContactPersonModel>>(){}.getType();
        ArrayList<ContactPersonModel> contactPersonList = gson.fromJson(jsonString, typedValue);
        contactPersons.addAll(contactPersonList);
    }
}
