package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PricingOfflineEquipmentData implements Parcelable {

    private  String Ausstattung;
    private String Bezeichnung;
    private String Hoehengruppe;
    private int Hoehenhauptgruppe;
    @SerializedName("bezeichnung")
    private String bezeichnung_1;

    public PricingOfflineEquipmentData() {
    }

    public PricingOfflineEquipmentData(String ausstattung, String bezeichnung, String hoehengruppe, int hoehenhauptgruppe, String bezeichnung_1) {
        Ausstattung = ausstattung;
        Bezeichnung = bezeichnung;
        Hoehengruppe = hoehengruppe;
        Hoehenhauptgruppe = hoehenhauptgruppe;
        this.bezeichnung_1 = bezeichnung_1;
    }

    public String getAusstattung() {
        return Ausstattung;
    }

    public void setAusstattung(String ausstattung) {
        Ausstattung = ausstattung;
    }

    public String getBezeichnung() {
        return Bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        Bezeichnung = bezeichnung;
    }

    public String getHoehengruppe() {
        return Hoehengruppe;
    }

    public void setHoehengruppe(String hoehengruppe) {
        Hoehengruppe = hoehengruppe;
    }

    public int getHoehenhauptgruppe() {
        return Hoehenhauptgruppe;
    }

    public void setHoehenhauptgruppe(int hoehenhauptgruppe) {
        Hoehenhauptgruppe = hoehenhauptgruppe;
    }

    public String getBezeichnung_1() {
        return bezeichnung_1;
    }

    public void setBezeichnung_1(String bezeichnung_1) {
        this.bezeichnung_1 = bezeichnung_1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Ausstattung);
        dest.writeString(this.Bezeichnung);
        dest.writeString(this.Hoehengruppe);
        dest.writeInt(this.Hoehenhauptgruppe);
        dest.writeString(this.bezeichnung_1);
    }

    private PricingOfflineEquipmentData(Parcel in) {
        this.Ausstattung = in.readString();
        this.Bezeichnung = in.readString();
        this.Hoehengruppe = in.readString();
        this.Hoehenhauptgruppe = in.readInt();
        this.bezeichnung_1 = in.readString();
    }

    public static final Creator<PricingOfflineEquipmentData> CREATOR = new Creator<PricingOfflineEquipmentData>() {
        public PricingOfflineEquipmentData createFromParcel(Parcel source) {
            return new PricingOfflineEquipmentData(source);
        }

        public PricingOfflineEquipmentData[] newArray(int size) {
            return new PricingOfflineEquipmentData[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<PricingOfflineEquipmentData> listOfOffLineEquipment)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<PricingOfflineEquipmentData>>(){}.getType();
        ArrayList<PricingOfflineEquipmentData> listOFPricingEquipment = gson.fromJson(jsonString, typedValue);
        listOfOffLineEquipment.addAll(listOFPricingEquipment);
    }
}
