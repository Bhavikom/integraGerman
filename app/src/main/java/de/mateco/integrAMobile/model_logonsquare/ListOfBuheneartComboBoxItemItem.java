package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ListOfBuheneartComboBoxItemItem{

	@SerializedName("Sort")
	@JsonField(name ="Sort")
	private int sort;

	@SerializedName("Sprache")
	@JsonField(name ="Sprache")
	private int sprache;

	@SerializedName("BuehnenArt")
	@JsonField(name ="BuehnenArt")
	private int buehnenArt;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setSort(int sort){
		this.sort = sort;
	}

	public int getSort(){
		return sort;
	}

	public void setSprache(int sprache){
		this.sprache = sprache;
	}

	public int getSprache(){
		return sprache;
	}

	public void setBuehnenArt(int buehnenArt){
		this.buehnenArt = buehnenArt;
	}

	public int getBuehnenArt(){
		return buehnenArt;
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
			"ListOfBuheneartComboBoxItemItem{" + 
			"sort = '" + sort + '\'' + 
			",sprache = '" + sprache + '\'' + 
			",buehnenArt = '" + buehnenArt + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}