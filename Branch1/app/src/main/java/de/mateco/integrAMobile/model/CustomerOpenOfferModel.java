package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerOpenOfferModel implements Parcelable {
    private String Kontakt, Region, Mandant, NL, Angebot, Erstellt_am, Geraetegruppe, Hoehengruppe, Geraetetyp, AnzahlGeraete,
            Mietdauer, Staffel, Preis, Vers, Status, EinsatzPLZ, Einsatzort;

    //private String NL, Angebot, Erstellt_am, HGRP, Geraetetyp, Ant, Mietdauer, Staffel, Preis, Haftb, Status;

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getMandant() {
        return Mandant;
    }

    public void setMandant(String mandant) {
        Mandant = mandant;
    }

    public String getNL() {
        return NL;
    }

    public void setNL(String NL) {
        this.NL = NL;
    }

    public String getAngebot() {
        return Angebot;
    }

    public void setAngebot(String angebot) {
        Angebot = angebot;
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

    public String getHoehengruppe() {
        return Hoehengruppe;
    }

    public void setHoehengruppe(String hoehengruppe) {
        Hoehengruppe = hoehengruppe;
    }

    public String getGeraetetyp() {
        return Geraetetyp;
    }

    public void setGeraetetyp(String geraetetyp) {
        Geraetetyp = geraetetyp;
    }

    public String getAnzahlGeraete() {
        return AnzahlGeraete;
    }

    public void setAnzahlGeraete(String anzahlGeraete) {
        AnzahlGeraete = anzahlGeraete;
    }

    public String getMietdauer() {
        return Mietdauer;
    }

    public void setMietdauer(String mietdauer) {
        Mietdauer = mietdauer;
    }

    public String getStaffel() {
        return Staffel;
    }

    public void setStaffel(String staffel) {
        Staffel = staffel;
    }

    public String getPreis() {
        return Preis;
    }

    public void setPreis(String preis) {
        Preis = preis;
    }

    public String getVers() {
        return Vers;
    }

    public void setVers(String vers) {
        Vers = vers;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEinsatzPLZ() {
        return EinsatzPLZ;
    }

    public void setEinsatzPLZ(String einsatzPLZ) {
        EinsatzPLZ = einsatzPLZ;
    }

    public String getEinsatzort() {
        return Einsatzort;
    }

    public void setEinsatzort(String einsatzort) {
        Einsatzort = einsatzort;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Kontakt);
        dest.writeString(this.Region);
        dest.writeString(this.Mandant);
        dest.writeString(this.NL);
        dest.writeString(this.Angebot);
        dest.writeString(this.Erstellt_am);
        dest.writeString(this.Geraetegruppe);
        dest.writeString(this.Hoehengruppe);
        dest.writeString(this.Geraetetyp);
        dest.writeString(this.AnzahlGeraete);
        dest.writeString(this.Mietdauer);
        dest.writeString(this.Staffel);
        dest.writeString(this.Preis);
        dest.writeString(this.Vers);
        dest.writeString(this.Status);
        dest.writeString(this.EinsatzPLZ);
        dest.writeString(this.Einsatzort);
    }

    public CustomerOpenOfferModel() {
    }

    private CustomerOpenOfferModel(Parcel in) {
        this.Kontakt = in.readString();
        this.Region = in.readString();
        this.Mandant = in.readString();
        this.NL = in.readString();
        this.Angebot = in.readString();
        this.Erstellt_am = in.readString();
        this.Geraetegruppe = in.readString();
        this.Hoehengruppe = in.readString();
        this.Geraetetyp = in.readString();
        this.AnzahlGeraete = in.readString();
        this.Mietdauer = in.readString();
        this.Staffel = in.readString();
        this.Preis = in.readString();
        this.Vers = in.readString();
        this.Status = in.readString();
        this.EinsatzPLZ = in.readString();
        this.Einsatzort = in.readString();
    }

    public static final Parcelable.Creator<CustomerOpenOfferModel> CREATOR = new Parcelable.Creator<CustomerOpenOfferModel>() {
        public CustomerOpenOfferModel createFromParcel(Parcel source) {
            return new CustomerOpenOfferModel(source);
        }

        public CustomerOpenOfferModel[] newArray(int size) {
            return new CustomerOpenOfferModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<CustomerOpenOfferModel> openSpecials)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<CustomerOpenOfferModel>>(){}.getType();
        ArrayList<CustomerOpenOfferModel> listOfOpenSpecials = gson.fromJson(jsonString, typedValue);
        openSpecials.addAll(listOfOpenSpecials);
    }
}
