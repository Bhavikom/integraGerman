package de.mateco.integrAMobile.model_logonsquare;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CustomerContactPersonFeatureListItem implements Parcelable {

	@SerializedName("Merkmal")
	@JsonField(name ="Merkmal")
	private String merkmal;

	@SerializedName("Kategorie")
	@JsonField(name ="Kategorie")
	private String kategorie;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setMerkmal(String merkmal){
		this.merkmal = merkmal;
	}

	public String getMerkmal(){
		return merkmal;
	}

	public void setKategorie(String kategorie){
		this.kategorie = kategorie;
	}

	public String getKategorie(){
		return kategorie;
	}

	public void setBezeichnung(String bezeichnung){
		this.bezeichnung = bezeichnung;
	}

	public String getBezeichnung(){
		return bezeichnung;
	}

	@Override
 	public String toString(){
		return 
			"CustomerContactPersonFeatureListItem{" + 
			"merkmal = '" + merkmal + '\'' + 
			",kategorie = '" + kategorie + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}
	public CustomerContactPersonFeatureListItem() {
	}
	private CustomerContactPersonFeatureListItem(Parcel in) {
		this.merkmal = in.readString();
		this.bezeichnung = in.readString();
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}
	public static final Creator<CustomerContactPersonFeatureListItem> CREATOR = new Creator<CustomerContactPersonFeatureListItem>() {
		public CustomerContactPersonFeatureListItem createFromParcel(Parcel source) {
			return new CustomerContactPersonFeatureListItem(source);
		}

		public CustomerContactPersonFeatureListItem[] newArray(int size) {
			return new CustomerContactPersonFeatureListItem[size];
		}
	};
}