package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FeatureModel implements Parcelable {
    private String Merkmal, Bezeichnung;

    public String getMerkmal() {
        return Merkmal;
    }

    public void setMerkmal(String merkmal) {
        Merkmal = merkmal;
    }

    public String getBezeichnung() {
        return Bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        Bezeichnung = bezeichnung;
    }

    public FeatureModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Merkmal);
        dest.writeString(this.Bezeichnung);
    }

    private FeatureModel(Parcel in) {
        this.Merkmal = in.readString();
        this.Bezeichnung = in.readString();
    }

    public static final Creator<FeatureModel> CREATOR = new Creator<FeatureModel>() {
        public FeatureModel createFromParcel(Parcel source) {
            return new FeatureModel(source);
        }

        public FeatureModel[] newArray(int size) {
            return new FeatureModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<FeatureModel> features)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<FeatureModel>>(){}.getType();
        ArrayList<FeatureModel> featureList = gson.fromJson(jsonString, typedValue);
        features.addAll(featureList);
    }
}
