package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProjectArtModel implements Parcelable
{
    @SerializedName("Objektart")
    private String projectArtId;
    @SerializedName("Bezeichnung")
    private String projectArtdesignation;

    public String getProjectArtId() {
        return projectArtId;
    }

    public void setProjectArtId(String projectArtId) {
        this.projectArtId = projectArtId;
    }

    public String getProjectArtdesignation() {
        return projectArtdesignation;
    }

    public void setProjectArtdesignation(String projectArtdesignation) {
        this.projectArtdesignation = projectArtdesignation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectArtId);
        dest.writeString(this.projectArtdesignation);
    }

    public ProjectArtModel() {
    }

    private ProjectArtModel(Parcel in) {
        this.projectArtId = in.readString();
        this.projectArtdesignation = in.readString();
    }

    public static final Parcelable.Creator<ProjectArtModel> CREATOR = new Parcelable.Creator<ProjectArtModel>() {
        public ProjectArtModel createFromParcel(Parcel source) {
            return new ProjectArtModel(source);
        }

        public ProjectArtModel[] newArray(int size) {
            return new ProjectArtModel[size];
        }
    };
}
