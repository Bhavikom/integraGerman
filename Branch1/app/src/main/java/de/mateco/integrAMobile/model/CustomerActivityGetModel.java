package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerActivityGetModel implements Parcelable {

    public String getNeedToAddNewActivity() {
        return needToAddNewActivity;
    }

    public void setNeedToAddNewActivity(String needToAddNewActivity) {
        this.needToAddNewActivity = needToAddNewActivity;
    }

    @SerializedName("NeedToAddNewActivity")
    String needToAddNewActivity="";

    @SerializedName("CustomerActivityList")
    private ArrayList<CustomerActivityModel> listOfActivities;
    @SerializedName("OfferList")
    private ArrayList<CustomerOfferModel> listOfOffers;
    @SerializedName("ProjectList")
    private ArrayList<CustomerProjectModel> listOfProject;

    public ArrayList<CustomerActivityModel> getListOfActivities() {
        return listOfActivities;
    }

    public void setListOfActivities(ArrayList<CustomerActivityModel> listOfActivities) {
        this.listOfActivities = listOfActivities;
    }

    public ArrayList<CustomerOfferModel> getListOfOffers() {
        return listOfOffers;
    }

    public void setListOfOffers(ArrayList<CustomerOfferModel> listOfOffers) {
        this.listOfOffers = listOfOffers;
    }

    public ArrayList<CustomerProjectModel> getListOfProject() {
        return listOfProject;
    }

    public void setListOfProject(ArrayList<CustomerProjectModel> listOfProject) {
        this.listOfProject = listOfProject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.listOfActivities);
        dest.writeSerializable(this.listOfOffers);
        dest.writeSerializable(this.listOfProject);
        dest.writeSerializable(this.needToAddNewActivity);
    }

    public CustomerActivityGetModel() {
    }

    private CustomerActivityGetModel(Parcel in) {
        this.listOfActivities = (ArrayList<CustomerActivityModel>) in.readSerializable();
        this.listOfOffers = (ArrayList<CustomerOfferModel>) in.readSerializable();
        this.listOfProject = (ArrayList<CustomerProjectModel>) in.readSerializable();
        this.needToAddNewActivity = (String) in.readSerializable();
    }

    public static final Parcelable.Creator<CustomerActivityGetModel> CREATOR = new Parcelable.Creator<CustomerActivityGetModel>() {
        public CustomerActivityGetModel createFromParcel(Parcel source) {
            return new CustomerActivityGetModel(source);
        }

        public CustomerActivityGetModel[] newArray(int size) {
            return new CustomerActivityGetModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<CustomerActivityGetModel> listOfResult)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<CustomerActivityGetModel>>(){}.getType();
        ArrayList<CustomerActivityGetModel> activityList = gson.fromJson(jsonString, typedValue);
        listOfResult.addAll(activityList);
    }
}
