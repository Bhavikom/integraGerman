package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerLostSaleDataModel implements Parcelable {
    private String Absagegrund, Erstellt_am, Geraetegruppe, Geraetetyp, Hohengruppe, Kontakt, Mandant, Mietdauer, Mietpreis, NL,
            Region, Versicherung;

    //private String NL, Erstellt_am, HGRP, Geraetetyp, MD, Mietpreis, Haft, Absagegrund;

    public String getAbsagegrund() {
        return Absagegrund;
    }

    public void setAbsagegrund(String absagegrund) {
        Absagegrund = absagegrund;
    }

    public String getErstellt_am() {
        return Erstellt_am;
    }

    public void setErstellt_am(String erstellt_am) {
        Erstellt_am = erstellt_am;
    }

    public String getGeraetegruppe() {
        return Geraetegruppe;
    }

    public void setGeraetegruppe(String geraetegruppe) {
        Geraetegruppe = geraetegruppe;
    }

    public String getGeraetetyp() {
        return Geraetetyp;
    }

    public void setGeraetetyp(String geraetetyp) {
        Geraetetyp = geraetetyp;
    }

    public String getHohengruppe() {
        return Hohengruppe;
    }

    public void setHohengruppe(String hohengruppe) {
        Hohengruppe = hohengruppe;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getMandant() {
        return Mandant;
    }

    public void setMandant(String mandant) {
        Mandant = mandant;
    }

    public String getMietdauer() {
        return Mietdauer;
    }

    public void setMietdauer(String mietdauer) {
        Mietdauer = mietdauer;
    }

    public String getMietpreis() {
        return Mietpreis;
    }

    public void setMietpreis(String mietpreis) {
        Mietpreis = mietpreis;
    }

    public String getNL() {
        return NL;
    }

    public void setNL(String NL) {
        this.NL = NL;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getVersicherung() {
        return Versicherung;
    }

    public void setVersicherung(String versicherung) {
        Versicherung = versicherung;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Absagegrund);
        dest.writeString(this.Erstellt_am);
        dest.writeString(this.Geraetegruppe);
        dest.writeString(this.Geraetetyp);
        dest.writeString(this.Hohengruppe);
        dest.writeString(this.Kontakt);
        dest.writeString(this.Mandant);
        dest.writeString(this.Mietdauer);
        dest.writeString(this.Mietpreis);
        dest.writeString(this.NL);
        dest.writeString(this.Region);
        dest.writeString(this.Versicherung);
    }

    public CustomerLostSaleDataModel() {
    }

    private CustomerLostSaleDataModel(Parcel in) {
        this.Absagegrund = in.readString();
        this.Erstellt_am = in.readString();
        this.Geraetegruppe = in.readString();
        this.Geraetetyp = in.readString();
        this.Hohengruppe = in.readString();
        this.Kontakt = in.readString();
        this.Mandant = in.readString();
        this.Mietdauer = in.readString();
        this.Mietpreis = in.readString();
        this.NL = in.readString();
        this.Region = in.readString();
        this.Versicherung = in.readString();
    }

    public static final Parcelable.Creator<CustomerLostSaleDataModel> CREATOR = new Parcelable.Creator<CustomerLostSaleDataModel>() {
        public CustomerLostSaleDataModel createFromParcel(Parcel source) {
            return new CustomerLostSaleDataModel(source);
        }

        public CustomerLostSaleDataModel[] newArray(int size) {
            return new CustomerLostSaleDataModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<CustomerLostSaleDataModel> listOfLostSale)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<CustomerLostSaleDataModel>>(){}.getType();
        ArrayList<CustomerLostSaleDataModel> lostSales = gson.fromJson(jsonString, typedValue);
        listOfLostSale.addAll(lostSales);
    }
}
