package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PricingOfflineStandardPriceData implements Parcelable
{
    //private int pricingOfflineStandarPriceId;
    private  String Bezeichnung;
    private String Hoehengruppe;
    private String Hoehenhauptgruppe;
    private int HoehenhauptgruppeID;
    private double Listenpreis;
    private int Mandant;
    private String Niederlassung;
    private double TageM_11_20;
    private double TageM_1_2;
    private double TageM_3_4;
    private double TageM_5_10;
    private double TageR_11_20;
    private double TageR_1_2;
    private double TageR_3_4;
    private double TageR_5_10;
    private double ab21TageM;
    private double ab21TageR;
    private double bis8StundenM;
    private double bis8StundenR;
    private double uber8StundenM;
    private double uber8StundenR;

    private ArrayList<PriceStaffelModel> Key;

    public ArrayList<PriceStaffelModel> getKey() {
        return Key;
    }

    public void setKey(ArrayList<PriceStaffelModel> key) {
        Key = key;
    }

    public ArrayList<Double> getListOfRPrice() {
        return listOfRPrice;
    }

    public void setListOfRPrice(ArrayList<Double> listOfRPrice) {
        this.listOfRPrice = listOfRPrice;
    }

    public ArrayList<Double> getListOfMPrice() {
        return listOfMPrice;
    }

    public void setListOfMPrice(ArrayList<Double> listOfMPrice) {
        this.listOfMPrice = listOfMPrice;
    }

    private ArrayList<Double> listOfRPrice;
    private ArrayList<Double> listOfMPrice;



    public PricingOfflineStandardPriceData() {
    }

    public PricingOfflineStandardPriceData(int pricingOfflineStandarPriceId, String bezeichnung, String hoehengruppe, String hoehenhauptgruppe, int hoehenhauptgruppeID, double listenpreis, int mandant, String niederlassung, double tageM_11_20, double tageM_1_2, double tageM_3_4, double tageM_5_10, double tageR_11_20, double tageR_1_2, double tageR_3_4, double tageR_5_10, double ab21TageM, double ab21TageR, double bis8StundenM, double bis8StundenR, double uber8StundenM, double uber8StundenR) {
        //this.pricingOfflineStandarPriceId = pricingOfflineStandarPriceId;
        Bezeichnung = bezeichnung;
        Hoehengruppe = hoehengruppe;
        Hoehenhauptgruppe = hoehenhauptgruppe;
        HoehenhauptgruppeID = hoehenhauptgruppeID;
        Listenpreis = listenpreis;
        Mandant = mandant;
        Niederlassung = niederlassung;
        TageM_11_20 = tageM_11_20;
        TageM_1_2 = tageM_1_2;
        TageM_3_4 = tageM_3_4;
        TageM_5_10 = tageM_5_10;
        TageR_11_20 = tageR_11_20;
        TageR_1_2 = tageR_1_2;
        TageR_3_4 = tageR_3_4;
        TageR_5_10 = tageR_5_10;
        this.ab21TageM = ab21TageM;
        this.ab21TageR = ab21TageR;
        this.bis8StundenM = bis8StundenM;
        this.bis8StundenR = bis8StundenR;
        this.uber8StundenM = uber8StundenM;
        this.uber8StundenR = uber8StundenR;
    }

    public PricingOfflineStandardPriceData(String bezeichnung, String hoehengruppe, String hoehenhauptgruppe, int hoehenhauptgruppeID, double listenpreis, int mandant, String niederlassung, double tageM_11_20, double tageM_1_2, double tageM_3_4, double tageM_5_10, double tageR_11_20, double tageR_1_2, double tageR_3_4, double tageR_5_10, double ab21TageM, double ab21TageR, double bis8StundenM, double bis8StundenR, double uber8StundenM, double uber8StundenR) {
        Bezeichnung = bezeichnung;
        Hoehengruppe = hoehengruppe;
        Hoehenhauptgruppe = hoehenhauptgruppe;
        HoehenhauptgruppeID = hoehenhauptgruppeID;
        Listenpreis = listenpreis;
        Mandant = mandant;
        Niederlassung = niederlassung;
        TageM_11_20 = tageM_11_20;
        TageM_1_2 = tageM_1_2;
        TageM_3_4 = tageM_3_4;
        TageM_5_10 = tageM_5_10;
        TageR_11_20 = tageR_11_20;
        TageR_1_2 = tageR_1_2;
        TageR_3_4 = tageR_3_4;
        TageR_5_10 = tageR_5_10;
        this.ab21TageM = ab21TageM;
        this.ab21TageR = ab21TageR;
        this.bis8StundenM = bis8StundenM;
        this.bis8StundenR = bis8StundenR;
        this.uber8StundenM = uber8StundenM;
        this.uber8StundenR = uber8StundenR;
    }

//    public int getPricingOfflineStandarPriceId() {
//        return pricingOfflineStandarPriceId;
//    }
//
//    public void setPricingOfflineStandarPriceId(int pricingOfflineStandarPriceId) {
//        this.pricingOfflineStandarPriceId = pricingOfflineStandarPriceId;
//    }

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

    public String getHoehenhauptgruppe() {
        return Hoehenhauptgruppe;
    }

    public void setHoehenhauptgruppe(String hoehenhauptgruppe) {
        Hoehenhauptgruppe = hoehenhauptgruppe;
    }

    public int getHoehenhauptgruppeID() {
        return HoehenhauptgruppeID;
    }

    public void setHoehenhauptgruppeID(int hoehenhauptgruppeID) {
        HoehenhauptgruppeID = hoehenhauptgruppeID;
    }

    public double getListenpreis() {
        return Listenpreis;
    }

    public void setListenpreis(double listenpreis) {
        Listenpreis = listenpreis;
    }

    public int getMandant() {
        return Mandant;
    }

    public void setMandant(int mandant) {
        Mandant = mandant;
    }

    public String getNiederlassung() {
        return Niederlassung;
    }

    public void setNiederlassung(String niederlassung) {
        Niederlassung = niederlassung;
    }

    public double getTageM_11_20() {
        return TageM_11_20;
    }

    public void setTageM_11_20(double tageM_11_20) {
        TageM_11_20 = tageM_11_20;
    }

    public double getTageM_1_2() {
        return TageM_1_2;
    }

    public void setTageM_1_2(double tageM_1_2) {
        TageM_1_2 = tageM_1_2;
    }

    public double getTageM_3_4() {
        return TageM_3_4;
    }

    public void setTageM_3_4(double tageM_3_4) {
        TageM_3_4 = tageM_3_4;
    }

    public double getTageM_5_10() {
        return TageM_5_10;
    }

    public void setTageM_5_10(double tageM_5_10) {
        TageM_5_10 = tageM_5_10;
    }

    public double getTageR_11_20() {
        return TageR_11_20;
    }

    public void setTageR_11_20(double tageR_11_20) {
        TageR_11_20 = tageR_11_20;
    }

    public double getTageR_1_2() {
        return TageR_1_2;
    }

    public void setTageR_1_2(double tageR_1_2) {
        TageR_1_2 = tageR_1_2;
    }

    public double getTageR_3_4() {
        return TageR_3_4;
    }

    public void setTageR_3_4(double tageR_3_4) {
        TageR_3_4 = tageR_3_4;
    }

    public double getTageR_5_10() {
        return TageR_5_10;
    }

    public void setTageR_5_10(double tageR_5_10) {
        TageR_5_10 = tageR_5_10;
    }

    public double getAb21TageM() {
        return ab21TageM;
    }

    public void setAb21TageM(double ab21TageM) {
        this.ab21TageM = ab21TageM;
    }

    public double getAb21TageR() {
        return ab21TageR;
    }

    public void setAb21TageR(double ab21TageR) {
        this.ab21TageR = ab21TageR;
    }

    public double getBis8StundenM() {
        return bis8StundenM;
    }

    public void setBis8StundenM(double bis8StundenM) {
        this.bis8StundenM = bis8StundenM;
    }

    public double getBis8StundenR() {
        return bis8StundenR;
    }

    public void setBis8StundenR(double bis8StundenR) {
        this.bis8StundenR = bis8StundenR;
    }

    public double getUber8StundenM() {
        return uber8StundenM;
    }

    public void setUber8StundenM(double uber8StundenM) {
        this.uber8StundenM = uber8StundenM;
    }

    public double getUber8StundenR() {
        return uber8StundenR;
    }

    public void setUber8StundenR(double uber8StundenR) {
        this.uber8StundenR = uber8StundenR;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeInt(this.pricingOfflineStandarPriceId);
        dest.writeString(this.Bezeichnung);
        dest.writeString(this.Hoehengruppe);
        dest.writeString(this.Hoehenhauptgruppe);
        dest.writeInt(this.HoehenhauptgruppeID);
        dest.writeDouble(this.Listenpreis);
        dest.writeInt(this.Mandant);
        dest.writeString(this.Niederlassung);
        dest.writeDouble(this.TageM_11_20);
        dest.writeDouble(this.TageM_1_2);
        dest.writeDouble(this.TageM_3_4);
        dest.writeDouble(this.TageM_5_10);
        dest.writeDouble(this.TageR_11_20);
        dest.writeDouble(this.TageR_1_2);
        dest.writeDouble(this.TageR_3_4);
        dest.writeDouble(this.TageR_5_10);
        dest.writeDouble(this.ab21TageM);
        dest.writeDouble(this.ab21TageR);
        dest.writeDouble(this.bis8StundenM);
        dest.writeDouble(this.bis8StundenR);
        dest.writeDouble(this.uber8StundenM);
        dest.writeDouble(this.uber8StundenR);
    }

    private PricingOfflineStandardPriceData(Parcel in) {
        //this.pricingOfflineStandarPriceId = in.readInt();
        this.Bezeichnung = in.readString();
        this.Hoehengruppe = in.readString();
        this.Hoehenhauptgruppe = in.readString();
        this.HoehenhauptgruppeID = in.readInt();
        this.Listenpreis = in.readDouble();
        this.Mandant = in.readInt();
        this.Niederlassung = in.readString();
        this.TageM_11_20 = in.readDouble();
        this.TageM_1_2 = in.readDouble();
        this.TageM_3_4 = in.readDouble();
        this.TageM_5_10 = in.readDouble();
        this.TageR_11_20 = in.readDouble();
        this.TageR_1_2 = in.readDouble();
        this.TageR_3_4 = in.readDouble();
        this.TageR_5_10 = in.readDouble();
        this.ab21TageM = in.readDouble();
        this.ab21TageR = in.readDouble();
        this.bis8StundenM = in.readDouble();
        this.bis8StundenR = in.readDouble();
        this.uber8StundenM = in.readDouble();
        this.uber8StundenR = in.readDouble();
    }

    public static final Creator<PricingOfflineStandardPriceData> CREATOR = new Creator<PricingOfflineStandardPriceData>() {
        public PricingOfflineStandardPriceData createFromParcel(Parcel source) {
            return new PricingOfflineStandardPriceData(source);
        }

        public PricingOfflineStandardPriceData[] newArray(int size) {
            return new PricingOfflineStandardPriceData[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<PricingOfflineStandardPriceData> listOfOffLineStandardPrice)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<PricingOfflineStandardPriceData>>(){}.getType();
        ArrayList<PricingOfflineStandardPriceData> listOFPricing = gson.fromJson(jsonString, typedValue);
        listOfOffLineStandardPrice.addAll(listOFPricing);
    }
}
