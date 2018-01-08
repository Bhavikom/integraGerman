package de.mateco.integrAMobile.model;


import android.os.Parcel;
import android.os.Parcelable;

public class SiteInspectionDeviceDataModel implements Parcelable {

    private transient int id;
    private transient int ParentId;
    private transient int BvoId;
    private int Alternativ;
    private int Position;
    private String Hoehengruppe;
    private String Arbeitshoehe;
    private String SeitlicheReichweite;
    private String Laenge;
    private String Breite;
    private String Hoehe;
    private String Korbbelastung;
    private String Korbarmlaenge;
    private String SonstigesText;
    private String Geraetegruppe,Geraetetyp;
    private int Gewicht;
    private int Diesel;
    private int Elektro;
    private int Allrad;
    private int Kette_Gummi;
    private int Reifen_markierungsarm;
    private int Powerlift;
    private int Stromerzeuger;
    private int Sonstiges;
    private int Anzahl;
    private String Hauptgeraet;

    public String getHauptgeraet() {
        return Hauptgeraet;
    }

    public void setHauptgeraet(String hauptgeraet) {
        Hauptgeraet = hauptgeraet;
    }

    public int getBvoId() {
        return BvoId;
    }

    public void setBvoId(int bvoId) {
        BvoId = bvoId;
    }

    public String getSonstigesText() {
        return SonstigesText;
    }

    public void setSonstigesText(String sonstigesText) {
        SonstigesText = sonstigesText;
    }

    public String getArbeitshoehe() {
        return Arbeitshoehe;
    }

    public void setArbeitshoehe(String arbeitshoehe) {
        Arbeitshoehe = arbeitshoehe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public int getAlternativ() {
        return Alternativ;
    }

    public void setAlternativ(int alternativ) {
        Alternativ = alternativ;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String getHoehengruppe() {
        return Hoehengruppe;
    }

    public void setHoehengruppe(String hoehengruppe) {
        Hoehengruppe = hoehengruppe;
    }

    public String getSeitlicheReichweite() {
        return SeitlicheReichweite;
    }

    public void setSeitlicheReichweite(String seitlicheReichweite) {
        SeitlicheReichweite = seitlicheReichweite;
    }

    public String getLaenge() {
        return Laenge;
    }

    public void setLaenge(String laenge) {
        Laenge = laenge;
    }

    public String getBreite() {
        return Breite;
    }

    public void setBreite(String breite) {
        Breite = breite;
    }

    public String getHoehe() {
        return Hoehe;
    }

    public void setHoehe(String hoehe) {
        Hoehe = hoehe;
    }

    public String getKorbbelastung() {
        return Korbbelastung;
    }

    public void setKorbbelastung(String korbbelastung) {
        Korbbelastung = korbbelastung;
    }

    public String getKorbarmlaenge() {
        return Korbarmlaenge;
    }

    public void setKorbarmlaenge(String korbarmlaenge) {
        Korbarmlaenge = korbarmlaenge;
    }

    public int getGewicht() {
        return Gewicht;
    }

    public void setGewicht(int gewicht) {
        Gewicht = gewicht;
    }

    public int getDiesel() {
        return Diesel;
    }

    public void setDiesel(int diesel) {
        Diesel = diesel;
    }

    public int getElektro() {
        return Elektro;
    }

    public void setElektro(int elektro) {
        Elektro = elektro;
    }

    public int getAllrad() {
        return Allrad;
    }

    public void setAllrad(int allrad) {
        Allrad = allrad;
    }

    public int getKette_Gummi() {
        return Kette_Gummi;
    }

    public void setKette_Gummi(int kette_Gummi) {
        Kette_Gummi = kette_Gummi;
    }

    public int getReifen_markierungsarm() {
        return Reifen_markierungsarm;
    }

    public void setReifen_markierungsarm(int reifen_markierungsarm) {
        Reifen_markierungsarm = reifen_markierungsarm;
    }

    public int getPowerlift() {
        return Powerlift;
    }

    public void setPowerlift(int powerlift) {
        Powerlift = powerlift;
    }

    public int getStromerzeuger() {
        return Stromerzeuger;
    }

    public void setStromerzeuger(int stromerzeuger) {
        Stromerzeuger = stromerzeuger;
    }

    public int getSonstiges() {
        return Sonstiges;
    }

    public void setSonstiges(int sonstiges) {
        Sonstiges = sonstiges;
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

    public int getAnzahl() {
        return Anzahl;
    }

    public void setAnzahl(int anzahl) {
        Anzahl = anzahl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.ParentId);
        dest.writeInt(this.BvoId);
        dest.writeInt(this.Alternativ);
        dest.writeInt(this.Position);
        dest.writeString(this.Hoehengruppe);
        dest.writeString(this.Arbeitshoehe);
        dest.writeString(this.SeitlicheReichweite);
        dest.writeString(this.Laenge);
        dest.writeString(this.Breite);
        dest.writeString(this.Hoehe);
        dest.writeString(this.Korbbelastung);
        dest.writeString(this.Korbarmlaenge);
        dest.writeString(this.SonstigesText);
        dest.writeString(this.Geraetegruppe);
        dest.writeString(this.Geraetetyp);
        dest.writeInt(this.Gewicht);
        dest.writeInt(this.Diesel);
        dest.writeInt(this.Elektro);
        dest.writeInt(this.Allrad);
        dest.writeInt(this.Kette_Gummi);
        dest.writeInt(this.Reifen_markierungsarm);
        dest.writeInt(this.Powerlift);
        dest.writeInt(this.Stromerzeuger);
        dest.writeInt(this.Sonstiges);
        dest.writeInt(this.Anzahl);
        dest.writeString(this.Hauptgeraet);
    }

    public SiteInspectionDeviceDataModel() {
    }

    private SiteInspectionDeviceDataModel(Parcel in) {
        this.id = in.readInt();
        this.ParentId = in.readInt();
        this.BvoId = in.readInt();
        this.Alternativ = in.readInt();
        this.Position = in.readInt();
        this.Hoehengruppe = in.readString();
        this.Arbeitshoehe = in.readString();
        this.SeitlicheReichweite = in.readString();
        this.Laenge = in.readString();
        this.Breite = in.readString();
        this.Hoehe = in.readString();
        this.Korbbelastung = in.readString();
        this.Korbarmlaenge = in.readString();
        this.SonstigesText = in.readString();
        this.Geraetegruppe = in.readString();
        this.Geraetetyp = in.readString();
        this.Gewicht = in.readInt();
        this.Diesel = in.readInt();
        this.Elektro = in.readInt();
        this.Allrad = in.readInt();
        this.Kette_Gummi = in.readInt();
        this.Reifen_markierungsarm = in.readInt();
        this.Powerlift = in.readInt();
        this.Stromerzeuger = in.readInt();
        this.Sonstiges = in.readInt();
        this.Anzahl = in.readInt();
        this.Hauptgeraet = in.readString();
    }

    public static final Creator<SiteInspectionDeviceDataModel> CREATOR = new Creator<SiteInspectionDeviceDataModel>() {
        public SiteInspectionDeviceDataModel createFromParcel(Parcel source) {
            return new SiteInspectionDeviceDataModel(source);
        }

        public SiteInspectionDeviceDataModel[] newArray(int size) {
            return new SiteInspectionDeviceDataModel[size];
        }
    };
}
