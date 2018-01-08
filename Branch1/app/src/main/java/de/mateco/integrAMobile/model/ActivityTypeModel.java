package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ActivityTypeModel implements Parcelable {
    @SerializedName("akttyp")
    private String activityTypeId;
    @SerializedName("Bezeichnung")
    private String activityTypeName;

    public String getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(String activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityTypeId);
        dest.writeString(this.activityTypeName);
    }

    public ActivityTypeModel() {
    }

    private ActivityTypeModel(Parcel in) {
        this.activityTypeId = in.readString();
        this.activityTypeName = in.readString();
    }

    public static final Parcelable.Creator<ActivityTypeModel> CREATOR = new Parcelable.Creator<ActivityTypeModel>() {
        public ActivityTypeModel createFromParcel(Parcel source) {
            return new ActivityTypeModel(source);
        }

        public ActivityTypeModel[] newArray(int size) {
            return new ActivityTypeModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<ActivityTypeModel> activities)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<ActivityTypeModel>>(){}.getType();
        ArrayList<ActivityTypeModel> listOfActivity = gson.fromJson(jsonString, typedValue);
        activities.addAll(listOfActivity);
    }
}
