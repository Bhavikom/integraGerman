package de.mateco.integrAMobile.Helper;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.GridImage;

public class DataHelper
{
    public static String bvoInsertPhotoStart = "de.mateco.integrAMobile.bvoInsertPhotoStart";
    public static String bvoAgainStartBvoUpload = "de.mateco.integrAMobile.bvoAgainStartBvoUpload";
    public static String isPersonLogin = "isLoginPerson";
    public static String LoginPerson = "LoginPerson";
    public static String isCustomerLoaded = "isCustomerLoaded";
    public static String LoadedCustomer = "LoadedCustomer";
    public static String LoadedCustomerContactPerson = "LoadedCustomerContactPerson";
    //public static String LoadedCustomerFeatures = "LoadedCustomerFeatures";
    public static String LoadedCustomerActivity = "LoadedCustomerActivity";
    public static String LoadedCustomerProject = "LoadedCustomerProject";
    public static String LoadedCustomerOffer = "LoadedCustomerOffer";
    public static String LoadedCustomerOpenSpecials = "CustomerOpenSpecials";
    public static String LoadedCustomerCompletedOrders = "CustomerCompletedOrder";
    public static String LoadedCustomerOpenOrders = "CustomerOpenOrder";
    public static String LoadedCustomerLostSale = "CustomerLostSale";
    public static String isSiteInspectionLoaded = "SiteInspectionLoaded";
    public static String PricingCustomerBasicOrderInfo = "PricingCustomerBasicOrderInfo";
    public static String LoadedProjectDetailGenerallyInfo = "LoadedProjectDetailGenerallyInfo";
    public static String LoadedProjectDetailActivityInfo = "LoadedProjectDetailActivityInfo";
    public static String LoadedProjectDetailTradesInfo = "LoadedProjectDetailTradesInfo";
    public static String ProjectTradeUpdate = "ProjectTradeUpdate";
    public static String ProjectTradeInsert = "ProjectTradeInsert";
    public static String AgendaEmployeeSelected = "AgendaEmployeeSelected";
    public static String AgendaDate = "AgendaDate";
    public static String StoreAgenda = "StoreAgenda";
    public static String StoreOtherAgenda = "StoreOtherAgenda";
    public static String WeeklyAgendaDate = "WeeklyAgendaDate";
    public static String NetworkError="networkerror";
    public static long NETWORK_CALL_DURATION=3000;
    //    https://priceapp.tk/Mateco/MatecoSalesAppService.svc/json/GetToken
//    public static String customerDataFragment = "Customer Data Fragment";
//    public static String customerDataFragmentEdit = "Customer Data Fragment Edit";
    //public static String ACCESS_PROTOCOL = "http://";
    public static String ACCESS_PROTOCOL = "https://";

    //public static String ACCESS_HOST = "27.113.255.34/";
    //public static String ACCESS_HOST = "mobile.mateco.de/";
    public static String ACCESS_HOST = "mobile4.mateco.de/";
    //public static String ACCESS_HOST = "192.168.15.153/";

    /* old url */
    /* for live version old */
    //public static String APP_NAME = "integraMobile/MatecoSalesAppService.SalesAppService.svc/json/";
    /* for test version */
    /* for shculung version */

    /* for pl version */
    //public static String APP_NAME =  "integrAMobilePl/MatecoSalesAppService.svc/json/";
    //public static String APP_NAME =  "integrAMobilePlTest/MatecoSalesAppService.svc/json/";

    /* for lu version*/
    //public static String APP_NAME = "integrAMobileLu/MatecoSalesAppService.svc/json/";
    //public static String APP_NAME = "integrAMobileLuTest/MatecoSalesAppService.svc/json/";

    // use when want to call with TEST
   /* public static String APP_NAME = "integrAMobileTest/MatecoSalesAppService.svc/json/";
    public static String URL_USER_HELPER = "https://mobile.mateco.de/UserServiceTest/api/userhelper/";
    public static String URL_PRICE_HELPER = "https://mobile.mateco.de/PriceServiceTest/api/pricehelper/";
    public static String URL_CUSTOMER_HELPER = "https://mobile.mateco.de/CustomerServiceTest/api/customerhelper/";
    public static String URL_BVO_HELPER = "https://mobile.mateco.de/BVOServiceTest/api/bvohelper/";
    public static String URL_PROJECT_HELPER = "https://mobile.mateco.de/ProjectServiceTest/api/projecthelper/";
    public static String URL_AGENDA_HELPER = "https://mobile.mateco.de/AgendaServiceTest/api/agendahelper/";*/

