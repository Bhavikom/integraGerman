package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProjectModel implements Parcelable {
    private String Adresse, Beginn, Beschreibung, Datum, Ende, Fertig, MatchCode, Mitarbeiter, MitarbeiterID, Ort, Projekt,
            Projektart, ProjektartID, PLZ;

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getBeginn() {
        return Beginn;
    }

    public void setBeginn(String beginn) {
        Beginn = beginn;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public String getEnde() {
        return Ende;
    }

    public void setEnde(String ende) {
        Ende = ende;
    }

    public String getFertig() {
        return Fertig;
    }

    public void setFertig(String fertig) {
        Fertig = fertig;
    }

    public String getMatchCode() {
        return MatchCode;
    }

    public void setMatchCode(String matchCode) {
        MatchCode = matchCode;
    }

    public String getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }

    public String getMitarbeiterID() {
        return MitarbeiterID;
    }

    public void setMitarbeiterID(String mitarbeiterID) {
        MitarbeiterID = mitarbeiterID;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getProjekt() {
        return Projekt;
    }

    public void setProjekt(String projekt) {
        Projekt = projekt;
    }

    public String getProjektart() {
        return Projektart;
    }

    public void setProjektart(String projektart) {
        Projektart = projektart;
    }

    public String getProjektartID() {
        return ProjektartID;
    }

    public void setProjektartID(String projektartID) {
        ProjektartID = projektartID;
    }



    public static void extractFromJson(String jsonString, ArrayList<ProjectModel> projects)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<ProjectModel>>(){}.getType();
        ArrayList<ProjectModel> projectList = gson.fromJson(jsonString, typedValue);
        projects.addAll(projectList);
    }

    public ProjectModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Adresse);
        dest.writeString(this.Beginn);
        dest.writeString(this.Beschreibung);
        dest.writeString(this.Datum);
        dest.writeString(this.Ende);
        dest.writeString(this.Fertig);
        dest.writeString(this.MatchCode);
        dest.writeString(this.Mitarbeiter);
        dest.writeString(this.MitarbeiterID);
        dest.writeString(this.Ort);
        dest.writeString(this.Projekt);
        dest.writeString(this.Projektart);
        dest.writeString(this.ProjektartID);
        dest.writeString(this.PLZ);
    }

    protected ProjectModel(Parcel in) {
        this.Adresse = in.readString();
        this.Beginn = in.readString();
        this.Beschreibung = in.readString();
        this.Datum = in.readString();
        this.Ende = in.readString();
        this.Fertig = in.readString();
        this.MatchCode = in.readString();
        this.Mitarbeiter = in.readString();
        this.MitarbeiterID = in.readString();
        this.Ort = in.readString();
        this.Projekt = in.readString();
        this.Projektart = in.readString();
        this.ProjektartID = in.readString();
        this.PLZ = in.readString();
    }

    public static final Creator<ProjectModel> CREATOR = new Creator<ProjectModel>() {
        public ProjectModel createFromParcel(Parcel source) {
            return new ProjectModel(source);
        }

        public ProjectModel[] newArray(int size) {
            return new ProjectModel[size];
        }
    };
}
