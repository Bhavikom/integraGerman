package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
public class PriceEquipmentHeightListItem{

	@SerializedName("Ausstattung")
	@JsonField(name ="Ausstattung")
	private String ausstattung;

	@SerializedName("Hoehengruppe")
	@JsonField(name ="Hoehengruppe")
	private String hoehengruppe;

	@SerializedName("bezeichnung2")
	@JsonField(name ="bezeichnung2")
	private String bezeichnung2;

	@SerializedName("Hoehenhauptgruppe")
	@JsonField(name ="Hoehenhauptgruppe")
	private String hoehenhauptgruppe;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setAusstattung(String ausstattung){
		this.ausstattung = ausstattung;
	}

	public String getAusstattung(){
		return ausstattung;
	}

	public void setHoehengruppe(String hoehengruppe){
		this.hoehengruppe = hoehengruppe;
	}

	public String getHoehengruppe(){
		return hoehengruppe;
	}

	public void setBezeichnung2(String bezeichnung2){
		this.bezeichnung2 = bezeichnung2;
	}

	public String getBezeichnung2(){
		return bezeichnung2;
	}

	public void setHoehenhauptgruppe(String hoehenhauptgruppe){
		this.hoehenhauptgruppe = hoehenhauptgruppe;
	}

	public String getHoehenhauptgruppe(){
		return hoehenhauptgruppe;
	}

	public void setBezeichnung(String bezeichnung){
		this.bezeichnung2 = bezeichnung;
	}

	public String getBezeichnung(){
		return bezeichnung2;
	}

	@Override
 	public String toString(){
		return 
			"PriceEquipmentHeightListItem{" + 
			"ausstattung = '" + ausstattung + '\'' + 
			",hoehengruppe = '" + hoehengruppe + '\'' + 
			",bezeichnung2 = '" + bezeichnung2 + '\'' +
			",hoehenhauptgruppe = '" + hoehenhauptgruppe + '\'' + 
			",bezeichnung2 = '" + bezeichnung2 + '\'' +
			"}";
		}
}