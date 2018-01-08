package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pricing2KaNrListViewMoreThen1800Data implements Parcelable {

    private String Hoehengruppe;
    private Double Listenpreis;
    private String Sort;
    private ArrayList<PriceStaffelModel> Key;
    private ArrayList<Double> listPrice;


    public Pricing2KaNrListViewMoreThen1800Data() {
    }

    public Pricing2KaNrListViewMoreThen1800Data(String hoehengruppe, Double listenpreis, String sort, ArrayList<PriceStaffelModel> key, ArrayList<Double> listPrice) {
        Hoehengruppe = hoehengruppe;
        Listenpreis = listenpreis;
        Sort = sort;
        Key = key;
        this.listPrice = listPrice;
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
        dest.writeString(this.Hoehengruppe);
        dest.writeValue(this.Listenpreis);
        dest.writeString(this.Sort);
        dest.writeSerializable(this.Key);
        dest.writeSerializable(this.listPrice);
    }

    private Pricing2KaNrListViewMoreThen1800Data(Parcel in) {
        this.Hoehengruppe = in.readString();
        this.Listenpreis = (Double) in.readValue(Double.class.getClassLoader());
        this.Sort = in.readString();
        this.Key = (ArrayList<PriceStaffelModel>) in.readSerializable();
        this.listPrice = (ArrayList<Double>) in.readSerializable();
    }

    public static final Creator<Pricing2KaNrListViewMoreThen1800Data> CREATOR = new Creator<Pricing2KaNrListViewMoreThen1800Data>() {
        public Pricing2KaNrListViewMoreThen1800Data createFromParcel(Parcel source) {
            return new Pricing2KaNrListViewMoreThen1800Data(source);
        }

        public Pricing2KaNrListViewMoreThen1800Data[] newArray(int size) {
            return new Pricing2KaNrListViewMoreThen1800Data[size];
        }
    };
}
