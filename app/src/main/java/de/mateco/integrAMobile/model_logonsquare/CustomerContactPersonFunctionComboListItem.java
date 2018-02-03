package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CustomerContactPersonFunctionComboListItem{

	@SerializedName("Funktion")
	@JsonField(name ="Funktion")
	private String funktion;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setFunktion(String funktion){
		this.funktion = funktion;
	}

	public String getFunktion(){
		return funktion;
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
			"CustomerContactPersonFunctionComboListItem{" + 
			"funktion = '" + funktion + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}