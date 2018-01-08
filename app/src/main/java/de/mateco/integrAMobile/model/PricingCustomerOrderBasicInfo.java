package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PricingCustomerOrderBasicInfo implements Parcelable {
    private String project, country, zipCode, place, street, zusatz, contactPersonName, contactPersonMobile, enferrung, projectId;

    private String anfahrt, abfahrt, beiladungsPreis, pauschale, notes;

    private String bestellerTelephone, bestellerEmail, bestellerAnrede, bestellerMobile;

    private String startDate, endDate,datesCommas;

    public String getDatesCommas() {
        return datesCommas;
    }

    public void setDatesCommas(String datesCommas) {
        this.datesCommas = datesCommas;
    }

    private double latitude, longitude;


    private int geraeteTypeId;

    public int getGeraeteTypeId() {
        return geraeteTypeId;
    }

    public void setGeraeteTypeId(int geraeteTypeId) {
        this.geraeteTypeId = geraeteTypeId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZusatz() {
        return zusatz;
    }

    public void setZusatz(String zusatz) {
        this.zusatz = zusatz;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonMobile() {
        return contactPersonMobile;
    }

    public void setContactPersonMobile(String contactPersonMobile) {
        this.contactPersonMobile = contactPersonMobile;
    }

    public String getEnferrung() {
        return enferrung;
    }

    public void setEnferrung(String enferrung) {
        this.enferrung = enferrung;
    }

    public String getAnfahrt() {
        return anfahrt;
    }

    public void setAnfahrt(String anfahrt) {
        this.anfahrt = anfahrt;
    }

    public String getAbfahrt() {
        return abfahrt;
    }

    public void setAbfahrt(String abfahrt) {
        this.abfahrt = abfahrt;
    }

    public String getBeiladungsPreis() {
        return beiladungsPreis;
    }

    public void setBeiladungsPreis(String beiladungsPreis) {
        this.beiladungsPreis = beiladungsPreis;
    }

    public String getPauschale() {
        return pauschale;
    }

    public void setPauschale(String pauschale) {
        this.pauschale = pauschale;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBestellerTelephone() {
        return bestellerTelephone;
    }

    public void setBestellerTelephone(String bestellerTelephone) {
        this.bestellerTelephone = bestellerTelephone;
    }

    public String getBestellerEmail() {
        return bestellerEmail;
    }

    public void setBestellerEmail(String bestellerEmail) {
        this.bestellerEmail = bestellerEmail;
    }

    public String getBestellerAnrede() {
        return bestellerAnrede;
    }

    public void setBestellerAnrede(String bestellerAnrede) {
        this.bestellerAnrede = bestellerAnrede;
    }

    public String getBestellerMobile() {
        return bestellerMobile;
    }

    public void setBestellerMobile(String bestellerMobile) {
        this.bestellerMobile = bestellerMobile;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public PricingCustomerOrderBasicInfo() {
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.project);
        dest.writeString(this.country);
        dest.writeString(this.zipCode);
        dest.writeString(this.place);
        dest.writeString(this.street);
        dest.writeString(this.zusatz);
        dest.writeString(this.contactPersonName);
        dest.writeString(this.contactPersonMobile);
        dest.writeString(this.enferrung);
        dest.writeString(this.projectId);
        dest.writeString(this.anfahrt);
        dest.writeString(this.abfahrt);
        dest.writeString(this.beiladungsPreis);
        dest.writeString(this.pauschale);
        dest.writeString(this.notes);
        dest.writeString(this.bestellerTelephone);
        dest.writeString(this.bestellerEmail);
        dest.writeString(this.bestellerAnrede);
        dest.writeString(this.bestellerMobile);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.geraeteTypeId);
        dest.writeString(this.datesCommas);
    }

    protected PricingCustomerOrderBasicInfo(Parcel in) {
        this.project = in.readString();
        this.country = in.readString();
        this.zipCode = in.readString();
        this.place = in.readString();
        this.street = in.readString();
        this.zusatz = in.readString();
        this.contactPersonName = in.readString();
        this.contactPersonMobile = in.readString();
        this.enferrung = in.readString();
        this.projectId = in.readString();
        this.anfahrt = in.readString();
        this.abfahrt = in.readString();
        this.beiladungsPreis = in.readString();
        this.pauschale = in.readString();
        this.notes = in.readString();
        this.bestellerTelephone = in.readString();
        this.bestellerEmail = in.readString();
        this.bestellerAnrede = in.readString();
        this.bestellerMobile = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.geraeteTypeId = in.readInt();
        this.datesCommas=in.readString();
    }

    public static final Creator<PricingCustomerOrderBasicInfo> CREATOR = new Creator<PricingCustomerOrderBasicInfo>() {
        public PricingCustomerOrderBasicInfo createFromParcel(Parcel source) {
            return new PricingCustomerOrderBasicInfo(source);
        }

        public PricingCustomerOrderBasicInfo[] newArray(int size) {
            return new PricingCustomerOrderBasicInfo[size];
        }
    };
}
