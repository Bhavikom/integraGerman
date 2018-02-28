package de.mateco.integrAMobile.Helper;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import de.mateco.integrAMobile.model.Language;

/**
 * used for parsing languagexml.txt file from assets
 */
public class ItemXMLHandler extends DefaultHandler {

    Boolean currentElement = false;
    String currentValue = "";
    Language language = null;
    private ArrayList<Language> itemsList = new ArrayList<Language>();

    public ArrayList<Language> getItemsList() {
        return itemsList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        currentElement = true;
        currentValue = "";
        if (localName.equals("Language")) {
            language = new Language();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        currentElement = false;
        if (localName.equalsIgnoreCase("languageName"))
            language.setLangName(currentValue);
        else if (localName.equalsIgnoreCase("languageCode"))
            language.setLangCode(currentValue);
        else if (localName.equalsIgnoreCase("languageFlag"))
            language.setLangFlag(currentValue);
        else if (localName.equalsIgnoreCase("labelCustomerNo"))
            language.setLabelCustomerNo(currentValue);
        else if (localName.equalsIgnoreCase("labelKanr"))
            language.setLabelKanr(currentValue);
        else if (localName.equalsIgnoreCase("labelMatchCode"))
            language.setLabelMatchCode(currentValue);
        else if (localName.equalsIgnoreCase("labelRoad"))
            language.setLabelRoad(currentValue);
        else if (localName.equalsIgnoreCase("labelZipCode"))
            language.setLabelZipCode(currentValue);
        else if (localName.equalsIgnoreCase("labelPlace"))
            language.setLabelPlace(currentValue);
        else if (localName.equalsIgnoreCase("labelTelephone"))
            language.setLabelTelephone(currentValue);
        else if (localName.equalsIgnoreCase("labelBonusIndex"))
            language.setLabelBonusIndex(currentValue);
        else if (localName.equalsIgnoreCase("labelSalesRunning"))
            language.setLabelSalesRunning(currentValue);
        else if (localName.equalsIgnoreCase("labelSalesPreviousYear"))
            language.setLabelSalesPreviousYear(currentValue);
        else if (localName.equalsIgnoreCase("labelSalesLatest"))
            language.setLabelSalesLatest(currentValue);
        else if (localName.equalsIgnoreCase("labelGeneral"))
            language.setLabelGeneral(currentValue);
        else if (localName.equalsIgnoreCase("labelContacts"))
            language.setLabelContacts(currentValue);
        else if (localName.equalsIgnoreCase("labelName"))
            language.setLabelName(currentValue);
        else if (localName.equalsIgnoreCase("labelCountry"))
            language.setLabelCountry(currentValue);
        else if (localName.equalsIgnoreCase("labelPhone"))
            language.setLabelPhone(currentValue);
        else if (localName.equalsIgnoreCase("labelFax"))
            language.setLabelFax(currentValue);
        else if (localName.equalsIgnoreCase("labelBusiness"))
            language.setLabelBusiness(currentValue);
        else if (localName.equalsIgnoreCase("labelEmail"))
            language.setLabelEmail(currentValue);
        else if (localName.equalsIgnoreCase("labelArea"))
            language.setLabelArea(currentValue);
        else if (localName.equalsIgnoreCase("labelResponsiblePerson"))
            language.setLabelResponsiblePerson(currentValue);
        else if (localName.equalsIgnoreCase("labelCreditLimit"))
            language.setLabelCreditLimit(currentValue);
        else if (localName.equalsIgnoreCase("labelPayment"))
            language.setLabelPayment(currentValue);
        else if (localName.equalsIgnoreCase("labelAddDate"))
            language.setLabelAddDate(currentValue);
        else if (localName.equalsIgnoreCase("labelCustomerBonus"))
            language.setLabelCustomerBonus(currentValue);
        else if (localName.equalsIgnoreCase("labelWithContract"))
            language.setLabelWithContract(currentValue);
        else if (localName.equalsIgnoreCase("labelSalutation"))
            language.setLabelSalutation(currentValue);
        else if (localName.equalsIgnoreCase("labelTitle"))
            language.setLabelTitle(currentValue);
        else if (localName.equalsIgnoreCase("labelFirstName"))
            language.setLabelFirstName(currentValue);
        else if (localName.equalsIgnoreCase("labelLastName"))
            language.setLabelLastName(currentValue);
        else if (localName.equalsIgnoreCase("labelFunction"))
            language.setLabelFunction(currentValue);
        else if (localName.equalsIgnoreCase("labelDecisionMakers"))
            language.setLabelDecisionMakers(currentValue);
        else if (localName.equalsIgnoreCase("labelAdditionalInfo"))
            language.setLabelAdditionalInfo(currentValue);
        else if (localName.equalsIgnoreCase("labelDocumentLanguage"))
            language.setLabelDocumentLanguage(currentValue);
        else if (localName.equalsIgnoreCase("labelFeature"))
            language.setLabelFeature(currentValue);
        else if (localName.equalsIgnoreCase("labelMobile"))
            language.setLabelMobile(currentValue);
        else if (localName.equalsIgnoreCase("labelWebsite"))
            language.setLabelWebsite(currentValue);
        else if (localName.equalsIgnoreCase("labelLegalForm"))
            language.setLabelLegalForm(currentValue);
        else if (localName.equalsIgnoreCase("labelOwner"))
            language.setLabelOwner(currentValue);
        else if (localName.equalsIgnoreCase("labelVatNo"))
            language.setLabelVatNo(currentValue);
        else if (localName.equalsIgnoreCase("labelOrderNo"))
            language.setLabelOrderNo(currentValue);
        else if (localName.equalsIgnoreCase("labelPaymentDeadline"))
            language.setLabelPaymentDeadline(currentValue);
        else if (localName.equalsIgnoreCase("labelOrderBacklog"))
            language.setLabelOrderBacklog(currentValue);
        else if (localName.equalsIgnoreCase("labelSalesPotential"))
            language.setLabelSalesPotential(currentValue);
        else if (localName.equalsIgnoreCase("labelActivity"))
            language.setLabelActivity(currentValue);
        else if (localName.equalsIgnoreCase("labelTopic"))
            language.setLabelTopic(currentValue);
        else if (localName.equalsIgnoreCase("labelDate"))
            language.setLabelDate(currentValue);
        else if (localName.equalsIgnoreCase("labelStartTime"))
            language.setLabelStartTime(currentValue);
        else if (localName.equalsIgnoreCase("labelEndTime"))
            language.setLabelEndTime(currentValue);
        else if (localName.equalsIgnoreCase("labelOffer"))
            language.setLabelOffer(currentValue);
        else if (localName.equalsIgnoreCase("labelProject"))
            language.setLabelProject(currentValue);
        else if (localName.equalsIgnoreCase("labelRealized"))
            language.setLabelRealized(currentValue);
        else if (localName.equalsIgnoreCase("labelFixedTime"))
            language.setLabelFixedTime(currentValue);
        else if (localName.equalsIgnoreCase("labelEmployee"))
            language.setLabelEmployee(currentValue);
        else if (localName.equalsIgnoreCase("labelOpenOrders"))
            language.setLabelOpenOrders(currentValue);
        else if (localName.equalsIgnoreCase("labelContractNo"))
            language.setLabelContractNo(currentValue);
        else if (localName.equalsIgnoreCase("labelNL"))
            language.setLabelNL(currentValue);
        else if (localName.equalsIgnoreCase("labelHGRP"))
            language.setLabelHGRP(currentValue);
        else if (localName.equalsIgnoreCase("labelDesiredDevice"))
            language.setLabelDesiredDevice(currentValue);
        else if (localName.equalsIgnoreCase("labelDelivered"))
            language.setLabelDelivered(currentValue);
        else if (localName.equalsIgnoreCase("labelArrival"))
            language.setLabelArrival(currentValue);
        else if (localName.equalsIgnoreCase("labelMD"))
            language.setLabelMD(currentValue);
        else if (localName.equalsIgnoreCase("labelUnit"))
            language.setLabelUnit(currentValue);
        else if (localName.equalsIgnoreCase("labelLP"))
            language.setLabelLP(currentValue);
        else if (localName.equalsIgnoreCase("labelCape"))
            language.setLabelCape(currentValue);
        else if (localName.equalsIgnoreCase("labelTP"))
            language.setLabelTP(currentValue);
        else if (localName.equalsIgnoreCase("labelSB"))
            language.setLabelSB(currentValue);

        else if (localName.equalsIgnoreCase("labelBest"))
            language.setLabelBest(currentValue);
        else if (localName.equalsIgnoreCase("labelSP"))
            language.setLabelSP(currentValue);
        else if (localName.equalsIgnoreCase("labelHB"))
            language.setLableHB(currentValue);

        else if (localName.equalsIgnoreCase("labelCompletedOffers"))
            language.setLabelCompletedOffers(currentValue);
        else if (localName.equalsIgnoreCase("labelOpenOffer"))
            language.setLabelOpenOffer(currentValue);
        else if (localName.equalsIgnoreCase("labelCreatedOn"))
            language.setLabelCreatedOn(currentValue);
        else if (localName.equalsIgnoreCase("labelDeviceType"))
            language.setLabelDeviceType(currentValue);
        else if (localName.equalsIgnoreCase("labelQuantity"))
            language.setLabelQuantity(currentValue);
        else if (localName.equalsIgnoreCase("labelRelay"))
            language.setLabelRelay(currentValue);
        else if (localName.equalsIgnoreCase("labelPrice"))
            language.setLabelPrice(currentValue);
        else if (localName.equalsIgnoreCase("labelHaftb"))
            language.setLabelHaftb(currentValue);
        else if (localName.equalsIgnoreCase("labelStatus"))
            language.setLabelStatus(currentValue);
        else if (localName.equalsIgnoreCase("labelLostSale"))
            language.setLabelLostSale(currentValue);
        else if (localName.equalsIgnoreCase("labelRentalPrice"))
            language.setLabelRentalPrice(currentValue);
        else if (localName.equalsIgnoreCase("labelReasonForRejection"))
            language.setLabelReasonForRejection(currentValue);
        else if (localName.equalsIgnoreCase("labelGenerally"))
            language.setLabelGenerally(currentValue);
        else if (localName.equalsIgnoreCase("labelTrades"))
            language.setLabelTrades(currentValue);
        else if (localName.equalsIgnoreCase("labelNotes"))
            language.setLabelNotes(currentValue);
        else if (localName.equalsIgnoreCase("labelStartOfConstruction"))
            language.setLabelStartOfConstruction(currentValue);
        else if (localName.equalsIgnoreCase("labelEndOfConstruction"))
            language.setLabelEndOfConstruction(currentValue);
        else if (localName.equalsIgnoreCase("labelSize"))
            language.setLabelSize(currentValue);
        else if (localName.equalsIgnoreCase("labelHeight"))
            language.setLabelHeight(currentValue);
        else if (localName.equalsIgnoreCase("labelTypeOfProject"))
            language.setLabelTypeOfProject(currentValue);
        else if (localName.equalsIgnoreCase("labelProjectType"))
            language.setLabelProjectType(currentValue);
        else if (localName.equalsIgnoreCase("labelActPhase"))
            language.setLabelActPhase(currentValue);
        else if (localName.equalsIgnoreCase("labelDateOfUpdate"))
            language.setLabelDateOfUpdate(currentValue);
        else if (localName.equalsIgnoreCase("labelStatesADM"))
            language.setLabelStatesADM(currentValue);
        else if (localName.equalsIgnoreCase("labelAbgeschlosse"))
            language.setLabelAbgeschlosse(currentValue);
        else if (localName.equalsIgnoreCase("labelRamp"))
            language.setLabelRamp(currentValue);
        else if (localName.equalsIgnoreCase("labelMatureWhite"))
            language.setLabelMatureWhite(currentValue);
        else if (localName.equalsIgnoreCase("labelDesignation"))
            language.setLabelDesignation(currentValue);
        else if (localName.equalsIgnoreCase("labelAnspPartner"))
            language.setLabelAnspPartner(currentValue);
        else if (localName.equalsIgnoreCase("labelPurchaser"))
            language.setLabelPurchaser(currentValue);
        else if (localName.equalsIgnoreCase("labelApOnSite"))
            language.setLabelApOnSite(currentValue);
        else if (localName.equalsIgnoreCase("labelInsertDate"))
            language.setLabelInsertDate(currentValue);
        else if (localName.equalsIgnoreCase("labelOperationalLife"))
            language.setLabelOperationalLife(currentValue);
        else if (localName.equalsIgnoreCase("labelBuildingProject"))
            language.setLabelBuildingProject(currentValue);
        else if (localName.equalsIgnoreCase("labelAccess"))
            language.setLabelAccess(currentValue);
        else if (localName.equalsIgnoreCase("labelRegistrationRequired"))
            language.setLabelRegistrationRequired(currentValue);
        else if (localName.equalsIgnoreCase("labelWorkToBePerformed"))
            language.setLabelWorkToBePerformed(currentValue);
        else if (localName.equalsIgnoreCase("labelKnickPointHigh"))
            language.setLabelKnickPointHigh(currentValue);
        else if (localName.equalsIgnoreCase("labelInterferingEdgesAt"))
            language.setLabelInterferingEdgesAt(currentValue);
        else if (localName.equalsIgnoreCase("labelOther"))
            language.setLabelOther(currentValue);
        else if (localName.equalsIgnoreCase("labelToday"))
            language.setLabelToday(currentValue);
        else if (localName.equalsIgnoreCase("labelCustomer"))
            language.setLabelCustomer(currentValue);
        else if (localName.equalsIgnoreCase("labelCustomerDetails"))
            language.setLabelCustomerDetails(currentValue);
        else if (localName.equalsIgnoreCase("labelPropertyDetails"))
            language.setLabelPropertyDetails(currentValue);
        else if (localName.equalsIgnoreCase("labelOfferDetails"))
            language.setLabelOfferDetails(currentValue);
        else if (localName.equalsIgnoreCase("labelNotice"))
            language.setLabelNotice(currentValue);
        else if (localName.equalsIgnoreCase("labelActivityDetails"))
            language.setLabelActivityDetails(currentValue);
        else if (localName.equalsIgnoreCase("labelOverDue"))
            language.setLabelOverDue(currentValue);
        else if (localName.equalsIgnoreCase("labelFuture"))
            language.setLabelFuture(currentValue);
        else if (localName.equalsIgnoreCase("labelFrom"))
            language.setLabelFrom(currentValue);
        else if (localName.equalsIgnoreCase("labelTotalOutput"))
            language.setLabelTotalOutput(currentValue);
        else if (localName.equalsIgnoreCase("labelFixedTimes"))
            language.setLabelFixedTimes(currentValue);
        else if (localName.equalsIgnoreCase("labelOk"))
            language.setLabelOk(currentValue);
        else if (localName.equalsIgnoreCase("labelAbort"))
            language.setLabelAbort(currentValue);
        else if (localName.equalsIgnoreCase("labelAppSetting"))
            language.setLabelAppSetting(currentValue);
        else if (localName.equalsIgnoreCase("labelAccountSetting"))
            language.setLabelAccountSetting(currentValue);

        else if (localName.equalsIgnoreCase("labelSatellite"))
            language.setLabelSatellite(currentValue);
        else if (localName.equalsIgnoreCase("labelNormal"))
            language.setLabelNormal(currentValue);

        else if (localName.equalsIgnoreCase("labelOfflineData"))
            language.setLabelOfflineData(currentValue);
        else if (localName.equalsIgnoreCase("labelHelp"))
            language.setLabelHelp(currentValue);
        else if (localName.equalsIgnoreCase("labelRefreshInterval"))
            language.setLabelRefreshInterval(currentValue);
        else if (localName.equalsIgnoreCase("labelLanguage"))
            language.setLabelLanguage(currentValue);
        else if (localName.equalsIgnoreCase("labelOfflineViewData"))
            language.setLabelOfflineViewData(currentValue);
        else if (localName.equalsIgnoreCase("labelEmailAddress"))
            language.setLabelEmailAddress(currentValue);
        else if (localName.equalsIgnoreCase("labelPassword"))
            language.setLabelPassword(currentValue);
        else if (localName.equalsIgnoreCase("labelAddress"))
            language.setLabelAddress(currentValue);
        else if (localName.equalsIgnoreCase("labelLastSyncTime"))
            language.setLabelLastSyncTime(currentValue);
        else if (localName.equalsIgnoreCase("labelSyncNow"))
            language.setLabelSyncNow(currentValue);
        else if (localName.equalsIgnoreCase("labelCleanUpOfflineData"))
            language.setLabelCleanUpOfflineData(currentValue);
        else if (localName.equalsIgnoreCase("labelMemo"))
            language.setLabelMemo(currentValue);
        else if (localName.equalsIgnoreCase("labelHideRetired"))
            language.setLabelHideRetired(currentValue);
        else if (localName.equalsIgnoreCase("labelCustomerDistributionType"))
            language.setLabelCustomerDistributionType(currentValue);
        else if (localName.equalsIgnoreCase("labelCustomerTypeTrend"))
            language.setLabelCustomerTypeTrend(currentValue);
        else if (localName.equalsIgnoreCase("labelLostSaleReason"))
            language.setLabelLostSaleReason(currentValue);
        else if (localName.equalsIgnoreCase("labelUsIdNr"))
            language.setLabelUsIdNr(currentValue);
        else if (localName.equalsIgnoreCase("labelLevelGroup"))
            language.setLabelLevelGroup(currentValue);
        else if (localName.equalsIgnoreCase("labelEquipment"))
            language.setLabelEquipment(currentValue);
        else if (localName.equalsIgnoreCase("labelRental"))
            language.setLabelRental(currentValue);
        else if (localName.equalsIgnoreCase("labelIntegraCalenderFrom"))
            language.setLabelIntegraCalenderFrom(currentValue);
        else if (localName.equalsIgnoreCase("labelTo"))
            language.setLabelTo(currentValue);
        else if (localName.equalsIgnoreCase("labelImportHelp"))
            language.setLabelImportHelp(currentValue);
        else if (localName.equalsIgnoreCase("labelContactNo"))
            language.setLabelContactNo(currentValue);
        else if (localName.equalsIgnoreCase("labelFiveTenTargetPrice"))
            language.setLabelFiveTenTargetPrice(currentValue);
        else if (localName.equalsIgnoreCase("labelDayMiniPrice"))
            language.setLabelDayMiniPrice1(currentValue);
        else if (localName.equalsIgnoreCase("labelElevenTwentyTargetPrice"))
            language.setLabelElevenTwentyTargetPrice(currentValue);
        else if (localName.equalsIgnoreCase("labelAboveTwentyOneTargetPrice"))
            language.setLabelAboveTwentyOneTargetPrice(currentValue);
        else if (localName.equalsIgnoreCase("labelTransport"))
            language.setLabelTransport(currentValue);
        else if (localName.equalsIgnoreCase("labelApproach"))
            language.setLabelApproach(currentValue);
        else if (localName.equalsIgnoreCase("labelDeparture"))
            language.setLabelDeparture(currentValue);
        else if (localName.equalsIgnoreCase("labelAdditionalCargoPrice"))
            language.setLabelAdditionalCargoPrice(currentValue);
        else if (localName.equalsIgnoreCase("labelFlateRate"))
            language.setLabelFlateRate(currentValue);
        else if (localName.equalsIgnoreCase("labelLandUse"))
            language.setLabelLandUse(currentValue);
        else if (localName.equalsIgnoreCase("labelUseZipCode"))
            language.setLabelUseZipCode(currentValue);
        else if (localName.equalsIgnoreCase("labelOfUse"))
            language.setLabelOfUse(currentValue);
        else if (localName.equalsIgnoreCase("labelReadUse"))
            language.setLabelReadUse(currentValue);
        else if (localName.equalsIgnoreCase("labelAddition"))
            language.setLabelAddition(currentValue);
        else if (localName.equalsIgnoreCase("labelContractSpot"))
            language.setLabelContractSpot(currentValue);
        else if (localName.equalsIgnoreCase("labelAnspTelefon"))
            language.setLabelAnspTelefon(currentValue);
        else if (localName.equalsIgnoreCase("labelTollKilometers"))
            language.setLabelTollKilometers(currentValue);
        else if (localName.equalsIgnoreCase("labelCost"))
            language.setLabelCost(currentValue);
        else if (localName.equalsIgnoreCase("labelHandllingFee"))
            language.setLabelHandlingFee(currentValue);
        else if (localName.equalsIgnoreCase("labelNewAddress"))
            language.setLabelNewAddress(currentValue);
        else if (localName.equalsIgnoreCase("labelCurrentAddress"))
            language.setLabelCurrentAddress(currentValue);
        else if (localName.equalsIgnoreCase("messageBvoUploadFailed"))
            language.setMessageBvoUploadFailed(currentValue);
        else if(localName.equalsIgnoreCase("labelInsertData"))
            language.setLabelInsertData(currentValue);
        else if(localName.equalsIgnoreCase("labelSiteInspectionWithoutCustomer"))
            language.setLabelSiteInspectionWithoutCustomer(currentValue);
        else if(localName.equalsIgnoreCase("labelMap"))
            language.setLabelMap(currentValue);
        else if(localName.equalsIgnoreCase("labelUpdate"))
            language.setLabelUpdate(currentValue);
        else if(localName.equalsIgnoreCase("labelCamera"))
            language.setLabelCamera(currentValue);
        else if(localName.equalsIgnoreCase("labelGallery"))
            language.setLabelGallery(currentValue);
        else if(localName.equalsIgnoreCase("labelPracticability"))
            language.setLabelPracticability(currentValue);
        else if(localName.equalsIgnoreCase("labelSummer"))
            language.setLabelSummer(currentValue);
        else if(localName.equalsIgnoreCase("labelWinter"))
            language.setLabelWinter(currentValue);
        else if(localName.equalsIgnoreCase("labelWetness"))
            language.setLabelWetness(currentValue);
        else if(localName.equalsIgnoreCase("LabelDrivingPlates"))
            language.setLabelDrivingPlates(currentValue);
        else if(localName.equalsIgnoreCase("labelBongossiPlates"))
            language.setLabelBongossiPlates(currentValue);
        else if(localName.equalsIgnoreCase("labelPublicRoadCountry"))
            language.setLabelPublicRoadCountry(currentValue);
        else if(localName.equalsIgnoreCase("labelPrivateProperty"))
            language.setLabelPrivateProperty(currentValue);
        else if(localName.equalsIgnoreCase("labelInterior"))
            language.setLabelInterior(currentValue);
        else if(localName.equalsIgnoreCase("labelPowerLiftSystem"))
            language.setLabelPowerLiftSystem(currentValue);
        else if(localName.equalsIgnoreCase("labelPowerGenerator"))
            language.setLabelPowerGenerator(currentValue);
        else if(localName.equalsIgnoreCase("labelOther"))
            language.setLabelOther(currentValue);
        else if(localName.equalsIgnoreCase("labelAccessObstruction"))
            language.setLabelAccessObstruction(currentValue);
        else if(localName.equalsIgnoreCase("labelDesclaimer"))
            language.setLabelDesclaimer(currentValue);
        else if(localName.equalsIgnoreCase("labelAccessForest"))
            language.setLabelAccessForest(currentValue);
        else if(localName.equalsIgnoreCase("labelWhich"))
            language.setLabelWhich(currentValue);
        else if(localName.equalsIgnoreCase("labelFor"))
            language.setLabelFor(currentValue);
        else if(localName.equalsIgnoreCase("labelAccessLying"))
            language.setLabelAccessLying(currentValue);
        else if(localName.equalsIgnoreCase("labelStk"))
            language.setLabelStk(currentValue);
        else if(localName.equalsIgnoreCase("labelDirections"))
            language.setLabelDirections(currentValue);
        else if(localName.equalsIgnoreCase("labelVerdSewers"))
            language.setLabelVerdSewers(currentValue);
        else if(localName.equalsIgnoreCase("labelLoadAccess"))
            language.setLabelLoadAccess(currentValue);
        else if(localName.equalsIgnoreCase("labelLoadStand"))
            language.setLabelLoadStand(currentValue);
        else if(localName.equalsIgnoreCase("labelMaxTrafficLoad"))
            language.setLabelMaxTrafficLoad(currentValue);
        else if(localName.equalsIgnoreCase("labelAdditionalSupportingMaterial"))
            language.setLabelAdditionalSupportingMaterial(currentValue);
        else if(localName.equalsIgnoreCase("labelUndergroundStand"))
            language.setLabelUndergroundStand(currentValue);
        else if(localName.equalsIgnoreCase("labelRoadGrade"))
            language.setLabelRoadGrade(currentValue);
        else if(localName.equalsIgnoreCase("labelAdditionalAccessaries"))
            language.setLabelAdditionalAccessories(currentValue);
        else if(localName.equalsIgnoreCase("labelAcquisitionDate"))
            language.setLabelAcquisitionDate(currentValue);
        else if(localName.equalsIgnoreCase("labelAuthOwner"))
            language.setLabelAuthOwner(currentValue);
        else if(localName.equalsIgnoreCase("labelAnsbach"))
            language.setLabelAnsbach(currentValue);
        else if(localName.equalsIgnoreCase("labelFaxNo"))
            language.setLabelFaxNo(currentValue);
        else if(localName.equalsIgnoreCase("labelIsFord"))
            language.setLabelIsFord(currentValue);
        else if(localName.equalsIgnoreCase("labelAuthCommunity"))
            language.setLabelAuthCommunity(currentValue);
        else if(localName.equalsIgnoreCase("labelAuthPt"))
            language.setLabelAuthPt(currentValue);
        else if(localName.equalsIgnoreCase("labelAuthTaxiAssociation"))
            language.setLabelAuthTaxiAssociation(currentValue);
        else if(localName.equalsIgnoreCase("labelAbsperrma"))
            language.setLabelAbsperrma(currentValue);
        else if(localName.equalsIgnoreCase("labelSketch"))
            language.setLabelSketch(currentValue);
        else if(localName.equalsIgnoreCase("labelTrafficSignPlanRequired"))
            language.setLabelTrafficSignPlanRequired(currentValue);
        else if(localName.equalsIgnoreCase("labelPreparationOfStoppingSigns"))
            language.setLabelPreparationOfStoppingSigns(currentValue);
        else if(localName.equalsIgnoreCase("labelLayingBusStop"))
            language.setLabelLayingBusStop(currentValue);
        else if(localName.equalsIgnoreCase("labelTransferTaxiStand"))
            language.setLabelTransferTaxiStand(currentValue);
        else if(localName.equalsIgnoreCase("labelLayingHandicappedParking"))
            language.setLabelLayingHandicappedParking(currentValue);
        else if(localName.equalsIgnoreCase("labelDeviceGroup"))
            language.setLabelDeviceGroup(currentValue);
        else if(localName.equalsIgnoreCase("labelHeightScale"))
            language.setLabelHeightScale(currentValue);
        else if(localName.equalsIgnoreCase("labelAlternativeDeviceType"))
            language.setLabelAlternativeDeviceType(currentValue);
        else if(localName.equalsIgnoreCase("labelWorkinhHeight"))
            language.setLabelWorkinhHeight(currentValue);
        else if(localName.equalsIgnoreCase("labelLateralReach"))
            language.setLabelLateralReach(currentValue);
        else if(localName.equalsIgnoreCase("labelMaxLength"))
            language.setLabelMaxLength(currentValue);
        else if(localName.equalsIgnoreCase("labelMaxBreadth"))
            language.setLabelMaxBreadth(currentValue);
        else if(localName.equalsIgnoreCase("labelMaxHeight"))
            language.setLabelMaxHeight(currentValue);
        else if(localName.equalsIgnoreCase("labelMaxWeight"))
            language.setLabelMaxWeight(currentValue);
        else if(localName.equalsIgnoreCase("labelBasketLoad"))
            language.setLabelBasketLoad(currentValue);
        else if(localName.equalsIgnoreCase("labelBoomLength"))
            language.setLabelBoomLength(currentValue);
        else if(localName.equalsIgnoreCase("labelDiesel"))
            language.setLabelDiesel(currentValue);
        else if(localName.equalsIgnoreCase("labelElectrostatics"))
            language.setLabelElectrostatics(currentValue);
        else if(localName.equalsIgnoreCase("labelFourWheel"))
            language.setLabelFourWheel(currentValue);
        else if(localName.equalsIgnoreCase("labelChainRubber"))
            language.setLabelChainRubber(currentValue);
        else if(localName.equalsIgnoreCase("labelWhiteReef"))
            language.setLabelWhiteReef(currentValue);
        else if(localName.equalsIgnoreCase("labelColor"))
            language.setLabelColor(currentValue);
        else if(localName.equalsIgnoreCase("labelClear"))
            language.setLabelClear(currentValue);
        else if(localName.equalsIgnoreCase("labelYes"))
            language.setLabelYes(currentValue);
        else if(localName.equalsIgnoreCase("labelNo"))
            language.setLabelNo(currentValue);
        else if(localName.equalsIgnoreCase("labelDevice"))
            language.setLabelDevice(currentValue);
        else if(localName.equalsIgnoreCase("labelSales"))
            language.setLabelSales(currentValue);
        else if(localName.equalsIgnoreCase("labelSales12Month"))
            language.setLabelSales12Month(currentValue);
        else if(localName.equalsIgnoreCase("labelSearch"))
            language.setLabelSearch(currentValue);
        else if(localName.equalsIgnoreCase("labelHome"))
            language.setLabelHome(currentValue);
        else if(localName.equalsIgnoreCase("labelSiteInspection"))
            language.setLabelSiteInspection(currentValue);
        else if(localName.equalsIgnoreCase("labelVisitPlan"))
            language.setLabelVisitPlan(currentValue);
        else if(localName.equalsIgnoreCase("labelExtra"))
            language.setLabelExtra(currentValue);
        else if(localName.equalsIgnoreCase("labelNew"))
            language.setLabelNew(currentValue);
        else if(localName.equalsIgnoreCase("labelCustomerData"))
            language.setLabelCustomerData(currentValue);
        else if(localName.equalsIgnoreCase("labelContactPerson"))
            language.setLabelContactPerson(currentValue);
        else if(localName.equalsIgnoreCase("labelDashBoard"))
            language.setLabelDashBoard(currentValue);
        else if(localName.equalsIgnoreCase("labelResume"))
            language.setLabelResume(currentValue);
        else if(localName.equalsIgnoreCase("labelDailyAgenda"))
            language.setLabelDailyAgenda(currentValue);
        else if(localName.equalsIgnoreCase("labelWeeklyAgenda"))
            language.setLabelWeeklyAgenda(currentValue);
        else if(localName.equalsIgnoreCase("labelSendVisitingCard"))
            language.setLabelSendVisitingCard(currentValue);
        else if(localName.equalsIgnoreCase("labelAdditionalMobileWindPower"))
            language.setLabelAdditionalMobileWindPower(currentValue);
        else if(localName.equalsIgnoreCase("labelSiteNo"))
            language.setLabelSiteNo(currentValue);
        else if(localName.equalsIgnoreCase("labelNetworkOperator"))
            language.setLabelNetworkOperator(currentValue);
        else if(localName.equalsIgnoreCase("labelMoreAntennasAvailable"))
            language.setLabelMoreAntennasAvailable(currentValue);
        else if(localName.equalsIgnoreCase("labelCurrentGroundingNecessary"))
            language.setLabelCurrentGroundingNecessary(currentValue);
        else if(localName.equalsIgnoreCase("labelSteelConstruction"))
            language.setLabelSteelConstruction(currentValue);
        else if(localName.equalsIgnoreCase("labelCableLaying"))
            language.setLabelCableLaying(currentValue);
        else if(localName.equalsIgnoreCase("labelAdequateSpacing"))
            language.setLabelAdequateSpacing(currentValue);
        else if(localName.equalsIgnoreCase("labelTieDown"))
            language.setLabelTieDown(currentValue);
        else if(localName.equalsIgnoreCase("labelBasePlan"))
            language.setLabelBasePlan(currentValue);
        else if(localName.equalsIgnoreCase("labelWhatToDo"))
            language.setLabelWhatToDo(currentValue);
        else if(localName.equalsIgnoreCase("labelNumberWKA"))
            language.setLabelNumberWKA(currentValue);
        else if(localName.equalsIgnoreCase("labelWKAType"))
            language.setLabelWKAType(currentValue);
        else if(localName.equalsIgnoreCase("labelBoltingWKA"))
            language.setLabelBoltingWKA(currentValue);
        else if(localName.equalsIgnoreCase("labelShutDown"))
            language.setLabelShutDown(currentValue);
        else if(localName.equalsIgnoreCase("labelMicrowave"))
            language.setLabelMicrowave(currentValue);
        else if(localName.equalsIgnoreCase("labelElectricityPylon"))
            language.setLabelElectricityPylon(currentValue);
        else if(localName.equalsIgnoreCase("labelWindPower"))
            language.setLabelWindPower(currentValue);
        else if(localName.equalsIgnoreCase("labelLaneRoadway"))
            language.setLabelLaneRoadway(currentValue);
        else if(localName.equalsIgnoreCase("labelRoadway"))
            language.setLabelRoadway(currentValue);
        else if(localName.equalsIgnoreCase("labelDirection"))
            language.setLabelDirection(currentValue);
        else if(localName.equalsIgnoreCase("labelNumber"))
            language.setLabelNumber(currentValue);
        else if(localName.equalsIgnoreCase("labelLane"))
            language.setLabelLane(currentValue);
        else if(localName.equalsIgnoreCase("labelGoAway"))
            language.setLabelGoAway(currentValue);
        else if(localName.equalsIgnoreCase("labelAvailable"))
            language.setLabelAvailable(currentValue);
        else if(localName.equalsIgnoreCase("labelDuringConstruction"))
            language.setLabelDuringConstruction(currentValue);
        else if(localName.equalsIgnoreCase("labelUsuallyPlan"))
            language.setLabelUsuallyPlan(currentValue);
        else if(localName.equalsIgnoreCase("labelPermits"))
            language.setLabelPermits(currentValue);
        else if(localName.equalsIgnoreCase("labelperformWorkLabels"))
            language.setLabelperformWorkLabels(currentValue);
        else if(localName.equalsIgnoreCase("labelOperatingEnvironment"))
            language.setLabelOperatingEnvironment(currentValue);
        else if(localName.equalsIgnoreCase("labelInrinsicActivity"))
            language.setLabelInrinsicActivity(currentValue);
        else if(localName.equalsIgnoreCase("labelVerbleOffered"))
            language.setLabelVerbleOffered(currentValue);
        else if(localName.equalsIgnoreCase("labelEquipmentRent"))
            language.setLabelEquipmentRent(currentValue);
        else if(localName.equalsIgnoreCase("labelToll"))
            language.setLabelToll(currentValue);
        else if(localName.equalsIgnoreCase("labelGesAmenities"))
            language.setLabelGesAmenities(currentValue);
        else if(localName.equalsIgnoreCase("labelSatntag"))
            language.setLabelSatntag(currentValue);
        else if(localName.equalsIgnoreCase("labelServicePackages"))
            language.setLabelServicePackages(currentValue);
        else if(localName.equalsIgnoreCase("labelBranch"))
            language.setLabelBranch(currentValue);
        else if(localName.equalsIgnoreCase("labelManufacturer"))
            language.setLabelManufacturer(currentValue);
        else if(localName.equalsIgnoreCase("labelType"))
            language.setLabelType(currentValue);
        else if(localName.equalsIgnoreCase("labelHF"))
            language.setLabelHF(currentValue);
        else if(localName.equalsIgnoreCase("labelHandlingFee"))
            language.setLabelHandlingFee(currentValue);
        else if(localName.equalsIgnoreCase("labelTotal"))
            language.setLabelTotal(currentValue);
        else if(localName.equalsIgnoreCase("labelCache"))
            language.setLabelCache(currentValue);
        else if(localName.equalsIgnoreCase("labelPowerLiftSystem"))
            language.setLabelPowerLiftSystem(currentValue);
        else if (localName.equalsIgnoreCase("labelDeviceTypeNumber"))
            language.setLabelDeviceTypeNumber(currentValue);
        else if(localName.equalsIgnoreCase("labelLiabilityLimitation"))
            language.setLabelLiabilityLimitation(currentValue);
        else if(localName.equalsIgnoreCase("labelOrder"))
            language.setLabelOrder(currentValue);
        else if(localName.equalsIgnoreCase("labelCockpit"))
            language.setLabelCockpit(currentValue);
        else if(localName.equalsIgnoreCase("labelPricing"))
            language.setLabelPricing(currentValue);
        else if(localName.equalsIgnoreCase("labelPricingDetail"))
            language.setLabelPricingDetail(currentValue);
        else if(localName.equalsIgnoreCase("labelPricingGetheringActivity"))
            language.setLabelPricingGetheringActivity(currentValue);
        else if (localName.equalsIgnoreCase("labelSubmit"))
            language.setLabelSubmit(currentValue);
        else if (localName.equalsIgnoreCase("labelKinkPointHigh"))
            language.setLabelKinkPointHigh(currentValue);
        else if (localName.equalsIgnoreCase("labelSiteInspectionNew"))
            language.setLabelSiteInspectionNew(currentValue);
        else if (localName.equalsIgnoreCase("labelPhotos"))
            language.setLabelPhotos(currentValue);
        else if (localName.equalsIgnoreCase("labelDeviceData"))
            language.setLabelDeviceData(currentValue);
        else if(localName.equalsIgnoreCase("labelLogistics"))
            language.setLabelLogistics(currentValue);
        else if(localName.equalsIgnoreCase("labelAvailability"))
            language.setLabelAvailability(currentValue);
        else if(localName.equalsIgnoreCase("labelGeratermiete"))
            language.setLabelGeratermiete(currentValue);
        else if(localName.equalsIgnoreCase("labelLimitationOfLiability"))
            language.setLabelLimitationOfLiability(currentValue);
        else if(localName.equalsIgnoreCase("labelTermsofPayment"))
            language.setLabelTermsofPayment(currentValue);
        else if(localName.equalsIgnoreCase("labelPaysNoAdvancePayment"))
            language.setLabelPaysNoAdvancePayment(currentValue);
        else if(localName.equalsIgnoreCase("labelCashDiscount"))
            language.setLabelCashDiscount(currentValue);
        else if(localName.equalsIgnoreCase("labelTheCustomerHasChosenAnAlternativeMtecoUnit"))
            language.setLabelTheCustomerHasChosenAnAlternativeMtecoUnit(currentValue);
        else if(localName.equalsIgnoreCase("labelTheCustomerDoesNotHaveNeedLess"))
            language.setLabelTheCustomerDoesNotHaveNeedLess(currentValue);
        else if(localName.equalsIgnoreCase("labelTheCustomerHasNotReceivedTheOrder"))
            language.setLabelTheCustomerHasNotReceivedTheOrder(currentValue);
        else if(localName.equalsIgnoreCase("labelCompetitor"))
            language.setLabelCompetitor(currentValue);
        else if(localName.equalsIgnoreCase("labelCompetitor"))
            language.setLabelCompetitor(currentValue);
        else if (localName.equalsIgnoreCase("labelAlert"))
            language.setLabelAlert(currentValue);
        else if (localName.equalsIgnoreCase("messageProviders"))
            language.setMessageProviders(currentValue);
        else if (localName.equalsIgnoreCase("messageDelete"))
            language.setMessageDelete(currentValue);
        else if (localName.equalsIgnoreCase("messageSelectImageFrom"))
            language.setMessageSelectImageFrom(currentValue);
        else if (localName.equalsIgnoreCase("messageEnterNotes"))
            language.setMessageEnterNotes(currentValue);
        else if (localName.equalsIgnoreCase("messageNetworkNotAvailable"))
            language.setMessageNetworkNotAvailable(currentValue);
        else if (localName.equalsIgnoreCase("messageError"))
            language.setMessageError(currentValue);
        else if (localName.equalsIgnoreCase("messageWaitWhileLoading"))
            language.setMessageWaitWhileLoading(currentValue);
        else if (localName.equalsIgnoreCase("labelChangePassword"))
            language.setLabelChangePassword(currentValue);
        else if (localName.equalsIgnoreCase("labelLogin"))
            language.setLabelLogin(currentValue);
        else if (localName.equalsIgnoreCase("labelUsername"))
            language.setLabelUsername(currentValue);
        else if (localName.equalsIgnoreCase("messageSelectCustomerFirst"))
            language.setMessageSelectCustomerFirst(currentValue);
        else if (localName.equalsIgnoreCase("messageInputSearchParameter"))
            language.setMessageInputSearchParameter(currentValue);
        else if (localName.equalsIgnoreCase("messageSearchQueryBroad"))
            language.setMessageSearchQueryBroad(currentValue);
        else if (localName.equalsIgnoreCase("messageLoadCustomerFirst"))
            language.setMessageLoadCustomerFirst(currentValue);
        else if (localName.equalsIgnoreCase("labelRequired"))
            language.setLabelRequired(currentValue);
        else if (localName.equalsIgnoreCase("messageNotValidEmail"))
            language.setMessageNotValidEmail(currentValue);
        else if (localName.equalsIgnoreCase("messageNotValidWebsite"))
            language.setMessageNotValidWebsite(currentValue);
        else if (localName.equalsIgnoreCase("messageNotValidNumber"))
            language.setMessageNotValidNumber(currentValue);
        else if (localName.equalsIgnoreCase("messageNoCustomerSelected"))
            language.setMessageNoCustomerSelected(currentValue);
        else if (localName.equalsIgnoreCase("messageNoCustomerLoaded"))
            language.setMessageNoCustomerLoaded(currentValue);
        else if (localName.equalsIgnoreCase("messageSelectItemToRemove"))
            language.setMessageSelectItemToRemove(currentValue);
        else if (localName.equalsIgnoreCase("labelofflineSettings"))
            language.setLabelofflineSettings(currentValue);
        else if (localName.equalsIgnoreCase("messageCustomerupdated"))
            language.setMessageCustomerupdated(currentValue);
        else if (localName.equalsIgnoreCase("messageContactPersonInserted"))
            language.setMessageContactPersonInserted(currentValue);
        else if (localName.equalsIgnoreCase("messageCustomerLoaded"))
            language.setMessageCustomerLoaded(currentValue);
        else if (localName.equalsIgnoreCase("messageFunctionImplemented"))
            language.setMessageFunctionImplemented(currentValue);
        else if (localName.equalsIgnoreCase("messageNoEmployees"))
            language.setMessageNoEmployees(currentValue);
        else if (localName.equalsIgnoreCase("messageNoOffers"))
            language.setMessageNoOffers(currentValue);
        else if (localName.equalsIgnoreCase("messageNoContactPersons"))
            language.setMessageNoContactPersons(currentValue);
        else if (localName.equalsIgnoreCase("messageNoPeojects"))
            language.setMessageNoPeojects(currentValue);
        else if (localName.equalsIgnoreCase("labelCompletedOrders"))
            language.setLabelCompletedOrders(currentValue);
        else if (localName.equalsIgnoreCase("labelOpenOffers"))
            language.setLabelOpenOffers(currentValue);
        else if (localName.equalsIgnoreCase("labelTypeOfProjct"))
            language.setLabelTypeOfProjct(currentValue);
        else if (localName.equalsIgnoreCase("labelActivityPhase"))
            language.setLabelActivityPhase(currentValue);
        else if (localName.equalsIgnoreCase("labelCleanUpData"))
            language.setLabelCleanUpData(currentValue);
        else if (localName.equalsIgnoreCase("labelDelete"))
            language.setLabelDelete(currentValue);
        else if (localName.equalsIgnoreCase("labelPhoneCalls"))
            language.setLabelPhoneCalls(currentValue);
        else if (localName.equalsIgnoreCase("labelOverDue"))
            language.setLabelOverDue(currentValue);
        else if (localName.equalsIgnoreCase("labelFuture"))
            language.setLabelFuture(currentValue);
        else if (localName.equalsIgnoreCase("labelAdd"))
            language.setLabelAdd(currentValue);
        else if (localName.equalsIgnoreCase("labelCancel"))
            language.setLabelCancel(currentValue);
        else if (localName.equalsIgnoreCase("messageSelectEquipment"))
            language.setMessageSelectEquipment(currentValue);
        else if (localName.equalsIgnoreCase("labelSelect"))
            language.setLabelSelect(currentValue);
        else if (localName.equalsIgnoreCase("messageCustomerStored"))
            language.setMessageCustomerStored(currentValue);
        else if (localName.equalsIgnoreCase("labelWidth"))
            language.setLabelWidth(currentValue);
        else if (localName.equalsIgnoreCase("labelNote"))
            language.setLabelNote(currentValue);
        else if (localName.equalsIgnoreCase("messageMaximumSizeToStoreCustomer"))
            language.setMessageMaximumSizeToStoreCustomer(currentValue);
        else if (localName.equalsIgnoreCase("labelMessage"))
            language.setLabelMessage(currentValue);
        else if (localName.equalsIgnoreCase("labelGel_Gerat"))
            language.setLabelGel_Gerat(currentValue);
        else if (localName.equalsIgnoreCase("labelKaP"))
            language.setLabelKaP(currentValue);
        else if (localName.equalsIgnoreCase("labelMandant"))
            language.setLabelMandant(currentValue);
        else if (localName.equalsIgnoreCase("labelMietende"))
            language.setLabelMietende(currentValue);
        else if (localName.equalsIgnoreCase("labelRegion"))
            language.setLabelRegion(currentValue);
        else if (localName.equalsIgnoreCase("labelSelbstbehalt"))
            language.setLabelSelbstbehalt(currentValue);
        else if (localName.equalsIgnoreCase("labelSelbstbehalt2"))
            language.setLabelSelbstbehalt2(currentValue);
        else if (localName.equalsIgnoreCase("labelVers_LP"))
            language.setLabelVers_LP(currentValue);
        else if (localName.equalsIgnoreCase("labelVers_TP"))
            language.setLabelVers_TP(currentValue);
        else if (localName.equalsIgnoreCase("labelWunschgerat"))
            language.setLabelWunschgerat(currentValue);
        else if (localName.equalsIgnoreCase("labelContact"))
            language.setLabelContact(currentValue);
        else if (localName.equalsIgnoreCase("labelInsurance"))
            language.setLabelInsurance(currentValue);
        else if (localName.equalsIgnoreCase("messageSelectFeature"))
            language.setMessageSelectFeature(currentValue);
        else if (localName.equalsIgnoreCase("messageSelectEmployee"))
            language.setMessageSelectEmployee(currentValue);
        else if (localName.equalsIgnoreCase("messageSelectContactPerson"))
            language.setMessageSelectContactPerson(currentValue);
        else if (localName.equalsIgnoreCase("labelConfirmation"))
            language.setLabelConfirmation(currentValue);
        else if (localName.equalsIgnoreCase("messageCancelAddingActivity"))
            language.setMessageCancelAddingActivity(currentValue);
        else if (localName.equalsIgnoreCase("messageCancelAddingContactPerson"))
            language.setMessageCancelAddingContactPerson(currentValue);
        else if (localName.equalsIgnoreCase("messageNoResultFound"))
            language.setMessageNoResultFound(currentValue);
        else if (localName.equalsIgnoreCase("messageCustomerNotFound"))
            language.setMessageCustomerNotFound(currentValue);
        else if (localName.equalsIgnoreCase("messageSelectStartTime"))
            language.setMessageSelectStartTime(currentValue);
        else if (localName.equalsIgnoreCase("messageEndTimeGreaterThenStartTime"))
            language.setMessageEndTimeGreaterThenStartTime(currentValue);
        else if (localName.equalsIgnoreCase("messageNoActivityFound"))
            language.setMessageNoActivityFound(currentValue);
        else if (localName.equalsIgnoreCase("messagePleaseSelectDate"))
            language.setMessagePleaseSelectDate(currentValue);
        else if (localName.equalsIgnoreCase("messagePleaseSelectActivityType"))
            language.setMessagePleaseSelectActivityType(currentValue);
        else if (localName.equalsIgnoreCase("messagePleaseSelectActivity"))
            language.setMessagePleaseSelectActivity(currentValue);
        else if (localName.equalsIgnoreCase("messageDateMustBeLessThen2079"))
            language.setMessageDateMustBeLessThen2079(currentValue);
        else if (localName.equalsIgnoreCase("messageCustomerAlreadyExistsUpdatingContent"))
            language.setMessageCustomerAlreadyExistsUpdatingContent(currentValue);
        else if (localName.equalsIgnoreCase("messageCachePriceAddedToCacheCart"))
            language.setMessageCachePriceAddedToCacheCart(currentValue);
        else if (localName.equalsIgnoreCase("messagePleaseSelectCartItem"))
            language.setMessagePleaseSelectCartItem(currentValue);
        else if (localName.equalsIgnoreCase("messageYourOrderSuccessfullyTransferredToCart"))
            language.setMessageYourOrderSuccessfullyTransferredToCart(currentValue);
        else if (localName.equalsIgnoreCase("messageYourDealsSuccessfullyTransferredToCart"))
            language.setMessageYourDealsSuccessfullyTransferredToCart(currentValue);
        else if (localName.equalsIgnoreCase("messagePleaseEnterReasonForRejection"))
            language.setMessagePleaseEnterReasonForRejection(currentValue);
        else if (localName.equalsIgnoreCase("messageErrorAtParsing"))
            language.setMessageErrorAtParsing(currentValue);
        else if (localName.equalsIgnoreCase("messagePleaseWaitWhileDataBeingLoadedForFirstTime"))
            language.setMessagePleaseWaitWhileDataBeingLoadedForFirstTime(currentValue);
        else if (localName.equalsIgnoreCase("messageCustomerNumberNotFound"))
            language.setMessageCustomerNumberNotFound(currentValue);
        else if (localName.equalsIgnoreCase("messageNoCustomerContactPersonFound"))
            language.setMessageNoCustomerContactPersonFound(currentValue);
        else if (localName.equalsIgnoreCase("messagePleaseAddAtlist1ContactPerson"))
            language.setMessagePleaseAddAtlist1ContactPerson(currentValue);
        else if (localName.equalsIgnoreCase("messageActivityYouMustAddNewActivity"))
            language.setMessageActivityYouMustAddNewActivity(currentValue);
        else if (localName.equalsIgnoreCase("messagePleaseSelectDeviceGroupItem"))
            language.setMessagePleaseSelectDeviceGroupItem(currentValue);
        else if (localName.equalsIgnoreCase("messageAuftragBtn"))
            language.setMessageAuftragBtn(currentValue);
        else if (localName.equalsIgnoreCase("messageAngeboteBtn"))
            language.setMessageAngeboteBtn(currentValue);
        else if (localName.equalsIgnoreCase("messageMunclichesAngeboteBtn"))
            language.setMessageMunclichesAngeboteBtn(currentValue);
        else if (localName.equalsIgnoreCase("messageLostSaleBtn"))
            language.setMessageLostSaleBtn(currentValue);
        else if (localName.equalsIgnoreCase("messageSelectOneReasonForRejection"))
            language.setMessageSelectOneReasonForRejection(currentValue);
        else if (localName.equalsIgnoreCase("messageSaveData"))
            language.setMessageSaveData(currentValue);
        else if (localName.equalsIgnoreCase("messageDataSaved"))
            language.setMessageDataSaved(currentValue);
        else if (localName.equalsIgnoreCase("messageWithoutCustoer"))
            language.setMessageWithoutCustomer(currentValue);
        else if (localName.equalsIgnoreCase("labelUseMap"))
            language.setLabelUseMap(currentValue);
        else if(localName.equalsIgnoreCase("labelRoadway1"))
            language.setLabelRoadway1(currentValue);
        else if(localName.equalsIgnoreCase("messageBvoConfirm"))
            language.setMessageBvoConfirm(currentValue);
        else if(localName.equalsIgnoreCase("messageBvoSaved"))
            language.setMessageBvoSaved(currentValue);
        else if(localName.equalsIgnoreCase("labelBvoResume"))
            language.setLabelBvoResume(currentValue);
        else if(localName.equalsIgnoreCase("labelAlternateDeviceUnit"))
            language.setLabelAlternateDeviceUnit(currentValue);
        else if(localName.equalsIgnoreCase("labelAlternateDevice"))
            language.setLabelAlternateDevice(currentValue);
        else if(localName.equalsIgnoreCase("messageTheCustomerAgreementDoesNotContainPrices"))
            language.setMessageTheCustomerAgreementDoesNotContainPrices(currentValue);
        else if(localName.equalsIgnoreCase("messageMapSaved"))
            language.setMessageMapSaved(currentValue);
        else if(localName.equalsIgnoreCase("messageLeaveBvo"))
            language.setMessageLeaveBvo(currentValue);
        else if(localName.equalsIgnoreCase("messageMietdauermussGreterThenZero"))
            language.setMessageMietdauermussGreterThenZero(currentValue);
        else if(localName.equalsIgnoreCase("messageDrawingSaved"))
            language.setMessageDrawingSaved(currentValue);
        else if(localName.equalsIgnoreCase("messageBvoStored"))
            language.setMessageBvoStored(currentValue);
        else if(localName.equalsIgnoreCase("labelSelectText"))
            language.setLabelSelectText(currentValue);
        else if(localName.equalsIgnoreCase("labelCustomerOfflineData"))
            language.setLabelCustomerOfflineData(currentValue);
        else if(localName.equalsIgnoreCase("labelCustomerSearch"))
            language.setLabelCustomerSearch(currentValue);
        else if(localName.equalsIgnoreCase("labelConfirmPassword"))
            language.setLabelConfirmPassword(currentValue);
        else if(localName.equalsIgnoreCase("messageBothPasswordMustMatch"))
            language.setMessageBothPasswordMustMatch(currentValue);
        else if(localName.equalsIgnoreCase("labelLocation"))
            language.setLabelLocation(currentValue);
        else if(localName.equalsIgnoreCase("labelForgotPassword"))
            language.setLabelForgotPassword(currentValue);
        else if(localName.equalsIgnoreCase("labelProjectSearch"))
            language.setLabelProjectSearch(currentValue);
        else if(localName.equalsIgnoreCase("labelDescription"))
            language.setLabelDescription(currentValue);
        else if(localName.equalsIgnoreCase("messageBvoSentByWifi"))
            language.setMessageBvoSentByWifi(currentValue);
        else if(localName.equalsIgnoreCase("labelLongitude"))
            language.setLabelLongitude(currentValue);
        else if(localName.equalsIgnoreCase("labelLatitude"))
            language.setLabelLatitude(currentValue);
        else if(localName.equalsIgnoreCase("messagePleaseSelectBranch"))
            language.setMessagePleaseSelectBranch(currentValue);
        else if(localName.equalsIgnoreCase("messagePleaseSelectOneEmailId"))
            language.setMessagePleaseSelectOneEmailId(currentValue);
        else if(localName.equalsIgnoreCase("labelSendEmailToMe"))
            language.setLabelSendEmailToMe(currentValue);
        else if(localName.equalsIgnoreCase("messagePleaseRemoveFlatRate"))
            language.setMessagePleaseRemoveFlatRate(currentValue);
        else if(localName.equalsIgnoreCase("messageActivityIsInFuture"))
            language.setMessageActivityIsInFuture(currentValue);
        else if(localName.equalsIgnoreCase("labelAppVersion"))
            language.setLabelAppVersion(currentValue);
        else if(localName.equalsIgnoreCase("messageBvOEmailSendSuccessful"))
            language.setMessageBvOEmailSendSuccessful(currentValue);
        else if(localName.equalsIgnoreCase("messagePleaseSelectGerateType"))
            language.setMessagePleaseSelectGerateType(currentValue);
        else if(localName.equalsIgnoreCase("labelOutdoor"))
            language.setLabelOutdoor(currentValue);
        else if(localName.equalsIgnoreCase("labelLogout"))
            language.setLabelLogout(currentValue);
        else if(localName.equalsIgnoreCase("messageValueMustBeLessThen100"))
            language.setMessageValueMustBeLessThen100(currentValue);
        else if(localName.equalsIgnoreCase("messageAreYouSureWantToLogout"))
            language.setMessageAreYouSureWantToLogout(currentValue);
        else if(localName.equalsIgnoreCase("messagePasswordChangedSuccessfully"))
            language.setMessagePasswordChangedSuccessfully(currentValue);
        else if(localName.equalsIgnoreCase("messageEnterYourPlaceHere"))
            language.setMessageEnterYourPlaceHere(currentValue);
        else if(localName.equalsIgnoreCase("messageCantEnterMoreThen3devices"))
            language.setMessageCantEnterMoreThen3devices(currentValue);
        else if(localName.equalsIgnoreCase("messagePleaseSelectMainDevice"))
            language.setMessagePleaseSelectMainDevice(currentValue);
        else if(localName.equalsIgnoreCase("messageLoadMoreData"))
            language.setMessageLoadMoreData(currentValue);
        else if(localName.equalsIgnoreCase("labelCustomerPageSize"))
            language.setLabelCustomerPageSize(currentValue);
        else if(localName.equalsIgnoreCase("messageSuccessfullySaved"))
            language.setMessageSuccessfullySaved(currentValue);
        else if(localName.equalsIgnoreCase("messageEquipmentSizeMustBeLessThen5"))
            language.setMessageEquipmentSizeMustBeLessThen5(currentValue);
        else if(localName.equalsIgnoreCase("labelSet"))
            language.setLabelSet(currentValue);
        else if(localName.equalsIgnoreCase("labelCurrentPassword"))
            language.setLabelCurrentPassword(currentValue);
        else if(localName.equalsIgnoreCase("messageIncorrectEmailOrPassword"))
            language.setMessageIncorrectEmailOrPassword(currentValue);
        else if(localName.equalsIgnoreCase("messageSelectDeviceType"))
            language.setMessageSelectDeviceType(currentValue);
        else if(localName.equalsIgnoreCase("labelProjectStage"))
            language.setLabelProjectStage(currentValue);
        else if(localName.equalsIgnoreCase("labelProjectArt"))
            language.setLabelProjectArt(currentValue);
        else if(localName.equalsIgnoreCase("labelProjectPhase"))
            language.setLabelProjectPhase(currentValue);
        else if(localName.equalsIgnoreCase("labelMeetings"))
            language.setLabelMeetings(currentValue);
        else if(localName.equalsIgnoreCase("labelProjectDetails"))
            language.setLabelProjectDetails(currentValue);
        else if(localName.equalsIgnoreCase("messageMatchCodeNotFound"))
            language.setMessageMatchCodeNotFound(currentValue);
        else if(localName.equalsIgnoreCase("messageSelectProjectFirst"))
            language.setMessageSelectProjectFirst(currentValue);
        else if(localName.equalsIgnoreCase("labelMountingEnd"))
            language.setLabelMountingEnd(currentValue);
        else if(localName.equalsIgnoreCase("labelFaxBuildingSite"))
            language.setLabelFaxBuildingSite(currentValue);
        else if(localName.equalsIgnoreCase("messageCancelAddingTrade"))
            language.setMessageCancelAddingTrade(currentValue);
        else if(localName.equalsIgnoreCase("labelKASalesPreviousYear"))
            language.setLabelKASalesPreviousYear(currentValue);
        else if(localName.equalsIgnoreCase("labelKASalesCurrent"))
            language.setLabelKASalesCurrent(currentValue);
        else if(localName.equalsIgnoreCase("labelKASalesLast12Month"))
            language.setLabelKASalesLast12Month(currentValue);
        else if(localName.equalsIgnoreCase("labelSalesBoniDate"))
            language.setLabelSalesBoniDate(currentValue);
        else if(localName.equalsIgnoreCase("messagePasswordLengthMustBeGreaterThen"))
            language.setMessagePasswordLengthMustBeGreaterThen(currentValue);
        else if(localName.equalsIgnoreCase("labelCustomerDetailsSpace"))
            language.setLabelCustomerDetailsSpace(currentValue);
        else if(localName.equalsIgnoreCase("labelPropertyDetailsSpace"))
            language.setLabelPropertyDetailsSpace(currentValue);
        else if(localName.equalsIgnoreCase("labelOfferDetailsSpace"))
            language.setLabelOfferDetailsSpace(currentValue);
        else if(localName.equalsIgnoreCase("labelProjectDetailsSpace"))
            language.setLabelProjectDetailsSpace(currentValue);
        else if(localName.equalsIgnoreCase("labelShare"))
            language.setLabelShare(currentValue);
        else if(localName.equalsIgnoreCase("messageUnderConstruction"))
            language.setMessageUnderConstruction(currentValue);
        else if(localName.equalsIgnoreCase("labelBranch1"))
            language.setLabelBranch1(currentValue);
        else if(localName.equalsIgnoreCase("messagePhoneValidation"))
            language.setMessagePhoneValidation(currentValue);
        else if(localName.equalsIgnoreCase("labelBvoSent"))
            language.setLabelBvoSent(currentValue);
        else if(localName.equalsIgnoreCase("labelBvoDraft"))
            language.setLabelBvoDraft(currentValue);
        else if(localName.equalsIgnoreCase("messageEmailValidation"))
            language.setMessageEmailValidation(currentValue);
        else if(localName.equalsIgnoreCase("messageSelectEvent"))
            language.setMessageSelectEvent(currentValue);
        else if(localName.equalsIgnoreCase("messageLargeNumber"))
            language.setMessageLargeNumber(currentValue);
        else if(localName.equalsIgnoreCase("labelVisitingCardSubject"))
            language.setLabelVisitingCardSubject(currentValue);
        else if(localName.equalsIgnoreCase("messageBvoSave"))
            language.setMessageBvoSave(currentValue);
        else if(localName.equalsIgnoreCase("labelMr"))
            language.setLabelMr(currentValue);
        else if(localName.equalsIgnoreCase("labelBvoNotUploaded"))
            language.setLabelBvoNotUploaded(currentValue);
        else if(localName.equalsIgnoreCase("labelBvoProcessed"))
            language.setLabelBvoProcessed(currentValue);
        else if(localName.equalsIgnoreCase("labelWithRealized"))
            language.setLabelWithRealized(currentValue);
        else if(localName.equalsIgnoreCase("messageErrorOccured"))
            language.setMessageErrorOccured(currentValue);
        else if(localName.equalsIgnoreCase("messageEmailAddress"))
            language.setMessageEmailAddress(currentValue);
        else if(localName.equalsIgnoreCase("labelNewProject"))
            language.setLabelNewProject(currentValue);
        else if(localName.equalsIgnoreCase("messageLoadMoreProject"))
            language.setMessageLoadMoreProject(currentValue);
        else if(localName.equalsIgnoreCase("labelBusinessPhone"))
            language.setLabelBusinessPhone(currentValue);
        else if(localName.equalsIgnoreCase("messageEnter"))
            language.setMessageEnter(currentValue);
        else if(localName.equalsIgnoreCase("labelCurrentLanguage"))
            language.setLabelCurrentLanguage(currentValue);
        else if(localName.equalsIgnoreCase("labelOfflineCustomer"))
            language.setLabelOfflineCustomer(currentValue);
        else if(localName.equalsIgnoreCase("labelSetPages"))
            language.setLabelSetPages(currentValue);
        else if(localName.equalsIgnoreCase("labelLogoutFromApp"))
            language.setLabelLogoutFromApp(currentValue);
        else if(localName.equalsIgnoreCase("labelFirm"))
            language.setLabelFirm(currentValue);
        else if(localName.equalsIgnoreCase("labelTelefonnummern"))
            language.setLabelTelefonnummern(currentValue);
        else if(localName.equalsIgnoreCase("labelMobiltelefon"))
            language.setLabelMobiltelefon(currentValue);
        else if(localName.equalsIgnoreCase("labelCurrentWeek"))
            language.setLabelCurrentWeek(currentValue);
        else if(localName.equalsIgnoreCase("labelTrl"))
            language.setLabelTrl(currentValue);
        else if(localName.equalsIgnoreCase("labelMontBegin"))
            language.setLabelMontBegin(currentValue);
        else if(localName.equalsIgnoreCase("labelHeightScale1"))
            language.setLabelHeightScale1(currentValue);
        else if(localName.equalsIgnoreCase("labelProjectStage1"))
            language.setLabelProjectStage1(currentValue);
        else if(localName.equalsIgnoreCase("labelAddTrade"))
            language.setLabelAddTrade(currentValue);
        else if(localName.equalsIgnoreCase("labelEnter"))
            language.setLabelEnter(currentValue);
        else if(localName.equalsIgnoreCase("labelPlease"))
            language.setLabelPlease(currentValue);
        else if(localName.equalsIgnoreCase("messageNoTradeFound"))
            language.setMessageNoTradeFound(currentValue);
        else if(localName.equalsIgnoreCase("labelEmailBody"))
            language.setLabelEmailBody(currentValue);
        else if(localName.equalsIgnoreCase("messageAuftragSelectOne"))
            language.setMessageAuftragSelectOne(currentValue);
        else if(localName.equalsIgnoreCase("labelAnspMobile"))
            language.setLabelAnspMobile(currentValue);
        else if(localName.equalsIgnoreCase("messageGerateTypNotAvailable"))
            language.setMessageGerateTypNotAvailable(currentValue);
        else if(localName.equalsIgnoreCase("labelSendDataToAdmin"))
            language.setLabelSendDataToAdmin(currentValue);
        else if(localName.equalsIgnoreCase("labelSendDataForAnalysis"))
            language.setLabelSendDataForAnalysis(currentValue);
        else if(localName.equalsIgnoreCase("labelBVOUploadSize"))
            language.setLabelBVOUploadSize(currentValue);
        else if(localName.equalsIgnoreCase("labelBVOUploadSizeHeading"))
            language.setLabelBVOUploadSizeHeading(currentValue);
        else if(localName.equalsIgnoreCase("labelForSupportCallHeading"))
            language.setLabelForSupportCallHeading(currentValue);
        else if(localName.equalsIgnoreCase("labelForSupportCallInfo"))
            language.setLabelForSupportCallInfo(currentValue);
        else if(localName.equalsIgnoreCase("labelForSupportEmailHeading"))
            language.setLabelForSupportEmailHeading(currentValue);
        else if(localName.equalsIgnoreCase("labelForSupportEmailInfo"))
            language.setLabelForSupportEmailInfo(currentValue);
        else if(localName.equalsIgnoreCase("buttonSupportEmail"))
            language.setButtonSupportEmail(currentValue);
        else if(localName.equalsIgnoreCase("messageGewerkeAlreadyExists"))
            language.setMessageGewerkeAlreadyExists(currentValue);
        else if(localName.equalsIgnoreCase("labelDisable"))
            language.setLabelDisable(currentValue);
        else if(localName.equalsIgnoreCase("labelReset"))
            language.setLabelReset(currentValue);
        else if(localName.equalsIgnoreCase("labelWeekand"))
            language.setLableWeekend(currentValue);
        else if (localName.equalsIgnoreCase("language"))
            itemsList.add(language);
        else if (localName.equalsIgnoreCase("messageMatchcodeNotMatch"))
            language.setMessageMatchcodeNotMatch(currentValue);
        else if (localName.equalsIgnoreCase("messagePleaseSelectLegalForm"))
            language.setMessagePleaseSelectLegalForm(currentValue);
        else if (localName.equalsIgnoreCase("messageFailer"))
            language.setMessageFailer(currentValue);
        else if (localName.equalsIgnoreCase("labelCustomervon"))
            language.setLabelCustomervon(currentValue);
        else if (localName.equalsIgnoreCase("labelIndustrie"))
            language.setLabelIndustrie(currentValue);
        else if (localName.equalsIgnoreCase("labelStrasse"))
            language.setLabelStrasse(currentValue);
        else if (localName.equalsIgnoreCase("labelGenehmi"))
            language.setLabelGenehmi(currentValue);
        else if (localName.equalsIgnoreCase("messageErrorCustomerInsert"))
            language.setMessageErrorCustomerInsert(currentValue);
        else if (localName.equalsIgnoreCase("labelContactPersonSearch"))
            language.setLabelContactPersonSearch(currentValue);
        else if (localName.equalsIgnoreCase("labelTelephoneAnsp"))
        language.setLabelTelephoneAnsp(currentValue);
        else if (localName.equalsIgnoreCase("labelStatesADMOld"))
            language.setLabelStatesADMOld(currentValue);
        else if (localName.equalsIgnoreCase("messageForKanrGreaterThen2"))
            language.setMessageForKanrGreaterThen2(currentValue);
        else if (localName.equalsIgnoreCase("labelContactPersonSearchTitle"))
            language.setLabelContactPersonSearchTitle(currentValue);
        else if (localName.equalsIgnoreCase("labelMatchResultNotFound"))
            language.setLabelMatchResultNotFound(currentValue);


    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        if (currentElement)
        {
            currentValue = currentValue +  new String(ch, start, length);
        }
    }
}