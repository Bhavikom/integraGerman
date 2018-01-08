package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SalutationModel implements Parcelable
{
    @SerializedName("Anrede")
    private String salutationId;
    @SerializedName("Bezeichnung")
    private String salutationDesignation;

    public String getSalutationId() {
        return salutationId;
    }

    public void setSalutationId(String salutationId) {
        this.salutationId = salutationId;
    }

    public String getSalutationDesignation() {
        return salutationDesignation;
    }

    public void setSalutationDesignation(String salutationDesignation) {
        this.salutationDesignation = salutationDesignation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.salutationId);
        dest.writeString(this.salutationDesignation);
    }

    public SalutationModel() {
    }

    private SalutationModel(Parcel in) {
        this.salutationId = in.readString();
        this.salutationDesignation = in.readString();
    }

    public static final Parcelable.Creator<SalutationModel> CREATOR = new Parcelable.Creator<SalutationModel>() {
        public SalutationModel createFromParcel(Parcel source) {
            return new SalutationModel(source);
        }

        public SalutationModel[] newArray(int size) {
            return new SalutationModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<SalutationModel> countries)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<SalutationModel>>(){}.getType();
        ArrayList<SalutationModel> salutationList = gson.fromJson(jsonString, typedValue);
        countries.addAll(salutationList);
    }
}
