package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SiteInspectionDeviceTypeModel implements Parcelable {
    private String Bezeichnung, GeraeettypID, Hoehengruppe, arbeitshoehe;

    public String getBezeichnung() {
        return Bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        Bezeichnung = bezeichnung;
    }

    public String getGeraeettypID() {
        return GeraeettypID;
    }

    public void setGeraeettypID(String geraeettypID) {
        GeraeettypID = geraeettypID;
    }

    public String getHoehengruppe() {
        return Hoehengruppe;
    }

    public void setHoehengruppe(String hoehengruppe) {
        Hoehengruppe = hoehengruppe;
    }

    public String getArbeitshoehe() {
        return arbeitshoehe;
    }

    public void setArbeitshoehe(String arbeitshoehe) {
        this.arbeitshoehe = arbeitshoehe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Bezeichnung);
        dest.writeString(this.GeraeettypID);
        dest.writeString(this.Hoehengruppe);
        dest.writeString(this.arbeitshoehe);
    }

    public SiteInspectionDeviceTypeModel() {
    }

    private SiteInspectionDeviceTypeModel(Parcel in) {
        this.Bezeichnung = in.readString();
        this.GeraeettypID = in.readString();
        this.Hoehengruppe = in.readString();
        this.arbeitshoehe = in.readString();
    }

    public static final Creator<SiteInspectionDeviceTypeModel> CREATOR = new Creator<SiteInspectionDeviceTypeModel>() {
        public SiteInspectionDeviceTypeModel createFromParcel(Parcel source) {
            return new SiteInspectionDeviceTypeModel(source);
        }

        public SiteInspectionDeviceTypeModel[] newArray(int size) {
            return new SiteInspectionDeviceTypeModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<SiteInspectionDeviceTypeModel> deviceTypes)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<SiteInspectionDeviceTypeModel>>(){}.getType();
        ArrayList<SiteInspectionDeviceTypeModel> types = gson.fromJson(jsonString, typedValue);
        deviceTypes.addAll(types);
    }
}
