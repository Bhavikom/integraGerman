package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ShreyashMashru on 15-05-2015.
 */
public class WeeklyAgendaModel implements Parcelable {
    private String Adresse, Aktivitaet, Aktivitaetstytp, Angebot, Beschreibung, Datum, Endzeit, Fest, Kontakt,
            Matchcode, Matchcode_Projekt, Mitarbeiter, Name1, Notiz, Ort, PLZ, PLZ_Ort, PLZ_Projekt, Projekt,
            Realisiert, Startzeit, Strasse, Typ;
    /**********20161109**********/
    int Thema;
    /****************************/

    private ArrayList<String> Ansprechpartner, MitarbeiterName;

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getAktivitaet() {
        return Aktivitaet;
    }

    public void setAktivitaet(String aktivitaet) {
        Aktivitaet = aktivitaet;
    }

    public String getAktivitaetstytp() {
        return Aktivitaetstytp;
    }

    public void setAktivitaetstytp(String aktivitaetstytp) {
        Aktivitaetstytp = aktivitaetstytp;
    }

    public String getAngebot() {
        return Angebot;
    }

    public void setAngebot(String angebot) {
        Angebot = angebot;
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

    public String getMatchcode() {
        return Matchcode;
    }

    public void setMatchcode(String matchcode) {
        Matchcode = matchcode;
    }

    public String getMatchcode_Projekt() {
        return Matchcode_Projekt;
    }

    public void setMatchcode_Projekt(String matchcode_Projekt) {
        Matchcode_Projekt = matchcode_Projekt;
    }

    public String getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }

    public String getName1() {
        return Name1;
    }

    public void setName1(String name1) {
        Name1 = name1;
    }

    public String getNotiz() {
        return Notiz;
    }

    public void setNotiz(String notiz) {
        Notiz = notiz;
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

    public String getPLZ_Ort() {
        return PLZ_Ort;
    }

    public void setPLZ_Ort(String PLZ_Ort) {
        this.PLZ_Ort = PLZ_Ort;
    }

    public String getPLZ_Projekt() {
        return PLZ_Projekt;
    }

    public void setPLZ_Projekt(String PLZ_Projekt) {
        this.PLZ_Projekt = PLZ_Projekt;
    }

    public String getProjekt() {
        return Projekt;
    }

    public void setProjekt(String projekt) {
        Projekt = projekt;
    }

    public String getRealisiert() {
        return Realisiert;
    }

    public void setRealisiert(String realisiert) {
        Realisiert = realisiert;
    }

    public String getStartzeit() {
        return Startzeit;
    }

    public void setStartzeit(String startzeit) {
        Startzeit = startzeit;
    }

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String strasse) {
        Strasse = strasse;
    }

    public String getTyp() {
        return Typ;
    }

    /**********20161109**********/
    public void setTyp(String typ) {
        Typ = typ;
    }

    public int getAktThema() {
        return Thema;
    }
    /****************************/
    public void setAktThema(int AktThema) {
        this.Thema = AktThema;
    }

    public ArrayList<String> getAnsprechpartner() {
        return Ansprechpartner;
    }

    public void setAnsprechpartner(ArrayList<String> ansprechpartner) {
        Ansprechpartner = ansprechpartner;
    }

    public ArrayList<String> getMitarbeiterName() {
        return MitarbeiterName;
    }

    public void setMitarbeiterName(ArrayList<String> mitarbeiterName) {
        MitarbeiterName = mitarbeiterName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Adresse);
        dest.writeString(this.Aktivitaet);
        dest.writeString(this.Aktivitaetstytp);
        dest.writeString(this.Angebot);
        dest.writeString(this.Beschreibung);
        dest.writeString(this.Datum);
        dest.writeString(this.Endzeit);
        dest.writeString(this.Fest);
        dest.writeString(this.Kontakt);
        dest.writeString(this.Matchcode);
        dest.writeString(this.Matchcode_Projekt);
        dest.writeString(this.Mitarbeiter);
        dest.writeString(this.Name1);
        dest.writeString(this.Notiz);
        dest.writeString(this.Ort);
        dest.writeString(this.PLZ);
        dest.writeString(this.PLZ_Ort);
        dest.writeString(this.PLZ_Projekt);
        dest.writeString(this.Projekt);
        dest.writeString(this.Realisiert);
        dest.writeString(this.Startzeit);
        dest.writeString(this.Strasse);
        dest.writeString(this.Typ);
        /**********20161109**********/
        dest.writeInt(this.Thema);
        /****************************/
        dest.writeSerializable(this.Ansprechpartner);
        dest.writeSerializable(this.MitarbeiterName);
    }

    public WeeklyAgendaModel() {
    }

    private WeeklyAgendaModel(Parcel in) {
        this.Adresse = in.readString();
        this.Aktivitaet = in.readString();
        this.Aktivitaetstytp = in.readString();
        this.Angebot = in.readString();
        this.Beschreibung = in.readString();
        this.Datum = in.readString();
        this.Endzeit = in.readString();
        this.Fest = in.readString();
        this.Kontakt = in.readString();
        this.Matchcode = in.readString();
        this.Matchcode_Projekt = in.readString();
        this.Mitarbeiter = in.readString();
        this.Name1 = in.readString();
        this.Notiz = in.readString();
        this.Ort = in.readString();
        this.PLZ = in.readString();
        this.PLZ_Ort = in.readString();
        this.PLZ_Projekt = in.readString();
        this.Projekt = in.readString();
        this.Realisiert = in.readString();
        this.Startzeit = in.readString();
        this.Strasse = in.readString();
        this.Typ = in.readString();
        /**********20161109**********/
        this.Thema = in.readInt();
        /****************************/
        this.Ansprechpartner = (ArrayList<String>) in.readSerializable();
        this.MitarbeiterName = (ArrayList<String>) in.readSerializable();
    }

    public static final Creator<WeeklyAgendaModel> CREATOR = new Creator<WeeklyAgendaModel>() {
        public WeeklyAgendaModel createFromParcel(Parcel source) {
            return new WeeklyAgendaModel(source);
        }

        public WeeklyAgendaModel[] newArray(int size) {
            return new WeeklyAgendaModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<WeeklyAgendaModel> weeklyAgendas)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<WeeklyAgendaModel>>(){}.getType();
        ArrayList<WeeklyAgendaModel> agendaList = gson.fromJson(jsonString, typedValue);
        weeklyAgendas.addAll(agendaList);
    }
}
