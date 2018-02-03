package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class BVOBauvorhabenComboListItem{

	@SerializedName("Bauvorhaben")
	@JsonField(name ="Bauvorhaben")
	private String bauvorhaben;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setBauvorhaben(String bauvorhaben){
		this.bauvorhaben = bauvorhaben;
	}

	public String getBauvorhaben(){
		return bauvorhaben;
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
			"BVOBauvorhabenComboListItem{" + 
			"bauvorhaben = '" + bauvorhaben + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}