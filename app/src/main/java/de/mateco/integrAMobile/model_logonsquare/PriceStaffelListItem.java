package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class PriceStaffelListItem{

	@SerializedName("Aktiv")
	@JsonField(name ="Aktiv")
	private String aktiv;

	@SerializedName("BisEinheit")
	@JsonField(name ="BisEinheit")
	private String bisEinheit;

	@SerializedName("Staffel")
	@JsonField(name ="Staffel")
	private String staffel;

	@SerializedName("Einheit")
	@JsonField(name ="Einheit")
	private String einheit;

	@SerializedName("Angebotstext")
	@JsonField(name ="Angebotstext")
	private String angebotstext;

	@SerializedName("AbEinheit")
	@JsonField(name ="AbEinheit")
	private String abEinheit;

	@SerializedName("Sprache")
	@JsonField(name ="Sprache")
	private String sprache;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	@SerializedName("StandardStaffel")
	@JsonField(name ="StandardStaffel")
	private String standardStaffel;

	public void setAktiv(String aktiv){
		this.aktiv = aktiv;
	}

	public String getAktiv(){
		return aktiv;
	}

	public void setBisEinheit(String bisEinheit){
		this.bisEinheit = bisEinheit;
	}

	public String getBisEinheit(){
		return bisEinheit;
	}

	public void setStaffel(String staffel){
		this.staffel = staffel;
	}

	public String getStaffel(){
		return staffel;
	}

	public void setEinheit(String einheit){
		this.einheit = einheit;
	}

	public String getEinheit(){
		return einheit;
	}

	public void setAngebotstext(String angebotstext){
		this.angebotstext = angebotstext;
	}

	public String getAngebotstext(){
		return angebotstext;
	}

	public void setAbEinheit(String abEinheit){
		this.abEinheit = abEinheit;
	}

	public String getAbEinheit(){
		return abEinheit;
	}

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

	public void setStandardStaffel(String standardStaffel){
		this.standardStaffel = standardStaffel;
	}

	public String getStandardStaffel(){
		return standardStaffel;
	}

	@Override
 	public String toString(){
		return 
			"PriceStaffelListItem{" + 
			"aktiv = '" + aktiv + '\'' + 
			",bisEinheit = '" + bisEinheit + '\'' + 
			",staffel = '" + staffel + '\'' + 
			",einheit = '" + einheit + '\'' + 
			",angebotstext = '" + angebotstext + '\'' + 
			",abEinheit = '" + abEinheit + '\'' + 
			",sprache = '" + sprache + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			",standardStaffel = '" + standardStaffel + '\'' + 
			"}";
		}
}