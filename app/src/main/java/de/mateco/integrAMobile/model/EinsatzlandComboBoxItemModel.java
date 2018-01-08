package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by mmehta on 11.06.2016.
 */
public class EinsatzlandComboBoxItemModel implements Parcelable {

    int Land;
    String Sort="";

    public int getLand() {
        return Land;
    }

    public void setLand(int land) {
        Land = land;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public String getBeZeichnung() {
        return BeZeichnung;
    }

    public void setBeZeichnung(String beZeichnung) {
        BeZeichnung = beZeichnung;
    }

    public String getMachCodeLand() {
        return MachCodeLand;
    }

    public void setMachCodeLand(String machCodeLand) {
        MachCodeLand = machCodeLand;
    }

    String BeZeichnung="";
    String MachCodeLand="";

    protected EinsatzlandComboBoxItemModel(Parcel in) {
        this.Land = Integer.parseInt(in.readString());
        this.BeZeichnung = in.readString();
        this.Sort= in.readString();
        this.MachCodeLand = in.readString();

    }

    public static final Creator<EinsatzlandComboBoxItemModel> CREATOR = new Creator<EinsatzlandComboBoxItemModel>() {
        @Override
        public EinsatzlandComboBoxItemModel createFromParcel(Parcel in) {
            return new EinsatzlandComboBoxItemModel(in);
        }

        @Override
        public EinsatzlandComboBoxItemModel[] newArray(int size) {
            return new EinsatzlandComboBoxItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(String.valueOf(this.Land));
        dest.writeString(this.BeZeichnung);
        dest.writeString(this.Sort);
        dest.writeString(this.MachCodeLand);
    }
    public static void extractFromJson(String jsonString, ArrayList<EinsatzlandComboBoxItemModel> deviceTypes)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<EinsatzlandComboBoxItemModel>>(){}.getType();
        ArrayList<EinsatzlandComboBoxItemModel> types = gson.fromJson(jsonString, typedValue);
        deviceTypes.addAll(types);
    }
}
