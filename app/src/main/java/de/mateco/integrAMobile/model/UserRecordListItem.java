package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserRecordListItem implements Parcelable{

	@SerializedName("NumberRange")
	private String numberRange;

	@SerializedName("UserName")
	private String userName;

	@SerializedName("UserDesignation")
	private String userDesignation;

	@SerializedName("UserId")
	private int userId;

	@SerializedName("Country")
	private String country;

	@SerializedName("UserEmail")
	private String userEmail;

	@SerializedName("BranchCode")
	private String branchCode;

	@SerializedName("Location")
	private String location;

	@SerializedName("UserNumber")
	private String userNumber;

	protected UserRecordListItem(Parcel in) {
		numberRange = in.readString();
		userName = in.readString();
		userDesignation = in.readString();
		userId = in.readInt();
		country = in.readString();
		userEmail = in.readString();
		branchCode = in.readString();
		location = in.readString();
		userNumber = in.readString();
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.numberRange);
		dest.writeString(this.userName);
		dest.writeString(this.userDesignation);
		dest.writeInt(this.userId);
		dest.writeString(this.country);
		dest.writeString(this.userEmail);
		dest.writeString(this.branchCode);
		dest.writeString(this.location);
		dest.writeString(this.userNumber);


	}
	public static final Creator<UserRecordListItem> CREATOR = new Creator<UserRecordListItem>() {
		@Override
		public UserRecordListItem createFromParcel(Parcel in) {
			return new UserRecordListItem(in);
		}

		@Override
		public UserRecordListItem[] newArray(int size) {
			return new UserRecordListItem[size];
		}
	};

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

	@Override
	public int describeContents() {
		return 0;
	}
}