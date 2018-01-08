package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProjectPhaseModel implements Parcelable
{
    @SerializedName("Objektphase")
    private String projectPhaseId;
    @SerializedName("Bezeichnung")
    private String projectPhaseDesignation;

    public String getProjectPhaseId() {
        return projectPhaseId;
    }

    public void setProjectPhaseId(String projectPhaseId) {
        this.projectPhaseId = projectPhaseId;
    }

    public String getProjectPhaseDesignation() {
        return projectPhaseDesignation;
    }

    public void setProjectPhaseDesignation(String projectPhaseDesignation) {
        this.projectPhaseDesignation = projectPhaseDesignation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectPhaseId);
        dest.writeString(this.projectPhaseDesignation);
    }

    public ProjectPhaseModel() {
    }

    private ProjectPhaseModel(Parcel in) {
        this.projectPhaseId = in.readString();
        this.projectPhaseDesignation = in.readString();
    }

    public static final Parcelable.Creator<ProjectPhaseModel> CREATOR = new Parcelable.Creator<ProjectPhaseModel>() {
        public ProjectPhaseModel createFromParcel(Parcel source) {
            return new ProjectPhaseModel(source);
        }

        public ProjectPhaseModel[] newArray(int size) {
            return new ProjectPhaseModel[size];
        }
    };
}
