package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by S Soft on 13-Sep-17.
 */

public class ContactPersonSearchModel implements Parcelable {
    private String KundenNr;
    private String KaNr;
    private String MatchCode;
    private String Name1;
    private String Strasse;
    private String PLZ;
    private String Ort;
    private String Telefon;
    private String PageNumber;
    private String PageSize;
    private String UstIDNr;
    private String Anrede;

    private String ADM;
    private String Auftragsbestand;
    private String Boni_Index;
    private String Branche;
    private String EMAil;
    private String Erfassungsdatum;
    private String Gebiet;
    private String Kontakt;
    private String Gesamt_OP;
    private String Inhaber;
    private String Umsatz_LFD;
    private String Umsatz_Vorjahr;
    private String Umsatz_12_Monate;
    private String AnsPartnerId;

    public String getAnsPartnerId() {
        return AnsPartnerId;
    }

    public void setAnsPartnerId(String ansPartnerId) {
        AnsPartnerId = ansPartnerId;
    }

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

    public String getUstIDNr() {
        return UstIDNr;
    }

    public void setUstIDNr(String ustIDNr) {
        UstIDNr = ustIDNr;
    }

    public String getAnrede() {
        return Anrede;
    }

    public void setAnrede(String anrede) {
        Anrede = anrede;
    }

    public String getVorName() {
        return VorName;
    }

    public void setVorName(String vorName) {
        VorName = vorName;
    }

    public String getNachName() {
        return NachName;
    }

    public void setNachName(String nachName) {
        NachName = nachName;
    }

    public String getAnspTelefon() {
        return AnspTelefon;
    }

    public void setAnspTelefon(String anspTelefon) {
        AnspTelefon = anspTelefon;
    }

    private String VorName;
    private String NachName;
    private String AnspTelefon;

    public String getMitarbeiter() {
        return Mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        Mitarbeiter = mitarbeiter;
    }

    private String Mitarbeiter;

    public String getKundenNr() {
        return KundenNr;
    }

    public void setKundenNr(String kundenNr) {
        KundenNr = kundenNr;
    }

    public String getKaNr() {
        return KaNr;
    }

    public void setKaNr(String kaNr) {
        KaNr = kaNr;
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

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String strasse) {
        Strasse = strasse;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(String pageNumber) {
        PageNumber = pageNumber;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
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
        dest.writeString(this.UstIDNr);
        dest.writeString(this.Anrede);
        dest.writeString(this.VorName);
        dest.writeString(this.NachName);
        dest.writeString(this.AnspTelefon);
        dest.writeString(this.AnsPartnerId);

    }

    public ContactPersonSearchModel() {
    }

    private ContactPersonSearchModel(Parcel in) {
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
        this.UstIDNr = in.readString();
        this.Anrede = in.readString();
        this.VorName = in.readString();
        this.NachName = in.readString();
        this.AnspTelefon = in.readString();
        this.AnsPartnerId = in.readString();
    }

    public static final Parcelable.Creator<ContactPersonSearchModel> CREATOR = new Parcelable.Creator<ContactPersonSearchModel>() {
        public ContactPersonSearchModel createFromParcel(Parcel source) {
            return new ContactPersonSearchModel(source);
        }

        public ContactPersonSearchModel[] newArray(int size) {
            return new ContactPersonSearchModel[size];
        }
    };
    public static void extractFromJson(String jsonString, ArrayList<ContactPersonSearchModel> arrayList)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<ContactPersonSearchModel>>(){}.getType();
        ArrayList<ContactPersonSearchModel> customerList = gson.fromJson(jsonString, typedValue);
        arrayList.addAll(customerList);
    }
}

