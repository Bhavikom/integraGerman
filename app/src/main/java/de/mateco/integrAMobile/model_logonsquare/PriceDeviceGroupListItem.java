package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class PriceDeviceGroupListItem{

	@SerializedName("bezeichnung")
	@JsonField(name ="bezeichnung")
	private String bezeichnung;

	@SerializedName("Hoehenhauptgruppe")
	@JsonField(name ="Hoehenhauptgruppe")
	private String hoehenhauptgruppe;

	public void setBezeichnung(String bezeichnung){
		this.bezeichnung = bezeichnung;
	}

	public String getBezeichnung(){
		return bezeichnung;
	}

	public void setHoehenhauptgruppe(String hoehenhauptgruppe){
		this.hoehenhauptgruppe = hoehenhauptgruppe;
	}

	public String getHoehenhauptgruppe(){
		return hoehenhauptgruppe;
	}

	@Override
 	public String toString(){
		return 
			"PriceDeviceGroupListItem{" + 
			"bezeichnung = '" + bezeichnung + '\'' + 
			",hoehenhauptgruppe = '" + hoehenhauptgruppe + '\'' + 
			"}";
		}
}