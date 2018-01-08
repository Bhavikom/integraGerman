package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProjectDetailActivityModel implements Parcelable {
    private String Aktivitaet;
    private String Aktivitatstyp;
    private String AktthemaID;
    private String AkttypID;
    private String Endzeit;
    private String Fest;
    private String Kontakt;
    private String Notiz;
    private String Projekt;
    private String ProjektID;
    private String Realisiert;
    private String Startdatum;
    private String Startzeit;
    private String Thema;
    private String angebot;
    private String CustomerName="";

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    private String FirstName;

    @SerializedName("ContactActivity")
    private ArrayList<SimpleContactPersonAnsPartner> listOfContact;

    @SerializedName("EmployeeActivity")
    private ArrayList<SimpleEmployeeMitrabeiter> listOfEmployee;

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

    public ArrayList<SimpleContactPersonAnsPartner> getListOfContact() {
        return listOfContact;
    }

    public void setListOfContact(ArrayList<SimpleContactPersonAnsPartner> listOfContact) {
        this.listOfContact = listOfContact;
    }

    public ArrayList<SimpleEmployeeMitrabeiter> getListOfEmployee() {
        return listOfEmployee;
    }

    public void setListOfEmployee(ArrayList<SimpleEmployeeMitrabeiter> listOfEmployee) {
        this.listOfEmployee = listOfEmployee;
    }

    public ProjectDetailActivityModel() {
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
        dest.writeString(this.CustomerName);
        dest.writeSerializable(this.listOfContact);
        dest.writeSerializable(this.listOfEmployee);
    }

    private ProjectDetailActivityModel(Parcel in) {
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
        this.CustomerName = in.readString();
        this.listOfContact = (ArrayList<SimpleContactPersonAnsPartner>) in.readSerializable();
        this.listOfEmployee = (ArrayList<SimpleEmployeeMitrabeiter>) in.readSerializable();
    }

    public static final Creator<ProjectDetailActivityModel> CREATOR = new Creator<ProjectDetailActivityModel>() {
        public ProjectDetailActivityModel createFromParcel(Parcel source) {
            return new ProjectDetailActivityModel(source);
        }

        public ProjectDetailActivityModel[] newArray(int size) {
            return new ProjectDetailActivityModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<ProjectDetailActivityModel> listOfProjectActivity)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<ProjectDetailActivityModel>>(){}.getType();
        ArrayList<ProjectDetailActivityModel> objects = gson.fromJson(jsonString, typedValue);
        listOfProjectActivity.addAll(objects);
    }
}
