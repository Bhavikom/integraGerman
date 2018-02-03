package de.mateco.integrAMobile.model_logonsquare;


import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.IOException;
import java.util.List;

@JsonObject
public class BVODeviceTypeListItem{

	@SerializedName("GeraeettypID")
	@JsonField(name ="GeraeettypID")
	private String geraeettypID;

	@SerializedName("Hoehengruppe")
	@JsonField(name ="Hoehengruppe")
	private String hoehengruppe;

	@SerializedName("arbeitshoehe")
	@JsonField(name ="arbeitshoehe")
	private String arbeitshoehe;

	@SerializedName("Bezeichnung")
	@JsonField(name ="Bezeichnung")
	private String bezeichnung;

	public void setGeraeettypID(String geraeettypID){
		this.geraeettypID = geraeettypID;
	}

	public String getGeraeettypID(){
		return geraeettypID;
	}

	public void setHoehengruppe(String hoehengruppe){
		this.hoehengruppe = hoehengruppe;
	}

	public String getHoehengruppe(){
		return hoehengruppe;
	}

	public void setArbeitshoehe(String arbeitshoehe){
		this.arbeitshoehe = arbeitshoehe;
	}

	public String getArbeitshoehe(){
		return arbeitshoehe;
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
			"BVODeviceTypeListItem{" + 
			"geraeettypID = '" + geraeettypID + '\'' + 
			",hoehengruppe = '" + hoehengruppe + '\'' + 
			",arbeitshoehe = '" + arbeitshoehe + '\'' + 
			",bezeichnung = '" + bezeichnung + '\'' + 
			"}";
		}
	public static void extractFromJson(String jsonString, List<BVODeviceTypeListItem> deviceTypes)
	{
		/*Gson gson = new Gson();
		Type typedValue = new TypeToken<ArrayList<SiteInspectionDeviceTypeModel>>(){}.getType();
		ArrayList<SiteInspectionDeviceTypeModel> types = gson.fromJson(jsonString, typedValue);
		deviceTypes.addAll(types);*/

		try {
			deviceTypes = LoganSquare.parseList(jsonString,BVODeviceTypeListItem.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}