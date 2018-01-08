package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginPersonModel implements Parcelable {
    private String Country, Location, NumberRange, UserDesignation, UserEmail, UserId, UserName, UserNumber, BranchCode;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getNumberRange() {
        return NumberRange;
    }

    public void setNumberRange(String numberRange) {
        NumberRange = numberRange;
    }

    public String getUserDesignation() {
        return UserDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        UserDesignation = userDesignation;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserNumber() {
        return UserNumber;
    }

    public void setUserNumber(String userNumber) {
        UserNumber = userNumber;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public static void extractFromJson(String jsonString, ArrayList<LoginPersonModel> Persons)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<LoginPersonModel>>(){}.getType();
        ArrayList<LoginPersonModel> listOfPerson = gson.fromJson(jsonString, typedValue);
        Persons.addAll(listOfPerson);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Country);
        dest.writeString(this.Location);
        dest.writeString(this.NumberRange);
        dest.writeString(this.UserDesignation);
        dest.writeString(this.UserEmail);
        dest.writeString(this.UserId);
        dest.writeString(this.UserName);
        dest.writeString(this.UserNumber);
        dest.writeString(this.BranchCode);
    }

    public LoginPersonModel() {
    }

    private LoginPersonModel(Parcel in) {
        this.Country = in.readString();
        this.Location = in.readString();
        this.NumberRange = in.readString();
        this.UserDesignation = in.readString();
        this.UserEmail = in.readString();
        this.UserId = in.readString();
        this.UserName = in.readString();
        this.UserNumber = in.readString();
        this.BranchCode = in.readString();
    }

    public static final Creator<LoginPersonModel> CREATOR = new Creator<LoginPersonModel>() {
        public LoginPersonModel createFromParcel(Parcel source) {
            return new LoginPersonModel(source);
        }

        public LoginPersonModel[] newArray(int size) {
            return new LoginPersonModel[size];
        }
    };
}
