package de.mateco.integrAMobile.model_logonsquare;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@JsonObject
public class ResponseMain {

	@SerializedName("CustomerActivityTopicList")
	@JsonField(name ="CustomerActivityTopicList")
	private List<CustomerActivityTopicListItem> customerActivityTopicList;

	@SerializedName("ProjektGewerkComboList")
	@JsonField(name ="ProjektGewerkComboList")
	private List<ProjektGewerkComboListItem> projektGewerkComboList;

	@SerializedName("listOfBuheneartComboBoxItem")
	@JsonField(name ="listOfBuheneartComboBoxItem")
	private List<ListOfBuheneartComboBoxItemItem> listOfBuheneartComboBoxItem;

	@SerializedName("CustomerRechtsFormComboList")
	@JsonField(name ="CustomerRechtsFormComboList")
	private List<CustomerRechtsFormComboListItem> customerRechtsFormComboList;

	@SerializedName("PriceEquipmentHeightList")
	@JsonField(name ="PriceEquipmentHeightList")
	private List<PriceEquipmentHeightListItem> priceEquipmentHeightList;

	@SerializedName("CustomerContactPersonFeatureList")
	@JsonField(name ="CustomerContactPersonFeatureList")
	private List<CustomerContactPersonFeatureListItem> customerContactPersonFeatureList;

	@SerializedName("CustomerContactPersonDocumentlanguageList")
	@JsonField(name ="CustomerContactPersonDocumentlanguageList")
	private List<CustomerContactPersonDocumentlanguageListItem> customerContactPersonDocumentlanguageList;

	@SerializedName("PriceStandardList")
	@JsonField(name ="PriceStandardList")
	private List<PriceStandardListItem> priceStandardList;

	@SerializedName("ProjektGebietComboList")
	@JsonField(name ="ProjektGebietComboList")
	private List<ProjektGebietComboListItem> projektGebietComboList;

	@SerializedName("UserRecordList")
	@JsonField(name ="UserRecordList")
	private List<UserRecordListItem> userRecordList;

	@SerializedName("CustomerActivityTypeList")
	@JsonField(name ="CustomerActivityTypeList")
	private List<CustomerActivityTypeListItem> customerActivityTypeList;

	@SerializedName("PriceDeviceGroupList")
	@JsonField(name ="PriceDeviceGroupList")
	private List<PriceDeviceGroupListItem> priceDeviceGroupList;

	@SerializedName("listOfLadefahrzeugComboBoxItem")
	@JsonField(name ="listOfLadefahrzeugComboBoxItem")
	private List<ListOfLadefahrzeugComboBoxItemItem> listOfLadefahrzeugComboBoxItem;

	@SerializedName("PriceBranchList")
	@JsonField(name ="PriceBranchList")
	private List<PriceBranchListItem> priceBranchList;

	@SerializedName("PriceRentalList")
	@JsonField(name ="PriceRentalList")
	private List<PriceRentalListItem> priceRentalList;

	@SerializedName("PriceStaffelList")
	@JsonField(name ="PriceStaffelList")
	private List<PriceStaffelListItem> priceStaffelList;

	@SerializedName("CustomerContactPersonSalutationComboList")
	@JsonField(name ="CustomerContactPersonSalutationComboList")
	private List<CustomerContactPersonSalutationComboListItem> customerContactPersonSalutationComboList;

	@SerializedName("ProjekttypComboList")
	@JsonField(name ="ProjekttypComboList")
	private List<ProjekttypComboListItem> projekttypComboList;

	@SerializedName("ProjektBUhnenAubenInnenComboList")
	@JsonField(name ="ProjektBUhnenAubenInnenComboList")
	private List<ProjektBUhnenAubenInnenComboListItem> projektBUhnenAubenInnenComboList;

	@SerializedName("CustomerActivityEmployeeList")
	@JsonField(name ="CustomerActivityEmployeeList")
	private List<CustomerActivityEmployeeListItem> customerActivityEmployeeList;

	@SerializedName("CustomerContactPersonDecisionMakersList")
	@JsonField(name ="CustomerContactPersonDecisionMakersList")
	private List<CustomerContactPersonDecisionMakersListItem> customerContactPersonDecisionMakersList;

	@SerializedName("ProjektphaseComboList")
	@JsonField(name ="ProjektphaseComboList")
	private List<ProjektphaseComboListItem> projektphaseComboList;

