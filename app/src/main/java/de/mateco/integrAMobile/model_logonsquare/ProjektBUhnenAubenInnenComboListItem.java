package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ProjektBUhnenAubenInnenComboListItem{

	@SerializedName("Buehnenart")
	@JsonField(name ="Buehnenart")
	private String buehnenart;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setBuehnenart(String buehnenart){
		this.buehnenart = buehnenart;
	}

	public String getBuehnenart(){
		return buehnenart;
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
			"ProjektBUhnenAubenInnenComboListItem{" + 
			"buehnenart = '" + buehnenart + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}