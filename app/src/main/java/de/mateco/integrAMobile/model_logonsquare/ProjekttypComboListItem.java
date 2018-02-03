package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ProjekttypComboListItem{

	@SerializedName("Objekttyp")
	@JsonField(name ="Objekttyp")
	private String objekttyp;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setObjekttyp(String objekttyp){
		this.objekttyp = objekttyp;
	}

	public String getObjekttyp(){
		return objekttyp;
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
			"ProjekttypComboListItem{" + 
			"objekttyp = '" + objekttyp + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}