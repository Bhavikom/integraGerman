package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProjectTypeModel implements Parcelable
{
    @SerializedName("Objekttyp")
    private String projectTypeId;
    @SerializedName("Bezeichnung")
    private String projectTypeDesignation;

    public String getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public String getProjectTypeDesignation() {
        return projectTypeDesignation;
    }

    public void setProjectTypeDesignation(String projectTypeDesignation) {
        this.projectTypeDesignation = projectTypeDesignation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectTypeId);
        dest.writeString(this.projectTypeDesignation);
    }

    public ProjectTypeModel() {
    }

    private ProjectTypeModel(Parcel in) {
        this.projectTypeId = in.readString();
        this.projectTypeDesignation = in.readString();
    }

    public static final Parcelable.Creator<ProjectTypeModel> CREATOR = new Parcelable.Creator<ProjectTypeModel>() {
        public ProjectTypeModel createFromParcel(Parcel source) {
            return new ProjectTypeModel(source);
        }

        public ProjectTypeModel[] newArray(int size) {
            return new ProjectTypeModel[size];
        }
    };
}
