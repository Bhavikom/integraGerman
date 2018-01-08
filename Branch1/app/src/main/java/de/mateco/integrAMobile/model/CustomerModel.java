package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerModel implements Parcelable
{
    private String ADM;
    private String Auftragsbestand;
    private String Boni_Index;
    private String Branche;
    private String EMAil;
    private String Erfassungsdatum;
    private String KaNr;
    private String Gebiet;
    private String Kontakt;
    private String KundenNr;
    private String MatchCode;
    private String Name1;
    private String Ort;
    private String PLZ;
    private String Strasse;
    private String Telefon;
    private String Gesamt_OP;
    private String Inhaber;
    private String Umsatz_LFD;
    private String Umsatz_Vorjahr;
    private String Umsatz_12_Monate;
    private String Umsatzpotenzial;
    private String Kreditlimit;
    private String Land;
    private String Name2;
    private String Name3;
    private String Rechtsform;
    private String Telefax;
    private String USTIDNR;
    private String Website;
    private String Zahlungsziel;
    private String JahresbestellNr;
    private String Boni_Datum;
    private String KA_Umsatz_LFD;
    private String KA_Umsatz_Vorjahr;
    private String KA_Umsatz_12_Monate;

    public String getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }

    private String Mitarbeiter;

    public CustomerModel() {
    }

