package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LegalFormModel implements Parcelable
{
    @SerializedName("RECHTSFORM")
    private String rechtsFormId;
    @SerializedName("Bezeichnung")
    private String rechtsFormDesignation;

    public String getRechtsFormId() {
        return rechtsFormId;
    }

    public void setRechtsFormId(String rechtsFormId) {
        this.rechtsFormId = rechtsFormId;
    }

    public String getRechtsFormDesignation() {
        return rechtsFormDesignation;
    }

    public void setRechtsFormDesignation(String rechtsFormDesignation) {
        this.rechtsFormDesignation = rechtsFormDesignation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rechtsFormId);
        dest.writeString(this.rechtsFormDesignation);
    }

    public LegalFormModel() {
    }

    private LegalFormModel(Parcel in) {
        this.rechtsFormId = in.readString();
        this.rechtsFormDesignation = in.readString();
    }

    public static final Parcelable.Creator<LegalFormModel> CREATOR = new Parcelable.Creator<LegalFormModel>() {
        public LegalFormModel createFromParcel(Parcel source) {
            return new LegalFormModel(source);
        }

        public LegalFormModel[] newArray(int size) {
            return new LegalFormModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<LegalFormModel> countries)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<LegalFormModel>>(){}.getType();
        ArrayList<LegalFormModel> legalFormList = gson.fromJson(jsonString, typedValue);
        countries.addAll(legalFormList);
    }
}