	@SerializedName("BVOBauvorhabenComboList")
	@JsonField(name ="BVOBauvorhabenComboList")
	private List<BVOBauvorhabenComboListItem> bVOBauvorhabenComboList;

	@SerializedName("BrancheList")
	@JsonField(name ="BrancheList")
	private List<BrancheListItem> brancheList;

	@SerializedName("BVODeviceTypeList")
	@JsonField(name ="BVODeviceTypeList")
	private List<BVODeviceTypeListItem> bVODeviceTypeList;

	@SerializedName("CustomerContactPersonFunctionComboList")
	@JsonField(name ="CustomerContactPersonFunctionComboList")
	private List<CustomerContactPersonFunctionComboListItem> customerContactPersonFunctionComboList;

	@SerializedName("BVOZugangComboList")
	@JsonField(name ="BVOZugangComboList")
	private List<BVOZugangComboListItem> bVOZugangComboList;

	@SerializedName("CustomerLandList")
	@JsonField(name ="CustomerLandList")
	private List<CustomerLandListItem> customerLandList;

	@SerializedName("ProjektartComboList")
	@JsonField(name ="ProjektartComboList")
	private List<ProjektartComboListItem> projektartComboList;

	public void setCustomerActivityTopicList(List<CustomerActivityTopicListItem> customerActivityTopicList){
		this.customerActivityTopicList = customerActivityTopicList;
	}

	public List<CustomerActivityTopicListItem> getCustomerActivityTopicList(){
		return customerActivityTopicList;
	}

	public void setProjektGewerkComboList(List<ProjektGewerkComboListItem> projektGewerkComboList){
		this.projektGewerkComboList = projektGewerkComboList;
	}

	public List<ProjektGewerkComboListItem> getProjektGewerkComboList(){
		return projektGewerkComboList;
	}

	public void setListOfBuheneartComboBoxItem(List<ListOfBuheneartComboBoxItemItem> listOfBuheneartComboBoxItem){
		this.listOfBuheneartComboBoxItem = listOfBuheneartComboBoxItem;
	}

	public List<ListOfBuheneartComboBoxItemItem> getListOfBuheneartComboBoxItem(){
		return listOfBuheneartComboBoxItem;
	}

	public void setCustomerRechtsFormComboList(List<CustomerRechtsFormComboListItem> customerRechtsFormComboList){
		this.customerRechtsFormComboList = customerRechtsFormComboList;
	}

	public List<CustomerRechtsFormComboListItem> getCustomerRechtsFormComboList(){
		return customerRechtsFormComboList;
	}

	public void setPriceEquipmentHeightList(List<PriceEquipmentHeightListItem> priceEquipmentHeightList){
		this.priceEquipmentHeightList = priceEquipmentHeightList;
	}

	public List<PriceEquipmentHeightListItem> getPriceEquipmentHeightList(){
		return priceEquipmentHeightList;
	}

	public void setCustomerContactPersonFeatureList(List<CustomerContactPersonFeatureListItem> customerContactPersonFeatureList){
		this.customerContactPersonFeatureList = customerContactPersonFeatureList;
	}

	public List<CustomerContactPersonFeatureListItem> getCustomerContactPersonFeatureList(){
		return customerContactPersonFeatureList;
	}

	public void setCustomerContactPersonDocumentlanguageList(List<CustomerContactPersonDocumentlanguageListItem> customerContactPersonDocumentlanguageList){
		this.customerContactPersonDocumentlanguageList = customerContactPersonDocumentlanguageList;
	}

	public List<CustomerContactPersonDocumentlanguageListItem> getCustomerContactPersonDocumentlanguageList(){
		return customerContactPersonDocumentlanguageList;
	}

	public void setPriceStandardList(List<PriceStandardListItem> priceStandardList){
		this.priceStandardList = priceStandardList;
	}

	public List<PriceStandardListItem> getPriceStandardList(){
		return priceStandardList;
	}

	public void setProjektGebietComboList(List<ProjektGebietComboListItem> projektGebietComboList){
		this.projektGebietComboList = projektGebietComboList;
	}

	public List<ProjektGebietComboListItem> getProjektGebietComboList(){
		return projektGebietComboList;
	}

