package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ProjektGebietComboListItem{

	@SerializedName("Gebiet")
	@JsonField(name ="Gebiet")
	private String gebiet;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setGebiet(String gebiet){
		this.gebiet = gebiet;
	}

	public String getGebiet(){
		return gebiet;
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
			"ProjektGebietComboListItem{" + 
			"gebiet = '" + gebiet + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}