package de.mateco.integrAMobile.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SiteInspectionModel implements Parcelable {

    private transient int id;
    private transient String uploadId;
    private transient int flag;
    private transient String date;
    @SerializedName("BVOList")
    SiteInspectionNewModel siteInspectionNewModel;
    @SerializedName("BVOUmgebungList")
    SiteInspectionOperationalEnvironmentModel siteInspectionOperationalEnvironmentModel;
    @SerializedName("BVOGenehmigungList")
    SiteInspectionOperationalDataPermitsModel siteInspectionOperationalDataPermitsModel;
    @SerializedName("BVOMobilfunkList")
    SiteInspectionAdditionalMobileWindPowerModel siteInspectionAdditionalMobileWindPowerModel;
    @SerializedName("Email")
    ArrayList<String> listOfEmailAddress;
    @SerializedName("EmailNote")
    private String emailBodyContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SiteInspectionNewModel getSiteInspectionNewModel() {
        return siteInspectionNewModel;
    }

    public void setSiteInspectionNewModel(SiteInspectionNewModel siteInspectionNewModel) {
        this.siteInspectionNewModel = siteInspectionNewModel;
    }

    public SiteInspectionOperationalEnvironmentModel getSiteInspectionOperationalEnvironmentModel() {
        return siteInspectionOperationalEnvironmentModel;
    }

    public void setSiteInspectionOperationalEnvironmentModel(SiteInspectionOperationalEnvironmentModel siteInspectionOperationalEnvironmentModel) {
        this.siteInspectionOperationalEnvironmentModel = siteInspectionOperationalEnvironmentModel;
    }

    public SiteInspectionOperationalDataPermitsModel getSiteInspectionOperationalDataPermitsModel() {
        return siteInspectionOperationalDataPermitsModel;
    }

    public void setSiteInspectionOperationalDataPermitsModel(SiteInspectionOperationalDataPermitsModel siteInspectionOperationalDataPermitsModel) {
        this.siteInspectionOperationalDataPermitsModel = siteInspectionOperationalDataPermitsModel;
    }

    public SiteInspectionAdditionalMobileWindPowerModel getSiteInspectionAdditionalMobileWindPowerModel() {
        return siteInspectionAdditionalMobileWindPowerModel;
    }

    public void setSiteInspectionAdditionalMobileWindPowerModel(SiteInspectionAdditionalMobileWindPowerModel siteInspectionAdditionalMobileWindPowerModel) {
        this.siteInspectionAdditionalMobileWindPowerModel = siteInspectionAdditionalMobileWindPowerModel;
    }

    public ArrayList<String> getListOfEmailAddress() {
        return listOfEmailAddress;
    }

    public void setListOfEmailAddress(ArrayList<String> listOfEmailAddress) {
        this.listOfEmailAddress = listOfEmailAddress;
    }

    public String getEmailBodyContent() {
        return emailBodyContent;
    }

    public void setEmailBodyContent(String emailBodyContent) {
        this.emailBodyContent = emailBodyContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.uploadId);
        dest.writeInt(this.flag);
        dest.writeString(this.date);
        dest.writeString(this.emailBodyContent);
        dest.writeParcelable(this.siteInspectionNewModel, flags);
        dest.writeParcelable(this.siteInspectionOperationalEnvironmentModel, 0);
        dest.writeParcelable(this.siteInspectionOperationalDataPermitsModel, 0);
        dest.writeParcelable(this.siteInspectionAdditionalMobileWindPowerModel, 0);
        dest.writeSerializable(this.listOfEmailAddress);
    }

    public SiteInspectionModel() {
    }

    private SiteInspectionModel(Parcel in) {
        this.id = in.readInt();
        this.uploadId = in.readString();
        this.flag = in.readInt();
        this.date = in.readString();
        this.emailBodyContent = in.readString();
        this.siteInspectionNewModel = in.readParcelable(SiteInspectionNewModel.class.getClassLoader());
        this.siteInspectionOperationalEnvironmentModel = in.readParcelable(SiteInspectionOperationalEnvironmentModel.class.getClassLoader());
        this.siteInspectionOperationalDataPermitsModel = in.readParcelable(SiteInspectionOperationalDataPermitsModel.class.getClassLoader());
        this.siteInspectionAdditionalMobileWindPowerModel = in.readParcelable(SiteInspectionAdditionalMobileWindPowerModel.class.getClassLoader());
        this.listOfEmailAddress = (ArrayList<String>) in.readSerializable();
    }

    public static final Creator<SiteInspectionModel> CREATOR = new Creator<SiteInspectionModel>() {
        public SiteInspectionModel createFromParcel(Parcel source) {
            return new SiteInspectionModel(source);
        }

        public SiteInspectionModel[] newArray(int size) {
            return new SiteInspectionModel[size];
        }
    };

    public static void getEmailAddressFromString(String jsonString,ArrayList<String> listOfEmailAddress)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> emailAddressList = gson.fromJson(jsonString, typedValue);
        listOfEmailAddress.addAll(emailAddressList);
    }
}