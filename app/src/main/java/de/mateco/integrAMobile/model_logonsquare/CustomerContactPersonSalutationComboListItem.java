package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CustomerContactPersonSalutationComboListItem{

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	@SerializedName("Anrede")
	@JsonField(name ="Anrede")
	private String anrede;

	public void setBezeichnung(String bezeichnung){
		this.bezeichnung = bezeichnung;
	}

	public String getBezeichnung(){
		return bezeichnung;
	}

	public void setAnrede(String anrede){
		this.anrede = anrede;
	}

	public String getAnrede(){
		return anrede;
	}

	@Override
 	public String toString(){
		return 
			"CustomerContactPersonSalutationComboListItem{" + 
			"bezeichnung = '" + bezeichnung + '\'' + 
			",anrede = '" + anrede + '\'' + 
			"}";
		}
}