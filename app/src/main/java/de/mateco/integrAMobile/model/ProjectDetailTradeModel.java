package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProjectDetailTradeModel implements Parcelable {
    private String Ansprechpartner;
    private String Fax_Baustelle;
    private String Gewerk;
    private String GewerkID;
    private String KundenNr;
    private String MatchCode;
    private String Montagebeginn;
    private String Montageende;
    private String Name1;
    private String Name2;
    private String Ort;
    private String PLZ;
    private String Projekt;
    private String Strasse;
    private String Tel_Baustelle;
    private String Telefax;
    private String Telefon;
    private String anspartnerID;
    private String Nachname;

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    private String Kontakt;

    public String getMontageende() {
        return Montageende;
    }

    public void setMontageende(String montageende) {
        Montageende = montageende;
    }

    public String getAnsprechpartner() {
        return Ansprechpartner;
    }

    public void setAnsprechpartner(String ansprechpartner) {
        Ansprechpartner = ansprechpartner;
    }

    public String getFax_Baustelle() {
        return Fax_Baustelle;
    }

    public void setFax_Baustelle(String fax_Baustelle) {
        Fax_Baustelle = fax_Baustelle;
    }

    public String getGewerk() {
        return Gewerk;
    }

    public void setGewerk(String gewerk) {
        Gewerk = gewerk;
    }

    public String getGewerkID() {
        return GewerkID;
    }

    public void setGewerkID(String gewerkID) {
        GewerkID = gewerkID;
    }

    public String getKundenNr() {
        return KundenNr;
    }

    public void setKundenNr(String kundenNr) {
        KundenNr = kundenNr;
    }

    public String getMatchCode() {
        return MatchCode;
    }

    public void setMatchCode(String matchCode) {
        MatchCode = matchCode;
    }

    public String getMontagebeginn() {
        return Montagebeginn;
    }

    public void setMontagebeginn(String montagebeginn) {
        Montagebeginn = montagebeginn;
    }

    public String getName1() {
        return Name1;
    }

    public void setName1(String name1) {
        Name1 = name1;
    }

    public String getName2() {
        return Name2;
    }

    public void setName2(String name2) {
        Name2 = name2;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getProjekt() {
        return Projekt;
    }

    public void setProjekt(String projekt) {
        Projekt = projekt;
    }

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String strasse) {
        Strasse = strasse;
    }

    public String getTel_Baustelle() {
        return Tel_Baustelle;
    }

    public void setTel_Baustelle(String tel_Baustelle) {
        Tel_Baustelle = tel_Baustelle;
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

    public String getAnspartnerID() {
        return anspartnerID;
    }

    public void setAnspartnerID(String anspartnerID) {
        this.anspartnerID = anspartnerID;
    }

    public String getNachname() {
        return Nachname;
    }

    public void setNachname(String nachname) {
        Nachname = nachname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Ansprechpartner);
        dest.writeString(this.Fax_Baustelle);
        dest.writeString(this.Gewerk);
        dest.writeString(this.GewerkID);
        dest.writeString(this.KundenNr);
        dest.writeString(this.MatchCode);
        dest.writeString(this.Montagebeginn);
        dest.writeString(this.Name1);
        dest.writeString(this.Name2);
        dest.writeString(this.Ort);
        dest.writeString(this.PLZ);
        dest.writeString(this.Projekt);
        dest.writeString(this.Strasse);
        dest.writeString(this.Tel_Baustelle);
        dest.writeString(this.Telefax);
        dest.writeString(this.Telefon);
        dest.writeString(this.anspartnerID);
        dest.writeString(this.Montageende);
        dest.writeString(this.Kontakt);
    }

    public ProjectDetailTradeModel() {
    }

    private ProjectDetailTradeModel(Parcel in) {
        this.Ansprechpartner = in.readString();
        this.Fax_Baustelle = in.readString();
        this.Gewerk = in.readString();
        this.GewerkID = in.readString();
        this.KundenNr = in.readString();
        this.MatchCode = in.readString();
        this.Montagebeginn = in.readString();
        this.Name1 = in.readString();
        this.Name2 = in.readString();
        this.Ort = in.readString();
        this.PLZ = in.readString();
        this.Projekt = in.readString();
        this.Strasse = in.readString();
        this.Tel_Baustelle = in.readString();
        this.Telefax = in.readString();
        this.Telefon = in.readString();
        this.anspartnerID = in.readString();
        this.Montageende = in.readString();
        this.Kontakt=in.readString();
    }

    public static final Parcelable.Creator<ProjectDetailTradeModel> CREATOR = new Parcelable.Creator<ProjectDetailTradeModel>() {
        public ProjectDetailTradeModel createFromParcel(Parcel source) {
            return new ProjectDetailTradeModel(source);
        }

        public ProjectDetailTradeModel[] newArray(int size) {
            return new ProjectDetailTradeModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<ProjectDetailTradeModel> listOfProjectTrade)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<ProjectDetailTradeModel>>(){}.getType();
        ArrayList<ProjectDetailTradeModel> objects = gson.fromJson(jsonString, typedValue);
        listOfProjectTrade.addAll(objects);
    }
}
