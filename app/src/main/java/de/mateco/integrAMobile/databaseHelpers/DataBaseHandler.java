package de.mateco.integrAMobile.databaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import de.mateco.integrAMobile.Helper.GlobalClass;
import de.mateco.integrAMobile.model.ActivityTopicModel;
import de.mateco.integrAMobile.model.ActivityTypeModel;
import de.mateco.integrAMobile.model.BuheneartModel;
import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CountryModel;
import de.mateco.integrAMobile.model.CustomerActivityModel;
import de.mateco.integrAMobile.model.CustomerBranchModel;
import de.mateco.integrAMobile.model.CustomerCompletedOrderModel;
import de.mateco.integrAMobile.model.CustomerDatabaseModel;
import de.mateco.integrAMobile.model.CustomerLostSaleDataModel;
import de.mateco.integrAMobile.model.CustomerModel;
import de.mateco.integrAMobile.model.CustomerOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOfferModel;
import de.mateco.integrAMobile.model.CustomerOpenOrderModel;
import de.mateco.integrAMobile.model.CustomerProjectModel;
import de.mateco.integrAMobile.model.CustomerSearchPagingRequestModel;
import de.mateco.integrAMobile.model.DecisionMakerModel;
import de.mateco.integrAMobile.model.DocumentLanguageModel;
import de.mateco.integrAMobile.model.EmployeeModel;
import de.mateco.integrAMobile.model.FeatureModel;
import de.mateco.integrAMobile.model.FunctionModel;
import de.mateco.integrAMobile.model.GridImage;
import de.mateco.integrAMobile.model.LadefahrzeugComboBoxItemModel;
import de.mateco.integrAMobile.model.LegalFormModel;
import de.mateco.integrAMobile.model.PriceInfoModelClass;
import de.mateco.integrAMobile.model.PriceStaffelModel;
import de.mateco.integrAMobile.model.Pricing1BranchData;
import de.mateco.integrAMobile.model.Pricing1DeviceData;
import de.mateco.integrAMobile.model.Pricing1EquipmentData;
import de.mateco.integrAMobile.model.Pricing1LevelGroupData;
import de.mateco.integrAMobile.model.Pricing1PriceRentalData;
import de.mateco.integrAMobile.model.Pricing2KaNrData;
import de.mateco.integrAMobile.model.Pricing3InsertData;
import de.mateco.integrAMobile.model.Pricing3LostSaleData;
import de.mateco.integrAMobile.model.PricingOfflineEquipmentData;
import de.mateco.integrAMobile.model.PricingOfflineStandardPriceData;
import de.mateco.integrAMobile.model.ProjectAreaModel;
import de.mateco.integrAMobile.model.ProjectArtModel;
import de.mateco.integrAMobile.model.ProjectPhaseModel;
import de.mateco.integrAMobile.model.ProjectStagesModel;
import de.mateco.integrAMobile.model.ProjectTradeModel;
import de.mateco.integrAMobile.model.ProjectTypeModel;
import de.mateco.integrAMobile.model.SalutationModel;
import de.mateco.integrAMobile.model.SiteInspectionAccessModel;
import de.mateco.integrAMobile.model.SiteInspectionAdditionalMobileWindPowerModel;
import de.mateco.integrAMobile.model.SiteInspectionBuildingProjectModel;
import de.mateco.integrAMobile.model.SiteInspectionDeviceDataModel;
import de.mateco.integrAMobile.model.SiteInspectionDeviceTypeModel;
import de.mateco.integrAMobile.model.SiteInspectionModel;
import de.mateco.integrAMobile.model.SiteInspectionNewModel;
import de.mateco.integrAMobile.model.SiteInspectionOperationalDataPermitsModel;
import de.mateco.integrAMobile.model.SiteInspectionOperationalEnvironmentModel;


