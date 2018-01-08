package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ShreyashMashru on 08-05-2015.
 */
public class ProjectInsertModel implements Parcelable {
    private String Beschreibung, Projektart, Strasse, PLZ, Ort, MatchCode, Mitarbeiter, UserID;

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public String getProjektart() {
        return Projektart;
    }

    public void setProjektart(String projektart) {
        Projektart = projektart;
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

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Beschreibung);
        dest.writeString(this.Projektart);
        dest.writeString(this.Strasse);
        dest.writeString(this.PLZ);
        dest.writeString(this.Ort);
        dest.writeString(this.MatchCode);
        dest.writeString(this.Mitarbeiter);
        dest.writeString(this.UserID);
    }

    public ProjectInsertModel() {
    }

    private ProjectInsertModel(Parcel in) {
        this.Beschreibung = in.readString();
        this.Projektart = in.readString();
        this.Strasse = in.readString();
        this.PLZ = in.readString();
        this.Ort = in.readString();
        this.MatchCode = in.readString();
        this.Mitarbeiter = in.readString();
        this.UserID = in.readString();
    }

    public static final Parcelable.Creator<ProjectInsertModel> CREATOR = new Parcelable.Creator<ProjectInsertModel>() {
        public ProjectInsertModel createFromParcel(Parcel source) {
            return new ProjectInsertModel(source);
        }

        public ProjectInsertModel[] newArray(int size) {
            return new ProjectInsertModel[size];
        }
    };
}
