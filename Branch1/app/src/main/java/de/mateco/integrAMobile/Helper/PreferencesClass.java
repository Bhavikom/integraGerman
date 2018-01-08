package de.mateco.integrAMobile.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by mmehta on 01.06.2016.
 */
public class PreferencesClass
{
    //static  SharedPreferences prefereces;


    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    private static final String PREF_NAME = "PRICED";

    // Context
    Context context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Constructor
    public PreferencesClass(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public static void storePriceData(Context context,AddPriceParsableClass parsable)
    {
        //prefereces = PreferenceManager.getDefaultSharedPreferences(context);
        //editor = prefereces.edit();

        editor.putString("isAnlieferung", String.valueOf(parsable.flagAnlieferung));
        editor.putString("isKann", String.valueOf(parsable.flagKann));
        editor.putString("isLieferung", String.valueOf(parsable.flagLieferung));
        editor.putString("isVoranmeldung", String.valueOf(parsable.flagVoranmeldung));
        editor.putString("isBenachrichtgung", String.valueOf(parsable.flagBenachrichtgung));
        editor.putString("isRampena", String.valueOf(parsable.flagRampena));
        editor.putString("isSonstige", String.valueOf(parsable.flagSonstige));
        editor.putString("isEinweisung", String.valueOf(parsable.flagEinweisung));
        editor.putString("isSelbstfahrer", String.valueOf(parsable.flagSelbstfahrer));

        editor.putString("Kann", String.valueOf(parsable.strKann));
        editor.putString("Voranmeldung", String.valueOf(parsable.strVoranmeldung));
        editor.putString("Benachrich", String.valueOf(parsable.strBenachrich));
        editor.putString("Sonstige", String.valueOf(parsable.strSonstige));

        editor.putString("Ladefahrzeug", String.valueOf(parsable.intSpinneritem));
        editor.putString("startDate", String.valueOf(parsable.strStartDate));
        editor.putString("startTime", String.valueOf(parsable.strStartTime));
        editor.putString("endDate", String.valueOf(parsable.strEndDate));
        editor.putString("endTime", String.valueOf(parsable.strEndtime));

        editor.commit();
    }
    public void clearDates(){

        try {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = sharedPreferences.edit();
            editor.putString("startDate","");
            editor.putString("startTime","");
            editor.putString("endDate", "");
            editor.putString("endTime", "");
            editor.commit();

            editor.clear().commit();

            Log.e(" in try  "," exception while clearing dates : "+sharedPreferences.getString("startDate",""));


        }
        catch (Exception e){
            Log.e(" @@@@@@@@ "," exception while clearing dates : "+e.toString());
        }

    }

    public void clearPreferences(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        //sharedPreferences.edit().clear().commit();
       // prefereces = PreferenceManager.getDefaultSharedPreferences(context);
        //editor = prefereces.edit();

        /*sharedPreferences.edit().remove("isAnlieferung").apply();
        sharedPreferences.edit().remove("isKann").apply();
        sharedPreferences.edit().remove("isLieferung").apply();
        sharedPreferences.edit().remove("isVoranmeldung").apply();
        sharedPreferences.edit().remove("isBenachrichtgung").apply();
        sharedPreferences.edit().remove("isRampena").apply();
        sharedPreferences.edit().remove("isSonstige").apply();
        sharedPreferences.edit().remove("isEinweisung").apply();
        sharedPreferences.edit().remove("isSelbstfahrer").apply();
        sharedPreferences.edit().remove("Kann").apply();
        sharedPreferences.edit().remove("Voranmeldung").apply();
        sharedPreferences.edit().remove("Benachrich").apply();
        sharedPreferences.edit().remove("Sonstige").apply();
        sharedPreferences.edit().remove("Ladefahrzeug").apply();
        sharedPreferences.edit().remove("startDate").apply();
        sharedPreferences.edit().remove("startTime").apply();
        sharedPreferences.edit().remove("endDate").apply();
        sharedPreferences.edit().remove("endTime").apply();
        sharedPreferences.edit().remove("position_combo").apply();*/



    }
    public void startDate(String date){
        editor.putString("start_date",date);
        editor.commit();
    }
    public void endDate(String date){
        editor.putString("end_date",date);
        editor.commit();
    }
    public void startTime(String time){
        editor.putString("start_time",time);
        editor.commit();
    }
    public void endTime(String time){
        editor.putString("end_time",time);
        editor.commit();
    }
    public String getstartDate(){
        return  sharedPreferences.getString("start_date","");
    }
    public String getendDate(){
        return  sharedPreferences.getString("end_date","");
    }
    public String getstartTime(){
        return  sharedPreferences.getString("start_time","");
    }
    public String getendTime(){
        return  sharedPreferences.getString("end_time","");
    }
    public void clearstartDate(){
        sharedPreferences.edit().remove("start_date").commit();
    }
    public void clearendDate(){
        sharedPreferences.edit().remove("end_date").commit();
    }
    public void clearstartTime(){
        sharedPreferences.edit().remove("start_time").commit();
    }
    public void clearendTime(){
        sharedPreferences.edit().remove("end_time").commit();

    }

    public void saveComboboxPos(int pos){
        editor.putInt("position_combo",pos);
        editor.commit();
    }
    public int getComboboxPos(){
        return  sharedPreferences.getInt("position_combo",0);
    }
    public void savePosition(String pos)
    {
        editor.putString("position",pos);
        editor.commit();
    }
    public String getPosition(){
        return  sharedPreferences.getString("position","");
    }
    public void saveFirm(String pos)
    {
        editor.putString("firm",pos);
        editor.commit();
    }
    public String getFirm(){
        return  sharedPreferences.getString("firm","");
    }
    public void saveAddress(String addresss)
    {
        editor.putString("address",addresss);
        editor.commit();
    }
    public String getAddress(){
        return  sharedPreferences.getString("address","");
    }

    public void setisPrice(String str)
    {
        editor.putString("is_price",str);
        editor.commit();
    }
    public String getisPrice(){
        return  sharedPreferences.getString("is_price","");
    }
    public void clearisPrice(){
        sharedPreferences.edit().remove("is_price").commit();
    }

    public void clearCombobox(){
        sharedPreferences.edit().remove("position_combo").commit();
    }

    public void savePriceDevice(String priceDevice)
    {
        editor.putString("price_device",priceDevice);
        editor.commit();
    }
    public String getPriceDevice()
    {
        return sharedPreferences.getString("price_device","");
    }
    public void saveBranchId(int id){
        editor.putInt("branchId", id);
        editor.commit();
    }
    public int getBranchId(){
        return sharedPreferences.getInt("branchId", 0);
    }
    public void saveComeFrom(String comefrom){
        editor.putString("comefrom", comefrom);
        editor.commit();
    }
    public String getComefrom(){
        return sharedPreferences.getString("comefrom","");
    }


    public AddPriceParsableClass getPriceData(Context context)
    {
        AddPriceParsableClass pref = new AddPriceParsableClass();
        try {
            //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

            pref.flagAnlieferung = sharedPreferences.getString("isAnlieferung", "");
            pref.flagKann = sharedPreferences.getString("isKann", "");
            pref.flagLieferung = sharedPreferences.getString("isLieferung", "");
            pref.flagVoranmeldung = sharedPreferences.getString("isVoranmeldung", "");
            pref.flagBenachrichtgung = sharedPreferences.getString("isBenachrichtgung", "");
            pref.flagRampena = sharedPreferences.getString("isRampena", "");
            pref.flagSonstige = sharedPreferences.getString("isSonstige", "");
            pref.flagEinweisung = sharedPreferences.getString("isEinweisung", "");
            pref.flagSelbstfahrer = sharedPreferences.getString("isSelbstfahrer", "");

            pref.strKann = sharedPreferences.getString("Kann", "");
            pref.strVoranmeldung = sharedPreferences.getString("Voranmeldung", "");
            pref.strBenachrich = sharedPreferences.getString("Benachrich", "");
            pref.strSonstige = sharedPreferences.getString("Sonstige", "");

            pref.intSpinneritem= Integer.parseInt(sharedPreferences.getString("Ladefahrzeug", ""));
            pref.strStartDate = sharedPreferences.getString("startDate", "");
            pref.strStartTime = sharedPreferences.getString("startTime", "");
            pref.strEndDate = sharedPreferences.getString("endDate", "");
            pref.strEndtime = sharedPreferences.getString("endTime", "");





            return pref;
        }
        catch (Exception e){
            return pref;
        }

    }

}

