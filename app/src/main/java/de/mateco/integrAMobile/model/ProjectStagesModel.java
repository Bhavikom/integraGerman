package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProjectStagesModel implements Parcelable
{
    @SerializedName("Buehnenart")
    private String projectStageId;
    @SerializedName("Bezeichnung")
    private String projectStageDesignation;

    public String getProjectStageId() {
        return projectStageId;
    }

    public void setProjectStageId(String projectStageId) {
        this.projectStageId = projectStageId;
    }

    public String getProjectStageDesignation() {
        return projectStageDesignation;
    }

    public void setProjectStageDesignation(String projectStageDesignation) {
        this.projectStageDesignation = projectStageDesignation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectStageId);
        dest.writeString(this.projectStageDesignation);
    }

    public ProjectStagesModel() {
    }

    private ProjectStagesModel(Parcel in) {
        this.projectStageId = in.readString();
        this.projectStageDesignation = in.readString();
    }

    public static final Parcelable.Creator<ProjectStagesModel> CREATOR = new Parcelable.Creator<ProjectStagesModel>() {
        public ProjectStagesModel createFromParcel(Parcel source) {
            return new ProjectStagesModel(source);
        }

        public ProjectStagesModel[] newArray(int size) {
            return new ProjectStagesModel[size];
        }
    };
}
