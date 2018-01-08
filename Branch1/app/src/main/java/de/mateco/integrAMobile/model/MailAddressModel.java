package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MailAddressModel implements Parcelable {
    private String Email, Funktion;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFunktion() {
        return Funktion;
    }

    public void setFunktion(String funktion) {
        Funktion = funktion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Email);
        dest.writeString(this.Funktion);
    }

    public MailAddressModel() {
    }

    private MailAddressModel(Parcel in) {
        this.Email = in.readString();
        this.Funktion = in.readString();
    }

    public static final Parcelable.Creator<MailAddressModel> CREATOR = new Parcelable.Creator<MailAddressModel>() {
        public MailAddressModel createFromParcel(Parcel source) {
            return new MailAddressModel(source);
        }

        public MailAddressModel[] newArray(int size) {
            return new MailAddressModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<MailAddressModel> mailAddresses)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<MailAddressModel>>(){}.getType();
        ArrayList<MailAddressModel> listOfMailAddress = gson.fromJson(jsonString, typedValue);
        mailAddresses.addAll(listOfMailAddress);
    }
}
