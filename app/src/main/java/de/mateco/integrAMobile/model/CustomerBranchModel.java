package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ShreyashMashru on 13-05-2015.
 */
public class CustomerBranchModel implements Parcelable
{
    @SerializedName("BrancheID")
    private String id;
    @SerializedName("BrancheName")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public CustomerBranchModel() {
    }

    private CustomerBranchModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<CustomerBranchModel> CREATOR = new Parcelable.Creator<CustomerBranchModel>() {
        public CustomerBranchModel createFromParcel(Parcel source) {
            return new CustomerBranchModel(source);
        }

        public CustomerBranchModel[] newArray(int size) {
            return new CustomerBranchModel[size];
        }
    };
}
