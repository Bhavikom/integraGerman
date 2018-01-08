package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by mmehta on 10.06.2016.
 */
public class LadefahrzeugComboBoxItemModel implements Parcelable {
    String Bezeichnung="";
    int Id;

    public String getBezeichnung() {
        return Bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        Bezeichnung = bezeichnung;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSprache() {
        return Sprache;
    }

    public void setSprache(int sprache) {
        Sprache = sprache;
    }

    int Sprache;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.Bezeichnung);
        dest.writeString(String.valueOf(this.Id));
        dest.writeString(String.valueOf(this.Sprache));

    }
    public LadefahrzeugComboBoxItemModel() {
    }

    private LadefahrzeugComboBoxItemModel(Parcel in) {
        this.Bezeichnung = in.readString();
        this.Id = Integer.parseInt(in.readString());
        this.Sprache= Integer.parseInt(in.readString());

    }
    public static final Creator<LadefahrzeugComboBoxItemModel> CREATOR = new Creator<LadefahrzeugComboBoxItemModel>() {
        public LadefahrzeugComboBoxItemModel createFromParcel(Parcel source) {
            return new LadefahrzeugComboBoxItemModel(source);
        }

        public LadefahrzeugComboBoxItemModel[] newArray(int size) {
            return new LadefahrzeugComboBoxItemModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<LadefahrzeugComboBoxItemModel> deviceTypes)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<LadefahrzeugComboBoxItemModel>>(){}.getType();
        ArrayList<LadefahrzeugComboBoxItemModel> types = gson.fromJson(jsonString, typedValue);
        deviceTypes.addAll(types);
    }
}
