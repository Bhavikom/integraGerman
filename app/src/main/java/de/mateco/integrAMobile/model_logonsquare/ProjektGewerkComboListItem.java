package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ProjektGewerkComboListItem{

	@SerializedName("Gewerk")
	@JsonField(name ="Gewerk")
	private String gewerk;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setGewerk(String gewerk){
		this.gewerk = gewerk;
	}

	public String getGewerk(){
		return gewerk;
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
			"ProjektGewerkComboListItem{" + 
			"gewerk = '" + gewerk + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}