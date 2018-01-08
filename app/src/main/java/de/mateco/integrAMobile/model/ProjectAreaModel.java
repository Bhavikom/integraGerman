package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProjectAreaModel implements Parcelable
{
    @SerializedName("Gebiet")
    private String projectAreaId;
    @SerializedName("Bezeichnung")
    private String projectAreaDesignation;

    public String getProjectAreaId() {
        return projectAreaId;
    }

    public void setProjectAreaId(String projectAreaId) {
        this.projectAreaId = projectAreaId;
    }

    public String getProjectAreaDesignation() {
        return projectAreaDesignation;
    }

    public void setProjectAreaDesignation(String projectAreaDesignation) {
        this.projectAreaDesignation = projectAreaDesignation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectAreaId);
        dest.writeString(this.projectAreaDesignation);
    }

    public ProjectAreaModel() {
    }

    private ProjectAreaModel(Parcel in) {
        this.projectAreaId = in.readString();
        this.projectAreaDesignation = in.readString();
    }

    public static final Parcelable.Creator<ProjectAreaModel> CREATOR = new Parcelable.Creator<ProjectAreaModel>() {
        public ProjectAreaModel createFromParcel(Parcel source) {
            return new ProjectAreaModel(source);
        }

        public ProjectAreaModel[] newArray(int size) {
            return new ProjectAreaModel[size];
        }
    };
}