	public void setUserRecordList(List<UserRecordListItem> userRecordList){
		this.userRecordList = userRecordList;
	}

	public List<UserRecordListItem> getUserRecordList(){
		return userRecordList;
	}

	public void setCustomerActivityTypeList(List<CustomerActivityTypeListItem> customerActivityTypeList){
		this.customerActivityTypeList = customerActivityTypeList;
	}

	public List<CustomerActivityTypeListItem> getCustomerActivityTypeList(){
		return customerActivityTypeList;
	}

	public void setPriceDeviceGroupList(List<PriceDeviceGroupListItem> priceDeviceGroupList){
		this.priceDeviceGroupList = priceDeviceGroupList;
	}

	public List<PriceDeviceGroupListItem> getPriceDeviceGroupList(){
		return priceDeviceGroupList;
	}

	public void setListOfLadefahrzeugComboBoxItem(List<ListOfLadefahrzeugComboBoxItemItem> listOfLadefahrzeugComboBoxItem){
		this.listOfLadefahrzeugComboBoxItem = listOfLadefahrzeugComboBoxItem;
	}

	public List<ListOfLadefahrzeugComboBoxItemItem> getListOfLadefahrzeugComboBoxItem(){
		return listOfLadefahrzeugComboBoxItem;
	}

	public void setPriceBranchList(List<PriceBranchListItem> priceBranchList){
		this.priceBranchList = priceBranchList;
	}

	public List<PriceBranchListItem> getPriceBranchList(){
		return priceBranchList;
	}

	public void setPriceRentalList(List<PriceRentalListItem> priceRentalList){
		this.priceRentalList = priceRentalList;
	}

	public List<PriceRentalListItem> getPriceRentalList(){
		return priceRentalList;
	}

	public void setPriceStaffelList(List<PriceStaffelListItem> priceStaffelList){
		this.priceStaffelList = priceStaffelList;
	}

	public List<PriceStaffelListItem> getPriceStaffelList(){
		return priceStaffelList;
	}

	public void setCustomerContactPersonSalutationComboList(List<CustomerContactPersonSalutationComboListItem> customerContactPersonSalutationComboList){
		this.customerContactPersonSalutationComboList = customerContactPersonSalutationComboList;
	}

	public List<CustomerContactPersonSalutationComboListItem> getCustomerContactPersonSalutationComboList(){
		return customerContactPersonSalutationComboList;
	}

	public void setProjekttypComboList(List<ProjekttypComboListItem> projekttypComboList){
		this.projekttypComboList = projekttypComboList;
	}

	public List<ProjekttypComboListItem> getProjekttypComboList(){
		return projekttypComboList;
	}

	public void setProjektBUhnenAubenInnenComboList(List<ProjektBUhnenAubenInnenComboListItem> projektBUhnenAubenInnenComboList){
		this.projektBUhnenAubenInnenComboList = projektBUhnenAubenInnenComboList;
	}

	public List<ProjektBUhnenAubenInnenComboListItem> getProjektBUhnenAubenInnenComboList(){
		return projektBUhnenAubenInnenComboList;
	}

	public void setCustomerActivityEmployeeList(List<CustomerActivityEmployeeListItem> customerActivityEmployeeList){
		this.customerActivityEmployeeList = customerActivityEmployeeList;
	}

	public List<CustomerActivityEmployeeListItem> getCustomerActivityEmployeeList(){
		return customerActivityEmployeeList;
	}

	public void setCustomerContactPersonDecisionMakersList(List<CustomerContactPersonDecisionMakersListItem> customerContactPersonDecisionMakersList){
		this.customerContactPersonDecisionMakersList = customerContactPersonDecisionMakersList;
	}

	public List<CustomerContactPersonDecisionMakersListItem> getCustomerContactPersonDecisionMakersList(){
		return customerContactPersonDecisionMakersList;
	}

	public void setProjektphaseComboList(List<ProjektphaseComboListItem> projektphaseComboList){
		this.projektphaseComboList = projektphaseComboList;
	}

	public List<ProjektphaseComboListItem> getProjektphaseComboList(){
		return projektphaseComboList;
	}

	public void setBVOBauvorhabenComboList(List<BVOBauvorhabenComboListItem> bVOBauvorhabenComboList){
		this.bVOBauvorhabenComboList = bVOBauvorhabenComboList;
	}

