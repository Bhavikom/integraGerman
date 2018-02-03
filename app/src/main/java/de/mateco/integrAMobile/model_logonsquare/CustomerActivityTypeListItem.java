package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CustomerActivityTypeListItem{

	@SerializedName("akttyp")
	@JsonField(name ="akttyp")
	private String akttyp;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setAkttyp(String akttyp){
		this.akttyp = akttyp;
	}

	public String getAkttyp(){
		return akttyp;
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
			"CustomerActivityTypeListItem{" + 
			"akttyp = '" + akttyp + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
}