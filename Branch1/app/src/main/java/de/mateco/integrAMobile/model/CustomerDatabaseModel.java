package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CustomerDatabaseModel implements Parcelable {
    private String customerNo, kaNr, matchCode, name1, road, zipCode, place, telephone, customerContact;
    private CustomerModel customerModel;
    private ArrayList<ContactPersonModel> listOfContactPerson;
    private ArrayList<CustomerActivityModel> listOfActivity;
    private ArrayList<CustomerProjectModel> listOfProject;
    private ArrayList<CustomerOfferModel> listOfOffer;
    private ArrayList<CustomerOpenOrderModel> listOfOpenOrder;
    private ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder;
    private ArrayList<CustomerOpenOfferModel> listOfOpenOffer;
    private ArrayList<CustomerLostSaleDataModel> listOfLostSale;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getKaNr() {
        return kaNr;
    }

    public void setKaNr(String kaNr) {
        this.kaNr = kaNr;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public ArrayList<ContactPersonModel> getListOfContactPerson() {
        return listOfContactPerson;
    }

    public void setListOfContactPerson(ArrayList<ContactPersonModel> listOfContactPerson) {
        this.listOfContactPerson = listOfContactPerson;
    }

    public ArrayList<CustomerActivityModel> getListOfActivity() {
        return listOfActivity;
    }

    public void setListOfActivity(ArrayList<CustomerActivityModel> listOfActivity) {
        this.listOfActivity = listOfActivity;
    }

    public ArrayList<CustomerProjectModel> getListOfProject() {
        return listOfProject;
    }

    public void setListOfProject(ArrayList<CustomerProjectModel> listOfProject) {
        this.listOfProject = listOfProject;
    }

    public ArrayList<CustomerOfferModel> getListOfOffer() {
        return listOfOffer;
    }

    public void setListOfOffer(ArrayList<CustomerOfferModel> listOfOffer) {
        this.listOfOffer = listOfOffer;
    }

    public ArrayList<CustomerOpenOrderModel> getListOfOpenOrder() {
        return listOfOpenOrder;
    }

    public void setListOfOpenOrder(ArrayList<CustomerOpenOrderModel> listOfOpenOrder) {
        this.listOfOpenOrder = listOfOpenOrder;
    }

    public ArrayList<CustomerCompletedOrderModel> getListOfCompletedOrder() {
        return listOfCompletedOrder;
    }

    public void setListOfCompletedOrder(ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder) {
        this.listOfCompletedOrder = listOfCompletedOrder;
    }

    public ArrayList<CustomerOpenOfferModel> getListOfOpenOffer() {
        return listOfOpenOffer;
    }

    public void setListOfOpenOffer(ArrayList<CustomerOpenOfferModel> listOfOpenOffer) {
        this.listOfOpenOffer = listOfOpenOffer;
    }

    public ArrayList<CustomerLostSaleDataModel> getListOfLostSale() {
        return listOfLostSale;
    }

    public void setListOfLostSale(ArrayList<CustomerLostSaleDataModel> listOfLostSale) {
        this.listOfLostSale = listOfLostSale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customerNo);
        dest.writeString(this.kaNr);
        dest.writeString(this.matchCode);
        dest.writeString(this.name1);
        dest.writeString(this.road);
        dest.writeString(this.zipCode);
        dest.writeString(this.place);
        dest.writeString(this.telephone);
        dest.writeString(this.customerContact);
        dest.writeParcelable(this.customerModel, 0);
        dest.writeSerializable(this.listOfContactPerson);
        dest.writeSerializable(this.listOfActivity);
        dest.writeSerializable(this.listOfProject);
        dest.writeSerializable(this.listOfOffer);
        dest.writeSerializable(this.listOfOpenOrder);
        dest.writeSerializable(this.listOfCompletedOrder);
        dest.writeSerializable(this.listOfOpenOffer);
        dest.writeSerializable(this.listOfLostSale);
    }

    public CustomerDatabaseModel() {
    }

    private CustomerDatabaseModel(Parcel in) {
        this.customerNo = in.readString();
        this.kaNr = in.readString();
        this.matchCode = in.readString();
        this.name1 = in.readString();
        this.road = in.readString();
        this.zipCode = in.readString();
        this.place = in.readString();
        this.telephone = in.readString();
        this.customerContact = in.readString();
        this.customerModel = in.readParcelable(CustomerModel.class.getClassLoader());
        this.listOfContactPerson = (ArrayList<ContactPersonModel>) in.readSerializable();
        this.listOfActivity = (ArrayList<CustomerActivityModel>) in.readSerializable();
        this.listOfProject = (ArrayList<CustomerProjectModel>) in.readSerializable();
        this.listOfOffer = (ArrayList<CustomerOfferModel>) in.readSerializable();
        this.listOfOpenOrder = (ArrayList<CustomerOpenOrderModel>) in.readSerializable();
        this.listOfCompletedOrder = (ArrayList<CustomerCompletedOrderModel>) in.readSerializable();
        this.listOfOpenOffer = (ArrayList<CustomerOpenOfferModel>) in.readSerializable();
        this.listOfLostSale = (ArrayList<CustomerLostSaleDataModel>) in.readSerializable();
    }

    public static final Creator<CustomerDatabaseModel> CREATOR = new Creator<CustomerDatabaseModel>() {
        public CustomerDatabaseModel createFromParcel(Parcel source) {
            return new CustomerDatabaseModel(source);
        }

        public CustomerDatabaseModel[] newArray(int size) {
            return new CustomerDatabaseModel[size];
        }
    };
}
