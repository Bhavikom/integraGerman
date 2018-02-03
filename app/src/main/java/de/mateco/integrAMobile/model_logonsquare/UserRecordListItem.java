package de.mateco.integrAMobile.model_logonsquare;


import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class UserRecordListItem{

	@SerializedName("NumberRange")
	@JsonField(name ="NumberRange")
	private String numberRange;

	@SerializedName("UserName")
	@JsonField(name ="UserName")
	private String userName;

	@SerializedName("UserDesignation")
	@JsonField(name ="UserDesignation")
	private String userDesignation;

	@SerializedName("UserId")
	@JsonField(name ="UserId")
	private int userId;

	@SerializedName("Country")
	@JsonField(name ="Country")
	private String country;

	@SerializedName("UserEmail")
	@JsonField(name ="UserEmail")
	private String userEmail;

	@SerializedName("BranchCode")
	@JsonField(name ="BranchCode")
	private String branchCode;

	@SerializedName("Location")
	@JsonField(name ="Location")
	private String location;

	@SerializedName("UserNumber")
	@JsonField(name ="UserNumber")
	private String userNumber;

	public void setNumberRange(String numberRange){
		this.numberRange = numberRange;
	}

	public String getNumberRange(){
		return numberRange;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserDesignation(String userDesignation){
		this.userDesignation = userDesignation;
	}

	public String getUserDesignation(){
		return userDesignation;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setUserEmail(String userEmail){
		this.userEmail = userEmail;
	}

	public String getUserEmail(){
		return userEmail;
	}

	public void setBranchCode(String branchCode){
		this.branchCode = branchCode;
	}

	public String getBranchCode(){
		return branchCode;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setUserNumber(String userNumber){
		this.userNumber = userNumber;
	}

	public String getUserNumber(){
		return userNumber;
	}

	@Override
 	public String toString(){
		return 
			"UserRecordListItem{" + 
			"numberRange = '" + numberRange + '\'' + 
			",userName = '" + userName + '\'' + 
			",userDesignation = '" + userDesignation + '\'' + 
			",userId = '" + userId + '\'' + 
			",country = '" + country + '\'' + 
			",userEmail = '" + userEmail + '\'' + 
			",branchCode = '" + branchCode + '\'' + 
			",location = '" + location + '\'' + 
			",userNumber = '" + userNumber + '\'' + 
			"}";
		}
}