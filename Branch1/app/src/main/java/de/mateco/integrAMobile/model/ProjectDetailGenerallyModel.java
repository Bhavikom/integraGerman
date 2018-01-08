package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectDetailGenerallyModel implements Parcelable
{
    private String Abgeschlossen, Akt_Phase, ArtInnenID, ArtaussenID, Baubeginn, Bauende, Beschreibung, Buhnen_Aussen, Buhnen_Innen,
            Datum_Aktuell, Gebiet, Groesse, Hoehe, Hoehe_von_bis, MatchCode, Notiz, Ort, PLZ, PhaseID, Projekt, Projektart,
            ProjektartID, Projekttyp, ProjekttypID, Rampe, Strasse, Weisse_Reifen, zust_ADM;

    public String getAbgeschlossen() {
        return Abgeschlossen;
    }

    public void setAbgeschlossen(String abgeschlossen) {
        Abgeschlossen = abgeschlossen;
    }

    public String getAkt_Phase() {
        return Akt_Phase;
    }

    public void setAkt_Phase(String akt_Phase) {
        Akt_Phase = akt_Phase;
    }

    public String getArtInnenID() {
        return ArtInnenID;
    }

    public void setArtInnenID(String artInnenID) {
        ArtInnenID = artInnenID;
    }

    public String getArtaussenID() {
        return ArtaussenID;
    }

    public void setArtaussenID(String artaussenID) {
        ArtaussenID = artaussenID;
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

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public String getBuhnen_Aussen() {
        return Buhnen_Aussen;
    }

    public void setBuhnen_Aussen(String buhnen_Aussen) {
        Buhnen_Aussen = buhnen_Aussen;
    }

    public String getBuhnen_Innen() {
        return Buhnen_Innen;
    }

    public void setBuhnen_Innen(String buhnen_Innen) {
        Buhnen_Innen = buhnen_Innen;
    }

    public String getDatum_Aktuell() {
        return Datum_Aktuell;
    }

    public void setDatum_Aktuell(String datum_Aktuell) {
        Datum_Aktuell = datum_Aktuell;
    }

    public String getGebiet() {
        return Gebiet;
    }

    public void setGebiet(String gebiet) {
        Gebiet = gebiet;
    }

    public String getGroesse() {
        return Groesse;
    }

    public void setGroesse(String groesse) {
        Groesse = groesse;
    }

    public String getHoehe() {
        return Hoehe;
    }

    public void setHoehe(String hoehe) {
        Hoehe = hoehe;
    }

    public String getHoehe_von_bis() {
        return Hoehe_von_bis;
    }

    public void setHoehe_von_bis(String hoehe_von_bis) {
        Hoehe_von_bis = hoehe_von_bis;
    }

    public String getMatchCode() {
        return MatchCode;
    }

    public void setMatchCode(String matchCode) {
        MatchCode = matchCode;
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

    public String getPhaseID() {
        return PhaseID;
    }

    public void setPhaseID(String phaseID) {
        PhaseID = phaseID;
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

    public String getProjekttyp() {
        return Projekttyp;
    }

    public void setProjekttyp(String projekttyp) {
        Projekttyp = projekttyp;
    }

    public String getProjekttypID() {
        return ProjekttypID;
    }

    public void setProjekttypID(String projekttypID) {
        ProjekttypID = projekttypID;
    }

    public String getRampe() {
        return Rampe;
    }

    public void setRampe(String rampe) {
        Rampe = rampe;
    }

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String strasse) {
        Strasse = strasse;
    }

    public String getWeisse_Reifen() {
        return Weisse_Reifen;
    }

    public void setWeisse_Reifen(String weisse_Reifen) {
        Weisse_Reifen = weisse_Reifen;
    }

    public String getZust_ADM() {
        return zust_ADM;
    }

    public void setZust_ADM(String zust_ADM) {
        this.zust_ADM = zust_ADM;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Abgeschlossen);
        dest.writeString(this.Akt_Phase);
        dest.writeString(this.ArtInnenID);
        dest.writeString(this.ArtaussenID);
        dest.writeString(this.Baubeginn);
        dest.writeString(this.Bauende);
        dest.writeString(this.Beschreibung);
        dest.writeString(this.Buhnen_Aussen);
        dest.writeString(this.Buhnen_Innen);
        dest.writeString(this.Datum_Aktuell);
        dest.writeString(this.Gebiet);
        dest.writeString(this.Groesse);
        dest.writeString(this.Hoehe);
        dest.writeString(this.Hoehe_von_bis);
        dest.writeString(this.MatchCode);
        dest.writeString(this.Notiz);
        dest.writeString(this.Ort);
        dest.writeString(this.PLZ);
        dest.writeString(this.PhaseID);
        dest.writeString(this.Projekt);
        dest.writeString(this.Projektart);
        dest.writeString(this.ProjektartID);
        dest.writeString(this.Projekttyp);
        dest.writeString(this.ProjekttypID);
        dest.writeString(this.Rampe);
        dest.writeString(this.Strasse);
        dest.writeString(this.Weisse_Reifen);
        dest.writeString(this.zust_ADM);
    }

    public ProjectDetailGenerallyModel() {
    }

    private ProjectDetailGenerallyModel(Parcel in) {
        this.Abgeschlossen = in.readString();
        this.Akt_Phase = in.readString();
        this.ArtInnenID = in.readString();
        this.ArtaussenID = in.readString();
        this.Baubeginn = in.readString();
        this.Bauende = in.readString();
        this.Beschreibung = in.readString();
        this.Buhnen_Aussen = in.readString();
        this.Buhnen_Innen = in.readString();
        this.Datum_Aktuell = in.readString();
        this.Gebiet = in.readString();
        this.Groesse = in.readString();
        this.Hoehe = in.readString();
        this.Hoehe_von_bis = in.readString();
        this.MatchCode = in.readString();
        this.Notiz = in.readString();
        this.Ort = in.readString();
        this.PLZ = in.readString();
        this.PhaseID = in.readString();
        this.Projekt = in.readString();
        this.Projektart = in.readString();
        this.ProjektartID = in.readString();
        this.Projekttyp = in.readString();
        this.ProjekttypID = in.readString();
        this.Rampe = in.readString();
        this.Strasse = in.readString();
        this.Weisse_Reifen = in.readString();
        this.zust_ADM = in.readString();
    }

    public static final Parcelable.Creator<ProjectDetailGenerallyModel> CREATOR = new Parcelable.Creator<ProjectDetailGenerallyModel>() {
        public ProjectDetailGenerallyModel createFromParcel(Parcel source) {
            return new ProjectDetailGenerallyModel(source);
        }

        public ProjectDetailGenerallyModel[] newArray(int size) {
            return new ProjectDetailGenerallyModel[size];
        }
    };
}
