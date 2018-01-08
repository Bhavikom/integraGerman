package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FunctionModel implements Parcelable
{
    @SerializedName("Bezeichnung")
    private String functionDesignation;
    @SerializedName("Funktion")
    private String functionId;

    public String getFunctionDesignation() {
        return functionDesignation;
    }

    public void setFunctionDesignation(String functionDesignation) {
        this.functionDesignation = functionDesignation;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.functionDesignation);
        dest.writeString(this.functionId);
    }

    public FunctionModel() {
    }

    private FunctionModel(Parcel in) {
        this.functionDesignation = in.readString();
        this.functionId = in.readString();
    }

    public static final Parcelable.Creator<FunctionModel> CREATOR = new Parcelable.Creator<FunctionModel>() {
        public FunctionModel createFromParcel(Parcel source) {
            return new FunctionModel(source);
        }

        public FunctionModel[] newArray(int size) {
            return new FunctionModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<FunctionModel> countries)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<FunctionModel>>(){}.getType();
        ArrayList<FunctionModel> functionList = gson.fromJson(jsonString, typedValue);
        countries.addAll(functionList);
    }
}
