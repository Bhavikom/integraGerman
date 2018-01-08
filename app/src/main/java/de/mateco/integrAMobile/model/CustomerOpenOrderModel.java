package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerOpenOrderModel implements Parcelable
{
    private String AuftragNr, Einheit, EinsatzPLZ, Einsatzort, Gel_Gerat, Geraetegruppe, Hohengruppe, KaNr, KaP, KundenNr, LP, Mandant,
            Mietbeginn, Mietdauer, Mietende, NL, Region, Selbstbehalt, TP, Vers_LP, Vers_TP, Wunschgerat;

//    private String AuftragNr, NL, KaNr, HGRP, Wunschgerat, Gel_Gerat, Mietbeginn, MD, Einheit, LP, KaP, TP, SB, Haft_LP, Haft_TP,
//            EinsatzPLZ, Einsatzort;

    public String getAuftragNr() {
        return AuftragNr;
    }

    public void setAuftragNr(String auftragNr) {
        AuftragNr = auftragNr;
    }

    public String getEinheit() {
        return Einheit;
    }

    public void setEinheit(String einheit) {
        Einheit = einheit;
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

    public String getGel_Gerat() {
        return Gel_Gerat;
    }

    public void setGel_Gerat(String gel_Gerat) {
        Gel_Gerat = gel_Gerat;
    }

    public String getGeraetegruppe() {
        return Geraetegruppe;
    }

    public void setGeraetegruppe(String geraetegruppe) {
        Geraetegruppe = geraetegruppe;
    }

    public String getHohengruppe() {
        return Hohengruppe;
    }

    public void setHohengruppe(String hohengruppe) {
        Hohengruppe = hohengruppe;
    }

    public String getKaNr() {
        return KaNr;
    }

    public void setKaNr(String kaNr) {
        KaNr = kaNr;
    }

    public String getKaP() {
        return KaP;
    }

    public void setKaP(String kaP) {
        KaP = kaP;
    }

    public String getKundenNr() {
        return KundenNr;
    }

    public void setKundenNr(String kundenNr) {
        KundenNr = kundenNr;
    }

    public String getLP() {
        return LP;
    }

    public void setLP(String LP) {
        this.LP = LP;
    }

    public String getMandant() {
        return Mandant;
    }

    public void setMandant(String mandant) {
        Mandant = mandant;
    }

    public String getMietbeginn() {
        return Mietbeginn;
    }

    public void setMietbeginn(String mietbeginn) {
        Mietbeginn = mietbeginn;
    }

    public String getMietdauer() {
        return Mietdauer;
    }

    public void setMietdauer(String mietdauer) {
        Mietdauer = mietdauer;
    }

    public String getMietende() {
        return Mietende;
    }

    public void setMietende(String mietende) {
        Mietende = mietende;
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

    public String getSelbstbehalt() {
        return Selbstbehalt;
    }

    public void setSelbstbehalt(String selbstbehalt) {
        Selbstbehalt = selbstbehalt;
    }

    public String getTP() {
        return TP;
    }

    public void setTP(String TP) {
        this.TP = TP;
    }

    public String getVers_LP() {
        return Vers_LP;
    }

    public void setVers_LP(String vers_LP) {
        Vers_LP = vers_LP;
    }

    public String getVers_TP() {
        return Vers_TP;
    }

    public void setVers_TP(String vers_TP) {
        Vers_TP = vers_TP;
    }

    public String getWunschgerat() {
        return Wunschgerat;
    }

    public void setWunschgerat(String wunschgerat) {
        Wunschgerat = wunschgerat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AuftragNr);
        dest.writeString(this.Einheit);
        dest.writeString(this.EinsatzPLZ);
        dest.writeString(this.Einsatzort);
        dest.writeString(this.Gel_Gerat);
        dest.writeString(this.Geraetegruppe);
        dest.writeString(this.Hohengruppe);
        dest.writeString(this.KaNr);
        dest.writeString(this.KaP);
        dest.writeString(this.KundenNr);
        dest.writeString(this.LP);
        dest.writeString(this.Mandant);
        dest.writeString(this.Mietbeginn);
        dest.writeString(this.Mietdauer);
        dest.writeString(this.Mietende);
        dest.writeString(this.NL);
        dest.writeString(this.Region);
        dest.writeString(this.Selbstbehalt);
        dest.writeString(this.TP);
        dest.writeString(this.Vers_LP);
        dest.writeString(this.Vers_TP);
        dest.writeString(this.Wunschgerat);
    }

    public CustomerOpenOrderModel() {
    }

    private CustomerOpenOrderModel(Parcel in) {
        this.AuftragNr = in.readString();
        this.Einheit = in.readString();
        this.EinsatzPLZ = in.readString();
        this.Einsatzort = in.readString();
        this.Gel_Gerat = in.readString();
        this.Geraetegruppe = in.readString();
        this.Hohengruppe = in.readString();
        this.KaNr = in.readString();
        this.KaP = in.readString();
        this.KundenNr = in.readString();
        this.LP = in.readString();
        this.Mandant = in.readString();
        this.Mietbeginn = in.readString();
        this.Mietdauer = in.readString();
        this.Mietende = in.readString();
        this.NL = in.readString();
        this.Region = in.readString();
        this.Selbstbehalt = in.readString();
        this.TP = in.readString();
        this.Vers_LP = in.readString();
        this.Vers_TP = in.readString();
        this.Wunschgerat = in.readString();
    }

    public static final Parcelable.Creator<CustomerOpenOrderModel> CREATOR = new Parcelable.Creator<CustomerOpenOrderModel>() {
        public CustomerOpenOrderModel createFromParcel(Parcel source) {
            return new CustomerOpenOrderModel(source);
        }

        public CustomerOpenOrderModel[] newArray(int size) {
            return new CustomerOpenOrderModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<CustomerOpenOrderModel> customerOpenOrders)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<CustomerOpenOrderModel>>(){}.getType();
        ArrayList<CustomerOpenOrderModel> listOfOpenOrder = gson.fromJson(jsonString, typedValue);
        customerOpenOrders.addAll(listOfOpenOrder);
    }
}
