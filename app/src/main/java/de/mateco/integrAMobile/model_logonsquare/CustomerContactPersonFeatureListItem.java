package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CustomerContactPersonFeatureListItem{

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
}