package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CustomerFullDetailModel implements Parcelable {
    private CustomerModel CustomerSearchList;

    private ArrayList<ContactPersonModel> CustomerContactPersonList;

    private CustomerActivityGetModel CustomerActivityListt;

    private ArrayList<CustomerLostSaleDataModel> CustomerLostSaleList;

    private ArrayList<CustomerOpenOfferModel> CustomerOpenSpecialsList;

    private ArrayList<CustomerOpenOrderModel> CustomerOpenOrderList;

    private ArrayList<CustomerCompletedOrderModel> CustomerCompletedOrdersList;

    public CustomerModel getCustomerSearchList() {
        return CustomerSearchList;
    }

    public void setCustomerSearchList(CustomerModel customerSearchList) {
        CustomerSearchList = customerSearchList;
    }

    public ArrayList<ContactPersonModel> getCustomerContactPersonList() {
        return CustomerContactPersonList;
    }

    public void setCustomerContactPersonList(ArrayList<ContactPersonModel> customerContactPersonList) {
        CustomerContactPersonList = customerContactPersonList;
    }

    public CustomerActivityGetModel getCustomerActivityListt() {
        return CustomerActivityListt;
    }

    public void setCustomerActivityListt(CustomerActivityGetModel customerActivityListt) {
        CustomerActivityListt = customerActivityListt;
    }

    public ArrayList<CustomerLostSaleDataModel> getCustomerLostSaleList() {
        return CustomerLostSaleList;
    }

    public void setCustomerLostSaleList(ArrayList<CustomerLostSaleDataModel> customerLostSaleList) {
        CustomerLostSaleList = customerLostSaleList;
    }

    public ArrayList<CustomerOpenOfferModel> getCustomerOpenSpecialsList() {
        return CustomerOpenSpecialsList;
    }

    public void setCustomerOpenSpecialsList(ArrayList<CustomerOpenOfferModel> customerOpenSpecialsList) {
        CustomerOpenSpecialsList = customerOpenSpecialsList;
    }

    public ArrayList<CustomerOpenOrderModel> getCustomerOpenOrderList() {
        return CustomerOpenOrderList;
    }

    public void setCustomerOpenOrderList(ArrayList<CustomerOpenOrderModel> customerOpenOrderList) {
        CustomerOpenOrderList = customerOpenOrderList;
    }

    public ArrayList<CustomerCompletedOrderModel> getCustomerCompletedOrdersList() {
        return CustomerCompletedOrdersList;
    }

    public void setCustomerCompletedOrdersList(ArrayList<CustomerCompletedOrderModel> customerCompletedOrdersList) {
        CustomerCompletedOrdersList = customerCompletedOrdersList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.CustomerSearchList, 0);
        dest.writeSerializable(this.CustomerContactPersonList);
        dest.writeParcelable(this.CustomerActivityListt, 0);
        dest.writeSerializable(this.CustomerLostSaleList);
        dest.writeSerializable(this.CustomerOpenSpecialsList);
        dest.writeSerializable(this.CustomerOpenOrderList);
        dest.writeSerializable(this.CustomerCompletedOrdersList);
    }

    public CustomerFullDetailModel() {
    }

    private CustomerFullDetailModel(Parcel in) {
        this.CustomerSearchList = in.readParcelable(CustomerModel.class.getClassLoader());
        this.CustomerContactPersonList = (ArrayList<ContactPersonModel>) in.readSerializable();
        this.CustomerActivityListt = in.readParcelable(CustomerActivityGetModel.class.getClassLoader());
        this.CustomerLostSaleList = (ArrayList<CustomerLostSaleDataModel>) in.readSerializable();
        this.CustomerOpenSpecialsList = (ArrayList<CustomerOpenOfferModel>) in.readSerializable();
        this.CustomerOpenOrderList = (ArrayList<CustomerOpenOrderModel>) in.readSerializable();
        this.CustomerCompletedOrdersList = (ArrayList<CustomerCompletedOrderModel>) in.readSerializable();
    }

    public static final Parcelable.Creator<CustomerFullDetailModel> CREATOR = new Parcelable.Creator<CustomerFullDetailModel>() {
        public CustomerFullDetailModel createFromParcel(Parcel source) {
            return new CustomerFullDetailModel(source);
        }

        public CustomerFullDetailModel[] newArray(int size) {
            return new CustomerFullDetailModel[size];
        }
    };
}
