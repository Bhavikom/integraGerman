package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChangePasswordModel implements Parcelable {
    private String email, currentpassword, newpassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentpassword() {
        return currentpassword;
    }

    public void setCurrentpassword(String currentpassword) {
        this.currentpassword = currentpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.currentpassword);
        dest.writeString(this.newpassword);
    }

    public ChangePasswordModel() {
    }

    private ChangePasswordModel(Parcel in) {
        this.email = in.readString();
        this.currentpassword = in.readString();
        this.newpassword = in.readString();
    }

    public static final Parcelable.Creator<ChangePasswordModel> CREATOR = new Parcelable.Creator<ChangePasswordModel>() {
        public ChangePasswordModel createFromParcel(Parcel source) {
            return new ChangePasswordModel(source);
        }

        public ChangePasswordModel[] newArray(int size) {
            return new ChangePasswordModel[size];
        }
    };
}
