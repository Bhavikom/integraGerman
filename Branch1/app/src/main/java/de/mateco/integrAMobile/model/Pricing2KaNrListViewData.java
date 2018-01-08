package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pricing2KaNrListViewData implements Parcelable {

    private String Hoehengruppe;
    private Double Listenpreis;
    private String Satzart;
    private String Sort;
    private ArrayList<PriceStaffelModel> Key;
    private ArrayList<Double> listOfRPrice;
    private ArrayList<Double> listOfMPrice;

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

    public String getSatzart() {
        return Satzart;
    }

    public void setSatzart(String satzart) {
        Satzart = satzart;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

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


    public Pricing2KaNrListViewData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Hoehengruppe);
        dest.writeValue(this.Listenpreis);
        dest.writeString(this.Satzart);
        dest.writeString(this.Sort);
        dest.writeSerializable(this.Key);
        dest.writeSerializable(this.listOfRPrice);
        dest.writeSerializable(this.listOfMPrice);
    }

    private Pricing2KaNrListViewData(Parcel in) {
        this.Hoehengruppe = in.readString();
        this.Listenpreis = (Double) in.readValue(Double.class.getClassLoader());
        this.Satzart = in.readString();
        this.Sort = in.readString();
        this.Key = (ArrayList<PriceStaffelModel>) in.readSerializable();
        this.listOfRPrice = (ArrayList<Double>) in.readSerializable();
        this.listOfMPrice = (ArrayList<Double>) in.readSerializable();
    }

    public static final Creator<Pricing2KaNrListViewData> CREATOR = new Creator<Pricing2KaNrListViewData>() {
        public Pricing2KaNrListViewData createFromParcel(Parcel source) {
            return new Pricing2KaNrListViewData(source);
        }

        public Pricing2KaNrListViewData[] newArray(int size) {
            return new Pricing2KaNrListViewData[size];
        }
    };
}
