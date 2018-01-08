package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DecisionMakerModel implements Parcelable {
    @SerializedName("Entscheider")
    private String decisionMakerId;
    @SerializedName("Bezeichnung")
    private String decisionMakerDesignation;

    public String getDecisionMakerDesignation() {
        return decisionMakerDesignation;
    }

    public void setDecisionMakerDesignation(String decisionMakerDesignation) {
        this.decisionMakerDesignation = decisionMakerDesignation;
    }

    public String getDecisionMakerId() {
        return decisionMakerId;
    }

    public void setDecisionMakerId(String decisionMakerId) {
        this.decisionMakerId = decisionMakerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.decisionMakerId);
        dest.writeString(this.decisionMakerDesignation);
    }

    public DecisionMakerModel() {
    }

    private DecisionMakerModel(Parcel in) {
        this.decisionMakerId = in.readString();
        this.decisionMakerDesignation = in.readString();
    }

    public static final Parcelable.Creator<DecisionMakerModel> CREATOR = new Parcelable.Creator<DecisionMakerModel>() {
        public DecisionMakerModel createFromParcel(Parcel source) {
            return new DecisionMakerModel(source);
        }

        public DecisionMakerModel[] newArray(int size) {
            return new DecisionMakerModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<DecisionMakerModel> countries)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<DecisionMakerModel>>(){}.getType();
        ArrayList<DecisionMakerModel> decisionMakersList = gson.fromJson(jsonString, typedValue);
        countries.addAll(decisionMakersList);
    }
}