    /* use when0 want to call with SCHULUNG url*/
    public static String APP_NAME = "integrAMobileSchulung/MatecoSalesAppService.svc/json/";
    public static String URL_USER_HELPER = "https://mobile.mateco.de/UserServiceSchulung/api/userhelper/";
    public static String URL_PRICE_HELPER = "https://mobile.mateco.de/PriceServiceSchulung/api/pricehelper/";
    public static String URL_CUSTOMER_HELPER = "https://mobile.mateco.de/CustomerServiceSchulung/api/customerhelper/";
    public static String URL_BVO_HELPER = "https://mobile.mateco.de/BVOServiceSchulung/api/bvohelper/";
    public static String URL_PROJECT_HELPER = "https://mobile.mateco.de/ProjectServiceSchulung/api/projecthelper/";
    public static String URL_AGENDA_HELPER = "https://mobile.mateco.de/AgendaServiceSchulung/api/agendahelper/";

    /* use when want to call with LIVE URL */
    /*public static String APP_NAME = "integrAMobile/MatecoSalesAppService.svc/json/";
    public static String URL_USER_HELPER = "https://mobile4.mateco.de/UserService/api/userhelper/";
    public static String URL_PRICE_HELPER = "https://mobile4.mateco.de/PriceService/api/pricehelper/";
    public static String URL_CUSTOMER_HELPER = "https://mobile4.mateco.de/CustomerService/api/customerhelper/";
    public static String URL_BVO_HELPER = "https://mobile4.mateco.de/BVOService/api/bvohelper/";
    public static String URL_PROJECT_HELPER = "https://mobile4.mateco.de/ProjectService/api/projecthelper/";
    public static String URL_AGENDA_HELPER = "https://mobile4.mateco.de/AgendaService/api/agendahelper/";*/