//    public CustomerModel(JSONObject jsonObject)
//    {
//        try
//        {
//            this.ADM = jsonObject.getString("ADM");
//            this.Auftragsbestand = jsonObject.getString("Auftragsbestand");
//            this.Boni_Index = jsonObject.getString("Boni_Index");
//            this.Branche = jsonObject.getString("Branche");
//            this.EMAil = jsonObject.getString("EMAil");
//            this.Erfassungsdatum = jsonObject.getString("Erfassungsdatum");
//            this.Gebiet = jsonObject.getString("Gebiet");
//            this.Gesamt_OP = jsonObject.getString("Gesamt_OP");
//            this.Inhaber = jsonObject.getString("Inhaber");
//            this.JahresbestellNr = jsonObject.getString("JahresbestellNr");
//            this.KaNr = jsonObject.getString("KaNr");
//            this.Kreditlimit = jsonObject.getString("Kreditlimit");
//            this.Kontakt = jsonObject.getString("Kontakt");
//            this.KundenNr = jsonObject.getString("KundenNr");
//            this.Land = jsonObject.getString("Land");
//            this.MatchCode = jsonObject.getString("MatchCode");
//            this.Name1 = jsonObject.getString("Name1");
//            this.Name2 = jsonObject.getString("Name2");
//            this.Name3 = jsonObject.getString("Name3");
//            this.Ort = jsonObject.getString("Ort");
//            this.PLZ = jsonObject.getString("PLZ");
//            this.Rechtsform = jsonObject.getString("Rechtsform");
//            this.Strasse = jsonObject.getString("Strasse");
//            this.Telefax = jsonObject.getString("Telefax");
//            this.Telefon = jsonObject.getString("Telefon");
//            this.USTIDNR = jsonObject.getString("USTIDNR");
//            this.Umsatz_12_Monate = jsonObject.getString("Umsatz_12_Monate");
//            this.Umsatz_LFD = jsonObject.getString("Umsatz_LFD");
//            this.Umsatz_Vorjahr = jsonObject.getString("Umsatz_Vorjahr");
//            this.Umsatzpotenzial = jsonObject.getString("Umsatzpotenzial");
//            this.Website = jsonObject.getString("Website");
//            this.Zahlungsziel = jsonObject.getString("Zahlungsziel");
//            this.Boni_Datum = jsonObject.getString("Boni_Datum");
//            this.KA_Umsatz_LFD = jsonObject.getString("KA_Umsatz_LFD");
//            this.KA_Umsatz_Vorjahr = jsonObject.getString("KA_Umsatz_Vorjahr");
//            this.KA_Umsatz_12_Monate = jsonObject.getString("KA_Umsatz_12_Monate");
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//    }

    public String getADM() {
        return ADM;
    }

    public void setADM(String ADM) {
        this.ADM = ADM;
    }

    public String getAuftragsbestand() {
        return Auftragsbestand;
    }

    public void setAuftragsbestand(String auftragsbestand) {
        Auftragsbestand = auftragsbestand;
    }

    public String getBoni_Index() {
        return Boni_Index;
    }

    public void setBoni_Index(String boni_Index) {
        Boni_Index = boni_Index;
    }

    public String getBranche() {
        return Branche;
    }

    public void setBranche(String branche) {
        Branche = branche;
    }

    public String getEMAil() {
        return EMAil;
    }

    public void setEMAil(String EMAil) {
        this.EMAil = EMAil;
    }

    public String getErfassungsdatum() {
        return Erfassungsdatum;
    }

    public void setErfassungsdatum(String erfassungsdatum) {
        Erfassungsdatum = erfassungsdatum;
    }

    public String getKaNr() {
        return KaNr;
    }

    public void setKaNr(String kaNr) {
        KaNr = kaNr;
    }

    public String getGebiet() {
        return Gebiet;
    }

    public void setGebiet(String gebiet) {
        Gebiet = gebiet;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getKundenNr() {
        return KundenNr;
    }

    public void setKundenNr(String kundenNr) {
        KundenNr = kundenNr;
    }

    public String getMatchCode() {
        return MatchCode;
    }

    public void setMatchCode(String matchCode) {
        MatchCode = matchCode;
    }

    public String getName1() {
        return Name1;
    }

    public void setName1(String name1) {
        Name1 = name1;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String strasse) {
        Strasse = strasse;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getGesamt_OP() {
        return Gesamt_OP;
    }

    public void setGesamt_OP(String gesamt_OP) {
        Gesamt_OP = gesamt_OP;
    }

    public String getInhaber() {
        return Inhaber;
    }

    public void setInhaber(String inhaber) {
        Inhaber = inhaber;
    }

    public String getUmsatz_LFD() {
        return Umsatz_LFD;
    }

    public void setUmsatz_LFD(String umsatz_LFD) {
        Umsatz_LFD = umsatz_LFD;
    }

    public String getUmsatz_Vorjahr() {
        return Umsatz_Vorjahr;
    }

    public void setUmsatz_Vorjahr(String umsatz_Vorjahr) {
        Umsatz_Vorjahr = umsatz_Vorjahr;
    }

    public String getUmsatz_12_Monate() {
        return Umsatz_12_Monate;
    }

    public void setUmsatz_12_Monate(String umsatz_12_Monate) {
        Umsatz_12_Monate = umsatz_12_Monate;
    }

    public String getUmsatzpotenzial() {
        return Umsatzpotenzial;
    }

    public void setUmsatzpotenzial(String umsatzpotenzial) {
        Umsatzpotenzial = umsatzpotenzial;
    }

    public String getKreditlimit() {
        return Kreditlimit;
    }

    public void setKreditlimit(String kreditlimit) {
        Kreditlimit = kreditlimit;
    }

    public String getLand() {
        return Land;
    }

    public void setLand(String land) {
        Land = land;
    }

    public String getName2() {
        return Name2;
    }

    public void setName2(String name2) {
        Name2 = name2;
    }

    public String getName3() {
        return Name3;
    }

    public void setName3(String name3) {
        Name3 = name3;
    }

    public String getRechtsform() {
        return Rechtsform;
    }

    public void setRechtsform(String rechtsform) {
        Rechtsform = rechtsform;
    }

    public String getTelefax() {
        return Telefax;
    }

    public void setTelefax(String telefax) {
        Telefax = telefax;
    }

    public String getUSTIDNR() {
        return USTIDNR;
    }

    public void setUSTIDNR(String USTIDNR) {
        this.USTIDNR = USTIDNR;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getZahlungsziel() {
        return Zahlungsziel;
    }

    public void setZahlungsziel(String zahlungsziel) {
        Zahlungsziel = zahlungsziel;
    }

    public String getJahresbestellNr() {
        return JahresbestellNr;
    }

    public void setJahresbestellNr(String jahresbestellNr) {
        JahresbestellNr = jahresbestellNr;
    }

    public String getBoni_Datum() {
        return Boni_Datum;
    }

    public void setBoni_Datum(String boni_Datum) {
        Boni_Datum = boni_Datum;
    }

    public String getKA_Umsatz_LFD() {
        return KA_Umsatz_LFD;
    }

    public void setKA_Umsatz_LFD(String KA_Umsatz_LFD) {
        this.KA_Umsatz_LFD = KA_Umsatz_LFD;
    }

    public String getKA_Umsatz_Vorjahr() {
        return KA_Umsatz_Vorjahr;
    }

    public void setKA_Umsatz_Vorjahr(String KA_Umsatz_Vorjahr) {
        this.KA_Umsatz_Vorjahr = KA_Umsatz_Vorjahr;
    }

    public String getKA_Umsatz_12_Monate() {
        return KA_Umsatz_12_Monate;
    }

    public void setKA_Umsatz_12_Monate(String KA_Umsatz_12_Monate) {
        this.KA_Umsatz_12_Monate = KA_Umsatz_12_Monate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ADM);
        dest.writeString(this.Auftragsbestand);
        dest.writeString(this.Boni_Index);
        dest.writeString(this.Branche);
        dest.writeString(this.EMAil);
        dest.writeString(this.Erfassungsdatum);
        dest.writeString(this.KaNr);
        dest.writeString(this.Gebiet);
        dest.writeString(this.Kontakt);
        dest.writeString(this.KundenNr);
        dest.writeString(this.MatchCode);
        dest.writeString(this.Name1);
        dest.writeString(this.Ort);
        dest.writeString(this.PLZ);
        dest.writeString(this.Strasse);
        dest.writeString(this.Telefon);
        dest.writeString(this.Gesamt_OP);
        dest.writeString(this.Inhaber);
        dest.writeString(this.Umsatz_LFD);
        dest.writeString(this.Umsatz_Vorjahr);
        dest.writeString(this.Umsatz_12_Monate);
        dest.writeString(this.Umsatzpotenzial);
        dest.writeString(this.Kreditlimit);
        dest.writeString(this.Land);
        dest.writeString(this.Name2);
        dest.writeString(this.Name3);
        dest.writeString(this.Rechtsform);
        dest.writeString(this.Telefax);
        dest.writeString(this.USTIDNR);
        dest.writeString(this.Website);
        dest.writeString(this.Zahlungsziel);
        dest.writeString(this.JahresbestellNr);
        dest.writeString(this.Boni_Datum);
        dest.writeString(this.KA_Umsatz_LFD);
        dest.writeString(this.KA_Umsatz_Vorjahr);
        dest.writeString(this.KA_Umsatz_12_Monate);
        dest.writeString(this.Mitarbeiter);
    }

    private CustomerModel(Parcel in) {
        this.ADM = in.readString();
        this.Auftragsbestand = in.readString();
        this.Boni_Index = in.readString();
        this.Branche = in.readString();
        this.EMAil = in.readString();
        this.Erfassungsdatum = in.readString();
        this.KaNr = in.readString();
        this.Gebiet = in.readString();
        this.Kontakt = in.readString();
        this.KundenNr = in.readString();
        this.MatchCode = in.readString();
        this.Name1 = in.readString();
        this.Ort = in.readString();
        this.PLZ = in.readString();
        this.Strasse = in.readString();
        this.Telefon = in.readString();
        this.Gesamt_OP = in.readString();
        this.Inhaber = in.readString();
        this.Umsatz_LFD = in.readString();
        this.Umsatz_Vorjahr = in.readString();
        this.Umsatz_12_Monate = in.readString();
        this.Umsatzpotenzial = in.readString();
        this.Kreditlimit = in.readString();
        this.Land = in.readString();
        this.Name2 = in.readString();
        this.Name3 = in.readString();
        this.Rechtsform = in.readString();
        this.Telefax = in.readString();
        this.USTIDNR = in.readString();
        this.Website = in.readString();
        this.Zahlungsziel = in.readString();
        this.JahresbestellNr = in.readString();
        this.Boni_Datum = in.readString();
        this.KA_Umsatz_LFD = in.readString();
        this.KA_Umsatz_Vorjahr = in.readString();
        this.KA_Umsatz_12_Monate = in.readString();
        this.Mitarbeiter = in.readString();
    }

    public static final Creator<CustomerModel> CREATOR = new Creator<CustomerModel>() {
        public CustomerModel createFromParcel(Parcel source) {
            return new CustomerModel(source);
        }

        public CustomerModel[] newArray(int size) {
            return new CustomerModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<CustomerModel> countries)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<CustomerModel>>(){}.getType();
        ArrayList<CustomerModel> customerList = gson.fromJson(jsonString, typedValue);
        countries.addAll(customerList);
    }
}
