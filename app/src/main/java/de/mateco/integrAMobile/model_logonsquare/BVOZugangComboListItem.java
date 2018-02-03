package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
public class BVOZugangComboListItem{

	@SerializedName("Zugang")
	@JsonField(name ="Zugang")
	private String zugang;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setZugang(String zugang){
		this.zugang = zugang;
	}

	public String getZugang(){
		return zugang;
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
			"BVOZugangComboListItem{" + 
			"zugang = '" + zugang + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}