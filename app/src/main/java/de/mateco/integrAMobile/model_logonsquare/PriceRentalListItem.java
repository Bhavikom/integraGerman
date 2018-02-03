package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
public class PriceRentalListItem{

	@SerializedName("einheit")
	@JsonField(name ="einheit")
	private String einheit;

	@SerializedName("bezeichnung")
	@JsonField(name ="bezeichnung")
	private String bezeichnung;

	public void setEinheit(String einheit){
		this.einheit = einheit;
	}

	public String getEinheit(){
		return einheit;
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
			"PriceRentalListItem{" + 
			"einheit = '" + einheit + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}