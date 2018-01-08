package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CountryModel implements Parcelable
{
    @SerializedName("Land")
    private String countryId;
    @SerializedName("Bezeichnung")
    private String countryName;

    public CountryModel() {
    }

    private CountryModel(Parcel in) {
        this.countryId = in.readString();
        this.countryName = in.readString();
    }

    public static final Parcelable.Creator<CountryModel> CREATOR = new Parcelable.Creator<CountryModel>() {
        public CountryModel createFromParcel(Parcel source) {
            return new CountryModel(source);
        }

        public CountryModel[] newArray(int size) {
            return new CountryModel[size];
        }
    };

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.countryId);
        dest.writeString(this.countryName);
    }

    public static void extractFromJson(String jsonString, ArrayList<CountryModel> countries)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<CountryModel>>(){}.getType();
        ArrayList<CountryModel> countryList = gson.fromJson(jsonString, typedValue);
        countries.addAll(countryList);
    }
}
