package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ActivityTopicModel implements Parcelable {
    @SerializedName("aktthema")
    private String activityTopicId;
    @SerializedName("Bezeichnung")
    private String activityTopicName;

    public String getActivityTopicId() {
        return activityTopicId;
    }

    public void setActivityTopicId(String activityTopicId) {
        this.activityTopicId = activityTopicId;
    }

    public String getActivityTopicName() {
        return activityTopicName;
    }

    public void setActivityTopicName(String activityTopicName) {
        this.activityTopicName = activityTopicName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityTopicId);
        dest.writeString(this.activityTopicName);
    }

    public ActivityTopicModel() {
    }

    private ActivityTopicModel(Parcel in) {
        this.activityTopicId = in.readString();
        this.activityTopicName = in.readString();
    }

    public static final Parcelable.Creator<ActivityTopicModel> CREATOR = new Parcelable.Creator<ActivityTopicModel>() {
        public ActivityTopicModel createFromParcel(Parcel source) {
            return new ActivityTopicModel(source);
        }

        public ActivityTopicModel[] newArray(int size) {
            return new ActivityTopicModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<ActivityTopicModel> activityTopic)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<ActivityTopicModel>>(){}.getType();
        ArrayList<ActivityTopicModel> listOfActivityTopic = gson.fromJson(jsonString, typedValue);
        activityTopic.addAll(listOfActivityTopic);
    }
}
