package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CustomerContactPersonDocumentlanguageListItem{

	@SerializedName("Sprache")
	@JsonField(name ="Sprache")
	private String sprache;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setSprache(String sprache){
		this.sprache = sprache;
	}

	public String getSprache(){
		return sprache;
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
			"CustomerContactPersonDocumentlanguageListItem{" + 
			"sprache = '" + sprache + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}