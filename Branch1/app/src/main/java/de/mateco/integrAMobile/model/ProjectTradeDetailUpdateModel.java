package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ProjectTradeDetailUpdateModel implements Parcelable {

    String projectId,Kontakt,Anspartner,Gewerk,Beginn,Ende,Telefon,Telefax;

    public ProjectTradeDetailUpdateModel()
    {

    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public String getBeginn() {
        return Beginn;
    }

    public void setBeginn(String beginn) {
        Beginn = beginn;
    }

    public String getEnde() {
        return Ende;
    }

    public void setEnde(String ende) {
        Ende = ende;
    }

    public String getGewerk() {
        return Gewerk;
    }

    public void setGewerk(String gewerk) {
        Gewerk = gewerk;
    }

    public String getAnspartner() {
        return Anspartner;
    }

    public void setAnspartner(String anspartner) {
        Anspartner = anspartner;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private ProjectTradeDetailUpdateModel(Parcel in) {
        this.projectId = in.readString();
        this.Kontakt = in.readString();
        this.Anspartner = in.readString();
        this.Gewerk = in.readString();
        this.Beginn = in.readString();
        this.Ende = in.readString();
        this.Telefax = in.readString();
        this.Telefon = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectId);
        dest.writeString(this.Kontakt);
        dest.writeString(this.Anspartner);
        dest.writeString(this.Gewerk);
        dest.writeString(this.Beginn);
        dest.writeString(this.Ende);
        dest.writeString(this.Telefax);
        dest.writeString(this.Telefon);
    }

    public static final Parcelable.Creator<ProjectTradeDetailUpdateModel> CREATOR = new Parcelable.Creator<ProjectTradeDetailUpdateModel>() {
        public ProjectTradeDetailUpdateModel createFromParcel(Parcel source) {
            return new ProjectTradeDetailUpdateModel(source);
        }

        public ProjectTradeDetailUpdateModel[] newArray(int size) {
            return new ProjectTradeDetailUpdateModel[size];
        }
    };
}
