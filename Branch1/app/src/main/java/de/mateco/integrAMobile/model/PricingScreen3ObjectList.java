package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ShreyashMashru on 07-05-2015.
 */
public class PricingScreen3ObjectList implements Parcelable {
    private String PriceHandlingCheckBox, PriceServiceCheckBox, PriceHaftungsbegrenzungBoxStatus, PriceKalendertaeglichCheckBox;

    private String maut, transportPrice;

    @SerializedName("PriceHAFTBList")
    private ArrayList<PricingHaftbModel> listOfPriceHaftb;

    public String getMaut() {
        return maut;
    }

    public void setMaut(String maut) {
        this.maut = maut;
    }

    public String getTransportPrice() {
        return transportPrice;
    }

    public void setTransportPrice(String transportPrice) {
        this.transportPrice = transportPrice;
    }

    public String getPriceHandlingCheckBox() {
        return PriceHandlingCheckBox;
    }

    public void setPriceHandlingCheckBox(String priceHandlingCheckBox) {
        PriceHandlingCheckBox = priceHandlingCheckBox;
    }

    public String getPriceServiceCheckBox() {
        return PriceServiceCheckBox;
    }

    public void setPriceServiceCheckBox(String priceServiceCheckBox) {
        PriceServiceCheckBox = priceServiceCheckBox;
    }

    public String getPriceHaftungsbegrenzungBoxStatus() {
        return PriceHaftungsbegrenzungBoxStatus;
    }

    public void setPriceHaftungsbegrenzungBoxStatus(String priceHaftungsbegrenzungBoxStatus) {
        PriceHaftungsbegrenzungBoxStatus = priceHaftungsbegrenzungBoxStatus;
    }

    public String getPriceKalendertaeglichCheckBox() {
        return PriceKalendertaeglichCheckBox;
    }

    public void setPriceKalendertaeglichCheckBox(String priceKalendertaeglichCheckBox) {
        PriceKalendertaeglichCheckBox = priceKalendertaeglichCheckBox;
    }

    public ArrayList<PricingHaftbModel> getListOfPriceHaftb() {
        return listOfPriceHaftb;
    }

    public void setListOfPriceHaftb(ArrayList<PricingHaftbModel> listOfPriceHaftb) {
        this.listOfPriceHaftb = listOfPriceHaftb;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PriceHandlingCheckBox);
        dest.writeString(this.PriceServiceCheckBox);
        dest.writeString(this.PriceHaftungsbegrenzungBoxStatus);
        dest.writeString(this.PriceKalendertaeglichCheckBox);
        dest.writeString(this.maut);
        dest.writeString(this.transportPrice);
        dest.writeSerializable(this.listOfPriceHaftb);
    }

    public PricingScreen3ObjectList() {
    }

    private PricingScreen3ObjectList(Parcel in) {
        this.PriceHandlingCheckBox = in.readString();
        this.PriceServiceCheckBox = in.readString();
        this.PriceHaftungsbegrenzungBoxStatus = in.readString();
        this.PriceKalendertaeglichCheckBox = in.readString();
        this.maut = in.readString();
        this.transportPrice = in.readString();
        this.listOfPriceHaftb = (ArrayList<PricingHaftbModel>) in.readSerializable();
    }

    public static final Parcelable.Creator<PricingScreen3ObjectList> CREATOR = new Parcelable.Creator<PricingScreen3ObjectList>() {
        public PricingScreen3ObjectList createFromParcel(Parcel source) {
            return new PricingScreen3ObjectList(source);
        }

        public PricingScreen3ObjectList[] newArray(int size) {
            return new PricingScreen3ObjectList[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<PricingScreen3ObjectList> objects)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<PricingScreen3ObjectList>>(){}.getType();
        ArrayList<PricingScreen3ObjectList> listOfObject = gson.fromJson(jsonString, typedValue);
        objects.addAll(listOfObject);
    }
}
