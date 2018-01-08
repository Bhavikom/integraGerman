package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectDetailGenerallyUpdateModel implements Parcelable {
    private String Projekt, Mitarbeiter, Gebiet, Projektart, ArtaussenID, PhaseID, MatchCode, Beschreibung, Strasse, PLZ, Ort,
            Baubeginn, Bauende, Datum, ArtInnen, Hoehe, Groesse, Rampe, Hoehe_von_bis, ProjekttypID, Weisse_Reifen, Notiz,
            AenderungMitarbeiter, zust_ADM, Abgeschlossen;

    public String getProjekt() {
        return Projekt;
    }

    public void setProjekt(String projekt) {
        Projekt = projekt;
    }

    public String getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }

    public String getGebiet() {
        return Gebiet;
    }

    public void setGebiet(String gebiet) {
        Gebiet = gebiet;
    }

    public String getProjektart() {
        return Projektart;
    }

    public void setProjektart(String projektart) {
        Projektart = projektart;
    }

    public String getArtaussenID() {
        return ArtaussenID;
    }

    public void setArtaussenID(String artaussenID) {
        ArtaussenID = artaussenID;
    }

    public String getPhaseID() {
        return PhaseID;
    }

    public void setPhaseID(String phaseID) {
        PhaseID = phaseID;
    }

    public String getMatchCode() {
        return MatchCode;
    }

    public void setMatchCode(String matchCode) {
        MatchCode = matchCode;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String strasse) {
        Strasse = strasse;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getBaubeginn() {
        return Baubeginn;
    }

    public void setBaubeginn(String baubeginn) {
        Baubeginn = baubeginn;
    }

    public String getBauende() {
        return Bauende;
    }

    public void setBauende(String bauende) {
        Bauende = bauende;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public String getArtInnen() {
        return ArtInnen;
    }

    public void setArtInnen(String artInnen) {
        ArtInnen = artInnen;
    }

    public String getHoehe() {
        return Hoehe;
    }

    public void setHoehe(String hoehe) {
        Hoehe = hoehe;
    }

    public String getGroesse() {
        return Groesse;
    }

    public void setGroesse(String groesse) {
        Groesse = groesse;
    }

    public String getRampe() {
        return Rampe;
    }

    public void setRampe(String rampe) {
        Rampe = rampe;
    }

    public String getHoehe_von_bis() {
        return Hoehe_von_bis;
    }

    public void setHoehe_von_bis(String hoehe_von_bis) {
        Hoehe_von_bis = hoehe_von_bis;
    }

    public String getProjekttypID() {
        return ProjekttypID;
    }

    public void setProjekttypID(String projekttypID) {
        ProjekttypID = projekttypID;
    }

    public String getWeisse_Reifen() {
        return Weisse_Reifen;
    }

    public void setWeisse_Reifen(String weisse_Reifen) {
        Weisse_Reifen = weisse_Reifen;
    }

    public String getNotiz() {
        return Notiz;
    }

    public void setNotiz(String notiz) {
        Notiz = notiz;
    }

    public String getAenderungMitarbeiter() {
        return AenderungMitarbeiter;
    }

    public void setAenderungMitarbeiter(String aenderungMitarbeiter) {
        AenderungMitarbeiter = aenderungMitarbeiter;
    }

    public String getZust_ADM() {
        return zust_ADM;
    }

    public void setZust_ADM(String zust_ADM) {
        this.zust_ADM = zust_ADM;
    }

    public String getAbgeschlossen() {
        return Abgeschlossen;
    }

    public void setAbgeschlossen(String abgeschlossen) {
        Abgeschlossen = abgeschlossen;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Projekt);
        dest.writeString(this.Mitarbeiter);
        dest.writeString(this.Gebiet);
        dest.writeString(this.Projektart);
        dest.writeString(this.ArtaussenID);
        dest.writeString(this.PhaseID);
        dest.writeString(this.MatchCode);
        dest.writeString(this.Beschreibung);
        dest.writeString(this.Strasse);
        dest.writeString(this.PLZ);
        dest.writeString(this.Ort);
        dest.writeString(this.Baubeginn);
        dest.writeString(this.Bauende);
        dest.writeString(this.Datum);
        dest.writeString(this.ArtInnen);
        dest.writeString(this.Hoehe);
        dest.writeString(this.Groesse);
        dest.writeString(this.Rampe);
        dest.writeString(this.Hoehe_von_bis);
        dest.writeString(this.ProjekttypID);
        dest.writeString(this.Weisse_Reifen);
        dest.writeString(this.Notiz);
        dest.writeString(this.AenderungMitarbeiter);
        dest.writeString(this.zust_ADM);
        dest.writeString(this.Abgeschlossen);
    }

    public ProjectDetailGenerallyUpdateModel() {
    }

    private ProjectDetailGenerallyUpdateModel(Parcel in) {
        this.Projekt = in.readString();
        this.Mitarbeiter = in.readString();
        this.Gebiet = in.readString();
        this.Projektart = in.readString();
        this.ArtaussenID = in.readString();
        this.PhaseID = in.readString();
        this.MatchCode = in.readString();
        this.Beschreibung = in.readString();
        this.Strasse = in.readString();
        this.PLZ = in.readString();
        this.Ort = in.readString();
        this.Baubeginn = in.readString();
        this.Bauende = in.readString();
        this.Datum = in.readString();
        this.ArtInnen = in.readString();
        this.Hoehe = in.readString();
        this.Groesse = in.readString();
        this.Rampe = in.readString();
        this.Hoehe_von_bis = in.readString();
        this.ProjekttypID = in.readString();
        this.Weisse_Reifen = in.readString();
        this.Notiz = in.readString();
        this.AenderungMitarbeiter = in.readString();
        this.zust_ADM = in.readString();
        this.Abgeschlossen = in.readString();
    }

    public static final Creator<ProjectDetailGenerallyUpdateModel> CREATOR = new Creator<ProjectDetailGenerallyUpdateModel>() {
        public ProjectDetailGenerallyUpdateModel createFromParcel(Parcel source) {
            return new ProjectDetailGenerallyUpdateModel(source);
        }

        public ProjectDetailGenerallyUpdateModel[] newArray(int size) {
            return new ProjectDetailGenerallyUpdateModel[size];
        }
    };
}
