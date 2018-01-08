package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerProjectModel implements Parcelable
{
    @SerializedName("Objekt")
    private String projectId;
    @SerializedName("Projekt")
    private String projectName;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectId);
        dest.writeString(this.projectName);
    }

    public CustomerProjectModel() {
    }

    private CustomerProjectModel(Parcel in) {
        this.projectId = in.readString();
        this.projectName = in.readString();
    }

    public static final Parcelable.Creator<CustomerProjectModel> CREATOR = new Parcelable.Creator<CustomerProjectModel>()
    {
        public CustomerProjectModel createFromParcel(Parcel source)
        {
            return new CustomerProjectModel(source);
        }

        public CustomerProjectModel[] newArray(int size)
        {
            return new CustomerProjectModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<CustomerProjectModel> projects)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<CustomerProjectModel>>(){}.getType();
        ArrayList<CustomerProjectModel> listOFProject = gson.fromJson(jsonString, typedValue);
        projects.addAll(listOFProject);
    }
}
