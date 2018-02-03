package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ProjektphaseComboListItem{

	@SerializedName("Objektphase")
	@JsonField(name ="Objektphase")
	private String objektphase;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setObjektphase(String objektphase){
		this.objektphase = objektphase;
	}

	public String getObjektphase(){
		return objektphase;
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
			"ProjektphaseComboListItem{" + 
			"objektphase = '" + objektphase + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}