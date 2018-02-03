package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ListOfLadefahrzeugComboBoxItemItem{

	@SerializedName("Id")
	@JsonField(name ="Id")
	private int id;

	@SerializedName("Sprache")
	@JsonField(name ="Sprache")
	private int sprache;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSprache(int sprache){
		this.sprache = sprache;
	}

	public int getSprache(){
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
			"ListOfLadefahrzeugComboBoxItemItem{" + 
			"id = '" + id + '\'' + 
			",sprache = '" + sprache + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}