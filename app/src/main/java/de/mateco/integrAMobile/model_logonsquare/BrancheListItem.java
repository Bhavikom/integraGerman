package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class BrancheListItem{

	@SerializedName("BrancheName")
	@JsonField(name ="BrancheName")
	private String brancheName;

	@SerializedName("BrancheID")
	@JsonField(name ="BrancheID")
	private String brancheID;

	public void setBrancheName(String brancheName){
		this.brancheName = brancheName;
	}

	public String getBrancheName(){
		return brancheName;
	}

	public void setBrancheID(String brancheID){
		this.brancheID = brancheID;
	}

	public String getBrancheID(){
		return brancheID;
	}

	@Override
 	public String toString(){
		return 
			"BrancheListItem{" + 
			"brancheName = '" + brancheName + '\'' + 
			",brancheID = '" + brancheID + '\'' + 
			"}";
		}
}