	public List<BVOBauvorhabenComboListItem> getBVOBauvorhabenComboList(){
		return bVOBauvorhabenComboList;
	}

	public void setBrancheList(List<BrancheListItem> brancheList){
		this.brancheList = brancheList;
	}

	public List<BrancheListItem> getBrancheList(){
		return brancheList;
	}

	public void setBVODeviceTypeList(List<BVODeviceTypeListItem> bVODeviceTypeList){
		this.bVODeviceTypeList = bVODeviceTypeList;
	}

	public List<BVODeviceTypeListItem> getBVODeviceTypeList(){
		return bVODeviceTypeList;
	}

	public void setCustomerContactPersonFunctionComboList(List<CustomerContactPersonFunctionComboListItem> customerContactPersonFunctionComboList){
		this.customerContactPersonFunctionComboList = customerContactPersonFunctionComboList;
	}

	public List<CustomerContactPersonFunctionComboListItem> getCustomerContactPersonFunctionComboList(){
		return customerContactPersonFunctionComboList;
	}

	public void setBVOZugangComboList(List<BVOZugangComboListItem> bVOZugangComboList){
		this.bVOZugangComboList = bVOZugangComboList;
	}

	public List<BVOZugangComboListItem> getBVOZugangComboList(){
		return bVOZugangComboList;
	}

	public void setCustomerLandList(List<CustomerLandListItem> customerLandList){
		this.customerLandList = customerLandList;
	}

	public List<CustomerLandListItem> getCustomerLandList(){
		return customerLandList;
	}

	public void setProjektartComboList(List<ProjektartComboListItem> projektartComboList){
		this.projektartComboList = projektartComboList;
	}

	public List<ProjektartComboListItem> getProjektartComboList(){
		return projektartComboList;
	}

	@Override
 	public String toString(){
		return 
			"ResponseMain{" +
			"customerActivityTopicList = '" + customerActivityTopicList + '\'' + 
			",projektGewerkComboList = '" + projektGewerkComboList + '\'' + 
			",listOfBuheneartComboBoxItem = '" + listOfBuheneartComboBoxItem + '\'' + 
			",customerRechtsFormComboList = '" + customerRechtsFormComboList + '\'' + 
			",priceEquipmentHeightList = '" + priceEquipmentHeightList + '\'' + 
			",customerContactPersonFeatureList = '" + customerContactPersonFeatureList + '\'' + 
			",customerContactPersonDocumentlanguageList = '" + customerContactPersonDocumentlanguageList + '\'' + 
			",priceStandardList = '" + priceStandardList + '\'' + 
			",projektGebietComboList = '" + projektGebietComboList + '\'' + 
			",userRecordList = '" + userRecordList + '\'' + 
			",customerActivityTypeList = '" + customerActivityTypeList + '\'' + 
			",priceDeviceGroupList = '" + priceDeviceGroupList + '\'' + 
			",listOfLadefahrzeugComboBoxItem = '" + listOfLadefahrzeugComboBoxItem + '\'' + 
			",priceBranchList = '" + priceBranchList + '\'' + 
			",priceRentalList = '" + priceRentalList + '\'' + 
			",priceStaffelList = '" + priceStaffelList + '\'' + 
			",customerContactPersonSalutationComboList = '" + customerContactPersonSalutationComboList + '\'' + 
			",projekttypComboList = '" + projekttypComboList + '\'' + 
			",projektBUhnenAubenInnenComboList = '" + projektBUhnenAubenInnenComboList + '\'' + 
			",customerActivityEmployeeList = '" + customerActivityEmployeeList + '\'' + 
			",customerContactPersonDecisionMakersList = '" + customerContactPersonDecisionMakersList + '\'' + 
			",projektphaseComboList = '" + projektphaseComboList + '\'' + 
			",bVOBauvorhabenComboList = '" + bVOBauvorhabenComboList + '\'' + 
			",brancheList = '" + brancheList + '\'' + 
			",bVODeviceTypeList = '" + bVODeviceTypeList + '\'' + 
			",customerContactPersonFunctionComboList = '" + customerContactPersonFunctionComboList + '\'' + 
			",bVOZugangComboList = '" + bVOZugangComboList + '\'' + 
			",customerLandList = '" + customerLandList + '\'' + 
			",projektartComboList = '" + projektartComboList + '\'' + 
			"}";
		}
}