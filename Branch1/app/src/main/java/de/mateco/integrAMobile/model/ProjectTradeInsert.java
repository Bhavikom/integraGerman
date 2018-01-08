package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RuchaVyas on 27/08/2015.
 */
public class ProjectTradeInsert implements Parcelable{

    String projectId;
    String Kontakt;
    String Anspartner;
    String Gewerk;

    public ProjectTradeInsert()
    {

    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getAnspartner() {
        return Anspartner;
    }

    public void setAnspartner(String anspartner) {
        Anspartner = anspartner;
    }

    public String getGewerk() {
        return Gewerk;
    }

    public void setGewerk(String gewerk) {
        Gewerk = gewerk;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectId);
        dest.writeString(this.Kontakt);
        dest.writeString(this.Anspartner);
        dest.writeString(this.Gewerk);
    }

    protected ProjectTradeInsert(Parcel in) {
        this.projectId = in.readString();
        this.Kontakt = in.readString();
        this.Anspartner = in.readString();
        this.Gewerk = in.readString();
    }

    public static final Creator<ProjectTradeInsert> CREATOR = new Creator<ProjectTradeInsert>() {
        public ProjectTradeInsert createFromParcel(Parcel source) {
            return new ProjectTradeInsert(source);
        }

        public ProjectTradeInsert[] newArray(int size) {
            return new ProjectTradeInsert[size];
        }
    };
}
