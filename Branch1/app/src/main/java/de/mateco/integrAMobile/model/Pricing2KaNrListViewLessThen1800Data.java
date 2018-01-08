package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pricing2KaNrListViewLessThen1800Data implements Parcelable {



    private String Arbeitshoehe;
    private String Gerätetyp;
    private String Hoehengruppe;
    private Double Listenpreis;
    private ArrayList<PriceStaffelModel> Key;
    private ArrayList<Double> listPrice;

    public Pricing2KaNrListViewLessThen1800Data() {
    }


    public String getArbeitshoehe() {
        return Arbeitshoehe;
    }

    public void setArbeitshoehe(String arbeitshoehe) {
        Arbeitshoehe = arbeitshoehe;
    }

    public String getGerätetyp() {
        return Gerätetyp;
    }

    public void setGerätetyp(String gerätetyp) {
        Gerätetyp = gerätetyp;
    }

    public String getHoehengruppe() {
        return Hoehengruppe;
    }

    public void setHoehengruppe(String hoehengruppe) {
        Hoehengruppe = hoehengruppe;
    }

    public Double getListenpreis() {
        return Listenpreis;
    }

    public void setListenpreis(Double listenpreis) {
        Listenpreis = listenpreis;
    }

    public ArrayList<PriceStaffelModel> getKey() {
        return Key;
    }

    public void setKey(ArrayList<PriceStaffelModel> key) {
        Key = key;
    }

    public ArrayList<Double> getListPrice() {
        return listPrice;
    }

    public void setListPrice(ArrayList<Double> listPrice) {
        this.listPrice = listPrice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Arbeitshoehe);
        dest.writeString(this.Gerätetyp);
        dest.writeString(this.Hoehengruppe);
        dest.writeValue(this.Listenpreis);
        dest.writeSerializable(this.Key);
        dest.writeSerializable(this.listPrice);
    }

    private Pricing2KaNrListViewLessThen1800Data(Parcel in) {
        this.Arbeitshoehe = in.readString();
        this.Gerätetyp = in.readString();
        this.Hoehengruppe = in.readString();
        this.Listenpreis = (Double) in.readValue(Double.class.getClassLoader());
        this.Key = (ArrayList<PriceStaffelModel>) in.readSerializable();
        this.listPrice = (ArrayList<Double>) in.readSerializable();
    }

    public static final Creator<Pricing2KaNrListViewLessThen1800Data> CREATOR = new Creator<Pricing2KaNrListViewLessThen1800Data>() {
        public Pricing2KaNrListViewLessThen1800Data createFromParcel(Parcel source) {
            return new Pricing2KaNrListViewLessThen1800Data(source);
        }

        public Pricing2KaNrListViewLessThen1800Data[] newArray(int size) {
            return new Pricing2KaNrListViewLessThen1800Data[size];
        }
    };
}
