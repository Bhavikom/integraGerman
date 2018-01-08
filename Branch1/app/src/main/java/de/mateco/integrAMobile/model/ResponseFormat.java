package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mmehta on 23.10.2015.
 */
public class ResponseFormat implements Parcelable {
    private String errorMessage;
    private String successMessage;
    private int responseCode;
    private String bvoID;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getBvoID() {
        return bvoID;
    }

    public void setBvoID(String bvoID) {
        this.bvoID = bvoID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.errorMessage);
        dest.writeString(this.successMessage);
        dest.writeInt(this.responseCode);
        dest.writeString(this.bvoID);
    }

    public ResponseFormat() {
    }

    protected ResponseFormat(Parcel in) {
        this.errorMessage = in.readString();
        this.successMessage = in.readString();
        this.responseCode = in.readInt();
        this.bvoID = in.readString();
    }

    public static final Parcelable.Creator<ResponseFormat> CREATOR = new Parcelable.Creator<ResponseFormat>() {
        public ResponseFormat createFromParcel(Parcel source) {
            return new ResponseFormat(source);
        }

        public ResponseFormat[] newArray(int size) {
            return new ResponseFormat[size];
        }
    };
}