    //public static String CHECK_LOGIN = "GetUserDetails";3333333333333333333333
    public static String MAIN_SERVICE = "SalesServices";
    public static String CHECK_LOGIN = "UserLoginDetails";
    public static String CUSTOMER_SEARCH = "CustomerSearch";
    public static String CUSTOMER_LIST_SHOW = "CustomerListShow";
    public static String CUSTOMER_SEARCH_PAGING = "CustomerSearchPaging";
    public static String CHANGE_PASSWORD = "ChangePassword";
    public static String CHANGE_CURRENT_PASSWORD = "ChangeCurrentPassword";
    public static String ForgotPassword = "ForgetPassword";
    public static String CUSTOMER_INSERT = "CustomerInsert";
    public static String CUSTOMER_UPDATE = "CustomerUpdate";
    //    public static String COUNTRY_LIST = "CustomerLandCombo";
    public static String CUSTOMER_CONTACT_PERSON_LIST = "CustomerContactPersonList";
    public static String CUSTOMER_ACTIVITY_LIST = "CustomerActivityListShow";
    //    public static String RECHTS_FORM = "CustomerRFCombo";
//    public static String GET_FEATURE_LIST = "CustomerCPFeatureList";
//    public static String GET_EMPLOYEE_LIST = "CustomerActivityEmployee";
//    public static String GET_ACTIVITY_TYPE_LIST = "CustomerActivityType";
//    public static String GET_ACTIVITY_TOPIC_LIST = "CustomerActivityTopic";
    public static String GET_CUSTOMER_OPEN_ORDER = "CustomerOpenOrder";
    public static String GET_CUSTOMER_COMPLETED_ORDER = "CustomerCompletedOrders";
    public static String GET_CUSTOMER_OPEN_SPECIALS = "CustomerOpenSpecials";
    public static String GET_CUSTOMER_LOST_SALE = "CustomerLostSale";
    public static String INSERT_CUSTOMER_CONTACT_PERSON = "CustomerContactInsert";
    public static String UPDATE_CUSTOMER_CONTACT_PERSON = "CustomerContactUpdate";
    //    public static String GET_LOADED_CUSTOMER_FEATURE_LIST = "CustomerCPFeatureLoaded";
    public static String INSERT_CUSTOMER_ACTIVITY = "CustomerActivityInsert";
    public static String UPDATE_CUSTOMER_ACTIVITY = "CustomerActivityUpdate";
    //    public static String COMBO_SALUTATION = "CustomerCPSalutationCombo";
//    public static String COMBO_FUNCTION = "CustomerCPFunctionCombo";
//    public static String COMBO_DOCUMENT_LANGUAGE = "CustomerCPDocumentlanguage";
//    public static String COMBO_DECISION_MAKER = "CustomerCPDecisionmakers";
//    public static String pricing1Branch = "PriceBranchDropdown";
//    public static String pricing1Device = "PriceDeviceGroup";
    public static String pricing1LevelGroup = "PriceLevelGroup";
    public static String pricing1Equipment = "PriceEquipmentHeight";
    //    public static String pricing1PriceRental = "PriceRental";
    public static String pricing2PriceKaNrCombo = "PriceKaNrCombo";
    public static String pricing2PriceCockpitPreise = "PriceCockpitPreise";
    public static String PRICE_DEVICE_TYPE_COMBO_SERVICE = "PriceGeraeteTypeCombo";
    public static String pricing2PriceCockpitPreiseLessThen = "PriceCockpitPreiseLess";
    public static String pricing2PriceCockpitPreiseMoreThen = "PriceCockpitPreiseMore";
    public static String pricing3PriceHAFTB = "PriceHAFTB";
    public static String PriceHaftungsbegrenzungBoxStatus = "PriceHaftungsbegrenzungBoxStatus";
    public static String PriceKalendertaeglichCheckBox = "PriceKalendertaeglichCheckBox";
    public static String PriceHandlingCheckBox = "PriceHandlingCheckBox";
    public static String PriceServiceCheckBox = "PriceServiceCheckBox";
    //    public static String PriceOfflineStandard = "PriceOfflineStandard";
//    public static String PriceEquipmentHeightOffLine = "PriceEquipmentHeightOffLine";
    public static String PriceInsert = "PriceInsert";
    public static String Pricing_Screen_3_Obj = "PriceScreenThreeObj";
    public static String SiteInspectionId = "SiteInspectionId";
    public static String PriceReasonRejectionInsert = "PriceReasonRejectionInsert";
    public static String SiteInspectionWithoutCustomer = "SiteInspectionWithoutCUstomer";
    /********
     * 20161108
     **********/
    public static String SiteInspectionCheckForProperMatchcode = "CheckForProperMatchcode";
    /*************************/
    public static String SiteInspectionInsert = "BVOInsert";
    public static String PriceUseInformationInsert = "PriceUseInformationInsert";
    public static String Project_Insert = "ProjectInsert";
    public static String Project_Search = "ProjectSearch";
    public static String Project_Search_Paging = "ProjectSearchPaging";
    public static String PROJECT_ACTIVITY_INSERT = "ProjectActivityInsert";
    public static String PROJECT_ACTIVITY_UPDATE = "ProjectActivityUpdate";
    public static String SITEINSPECTION_GET_EMAIL_FROM_BRANCH = "BVOInfoEmails";
    public static String PricePriceEquipmentInsert = "PriceEquipmentInsert";

    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String OUT_JSON = "/json";
    //public static final String API_KEY = "AIzaSyC03EUCfmYY3tdZ_wWPkL7Bh5G_LmZDJpo";
    public static final String API_KEY = "AIzaSyCuN6kYYXd-CDFHCupQtWFPNThwjtps3Sk";
    public static final String PROJECT_LIST_SHOW = "ProjectListShow";
    public static final String PROJECT_GENERALLY_UPDATE = "ProjectGenerallyUpdate";
    public static final String GET_TODAY_AGENDA = "AgendaToday";
    public static final String GET_TODAY_AGENDA_OTHER = "AgendaDates";
    public static final String GET_WEEKLY_AGENDA = "AgendaWeek";
    public static String AGENDA_CUSTOMER_LIST_SHOW = "AgendaCustomerShow";
    public static String AGENDA_PROJECT_LIST_SHOW = "AgendaProjectListShow";
    public static String AGENDA_CUSTOMER_SHOW = "AgendaCustomerShow";
    public static String AGENDA_ACTIVITY_SHOW = "AgendaActivityShow";
    public static String pricing2PricegGetEntfernung = "getEntfernung";
    public static String pricing3PricegGetTransportPrice = "getTransportPrice";
    public static String GET_VISITING_CARD_DATA = "getVisitingCard";

