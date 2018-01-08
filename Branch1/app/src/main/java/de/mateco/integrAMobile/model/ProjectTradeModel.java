package de.mateco.integrAMobile.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProjectTradeModel implements Parcelable
{
    @SerializedName("Gewerk")
    private String projectTradeId;
    @SerializedName("Bezeichnung")
    private String projectTradeDesignation;



    public String getProjectTradeId() {
        return projectTradeId;
    }

    public void setProjectTradeId(String projectTradeId) {
        this.projectTradeId = projectTradeId;
    }

    public String getProjectTradeDesignation() {
        return projectTradeDesignation;
    }

    public void setProjectTradeDesignation(String projectTradeDesignation) {
        this.projectTradeDesignation = projectTradeDesignation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectTradeId);
        dest.writeString(this.projectTradeDesignation);
    }

    public ProjectTradeModel() {
    }

    private ProjectTradeModel(Parcel in) {
        this.projectTradeId = in.readString();
        this.projectTradeDesignation = in.readString();
    }

    public static final Parcelable.Creator<ProjectTradeModel> CREATOR = new Parcelable.Creator<ProjectTradeModel>() {
        public ProjectTradeModel createFromParcel(Parcel source) {
            return new ProjectTradeModel(source);
        }

        public ProjectTradeModel[] newArray(int size) {
            return new ProjectTradeModel[size];
        }
    };
}