public class DataBaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "MatecoSales.db";

    private static String TABLE_BRANCH = "TableBranch";
    private static String KEY_ID = "id";
    private static String KEY_DESIGNATION = "designation";
    private static String KEY_CLIENT = "client";
    private static String KEY_TIMESTAMP = "lastSynchronizationTimestamp";
    private static String KEY_ORT= "ort";
    private static String KEY_PLZ = "plz";
    private static String KEY_STRASSES = "strasses";


    private static String TABLE_DEVICE = "TableDevice";
    private static String KEY_DEVICE_ID = "id";
    private static String KEY_HEIGHT_MAIN_GROUP = "heightMainGroup";
    private static String KEY_DESIGNATION_DEVICE = "designation";
   // private static String KEY_TIMESTAMP = "lastSynchronizationTimestamp";

    private static String TABLE_PRICE_RENTAL = "TablePriceRental";
    private static String KEY_PRICE_RENTAL_ID = "id";
    private static String KEY_PRICE_RENTAL_DESIGNATION = "designation";
    private static String KEY_PRICE_RENTAL_UNIT = "unit";

    private String TABLE_RECHTS_FORM = "TableRechtsForm";
    private static String KEY_RECHTS_FORM_ID = "id";
    private static String KEY_RECHTS_FORM_DESIGNATION = "designation";

    private String TABLE_COUNTRY = "TableCountry";
    private static String KEY_COUNTRY_ID = "id";
    private static String KEY_COUNTRY_DESIGNATION = "designation";

    private String TABLE_SALUTATION = "TableSalutation";
    private static String KEY_SALUTATION_ID = "id";
    private static String KEY_SALUTATION_DESIGNATION = "designation";

    private String TABLE_FUNCTION = "TableFunction";
    private static String KEY_FUNCTION_ID = "id";
    private static String KEY_FUNCTION_DESIGNATION = "designation";

    private String TABLE_DOCUMENT_LANGUAGE = "TableDocumentLanguage";
    private static String KEY_DOCUMENT_LANGUAGE_ID = "id";
    private static String KEY_DOCUMENT_LANGUAGE_DESIGNATION = "designation";

    private String TABLE_DECISION_MAKER = "TableDecisionMaker";
    private static String KEY_DECISION_MAKER_ID = "id";
    private static String KEY_DECISION_MAKER_DESIGNATION = "designation";

    private static String TABLE_PHOTO = "TablePhoto";
    private static String KEY_PHOTO_ID = "id";
    private static String KEY_BVO_ID = "bvoId";
    private static String KEY_PHOTO_NAME = "name";
    private static String KEY_PHOTO_FLAG = "flag";
    private static String KEY_PHOTO_PATH = "path";

    private String TABLE_KaNr = "TableKaNr";
    private static String KEY_KaNr_ID = "id";
    private static String KEY_KaNr = "kaNr";
    private static String KEY_KaNr_NAME = "name";

    private String TABLE_FEATURE = "TableFeature";
    private static String KEY_FEATURE_ID = "id";
    private static String KEY_FEATURE_NAME = "designation";

    private String TABLE_EMPLOYEE = "TableEmployee";
    private static String KEY_EMPLOYEE_ID = "id";
    private static String KEY_EMPLOYEE_STRING_JSON = "employeeJson";

    private String TABLE_ACTIVITY_TYPE = "TableActivityType";
    private static String KEY_ACTIVITY_TYPE_ID = "id";
    private static String KEY_ACTIVITY_TYPE_NAME = "name";

    private String TABLE_ACTIVITY_TOPIC = "TableActivityTopic";
    private static String KEY_ACTIVITY_TOPIC_ID = "id";
    private static String KEY_ACTIVITY_TOPIC_NAME = "name";

    private static String TABLE_DEVICE_DATA = "TableDeviceData";
    private static String KEY_DEVICE_DATA_ID = "id";
    private static String KEY_ALTERNATIVE = "Alternativ";
    private static String KEY_PARENT_ID = "parentId";
    private static String KEY_POSITION = "Position";
    private static String KEY_DIESEL = "diesel";
    private static String KEY_ELEKTRO ="Elektro";
    private static String KEY_ALLRAD = "Allrad";
    private static String KEY_KETTE_GUMMI = "Kette_Gummi";
    private static String KEY_REIFEN = "Reifen_markierungsarm";
    private static String KEY_POWER_LIFT = "powerLift";
    private static String KEY_STROMERZEUGER = "Stromerzeuger";
    private static String KEY_SONSTIGES = "Sonstiges";
    private static String KEY_SONSTIGES_TEXT = "SonstigesText";
    private static String KEY_GERAETEGRUPPE = "Geraetegruppe";
    private static String KEY_HOHENGRUPPE = "Hoehengruppe";
    private static String KEY_GERAETTYP = "Geraetetyp";
    private static String KEY_ANZAHL = "Anzahl";
    private static String KEY_ARBEITSHOEHE = "Arbeitshoehe";
    private static String KEY_SEITLICHE = "SeitlicheReichweite";
    private static String KEY_LAENGE = "Laenge";
    private static String KEY_BREITE = "Breite";
    private static String KEY_HOEHE = "Hoehe";
    private static String KEY_GEWICHT = "Gewicht";
    private static String KEY_KORBELASTUNG = "Korbbelastung";
    private static String KEY_KORBARMLAENGE = "Korbarmlaenge";
    private static String KEY_HAUPTGERAT = "Hauptgeraet";

    private static String TABLE_LOST_SALE = "TableLostSale";
    private static String KEY_LOST_SALE_ID = "id";
    private static String KEY_LOST_SALE_BRANCH = "branch";
    private static String KEY_LOST_SALE_HGRP = "hgrp";
    private static String KEY_LOST_SALE_DEVICE_TYPE = "deviceType";
    private static String KEY_LOST_SALE_MANUFACTURER = "manufacturer";
    private static String KEY_LOST_SALE_TYPE = "type";
    private static String KEY_LOST_SALE_MD = "md";
    private static String KEY_LOST_SALE_RENTAL_PRICE = "rentalPrice";
    private static String KEY_LOST_SALE_SB = "sb";
    private static String KEY_LOST_SALE_HF = "hf";
    private static String KEY_LOST_SALE_SP = "sp";
    private static String KEY_LOST_SALE_HB = "hb";
    private static String KEY_LOST_SALE_BEST = "best";
    private static String KEY_LOST_CUSTOMER_KUNDEN_NR = "kundenNr";

    private String TABLE_CUSTOMER = "TableCustomer";
    //private String KEY_CUSTOMER_ID = "id";
    private String KEY_CUSTOMER_CUSTOMER_NO = "CustomerNo";
    private String KEY_CUSTOMER_KANR = "KaNr";
    private String KEY_CUSTOMER_KONTAKT = "CustomerContact";
    private String KEY_CUSTOMER_MATCHCODE = "MatchCode";
    private String KEY_CUSTOMER_NAME_1 = "Name1";
    private String KEY_CUSTOMER_ROAD = "Road";
    private String KEY_CUSTOMER_ZIP_CODE = "ZipCode";
    private String KEY_CUSTOMER_PLACE = "Place";
    private String KEY_CUSTOMER_Tel = "Telephone";
    private String KEY_CUSTOMER_DETAIL = "CustomerDetail";
    private String KEY_CUSTOMER_CONTACT_PERSON = "CustomerContactPerson";
    private String KEY_CUSTOMER_ACTIVITY = "CustomerActivity";
    private String KEY_CUSTOMER_PROJECT = "CustomerProject";
    private String KEY_CUSTOMER_OFFER = "CustomerOffer";
    private String KEY_CUSTOMER_OPEN_ORDER = "CustomerOpenOrder";
    private String KEY_CUSTOMER_COMPLETED_ORDER = "CustomerCompletedOrder";
    private String KEY_CUSTOMER_OPEN_OFFER = "CustomerOpenOffer";
    private String KEY_CUSTOMER_LOST_SALE = "CustomerLostSale";

    private static String TABLE_LEVEL_GROUP = "TableLevelGroup";
    private static String KEY_LEVEL_GROUP_ID = "id";
    private static String KEY_LEVEL_GROUP_DESIGNATION = "designation";
    private static String KEY_LEVEL_GROUP_HEIGHT_GROUP = "heightGroup";

    private static String TABLE_EQUIPMENT_DEVICE = "TableEquipmentDevice";
    private static String KEY_EQUIPMENT_DEVICE_ID = "id";
    private static String KEY_EQUIPMENT_DEVICE_EQUIPMENT = "equipment";
    private static String KEY_EQUIPMENT_DEVICE_DESIGNATION = "Designation";
    private static String KEY_EQUIPMENT_DEVICE_DESIGNATION1 = "designationId";
    private static String KEY_EQUIPMENT_DEVICE_HEIGHT_MAIN_GROUP = "height_main_group";

    private static String TABLE_PRICING_OFFLINE_STANDARD_PRICE = "TablePricingOfflineStandardPrice";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_ID = "id";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_BEZEINCHNUNG = "Bezeichnung";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENGRUPPE = "Hoehengruppe";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENHAUPTGRPPE = "Hoehenhauptgruppe";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENHAUPTGRPPE_ID = "HoehenhauptgruppeID";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_LISTENPREIS = "Listenpreis";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_MANDANT = "Mandant";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_NIEDERLASSUNG = "Niederlassung";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_11_20 = "TageM_11_20";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_1_2 = "TageM_1_2";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_3_4 = "TageM_3_4";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_5_10 = "TageM_5_10";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_11_20 = "TageR_11_20";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_1_2 = "TageR_1_2";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_3_4 = "TageR_3_4";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_5_10 = "TageR_5_10";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_AB_21_TAGE_M = "ab21TageM";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_AB_21_TAGE_R = "ab21TageR";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_BIS_8_STUNDEN_M = "bis8StundenM";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_BIS_8_STUNDEN_R = "bis8StundenR";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_UBER_8_STUNDEN_M = "uber8StundenM";
    private static String KEY_PRICING_OFFLINE_STANDARD_PRICE_UBER_8_STUNDEN_R = "uber8StundenR";

    private String TABLE_PRICING_OFFLINE_EQUIPMENT_HEIGHT = "TablePriceEquipmentHeightOffLine";
    private String KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_ID = "id";
    private String KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_AUSSTATTUNG = "Ausstattung";
    private String KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_BEZEICHNUNG = "Bezeichnung";
    private String KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_HOEHENGRPPE = "Hoehengruppe";
    private String KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_HOEHENHAUPTGRPPE = "Hoehenhauptgruppe";
    private String KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_BEZEICHNUNG1 = "bezeichnungName";

    private String TABLE_DEVICE_TYPE = "TableDeviceType";
    private String KEY_DEVICE_TYPE_BEZEICHUNG = "Bezeichnung";
    private String KEY_DEVICE_TYPE_ID = "GeraeettypID";
    private String KEY_DEVICE_TYPE_HOHENGRUPPE = "Hoehengruppe";
    private String KEY_DEVICE_TYPE_ARBEITSHOEHE = "arbeitshoehe";

    private String TABLE_BUILDING_PROJECT = "TableBuildingProject";
    private String KEY_BUILDING_PROJECT_BEZEICHUNG = "Bezeichnung";
    private String KEY_BUILDING_PROJECT_BAUVORHABEN = "Bauvorhaben";

    private String TABLE_ACCESS = "TableAccess";
    private String KEY_ACCESS_BEZEICHUNG = "Bezeichnung";
    private String KEY_ACCESS_ZUGANG = "Zugang";

    private String TABLE_SITE_INSPECTION = "TableSiteInspection";
    private String KEY_SITE_INSPECTION_ID = "id";
    private String KEY_UPLOAD_ID = "uploadId";
    private String KEY_FLAG = "flag";
    private String KEY_DATE = "date";
    private String KEY_SITE_INSPECTION_DETAIL = "SiteInspectionDetail";
    private String KEY_OPERATIONAL_ENVIRONMENT_DETAIL = "AddtinalEnvironment";
    private String KEY_OPERATIONAL_DATA_PERMITS = "OperationalDataPermits";
    private String KEY_ADDITIONAL_MOBILE_WIND_POWER = "AdditionalMobileWindPower";
    private String KEY_SELECT_MAIL = "SelectMail";
    private String KEY_EMAIL_BODY_CONTENT = "EmailBodyContent";

    private String TABLE_PRICING3INSERTJSON = "TablePricing3ScreenInsertJson";
    private String KEY_PRICING3INSERTJSON_ID = "id";
    private String KEY_PRICING3INSERTJSON_KUNDENNR = "kunndenNr";
    private String KEY_PRICING3INSERTJSON_JSON = "json";
    private String KEY_PRICING3INSERTJSON_Kontakt = "Kontakt";
    private String KEY_PRICING3INSERTJSON_CustomerNr = "CustomerNr";
    private String KEY_PRICING3INSERTJSON_Mandant = "Mandant";
    private String KEY_PRICING3INSERTJSON_Warenkorbart = "Warenkorbart";
    private String KEY_PRICING3INSERTJSON_Hoehengruppe = "Hoehengruppe";
    private String KEY_PRICING3INSERTJSON_Einheit_Mietdauer = "Einheit_Mietdauer";
    private String KEY_PRICING3INSERTJSON_Mietdauer = "Mietdauer";
    private String KEY_PRICING3INSERTJSON_Mietpreis = "Mietpreis";
    private String KEY_PRICING3INSERTJSON_Standtag = "Standtag";
    private String KEY_PRICING3INSERTJSON_Selbstbehalt = "Selbstbehalt";
    private String KEY_PRICING3INSERTJSON_HandlingFee = "HandlingFee";/////
    private String KEY_PRICING3INSERTJSON_ServicePauschale = "ServicePauschale";
    private String KEY_PRICING3INSERTJSON_Versicherung = "Versicherung";
    private String KEY_PRICING3INSERTJSON_WochenendeMitversichert = "WochenendeMitversichert";
    private String KEY_PRICING3INSERTJSON_TransportAnfahrt = "TransportAnfahrt";
    private String KEY_PRICING3INSERTJSON_TransportPauschal = "TransportPauschal";
    private String KEY_PRICING3INSERTJSON_TransportAbfahrt = "TransportAbfahrt";
    private String KEY_PRICING3INSERTJSON_Beiladungspauschale = "Beiladungspauschale";
    private String KEY_PRICING3INSERTJSON_Einsatzinformation = "Einsatzinformation";
    private String KEY_PRICING3INSERTJSON_Besteller = "Besteller";
    private String KEY_PRICING3INSERTJSON_Besteller_Telefon = "Besteller_Telefon";
    private String KEY_PRICING3INSERTJSON_Besteller_Email = "Besteller_Email";
    private String KEY_PRICING3INSERTJSON_Notiz = "Notiz";
    private String KEY_PRICING3INSERTJSON_KaNr = "KaNr";
    private String KEY_PRICING3INSERTJSON_AnsPartner = "AnsPartner";
    private String KEY_PRICING3INSERTJSON_Besteller_Anrede = "Besteller_Anrede";
    private String KEY_PRICING3INSERTJSON_Besteller_Mobil = "Besteller_Mobil";
    private String KEY_PRICING3INSERTJSON_Mautkilometer = "Mautkilometer";
    private String KEY_PRICING3INSERTJSON_Winterreifenpauschale = "Winterreifenpauschale";
    private String KEY_PRICING3INSERTJSON_Bearbeitet = "Bearbeitet";
    private String KEY_PRICING3INSERTJSON_Kalendertage = "Kalendertage";
    private String KEY_PRICING3INSERTJSON_Referenz = "Referenz";
    private String KEY_PRICING3INSERTJSON_Geraetetyp = "Geraetetyp";
    private String KEY_PRICING3INSERTJSON_loginUserNumberrange = "loginUserNumberrange";

    private String KEY_PRICING3INSERTJSON_BRANCH = "branch";
    private String KEY_PRICING3INSERTJSON_HGRP = "hgrp";
    private String KEY_PRICING3INSERTJSON_DEVICE_TYPE = "deviceType";
    private String KEY_PRICING3INSERTJSON_MANUFACTURER = "manufacturer";
    private String KEY_PRICING3INSERTJSON_TYPE = "type";
    private String KEY_PRICING3INSERTJSON_MD = "md";
    private String KEY_PRICING3INSERTJSON_RENTAL_PRICE = "rentalPrice";
    private String KEY_PRICING3INSERTJSON_SB = "sb";
    private String KEY_PRICING3INSERTJSON_HF = "hf";
    private String KEY_PRICING3INSERTJSON_SP = "sp";
    private String KEY_PRICING3INSERTJSON_HB = "hb";
    private String KEY_PRICING3INSERTJSON_BEST = "best";
    private String KEY_PRICING3INSERTJSON_CUSTOMER_KUNDEN_NR = "kundenNr";
    private String KEY_PRICING3INSERTJSON_Ersteller = "Ersteller";

    private static final String IS_KANN = "isKann";
    private static final String IS_LIEFERUNG = "isLieferung";
    private static final String IS_VORANMELDUNG = "isVoranmeldung";
    private static final String IS_BENACHRICHTGUNG = "isBenachrichtgung";
    private static final String IS_RAMPENA = "isRampena";
    private static final String IS_SONSTIGE = "isSonstige";
    private static final String IS_EINWEISUNG = "isEinweisung";
    private static final String IS_SELBSTFAHRER = "isSelbstfahrer";
    private static final String KANN = "kann";
    private static final String VORANMELDUNG = "voranmeldung";
    private static final String BENACHRICHTUNG = "benarchrichtung";
    private static final String SONSTIGE = "sonstige";
    private static final String LADEFAHRZAUG = "ladefahrzaug";
    private static final String START_DATE = "start_date";
    private static final String START_TIME = "start_time";
    private static final String END_DATE = "end_date";
    private static final String END_TIME = "end_time";


    private String TABLE_PROJECT_STAGE = "TableProjectStage";
    private static String KEY_PROJECT_STAGE_ID = "id";
    private static String KEY_PROJECT_STAGE_DESIGNATION = "designation";

    private String TABLE_PROJECT_AREA = "TableProjectArea";
    private static String KEY_PROJECT_AREA_ID = "id";
    private static String KEY_PROJECT_AREA_DESIGNATION = "designation";

    private String TABLE_PROJECT_ART = "TableProjectArt";
    private static String KEY_PROJECT_ART_ID = "id";
    private static String KEY_PROJECT_ART_DESIGNATION = "designation";

    private String TABLE_PROJECT_TYPE = "TableProjectTYPE";
    private static String KEY_PROJECT_TYPE_ID = "id";
    private static String KEY_PROJECT_TYPE_DESIGNATION = "designation";

    private String TABLE_PROJECT_PHASE = "TableProjectPhase";
    private static String KEY_PROJECT_PHASE_ID = "id";
    private static String KEY_PROJECT_PHASE_DESIGNATION = "designation";

    private String TABLE_PROJECT_TRADE = "TableProjectTrade";
    private static String KEY_PROJECT_TRADE_ID = "id";
    private static String KEY_PROJECT_TRADE_DESIGNATION = "designation";

    private String TABLE_CUSTOMER_BRANCH = "TableCustomerBranch";
    private static String KEY_CUSTOMER_BRANCH_ID = "id";
    private static String KEY_CUSTOMER_BRANCH_NAME = "name";

    private String TABLE_BUHENEART = "TableBuheneart";
    private static String KEY_BEZEICHUNG = "Bezeichnung";
    private static String KEY_BUHENEARTART = "Buheneart";
    private static String KEY_SORT = "Sort";
    private static String KEY_SPARCHE = "Sprache";

    private static String TABLE_STAFFEL = "TableStaffel";
    private static String KEY_STAFFEL = "Staffel";
    private static String KEY_JSON_OBJECT = "JsonObject";

    private String TABLE_LADEFAHRZEUG = "TableLadefahrzeug";
    private static String BEZEICHNUNG = "Bezeichnung";
    private static String ID="id";
    private static String SPRACHE="Sprache";

    private String TABLE_PRICE_JSON = "table_price_json";
    private static String JSON_STRING = "json_string";
    private static String JSON_ID = "json_id";


    public DataBaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String CREATE_PRICE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRICE_JSON + " ("

                + JSON_STRING + " TEXT, "
                + JSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT )";

        db.execSQL(CREATE_PRICE_TABLE);


        Log.e("onCreate ", "All Table Are created");
        String CREATE_LADEFAHRZEUG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LADEFAHRZEUG + " ("

                + BEZEICHNUNG + " TEXT, "
                + ID + " INTEGER, "
                + SPRACHE + " INTEGER )";

        db.execSQL(CREATE_LADEFAHRZEUG_TABLE);


        String CREATE_BRANCH_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BRANCH + " ("

                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_DESIGNATION + " TEXT, "
                + KEY_CLIENT + " INTEGER, "
                        + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                        + KEY_ORT + " TEXT, "
                        + KEY_PLZ + " TEXT, "
                + KEY_STRASSES + " TEXT )";

        db.execSQL(CREATE_BRANCH_TABLE);

        String CREATE_DEVICE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DEVICE + " ("

                + KEY_DEVICE_ID + " INTEGER PRIMARY KEY, "
                + KEY_HEIGHT_MAIN_GROUP + " INTEGER, "
                + KEY_DESIGNATION_DEVICE + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_DEVICE_TABLE);

        String CREATE_PRICE_RENTAL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRICE_RENTAL + " ("

                + KEY_PRICE_RENTAL_ID + " INTEGER PRIMARY KEY, "
                + KEY_PRICE_RENTAL_DESIGNATION + " TEXT, "
                + KEY_PRICE_RENTAL_UNIT + " INTEGER, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_PRICE_RENTAL_TABLE);

        String CREATE_COUNTRY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRY + " ("

                + KEY_COUNTRY_ID + " INTEGER, "
                + KEY_COUNTRY_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_COUNTRY_TABLE);

        String CREATE_RECHTS_FORM_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_RECHTS_FORM + " ("

                + KEY_RECHTS_FORM_ID + " INTEGER, "
                + KEY_RECHTS_FORM_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_RECHTS_FORM_TABLE);

        String CREATE_SALUTATION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SALUTATION + " ("

                + KEY_SALUTATION_ID + " INTEGER, "
                + KEY_SALUTATION_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_SALUTATION_TABLE);

        String CREATE_ACTIVITY_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACTIVITY_TYPE + " ("

                + KEY_ACTIVITY_TYPE_ID + " INTEGER, "
                + KEY_ACTIVITY_TYPE_NAME + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_ACTIVITY_TYPE_TABLE);

        String CREATE_ACTIVITY_TOPIC_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACTIVITY_TOPIC + " ("

                + KEY_ACTIVITY_TOPIC_ID + " INTEGER, "
                + KEY_ACTIVITY_TOPIC_NAME + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_ACTIVITY_TOPIC_TABLE);

        String CREATE_FUNCTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FUNCTION + " ("

                + KEY_FUNCTION_ID + " INTEGER, "
                + KEY_FUNCTION_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_FUNCTION_TABLE);

        String CREATE_DOCUMENT_LANGUAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DOCUMENT_LANGUAGE + " ("

                + KEY_DOCUMENT_LANGUAGE_ID + " INTEGER, "
                + KEY_DOCUMENT_LANGUAGE_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_DOCUMENT_LANGUAGE_TABLE);

        String CREATE_DECISION_MAKER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DECISION_MAKER + " ("

                + KEY_DECISION_MAKER_ID + " INTEGER, "
                + KEY_DECISION_MAKER_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_DECISION_MAKER_TABLE);

        String CREATE_PHOTO_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PHOTO + " ("

                + KEY_PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_BVO_ID + " INTEGER, "
                + KEY_PHOTO_NAME + " TEXT, "
                + KEY_PHOTO_FLAG + " INTEGER, "
                + KEY_PHOTO_PATH + " TEXT)";
        db.execSQL(CREATE_PHOTO_TABLE);

        String CREATE_KANR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_KaNr + " ("

                + KEY_KaNr_ID + " INTEGER, "
                + KEY_KaNr + " INTEGER, "
                + KEY_KaNr_NAME + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_KANR_TABLE);

        String CREATE_FEATURE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FEATURE + " ("

                + KEY_FEATURE_ID + " INTEGER, "
                + KEY_FEATURE_NAME + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_FEATURE_TABLE);

        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE + " ("

                + KEY_EMPLOYEE_ID + " INTEGER, "
                + KEY_EMPLOYEE_STRING_JSON + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_EMPLOYEE_TABLE);

        String CREATE_DEVICE_DATA = "CREATE TABLE IF NOT EXISTS " + TABLE_DEVICE_DATA + " ("

                + KEY_DEVICE_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_BVO_ID + " INTEGER, "
                + KEY_PARENT_ID + " INTEGER, "
                + KEY_ALTERNATIVE + " INTEGER, "
                + KEY_POSITION + " INTEGER, "
                + KEY_DIESEL + " TEXT,"
                + KEY_ELEKTRO + " TEXT,"
                + KEY_ALLRAD + " TEXT,"
                + KEY_KETTE_GUMMI + " TEXT,"
                + KEY_REIFEN + " TEXT,"
                + KEY_POWER_LIFT + " TEXT,"
                + KEY_STROMERZEUGER + " TEXT,"
                + KEY_SONSTIGES + " TEXT, "
                + KEY_SONSTIGES_TEXT + " TEXT, "
                + KEY_GERAETEGRUPPE + " TEXT, "
                + KEY_HOHENGRUPPE + " TEXT, "
                + KEY_GERAETTYP + " TEXT, "
                + KEY_ANZAHL + " INTEGER, "
                + KEY_ARBEITSHOEHE + " TEXT,"
                + KEY_SEITLICHE + " TEXT,"
                + KEY_LAENGE + " TEXT,"
                + KEY_BREITE + " TEXT,"
                + KEY_HOEHE + " TEXT,"
                + KEY_GEWICHT + " TEXT,"
                + KEY_KORBELASTUNG + " TEXT,"
                + KEY_KORBARMLAENGE + " TEXT,"
                + KEY_HAUPTGERAT + " Text)";
        db.execSQL(CREATE_DEVICE_DATA);

        String CREATE_LOST_SALE = "CREATE TABLE IF NOT EXISTS " + TABLE_LOST_SALE + " ("

                + KEY_LOST_SALE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_LOST_SALE_BRANCH + " TEXT, "
                + KEY_LOST_SALE_HGRP + " TEXT, "
                + KEY_LOST_SALE_DEVICE_TYPE + " TEXT, "
                + KEY_LOST_SALE_MANUFACTURER + " TEXT, "
                + KEY_LOST_SALE_TYPE + " TEXT, "
                + KEY_LOST_SALE_MD + " REAL, "
                + KEY_LOST_SALE_RENTAL_PRICE + " REAL, "
                + KEY_LOST_SALE_SB + " REAL, "
                + KEY_LOST_SALE_HF + " TEXT, "
                + KEY_LOST_SALE_SP + " TEXT, "
                + KEY_LOST_SALE_HB + " TEXT, "
                + KEY_LOST_SALE_BEST + " TEXT, "

                + KEY_LOST_CUSTOMER_KUNDEN_NR+ " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_LOST_SALE);

        String CREATE_LEVEL_GROUP = "CREATE TABLE IF NOT EXISTS " + TABLE_LEVEL_GROUP + " ("

                + KEY_LEVEL_GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_LEVEL_GROUP_DESIGNATION + " TEXT, "
                + KEY_LEVEL_GROUP_HEIGHT_GROUP + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";
        db.execSQL(CREATE_LEVEL_GROUP);

        String CREATE_EQUIPMENT_DEVICE = "CREATE TABLE IF NOT EXISTS " + TABLE_EQUIPMENT_DEVICE + " ("

                + KEY_EQUIPMENT_DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_EQUIPMENT_DEVICE_EQUIPMENT + " INTEGER, "
                + KEY_EQUIPMENT_DEVICE_DESIGNATION + " TEXT, "
                + KEY_EQUIPMENT_DEVICE_HEIGHT_MAIN_GROUP + " INTEGER, "
                + KEY_EQUIPMENT_DEVICE_DESIGNATION1 + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_EQUIPMENT_DEVICE);

        String CREATE_PRICING_OFFLINE_STANDARD_PRICE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_PRICING_OFFLINE_STANDARD_PRICE + " ("

                + KEY_PRICING_OFFLINE_STANDARD_PRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + KEY_PRICING_OFFLINE_STANDARD_PRICE_BEZEINCHNUNG + " TEXT, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENGRUPPE + " TEXT, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENHAUPTGRPPE + " TEXT, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENHAUPTGRPPE_ID + " INTEGER, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_LISTENPREIS + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_MANDANT + " INTEGER, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_NIEDERLASSUNG + " TEXT, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_11_20 + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_1_2 + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_3_4 + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_5_10 + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_11_20 + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_1_2 + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_3_4 + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_5_10 + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_AB_21_TAGE_M + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_AB_21_TAGE_R + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_BIS_8_STUNDEN_M + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_BIS_8_STUNDEN_R + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_UBER_8_STUNDEN_M + " REAL, "
                + KEY_PRICING_OFFLINE_STANDARD_PRICE_UBER_8_STUNDEN_R + " REAL, "

                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_PRICING_OFFLINE_STANDARD_PRICE);

        String CREATE_PRICING_OFFLINE_EQUIPMENT_HEIGHT = "CREATE TABLE IF NOT EXISTS " +
                TABLE_PRICING_OFFLINE_EQUIPMENT_HEIGHT + " ("

                + KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_AUSSTATTUNG + " INTEGER, "
                + KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_BEZEICHNUNG + " TEXT, "
                + KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_HOEHENGRPPE + " TEXT, "
                + KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_HOEHENHAUPTGRPPE + " INTEGER, "
                + KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_BEZEICHNUNG1 + " TEXT, "

                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_PRICING_OFFLINE_EQUIPMENT_HEIGHT);

        String CREATE_TABLE_CUSTOMER = "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMER + " ("

                + KEY_CUSTOMER_CUSTOMER_NO + " TEXT, "
                + KEY_CUSTOMER_KANR + " TEXT, "
                + KEY_CUSTOMER_KONTAKT + " TEXT, "
                + KEY_CUSTOMER_MATCHCODE + " TEXT, "
                + KEY_CUSTOMER_NAME_1 + " TEXT, "
                + KEY_CUSTOMER_ROAD + " TEXT, "
                + KEY_CUSTOMER_ZIP_CODE + " TEXT, "
                + KEY_CUSTOMER_PLACE + " TEXT, "
                + KEY_CUSTOMER_Tel + " TEXT, "
                + KEY_CUSTOMER_DETAIL + " TEXT, "
                + KEY_CUSTOMER_CONTACT_PERSON + " TEXT, "
                + KEY_CUSTOMER_ACTIVITY + " TEXT, "
                + KEY_CUSTOMER_PROJECT + " TEXT, "
                + KEY_CUSTOMER_OFFER + " TEXT, "
                + KEY_CUSTOMER_OPEN_ORDER + " TEXT, "
                + KEY_CUSTOMER_COMPLETED_ORDER + " TEXT, "
                + KEY_CUSTOMER_OPEN_OFFER + " TEXT, "
                + KEY_CUSTOMER_LOST_SALE + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_TABLE_CUSTOMER);

        String CREATE_DEVICE_TYPE = "CREATE TABLE IF NOT EXISTS " + TABLE_DEVICE_TYPE + " ("

                + KEY_DEVICE_TYPE_ID + " TEXT, "
                + KEY_DEVICE_TYPE_BEZEICHUNG + " TEXT, "
                + KEY_DEVICE_TYPE_HOHENGRUPPE + " TEXT, "
                + KEY_DEVICE_TYPE_ARBEITSHOEHE + " TEXT)";
        db.execSQL(CREATE_DEVICE_TYPE);

        String CREATE_BUIDING_PROJECT = "CREATE TABLE IF NOT EXISTS " + TABLE_BUILDING_PROJECT + " ("

                + KEY_BUILDING_PROJECT_BAUVORHABEN + " TEXT,"
                + KEY_BUILDING_PROJECT_BEZEICHUNG + " TEXT)";

        db.execSQL(CREATE_BUIDING_PROJECT);

        String CREATE_ACCESS = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCESS + " ("

                + KEY_ACCESS_ZUGANG + " TEXT, "
                + KEY_ACCESS_BEZEICHUNG + " TEXT)";

        db.execSQL(CREATE_ACCESS);

        String CREATE_SITE_INSPECTION = "CREATE TABLE IF NOT EXISTS " + TABLE_SITE_INSPECTION + " ("

                + KEY_SITE_INSPECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_UPLOAD_ID + " Text, "
                + KEY_FLAG + " INTEGER, "
                + KEY_DATE + " TEXT, "
                + KEY_SITE_INSPECTION_DETAIL + " TEXT, "
                + KEY_OPERATIONAL_ENVIRONMENT_DETAIL + " TEXT, "
                + KEY_OPERATIONAL_DATA_PERMITS + " TEXT,"
                + KEY_ADDITIONAL_MOBILE_WIND_POWER + " TEXT, "
                + KEY_SELECT_MAIL + " TEXT,"
                + KEY_EMAIL_BODY_CONTENT + " TEXT)";

        db.execSQL(CREATE_SITE_INSPECTION);

        String CREATE_PRICING3INSERTJSON = "CREATE TABLE IF NOT EXISTS " + TABLE_PRICING3INSERTJSON + " ("

                + KEY_PRICING3INSERTJSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PRICING3INSERTJSON_KUNDENNR + " TEXT, "

                + KEY_PRICING3INSERTJSON_Kontakt + " TEXT, "
                + KEY_PRICING3INSERTJSON_CustomerNr + " TEXT, "
                + KEY_PRICING3INSERTJSON_Mandant + " INTEGER, "
                + KEY_PRICING3INSERTJSON_Warenkorbart + " INTEGER, "
                + KEY_PRICING3INSERTJSON_Hoehengruppe + " TEXT, "
                + KEY_PRICING3INSERTJSON_Einheit_Mietdauer + " INTEGER, "
                + KEY_PRICING3INSERTJSON_Mietdauer + " INTEGER, "
                + KEY_PRICING3INSERTJSON_Mietpreis + " REAL, "
                + KEY_PRICING3INSERTJSON_Standtag + " REAL, "
                + KEY_PRICING3INSERTJSON_Selbstbehalt + " TEXT, "
                + KEY_PRICING3INSERTJSON_HandlingFee + " INTEGER, "
                + KEY_PRICING3INSERTJSON_ServicePauschale + " INTEGER, "
                + KEY_PRICING3INSERTJSON_Versicherung + " REAL, "
                + KEY_PRICING3INSERTJSON_WochenendeMitversichert + " INTEGER, "
                + KEY_PRICING3INSERTJSON_TransportAnfahrt + " REAL, "
                + KEY_PRICING3INSERTJSON_TransportPauschal + " REAL, "
                + KEY_PRICING3INSERTJSON_TransportAbfahrt + " REAL, "
                + KEY_PRICING3INSERTJSON_Beiladungspauschale + " REAL, "
                + KEY_PRICING3INSERTJSON_Einsatzinformation + " TEXT, "
                + KEY_PRICING3INSERTJSON_Besteller + " TEXT, "
                + KEY_PRICING3INSERTJSON_Besteller_Telefon + " TEXT, "
                + KEY_PRICING3INSERTJSON_Besteller_Email + " TEXT, "
                + KEY_PRICING3INSERTJSON_Notiz + " TEXT, "
                + KEY_PRICING3INSERTJSON_KaNr + " INTEGER, "
                + KEY_PRICING3INSERTJSON_AnsPartner + " TEXT, "
                + KEY_PRICING3INSERTJSON_Besteller_Anrede + " TEXT, "
                + KEY_PRICING3INSERTJSON_Besteller_Mobil + " TEXT, "
                + KEY_PRICING3INSERTJSON_Mautkilometer + " TEXT, "
                + KEY_PRICING3INSERTJSON_Winterreifenpauschale + " INTEGER, "
                + KEY_PRICING3INSERTJSON_Bearbeitet + " INTEGER, "
                + KEY_PRICING3INSERTJSON_Kalendertage + " INTEGER, "
                + KEY_PRICING3INSERTJSON_Referenz + " TEXT, "
                + KEY_PRICING3INSERTJSON_Geraetetyp + " TEXT, "
                + KEY_PRICING3INSERTJSON_loginUserNumberrange + " TEXT, "
                + KEY_PRICING3INSERTJSON_BRANCH + " TEXT, "
                + KEY_PRICING3INSERTJSON_HGRP + " TEXT, "
                + KEY_PRICING3INSERTJSON_DEVICE_TYPE + " TEXT, "
                + KEY_PRICING3INSERTJSON_MANUFACTURER + " TEXT, "
                + KEY_PRICING3INSERTJSON_TYPE + " TEXT, "
                + KEY_PRICING3INSERTJSON_MD + " INTEGER, "
                + KEY_PRICING3INSERTJSON_RENTAL_PRICE + " REAL, "
                + KEY_PRICING3INSERTJSON_SB + " REAL, "
                + KEY_PRICING3INSERTJSON_HF + " TEXT, "
                + KEY_PRICING3INSERTJSON_SP + " TEXT, "
                + KEY_PRICING3INSERTJSON_HB + " REAL, "
                + KEY_PRICING3INSERTJSON_BEST + " TEXT, "
                + KEY_PRICING3INSERTJSON_CUSTOMER_KUNDEN_NR + " TEXT, "
                + KEY_PRICING3INSERTJSON_Ersteller + " INTEGER, "
                + KEY_PRICING3INSERTJSON_JSON + " TEXT ,"

                + IS_KANN + " INTEGER , "
                + IS_LIEFERUNG + " INTEGER , "
                + IS_VORANMELDUNG + " INTEGER , "
                + IS_BENACHRICHTGUNG + " INTEGER , "
                + IS_RAMPENA + " INTEGER , "
                + IS_SONSTIGE + " INTEGER , "
                + IS_EINWEISUNG + " INTEGER , "
                + IS_SELBSTFAHRER + " INTEGER , "
                + KANN + " TEXT , "
                + VORANMELDUNG + " TEXT , "
                + BENACHRICHTUNG + " TEXT , "
                + SONSTIGE + " TEXT , "
                + LADEFAHRZAUG + " INTEGER , "
                + START_DATE + " TEXT , "
                + START_TIME + " TEXT , "
                + END_DATE + " TEXT , "
                + END_TIME + " TEXT "
                + ")";

        db.execSQL(CREATE_PRICING3INSERTJSON);

        String CREATE_PROJECT_STAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECT_STAGE + " ("

                + KEY_PROJECT_STAGE_ID + " INTEGER, "
                + KEY_PROJECT_STAGE_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_PROJECT_STAGE_TABLE);

        String CREATE_PROJECT_AREA_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECT_AREA + " ("

                + KEY_PROJECT_AREA_ID + " INTEGER, "
                + KEY_PROJECT_AREA_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_PROJECT_AREA_TABLE);

        String CREATE_PROJECT_ART_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECT_ART + " ("

                + KEY_PROJECT_ART_ID + " INTEGER, "
                + KEY_PROJECT_ART_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_PROJECT_ART_TABLE);

        String CREATE_PROJECT_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECT_TYPE + " ("

                + KEY_PROJECT_TYPE_ID + " INTEGER, "
                + KEY_PROJECT_TYPE_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_PROJECT_TYPE_TABLE);

        String CREATE_PROJECT_PHASE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECT_PHASE + " ("

                + KEY_PROJECT_PHASE_ID + " INTEGER, "
                + KEY_PROJECT_PHASE_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_PROJECT_PHASE_TABLE);

        String CREATE_PROJECT_TRADE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECT_TRADE + " ("

                + KEY_PROJECT_TRADE_ID + " INTEGER, "
                + KEY_PROJECT_TRADE_DESIGNATION + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_PROJECT_TRADE_TABLE);

        String CREATE_CUSTOMER_BRANCH_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMER_BRANCH + " ("

                + KEY_CUSTOMER_BRANCH_ID + " INTEGER, "
                + KEY_CUSTOMER_BRANCH_NAME + " TEXT, "
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_CUSTOMER_BRANCH_TABLE);

        String CREATW_TABLE_BUHENEART = "CREATE TABLE IF NOT EXISTS " + TABLE_BUHENEART + " ("

                + KEY_BEZEICHUNG + " TEXT, "
                + KEY_BUHENEARTART + " TEXT, "
                + KEY_SORT + " TEXT, "
                + KEY_SPARCHE + " TEXT)";

        db.execSQL(CREATW_TABLE_BUHENEART);

        String CREATE_TABLE_STAFFEL = "CREATE TABLE IF NOT EXISTS " + TABLE_STAFFEL + " ("

                + KEY_STAFFEL + " INTEGER, "
                + KEY_BEZEICHUNG + " TEXT, "
                + KEY_JSON_OBJECT + " TEXT)";

        db.execSQL(CREATE_TABLE_STAFFEL);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICE_RENTAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECHTS_FORM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALUTATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUNCTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENT_LANGUAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DECISION_MAKER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KaNr);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEATURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOST_SALE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVEL_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_DEVICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_TOPIC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICING_OFFLINE_STANDARD_PRICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICING_OFFLINE_EQUIPMENT_HEIGHT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUILDING_PROJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE_INSPECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_TRADE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_PHASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_AREA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_ART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_STAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_BRANCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUHENEART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFFEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICE_JSON);

        // Create tables again
        onCreate(db);
    }

    public void deleteAllTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_SITE_INSPECTION;
        db.execSQL(deleteSQL);
        //deleteSQL = "DELETE FROM " + TABLE_SITE_INSPECTION;
        //db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_BUILDING_PROJECT;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_DEVICE_TYPE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_CUSTOMER;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PRICING_OFFLINE_EQUIPMENT_HEIGHT;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PRICING_OFFLINE_STANDARD_PRICE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_EQUIPMENT_DEVICE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_LEVEL_GROUP;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_LOST_SALE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_EMPLOYEE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_DEVICE_DATA;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_BRANCH;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_DEVICE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_COUNTRY;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_RECHTS_FORM;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_SALUTATION;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_ACTIVITY_TYPE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PRICE_RENTAL;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_ACTIVITY_TOPIC;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_ACTIVITY_TOPIC;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_FUNCTION;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_DOCUMENT_LANGUAGE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_DECISION_MAKER;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PHOTO;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_KaNr;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_FEATURE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PRICE_RENTAL;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PRICING3INSERTJSON;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_AREA;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_STAGE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_TRADE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_ART;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_TYPE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_PHASE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_CUSTOMER_BRANCH;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_BUHENEART;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_STAFFEL;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void addBranch(Pricing1BranchData branch)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DESIGNATION, branch.getDesignation());
        values.put(KEY_CLIENT, branch.getClientId());
        values.put(KEY_ORT, branch.getOrt());
        values.put(KEY_PLZ, branch.getPlz());
        values.put(KEY_STRASSES, branch.getStrasse());


        long add = db.insert(TABLE_BRANCH, null, values);
        //Log.e(" in db to add in branch tabled "," database opearation done : "+add);
        db.close();
    }
    public void addPriceInfo(String json)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PRICE_JSON + " (json_string) VALUES (?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);
            stmt.bindString(1,json);
            stmt.execute();
            stmt.clearBindings();
                // long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }catch (Exception e){
            Log.e("addPriceInfo"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        try{

                ContentValues values = new ContentValues();
                values.put(JSON_STRING,json);
                db.insert(TABLE_PRICE_JSON, null, values);
                //Log.e("standard price", id+"");

            db.close();
        }
        catch (Exception e){

        }
        finally {
            db.close();
        }*/
    }
    public ArrayList<PriceInfoModelClass> getPriceInfo()
    {
        ArrayList<PriceInfoModelClass> arraylistPriceInfo = new ArrayList<PriceInfoModelClass>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PRICE_JSON,null);

        if (cursor.moveToFirst())
        {
            do
            {
                PriceInfoModelClass model = new PriceInfoModelClass();
                model.setStrJson(cursor.getString(cursor.getColumnIndex("json_string")));
                model.setId(cursor.getInt(cursor.getColumnIndex("json_id")));
                arraylistPriceInfo.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return arraylistPriceInfo;
    }
    public void deletePriceInfo(int Id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //String deleteSQL = "DELETE * FROM " + TABLE_PRICE_JSON;
        //String deleteSQL = "DELETE FROM " + TABLE_PRICE_JSON;
        String deleteSQL = "DELETE FROM " + TABLE_PRICE_JSON+ " WHERE " + JSON_ID + " = " + Id + "";
        db.execSQL(deleteSQL);
        db.close();
    }
    public void deletePriceInfoAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //String deleteSQL = "DELETE * FROM " + TABLE_PRICE_JSON;
        String deleteSQL = "DELETE FROM " + TABLE_PRICE_JSON;
        //String deleteSQL = "DELETE FROM " + TABLE_PRICE_JSON+ " WHERE " + JSON_ID + " = " + Id + "";
        db.execSQL(deleteSQL);
        db.close();
    }
    public int getCountPRice()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PRICE_JSON, null);
        int count = c.getCount();
        db.close();
        return count;
    }


    public void addLadefahzeg(ArrayList<LadefahrzeugComboBoxItemModel> arraylist)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_LADEFAHRZEUG + " (Bezeichnung,id,Sprache) VALUES ( ?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < arraylist.size(); x++){

                LadefahrzeugComboBoxItemModel model = new LadefahrzeugComboBoxItemModel();
                model = arraylist.get(x);

                stmt.bindString(1, model.getBezeichnung());
                stmt.bindString(2, String.valueOf(model.getId()));
                stmt.bindString(3, String.valueOf(model.getSprache()));
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addLadefahzeg"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try{
            for (int i = 0; i < arraylist.size(); i++)
            {
                ContentValues values = new ContentValues();
                LadefahrzeugComboBoxItemModel ladefahrzeugModel = new LadefahrzeugComboBoxItemModel();
                ladefahrzeugModel = arraylist.get(i);
                values.put(BEZEICHNUNG, ladefahrzeugModel.getBezeichnung());
                values.put(ID, ladefahrzeugModel.getId());
                values.put(SPRACHE, ladefahrzeugModel.getSprache());

                db.insert(TABLE_LADEFAHRZEUG, null, values);
                //Log.e("standard price", id+"");
            }
            db.setTransactionSuccessful();
            //db.close();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/


    }
    public ArrayList<LadefahrzeugComboBoxItemModel> getLadefahzeg()
    {
        ArrayList<LadefahrzeugComboBoxItemModel> ladefahrzeugArray = new ArrayList<LadefahrzeugComboBoxItemModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_LADEFAHRZEUG,null);
        if (cursor.moveToFirst())
        {
            do
            {
                LadefahrzeugComboBoxItemModel ladefahrzeugModel = new LadefahrzeugComboBoxItemModel();
                ladefahrzeugModel.setBezeichnung(cursor.getString(cursor.getColumnIndex("Bezeichnung")));
                ladefahrzeugModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
                ladefahrzeugModel.setSprache(cursor.getColumnIndex("Sprache"));

                ladefahrzeugArray.add(ladefahrzeugModel);
            } while (cursor.moveToNext());
        }
        db.close();
        return ladefahrzeugArray;
    }

    public void addDevice(Pricing1DeviceData device) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_HEIGHT_MAIN_GROUP, device.getHeight_main_group());
        values.put(KEY_DESIGNATION_DEVICE, device.getDesignation());

        db.replace(TABLE_DEVICE, null, values);
        db.close();
    }

    public void addPriceRental(Pricing1PriceRentalData priceRent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PRICE_RENTAL_DESIGNATION, priceRent.getDesignation());
        values.put(KEY_PRICE_RENTAL_UNIT, priceRent.getUnit());

        db.replace(TABLE_PRICE_RENTAL, null, values);
        db.close();
    }

    public void addBranch(ArrayList<Pricing1BranchData> listOfBranches)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_BRANCH + " (designation,client," +
                "ort,plz,strasses) VALUES ( ?,?,?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfBranches.size(); x++){

                Pricing1BranchData branchData = new Pricing1BranchData();
                branchData = listOfBranches.get(x);

                stmt.bindString(1, branchData.getDesignation());
                stmt.bindString(2, String.valueOf(branchData.getClientId()));
                stmt.bindString(3, branchData.getOrt());
                stmt.bindString(4, branchData.getPlz());
                stmt.bindString(5, branchData.getStrasse());
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addBranch"," excepiton while insert : "+e.toString());
        }

       /* SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Pricing1BranchData branch : listOfBranches)
            {


                ContentValues values = new ContentValues();

                values.put(KEY_DESIGNATION, branch.getDesignation());
                values.put(KEY_CLIENT, branch.getClientId());
                values.put(KEY_ORT, branch.getOrt());
                values.put(KEY_PLZ, branch.getPlz());
                values.put(KEY_STRASSES, branch.getStrasse());
                long id = db.replace(TABLE_BRANCH, null, values);
                Log.e(" in db to add in branch tabled "," database opearation done : "+id);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/


    }
    public ArrayList<Pricing1BranchData> getBranchList(int id)
    {
        ArrayList<Pricing1BranchData> arraylistBranchlist = new ArrayList<Pricing1BranchData>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_BRANCH + " Where client = " + id, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Pricing1BranchData branchList = new Pricing1BranchData();
                branchList.setDesignation(cursor.getString(cursor.getColumnIndex("designation")));
                branchList.setClientId(cursor.getInt(cursor.getColumnIndex("client")));
                branchList.setOrt(cursor.getString(cursor.getColumnIndex("ort")));
                branchList.setPlz(cursor.getString(cursor.getColumnIndex("plz")));
                branchList.setStrasse(cursor.getString(cursor.getColumnIndex("strasses")));

                arraylistBranchlist.add(branchList);
            } while (cursor.moveToNext());
        }
        db.close();
        return arraylistBranchlist;
    }

    public void addCustomerBranch(ArrayList<CustomerBranchModel> listOfBranches)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_CUSTOMER_BRANCH + " (id,name) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfBranches.size(); x++){

                CustomerBranchModel customerBranchModel = new CustomerBranchModel();
                customerBranchModel = listOfBranches.get(x);

                stmt.bindString(1, customerBranchModel.getId());
                stmt.bindString(2, customerBranchModel.getName());

                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addCustomerBranch"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (CustomerBranchModel branch : listOfBranches)
            {
                ContentValues values = new ContentValues();

                values.put(KEY_CUSTOMER_BRANCH_ID, branch.getId());
                values.put(KEY_CUSTOMER_BRANCH_NAME, branch.getName());

                long id = db.replace(TABLE_CUSTOMER_BRANCH, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addDevice(ArrayList<Pricing1DeviceData> listOfDeviceData)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_DEVICE + " (heightMainGroup,designation) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfDeviceData.size(); x++){

                Pricing1DeviceData deviceDataModel = new Pricing1DeviceData();
                deviceDataModel = listOfDeviceData.get(x);

                stmt.bindString(1, String.valueOf(deviceDataModel.getHeight_main_group()));
                stmt.bindString(2, deviceDataModel.getDesignation());
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addDevice"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Pricing1DeviceData deviceData : listOfDeviceData)
            {
                ContentValues values = new ContentValues();

                values.put(KEY_HEIGHT_MAIN_GROUP, deviceData.getHeight_main_group());
                values.put(KEY_DESIGNATION_DEVICE, deviceData.getDesignation());

                long id = db.replace(TABLE_DEVICE, null, values);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/


    }

    public void addPriceRental(ArrayList<Pricing1PriceRentalData> priceRents)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PRICE_RENTAL + " (designation,unit) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < priceRents.size(); x++){

                Pricing1PriceRentalData priceRental = new Pricing1PriceRentalData();
                priceRental = priceRents.get(x);

                stmt.bindString(1, priceRental.getDesignation());
                stmt.bindString(2, String.valueOf(priceRental.getUnit()));

                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addPriceRental"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Pricing1PriceRentalData priceRent : priceRents)
            {
                ContentValues values = new ContentValues();
                values.put(KEY_PRICE_RENTAL_DESIGNATION, priceRent.getDesignation());
                values.put(KEY_PRICE_RENTAL_UNIT, priceRent.getUnit());
                long id = db.replace(TABLE_PRICE_RENTAL, null, values);

            }
            db.setTransactionSuccessful();
            //db.close();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/


    }

    public void addCountries(ArrayList<CountryModel> countris)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_COUNTRY + " (id,designation) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < countris.size(); x++){

                CountryModel countryModel = new CountryModel();
                countryModel = countris.get(x);

                stmt.bindString(1, countryModel.getCountryId());
                stmt.bindString(2, countryModel.getCountryName());
                stmt.execute();
                stmt.clearBindings();
               // long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addCountries"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (CountryModel country : countris)
            {
                ContentValues countryValues = new ContentValues();
                countryValues.put(KEY_COUNTRY_ID, Integer.parseInt(country.getCountryId()));
                countryValues.put(KEY_COUNTRY_DESIGNATION, country.getCountryName());

                long id = db.replace(TABLE_COUNTRY, null, countryValues);

            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addActivityTypes(ArrayList<ActivityTypeModel> activitieTypes)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_ACTIVITY_TYPE + " (id,name) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < activitieTypes.size(); x++){

                ActivityTypeModel activityTypeModel = new ActivityTypeModel();
                activityTypeModel = activitieTypes.get(x);
                stmt.bindString(1, activityTypeModel.getActivityTypeId());
                stmt.bindString(2, activityTypeModel.getActivityTypeName());

                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addActivityTypes"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ActivityTypeModel activity : activitieTypes)
            {
                ContentValues countryValues = new ContentValues();
                countryValues.put(KEY_ACTIVITY_TYPE_ID, Integer.parseInt(activity.getActivityTypeId()));
                countryValues.put(KEY_ACTIVITY_TYPE_NAME, activity.getActivityTypeName());
                long id = db.replace(TABLE_ACTIVITY_TYPE, null, countryValues);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }
*/
    }

    public void addActivityTopics(ArrayList<ActivityTopicModel> activityTopics)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_ACTIVITY_TOPIC + " (id,name) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < activityTopics.size(); x++){

                ActivityTopicModel activityTopicModel = new ActivityTopicModel();
                activityTopicModel = activityTopics.get(x);

                stmt.bindString(1, activityTopicModel.getActivityTopicId());
                stmt.bindString(2, activityTopicModel.getActivityTopicName());
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addActivityTopics"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ActivityTopicModel activityTopic : activityTopics)
            {
                ContentValues countryValues = new ContentValues();
                countryValues.put(KEY_ACTIVITY_TOPIC_ID, Integer.parseInt(activityTopic.getActivityTopicId()));
                countryValues.put(KEY_ACTIVITY_TOPIC_NAME, activityTopic.getActivityTopicName());
                long id = db.replace(TABLE_ACTIVITY_TOPIC, null, countryValues);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addLegalForms(ArrayList<LegalFormModel> legalForms)
    {

        String sql = "INSERT OR REPLACE INTO " + TABLE_RECHTS_FORM + " (id,designation) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < legalForms.size(); x++){

                LegalFormModel legalFormModel = new LegalFormModel();
                legalFormModel = legalForms.get(x);

                stmt.bindString(1, legalFormModel.getRechtsFormId());
                stmt.bindString(2, legalFormModel.getRechtsFormDesignation());
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addLegalForms"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (LegalFormModel legalForm : legalForms)
            {
                ContentValues countryValues = new ContentValues();
                countryValues.put(KEY_RECHTS_FORM_ID, Integer.parseInt(legalForm.getRechtsFormId()));
                countryValues.put(KEY_RECHTS_FORM_DESIGNATION, legalForm.getRechtsFormDesignation());
                long id = db.replace(TABLE_RECHTS_FORM, null, countryValues);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addSalutation(ArrayList<SalutationModel> salutations)
    {

        String sql = "INSERT OR REPLACE INTO " + TABLE_SALUTATION + " (id,designation) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < salutations.size(); x++){

                SalutationModel salutationModel = new SalutationModel();
                salutationModel = salutations.get(x);

                stmt.bindString(1, salutationModel.getSalutationId());
                stmt.bindString(2, salutationModel.getSalutationDesignation());
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addSalutation"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (SalutationModel salutation : salutations)
            {
                ContentValues salutationValues = new ContentValues();
                salutationValues.put(KEY_SALUTATION_ID, Integer.parseInt(salutation.getSalutationId()));
                salutationValues.put(KEY_SALUTATION_DESIGNATION, salutation.getSalutationDesignation());
                long id = db.replace(TABLE_SALUTATION, null, salutationValues);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addEmployees(ArrayList<EmployeeModel> employees)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_EMPLOYEE + " (id,employeeJson) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < employees.size(); x++){

                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel = employees.get(x);

                stmt.bindString(1, employeeModel.getMitarbeiter());
                String json = new Gson().toJson(employeeModel);
                stmt.bindString(2,json);
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addEmployees"," excepiton while insert : "+e.toString());
        }
        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (EmployeeModel employee : employees)
            {
                ContentValues countryValues = new ContentValues();
                countryValues.put(KEY_EMPLOYEE_ID, Integer.parseInt(employee.getMitarbeiter()));

                String json = new Gson().toJson(employee);

                countryValues.put(KEY_EMPLOYEE_STRING_JSON, json);
                long id = db.replace(TABLE_EMPLOYEE, null, countryValues);
            }
            db.setTransactionSuccessful();
           //db.endTransaction();
            //db.close();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();

        }*/

    }

    public void addFunction(ArrayList<FunctionModel> functions)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_FUNCTION + " (id,designation) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < functions.size(); x++){

                FunctionModel functionModel = new FunctionModel();
                functionModel = functions.get(x);
                stmt.bindString(1, functionModel.getFunctionId());
                stmt.bindString(2, functionModel.getFunctionDesignation());
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addFunction"," excepiton while insert : "+e.toString());
        }
        /*if(functions != null && functions.size() > 0){
            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();
            try {
                for (FunctionModel function : functions)
                {
                    ContentValues functionValues = new ContentValues();
                    functionValues.put(KEY_FUNCTION_ID, Integer.parseInt(function.getFunctionId()));
                    functionValues.put(KEY_FUNCTION_DESIGNATION, function.getFunctionDesignation());
                    long id = db.replace(TABLE_FUNCTION, null, functionValues);
                }
                db.setTransactionSuccessful();
            }
            catch (Exception e){
                Log.e(" "," exception a; "+e.toString());
                db.endTransaction();
                db.close();
            }
            finally {
                db.endTransaction();
                db.close();
            }
        }*/
    }
    public void addSiteInspectionDeviceType(ArrayList<SiteInspectionDeviceTypeModel> listOfSiteInspectionDeviceType)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_DEVICE_TYPE + " (Bezeichnung,GeraeettypID,Hoehengruppe," +
                "arbeitshoehe) VALUES ( ?,?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfSiteInspectionDeviceType.size(); x++){

                SiteInspectionDeviceTypeModel deviceTypeModel = new SiteInspectionDeviceTypeModel();
                deviceTypeModel = listOfSiteInspectionDeviceType.get(x);

                stmt.bindString(1, deviceTypeModel.getBezeichnung());
                stmt.bindString(2, deviceTypeModel.getGeraeettypID());
                stmt.bindString(3, deviceTypeModel.getHoehengruppe());
                stmt.bindString(4, deviceTypeModel.getArbeitshoehe());
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addSiteInspectionDeviceType"," excepiton while insert : "+e.toString());
        }

        //SQLiteDatabase db = this.getWritableDatabase();
        /*if(listOfSiteInspectionDeviceType != null){
            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();
            try {
                //db.beginTransaction();
                for (SiteInspectionDeviceTypeModel deviceType : listOfSiteInspectionDeviceType)
                {
                    ContentValues languageValues = new ContentValues();
                    languageValues.put(KEY_DEVICE_TYPE_ID, deviceType.getGeraeettypID());
                    languageValues.put(KEY_DEVICE_TYPE_BEZEICHUNG, deviceType.getBezeichnung());
                    languageValues.put(KEY_DEVICE_TYPE_HOHENGRUPPE, deviceType.getHoehengruppe());
                    languageValues.put(KEY_DEVICE_TYPE_ARBEITSHOEHE, deviceType.getArbeitshoehe());

                    long id = db.replace(TABLE_DEVICE_TYPE, null, languageValues);
                }
                db.setTransactionSuccessful();
                //db.endTransaction();
                //db.close();
            }
            catch (Exception e){
                Log.e(" "," exception a; "+e.toString());
                db.endTransaction();
                db.close();
            }
            finally {
                db.endTransaction();
                db.close();
            }
        }*/
    }
    public void addDocumentLanguage(ArrayList<DocumentLanguageModel> languages)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_DOCUMENT_LANGUAGE + " (id,designation) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < languages.size(); x++){

                DocumentLanguageModel documentLanguageModel = new DocumentLanguageModel();
                documentLanguageModel = languages.get(x);

                stmt.bindString(1, documentLanguageModel.getDocumentLanguageId());
                stmt.bindString(2, documentLanguageModel.getDocumentLanguageDesignation());

                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
               // Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addDocumentLanguage"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (DocumentLanguageModel language : languages)
            {
                ContentValues languageValues = new ContentValues();
                languageValues.put(KEY_DOCUMENT_LANGUAGE_ID, Integer.parseInt(language.getDocumentLanguageId()));
                languageValues.put(KEY_DOCUMENT_LANGUAGE_DESIGNATION, language.getDocumentLanguageDesignation());
                long id = db.replace(TABLE_DOCUMENT_LANGUAGE, null, languageValues);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addDecisionMakers(ArrayList<DecisionMakerModel> decisionMakers)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_DECISION_MAKER + " (id,designation) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < decisionMakers.size(); x++){

                DecisionMakerModel decisionMakerModel = new DecisionMakerModel();
                decisionMakerModel = decisionMakers.get(x);

                stmt.bindString(1, decisionMakerModel.getDecisionMakerId());
                stmt.bindString(2, decisionMakerModel.getDecisionMakerDesignation());
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addDecisionMakers"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (DecisionMakerModel decisionMaker : decisionMakers)
            {
                ContentValues decisionMakerValues = new ContentValues();
                decisionMakerValues.put(KEY_DECISION_MAKER_ID, Integer.parseInt(decisionMaker.getDecisionMakerId()));
                decisionMakerValues.put(KEY_DECISION_MAKER_DESIGNATION, decisionMaker.getDecisionMakerDesignation());
                long id = db.replace(TABLE_DECISION_MAKER, null, decisionMakerValues);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addFeatures(ArrayList<FeatureModel> features)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_FEATURE + " (id,designation) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < features.size(); x++){

                FeatureModel featureModel = new FeatureModel();
                featureModel = features.get(x);

                stmt.bindString(1, featureModel.getMerkmal());
                stmt.bindString(2, featureModel.getBezeichnung());
                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addFeatures"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (FeatureModel feature : features)
            {
                ContentValues featureValues = new ContentValues();
                featureValues.put(KEY_FEATURE_ID, Integer.parseInt(feature.getMerkmal()));
                featureValues.put(KEY_FEATURE_NAME, feature.getBezeichnung());
                long id = db.replace(TABLE_FEATURE, null, featureValues);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addKaNrData(Pricing2KaNrData kaNr)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues kaNrValues = new ContentValues();
        kaNrValues.put(KEY_KaNr, kaNr.getKaNr());
        kaNrValues.put(KEY_KaNr_NAME, kaNr.getName());
        db.insert(TABLE_KaNr, null, kaNrValues);
        db.close();
    }

    public void addProjectStage(ArrayList<ProjectStagesModel> listOfProjectsStage)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PROJECT_STAGE + " (id,designation) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfProjectsStage.size(); x++){

                ProjectStagesModel stagesModel = new ProjectStagesModel();
                stagesModel = listOfProjectsStage.get(x);

                stmt.bindString(1, stagesModel.getProjectStageId());
                stmt.bindString(2, stagesModel.getProjectStageDesignation());

                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
               // Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addProjectStage"," excepiton while insert : "+e.toString());
        }
        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ProjectStagesModel projectStage : listOfProjectsStage)
            {
                ContentValues values = new ContentValues();
                values.put(KEY_PROJECT_STAGE_ID, Integer.parseInt(projectStage.getProjectStageId()));
                values.put(KEY_PROJECT_STAGE_DESIGNATION, projectStage.getProjectStageDesignation());
                long id = db.replace(TABLE_PROJECT_STAGE, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addProjectArea(ArrayList<ProjectAreaModel> listOfProjectsArea)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PROJECT_AREA + " (id,designation) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfProjectsArea.size(); x++){

                ProjectAreaModel projectAreaModel = new ProjectAreaModel();
                projectAreaModel = listOfProjectsArea.get(x);

                stmt.bindString(1, projectAreaModel.getProjectAreaId());
                stmt.bindString(2, projectAreaModel.getProjectAreaDesignation());

                stmt.execute();
                stmt.clearBindings();
               // long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addProjectArea"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ProjectAreaModel projectArea : listOfProjectsArea)
            {
                ContentValues values = new ContentValues();
                values.put(KEY_PROJECT_AREA_ID, Integer.parseInt(projectArea.getProjectAreaId()));
                values.put(KEY_PROJECT_AREA_DESIGNATION, projectArea.getProjectAreaDesignation());
                long id = db.replace(TABLE_PROJECT_AREA, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }
*/
    }

    public void addProjectArt(ArrayList<ProjectArtModel> listOfProjectsArt)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PROJECT_ART + " (id,designation) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfProjectsArt.size(); x++){

                ProjectArtModel projectArtModel = new ProjectArtModel();
                projectArtModel = listOfProjectsArt.get(x);

                stmt.bindString(1, projectArtModel.getProjectArtId());
                stmt.bindString(2, projectArtModel.getProjectArtdesignation());

                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addProjectArt"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ProjectArtModel projectArt : listOfProjectsArt)
            {
                ContentValues values = new ContentValues();
                values.put(KEY_PROJECT_ART_ID, Integer.parseInt(projectArt.getProjectArtId()));
                values.put(KEY_PROJECT_ART_DESIGNATION, projectArt.getProjectArtdesignation());
                long id = db.replace(TABLE_PROJECT_ART, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addProjectType(ArrayList<ProjectTypeModel> listOfProjectType)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PROJECT_TYPE + " (id,designation) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfProjectType.size(); x++){

                ProjectTypeModel projectTypeModel = new ProjectTypeModel();
                projectTypeModel = listOfProjectType.get(x);

                stmt.bindString(1, projectTypeModel.getProjectTypeId());
                stmt.bindString(2, projectTypeModel.getProjectTypeDesignation());

                stmt.execute();
                stmt.clearBindings();
                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addProjectType"," excepiton while insert : "+e.toString());
        }

       /* SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ProjectTypeModel projectType : listOfProjectType)
            {
                ContentValues values = new ContentValues();
                values.put(KEY_PROJECT_TYPE_ID, Integer.parseInt(projectType.getProjectTypeId()));
                values.put(KEY_PROJECT_TYPE_DESIGNATION, projectType.getProjectTypeDesignation());
                long id = db.replace(TABLE_PROJECT_TYPE, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();

        }*/

    }

    public void addProjectPhase(ArrayList<ProjectPhaseModel> listOfProjectPhase)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PROJECT_PHASE + " (id,designation) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfProjectPhase.size(); x++){

                ProjectPhaseModel phaseModel = new ProjectPhaseModel();
                phaseModel = listOfProjectPhase.get(x);

                stmt.bindString(1, phaseModel.getProjectPhaseId());
                stmt.bindString(2, phaseModel.getProjectPhaseDesignation());

                stmt.execute();
                stmt.clearBindings();
               // long count =stmt.executeInsert();
               // Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addProjectPhase"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ProjectPhaseModel projectPhase : listOfProjectPhase)
            {
                ContentValues values = new ContentValues();
                values.put(KEY_PROJECT_TYPE_ID, Integer.parseInt(projectPhase.getProjectPhaseId()));
                values.put(KEY_PROJECT_TYPE_DESIGNATION, projectPhase.getProjectPhaseDesignation());
                long id = db.replace(TABLE_PROJECT_PHASE, null, values);
            }
            db.setTransactionSuccessful();

        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addProjectTrade(ArrayList<ProjectTradeModel> listOfProjectTrade)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PROJECT_TRADE + " (id,designation) VALUES (?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfProjectTrade.size(); x++){

                ProjectTradeModel tradeModel = new ProjectTradeModel();
                tradeModel = listOfProjectTrade.get(x);

                stmt.bindString(1, tradeModel.getProjectTradeId());
                stmt.bindString(2, tradeModel.getProjectTradeDesignation());

                stmt.execute();
                stmt.clearBindings();
               // long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addProjectTrade"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ProjectTradeModel projectTrade : listOfProjectTrade)
            {
                ContentValues values = new ContentValues();
                values.put(KEY_PROJECT_TRADE_ID, Integer.parseInt(projectTrade.getProjectTradeId()));
                values.put(KEY_PROJECT_TRADE_DESIGNATION, projectTrade.getProjectTradeDesignation());
                long id = db.replace(TABLE_PROJECT_TRADE, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addPriceStaffel(ArrayList<PriceStaffelModel> listOfPriceStaffel)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_STAFFEL + " (Staffel,Bezeichnung,JsonObject) VALUES (?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfPriceStaffel.size(); x++){

                PriceStaffelModel priceStaffelModel = new PriceStaffelModel();
                priceStaffelModel = listOfPriceStaffel.get(x);

                stmt.bindString(1, priceStaffelModel.getStaffel());
                stmt.bindString(2, priceStaffelModel.getBezeichnung());
                stmt.bindString(3, new Gson().toJson(priceStaffelModel));


                stmt.execute();
                stmt.clearBindings();
               // long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addPriceStaffel"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (PriceStaffelModel priceStaffel : listOfPriceStaffel)
            {
                ContentValues values = new ContentValues();
                values.put(KEY_STAFFEL, Integer.parseInt(priceStaffel.getStaffel()));
                values.put(KEY_BEZEICHUNG, priceStaffel.getBezeichnung());
                values.put(KEY_JSON_OBJECT, new Gson().toJson(priceStaffel));
                long id = db.replace(TABLE_STAFFEL, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public void addCustomer(CustomerDatabaseModel customerDatabaseModel)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_CUSTOMER + " (CustomerNo,KaNr,CustomerContact,MatchCode,Name1,Road" +
                ",ZipCode,Place,Telephone,CustomerDetail,CustomerContactPerson,CustomerActivity,CustomerProject,CustomerOffer," +
                "CustomerOpenOrder,CustomerCompletedOrder,CustomerOpenOffer,CustomerLostSale) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?)";
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);
            stmt.bindString(1, customerDatabaseModel.getCustomerNo());
            stmt.bindString(2, customerDatabaseModel.getKaNr());
            stmt.bindString(3, customerDatabaseModel.getCustomerContact());
            stmt.bindString(4, customerDatabaseModel.getMatchCode());
            stmt.bindString(5, customerDatabaseModel.getName1());
            stmt.bindString(6, customerDatabaseModel.getRoad());
            stmt.bindString(7, customerDatabaseModel.getZipCode());
            stmt.bindString(8, customerDatabaseModel.getPlace());
            stmt.bindString(9, customerDatabaseModel.getTelephone());
            String customerDetail = new Gson().toJson(customerDatabaseModel.getCustomerModel());
            stmt.bindString(10, customerDetail);
            String customerContactPerson = new Gson().toJson(customerDatabaseModel.getListOfContactPerson());
            stmt.bindString(11, customerContactPerson);
            String customerActivity = new Gson().toJson(customerDatabaseModel.getListOfActivity());
            stmt.bindString(12, customerActivity);
            String customerProject = new Gson().toJson(customerDatabaseModel.getListOfProject());
            stmt.bindString(13, customerProject);
            String customerOffer = new Gson().toJson(customerDatabaseModel.getListOfOffer());
            stmt.bindString(14, customerOffer);
            String customerOpenOrder = new Gson().toJson(customerDatabaseModel.getListOfOpenOrder());
            stmt.bindString(15, customerOpenOrder);
            String customerCompletedOrder = new Gson().toJson(customerDatabaseModel.getListOfCompletedOrder());
            stmt.bindString(16, customerCompletedOrder);
            String customerOpenOffer = new Gson().toJson(customerDatabaseModel.getListOfOffer());
            stmt.bindString(17, customerOpenOffer);
            String customerLostSale = new Gson().toJson(customerDatabaseModel.getListOfLostSale());
            stmt.bindString(18, customerLostSale);


            stmt.execute();
            stmt.clearBindings();
                // long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);


            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }catch (Exception e){
            db.endTransaction();
            db.close();
            Log.e("addCustomer"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CUSTOMER_CUSTOMER_NO, customerDatabaseModel.getCustomerNo());
        values.put(KEY_CUSTOMER_KANR, customerDatabaseModel.getKaNr());
        values.put(KEY_CUSTOMER_KONTAKT, customerDatabaseModel.getCustomerContact());
        values.put(KEY_CUSTOMER_MATCHCODE, customerDatabaseModel.getMatchCode());
        values.put(KEY_CUSTOMER_NAME_1, customerDatabaseModel.getName1());
        values.put(KEY_CUSTOMER_ROAD, customerDatabaseModel.getRoad());
        values.put(KEY_CUSTOMER_ZIP_CODE, customerDatabaseModel.getZipCode());
        values.put(KEY_CUSTOMER_PLACE, customerDatabaseModel.getPlace());
        values.put(KEY_CUSTOMER_Tel, customerDatabaseModel.getTelephone());
        String customerDetail = new Gson().toJson(customerDatabaseModel.getCustomerModel());
        values.put(KEY_CUSTOMER_DETAIL, customerDetail);
        String customerContactPerson = new Gson().toJson(customerDatabaseModel.getListOfContactPerson());
        values.put(KEY_CUSTOMER_CONTACT_PERSON, customerContactPerson);
        String customerActivity = new Gson().toJson(customerDatabaseModel.getListOfActivity());
        values.put(KEY_CUSTOMER_ACTIVITY, customerActivity);
        String customerProject = new Gson().toJson(customerDatabaseModel.getListOfProject());
        values.put(KEY_CUSTOMER_PROJECT, customerProject);
        String customerOffer = new Gson().toJson(customerDatabaseModel.getListOfOffer());
        values.put(KEY_CUSTOMER_OFFER, customerOffer);
        String customerOpenOrder = new Gson().toJson(customerDatabaseModel.getListOfOpenOrder());
        values.put(KEY_CUSTOMER_OPEN_ORDER, customerOpenOrder);
        String customerCompletedOrder = new Gson().toJson(customerDatabaseModel.getListOfCompletedOrder());
        values.put(KEY_CUSTOMER_COMPLETED_ORDER, customerCompletedOrder);
        String customerOpenOffer = new Gson().toJson(customerDatabaseModel.getListOfOffer());
        values.put(KEY_CUSTOMER_OPEN_OFFER, customerOpenOffer);
        String customerLostSale = new Gson().toJson(customerDatabaseModel.getListOfLostSale());
        values.put(KEY_CUSTOMER_LOST_SALE, customerLostSale);
        db.replace(TABLE_CUSTOMER, null, values);
        db.close();*/
    }

    public void updateCustomerDetails(CustomerModel customerModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CUSTOMER_CUSTOMER_NO, customerModel.getKundenNr());
        values.put(KEY_CUSTOMER_KANR, customerModel.getKaNr());
        values.put(KEY_CUSTOMER_KONTAKT, customerModel.getKontakt());
        values.put(KEY_CUSTOMER_MATCHCODE, customerModel.getMatchCode());
        values.put(KEY_CUSTOMER_NAME_1, customerModel.getName1());
        values.put(KEY_CUSTOMER_ROAD, customerModel.getStrasse());
        values.put(KEY_CUSTOMER_ZIP_CODE, customerModel.getPLZ());
        values.put(KEY_CUSTOMER_PLACE, customerModel.getOrt());
        values.put(KEY_CUSTOMER_Tel, customerModel.getTelefon());
        String customerDetail = new Gson().toJson(customerModel);
        values.put(KEY_CUSTOMER_DETAIL, customerDetail);
        String where = KEY_CUSTOMER_KONTAKT + "=?";
        String[] whereArgs = new String[] {String.valueOf(customerModel.getKontakt())};
        db.update(TABLE_CUSTOMER, values, where, whereArgs);
        db.close();
    }

    public void updateCustomerCustomerContactPerson(String kontakt, ArrayList<ContactPersonModel> listOfContactPerson)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_CUSTOMER_CUSTOMER_NO, customerModel.getKundenNr());
        String customerContactPersons = new Gson().toJson(listOfContactPerson);
        values.put(KEY_CUSTOMER_CONTACT_PERSON, customerContactPersons);
        String where = KEY_CUSTOMER_KONTAKT + "=?";
        String[] whereArgs = new String[] {kontakt};
        db.update(TABLE_CUSTOMER, values, where, whereArgs);
        db.close();
    }

    public void updateCustomerActivityProjectOffer(String kontakt, ArrayList<CustomerActivityModel> listOfActivity,
                ArrayList<CustomerProjectModel> listOfProject, ArrayList<CustomerOfferModel> listOfOffer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_CUSTOMER_CUSTOMER_NO, customerModel.getKundenNr());
        String customerActivity = new Gson().toJson(listOfActivity);
        values.put(KEY_CUSTOMER_ACTIVITY, customerActivity);
        String customerProjects = new Gson().toJson(listOfProject);
        values.put(KEY_CUSTOMER_PROJECT, customerProjects);
        String customerOffers = new Gson().toJson(listOfOffer);
        values.put(KEY_CUSTOMER_OFFER, customerOffers);
        String where = KEY_CUSTOMER_KONTAKT + "=?";
        String[] whereArgs = new String[] {kontakt};
        db.update(TABLE_CUSTOMER, values, where, whereArgs);
        db.close();
    }

    public void updateCustomer(CustomerDatabaseModel customerDatabaseModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CUSTOMER_CUSTOMER_NO, customerDatabaseModel.getCustomerNo());
        values.put(KEY_CUSTOMER_KANR, customerDatabaseModel.getKaNr());
        values.put(KEY_CUSTOMER_KONTAKT, customerDatabaseModel.getCustomerContact());
        values.put(KEY_CUSTOMER_MATCHCODE, customerDatabaseModel.getMatchCode());
        values.put(KEY_CUSTOMER_NAME_1, customerDatabaseModel.getName1());
        values.put(KEY_CUSTOMER_ROAD, customerDatabaseModel.getRoad());
        values.put(KEY_CUSTOMER_ZIP_CODE, customerDatabaseModel.getZipCode());
        values.put(KEY_CUSTOMER_PLACE, customerDatabaseModel.getPlace());
        values.put(KEY_CUSTOMER_Tel, customerDatabaseModel.getTelephone());
        String customerDetail = new Gson().toJson(customerDatabaseModel.getCustomerModel());
        values.put(KEY_CUSTOMER_DETAIL, customerDetail);
        String customerContactPerson = new Gson().toJson(customerDatabaseModel.getListOfContactPerson());
        values.put(KEY_CUSTOMER_CONTACT_PERSON, customerContactPerson);
        String customerActivity = new Gson().toJson(customerDatabaseModel.getListOfActivity());
        values.put(KEY_CUSTOMER_ACTIVITY, customerActivity);
        String customerProject = new Gson().toJson(customerDatabaseModel.getListOfProject());
        values.put(KEY_CUSTOMER_PROJECT, customerProject);
        String customerOffer = new Gson().toJson(customerDatabaseModel.getListOfOffer());
        values.put(KEY_CUSTOMER_OFFER, customerOffer);
        String customerOpenOrder = new Gson().toJson(customerDatabaseModel.getListOfOpenOrder());
        values.put(KEY_CUSTOMER_OPEN_ORDER, customerOpenOrder);
        String customerCompletedOrder = new Gson().toJson(customerDatabaseModel.getListOfCompletedOrder());
        values.put(KEY_CUSTOMER_COMPLETED_ORDER, customerCompletedOrder);
        String customerOpenOffer = new Gson().toJson(customerDatabaseModel.getListOfOffer());
        values.put(KEY_CUSTOMER_OPEN_OFFER, customerOpenOffer);
        String customerLostSale = new Gson().toJson(customerDatabaseModel.getListOfLostSale());
        values.put(KEY_CUSTOMER_LOST_SALE, customerLostSale);
        String where = KEY_CUSTOMER_KONTAKT + "=?";
        String[] whereArgs = new String[] {String.valueOf(customerDatabaseModel.getCustomerContact())};
        db.update(TABLE_CUSTOMER, values, where, whereArgs);
        db.close();
    }

    public int getTotalDevice()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DEVICE_DATA, null);
        int count = c.getCount();
        db.close();
        return count;
    }

    public ArrayList<CustomerModel> getAllCustomer()
    {
        ArrayList<CustomerModel> listOfCustomer = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select " + KEY_CUSTOMER_DETAIL + " from " + TABLE_CUSTOMER;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
                CustomerModel country = new CustomerModel();
                String result = cursor.getString(0);
                country = new Gson().fromJson(result, CustomerModel.class);
                listOfCustomer.add(country);

            } while (cursor.moveToNext());
        }
        Log.e("list of customer", listOfCustomer.size() + " " + new Gson().toJson(listOfCustomer).toString());
        db.close();
        return listOfCustomer;
    }

    public ArrayList<CustomerModel> getCustomer(CustomerSearchPagingRequestModel customerSearchRequestModel)
    {
        ArrayList<CustomerModel> listOfCustomer = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select " + KEY_CUSTOMER_DETAIL + " from " + TABLE_CUSTOMER + " WHERE " +
                KEY_CUSTOMER_CUSTOMER_NO + " LIKE '%" + customerSearchRequestModel.getKundenNr() +"%'" + " AND " +
                KEY_CUSTOMER_KANR + " LIKE '%" + customerSearchRequestModel.getKaNr() +"%'" + " AND " +
                KEY_CUSTOMER_MATCHCODE + " LIKE '%" + customerSearchRequestModel.getMatchCode() +"%'" + " AND " +
                KEY_CUSTOMER_NAME_1 + " LIKE '%" + customerSearchRequestModel.getName1() +"%'" + " AND " +
                KEY_CUSTOMER_ROAD + " LIKE '%" + customerSearchRequestModel.getStrasse() +"%'" + " AND " +
                KEY_CUSTOMER_ZIP_CODE + " LIKE '%" + customerSearchRequestModel.getPLZ() +"%'" + " AND " +
                KEY_CUSTOMER_PLACE + " LIKE '%" + customerSearchRequestModel.getOrt() +"%'" + " AND " +
                KEY_CUSTOMER_Tel + " LIKE '%" + customerSearchRequestModel.getTelefon() +"%'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                CustomerModel country = new CustomerModel();
                String result = cursor.getString(0);
                country = new Gson().fromJson(result, CustomerModel.class);
                listOfCustomer.add(country);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfCustomer;
    }

    public boolean isCustomerAvailable(String Kontakt)
    {
       // Cursor cursor = db.rawQuery("select id,branch,hgrp,md,rentalPrice,sb,hf,sp,hb,best from " +
                //TABLE_LOST_SALE + " where kundenNr=? ", new String[]{KundenNr});

        //&&&&&
        //String query = "select CustomerContact from " + TABLE_CUSTOMER + " where CustomerContact=? ", new String[]{Kontakt});
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select CustomerContact from " + TABLE_CUSTOMER + " where CustomerContact=? ", new String[]{Kontakt});

        if (cursor.getCount() > 0)
        {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public CustomerDatabaseModel getCustomer(String Kontakt)
    {
        //ArrayList<CustomerDatabaseModel> listOfCustomer = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "select * from " + TABLE_CUSTOMER + " WHERE " +
               // KEY_CUSTOMER_KONTAKT + " = '" + Kontakt +"'";

        Cursor cursor = db.rawQuery("select * from " + TABLE_CUSTOMER + " WHERE CustomerContact=?",new String[]{Kontakt});
        CustomerDatabaseModel customerDatabaseModel = new CustomerDatabaseModel();
        if (cursor.moveToFirst())
        {
            customerDatabaseModel.setCustomerNo(cursor.getString(0));
            customerDatabaseModel.setKaNr(cursor.getString(1));
            customerDatabaseModel.setCustomerContact(cursor.getString(2));
            customerDatabaseModel.setMatchCode(cursor.getString(3));
            customerDatabaseModel.setName1(cursor.getString(4));
            customerDatabaseModel.setRoad(cursor.getString(5));
            customerDatabaseModel.setZipCode(cursor.getString(6));
            customerDatabaseModel.setPlace(cursor.getString(7));
            customerDatabaseModel.setTelephone(cursor.getString(8));

            String customerDetail = cursor.getString(9);
            customerDatabaseModel.setCustomerModel(new Gson().fromJson(customerDetail, CustomerModel.class));

            String customerContactPerson = cursor.getString(10);
            ArrayList<ContactPersonModel> listOfContactPerson = new ArrayList<>();
            ContactPersonModel.extractFromJson(customerContactPerson, listOfContactPerson);
            customerDatabaseModel.setListOfContactPerson(listOfContactPerson);

            String customerActivity = cursor.getString(11);
            ArrayList<CustomerActivityModel> listOfActivity = new ArrayList<>();
            CustomerActivityModel.extractFromJson(customerActivity, listOfActivity);
            customerDatabaseModel.setListOfActivity(listOfActivity);

            String customerProject = cursor.getString(12);
            ArrayList<CustomerProjectModel> listOfProject = new ArrayList<>();
            CustomerProjectModel.extractFromJson(customerProject, listOfProject);
            customerDatabaseModel.setListOfProject(listOfProject);

            String customerOffer = cursor.getString(13);
            ArrayList<CustomerOfferModel> listOfOffer = new ArrayList<>();
            CustomerOfferModel.extractFromJson(customerOffer, listOfOffer);
            customerDatabaseModel.setListOfOffer(listOfOffer);

            String customerOpenOrder = cursor.getString(14);
            ArrayList<CustomerOpenOrderModel> listOfOpenOrder = new ArrayList<>();
            CustomerOpenOrderModel.extractFromJson(customerOpenOrder, listOfOpenOrder);
            customerDatabaseModel.setListOfOpenOrder(listOfOpenOrder);

            String customerCompletedOrder = cursor.getString(15);
            ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder = new ArrayList<>();
            CustomerCompletedOrderModel.extractFromJson(customerCompletedOrder, listOfCompletedOrder);
            customerDatabaseModel.setListOfCompletedOrder(listOfCompletedOrder);

            String customerOpenSales = cursor.getString(16);
            ArrayList<CustomerOpenOfferModel> listOFOpenOffer = new ArrayList<>();
            CustomerOpenOfferModel.extractFromJson(customerOpenSales, listOFOpenOffer);
            customerDatabaseModel.setListOfOpenOffer(listOFOpenOffer);

            String customerLostSale = cursor.getString(17);
            ArrayList<CustomerLostSaleDataModel> listOfLostSale = new ArrayList<>();
            CustomerLostSaleDataModel.extractFromJson(customerLostSale, listOfLostSale);
            customerDatabaseModel.setListOfLostSale(listOfLostSale);
        }
        db.close();
        return customerDatabaseModel;
    }

    public int getCustomerTableRowsSize()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + TABLE_CUSTOMER;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        db.close();
        return count;
    }

//    public ArrayList<CustomerDatabaseModel> getCustomer(String kundenNr)
//    {
//        ArrayList<CustomerDatabaseModel> listOfCustomer = new ArrayList<>();
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "select * from " + TABLE_CUSTOMER + " WHERE " +
//                KEY_CUSTOMER_CUSTOMER_NO + " = '" + kundenNr +"'";
//        Log.e("select query", query);
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst())
//        {
//            do
//            {
//                CustomerDatabaseModel customerDatabaseModel = new CustomerDatabaseModel();
//                String customerDetail = cursor.getString(8);
//                customerDatabaseModel.setCustomerModel(new Gson().fromJson(customerDetail, CustomerModel.class));
//
//                String customerContactPerson = cursor.getString(9);
//                ArrayList<ContactPersonModel> listOfContactPerson = new ArrayList<>();
//                ContactPersonModel.extractFromJson(customerContactPerson, listOfContactPerson);
//                customerDatabaseModel.setListOfContactPerson(listOfContactPerson);
//
//                String customerActivity = cursor.getString(10);
//                ArrayList<CustomerActivityModel> listOfActivity = new ArrayList<>();
//                CustomerActivityModel.extractFromJson(customerActivity, listOfActivity);
//                customerDatabaseModel.setListOfActivity(listOfActivity);
//
//                String customerProject = cursor.getString(11);
//                ArrayList<CustomerProjectModel> listOfProject = new ArrayList<>();
//                CustomerProjectModel.extractFromJson(customerProject, listOfProject);
//                customerDatabaseModel.setListOfProject(listOfProject);
//
//                String customerOffer = cursor.getString(12);
//                ArrayList<CustomerOfferModel> listOfOffer = new ArrayList<>();
//                CustomerOfferModel.extractFromJson(customerOffer, listOfOffer);
//                customerDatabaseModel.setListOfOffer(listOfOffer);
//
//                String customerOpenOrder = cursor.getString(13);
//                ArrayList<CustomerOpenOrderModel> listOfOpenOrder = new ArrayList<>();
//                CustomerOpenOrderModel.extractFromJson(customerOpenOrder, listOfOpenOrder);
//                customerDatabaseModel.setListOfOpenOrder(listOfOpenOrder);
//
//                String customerCompletedOrder = cursor.getString(14);
//                ArrayList<CustomerCompletedOrderModel> listOfCompletedOrder = new ArrayList<>();
//                CustomerCompletedOrderModel.extractFromJson(customerCompletedOrder, listOfCompletedOrder);
//                customerDatabaseModel.setListOfCompletedOrder(listOfCompletedOrder);
//
//                String customerOpenSales = cursor.getString(15);
//                ArrayList<CustomerOpenOfferModel> listOFOpenOffer = new ArrayList<>();
//                CustomerOpenOfferModel.extractFromJson(customerOpenSales, listOFOpenOffer);
//                customerDatabaseModel.setListOfOpenOffer(listOFOpenOffer);
//
//                String customerLostSale = cursor.getString(16);
//                ArrayList<CustomerLostSaleDataModel> listOfLostSale = new ArrayList<>();
//                CustomerLostSaleDataModel.extractFromJson(customerLostSale, listOfLostSale);
//                customerDatabaseModel.setListOfLostSale(listOfLostSale);
//
//                listOfCustomer.add(customerDatabaseModel);
//            } while (cursor.moveToNext());
//        }
//
//        return listOfCustomer;
//    }

    public ArrayList<CountryModel> getCountries()
    {
        ArrayList<CountryModel> listOfCountry = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_COUNTRY, null);
        if (cursor.moveToFirst()) {
            do {
                CountryModel country = new CountryModel();

                country.setCountryId(cursor.getInt(0) + "");
                country.setCountryName(cursor.getString(1));
                listOfCountry.add(country);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfCountry;
    }
    public CountryModel getCountryFromName(String designation)
    {
        CountryModel country = new CountryModel();
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery("select * from " + TABLE_FUNCTION + " WHERE " + KEY_FUNCTION_ID + "=" + id, null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_COUNTRY +" where "+ KEY_COUNTRY_DESIGNATION + " ="+designation, null);
        if (cursor.moveToFirst()) {
            do {
                country.setCountryId(cursor.getInt(0) + "");
                country.setCountryName(cursor.getString(1));

            } while (cursor.moveToNext());
        }
        db.close();
        return country;
    }

    public ArrayList<CustomerBranchModel> getCustomerBranches()
    {
        ArrayList<CustomerBranchModel> listOfBranch = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_CUSTOMER_BRANCH, null);
        if (cursor.moveToFirst())
        {
            do
            {
                CustomerBranchModel country = new CustomerBranchModel();

                country.setId(cursor.getInt(0) + "");
                country.setName(cursor.getString(1));
                listOfBranch.add(country);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfBranch;
    }

    public ArrayList<ActivityTypeModel> getActivityTypes()
    {
        ArrayList<ActivityTypeModel> listOfActivity = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_ACTIVITY_TYPE, null);
        if (cursor.moveToFirst())
        {
            do
            {
                ActivityTypeModel activity = new ActivityTypeModel();

                activity.setActivityTypeId(cursor.getInt(0) + "");
                activity.setActivityTypeName(cursor.getString(1));
                listOfActivity.add(activity);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfActivity;
    }

    public ArrayList<ActivityTopicModel> getActivityTopics()
    {
        ArrayList<ActivityTopicModel> listOfActivityTopic = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_ACTIVITY_TOPIC, null);
        if (cursor.moveToFirst())
        {
            do
            {
                ActivityTopicModel activityTopic = new ActivityTopicModel();

                activityTopic.setActivityTopicId(cursor.getInt(0) + "");
                activityTopic.setActivityTopicName(cursor.getString(1));
                listOfActivityTopic.add(activityTopic);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfActivityTopic;
    }

    public ArrayList<FeatureModel> getFeatures()
    {
        ArrayList<FeatureModel> listOfFeature = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_FEATURE, null);
        if (cursor.moveToFirst()) {
            do {
                FeatureModel feature = new FeatureModel();

                feature.setMerkmal(cursor.getInt(0) + "");
                feature.setBezeichnung(cursor.getString(1));
                listOfFeature.add(feature);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfFeature;
    }

    public ArrayList<EmployeeModel> getEmployees()
    {
        ArrayList<EmployeeModel> listOfEmployee = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_EMPLOYEE, null);
        if (cursor.moveToFirst()) {
            do {
                EmployeeModel employee = new EmployeeModel();
                employee = new Gson().fromJson(cursor.getString(1), EmployeeModel.class);
//                employee.setEmployeeId(cursor.getInt(0) + "");
//                employee.setEmployeeName(cursor.getString(1));
                listOfEmployee.add(employee);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfEmployee;
    }

    public ArrayList<Pricing2KaNrData> getPricing2KaNrData(String limit)
    {
        ArrayList<Pricing2KaNrData> KaNr_list = new ArrayList<Pricing2KaNrData>();
        String Query;
        if(limit.equals("0"))
        {
            Query = "select distinct kaNr as kaNr,name as name from " + TABLE_KaNr+ " LIMIT 1";
        }
        else
        {
            Query = "select distinct kaNr as kaNr,name as name from " + TABLE_KaNr;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            do {
                Pricing2KaNrData kanr = new Pricing2KaNrData();

                kanr.setKaNr(cursor.getInt(0));
                kanr.setName(cursor.getString(1));
                KaNr_list.add(kanr);

            } while (cursor.moveToNext());
        }
        db.close();
        return KaNr_list;
    }

    public ArrayList<DecisionMakerModel> getDecisionMakers()
    {
        ArrayList<DecisionMakerModel> listOfDecisionMaker = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_DECISION_MAKER, null);
        if (cursor.moveToFirst())
        {
            do
            {
                DecisionMakerModel decisionMaker = new DecisionMakerModel();

                decisionMaker.setDecisionMakerId(cursor.getInt(0) + "");
                decisionMaker.setDecisionMakerDesignation(cursor.getString(1));
                listOfDecisionMaker.add(decisionMaker);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfDecisionMaker;
    }

    public ArrayList<DocumentLanguageModel> getDocumentLanguages()
    {
        ArrayList<DocumentLanguageModel> listOfLanguages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_DOCUMENT_LANGUAGE, null);
        if (cursor.moveToFirst())
        {
            do
            {
                DocumentLanguageModel language = new DocumentLanguageModel();
                language.setDocumentLanguageId(cursor.getInt(0) + "");
                language.setDocumentLanguageDesignation(cursor.getString(1));
                listOfLanguages.add(language);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfLanguages;
    }

    public ArrayList<FunctionModel> getFunction()
    {
        ArrayList<FunctionModel> listOfFunction = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_FUNCTION, null);
        if (cursor.moveToFirst())
        {
            do
            {
                FunctionModel function = new FunctionModel();
                function.setFunctionId(cursor.getInt(0) + "");
                function.setFunctionDesignation(cursor.getString(1));
                listOfFunction.add(function);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfFunction;
    }

    public String getFunctionById(int id)
    {
        FunctionModel function = new FunctionModel();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_FUNCTION + " WHERE " + KEY_FUNCTION_ID + "=" + id, null);
        if (cursor.moveToFirst())
        {
            do
            {

                function.setFunctionId(cursor.getInt(0) + "");
                function.setFunctionDesignation(cursor.getString(1));

            } while (cursor.moveToNext());
        }
        db.close();
        return function.getFunctionDesignation();
    }

    public ArrayList<LegalFormModel> getLegalForms()
    {
        ArrayList<LegalFormModel> listOfLegalForms = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_RECHTS_FORM, null);
        if (cursor.moveToFirst())
        {
            do
            {
                LegalFormModel legalForm = new LegalFormModel();

                legalForm.setRechtsFormId(cursor.getInt(0) + "");
                legalForm.setRechtsFormDesignation(cursor.getString(1));
                listOfLegalForms.add(legalForm);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfLegalForms;
    }

    public ArrayList<SalutationModel> getSalutation()
    {
        ArrayList<SalutationModel> listOfSalutations = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_SALUTATION, null);
        if (cursor.moveToFirst()) {
            do {
                SalutationModel salutation = new SalutationModel();

                salutation.setSalutationId(cursor.getInt(0) + "");
                salutation.setSalutationDesignation(cursor.getString(1));
                listOfSalutations.add(salutation);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfSalutations;
    }

    public ArrayList<ProjectStagesModel> getProjectStage()
    {
        ArrayList<ProjectStagesModel> listOfProjectStage = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PROJECT_STAGE, null);
        if (cursor.moveToFirst()) {
            do {
                ProjectStagesModel projectStage = new ProjectStagesModel();

                projectStage.setProjectStageId(cursor.getInt(0) + "");
                projectStage.setProjectStageDesignation(cursor.getString(1));
                listOfProjectStage.add(projectStage);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfProjectStage;
    }

    public ArrayList<ProjectAreaModel> getProjectArea()
    {
        ArrayList<ProjectAreaModel> listOfProjectArea = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PROJECT_AREA, null);
        if (cursor.moveToFirst()) {
            do {
                ProjectAreaModel projectArea = new ProjectAreaModel();

                projectArea.setProjectAreaId(cursor.getInt(0) + "");
                projectArea.setProjectAreaDesignation(cursor.getString(1));
                listOfProjectArea.add(projectArea);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfProjectArea;
    }

    public ArrayList<ProjectArtModel> getProjectArt()
    {
        ArrayList<ProjectArtModel> listOfProjectArt = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PROJECT_ART, null);
        if (cursor.moveToFirst()) {
            do {
                ProjectArtModel projectArt = new ProjectArtModel();

                projectArt.setProjectArtId(cursor.getInt(0) + "");
                projectArt.setProjectArtdesignation(cursor.getString(1));
                listOfProjectArt.add(projectArt);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfProjectArt;
    }

    public ArrayList<ProjectTypeModel> getProjectType()
    {
        ArrayList<ProjectTypeModel> listOfProjectType = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PROJECT_TYPE, null);
        if (cursor.moveToFirst()) {
            do {
                ProjectTypeModel projectArt = new ProjectTypeModel();

                projectArt.setProjectTypeId(cursor.getInt(0) + "");
                projectArt.setProjectTypeDesignation(cursor.getString(1));
                listOfProjectType.add(projectArt);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfProjectType;
    }

    public ArrayList<ProjectPhaseModel> getProjectPhase()
    {
        ArrayList<ProjectPhaseModel> listOfProjectPhase = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PROJECT_PHASE, null);
        if (cursor.moveToFirst()) {
            do {
                ProjectPhaseModel projectPhaseModel = new ProjectPhaseModel();

                projectPhaseModel.setProjectPhaseId(cursor.getInt(0) + "");
                projectPhaseModel.setProjectPhaseDesignation(cursor.getString(1));
                listOfProjectPhase.add(projectPhaseModel);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfProjectPhase;
    }

    public ArrayList<ProjectTradeModel> getProjectTrade()
    {
        ArrayList<ProjectTradeModel> listOfProjectTrade = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PROJECT_TRADE, null);
        if (cursor.moveToFirst()) {
            do {
                ProjectTradeModel projectTradeModel = new ProjectTradeModel();

                projectTradeModel.setProjectTradeId(cursor.getInt(0) + "");
                projectTradeModel.setProjectTradeDesignation(cursor.getString(1));
                listOfProjectTrade.add(projectTradeModel);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfProjectTrade;
    }

    public void deleteCustomerFromKontakt(String kontakt)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = KEY_CUSTOMER_KONTAKT + "=?";
        String[] whereArgs = new String[]{String.valueOf(kontakt)};

        //String deleteSQL = "DELETE FROM " + TABLE_CUSTOMER + " WHERE " + KEY_CUSTOMER_KONTAKT + " = '" + kontakt + "'";
        //db.execSQL(deleteSQL);
        db.delete(TABLE_CUSTOMER,whereClause,whereArgs);
        db.close();
    }

    public String getLoginSynchTime(Context context)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select " + KEY_TIMESTAMP + " from " + TABLE_COUNTRY, null);
        String timeStamp = "";
        if (cursor.moveToFirst())
        {
            timeStamp =  cursor.getString(0);
            DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                Date date = iso8601Format.parse(timeStamp);
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;
                timeStamp = android.text.format.DateUtils.formatDateTime
                        (context, when + TimeZone.getDefault().getOffset(when), flags);
            }
            catch (ParseException e)
            {
                Log.e("datetime dataabse", "Parsing ISO8601 datetime failed", e);
            }
        }
        db.close();
        return timeStamp;
    }

    public void deleteAllNews() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_BRANCH;
        String deleteWSQL = "DELETE FROM " + TABLE_DEVICE;
        String deleteWSQLPRICE = "DELETE FROM " + TABLE_PRICE_RENTAL;
        db.execSQL(deleteSQL);
        db.execSQL(deleteWSQL);
        db.execSQL(deleteWSQLPRICE);
        db.close();
    }

    public void deleteBranch()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_BRANCH;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteEmployee()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_EMPLOYEE;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteCustomer()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_CUSTOMER;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteActivityTypes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_ACTIVITY_TYPE;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteActivityTopics()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_ACTIVITY_TOPIC;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteLegalForms()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_RECHTS_FORM;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deletePriceRental()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_PRICE_RENTAL;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteDevice()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_DEVICE;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteCountry()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_COUNTRY;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteTableAtLogin()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_COUNTRY;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PRICING_OFFLINE_EQUIPMENT_HEIGHT;
        db.execSQL(deleteSQL);

        deleteSQL = "DELETE FROM " + TABLE_LADEFAHRZEUG;
        db.execSQL(deleteSQL);

        deleteSQL = "DELETE FROM " + TABLE_PRICING_OFFLINE_STANDARD_PRICE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_ACTIVITY_TOPIC;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_ACTIVITY_TYPE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_EMPLOYEE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_FEATURE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_DECISION_MAKER;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_DOCUMENT_LANGUAGE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_FUNCTION;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_SALUTATION;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_RECHTS_FORM;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PRICE_RENTAL;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_DEVICE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_BRANCH;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_DEVICE_TYPE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_BUILDING_PROJECT;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_ACCESS;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_KaNr;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_AREA;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_STAGE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_TRADE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_ART;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_TYPE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_PHASE;
        db.execSQL(deleteSQL);
        deleteSQL = "DELETE FROM " + TABLE_PROJECT_PHASE;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteKANR()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_KaNr;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteSalutation()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_SALUTATION;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteFunctions()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_FUNCTION;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteFeatures()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_FEATURE;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteDocumentLanguages()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_DOCUMENT_LANGUAGE;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteDecisionMakers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_DECISION_MAKER;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void addLostSale(Pricing3LostSaleData lostSale)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_LOST_SALE + " (branch,hgrp,deviceType,manufacturer,type" +
                ",md,rentalPrice,sb,hf,sp,hb,best,kundenNr) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindString(1,lostSale.getBranch());
            stmt.bindString(2,lostSale.gethGRP());
            stmt.bindString(3,lostSale.getDeviceType());
            stmt.bindString(4,lostSale.getManufacturer());
            stmt.bindString(5,lostSale.getType());
            stmt.bindString(6, String.valueOf(lostSale.getMd()));
            stmt.bindString(7, String.valueOf(lostSale.getRentalPrice()));
            stmt.bindString(8, String.valueOf(lostSale.getSb()));
            stmt.bindString(9,lostSale.getHfStatus());
            stmt.bindString(10,lostSale.getSpStatus());
            stmt.bindString(11, String.valueOf(lostSale.getHb()));
            stmt.bindString(12,lostSale.getBest());
            stmt.bindString(13,lostSale.getCustomerNo());
            stmt.execute();
            stmt.clearBindings();
            // long count =stmt.executeInsert();
            //Log.e(" prepare statment "," count variable for device type :  "+count);


            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }catch (Exception e){
            Log.e("addLostSale"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOST_SALE_BRANCH, lostSale.getBranch());
        values.put(KEY_LOST_SALE_HGRP, lostSale.gethGRP());
        values.put(KEY_LOST_SALE_DEVICE_TYPE, lostSale.getDeviceType());
        values.put(KEY_LOST_SALE_MANUFACTURER, lostSale.getManufacturer());
        values.put(KEY_LOST_SALE_TYPE, lostSale.getType());
        values.put(KEY_LOST_SALE_MD, lostSale.getMd());
        values.put(KEY_LOST_SALE_RENTAL_PRICE, lostSale.getRentalPrice());
        values.put(KEY_LOST_SALE_SB, lostSale.getSb());
        values.put(KEY_LOST_SALE_SP, lostSale.getSpStatus());
        values.put(KEY_LOST_SALE_HF, lostSale.getHfStatus());
        values.put(KEY_LOST_SALE_HB, lostSale.getHb());
        values.put(KEY_LOST_SALE_BEST, lostSale.getBest());
        values.put(KEY_LOST_CUSTOMER_KUNDEN_NR, lostSale.getCustomerNo());
        Log.e("Insert Lost Sale",values+"");
        db.insert(TABLE_LOST_SALE, null, values);
        db.close();*/
    }

    public void addPricingOfflineStandardPrice(ArrayList<PricingOfflineStandardPriceData> listOfPriceOfflineStandardPrice)
    {

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            for (int i = 0; i < listOfPriceOfflineStandardPrice.size(); i++)
            {
                ContentValues values = new ContentValues();
                PricingOfflineStandardPriceData priceOfflineStandardPrice = new PricingOfflineStandardPriceData();
                priceOfflineStandardPrice = listOfPriceOfflineStandardPrice.get(i);
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_BEZEINCHNUNG,
                        priceOfflineStandardPrice.getBezeichnung());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENGRUPPE,
                        priceOfflineStandardPrice.getHoehengruppe());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENHAUPTGRPPE,
                        priceOfflineStandardPrice.getHoehenhauptgruppe());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENHAUPTGRPPE_ID,
                        priceOfflineStandardPrice.getHoehenhauptgruppeID());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_LISTENPREIS,
                        priceOfflineStandardPrice.getListenpreis());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_MANDANT,
                        priceOfflineStandardPrice.getMandant());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_NIEDERLASSUNG,
                        priceOfflineStandardPrice.getNiederlassung());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_11_20,
                        priceOfflineStandardPrice.getTageM_11_20());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_1_2,
                        priceOfflineStandardPrice.getTageM_1_2());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_3_4,
                        priceOfflineStandardPrice.getTageM_3_4());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_M_5_10,
                        priceOfflineStandardPrice.getTageM_5_10());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_11_20,
                        priceOfflineStandardPrice.getTageR_11_20());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_1_2,
                        priceOfflineStandardPrice.getTageR_1_2());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_3_4,
                        priceOfflineStandardPrice.getTageR_3_4());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_TAGE_R_5_10,
                        priceOfflineStandardPrice.getTageR_5_10());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_AB_21_TAGE_M,
                        priceOfflineStandardPrice.getAb21TageM());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_AB_21_TAGE_R,
                        priceOfflineStandardPrice.getAb21TageR());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_BIS_8_STUNDEN_M,
                        priceOfflineStandardPrice.getBis8StundenM());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_BIS_8_STUNDEN_R,
                        priceOfflineStandardPrice.getBis8StundenR());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_UBER_8_STUNDEN_M,
                        priceOfflineStandardPrice.getUber8StundenM());
                values.put(KEY_PRICING_OFFLINE_STANDARD_PRICE_UBER_8_STUNDEN_R,
                        priceOfflineStandardPrice.getUber8StundenR());
                db.replace(TABLE_PRICING_OFFLINE_STANDARD_PRICE, null, values);
                //Log.e("standard price", id+"");
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            {
                db.endTransaction();
                db.close();

            }
        }*/
        // you can use INSERT only
        String sql = "INSERT OR REPLACE INTO " + TABLE_PRICING_OFFLINE_STANDARD_PRICE + " (Bezeichnung,Hoehengruppe,Hoehenhauptgruppe," +
                "HoehenhauptgruppeID,Listenpreis,Mandant,Niederlassung,TageM_11_20,TageM_1_2,TageM_3_4,TageM_5_10," +
                "TageR_11_20,TageR_1_2,TageR_3_4,TageR_5_10,ab21TageM,ab21TageR,bis8StundenM,bis8StundenR,uber8StundenM,uber8StundenR) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
        /*
         * According to the docs http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html
         * Writers should use beginTransactionNonExclusive() or beginTransactionWithListenerNonExclusive(SQLiteTransactionListener)
         * to start a transaction. Non-exclusive mode allows database file to be in readable by other threads executing queries.
         */
            db.beginTransactionNonExclusive();
            // db.beginTransaction();

            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfPriceOfflineStandardPrice.size(); x++){

                PricingOfflineStandardPriceData priceOfflineStandardPrice = new PricingOfflineStandardPriceData();
                priceOfflineStandardPrice = listOfPriceOfflineStandardPrice.get(x);

                stmt.bindString(1, priceOfflineStandardPrice.getBezeichnung());
                stmt.bindString(2, priceOfflineStandardPrice.getHoehengruppe());
                stmt.bindString(3, priceOfflineStandardPrice.getHoehenhauptgruppe());
                stmt.bindString(4, String.valueOf(priceOfflineStandardPrice.getHoehenhauptgruppeID()));
                stmt.bindString(5, String.valueOf(priceOfflineStandardPrice.getListenpreis()));
                stmt.bindString(6, String.valueOf(priceOfflineStandardPrice.getMandant()));
                stmt.bindString(7, String.valueOf(priceOfflineStandardPrice.getNiederlassung()));
                stmt.bindString(8, String.valueOf(priceOfflineStandardPrice.getTageM_11_20()));
                stmt.bindString(9, String.valueOf(priceOfflineStandardPrice.getTageM_1_2()));
                stmt.bindString(10, String.valueOf(priceOfflineStandardPrice.getTageM_3_4()));
                stmt.bindString(11, String.valueOf(priceOfflineStandardPrice.getTageM_5_10()));
                stmt.bindString(12, String.valueOf(priceOfflineStandardPrice.getTageR_11_20()));
                stmt.bindString(13, String.valueOf(priceOfflineStandardPrice.getTageR_1_2()));
                stmt.bindString(14, String.valueOf(priceOfflineStandardPrice.getTageR_3_4()));
                stmt.bindString(15, String.valueOf(priceOfflineStandardPrice.getTageR_5_10()));
                stmt.bindString(16, String.valueOf(priceOfflineStandardPrice.getAb21TageM()));
                stmt.bindString(17, String.valueOf(priceOfflineStandardPrice.getAb21TageR()));
                stmt.bindString(18, String.valueOf(priceOfflineStandardPrice.getBis8StundenM()));
                stmt.bindString(19, String.valueOf(priceOfflineStandardPrice.getBis8StundenR()));
                stmt.bindString(20, String.valueOf(priceOfflineStandardPrice.getUber8StundenM()));
                stmt.bindString(21, String.valueOf(priceOfflineStandardPrice.getUber8StundenR()));

                stmt.execute();
                stmt.clearBindings();

                //long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable :  "+count);


            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addPricingOfflineStandardPrice"," excepiton while insert: "+e.toString());
        }


    }


    public void addPricingOfflineEquipmentData(ArrayList<PricingOfflineEquipmentData> listOfPriceOfflineEquipment)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PRICING_OFFLINE_EQUIPMENT_HEIGHT + " (Ausstattung,Bezeichnung,Hoehengruppe," +
                "Hoehenhauptgruppe,bezeichnungName) VALUES ( ?,?,?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfPriceOfflineEquipment.size(); x++){

                PricingOfflineEquipmentData priceOfflineEquipmentdata = new PricingOfflineEquipmentData();
                priceOfflineEquipmentdata = listOfPriceOfflineEquipment.get(x);

                stmt.bindString(1, priceOfflineEquipmentdata.getAusstattung());
                stmt.bindString(2, priceOfflineEquipmentdata.getBezeichnung());
                stmt.bindString(3, priceOfflineEquipmentdata.getHoehengruppe());
                stmt.bindString(4, String.valueOf(priceOfflineEquipmentdata.getHoehenhauptgruppe()));
                stmt.bindString(5, priceOfflineEquipmentdata.getBezeichnung_1());

                stmt.execute();
                stmt.clearBindings();

               // long count =stmt.executeInsert();
               // Log.e(" prepare statment "," count variable for equipment :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addPricingOfflineEquipmentData"," excepiton while insert : "+e.toString());
        }
        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < listOfPriceOfflineEquipment.size(); i++)
            {
                ContentValues values = new ContentValues();
                PricingOfflineEquipmentData priceOfflineEquipment = new PricingOfflineEquipmentData();
                priceOfflineEquipment = listOfPriceOfflineEquipment.get(i);
                values.put(KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_AUSSTATTUNG, priceOfflineEquipment.getAusstattung());
                values.put(KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_BEZEICHNUNG, priceOfflineEquipment.getBezeichnung());
                values.put(KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_HOEHENGRPPE, priceOfflineEquipment.getHoehengruppe());
                values.put(KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_HOEHENHAUPTGRPPE,
                        priceOfflineEquipment.getHoehenhauptgruppe());
                values.put(KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_BEZEICHNUNG1, priceOfflineEquipment.getBezeichnung_1());
                db.replace(TABLE_PRICING_OFFLINE_EQUIPMENT_HEIGHT, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public ArrayList<Pricing1BranchData> getPricing1BranchData()
    {
        ArrayList<Pricing1BranchData> Branch_list = new ArrayList<Pricing1BranchData>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select designation,client from " + TABLE_BRANCH, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Pricing1BranchData branch = new Pricing1BranchData();

                branch.setDesignation(cursor.getString(0));
                branch.setClientId(cursor.getInt(1));
                Branch_list.add(branch);

            } while (cursor.moveToNext());
        }
        db.close();
        return Branch_list;
    }

    public ArrayList<Pricing1DeviceData> getPricing1DeviceData()
    {
        ArrayList<Pricing1DeviceData> Device_list = new ArrayList<Pricing1DeviceData>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select heightMainGroup,designation from " + TABLE_DEVICE, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Pricing1DeviceData device = new Pricing1DeviceData();
                device.setHeight_main_group(cursor.getInt(0));
                device.setDesignation(cursor.getString(1));
                Device_list.add(device);

            } while (cursor.moveToNext());
        }
        db.close();
        return Device_list;
    }

    public ArrayList<Pricing1PriceRentalData> getPricing1PriceRentalData()
    {
        ArrayList<Pricing1PriceRentalData> Price_Rental_list = new ArrayList<Pricing1PriceRentalData>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select designation,unit from " + TABLE_PRICE_RENTAL, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Pricing1PriceRentalData priceRental = new Pricing1PriceRentalData();
                priceRental.setDesignation(cursor.getString(0));
                priceRental.setUnit(cursor.getInt(1));
                Price_Rental_list.add(priceRental);

            } while (cursor.moveToNext());
        }
        db.close();
        return Price_Rental_list;
    }

    public ArrayList<Pricing3LostSaleData> getPricing3LostsaleData(String KundenNr)
    {
        ArrayList<Pricing3LostSaleData> LostSale_list = new ArrayList<Pricing3LostSaleData>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id,branch,hgrp,md,rentalPrice,sb,hf,sp,hb,best from " +
                TABLE_LOST_SALE + " where kundenNr=? ", new String[]{KundenNr});
        if (cursor.moveToFirst())
        {
            do
            {
                Pricing3LostSaleData lostSale = new Pricing3LostSaleData();
                lostSale.setId(cursor.getInt(0));
                lostSale.setBranch(cursor.getString(1));
                lostSale.sethGRP(cursor.getString(2));
                lostSale.setMd(cursor.getInt(3));
                Log.e("MD......"," " + cursor.getInt(3));
                lostSale.setRentalPrice(cursor.getDouble(4));
                lostSale.setSb(cursor.getDouble(5));
                lostSale.setHfStatus(cursor.getString(6));
                lostSale.setSpStatus(cursor.getString(7));
                lostSale.setHb(cursor.getDouble(8));
                lostSale.setBest(cursor.getString(9));
                LostSale_list.add(lostSale);
            } while (cursor.moveToNext());
        }
        db.close();
        return LostSale_list;
    }

    public ArrayList<Pricing1LevelGroupData> getPricing1LevelGroupData(int deviceTypeId)
    {
        ArrayList<Pricing1LevelGroupData> pricingLevelGroup_list = new ArrayList<Pricing1LevelGroupData>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select distinct Hoehengruppe, Bezeichnung from " +
                TABLE_PRICING_OFFLINE_STANDARD_PRICE + " where HoehenhauptgruppeID = ? order by Hoehengruppe ",
                new String[]{String.valueOf(deviceTypeId)});
        if (cursor.moveToFirst())
        {
            do
            {
                Pricing1LevelGroupData pricing1LevelGroup = new Pricing1LevelGroupData();
                pricing1LevelGroup.setHeightGroup(cursor.getString(0));
                pricing1LevelGroup.setDesignation(cursor.getString(1));
                pricingLevelGroup_list.add(pricing1LevelGroup);
            } while (cursor.moveToNext());
        }
        db.close();
        return pricingLevelGroup_list;
    }

    public ArrayList<Pricing1EquipmentData> getPricing1EquipmentData(String device)
    {
        ArrayList<Pricing1EquipmentData> pricingEquipment_list = new ArrayList<Pricing1EquipmentData>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT DISTINCT Ausstattung,bezeichnungName from " +
                TABLE_PRICING_OFFLINE_EQUIPMENT_HEIGHT + " WHERE " + KEY_PRICING_OFFLINE_EQUIPMENT_HEIGHT_HOEHENGRPPE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{device});
        if (cursor.moveToFirst())
        {
            do
            {
                Pricing1EquipmentData pricing1Equipment = new Pricing1EquipmentData();
                pricing1Equipment.setEquipment(cursor.getInt(0));
                pricing1Equipment.setDesignationId(cursor.getString(1));
                pricingEquipment_list.add(pricing1Equipment);

            } while (cursor.moveToNext());
        }
        db.close();
        return pricingEquipment_list;
    }

    public ArrayList<PricingOfflineStandardPriceData> getPricing2StandardPriceData
            (int Branch, final String DeviceType, int Einheit, String Assesories)
    {
        ArrayList<PricingOfflineStandardPriceData> pricing2Standard_list = new ArrayList<PricingOfflineStandardPriceData>();
        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT Hoehengruppe,Niederlassung,Listenpreis,TageM_1_2,TageR_1_2,TageM_3_4, TageR_3_4,TageM_5_10,TageR_5_10,TageM_11_20,TageR_11_20,ab21TageM,ab21TageR FROM "
//                + TABLE_PRICING_OFFLINE_STANDARD_PRICE + " WHERE " +
//                  KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENHAUPTGRPPE_ID + " = ? AND " +
//                  KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENGRUPPE + " = ? AND " + KEY_PRICING_OFFLINE_STANDARD_PRICE_MANDANT + " = ?";
        String query = "SELECT * FROM "+ TABLE_PRICING_OFFLINE_STANDARD_PRICE + " WHERE " +
                KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENGRUPPE + " = ? AND " + KEY_PRICING_OFFLINE_STANDARD_PRICE_MANDANT + " = ?";
        //KEY_PRICING_OFFLINE_STANDARD_PRICE_HOEHENHAUPTGRPPE_ID + " = ? AND " +
                Log.e("query", query);
        //Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(Branch),DeviceType,String.valueOf(Einheit)});
        Cursor cursor = db.rawQuery(query, new String[]{DeviceType,String.valueOf(Branch)});
        Log.e("cursor count", cursor.getCount() + "");
        if (cursor.moveToFirst())
        {
            do
            {
                PricingOfflineStandardPriceData pricing2StandardPrice = new PricingOfflineStandardPriceData();
                pricing2StandardPrice.setHoehengruppe(cursor.getString(cursor.getColumnIndex("Hoehengruppe")));
                pricing2StandardPrice.setHoehenhauptgruppeID(Integer.parseInt
                        (cursor.getString(cursor.getColumnIndex("HoehenhauptgruppeID"))));
                pricing2StandardPrice.setNiederlassung(cursor.getString(cursor.getColumnIndex("Niederlassung")));
                pricing2StandardPrice.setListenpreis(cursor.getDouble(cursor.getColumnIndex("Listenpreis")));
                pricing2StandardPrice.setTageM_1_2(cursor.getDouble(cursor.getColumnIndex("TageM_1_2")));
                pricing2StandardPrice.setTageR_1_2(cursor.getDouble(cursor.getColumnIndex("TageR_1_2")));
                pricing2StandardPrice.setTageM_3_4(cursor.getDouble(cursor.getColumnIndex("TageM_3_4")));
                pricing2StandardPrice.setTageR_3_4(cursor.getDouble(cursor.getColumnIndex("TageR_3_4")));
                pricing2StandardPrice.setTageM_5_10(cursor.getDouble(cursor.getColumnIndex("TageM_5_10")));
                pricing2StandardPrice.setTageR_5_10(cursor.getDouble(cursor.getColumnIndex("TageR_5_10")));
                pricing2StandardPrice.setTageM_11_20(cursor.getDouble(cursor.getColumnIndex("TageM_11_20")));
                pricing2StandardPrice.setTageR_11_20(cursor.getDouble(cursor.getColumnIndex("TageR_11_20")));
                pricing2StandardPrice.setAb21TageM(cursor.getDouble(cursor.getColumnIndex("ab21TageM")));
                pricing2StandardPrice.setAb21TageR(cursor.getDouble(cursor.getColumnIndex("ab21TageR")));

                pricing2Standard_list.add(pricing2StandardPrice);

            } while (cursor.moveToNext());
        }
        db.close();
        return pricing2Standard_list;

    }

    public void deleteLostSaleCart(int ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_LOST_SALE + " WHERE id = " + ids;
        db.execSQL(deleteSQL);
        db.close();
    }
    public void deleteLostSaleCart2() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_LOST_SALE;
        db.execSQL(deleteSQL);
        db.close();
    }
    public int getLostsaleCount()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_LOST_SALE, null);
        int count = c.getCount();
        db.close();
        return count;
    }


    public void deletePricingOfflineStandardPrice() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_PRICING_OFFLINE_STANDARD_PRICE;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deletePricingOfflineEqipment() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_PRICING_OFFLINE_EQUIPMENT_HEIGHT;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void addPhoto(GridImage image) {
        String sql = "INSERT OR REPLACE INTO " + TABLE_PHOTO + " (bvoId,name,flag,path) VALUES (?,?,?,?)";
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindString(1, String.valueOf(image.getBvoId()));
            if(image.getName() == null)
                stmt.bindString(2,"");
            else
                stmt.bindString(2,  image.getName());

            stmt.bindString(3, String.valueOf(image.getFlag()));
            stmt.bindString(4,image.getPath());

            stmt.execute();
            stmt.clearBindings();
            // long count =stmt.executeInsert();
            //Log.e(" prepare statment "," count variable for device type :  "+count);


            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }catch (Exception e){
            db.endTransaction();
            db.close();
            Log.e("addPhoto"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BVO_ID,image.getBvoId());
        if(image.getName() == null)
            values.put(KEY_PHOTO_NAME, "");
        else
            values.put(KEY_PHOTO_NAME, image.getName());
        values.put(KEY_PHOTO_FLAG, image.getFlag());
        values.put(KEY_PHOTO_PATH, image.getPath());
        db.insert(TABLE_PHOTO, null, values);
        db.close();*/
    }

    public void deletePhoto(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = KEY_PHOTO_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        //String deleteSQL = "DELETE FROM " + TABLE_PHOTO + " WHERE " + KEY_PHOTO_ID + "=" + id;
        //db.execSQL(deleteSQL);
        db.delete(TABLE_PHOTO,whereClause,whereArgs);
        db.close();
    }

    public void updatePhoto(String name,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHOTO_NAME,name);
        db.update(TABLE_PHOTO, values, KEY_PHOTO_ID + "=" + id, null);
        db.close();
    }

    public void updatePhotoByFlag(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHOTO_FLAG,1);
        db.update(TABLE_PHOTO, values, KEY_PHOTO_ID + "=" + id, null);
        db.close();
    }

    public void updatePhotoByPath(String path,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHOTO_PATH,path);
        db.update(TABLE_PHOTO, values, KEY_PHOTO_ID + "=" + id, null);
        db.close();
    }

    public void deletePhotosById(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = KEY_BVO_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        //String deleteSQL = "DELETE FROM " + TABLE_PHOTO + " WHERE " + KEY_BVO_ID + "=" + id;
        //db.execSQL(deleteSQL);
        db.delete(TABLE_PHOTO,whereClause,whereArgs);
        db.close();
    }

    public ArrayList<GridImage> getPhotos(int id) {
        ArrayList<GridImage> imageList = new ArrayList<GridImage>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PHOTO + " WHERE " + KEY_BVO_ID + "=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            do {
                GridImage image = new GridImage();
                image.setId(cursor.getInt(0));
                image.setBvoId(cursor.getInt(1));
                image.setName(cursor.getString(2));
                image.setFlag(cursor.getInt(3));
                image.setPath(cursor.getString(4));
                imageList.add(image);

            } while (cursor.moveToNext());
        }
        db.close();
        return imageList;
    }

    public ArrayList<GridImage> getPhotosByFlag(int id) {
        ArrayList<GridImage> imageList = new ArrayList<GridImage>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PHOTO + " WHERE " + KEY_BVO_ID + "=? AND " +
                KEY_PHOTO_FLAG + "=?", new String[]{String.valueOf(id),"0"});
        if (cursor.moveToFirst()) {
            do {
                GridImage image = new GridImage();
                image.setId(cursor.getInt(0));
                image.setBvoId(cursor.getInt(1));
                image.setName(cursor.getString(2));
                image.setFlag(cursor.getInt(3));
                image.setPath(cursor.getString(4));
                imageList.add(image);

            } while (cursor.moveToNext());
        }
        db.close();
        return imageList;
    }

    public void deleteDeviceById(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = KEY_BVO_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        //String deleteSQL = "DELETE FROM " + TABLE_DEVICE_DATA + " WHERE " + KEY_BVO_ID + "=" + id;
        //db.execSQL(deleteSQL);
        db.delete(TABLE_DEVICE_DATA,whereClause,whereArgs);
        db.close();
    }

    public void deleteAlternativeDeviceById(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_DEVICE_DATA + " WHERE " + KEY_ID + "=" + id;
        db.execSQL(deleteSQL);
        db.close();
    }

    public void deleteAlterNateDeviceFromParentId(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = KEY_PARENT_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        //String deleteSQL = "DELETE FROM " + TABLE_DEVICE_DATA + " WHERE " + KEY_PARENT_ID + "=" + id;
        //db.execSQL(deleteSQL);
        db.delete(TABLE_DEVICE_DATA,whereClause,whereArgs);
        db.close();
    }

    public int getParentDevice(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DEVICE_DATA + " WHERE " + KEY_PARENT_ID + "=? AND " +
                KEY_BVO_ID + "=?", new String[]{"0",String.valueOf(id)});
        Log.e("count", c.getCount() + "");
        int count = c.getCount();
        db.close();
        return count;
    }

    public void addDevice(SiteInspectionDeviceDataModel deviceDataModel)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_DEVICE_DATA + " (bvoId,parentId,Alternativ," +
                "Position,diesel,Elektro,Allrad,Kette_Gummi,Reifen_markierungsarm,powerLift,Stromerzeuger,Sonstiges," +
                "SonstigesText,Geraetegruppe,Hoehengruppe,Geraetetyp,Anzahl,Arbeitshoehe," +
                "SeitlicheReichweite,Laenge,Breite,Hoehe,Gewicht,Korbbelastung,Korbarmlaenge,Hauptgeraet) VALUES (?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);
            stmt.bindString(1, String.valueOf(deviceDataModel.getBvoId()));
            stmt.bindString(2, String.valueOf(deviceDataModel.getParentId()));
            stmt.bindString(3, String.valueOf(deviceDataModel.getAlternativ()));
            stmt.bindString(4, String.valueOf(deviceDataModel.getPosition()));
            stmt.bindString(5, String.valueOf(deviceDataModel.getDiesel()));
            stmt.bindString(6, String.valueOf(deviceDataModel.getElektro()));
            stmt.bindString(7, String.valueOf(deviceDataModel.getAllrad()));
            stmt.bindString(8, String.valueOf(deviceDataModel.getKette_Gummi()));
            stmt.bindString(9, String.valueOf(deviceDataModel.getReifen_markierungsarm()));
            stmt.bindString(10, String.valueOf(deviceDataModel.getPowerlift()));
            stmt.bindString(11, String.valueOf(deviceDataModel.getStromerzeuger()));
            stmt.bindString(12, String.valueOf(deviceDataModel.getSonstiges()));
            if(TextUtils.isEmpty(deviceDataModel.getSonstigesText())) {
                stmt.bindString(13,"");
            }else {
                stmt.bindString(13, deviceDataModel.getSonstigesText());
            }
            if(TextUtils.isEmpty(deviceDataModel.getGeraetegruppe())) {
                stmt.bindString(14, "");
            }else {
                stmt.bindString(14, deviceDataModel.getGeraetegruppe());
            }
            if(TextUtils.isEmpty(deviceDataModel.getHoehengruppe())) {
                stmt.bindString(15, "");
            }else {
                stmt.bindString(15, deviceDataModel.getHoehengruppe());
            }
            if(TextUtils.isEmpty(deviceDataModel.getGeraetetyp())) {
                stmt.bindString(16, "");
            }else {
                stmt.bindString(16, deviceDataModel.getGeraetetyp());
            }
            stmt.bindString(17, String.valueOf(deviceDataModel.getAnzahl()));
            if(TextUtils.isEmpty(deviceDataModel.getArbeitshoehe())) {
                stmt.bindString(18, "");
            }else {
                stmt.bindString(18, deviceDataModel.getArbeitshoehe());
            }
            if(TextUtils.isEmpty(deviceDataModel.getSeitlicheReichweite())) {
                stmt.bindString(19, "");
            }else {
                stmt.bindString(19, deviceDataModel.getSeitlicheReichweite());
            }
            if(TextUtils.isEmpty(deviceDataModel.getLaenge())) {
                stmt.bindString(20, "");
            }else {
                stmt.bindString(20, deviceDataModel.getLaenge());
            }
            if(TextUtils.isEmpty(deviceDataModel.getBreite())) {
                stmt.bindString(21,"");
            }else {
                stmt.bindString(21, deviceDataModel.getBreite());
            }
            if(TextUtils.isEmpty(deviceDataModel.getHoehe())) {
                stmt.bindString(22, "");
            }else {
                stmt.bindString(22, deviceDataModel.getHoehe());
            }
            stmt.bindString(23, String.valueOf(deviceDataModel.getGewicht()));
            if(TextUtils.isEmpty(deviceDataModel.getKorbbelastung())) {
                stmt.bindString(24, "");
            }else{
                stmt.bindString(24, deviceDataModel.getKorbbelastung());
            }
            if(TextUtils.isEmpty(deviceDataModel.getKorbarmlaenge())) {
                stmt.bindString(25, "");
            }else {
                stmt.bindString(25, deviceDataModel.getKorbarmlaenge());
            }
            if(TextUtils.isEmpty(deviceDataModel.getHauptgeraet())) {
                stmt.bindString(26, "");
            }else {
                stmt.bindString(26, deviceDataModel.getHauptgeraet());
            }
            stmt.execute();
            stmt.clearBindings();
                // long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }catch (Exception e){
            db.endTransaction();
            db.close();
            Log.e("addDevice"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BVO_ID,deviceDataModel.getBvoId());
        values.put(KEY_PARENT_ID,deviceDataModel.getParentId());
        values.put(KEY_ALTERNATIVE,deviceDataModel.getAlternativ());
        values.put(KEY_POSITION,deviceDataModel.getPosition());
        values.put(KEY_DIESEL,deviceDataModel.getDiesel());
        values.put(KEY_ELEKTRO,deviceDataModel.getElektro());
        values.put(KEY_ALLRAD,deviceDataModel.getAllrad());
        values.put(KEY_KETTE_GUMMI,deviceDataModel.getKette_Gummi());
        values.put(KEY_REIFEN,deviceDataModel.getReifen_markierungsarm());
        values.put(KEY_POWER_LIFT,deviceDataModel.getPowerlift());
        values.put(KEY_STROMERZEUGER,deviceDataModel.getStromerzeuger());
        values.put(KEY_SONSTIGES,deviceDataModel.getSonstiges());
        values.put(KEY_SONSTIGES_TEXT,deviceDataModel.getSonstigesText());
        values.put(KEY_GERAETEGRUPPE,deviceDataModel.getGeraetegruppe());
        values.put(KEY_HOHENGRUPPE,deviceDataModel.getHoehengruppe());
        values.put(KEY_GERAETTYP,deviceDataModel.getGeraetetyp());
        values.put(KEY_ANZAHL,deviceDataModel.getAnzahl());
        values.put(KEY_ARBEITSHOEHE,deviceDataModel.getArbeitshoehe());
        values.put(KEY_SEITLICHE,deviceDataModel.getSeitlicheReichweite());
        values.put(KEY_LAENGE,deviceDataModel.getLaenge());
        values.put(KEY_BREITE,deviceDataModel.getBreite());
        values.put(KEY_HOEHE,deviceDataModel.getHoehe());
        values.put(KEY_GEWICHT,deviceDataModel.getGewicht());
        values.put(KEY_KORBELASTUNG,deviceDataModel.getKorbbelastung());
        values.put(KEY_KORBARMLAENGE,deviceDataModel.getKorbarmlaenge());
        values.put(KEY_HAUPTGERAT, deviceDataModel.getHauptgeraet());
        long id = db.insert(TABLE_DEVICE_DATA, null, values);
        Log.e("databser id", id + " parent id" + deviceDataModel.getParentId());
        db.close();*/
    }

    public void updateDeviceByParentId(int id,int parentId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PARENT_ID,parentId);
        db.update(TABLE_DEVICE_DATA, values, KEY_DEVICE_DATA_ID + "=" + id, null);
        db.close();
    }

    public int getDeviceDataId(int bvoId,int pos)
    {
        int id=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_DEVICE_DATA_ID + " FROM " + TABLE_DEVICE_DATA + " WHERE " +
                KEY_BVO_ID + "=? AND " + KEY_POSITION + "=? AND " + KEY_PARENT_ID + "=?",new String[]{String.valueOf(bvoId),String.valueOf(pos),"0"});
        if (cursor.moveToFirst())
        {
            do
            {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        db.close();
        return id;
    }

    public void updateDevice(SiteInspectionDeviceDataModel deviceDataModel,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ALTERNATIVE,deviceDataModel.getAlternativ());
        values.put(KEY_POSITION,deviceDataModel.getPosition());
        values.put(KEY_DIESEL,deviceDataModel.getDiesel());
        values.put(KEY_ELEKTRO,deviceDataModel.getElektro());
        values.put(KEY_ALLRAD,deviceDataModel.getAllrad());
        values.put(KEY_KETTE_GUMMI, deviceDataModel.getKette_Gummi());
        values.put(KEY_REIFEN,deviceDataModel.getReifen_markierungsarm());
        values.put(KEY_POWER_LIFT,deviceDataModel.getPowerlift());
        values.put(KEY_STROMERZEUGER,deviceDataModel.getStromerzeuger());
        values.put(KEY_SONSTIGES,deviceDataModel.getSonstiges());
        values.put(KEY_SONSTIGES_TEXT,deviceDataModel.getSonstigesText());
        values.put(KEY_GERAETEGRUPPE,deviceDataModel.getGeraetegruppe());
        values.put(KEY_HOHENGRUPPE, deviceDataModel.getHoehengruppe());
        values.put(KEY_GERAETTYP, deviceDataModel.getGeraetetyp());
        values.put(KEY_ANZAHL,deviceDataModel.getAnzahl());
        values.put(KEY_ARBEITSHOEHE,deviceDataModel.getArbeitshoehe());
        values.put(KEY_SEITLICHE,deviceDataModel.getSeitlicheReichweite());
        values.put(KEY_LAENGE,deviceDataModel.getLaenge());
        values.put(KEY_BREITE,deviceDataModel.getBreite());
        values.put(KEY_HOEHE,deviceDataModel.getHoehe());
        values.put(KEY_GEWICHT,deviceDataModel.getGewicht());
        values.put(KEY_KORBELASTUNG,deviceDataModel.getKorbbelastung());
        values.put(KEY_KORBARMLAENGE,deviceDataModel.getKorbarmlaenge());
        values.put(KEY_HAUPTGERAT,deviceDataModel.getHauptgeraet());
        db.update(TABLE_DEVICE_DATA, values, KEY_DEVICE_DATA_ID + "=" + id, null);
        db.close();
    }



    public int getDeviceDataId()
    {
        int id=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_DEVICE_DATA_ID + " FROM " + TABLE_DEVICE_DATA
                + " ORDER BY " + KEY_DEVICE_DATA_ID + " DESC LIMIT 1",null);
        if (cursor.moveToFirst())
        {
            do
            {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        db.close();
        return id;
    }

    public ArrayList<SiteInspectionDeviceDataModel> getDeviceByID(int id)
    {
        ArrayList<SiteInspectionDeviceDataModel> listOfdeviceData = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DEVICE_DATA + " WHERE " + KEY_BVO_ID + "=?",new String[]{String.valueOf(id)});
        if (cursor.moveToFirst())
        {
            do
            {
                SiteInspectionDeviceDataModel deviceDataModel = new SiteInspectionDeviceDataModel();
                deviceDataModel.setId(cursor.getInt(0));
                deviceDataModel.setBvoId(cursor.getInt(1));
                deviceDataModel.setParentId(cursor.getInt(2));
                deviceDataModel.setAlternativ(cursor.getInt(3));
                deviceDataModel.setPosition(cursor.getInt(4));
                deviceDataModel.setDiesel(cursor.getInt(5));
                deviceDataModel.setElektro(cursor.getInt(6));
                deviceDataModel.setAllrad(cursor.getInt(7));
                deviceDataModel.setKette_Gummi(cursor.getInt(8));
                deviceDataModel.setReifen_markierungsarm(cursor.getInt(9));
                deviceDataModel.setPowerlift(cursor.getInt(10));
                deviceDataModel.setStromerzeuger(cursor.getInt(11));
                deviceDataModel.setSonstiges(cursor.getInt(12));
                deviceDataModel.setSonstigesText(cursor.getString(13));
                deviceDataModel.setGeraetegruppe(cursor.getString(14));
                deviceDataModel.setHoehengruppe(cursor.getString(15));
                deviceDataModel.setGeraetetyp(cursor.getString(16));
                deviceDataModel.setAnzahl(cursor.getInt(17));
                deviceDataModel.setArbeitshoehe(cursor.getString(18));
                deviceDataModel.setSeitlicheReichweite(cursor.getString(19));
                deviceDataModel.setLaenge(cursor.getString(20));
                deviceDataModel.setBreite(cursor.getString(21));
                deviceDataModel.setHoehe(cursor.getString(22));
                deviceDataModel.setGewicht(cursor.getInt(23));
                deviceDataModel.setKorbbelastung(cursor.getString(24));
                deviceDataModel.setKorbarmlaenge(cursor.getString(25));
                deviceDataModel.setHauptgeraet(cursor.getString(26));
                listOfdeviceData.add(deviceDataModel);
            } while (cursor.moveToNext());
        }
        db.close();
        return listOfdeviceData;
    }

    public void addBuildingProject(ArrayList<SiteInspectionBuildingProjectModel> listOfSiteInspectionBuildingProject)
    {

        String sql = "INSERT OR REPLACE INTO " + TABLE_BUILDING_PROJECT + " (Bezeichnung,Bauvorhaben) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfSiteInspectionBuildingProject.size(); x++){

                SiteInspectionBuildingProjectModel siteInspectionBuildingProjectModel = new SiteInspectionBuildingProjectModel();
                siteInspectionBuildingProjectModel = listOfSiteInspectionBuildingProject.get(x);

                stmt.bindString(1, siteInspectionBuildingProjectModel.getBezeichnung());
                stmt.bindString(2, siteInspectionBuildingProjectModel.getBauvorhaben());

                stmt.execute();
                stmt.clearBindings();
               // long count =stmt.executeInsert();
               // Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addBuildingProject"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < listOfSiteInspectionBuildingProject.size(); i++) {

                ContentValues values = new ContentValues();
                SiteInspectionBuildingProjectModel siteInspectionBuildingProjectModel =
                        new SiteInspectionBuildingProjectModel();
                siteInspectionBuildingProjectModel = listOfSiteInspectionBuildingProject.get(i);

                values.put(KEY_BUILDING_PROJECT_BAUVORHABEN, siteInspectionBuildingProjectModel.getBauvorhaben());
                values.put(KEY_BUILDING_PROJECT_BEZEICHUNG, siteInspectionBuildingProjectModel.getBezeichnung());

                db.replace(TABLE_BUILDING_PROJECT, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/


    }

    public void addAccess(ArrayList<SiteInspectionAccessModel> listOfSiteInspectionAccess)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_ACCESS + " (Bezeichnung,Zugang) VALUES ( ?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfSiteInspectionAccess.size(); x++){

                SiteInspectionAccessModel siteInspectionAccessModel = new SiteInspectionAccessModel();
                siteInspectionAccessModel = listOfSiteInspectionAccess.get(x);

                stmt.bindString(1, siteInspectionAccessModel.getBezeichnung());
                stmt.bindString(2, siteInspectionAccessModel.getZugang());
                stmt.execute();
                stmt.clearBindings();
               // long count =stmt.executeInsert();
               // Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addAccess"," excepiton while insert : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < listOfSiteInspectionAccess.size(); i++) {

                ContentValues values = new ContentValues();
                SiteInspectionAccessModel siteInspectionAccessModel = new SiteInspectionAccessModel();
                siteInspectionAccessModel = listOfSiteInspectionAccess.get(i);

                values.put(KEY_ACCESS_ZUGANG,siteInspectionAccessModel.getZugang());
                values.put(KEY_ACCESS_BEZEICHUNG, siteInspectionAccessModel.getBezeichnung());

                db.replace(TABLE_ACCESS, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();

        }*/


    }

    public void addSiteInspection(SiteInspectionModel siteInspectionModel)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_SITE_INSPECTION + " (uploadId,flag,date," +
                "SiteInspectionDetail,AddtinalEnvironment,OperationalDataPermits,AdditionalMobileWindPower," +
                "SelectMail) VALUES (?,?,?,?,?,?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindString(1, String.valueOf(0));
            stmt.bindString(2, String.valueOf(0));
            stmt.bindString(3, "");
            stmt.bindString(4, new Gson().toJson(siteInspectionModel.getSiteInspectionNewModel()));
            stmt.bindString(5, new Gson().toJson(siteInspectionModel.getSiteInspectionOperationalEnvironmentModel()));
            stmt.bindString(6, new Gson().toJson(siteInspectionModel.getSiteInspectionOperationalDataPermitsModel()));
            stmt.bindString(7, new Gson().toJson(siteInspectionModel.getSiteInspectionAdditionalMobileWindPowerModel()));
            stmt.bindString(8, new Gson().toJson(siteInspectionModel.getListOfEmailAddress()));

            stmt.execute();
            stmt.clearBindings();
                // long count =stmt.executeInsert();
                //Log.e(" prepare statment "," count variable for device type :  "+count);

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e("addSiteInspection"," excepiton while insert : "+e.toString());
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_UPLOAD_ID,0);
        values.put(KEY_FLAG,0);
        values.put(KEY_DATE,"");
        values.put(KEY_SITE_INSPECTION_DETAIL,new Gson().toJson(siteInspectionModel.getSiteInspectionNewModel()));
        values.put(KEY_OPERATIONAL_ENVIRONMENT_DETAIL,
                new Gson().toJson(siteInspectionModel.getSiteInspectionOperationalEnvironmentModel()));
        values.put(KEY_OPERATIONAL_DATA_PERMITS,
                new Gson().toJson(siteInspectionModel.getSiteInspectionOperationalDataPermitsModel()));
        values.put(KEY_ADDITIONAL_MOBILE_WIND_POWER,
                new Gson().toJson(siteInspectionModel.getSiteInspectionAdditionalMobileWindPowerModel()));
        values.put(KEY_SELECT_MAIL,new Gson().toJson(siteInspectionModel.getListOfEmailAddress()));
        db.insert(TABLE_SITE_INSPECTION, null, values);
        db.close();
    }

    public int getSiteInspectionId()
    {
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_SITE_INSPECTION_ID + " FROM " + TABLE_SITE_INSPECTION
                + " ORDER BY " + KEY_SITE_INSPECTION_ID + " DESC LIMIT 1", null);
        if (cursor.moveToFirst())
        {
            do
            {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        db.close();
        return id;
    }

    public void updateSiteInspectionDate(int id)
    {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) +1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE,c.get(Calendar.DAY_OF_MONTH) + "-" + month + "-" + c.get(Calendar.YEAR));
        db.update(TABLE_SITE_INSPECTION, values, KEY_SITE_INSPECTION_ID + "=" + id, null);
        db.close();
    }

    public void updateSiteInspectionEmailBody(int id, String emailBodyContent)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL_BODY_CONTENT, emailBodyContent);
        db.update(TABLE_SITE_INSPECTION, values, KEY_SITE_INSPECTION_ID + "=" + id, null);
        db.close();
    }

    public void updateSiteInspectionUpload(int id,String uploadId,int flag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_UPLOAD_ID,uploadId);
        values.put(KEY_FLAG,flag);
        db.update(TABLE_SITE_INSPECTION, values, KEY_SITE_INSPECTION_ID + "=" + id, null);
        db.close();
    }


    public void updateSiteInspectionFlag(int id,int flag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FLAG,flag);
        db.update(TABLE_SITE_INSPECTION,values,KEY_SITE_INSPECTION_ID + "=" + id,null);
        db.close();
    }

    public void updateSiteInspection(SiteInspectionModel siteInspectionModel,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SITE_INSPECTION_DETAIL,new Gson().toJson(siteInspectionModel.getSiteInspectionNewModel()));
        db.update(TABLE_SITE_INSPECTION, values, KEY_SITE_INSPECTION_ID + "=" + id, null);
        db.close();
    }

    public void updateOperationalEnvironment(SiteInspectionOperationalEnvironmentModel
                siteInspectionOperationalEnvironmentModel,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OPERATIONAL_ENVIRONMENT_DETAIL, new Gson().toJson(siteInspectionOperationalEnvironmentModel));
        db.update(TABLE_SITE_INSPECTION, values, KEY_SITE_INSPECTION_ID + "=" + id, null);
        db.close();
    }

    public void updateOperationalDataPermits(SiteInspectionOperationalDataPermitsModel
                siteInspectionOperationalDataPermitsModel,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OPERATIONAL_DATA_PERMITS, new Gson().toJson(siteInspectionOperationalDataPermitsModel));
        db.update(TABLE_SITE_INSPECTION, values, KEY_SITE_INSPECTION_ID + "=" + id, null);
        db.close();
    }

    public void updateAdditionalMobileWindPower(SiteInspectionAdditionalMobileWindPowerModel
                siteInspectionAdditionalMobileWindPowerModel,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ADDITIONAL_MOBILE_WIND_POWER,new Gson().toJson(siteInspectionAdditionalMobileWindPowerModel));
        db.update(TABLE_SITE_INSPECTION, values, KEY_SITE_INSPECTION_ID + "=" + id, null);
        db.close();
    }

    public void updateSelectEmail(ArrayList<String> listOfEmailAddress,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SELECT_MAIL,new Gson().toJson(listOfEmailAddress));
        db.update(TABLE_SITE_INSPECTION, values, KEY_SITE_INSPECTION_ID + "=" + id,null);
        db.close();
    }

    public SiteInspectionModel getSiteInspection(int id)
    {
        SiteInspectionModel siteInspectionModel = new SiteInspectionModel();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SITE_INSPECTION + " WHERE " +
                KEY_SITE_INSPECTION_ID + "=?",new String[]{String.valueOf(id)});
        if (cursor.moveToFirst())
        {
            do
            {
                siteInspectionModel.setId(cursor.getInt(0));
                siteInspectionModel.setUploadId(cursor.getString(1));
                siteInspectionModel.setFlag(cursor.getInt(2));
                Log.e("flag", cursor.getInt(2) + "");
                siteInspectionModel.setDate(cursor.getString(3));
                String siteInspectionDetail = cursor.getString(4);
                siteInspectionModel.setSiteInspectionNewModel
                        (new Gson().fromJson(siteInspectionDetail, SiteInspectionNewModel.class));
                String siteInspectionOperationalEnvironment = cursor.getString(5);
                siteInspectionModel.setSiteInspectionOperationalEnvironmentModel
                        (new Gson().fromJson(siteInspectionOperationalEnvironment, SiteInspectionOperationalEnvironmentModel.class));
                String siteInspectionOperationalDataPermits = cursor.getString(6);
                siteInspectionModel.setSiteInspectionOperationalDataPermitsModel
                        (new Gson().fromJson(siteInspectionOperationalDataPermits,SiteInspectionOperationalDataPermitsModel.class));
                String siteInspectionAdditionalMobileWindPower = cursor.getString(7);
                siteInspectionModel.setSiteInspectionAdditionalMobileWindPowerModel
                        (new Gson().fromJson(siteInspectionAdditionalMobileWindPower,SiteInspectionAdditionalMobileWindPowerModel.class));
                String siteInspectionSelectEmail = cursor.getString(8);
                ArrayList<String> listOfemailAddress = new ArrayList<>();
                SiteInspectionModel.getEmailAddressFromString(siteInspectionSelectEmail, listOfemailAddress);
                siteInspectionModel.setListOfEmailAddress(listOfemailAddress);
                String siteInspectionEmailBody = cursor.getString(cursor.getColumnIndex(KEY_EMAIL_BODY_CONTENT));
                siteInspectionModel.setEmailBodyContent(siteInspectionEmailBody);
            } while (cursor.moveToNext());
        }
        db.close();
        return siteInspectionModel;
    }

    public ArrayList<SiteInspectionModel> getSiteInspectionList()
    {
        ArrayList<SiteInspectionModel> listOfSiteInspection = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SITE_INSPECTION + " WHERE " +
                KEY_FLAG + "=2 ORDER BY " + KEY_SITE_INSPECTION_ID + " DESC" ,null);
        if (cursor.moveToFirst())
        {
            do
            {
                SiteInspectionModel siteInspectionModel = new SiteInspectionModel();
                siteInspectionModel.setId(cursor.getInt(0));
                siteInspectionModel.setUploadId(cursor.getString(1));
                siteInspectionModel.setFlag(cursor.getInt(2));
                siteInspectionModel.setDate(cursor.getString(3));
                String siteInspectionDetail = cursor.getString(4);
                siteInspectionModel.setSiteInspectionNewModel
                        (new Gson().fromJson(siteInspectionDetail, SiteInspectionNewModel.class));
                String siteInspectionOperationalEnvironment = cursor.getString(5);
                siteInspectionModel.setSiteInspectionOperationalEnvironmentModel(new Gson().fromJson
                        (siteInspectionOperationalEnvironment, SiteInspectionOperationalEnvironmentModel.class));
                String siteInspectionOperationalDataPermits = cursor.getString(6);
                siteInspectionModel.setSiteInspectionOperationalDataPermitsModel(new Gson().fromJson
                        (siteInspectionOperationalDataPermits,SiteInspectionOperationalDataPermitsModel.class));
                String siteInspectionAdditionalMobileWindPower = cursor.getString(7);
                siteInspectionModel.setSiteInspectionAdditionalMobileWindPowerModel(new Gson().fromJson
                        (siteInspectionAdditionalMobileWindPower,SiteInspectionAdditionalMobileWindPowerModel.class));
                String siteInspectionSelectEmail = cursor.getString(8);
                ArrayList<String> listOfemailAddress = new ArrayList<>();
                SiteInspectionModel.getEmailAddressFromString(siteInspectionSelectEmail, listOfemailAddress);
                siteInspectionModel.setListOfEmailAddress(listOfemailAddress);
                listOfSiteInspection.add(siteInspectionModel);
            } while (cursor.moveToNext());
        }
        db.close();
        return listOfSiteInspection;
    }

    public void deleteSiteInspection(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = KEY_SITE_INSPECTION_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        //String deleteSQL = "DELETE FROM " + TABLE_SITE_INSPECTION + " WHERE " + KEY_SITE_INSPECTION_ID + "=" + id;
        //db.execSQL(deleteSQL);
        db.delete(TABLE_SITE_INSPECTION,whereClause,whereArgs);
        db.close();
    }

    public ArrayList<SiteInspectionDeviceTypeModel> getDeviceType(String hightScale)
    {
        ArrayList<SiteInspectionDeviceTypeModel> listOfSiteInspectionDeviceType = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * from " + TABLE_DEVICE_TYPE + " WHERE " + KEY_DEVICE_TYPE_HOHENGRUPPE + "=" + "'" +
                hightScale + "'";

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                SiteInspectionDeviceTypeModel deviceType = new SiteInspectionDeviceTypeModel();
                deviceType.setGeraeettypID(cursor.getString(0));
                deviceType.setBezeichnung(cursor.getString(1));
                deviceType.setHoehengruppe(cursor.getString(2));
                deviceType.setArbeitshoehe(cursor.getString(3));
                listOfSiteInspectionDeviceType.add(deviceType);
            } while (cursor.moveToNext());
        }
        db.close();
        return listOfSiteInspectionDeviceType;
    }

    public ArrayList<SiteInspectionBuildingProjectModel> getBuildingProject()
    {
        ArrayList<SiteInspectionBuildingProjectModel> listofBuidlignProject = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * from " + TABLE_BUILDING_PROJECT;

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {

                SiteInspectionBuildingProjectModel buidlignProject = new SiteInspectionBuildingProjectModel();
                buidlignProject.setBauvorhaben(cursor.getString(0));
                buidlignProject.setBezeichnung(cursor.getString(1));
                listofBuidlignProject.add(buidlignProject);
            } while (cursor.moveToNext());
        }
        db.close();
        return listofBuidlignProject;
    }

    public ArrayList<PriceStaffelModel> getPriceStaffel()
    {
        ArrayList<PriceStaffelModel> listOfPriceStaffel = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * from " + TABLE_STAFFEL;

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {

                PriceStaffelModel object = new PriceStaffelModel();
                object = new Gson().fromJson(cursor.getString(2), PriceStaffelModel.class);
                //object.setStaffel(obj);
                //access.setBezeichnung(cursor.getString(1));
                listOfPriceStaffel.add(object);
            } while (cursor.moveToNext());
        }
        db.close();
        return listOfPriceStaffel;
    }

    public PriceStaffelModel getStaffelObjectFromStaffel(int staffel)
    {
        PriceStaffelModel object = new PriceStaffelModel();
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "SELECT * from " + TABLE_STAFFEL + " where " + KEY_STAFFEL + "=" + staffel;

        //Log.e(" @@@@@@ ", " queary variable in staffel : "+query);
        Cursor cursor = db.rawQuery("SELECT * from " + TABLE_STAFFEL + " where " + KEY_STAFFEL + "=?",
                new String[]{String.valueOf(staffel)});

        if (cursor.moveToFirst())
        {
            do {
                object = new Gson().fromJson(cursor.getString(2), PriceStaffelModel.class);
            } while (cursor.moveToNext());
        }
        db.close();
        return object;
    }

    public ArrayList<SiteInspectionAccessModel> getAccess()
    {
        ArrayList<SiteInspectionAccessModel> listOfAccess = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * from " + TABLE_ACCESS;

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {

                SiteInspectionAccessModel access = new SiteInspectionAccessModel();
                access.setZugang(cursor.getString(0));
                access.setBezeichnung(cursor.getString(1));
                listOfAccess.add(access);
            } while (cursor.moveToNext());
        }
        db.close();
        return listOfAccess;
    }

    public boolean checkIfPhotoUploadRemaining()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SITE_INSPECTION + " WHERE " + KEY_FLAG + "=?" ,new String[]{"3"});

        if(cursor.getCount() > 0)
        {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public ArrayList<SiteInspectionModel> getSiteInspectionListOutbox()
    {
        ArrayList<SiteInspectionModel> listOfSiteInspection = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SITE_INSPECTION + " WHERE " + KEY_FLAG + "=1 ORDER BY " +
                KEY_SITE_INSPECTION_ID + " DESC LIMIT 10"  ,null);
        if (cursor.moveToFirst())
        {
            do
            {
                SiteInspectionModel siteInspectionModel = new SiteInspectionModel();
                siteInspectionModel.setId(cursor.getInt(0));
                siteInspectionModel.setUploadId(cursor.getString(1));
                siteInspectionModel.setFlag(cursor.getInt(2));
                siteInspectionModel.setDate(cursor.getString(3));
                String siteInspectionDetail = cursor.getString(4);
                siteInspectionModel.setSiteInspectionNewModel(new Gson().fromJson
                        (siteInspectionDetail, SiteInspectionNewModel.class));
                String siteInspectionOperationalEnvironment = cursor.getString(5);
                siteInspectionModel.setSiteInspectionOperationalEnvironmentModel(new Gson().fromJson
                        (siteInspectionOperationalEnvironment, SiteInspectionOperationalEnvironmentModel.class));
                String siteInspectionOperationalDataPermits = cursor.getString(6);
                siteInspectionModel.setSiteInspectionOperationalDataPermitsModel(new Gson().fromJson
                        (siteInspectionOperationalDataPermits, SiteInspectionOperationalDataPermitsModel.class));
                String siteInspectionAdditionalMobileWindPower = cursor.getString(7);
                siteInspectionModel.setSiteInspectionAdditionalMobileWindPowerModel(new Gson().fromJson
                        (siteInspectionAdditionalMobileWindPower, SiteInspectionAdditionalMobileWindPowerModel.class));
                String siteInspectionSelectEmail = cursor.getString(8);
                ArrayList<String> listOfemailAddress = new ArrayList<>();
                SiteInspectionModel.getEmailAddressFromString(siteInspectionSelectEmail, listOfemailAddress);
                siteInspectionModel.setListOfEmailAddress(listOfemailAddress);
                listOfSiteInspection.add(siteInspectionModel);
            } while (cursor.moveToNext());
        }
        db.close();
        return listOfSiteInspection;
    }

    public ArrayList<SiteInspectionModel> getSiteInspectionListUploaded()
    {
        ArrayList<SiteInspectionModel> listOfSiteInspection = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SITE_INSPECTION + " WHERE " + KEY_FLAG + "=4 ORDER BY " +
                KEY_SITE_INSPECTION_ID + " DESC LIMIT 10"  ,null);
        if (cursor.moveToFirst())
        {
            do
            {
                SiteInspectionModel siteInspectionModel = new SiteInspectionModel();
                siteInspectionModel.setId(cursor.getInt(0));
                siteInspectionModel.setUploadId(cursor.getString(1));
                siteInspectionModel.setFlag(cursor.getInt(2));
                siteInspectionModel.setDate(cursor.getString(3));
                String siteInspectionDetail = cursor.getString(4);
                siteInspectionModel.setSiteInspectionNewModel(new Gson().fromJson
                        (siteInspectionDetail, SiteInspectionNewModel.class));
                String siteInspectionOperationalEnvironment = cursor.getString(5);
                siteInspectionModel.setSiteInspectionOperationalEnvironmentModel(new Gson().fromJson
                        (siteInspectionOperationalEnvironment, SiteInspectionOperationalEnvironmentModel.class));
                String siteInspectionOperationalDataPermits = cursor.getString(6);
                siteInspectionModel.setSiteInspectionOperationalDataPermitsModel(new Gson().fromJson
                        (siteInspectionOperationalDataPermits, SiteInspectionOperationalDataPermitsModel.class));
                String siteInspectionAdditionalMobileWindPower = cursor.getString(7);
                siteInspectionModel.setSiteInspectionAdditionalMobileWindPowerModel(new Gson().fromJson
                        (siteInspectionAdditionalMobileWindPower, SiteInspectionAdditionalMobileWindPowerModel.class));
                String siteInspectionSelectEmail = cursor.getString(8);
                ArrayList<String> listOfemailAddress = new ArrayList<>();
                SiteInspectionModel.getEmailAddressFromString(siteInspectionSelectEmail, listOfemailAddress);
                siteInspectionModel.setListOfEmailAddress(listOfemailAddress);
                listOfSiteInspection.add(siteInspectionModel);
            } while (cursor.moveToNext());
        }
        db.close();
        return listOfSiteInspection;
    }
    public boolean checkIfSiteInspectionUploadRemaining()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SITE_INSPECTION + " WHERE " + KEY_FLAG + "=?" ,new String[]{"1"});
        if(cursor.getCount() > 0)
        {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public SiteInspectionModel getSiteInspectionPhotoUploadModelSingle()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SITE_INSPECTION +
                " WHERE " + KEY_FLAG + "=3 limit 1", null);
        SiteInspectionModel siteInspectionModel = new SiteInspectionModel();
        if (cursor.moveToFirst())
        {
            siteInspectionModel.setId(cursor.getInt(0));
            siteInspectionModel.setUploadId(cursor.getString(1));
            siteInspectionModel.setFlag(cursor.getInt(2));
            siteInspectionModel.setDate(cursor.getString(3));
            String siteInspectionDetail = cursor.getString(4);
            siteInspectionModel.setSiteInspectionNewModel(new Gson().fromJson
                    (siteInspectionDetail, SiteInspectionNewModel.class));
            String siteInspectionOperationalEnvironment = cursor.getString(5);
            siteInspectionModel.setSiteInspectionOperationalEnvironmentModel(new Gson().fromJson
                    (siteInspectionOperationalEnvironment, SiteInspectionOperationalEnvironmentModel.class));
            String siteInspectionOperationalDataPermits = cursor.getString(6);
            siteInspectionModel.setSiteInspectionOperationalDataPermitsModel(new Gson().fromJson
                    (siteInspectionOperationalDataPermits, SiteInspectionOperationalDataPermitsModel.class));
            String siteInspectionAdditionalMobileWindPower = cursor.getString(7);
            siteInspectionModel.setSiteInspectionAdditionalMobileWindPowerModel(new Gson().fromJson
                    (siteInspectionAdditionalMobileWindPower, SiteInspectionAdditionalMobileWindPowerModel.class));
            String siteInspectionSelectEmail = cursor.getString(8);
            ArrayList<String> listOfemailAddress = new ArrayList<>();
            SiteInspectionModel.getEmailAddressFromString(siteInspectionSelectEmail, listOfemailAddress);
            siteInspectionModel.setListOfEmailAddress(listOfemailAddress);
            //listOfSiteInspection.add(siteInspectionModel);
//            do
//            {
//
//            } while (cursor.moveToNext());
        }
        db.close();
        return siteInspectionModel;
    }

    public SiteInspectionModel getSiteInspectionListToUploadSingle()
    {
        //ArrayList<SiteInspectionModel> listOfSiteInspection = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SITE_INSPECTION + " WHERE " +
                KEY_FLAG + "=1 limit 1", null);
        SiteInspectionModel siteInspectionModel = new SiteInspectionModel();
        if (cursor.moveToFirst())
        {
            siteInspectionModel.setId(cursor.getInt(0));
            siteInspectionModel.setUploadId(cursor.getString(1));
            siteInspectionModel.setFlag(cursor.getInt(2));
            siteInspectionModel.setDate(cursor.getString(3));
            String siteInspectionDetail = cursor.getString(4);
            siteInspectionModel.setSiteInspectionNewModel(new Gson().fromJson
                    (siteInspectionDetail, SiteInspectionNewModel.class));
            String siteInspectionOperationalEnvironment = cursor.getString(5);
            siteInspectionModel.setSiteInspectionOperationalEnvironmentModel
                    (new Gson().fromJson(siteInspectionOperationalEnvironment, SiteInspectionOperationalEnvironmentModel.class));
            String siteInspectionOperationalDataPermits = cursor.getString(6);
            siteInspectionModel.setSiteInspectionOperationalDataPermitsModel(new Gson().fromJson
                    (siteInspectionOperationalDataPermits, SiteInspectionOperationalDataPermitsModel.class));
            String siteInspectionAdditionalMobileWindPower = cursor.getString(7);
            siteInspectionModel.setSiteInspectionAdditionalMobileWindPowerModel(new Gson().fromJson
                    (siteInspectionAdditionalMobileWindPower, SiteInspectionAdditionalMobileWindPowerModel.class));
            String siteInspectionSelectEmail = cursor.getString(8);
            ArrayList<String> listOfemailAddress = new ArrayList<>();
            SiteInspectionModel.getEmailAddressFromString(siteInspectionSelectEmail, listOfemailAddress);
            siteInspectionModel.setListOfEmailAddress(listOfemailAddress);
            //listOfSiteInspection.add(siteInspectionModel);
//            do
//            {
//
//            } while (cursor.moveToNext());
        }
        db.close();
        return siteInspectionModel;
    }

    public void addPricing3InsertJson(String kundenNr, String Json,Pricing3InsertData priceInsert)
    {

        String sql = "INSERT OR REPLACE INTO " + TABLE_PRICING3INSERTJSON + " (kunndenNr,json,Kontakt,CustomerNr,Mandant,Warenkorbart," +
                "Hoehengruppe,Einheit_Mietdauer,Mietdauer,Mietpreis,Standtag,Selbstbehalt,HandlingFee,ServicePauschale,Versicherung," +
                "WochenendeMitversichert,TransportAnfahrt,TransportPauschal,TransportAbfahrt,Beiladungspauschale,Einsatzinformation," +
                "Besteller_Telefon,Besteller_Email,Notiz,KaNr,AnsPartner,Besteller_Anrede,Besteller_Mobil,Mautkilometer," +
                "Winterreifenpauschale,Bearbeitet,Kalendertage,Referenz,Geraetetyp,loginUserNumberrange,Ersteller," +
                "branch,hgrp,deviceType,manufacturer,type,md,rentalPrice,sb,hf,sp,hb,best,kundenNr,isKann,isLieferung," +
                "isVoranmeldung,isBenachrichtgung,isRampena,isSonstige,isEinweisung,isSelbstfahrer,kann,voranmeldung,benarchrichtung,sonstige,ladefahrzaug" +
                ",start_date,start_time,end_date,end_time) VALUES (?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindString(1,kundenNr);
            stmt.bindString(2,Json);
            stmt.bindString(3,  priceInsert.getKontakt());
            stmt.bindString(4, priceInsert.getKundenNr());
            stmt.bindString(5, String.valueOf(priceInsert.getMandant()));
            stmt.bindString(6, String.valueOf(priceInsert.getWarenkorbart()));
            stmt.bindString(7, priceInsert.getHoehengruppe());
            stmt.bindString(8, String.valueOf(priceInsert.getEinheit_Mietdauer()));
            stmt.bindString(9, String.valueOf(priceInsert.getMietdauer()));
            stmt.bindString(10,priceInsert.getMietpreis());
            stmt.bindString(11, String.valueOf(priceInsert.getStandtag()));
            stmt.bindString(12, priceInsert.getSelbstbehalt());
            stmt.bindString(13, String.valueOf(priceInsert.getHandlingFee()));
            stmt.bindString(14, String.valueOf(priceInsert.getServicePauschale()));
            stmt.bindString(15, String.valueOf(priceInsert.getVersicherung()));
            stmt.bindString(16, String.valueOf(priceInsert.getWochenendeMitversichert()));
            stmt.bindString(17, String.valueOf(priceInsert.getTransportAnfahrt()));
            stmt.bindString(18, String.valueOf(priceInsert.getTransportPauschal()));
            stmt.bindString(19, String.valueOf(priceInsert.getTransportAbfahrt()));
            stmt.bindString(20, String.valueOf(priceInsert.getBeiladungspauschale()));
            stmt.bindString(21, priceInsert.getEinsatzinformation());
            stmt.bindString(22, priceInsert.getBesteller_Telefon());
            stmt.bindString(23, priceInsert.getBesteller_Email());
            stmt.bindString(24, priceInsert.getNotiz());
            stmt.bindString(25, String.valueOf(priceInsert.getKaNr()));
            stmt.bindString(26, priceInsert.getAnsPartner());
            stmt.bindString(27, priceInsert.getBesteller_Anrede());
            stmt.bindString(28, priceInsert.getBesteller_Mobil());
            stmt.bindString(29, priceInsert.getMautkilometer());
            stmt.bindString(30, String.valueOf(priceInsert.getWinterreifenpauschale()));
            stmt.bindString(31, String.valueOf(priceInsert.getBearbeitet()));
            stmt.bindString(32, String.valueOf(priceInsert.getKalendertage()));
            stmt.bindString(33, priceInsert.getReferenz());
            stmt.bindString(34, priceInsert.getGeraetetyp());
            stmt.bindString(35, priceInsert.getUserID());
            stmt.bindString(36, String.valueOf(priceInsert.getErsteller()));
            stmt.bindString(37, priceInsert.getBranch());
            stmt.bindString(38, priceInsert.gethGRP());
            stmt.bindString(39, priceInsert.getDeviceType());
            stmt.bindString(40, priceInsert.getManufacturer());
            stmt.bindString(41, priceInsert.getType());
            stmt.bindString(42, String.valueOf(priceInsert.getMd()));
            stmt.bindString(43, String.valueOf(priceInsert.getRentalPrice()));
            stmt.bindString(44, String.valueOf(priceInsert.getSb()));
            stmt.bindString(45, priceInsert.getHfStatus());
            stmt.bindString(46, priceInsert.getSpStatus());
            stmt.bindString(47, String.valueOf(priceInsert.getHb()));
            stmt.bindString(48, priceInsert.getBest());
            stmt.bindString(49, priceInsert.getKundenNr());
            stmt.bindString(50, String.valueOf(priceInsert.isKann()));
            stmt.bindString(51, String.valueOf(priceInsert.isLieferung()));
            stmt.bindString(52, String.valueOf(priceInsert.isVoranmeldung()));
            stmt.bindString(53, String.valueOf(priceInsert.isBenachrichtgung()));
            stmt.bindString(54, String.valueOf(priceInsert.isRampena()));
            stmt.bindString(55, String.valueOf(priceInsert.isSonstige()));
            stmt.bindString(56, String.valueOf(priceInsert.isEinweisung()));
            stmt.bindString(57, String.valueOf(priceInsert.isSelbstfahrer()));
            stmt.bindString(58, priceInsert.getStrKann());
            stmt.bindString(59, priceInsert.getStrVoranmeldung());
            stmt.bindString(60, priceInsert.getStrBenachrich());
            stmt.bindString(61, priceInsert.getStrSonstige());
            stmt.bindString(62, String.valueOf(priceInsert.getintLadeiahrzeug()));
            stmt.bindString(63, priceInsert.getStrstartDate());
            stmt.bindString(64,priceInsert.getStrstartTime());
            stmt.bindString(65, priceInsert.getStrendDate());
            stmt.bindString(66, priceInsert.getStrendTime());
            stmt.execute();
            stmt.clearBindings();
            // long count =stmt.executeInsert();
            //Log.e(" prepare statment "," count variable for device type :  "+count);
            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e(" prepare statment for device type"," excepiton while inser : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PRICING3INSERTJSON_KUNDENNR, kundenNr);
        values.put(KEY_PRICING3INSERTJSON_JSON, Json);
        values.put(KEY_PRICING3INSERTJSON_Kontakt, priceInsert.getKontakt());
        values.put(KEY_PRICING3INSERTJSON_CustomerNr, priceInsert.getKundenNr());
        values.put(KEY_PRICING3INSERTJSON_Mandant, priceInsert.getMandant());
        values.put(KEY_PRICING3INSERTJSON_Warenkorbart, priceInsert.getWarenkorbart());
        values.put(KEY_PRICING3INSERTJSON_Hoehengruppe, priceInsert.getHoehengruppe());
        values.put(KEY_PRICING3INSERTJSON_Einheit_Mietdauer, priceInsert.getEinheit_Mietdauer());
        values.put(KEY_PRICING3INSERTJSON_Mietdauer, priceInsert.getMietdauer());
        values.put(KEY_PRICING3INSERTJSON_Mietpreis, priceInsert.getMietpreis());
        values.put(KEY_PRICING3INSERTJSON_Standtag, priceInsert.getStandtag());
        values.put(KEY_PRICING3INSERTJSON_Selbstbehalt, priceInsert.getSelbstbehalt());
        values.put(KEY_PRICING3INSERTJSON_HandlingFee, priceInsert.getHandlingFee());
        values.put(KEY_PRICING3INSERTJSON_ServicePauschale, priceInsert.getServicePauschale());
        values.put(KEY_PRICING3INSERTJSON_Versicherung, priceInsert.getVersicherung());
        values.put(KEY_PRICING3INSERTJSON_WochenendeMitversichert, priceInsert.getWochenendeMitversichert());
        values.put(KEY_PRICING3INSERTJSON_TransportAnfahrt, priceInsert.getTransportAnfahrt());
        values.put(KEY_PRICING3INSERTJSON_TransportPauschal, priceInsert.getTransportPauschal());
        values.put(KEY_PRICING3INSERTJSON_TransportAbfahrt, priceInsert.getTransportAbfahrt());
        values.put(KEY_PRICING3INSERTJSON_Beiladungspauschale, priceInsert.getBeiladungspauschale());
        values.put(KEY_PRICING3INSERTJSON_Einsatzinformation, priceInsert.getEinsatzinformation());
        values.put(KEY_PRICING3INSERTJSON_Besteller_Telefon, priceInsert.getBesteller_Telefon());
        values.put(KEY_PRICING3INSERTJSON_Besteller_Email, priceInsert.getBesteller_Email());
        values.put(KEY_PRICING3INSERTJSON_Notiz, priceInsert.getNotiz());
        values.put(KEY_PRICING3INSERTJSON_KaNr, priceInsert.getKaNr());
        values.put(KEY_PRICING3INSERTJSON_AnsPartner, priceInsert.getAnsPartner());
        values.put(KEY_PRICING3INSERTJSON_Besteller_Anrede, priceInsert.getBesteller_Anrede());
        values.put(KEY_PRICING3INSERTJSON_Besteller_Mobil, priceInsert.getBesteller_Mobil());
        values.put(KEY_PRICING3INSERTJSON_Mautkilometer, priceInsert.getMautkilometer());
        values.put(KEY_PRICING3INSERTJSON_Winterreifenpauschale, priceInsert.getWinterreifenpauschale());
        values.put(KEY_PRICING3INSERTJSON_Bearbeitet, priceInsert.getBearbeitet());
        values.put(KEY_PRICING3INSERTJSON_Kalendertage, priceInsert.getKalendertage());
        values.put(KEY_PRICING3INSERTJSON_Referenz, priceInsert.getReferenz());
        values.put(KEY_PRICING3INSERTJSON_Geraetetyp, priceInsert.getGeraetetyp());
        values.put(KEY_PRICING3INSERTJSON_loginUserNumberrange, priceInsert.getUserID());
        values.put(KEY_PRICING3INSERTJSON_Ersteller, priceInsert.getErsteller());
        values.put(KEY_PRICING3INSERTJSON_BRANCH, priceInsert.getBranch());
        values.put(KEY_PRICING3INSERTJSON_HGRP, priceInsert.gethGRP());
        values.put(KEY_PRICING3INSERTJSON_DEVICE_TYPE, priceInsert.getDeviceType());
        values.put(KEY_PRICING3INSERTJSON_MANUFACTURER, priceInsert.getManufacturer());
        values.put(KEY_PRICING3INSERTJSON_TYPE, priceInsert.getType());
        values.put(KEY_PRICING3INSERTJSON_MD, priceInsert.getMd());
        values.put(KEY_PRICING3INSERTJSON_RENTAL_PRICE, priceInsert.getRentalPrice());
        values.put(KEY_PRICING3INSERTJSON_SB, priceInsert.getSb());
        values.put(KEY_PRICING3INSERTJSON_HF, priceInsert.getHfStatus());
        values.put(KEY_PRICING3INSERTJSON_SP, priceInsert.getSpStatus());
        values.put(KEY_PRICING3INSERTJSON_HB, priceInsert.getHb());
        values.put(KEY_PRICING3INSERTJSON_BEST, priceInsert.getBest());
        values.put(KEY_PRICING3INSERTJSON_CUSTOMER_KUNDEN_NR, priceInsert.getKundenNr());
        values.put(IS_KANN, priceInsert.isKann());
        values.put(IS_LIEFERUNG, priceInsert.isLieferung());
        values.put(IS_VORANMELDUNG, priceInsert.isVoranmeldung());
        values.put(IS_BENACHRICHTGUNG, priceInsert.isBenachrichtgung());
        values.put(IS_RAMPENA, priceInsert.isRampena());
        values.put(IS_SONSTIGE, priceInsert.isSonstige());
        values.put(IS_EINWEISUNG, priceInsert.isEinweisung());
        values.put(IS_SELBSTFAHRER, priceInsert.isSelbstfahrer());
        values.put(KANN, priceInsert.getStrKann());
        values.put(VORANMELDUNG, priceInsert.getStrVoranmeldung());
        values.put(BENACHRICHTUNG, priceInsert.getStrBenachrich());
        values.put(SONSTIGE, priceInsert.getStrSonstige());
        values.put(LADEFAHRZAUG, priceInsert.getintLadeiahrzeug());
        values.put(START_DATE, priceInsert.getStrstartDate());
        values.put(START_TIME, priceInsert.getStrstartTime());
        values.put(END_DATE, priceInsert.getStrendDate());
        values.put(END_TIME, priceInsert.getStrendTime());

        long id = db.insert(TABLE_PRICING3INSERTJSON, null, values);
        Log.e(" @@@@@@@@@@@@@@@@@@@@"," inserte data in database on cache button click : "+id);
        db.close();*/
    }

    public Pricing3InsertData getPricing3InsertData(String KundenNr,int ids)
    {
        //Pricing3InsertData pricingInsert_list = new Pricing3InsertData();
        SQLiteDatabase db = this.getWritableDatabase();
        Pricing3InsertData pricing3InsertDatas = new Pricing3InsertData();
        try {




            String jsonOfEqu = "";
            Cursor cursor = db.rawQuery("select * from " + TABLE_PRICING3INSERTJSON + " where kunndenNr = ?  " +
                    "AND id = ?", new String[]{KundenNr, String.valueOf(ids)});
//        Cursor cursor = db.rawQuery("select Kontakt,kunndenNr,CustomerNr,Mandant,Warenkorbart,Hoehengruppe,Einheit_Mietdauer,Mietdauer,Mietpreis,Standtag,Selbstbehalt,HandlingFee,ServicePauschale,Versicherung,WochenendeMitversichert,TransportAnfahrt,TransportPauschal,TransportAbfahrt,Beiladungspauschale,Einsatzinformation,Besteller,Besteller_Telefon,Besteller_Email,Notiz,KaNr,AnsPartner,Besteller_Anrede,Besteller_Mobil,Mautkilometer,Winterreifenpauschale,Bearbeitet,Kalendertage,Referenz,Geraetetyp,loginUserNumberrange,branch,hgrp,deviceType,manufacturer,type,md,rentalPrice,sb,hf,sp,hb,best,kundenNr,Ersteller,json from " + TABLE_PRICING3INSERTJSON + " where kunndenNr = ?  AND id = ?", new String[]{KundenNr,String.valueOf(ids)});
            // ORDER BY id LIMIT 1
            Log.e("cursor", cursor.getCount() + "");
            if (cursor.moveToFirst()) {

                pricing3InsertDatas.setKontakt(cursor.getString(cursor.getColumnIndex("Kontakt")));
                pricing3InsertDatas.setKundenNr(cursor.getString(cursor.getColumnIndex("kunndenNr")));
                pricing3InsertDatas.setCustomerNr(cursor.getString(cursor.getColumnIndex("CustomerNr")));
                pricing3InsertDatas.setMandant(cursor.getInt(cursor.getColumnIndex("Mandant")));
                pricing3InsertDatas.setWarenkorbart(cursor.getInt(cursor.getColumnIndex("Warenkorbart")));
                pricing3InsertDatas.setHoehengruppe(cursor.getString(cursor.getColumnIndex("Hoehengruppe")));
                pricing3InsertDatas.setEinheit_Mietdauer(cursor.getInt(cursor.getColumnIndex("Einheit_Mietdauer")));
                pricing3InsertDatas.setMietdauer(cursor.getInt(cursor.getColumnIndex("Mietdauer")));
                pricing3InsertDatas.setMietpreis(cursor.getString(cursor.getColumnIndex("Mietpreis")));
                pricing3InsertDatas.setStandtag(cursor.getDouble(cursor.getColumnIndex("Standtag")));
                pricing3InsertDatas.setSelbstbehalt(cursor.getString(cursor.getColumnIndex("Selbstbehalt")));
                pricing3InsertDatas.setHandlingFee(cursor.getInt(cursor.getColumnIndex("HandlingFee")));
                pricing3InsertDatas.setServicePauschale(cursor.getInt(cursor.getColumnIndex("ServicePauschale")));
                pricing3InsertDatas.setVersicherung(cursor.getDouble(cursor.getColumnIndex("Versicherung")));
                pricing3InsertDatas.setWochenendeMitversichert(cursor.getInt(cursor.getColumnIndex("WochenendeMitversichert")));
                pricing3InsertDatas.setTransportAnfahrt(cursor.getDouble(cursor.getColumnIndex("TransportAnfahrt")));
                pricing3InsertDatas.setTransportPauschal(cursor.getDouble(cursor.getColumnIndex("TransportPauschal")));
                pricing3InsertDatas.setTransportAbfahrt(cursor.getDouble(cursor.getColumnIndex("TransportAbfahrt")));
                pricing3InsertDatas.setBeiladungspauschale(cursor.getDouble(cursor.getColumnIndex("Beiladungspauschale")));
                pricing3InsertDatas.setEinsatzinformation(cursor.getString(cursor.getColumnIndex("Einsatzinformation")));
                pricing3InsertDatas.setBesteller(cursor.getString(cursor.getColumnIndex("Besteller")));
                pricing3InsertDatas.setBesteller_Telefon(cursor.getString(cursor.getColumnIndex("Besteller_Telefon")));
                pricing3InsertDatas.setBesteller_Email(cursor.getString(cursor.getColumnIndex("Besteller_Email")));
                pricing3InsertDatas.setNotiz(cursor.getString(cursor.getColumnIndex("Notiz")));
                pricing3InsertDatas.setKaNr(cursor.getInt(cursor.getColumnIndex("KaNr")));
                pricing3InsertDatas.setAnsPartner(cursor.getString(cursor.getColumnIndex("AnsPartner")));
                pricing3InsertDatas.setBesteller_Anrede(cursor.getString(cursor.getColumnIndex("Besteller_Anrede")));
                pricing3InsertDatas.setBesteller_Mobil(cursor.getString(cursor.getColumnIndex("Besteller_Mobil")));
                pricing3InsertDatas.setMautkilometer(cursor.getString(cursor.getColumnIndex("Mautkilometer")));
                pricing3InsertDatas.setWinterreifenpauschale(cursor.getInt(cursor.getColumnIndex("Winterreifenpauschale")));
                pricing3InsertDatas.setBearbeitet(cursor.getInt(cursor.getColumnIndex("Bearbeitet")));
                pricing3InsertDatas.setKalendertage(cursor.getInt(cursor.getColumnIndex("Kalendertage")));
                pricing3InsertDatas.setReferenz(cursor.getString(cursor.getColumnIndex("Referenz")));
                pricing3InsertDatas.setGeraetetyp(cursor.getString(cursor.getColumnIndex("Geraetetyp")));
                pricing3InsertDatas.setUserID(cursor.getString(cursor.getColumnIndex("loginUserNumberrange")));
                pricing3InsertDatas.setBranch(cursor.getString(cursor.getColumnIndex("branch")));
                pricing3InsertDatas.sethGRP(cursor.getString(cursor.getColumnIndex("hgrp")));
                pricing3InsertDatas.setDeviceType(cursor.getString(cursor.getColumnIndex("deviceType")));
                pricing3InsertDatas.setManufacturer(cursor.getString(cursor.getColumnIndex("manufacturer")));
                pricing3InsertDatas.setType(cursor.getString(cursor.getColumnIndex("type")));
                pricing3InsertDatas.setMd(cursor.getInt(cursor.getColumnIndex("md")));
                pricing3InsertDatas.setRentalPrice(cursor.getDouble(cursor.getColumnIndex("rentalPrice")));
                pricing3InsertDatas.setSb(cursor.getDouble(cursor.getColumnIndex("sb")));
                pricing3InsertDatas.setHfStatus(cursor.getString(cursor.getColumnIndex("hf")));
                pricing3InsertDatas.setSpStatus(cursor.getString(cursor.getColumnIndex("sp")));
                pricing3InsertDatas.setHb(cursor.getDouble(cursor.getColumnIndex("hb")));
                pricing3InsertDatas.setBest(cursor.getString(cursor.getColumnIndex("best")));
                pricing3InsertDatas.setKundenNr(cursor.getString(cursor.getColumnIndex("kundenNr")));
                pricing3InsertDatas.setErsteller(cursor.getInt(cursor.getColumnIndex("Ersteller")));
                pricing3InsertDatas.setJsonOfEqu(cursor.getString(cursor.getColumnIndex("json")));


                pricing3InsertDatas.setKann(GlobalClass.intToBool(cursor.getInt(cursor.getColumnIndex("isKann"))));//////////
                pricing3InsertDatas.setLieferung(GlobalClass.intToBool(cursor.getInt(cursor.getColumnIndex("isLieferung"))));
                pricing3InsertDatas.setVoranmeldung(GlobalClass.intToBool(cursor.getInt(cursor.getColumnIndex("isVoranmeldung"))));
                pricing3InsertDatas.setBenachrichtgung(GlobalClass.intToBool(cursor.getInt(cursor.getColumnIndex("isBenachrichtgung"))));
                pricing3InsertDatas.setRampena(GlobalClass.intToBool(cursor.getInt(cursor.getColumnIndex("isRampena"))));
                pricing3InsertDatas.setSonstige(GlobalClass.intToBool(cursor.getInt(cursor.getColumnIndex("isSonstige"))));
                pricing3InsertDatas.setEinweisung(GlobalClass.intToBool(cursor.getInt(cursor.getColumnIndex("isEinweisung"))));
                pricing3InsertDatas.setSelbstfahrer(GlobalClass.intToBool(cursor.getInt(cursor.getColumnIndex("isSelbstfahrer"))));

                pricing3InsertDatas.setStrKann(cursor.getString(cursor.getColumnIndex("kann")));
                pricing3InsertDatas.setStrVoranmeldung(cursor.getString(cursor.getColumnIndex("voranmeldung")));
                pricing3InsertDatas.setStrBenachrich(cursor.getString(cursor.getColumnIndex("benarchrichtung")));
                pricing3InsertDatas.setStrSonstige(cursor.getString(cursor.getColumnIndex("sonstige")));
                pricing3InsertDatas.setintLadeiahrzeug((cursor.getInt(cursor.getColumnIndex("ladefahrzaug"))));

                pricing3InsertDatas.setStrstartDate(cursor.getString(cursor.getColumnIndex("start_date")));
                pricing3InsertDatas.setStrstartTime(cursor.getString(cursor.getColumnIndex("start_time")));
                pricing3InsertDatas.setStrendDate(cursor.getString(cursor.getColumnIndex("end_date")));
                pricing3InsertDatas.setStrendTime(cursor.getString(cursor.getColumnIndex("end_time")));
            }
            db.close();
            return pricing3InsertDatas;
        }catch (Exception e){
            Log.e(" in catch "," exception while fetch data : "+e.toString());
            db.close();
            return pricing3InsertDatas;
        }

    }

    public void addBuheneart(ArrayList<BuheneartModel> listOfBuheneart)
    {
        String sql = "INSERT OR REPLACE INTO " + TABLE_BUHENEART + " (Bezeichnung,Buheneart,Sort," +
                "Sprache) VALUES ( ?,?,?,?)";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            getPragma(db);
            db.beginTransactionNonExclusive();
            // db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(sql);

            for(int x=0; x < listOfBuheneart.size(); x++){

                BuheneartModel buheneartModel = new BuheneartModel();
                buheneartModel = listOfBuheneart.get(x);

                stmt.bindString(1, buheneartModel.getBezeichnung());
                stmt.bindString(2, buheneartModel.getBuehnenArt());
                stmt.bindString(3, buheneartModel.getSort());
                stmt.bindString(4, buheneartModel.getSprache());
                stmt.execute();
                stmt.clearBindings();
               // long count =stmt.executeInsert();
              //  Log.e(" prepare statment "," count variable for device type :  "+count);
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
        }catch (Exception e){
            Log.e(" prepare statment for device type"," excepiton while inser : "+e.toString());
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (BuheneartModel deviceType : listOfBuheneart)
            {
                ContentValues languageValues = new ContentValues();
                languageValues.put(KEY_BEZEICHUNG, deviceType.getBezeichnung());
                languageValues.put(KEY_BUHENEARTART, deviceType.getBuehnenArt());
                languageValues.put(KEY_SORT, deviceType.getSort());
                languageValues.put(KEY_SPARCHE, deviceType.getSprache());

                long id = db.replace(TABLE_BUHENEART, null, languageValues);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            db.endTransaction();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }*/

    }

    public ArrayList<BuheneartModel> getBuheneart()
    {
        ArrayList<BuheneartModel> listOfBuheneart = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_BUHENEART, null);
        if (cursor.moveToFirst()) {
            do {
                BuheneartModel buheneartModel = new BuheneartModel();
                buheneartModel.setBezeichnung(cursor.getString(0));
                buheneartModel.setBuehnenArt(cursor.getString(1));
                buheneartModel.setSort(cursor.getString(2));
                buheneartModel.setSprache(cursor.getString(3));
                listOfBuheneart.add(buheneartModel);

            } while (cursor.moveToNext());
        }
        db.close();
        return listOfBuheneart;
    }

    private void getPragma(SQLiteDatabase sqlDb){
        sqlDb.execSQL("PRAGMA cache_size=10000");
    }

}