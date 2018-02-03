package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CustomerLandListItem{

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	@SerializedName("Land")
	@JsonField(name ="Land")
	private String land;

	public void setBezeichnung(String bezeichnung){
		this.bezeichnung = bezeichnung;
	}

	public String getBezeichnung(){
		return bezeichnung;
	}

	public void setLand(String land){
		this.land = land;
	}

	public String getLand(){
		return land;
	}

	@Override
 	public String toString(){
		return 
			"CustomerLandListItem{" + 
			"bezeichnung = '" + bezeichnung + '\'' + 
			",land = '" + land + '\'' + 
			"}";
		}
}