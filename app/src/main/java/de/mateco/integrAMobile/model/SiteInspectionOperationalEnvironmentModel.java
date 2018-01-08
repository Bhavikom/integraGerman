package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SiteInspectionOperationalEnvironmentModel implements Parcelable {
    private String PublicRoadCountry,PrivateProperty,Interior,Outdoor;
    private String HindernisText, HaftungsausschlussText, ZufahrtAuslegen, Schaltafeln, Wegbeschreibung, TragslastZufahrt,
            TraglastStandplatz, Verkehrslastmax, StuetzmaterialText, Untergrund, Hinweise, Fahrbleche, BongossiPlatten, Stassengefaelle;
    private int Hindernis, Haftungsausschluss, Waldweg, VerdachtAbwasserkanal, Stuetzmaterial;

    @SerializedName("BVOBefahrbarkeitList")
    private ArrayList<SiteInspectionPracticabilityModel> listOfPracticability;

    public String getPublicRoadCountry() {
        return PublicRoadCountry;
    }

    public void setPublicRoadCountry(String publicRoadCountry) {
        PublicRoadCountry = publicRoadCountry;
    }

    public String getPrivateProperty() {
        return PrivateProperty;
    }

    public void setPrivateProperty(String privateProperty) {
        PrivateProperty = privateProperty;
    }

    public String getInterior() {
        return Interior;
    }

    public void setInterior(String interior) {
        Interior = interior;
    }

    public String getOutdoor() {
        return Outdoor;
    }

    public void setOutdoor(String outdoor) {
        Outdoor = outdoor;
    }

    public String getHindernisText() {
        return HindernisText;
    }

    public void setHindernisText(String hindernisText) {
        HindernisText = hindernisText;
    }

    public String getHaftungsausschlussText() {
        return HaftungsausschlussText;
    }

    public void setHaftungsausschlussText(String haftungsausschlussText) {
        HaftungsausschlussText = haftungsausschlussText;
    }

    public String getZufahrtAuslegen() {
        return ZufahrtAuslegen;
    }

    public void setZufahrtAuslegen(String zufahrtAuslegen) {
        ZufahrtAuslegen = zufahrtAuslegen;
    }

    public String getSchaltafeln() {
        return Schaltafeln;
    }

    public void setSchaltafeln(String schaltafeln) {
        Schaltafeln = schaltafeln;
    }

    public String getWegbeschreibung() {
        return Wegbeschreibung;
    }

    public void setWegbeschreibung(String wegbeschreibung) {
        Wegbeschreibung = wegbeschreibung;
    }

    public String getTragslastZufahrt() {
        return TragslastZufahrt;
    }

    public void setTragslastZufahrt(String tragslastZufahrt) {
        TragslastZufahrt = tragslastZufahrt;
    }

    public String getTraglastStandplatz() {
        return TraglastStandplatz;
    }

    public void setTraglastStandplatz(String traglastStandplatz) {
        TraglastStandplatz = traglastStandplatz;
    }

    public String getVerkehrslastmax() {
        return Verkehrslastmax;
    }

    public void setVerkehrslastmax(String verkehrslastmax) {
        Verkehrslastmax = verkehrslastmax;
    }

    public String getStuetzmaterialText() {
        return StuetzmaterialText;
    }

    public void setStuetzmaterialText(String stuetzmaterialText) {
        StuetzmaterialText = stuetzmaterialText;
    }

    public String getUntergrund() {
        return Untergrund;
    }

    public void setUntergrund(String untergrund) {
        Untergrund = untergrund;
    }

    public String getHinweise() {
        return Hinweise;
    }

    public void setHinweise(String hinweise) {
        Hinweise = hinweise;
    }

    public String getFahrbleche() {
        return Fahrbleche;
    }

    public void setFahrbleche(String fahrbleche) {
        Fahrbleche = fahrbleche;
    }

    public String getBongossiPlatten() {
        return BongossiPlatten;
    }

    public void setBongossiPlatten(String bongossiPlatten) {
        BongossiPlatten = bongossiPlatten;
    }

    public String getStassengefaelle() {
        return Stassengefaelle;
    }

    public void setStassengefaelle(String stassengefaelle) {
        Stassengefaelle = stassengefaelle;
    }

    public int getHindernis() {
        return Hindernis;
    }

    public void setHindernis(int hindernis) {
        Hindernis = hindernis;
    }

    public int getHaftungsausschluss() {
        return Haftungsausschluss;
    }

    public void setHaftungsausschluss(int haftungsausschluss) {
        Haftungsausschluss = haftungsausschluss;
    }

    public int getWaldweg() {
        return Waldweg;
    }

    public void setWaldweg(int waldweg) {
        Waldweg = waldweg;
    }

    public int getVerdachtAbwasserkanal() {
        return VerdachtAbwasserkanal;
    }

    public void setVerdachtAbwasserkanal(int verdachtAbwasserkanal) {
        VerdachtAbwasserkanal = verdachtAbwasserkanal;
    }

    public int getStuetzmaterial() {
        return Stuetzmaterial;
    }

    public void setStuetzmaterial(int stuetzmaterial) {
        Stuetzmaterial = stuetzmaterial;
    }

    public ArrayList<SiteInspectionPracticabilityModel> getListOfPracticability() {
        return listOfPracticability;
    }

    public void setListOfPracticability(ArrayList<SiteInspectionPracticabilityModel> listOfPracticability) {
        this.listOfPracticability = listOfPracticability;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.HindernisText);
        dest.writeString(this.HaftungsausschlussText);
        dest.writeString(this.ZufahrtAuslegen);
        dest.writeString(this.Schaltafeln);
        dest.writeString(this.Wegbeschreibung);
        dest.writeString(this.TragslastZufahrt);
        dest.writeString(this.TraglastStandplatz);
        dest.writeString(this.Verkehrslastmax);
        dest.writeString(this.StuetzmaterialText);
        dest.writeString(this.Untergrund);
        dest.writeString(this.Hinweise);
        dest.writeString(this.Fahrbleche);
        dest.writeString(this.BongossiPlatten);
        dest.writeString(this.Stassengefaelle);
        dest.writeString(this.PublicRoadCountry);
        dest.writeString(this.PrivateProperty);
        dest.writeString(this.Interior);
        dest.writeString(this.Outdoor);
        dest.writeInt(this.Hindernis);
        dest.writeInt(this.Haftungsausschluss);
        dest.writeInt(this.Waldweg);
        dest.writeInt(this.VerdachtAbwasserkanal);
        dest.writeInt(this.Stuetzmaterial);
        dest.writeSerializable(this.listOfPracticability);
    }

    public SiteInspectionOperationalEnvironmentModel() {
    }

    private SiteInspectionOperationalEnvironmentModel(Parcel in) {
        this.HindernisText = in.readString();
        this.HaftungsausschlussText = in.readString();
        this.ZufahrtAuslegen = in.readString();
        this.Schaltafeln = in.readString();
        this.Wegbeschreibung = in.readString();
        this.TragslastZufahrt = in.readString();
        this.TraglastStandplatz = in.readString();
        this.Verkehrslastmax = in.readString();
        this.StuetzmaterialText = in.readString();
        this.Untergrund = in.readString();
        this.Hinweise = in.readString();
        this.Fahrbleche = in.readString();
        this.BongossiPlatten = in.readString();
        this.Stassengefaelle = in.readString();
        this.PublicRoadCountry = in.readString();
        this.PrivateProperty = in.readString();
        this.Interior = in.readString();
        this.Outdoor = in.readString();
        this.Hindernis = in.readInt();
        this.Haftungsausschluss = in.readInt();
        this.Waldweg = in.readInt();
        this.VerdachtAbwasserkanal = in.readInt();
        this.Stuetzmaterial = in.readInt();

        this.listOfPracticability = (ArrayList<SiteInspectionPracticabilityModel>) in.readSerializable();
    }

    public static final Creator<SiteInspectionOperationalEnvironmentModel> CREATOR = new Creator<SiteInspectionOperationalEnvironmentModel>() {
        public SiteInspectionOperationalEnvironmentModel createFromParcel(Parcel source) {
            return new SiteInspectionOperationalEnvironmentModel(source);
        }

        public SiteInspectionOperationalEnvironmentModel[] newArray(int size) {
            return new SiteInspectionOperationalEnvironmentModel[size];
        }
    };
}
