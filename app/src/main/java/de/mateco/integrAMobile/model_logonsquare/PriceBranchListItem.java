package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class PriceBranchListItem{

	@SerializedName("Ort")
	@JsonField(name ="Ort")
	private String ort;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	@SerializedName("Strasse")
	@JsonField(name ="Strasse")
	private String strasse;

	@SerializedName("Mandant")
	@JsonField(name ="Mandant")
	private String mandant;

	@SerializedName("Plz")
	@JsonField(name ="Plz")
	private String plz;

	public void setOrt(String ort){
		this.ort = ort;
	}

	public String getOrt(){
		return ort;
	}

	public void setBezeichnung(String bezeichnung){
		this.bezeichnung = bezeichnung;
	}

	public String getBezeichnung(){
		return bezeichnung;
	}

	public void setStrasse(String strasse){
		this.strasse = strasse;
	}

	public String getStrasse(){
		return strasse;
	}

	public void setMandant(String mandant){
		this.mandant = mandant;
	}

	public String getMandant(){
		return mandant;
	}

	public void setPlz(String plz){
		this.plz = plz;
	}

	public String getPlz(){
		return plz;
	}

	@Override
 	public String toString(){
		return 
			"PriceBranchListItem{" + 
			"ort = '" + ort + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			",strasse = '" + strasse + '\'' + 
			",mandant = '" + mandant + '\'' + 
			",plz = '" + plz + '\'' + 
			"}";
		}
}