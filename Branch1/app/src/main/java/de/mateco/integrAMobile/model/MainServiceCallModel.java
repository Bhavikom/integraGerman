package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainServiceCallModel implements Parcelable
{
    @SerializedName("PriceStandardList")
    private ArrayList<PricingOfflineStandardPriceData> listOfStandardPrice;
    @SerializedName("PriceRentalList")
    private ArrayList<Pricing1PriceRentalData> listOfPriceRental;
    @SerializedName("PriceEquipmentHeightList")
    private ArrayList<PricingOfflineEquipmentData> listOfEquipmentHeight;
    @SerializedName("PriceDeviceGroupList")
    private ArrayList<Pricing1DeviceData> listOfDeviceGroup;
    @SerializedName("PriceBranchList")
    private ArrayList<Pricing1BranchData> listOfBranch;
    @SerializedName("CustomerRechtsFormComboList")
    private ArrayList<LegalFormModel> listOfLegalForm;
    @SerializedName("CustomerLandList")
    private ArrayList<CountryModel> listOfCountry;
    @SerializedName("CustomerContactPersonSalutationComboList")
    private ArrayList<SalutationModel> listOfSalutations;
    @SerializedName("CustomerContactPersonFunctionComboList")
    private ArrayList<FunctionModel> listOfFunctions;
    @SerializedName("CustomerContactPersonFeatureList")
    private ArrayList<FeatureModel> listOfFeatures;
    @SerializedName("CustomerContactPersonDocumentlanguageList")
    private ArrayList<DocumentLanguageModel> listOfDocumentLanguage;
    @SerializedName("CustomerContactPersonDecisionMakersList")
    private ArrayList<DecisionMakerModel> listOfDecisionMaker;
    @SerializedName("CustomerActivityTypeList")
    private ArrayList<ActivityTypeModel> listOfActivityType;
    @SerializedName("CustomerActivityTopicList")
    private ArrayList<ActivityTopicModel> listOfActivityTopic;
    @SerializedName("CustomerActivityEmployeeList")
    private ArrayList<EmployeeModel> listOfEmployee;
    @SerializedName("BVODeviceTypeList")
    private ArrayList<SiteInspectionDeviceTypeModel> listOfDeviceType;
    @SerializedName("BVOBauvorhabenComboList")
    private ArrayList<SiteInspectionBuildingProjectModel> listOfBuildingProject;
    @SerializedName("BVOZugangComboList")
    private ArrayList<SiteInspectionAccessModel> listOfAccess;
    @SerializedName("ProjektBUhnenAubenInnenComboList")
    private ArrayList<ProjectStagesModel> listOfProjectStage;
    @SerializedName("ProjektGebietComboList")
    private ArrayList<ProjectAreaModel> listOfArea;
    @SerializedName("ProjektartComboList")
    private ArrayList<ProjectArtModel> listOfProjectArt;
    @SerializedName("ProjekttypComboList")
    private ArrayList<ProjectTypeModel> listOfProjectType;
    @SerializedName("ProjektphaseComboList")
    private ArrayList<ProjectPhaseModel> listOfProjectPhase;
    @SerializedName("ProjektGewerkComboList")
    private ArrayList<ProjectTradeModel> listOfProjectTrade;
    @SerializedName("BrancheList")
    private ArrayList<CustomerBranchModel> listOfCustomerBranch;
    @SerializedName("listOfBuheneartComboBoxItem")
    private ArrayList<BuheneartModel> listOfBuheneart;

    public ArrayList<LadefahrzeugComboBoxItemModel> getArraylsitLadefahrzeug() {
        return arraylsitLadefahrzeug;
    }

    public void setArraylsitLadefahrzeug(ArrayList<LadefahrzeugComboBoxItemModel> arraylsitLadefahrzeug) {
        this.arraylsitLadefahrzeug = arraylsitLadefahrzeug;
    }

    @SerializedName("PriceStaffelList")
    private ArrayList<PriceStaffelModel> listOfPriceStaffel;

    @SerializedName("listOfLadefahrzeugComboBoxItem")
    private ArrayList<LadefahrzeugComboBoxItemModel> arraylsitLadefahrzeug;

    public ArrayList<EinsatzlandComboBoxItemModel> getArraylsitEinsatzland() {
        return arraylsitEinsatzland;
    }

    public void setArraylsitEinsatzland(ArrayList<EinsatzlandComboBoxItemModel> arraylsitEinsatzland) {
        this.arraylsitEinsatzland = arraylsitEinsatzland;
    }

    @SerializedName("EinsatzlandComboBoxItem")

    private ArrayList<EinsatzlandComboBoxItemModel> arraylsitEinsatzland;



    public ArrayList<PriceStaffelModel> getListOfPriceStaffel() {
        return listOfPriceStaffel;
    }

    public void setListOfPriceStaffel(ArrayList<PriceStaffelModel> listOfPriceStaffel) {
        this.listOfPriceStaffel = listOfPriceStaffel;
    }

    public ArrayList<BuheneartModel> getListOfBuheneart() {
        return listOfBuheneart;
    }

    public void setListOfBuheneart(ArrayList<BuheneartModel> listOfBuheneart) {
        this.listOfBuheneart = listOfBuheneart;
    }

    public static Creator<MainServiceCallModel> getCREATOR() {
        return CREATOR;
    }

    public ArrayList<PricingOfflineStandardPriceData> getListOfStandardPrice() {
        return listOfStandardPrice;
    }

    public void setListOfStandardPrice(ArrayList<PricingOfflineStandardPriceData> listOfStandardPrice) {
        this.listOfStandardPrice = listOfStandardPrice;
    }

    public ArrayList<Pricing1PriceRentalData> getListOfPriceRental() {
        return listOfPriceRental;
    }

    public void setListOfPriceRental(ArrayList<Pricing1PriceRentalData> listOfPriceRental) {
        this.listOfPriceRental = listOfPriceRental;
    }

    public ArrayList<PricingOfflineEquipmentData> getListOfEquipmentHeight() {
        return listOfEquipmentHeight;
    }

    public void setListOfEquipmentHeight(ArrayList<PricingOfflineEquipmentData> listOfEquipmentHeight) {
        this.listOfEquipmentHeight = listOfEquipmentHeight;
    }

    public ArrayList<Pricing1DeviceData> getListOfDeviceGroup() {
        return listOfDeviceGroup;
    }

    public void setListOfDeviceGroup(ArrayList<Pricing1DeviceData> listOfDeviceGroup) {
        this.listOfDeviceGroup = listOfDeviceGroup;
    }

    public ArrayList<Pricing1BranchData> getListOfBranch() {
        return listOfBranch;
    }

    public void setListOfBranch(ArrayList<Pricing1BranchData> listOfBranch) {
        this.listOfBranch = listOfBranch;
    }

    public ArrayList<LegalFormModel> getListOfLegalForm() {
        return listOfLegalForm;
    }

    public void setListOfLegalForm(ArrayList<LegalFormModel> listOfLegalForm) {
        this.listOfLegalForm = listOfLegalForm;
    }

    public ArrayList<CountryModel> getListOfCountry() {
        return listOfCountry;
    }

    public void setListOfCountry(ArrayList<CountryModel> listOfCountry) {
        this.listOfCountry = listOfCountry;
    }

    public ArrayList<SalutationModel> getListOfSalutations() {
        return listOfSalutations;
    }

    public void setListOfSalutations(ArrayList<SalutationModel> listOfSalutations) {
        this.listOfSalutations = listOfSalutations;
    }

    public ArrayList<FunctionModel> getListOfFunctions() {
        return listOfFunctions;
    }

    public void setListOfFunctions(ArrayList<FunctionModel> listOfFunctions) {
        this.listOfFunctions = listOfFunctions;
    }

    public ArrayList<FeatureModel> getListOfFeatures() {
        return listOfFeatures;
    }

    public void setListOfFeatures(ArrayList<FeatureModel> listOfFeatures) {
        this.listOfFeatures = listOfFeatures;
    }

    public ArrayList<DocumentLanguageModel> getListOfDocumentLanguage() {
        return listOfDocumentLanguage;
    }

    public void setListOfDocumentLanguage(ArrayList<DocumentLanguageModel> listOfDocumentLanguage) {
        this.listOfDocumentLanguage = listOfDocumentLanguage;
    }

    public ArrayList<DecisionMakerModel> getListOfDecisionMaker() {
        return listOfDecisionMaker;
    }

    public void setListOfDecisionMaker(ArrayList<DecisionMakerModel> listOfDecisionMaker) {
        this.listOfDecisionMaker = listOfDecisionMaker;
    }

    public ArrayList<ActivityTypeModel> getListOfActivityType() {
        return listOfActivityType;
    }

    public void setListOfActivityType(ArrayList<ActivityTypeModel> listOfActivityType) {
        this.listOfActivityType = listOfActivityType;
    }

    public ArrayList<ActivityTopicModel> getListOfActivityTopic() {
        return listOfActivityTopic;
    }

    public void setListOfActivityTopic(ArrayList<ActivityTopicModel> listOfActivityTopic) {
        this.listOfActivityTopic = listOfActivityTopic;
    }

    public ArrayList<EmployeeModel> getListOfEmployee() {
        return listOfEmployee;
    }

    public void setListOfEmployee(ArrayList<EmployeeModel> listOfEmployee) {
        this.listOfEmployee = listOfEmployee;
    }

    public ArrayList<SiteInspectionDeviceTypeModel> getListOfDeviceType() {
        return listOfDeviceType;
    }

    public void setListOfDeviceType(ArrayList<SiteInspectionDeviceTypeModel> listOfDeviceType) {
        this.listOfDeviceType = listOfDeviceType;
    }

    public ArrayList<SiteInspectionBuildingProjectModel> getListOfBuildingProject() {
        return listOfBuildingProject;
    }

    public void setListOfBuildingProject(ArrayList<SiteInspectionBuildingProjectModel> listOfBuildingProject) {
        this.listOfBuildingProject = listOfBuildingProject;
    }

    public ArrayList<SiteInspectionAccessModel> getListOfAccess() {
        return listOfAccess;
    }

    public void setListOfAccess(ArrayList<SiteInspectionAccessModel> listOfAccess) {
        this.listOfAccess = listOfAccess;
    }

    public ArrayList<ProjectStagesModel> getListOfProjectStage() {
        return listOfProjectStage;
    }

    public void setListOfProjectStage(ArrayList<ProjectStagesModel> listOfProjectStage) {
        this.listOfProjectStage = listOfProjectStage;
    }

    public ArrayList<ProjectAreaModel> getListOfArea() {
        return listOfArea;
    }

    public void setListOfArea(ArrayList<ProjectAreaModel> listOfArea) {
        this.listOfArea = listOfArea;
    }

    public ArrayList<ProjectArtModel> getListOfProjectArt() {
        return listOfProjectArt;
    }

    public void setListOfProjectArt(ArrayList<ProjectArtModel> listOfProjectArt) {
        this.listOfProjectArt = listOfProjectArt;
    }

    public ArrayList<ProjectTypeModel> getListOfProjectType() {
        return listOfProjectType;
    }

    public void setListOfProjectType(ArrayList<ProjectTypeModel> listOfProjectType) {
        this.listOfProjectType = listOfProjectType;
    }

    public ArrayList<ProjectPhaseModel> getListOfProjectPhase() {
        return listOfProjectPhase;
    }

    public void setListOfProjectPhase(ArrayList<ProjectPhaseModel> listOfProjectPhase) {
        this.listOfProjectPhase = listOfProjectPhase;
    }

    public ArrayList<ProjectTradeModel> getListOfProjectTrade() {
        return listOfProjectTrade;
    }

    public void setListOfProjectTrade(ArrayList<ProjectTradeModel> listOfProjectTrade) {
        this.listOfProjectTrade = listOfProjectTrade;
    }

    public ArrayList<CustomerBranchModel> getListOfCustomerBranch() {
        return listOfCustomerBranch;
    }

    public void setListOfCustomerBranch(ArrayList<CustomerBranchModel> listOfCustomerBranch) {
        this.listOfCustomerBranch = listOfCustomerBranch;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.listOfStandardPrice);
        dest.writeSerializable(this.listOfPriceRental);
        dest.writeSerializable(this.listOfEquipmentHeight);
        dest.writeSerializable(this.listOfDeviceGroup);
        dest.writeSerializable(this.listOfBranch);
        dest.writeSerializable(this.listOfLegalForm);
        dest.writeSerializable(this.listOfCountry);
        dest.writeSerializable(this.listOfSalutations);
        dest.writeSerializable(this.listOfFunctions);
        dest.writeSerializable(this.listOfFeatures);
        dest.writeSerializable(this.listOfDocumentLanguage);
        dest.writeSerializable(this.listOfDecisionMaker);
        dest.writeSerializable(this.listOfActivityType);
        dest.writeSerializable(this.listOfActivityTopic);
        dest.writeSerializable(this.listOfEmployee);
        dest.writeSerializable(this.listOfDeviceType);
        dest.writeSerializable(this.listOfBuildingProject);
        dest.writeSerializable(this.listOfAccess);
        dest.writeSerializable(this.listOfProjectStage);
        dest.writeSerializable(this.listOfArea);
        dest.writeSerializable(this.listOfProjectArt);
        dest.writeSerializable(this.listOfProjectType);
        dest.writeSerializable(this.listOfProjectPhase);
        dest.writeSerializable(this.listOfProjectTrade);
        dest.writeSerializable(this.listOfCustomerBranch);
        dest.writeSerializable(this.listOfPriceStaffel);
        dest.writeSerializable(this.arraylsitLadefahrzeug);
    }

    public MainServiceCallModel() {
    }

    private MainServiceCallModel(Parcel in) {
        this.listOfStandardPrice = (ArrayList<PricingOfflineStandardPriceData>) in.readSerializable();
        this.listOfPriceRental = (ArrayList<Pricing1PriceRentalData>) in.readSerializable();
        this.listOfEquipmentHeight = (ArrayList<PricingOfflineEquipmentData>) in.readSerializable();
        this.listOfDeviceGroup = (ArrayList<Pricing1DeviceData>) in.readSerializable();
        this.listOfBranch = (ArrayList<Pricing1BranchData>) in.readSerializable();
        this.listOfLegalForm = (ArrayList<LegalFormModel>) in.readSerializable();
        this.listOfCountry = (ArrayList<CountryModel>) in.readSerializable();
        this.listOfSalutations = (ArrayList<SalutationModel>) in.readSerializable();
        this.listOfFunctions = (ArrayList<FunctionModel>) in.readSerializable();
        this.listOfFeatures = (ArrayList<FeatureModel>) in.readSerializable();
        this.listOfDocumentLanguage = (ArrayList<DocumentLanguageModel>) in.readSerializable();
        this.listOfDecisionMaker = (ArrayList<DecisionMakerModel>) in.readSerializable();
        this.listOfActivityType = (ArrayList<ActivityTypeModel>) in.readSerializable();
        this.listOfActivityTopic = (ArrayList<ActivityTopicModel>) in.readSerializable();
        this.listOfEmployee = (ArrayList<EmployeeModel>) in.readSerializable();
        this.listOfDeviceType = (ArrayList<SiteInspectionDeviceTypeModel>) in.readSerializable();
        this.listOfBuildingProject = (ArrayList<SiteInspectionBuildingProjectModel>) in.readSerializable();
        this.listOfAccess = (ArrayList<SiteInspectionAccessModel>) in.readSerializable();
        this.listOfProjectStage = (ArrayList<ProjectStagesModel>) in.readSerializable();
        this.listOfArea = (ArrayList<ProjectAreaModel>) in.readSerializable();
        this.listOfProjectArt = (ArrayList<ProjectArtModel>) in.readSerializable();
        this.listOfProjectType = (ArrayList<ProjectTypeModel>) in.readSerializable();
        this.listOfProjectPhase = (ArrayList<ProjectPhaseModel>) in.readSerializable();
        this.listOfProjectTrade = (ArrayList<ProjectTradeModel>) in.readSerializable();
        this.listOfCustomerBranch = (ArrayList<CustomerBranchModel>) in.readSerializable();
        this.listOfPriceStaffel = (ArrayList<PriceStaffelModel>) in.readSerializable();
        this.arraylsitLadefahrzeug =(ArrayList<LadefahrzeugComboBoxItemModel>) in.readSerializable();
        this.arraylsitEinsatzland =(ArrayList<EinsatzlandComboBoxItemModel>) in.readSerializable();
    }

    public static final Creator<MainServiceCallModel> CREATOR = new Creator<MainServiceCallModel>() {
        public MainServiceCallModel createFromParcel(Parcel source) {
            return new MainServiceCallModel(source);
        }

        public MainServiceCallModel[] newArray(int size) {
            return new MainServiceCallModel[size];
        }
    };
}
