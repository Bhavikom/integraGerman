package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
public class CustomerContactPersonDecisionMakersListItem{

	@SerializedName("Entscheider")
	@JsonField(name ="Entscheider")
	private String entscheider;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setEntscheider(String entscheider){
		this.entscheider = entscheider;
	}

	public String getEntscheider(){
		return entscheider;
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
			"CustomerContactPersonDecisionMakersListItem{" + 
			"entscheider = '" + entscheider + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}