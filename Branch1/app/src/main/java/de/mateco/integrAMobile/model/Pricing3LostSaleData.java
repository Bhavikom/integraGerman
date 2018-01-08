package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BrijeshChavda on 2/20/2015.
 */
public class Pricing3LostSaleData implements Parcelable {
    boolean isSelected=false;
    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }
    int id;
    String branch;
    String hGRP;
    String deviceType;
    String manufacturer;

    String type;
    int md;
    double rentalPrice;
    double sb;

    String hfStatus;

    String spStatus;
    double hb;
    String best;
    String customerNo;

    public Pricing3LostSaleData() {
    }

    public Pricing3LostSaleData(String branch, String hGRP, String deviceType, String manufacturer, String type, int md, double rentalPrice, double sb, String hfStatus, String spStatus, double hb, String best, String customerNo) {
        this.branch = branch;
        this.hGRP = hGRP;
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.type = type;
        this.md = md;
        this.rentalPrice = rentalPrice;
        this.sb = sb;
        this.hfStatus = hfStatus;
        this.spStatus = spStatus;
        this.hb = hb;
        this.best = best;
        this.customerNo = customerNo;
    }

    public Pricing3LostSaleData(int id, String branch, String hGRP, String deviceType, String manufacturer, String type, int md, double rentalPrice, double sb, String hfStatus, String spStatus, double hb, String best, String customerNo) {
        this.id = id;
        this.branch = branch;
        this.hGRP = hGRP;
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.type = type;
        this.md = md;
        this.rentalPrice = rentalPrice;
        this.sb = sb;
        this.hfStatus = hfStatus;
        this.spStatus = spStatus;
        this.hb = hb;
        this.best = best;
        this.customerNo = customerNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String gethGRP() {
        return hGRP;
    }

    public void sethGRP(String hGRP) {
        this.hGRP = hGRP;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMd() {
        return md;
    }

    public void setMd(int md) {
        this.md = md;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public double getSb() {
        return sb;
    }

    public void setSb(double sb) {
        this.sb = sb;
    }

    public String getHfStatus() {
        return hfStatus;
    }

    public void setHfStatus(String hfStatus) {
        this.hfStatus = hfStatus;
    }

    public String getSpStatus() {
        return spStatus;
    }

    public void setSpStatus(String spStatus) {
        this.spStatus = spStatus;
    }

    public double getHb() {
        return hb;
    }

    public void setHb(double hb) {
        this.hb = hb;
    }

    public String getBest() {
        return best;
    }

    public void setBest(String best) {
        this.best = best;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.branch);
        dest.writeString(this.hGRP);
        dest.writeString(this.deviceType);
        dest.writeString(this.manufacturer);
        dest.writeString(this.type);
        dest.writeInt(this.md);
        dest.writeDouble(this.rentalPrice);
        dest.writeDouble(this.sb);
        dest.writeString(this.hfStatus);
        dest.writeString(this.spStatus);
        dest.writeDouble(this.hb);
        dest.writeString(this.best);
        dest.writeString(this.customerNo);
    }

    private Pricing3LostSaleData(Parcel in) {
        this.id = in.readInt();
        this.branch = in.readString();
        this.hGRP = in.readString();
        this.deviceType = in.readString();
        this.manufacturer = in.readString();
        this.type = in.readString();
        this.md = in.readInt();
        this.rentalPrice = in.readDouble();
        this.sb = in.readDouble();
        this.hfStatus = in.readString();
        this.spStatus = in.readString();
        this.hb = in.readDouble();
        this.best = in.readString();
        this.customerNo = in.readString();
    }

    public static final Creator<Pricing3LostSaleData> CREATOR = new Creator<Pricing3LostSaleData>() {
        public Pricing3LostSaleData createFromParcel(Parcel source) {
            return new Pricing3LostSaleData(source);
        }

        public Pricing3LostSaleData[] newArray(int size) {
            return new Pricing3LostSaleData[size];
        }
    };
}
