package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerOfferModel implements Parcelable {
    @SerializedName("Angebot")
    private String offerno;

    public String getOfferno() {
        return offerno;
    }

    public void setOfferno(String offerno) {
        this.offerno = offerno;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.offerno);
    }

    public CustomerOfferModel() {
    }

    private CustomerOfferModel(Parcel in) {
        this.offerno = in.readString();
    }

    public static final Parcelable.Creator<CustomerOfferModel> CREATOR = new Parcelable.Creator<CustomerOfferModel>() {
        public CustomerOfferModel createFromParcel(Parcel source) {
            return new CustomerOfferModel(source);
        }

        public CustomerOfferModel[] newArray(int size) {
            return new CustomerOfferModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<CustomerOfferModel> projects)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<CustomerOfferModel>>(){}.getType();
        ArrayList<CustomerOfferModel> listOfProjects = gson.fromJson(jsonString, typedValue);
        projects.addAll(listOfProjects);
    }
}