    public static String getToken()
    {
        long unixTime = System.currentTimeMillis() / 1000L;
        String stringtoConvert = "7ORM054AjZwAS3fJ:" + unixTime;
        byte[] data = stringtoConvert.getBytes();
        return Base64.encodeToString(data, Base64.DEFAULT);
    }
    public static String formatDateToGerman(String date)
    {
        try
        {
            if (date.equals(""))
            {
                return "";
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat outputFormatter = new SimpleDateFormat("dd.MM.yyyy");
            return outputFormatter.format(formatter.parse(date));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return date;
        }
    }
    public static Gson getGson(){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson;
    }
    public static boolean isNetworkAvailable(Context ctx)
    {
        ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public static String formatDateToOriginal(String date)
    {
        try
        {
            if (date.equals(""))
            {
                return "";
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat outputFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            return outputFormatter.format(formatter.parse(date));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return date;
        }
    }

    public static String formatDate(Date day)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        //String date =
        return formatter.format(day);
    }

    public static String formatTimeToOriginal(String date, String time)
    {
        try
        {
            if (date.equals(""))
            {
                return "";
            }
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat outputFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            if (time.equals(""))
            {
                return outputFormatter.format(formatter1.parse(date));
            }
            String finalDate = date + " " + time;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            return outputFormatter.format(formatter.parse(finalDate));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return date;
        }
    }

    public static String getGermanFromEnglishWithoutDecimal(String stringNumber)
    {
        if (stringNumber != null && stringNumber.length() > 0)
        {
            NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.ENGLISH);
            nf_in.setMinimumFractionDigits(0);
            nf_in.setGroupingUsed(true);
            // Log.e("at english from german", stringNumber);
            NumberFormat nf_de = NumberFormat.getInstance(Locale.GERMAN);
            nf_de.setMinimumFractionDigits(0);
            nf_de.setGroupingUsed(true);
            try
            {
                double val = nf_in.parse(stringNumber).doubleValue();
                //Log.e("double return", val+"");
                String string = nf_de.format(val);
                //Log.e("string return", string);
                return string;
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                return "";   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else
        {
            return "0";
        }
    }

    public static String formatTime(String date)
    {
        try
        {
            if (date.equals(""))
            {
                return "";
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            SimpleDateFormat outputFormatter = new SimpleDateFormat("kk:mm");
            return outputFormatter.format(formatter.parse(date));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return date;
        }
    }

    public static String formatTimeWhole(String date)
    {
        try
        {
            if (date.equals(""))
            {
                return "";
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            //SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            SimpleDateFormat outputFormatter = new SimpleDateFormat("HH:mm");
            return outputFormatter.format(formatter.parse(date));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return date;
        }
    }

    public static String formatTime(Date day)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String time = formatter.format(day);
        return time;
    }

    public static boolean yearLessThen2079(String date)
    {
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date yourDate;

        if (date.equals(""))
        {
            return true;
        }
        try
        {
            Date dateToCompare = outputFormatter.parse("01-01-2079 00:00");
            yourDate = outputFormatter.parse(date);
            if (dateToCompare.after(yourDate))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static String formatDisplayDate(Date day)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, E");
        String date = formatter.format(day);
        return date;
    }

    public static String getGermanCurrencyFormat(String stringNumber)
    {
        if (stringNumber != null && stringNumber.length() > 0)
        {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
            nf.setMinimumFractionDigits(2);
            nf.setGroupingUsed(true);
            try
            {
                return nf.format(Double.parseDouble(stringNumber)) + "";
            }
            catch (Exception e)
            {
                return "0,00";   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else
        {
            return "0,00";
        }
    }

    public static String getEnglishCurrencyFormat(String stringNumber)
    {
        if (stringNumber != null && stringNumber.length() > 0)
        {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            nf.setMinimumFractionDigits(2);
            nf.setGroupingUsed(false);
            try
            {
                return nf.format(Double.parseDouble(stringNumber)) + "";
            }
            catch (Exception e)
            {
                return "-1";   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else
        {
            return "0.00";
        }
    }

    public static String getEnglishCurrencyFromGerman(String stringNumber)
    {
        if (stringNumber != null && stringNumber.length() > 0)
        {
            NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.GERMAN);
            nf_in.setMinimumFractionDigits(2);
            nf_in.setGroupingUsed(true);
            NumberFormat nf_us = NumberFormat.getInstance(Locale.US);
            nf_us.setMinimumFractionDigits(2);
            nf_us.setGroupingUsed(false);
            try
            {
                double val;
                if (stringNumber.equals("0.0"))
                {
                    val = nf_in.parse("0,0").doubleValue();
                }
                else
                {
                    val = nf_in.parse(stringNumber).doubleValue();
                }
                String string = nf_us.format(val);
                return string;
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                return "-1";   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else
        {
            return "0.00";
        }
    }


    public static String getEnglishCurrencyFromGermanBvo(String stringNumber)
    {
        if (stringNumber != null && stringNumber.length() > 0)
        {
            NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.GERMAN);
            nf_in.setMinimumFractionDigits(2);
            nf_in.setGroupingUsed(true);
            NumberFormat nf_us = NumberFormat.getInstance(Locale.US);
            nf_us.setMinimumFractionDigits(2);
            nf_us.setGroupingUsed(false);
            try
            {
                double val;
                if (stringNumber.equals("0.0"))
                {
                    val = nf_in.parse("0,0").doubleValue();
                }
                else
                {
                    val = nf_in.parse(stringNumber).doubleValue();
                }
                String string = nf_us.format(val);
                return string;
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                return "0";   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else
        {
            return "0";
        }
    }

    public static String round(String stringNumber)
    {
        if (stringNumber != null && stringNumber.length() > 0)
        {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
            //nf.setMinimumFractionDigits(2);
            nf.setGroupingUsed(true);
            try
            {
                return nf.format(Double.parseDouble(stringNumber)) + "";
            }
            catch (Exception e)
            {
                return "";   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else
        {
            return "";
        }
    }

    public static String getDateFormatFromString(String string, SimpleDateFormat outputFormat, SimpleDateFormat inputFormat)
    {
        try
        {
            String date = outputFormat.format(inputFormat.parse(string));
            return date;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return string;
        }
    }

    public static String getGermanFromEnglish(String stringNumber)
    {
        if (stringNumber != null && stringNumber.length() > 0)
        {
            NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.ENGLISH);
            nf_in.setMinimumFractionDigits(2);
            nf_in.setGroupingUsed(true);
            // Log.e("at english from german", stringNumber);
            NumberFormat nf_de = NumberFormat.getInstance(Locale.GERMAN);
            nf_de.setMinimumFractionDigits(2);
            nf_de.setGroupingUsed(true);
            try
            {
                double val = nf_in.parse(stringNumber).doubleValue();
                //Log.e("double return", val+"");
                String string = nf_de.format(val);
                //Log.e("string return", string);
                return string;
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                return "-1";   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else
        {
            return "0,00";
        }
    }

    public static String getGermanFromEnglishBvo(String stringNumber)
    {
        if (stringNumber != null && stringNumber.length() > 0)
        {
            NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.ENGLISH);
            nf_in.setMinimumFractionDigits(2);
            nf_in.setGroupingUsed(true);
            // Log.e("at english from german", stringNumber);
            NumberFormat nf_de = NumberFormat.getInstance(Locale.GERMAN);
            nf_de.setMinimumFractionDigits(2);
            nf_de.setGroupingUsed(true);
            try
            {
                double val = nf_in.parse(stringNumber).doubleValue();
                //Log.e("double return", val+"");
                String string = nf_de.format(val);
                //Log.e("string return", string);
                return string;
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                return "";   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else
        {
            return "";
        }
    }

    public static void setUpUi(final Activity activity, View view)
    {
        if (!(view instanceof EditText))
        {
            view.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup)
        {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setUpUi(activity, innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        try
        {
            InputMethodManager inputmethodmanager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmethodmanager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e)
        {

        }
    }

    public static void clearAll(Activity activity, View view)
    {
        if (view instanceof EditText)
        {
            ((EditText) view).setText("");
        }
        if (view instanceof ViewGroup)
        {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                View innerView = ((ViewGroup) view).getChildAt(i);
                clearAll(activity, innerView);
            }
        }
    }

    public static boolean isValidBlankMail(String email)
    {
        if (TextUtils.isEmpty(email))
        {
            return true;
        }
        else
        {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isValidMail(String email)
    {
        if (TextUtils.isEmpty(email))
        {
            return false;
        }
        else
        {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
//        if(email.length() == 0)
//        {
//            return true;
//        }
//        boolean check;
//        Pattern p;
//        Matcher m;
//
//        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//        p = Pattern.compile(EMAIL_STRING);
//
//        m = p.matcher(email);
//        check = m.matches();
//        return check;
    }

    public static boolean isValidMobile(String phone)
    {
        if (phone.length() == 0)
        {
            return true;
        }
        if (PhoneNumberUtils.isGlobalPhoneNumber(phone))
        {
            return true;
        }
        else
        {
            return false;
        }
//        boolean check;
//        if(phone.length() < 6 || phone.length() > 13)
//        {
//            check = false;
//        }
//        else
//        {
//            check = true;
//        }
//        return check;
    }

    public static boolean isWebsiteValid(String website)
    {
        if (website.length() == 0)
        {
            return true;
        }
        return Patterns.WEB_URL.matcher(website).matches();
    }

    public static float calculateFileSize(String filepath)
    {
        //String filepathstr=filepath.toString();
        File file = new File(filepath);
        float fileSizeInBytes = file.length();
        float fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        //float fileSizeInMB = fileSizeInKB / 1024;
        //String calString = Float.toString(fileSizeInMB);
        return fileSizeInKB;
    }

    public static boolean isFileSizeGreaterThan10MB(Context context)
    {
        DataBaseHandler db = new DataBaseHandler(context);
        SharedPreferences preferences = context.getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        ArrayList<GridImage> images = db.getPhotos(preferences.getInt(DataHelper.SiteInspectionId, 0));

        float FileSize = 0;
        for (int i = 0; i < images.size(); i++)
        {
            FileSize += calculateFileSize(images.get(i).getPath());
        }
        int bvoSize = ((MatecoPriceApplication) context.getApplicationContext()).getSettingData(Constants.BVOUploadSize, 15) * 1024;
        if (FileSize > bvoSize)
        {
            return true;
        }
        return false;
    }

    public static float getFileSize(Context context)
    {
        DataBaseHandler db = new DataBaseHandler(context);
        SharedPreferences preferences = context.getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        ArrayList<GridImage> images = db.getPhotos(preferences.getInt(DataHelper.SiteInspectionId, 0));

        float FileSize = 0;
        for (int i = 0; i < images.size(); i++)
        {
            FileSize += calculateFileSize(images.get(i).getPath());
        }
        return FileSize;
    }

    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected())
        {
            return true;
        }
        return false;
    }

    public static String connectedVia(Context context)
    {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected())
        {
            return "Wifi";
        }
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mWifi.isConnected())
        {
            return "Mobilei";
        }
        return "Not connected";
    }

    public static boolean phoneNumberValidationGerman(String phoneNumber)
    {
//        Pattern p = Pattern.compile("^\\+[1-9]{1}[0-9]{3,14}$");
//        Matcher m = p.matcher(phoneNumber);
//        Log.i("tag", "matches? " + m.matches());
//        return m.matches();
        //^\+[1-9]{1}[0-9]{3,14}$
        if (phoneNumber.startsWith("+"))
        {
            return true;
        }
        return false;
    }

    public static boolean phoneNumberValidationWithoutBlankGerman(String phoneNumber)
    {
        if (phoneNumber.length() > 0)
        {
            return phoneNumberValidationGerman(phoneNumber);
        }
        return true;
    }

    public static ArrayList<BasicNameValuePair> getLatLongFromAddress(Context context, String inputAddress)
    {
        ArrayList<BasicNameValuePair> latLongs = new ArrayList<>();
        try
        {
            Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
            List<Address> addressList = geoCoder.getFromLocationName(inputAddress, 1);
            if (addressList != null && addressList.size() > 0)
            {
                Address address = addressList.get(0);
                latLongs.add(new BasicNameValuePair("latitude", address.getLatitude() + ""));
                latLongs.add(new BasicNameValuePair("longitude", address.getLongitude() + ""));
            }
            return latLongs;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return latLongs;
    }

    public static String generateSignedUrl(String basicUrl)
    {
        String keyString = "p6Bw1-LqWnqF-T4fn5LT71WFMeg=";
        UrlSigner signer = null;
        try
        {
            URL url = new URL(basicUrl);
            signer = new UrlSigner(keyString);
            String request = signer.signRequest(url.getPath(), url.getQuery());
            Log.e("url geenerated", "Signed URL :" + url.getProtocol() + "://" + url.getHost() + request);
            return url.getProtocol() + "://" + url.getHost() + request;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        return "error";
    }

//    public static String getFormattedLocationInDegree(double latitude, double longitude)
//    {
//        try
//        {
//            int latSeconds = (int) Math.round(latitude * 3600);
//            int latDegrees = latSeconds / 3600;
//            latSeconds = Math.abs(latSeconds % 3600);
//            int latMinutes = latSeconds / 60;
//            latSeconds %= 60;
//            int longSeconds = (int) Math.round(longitude * 3600);
//            int longDegrees = longSeconds / 3600;
//            longSeconds = Math.abs(longSeconds % 3600);
//            int longMinutes = longSeconds / 60;
//            longSeconds %= 60;
//            String latDegree = latDegrees >= 0 ? "N" : "S";
//            String lonDegrees = latDegrees >= 0 ? "E" : "W";
//            String symbol = context.getResources().getString(R.string.degreeSymbol);
//            return Math.abs(latDegrees) + symbol + latMinutes + "'" + latSeconds
//                    + "\"" + latDegree + " " + Math.abs(longDegrees) + symbol + longMinutes
//                    + "'" + longSeconds + "\"" + lonDegrees;
//        }
//        catch (Exception e)
//        {
//            return "" + String.format("%8.5f", latitude) + "  " + String.format("%8.5f", longitude);
//        }
//    }

    public static BasicNameValuePair getFormattedLocationInDegree(Context context, double latitude, double longitude)
    {
        try
        {
            int latSeconds = (int) Math.round(latitude * 3600);
            int latDegrees = latSeconds / 3600;
            latSeconds = Math.abs(latSeconds % 3600);
            int latMinutes = latSeconds / 60;
            latSeconds %= 60;
            int longSeconds = (int) Math.round(longitude * 3600);
            int longDegrees = longSeconds / 3600;
            longSeconds = Math.abs(longSeconds % 3600);
            int longMinutes = longSeconds / 60;
            longSeconds %= 60;
            String latDegree = latDegrees >= 0 ? "N" : "S";
            String lonDegrees = latDegrees >= 0 ? "E" : "W";
            String symbol = context.getResources().getString(R.string.degreeSymbol);
            BasicNameValuePair nameValuePair = new BasicNameValuePair(Math.abs(latDegrees) + symbol + latMinutes + "'" + latSeconds
                    + "\"" + latDegree, Math.abs(longDegrees) + symbol + longMinutes
                    + "'" + longSeconds + "\"" + lonDegrees);
//            return Math.abs(latDegrees) + symbol + latMinutes + "'" + latSeconds
//                    + "\"" + latDegree + " " + Math.abs(longDegrees) + symbol + longMinutes
//                    + "'" + longSeconds + "\"" + lonDegrees;
            return nameValuePair;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            BasicNameValuePair nameValuePair = new BasicNameValuePair(String.format("%8.5f", latitude), String.format("%8.5f", longitude));
            return nameValuePair;
            //return "" + String.format("%8.5f", latitude) + "  " + String.format("%8.5f", longitude);
        }
    }

    public static String getPath(final Context context, final Uri uri)
    {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri))
        {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri))
            {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type))
                {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri))
            {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri))
            {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type))
                {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("video".equals(type))
                {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("audio".equals(type))
                {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme()))
        {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme()))
        {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs)
    {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try
        {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst())
            {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri)
    {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri)
    {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri)
    {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}