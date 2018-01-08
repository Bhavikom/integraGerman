package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerActivityModel implements Parcelable
{
    private String Aktivitaet, Aktivitatstyp, AktthemaID, AkttypID, Endzeit, Fest, Kontakt, Notiz, Projekt, ProjektID, Realisiert,
            Startdatum, Startzeit, Thema, angebot;
    @SerializedName("ContactActivity")
    private ArrayList<SimpleContactPersonAnsPartner> listOfContactPersonId;
    @SerializedName("EmployeeActivity")
    private ArrayList<SimpleEmployeeMitrabeiter> listOfEmployeeId;

    public String getAktivitaet() {
        return Aktivitaet;
    }

    public void setAktivitaet(String aktivitaet) {
        Aktivitaet = aktivitaet;
    }

    public String getAktivitatstyp() {
        return Aktivitatstyp;
    }

    public void setAktivitatstyp(String aktivitatstyp) {
        Aktivitatstyp = aktivitatstyp;
    }

    public String getAktthemaID() {
        return AktthemaID;
    }

    public void setAktthemaID(String aktthemaID) {
        AktthemaID = aktthemaID;
    }

    public String getAkttypID() {
        return AkttypID;
    }

    public void setAkttypID(String akttypID) {
        AkttypID = akttypID;
    }

    public String getEndzeit() {
        return Endzeit;
    }

    public void setEndzeit(String endzeit) {
        Endzeit = endzeit;
    }

    public String getFest() {
        return Fest;
    }

    public void setFest(String fest) {
        Fest = fest;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getNotiz() {
        return Notiz;
    }

    public void setNotiz(String notiz) {
        Notiz = notiz;
    }

    public String getProjekt() {
        return Projekt;
    }

    public void setProjekt(String projekt) {
        Projekt = projekt;
    }

    public String getProjektID() {
        return ProjektID;
    }

    public void setProjektID(String projektID) {
        ProjektID = projektID;
    }

    public String getRealisiert() {
        return Realisiert;
    }

    public void setRealisiert(String realisiert) {
        Realisiert = realisiert;
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

    public String getThema() {
        return Thema;
    }

    public void setThema(String thema) {
        Thema = thema;
    }

    public String getAngebot() {
        return angebot;
    }

    public void setAngebot(String angebot) {
        this.angebot = angebot;
    }

    public ArrayList<SimpleContactPersonAnsPartner> getListOfContactPersonId() {
        return listOfContactPersonId;
    }

    public void setListOfContactPersonId(ArrayList<SimpleContactPersonAnsPartner> listOfContactPersonId) {
        this.listOfContactPersonId = listOfContactPersonId;
    }

    public ArrayList<SimpleEmployeeMitrabeiter> getListOfEmployeeId() {
        return listOfEmployeeId;
    }

    public void setListOfEmployeeId(ArrayList<SimpleEmployeeMitrabeiter> listOfEmployeeId) {
        this.listOfEmployeeId = listOfEmployeeId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Aktivitaet);
        dest.writeString(this.Aktivitatstyp);
        dest.writeString(this.AktthemaID);
        dest.writeString(this.AkttypID);
        dest.writeString(this.Endzeit);
        dest.writeString(this.Fest);
        dest.writeString(this.Kontakt);
        dest.writeString(this.Notiz);
        dest.writeString(this.Projekt);
        dest.writeString(this.ProjektID);
        dest.writeString(this.Realisiert);
        dest.writeString(this.Startdatum);
        dest.writeString(this.Startzeit);
        dest.writeString(this.Thema);
        dest.writeString(this.angebot);
        dest.writeSerializable(this.listOfContactPersonId);
        dest.writeSerializable(this.listOfEmployeeId);
    }

    public CustomerActivityModel() {
    }

    private CustomerActivityModel(Parcel in) {
        this.Aktivitaet = in.readString();
        this.Aktivitatstyp = in.readString();
        this.AktthemaID = in.readString();
        this.AkttypID = in.readString();
        this.Endzeit = in.readString();
        this.Fest = in.readString();
        this.Kontakt = in.readString();
        this.Notiz = in.readString();
        this.Projekt = in.readString();
        this.ProjektID = in.readString();
        this.Realisiert = in.readString();
        this.Startdatum = in.readString();
        this.Startzeit = in.readString();
        this.Thema = in.readString();
        this.angebot = in.readString();
        this.listOfContactPersonId = (ArrayList<SimpleContactPersonAnsPartner>) in.readSerializable();
        this.listOfEmployeeId = (ArrayList<SimpleEmployeeMitrabeiter>) in.readSerializable();
    }

    public static final Creator<CustomerActivityModel> CREATOR = new Creator<CustomerActivityModel>() {
        public CustomerActivityModel createFromParcel(Parcel source) {
            return new CustomerActivityModel(source);
        }

        public CustomerActivityModel[] newArray(int size) {
            return new CustomerActivityModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<CustomerActivityModel> listOfCustomerActivity)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<CustomerActivityModel>>(){}.getType();
        ArrayList<CustomerActivityModel> customerActivity = gson.fromJson(jsonString, typedValue);
        listOfCustomerActivity.addAll(customerActivity);
    }
}
