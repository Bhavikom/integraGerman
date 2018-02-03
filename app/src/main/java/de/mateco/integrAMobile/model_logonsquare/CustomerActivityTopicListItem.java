package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CustomerActivityTopicListItem{

	@SerializedName("aktthema")
	@JsonField(name ="aktthema")
	private String aktthema;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setAktthema(String aktthema){
		this.aktthema = aktthema;
	}

	public String getAktthema(){
		return aktthema;
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
			"CustomerActivityTopicListItem{" + 
			"aktthema = '" + aktthema + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}