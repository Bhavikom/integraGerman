package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DocumentLanguageModel implements Parcelable
{
    @SerializedName("Sprache")
    private String documentLanguageId;
    @SerializedName("Bezeichnung")
    private String documentLanguageDesignation;

    public String getDocumentLanguageDesignation() {
        return documentLanguageDesignation;
    }

    public void setDocumentLanguageDesignation(String documentLanguageDesignation) {
        this.documentLanguageDesignation = documentLanguageDesignation;
    }

    public String getDocumentLanguageId() {
        return documentLanguageId;
    }

    public void setDocumentLanguageId(String documentLanguageId) {
        this.documentLanguageId = documentLanguageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.documentLanguageId);
        dest.writeString(this.documentLanguageDesignation);
    }

    public DocumentLanguageModel() {
    }

    private DocumentLanguageModel(Parcel in) {
        this.documentLanguageId = in.readString();
        this.documentLanguageDesignation = in.readString();
    }

    public static final Parcelable.Creator<DocumentLanguageModel> CREATOR = new Parcelable.Creator<DocumentLanguageModel>() {
        public DocumentLanguageModel createFromParcel(Parcel source) {
            return new DocumentLanguageModel(source);
        }

        public DocumentLanguageModel[] newArray(int size) {
            return new DocumentLanguageModel[size];
        }
    };

    public static void extractFromJson(String jsonString, ArrayList<DocumentLanguageModel> documentLanguages)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<DocumentLanguageModel>>(){}.getType();
        ArrayList<DocumentLanguageModel> documentLanguageList = gson.fromJson(jsonString, typedValue);
        documentLanguages.addAll(documentLanguageList);
    }
}
