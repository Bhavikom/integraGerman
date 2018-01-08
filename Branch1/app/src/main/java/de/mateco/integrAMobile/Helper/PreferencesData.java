package de.mateco.integrAMobile.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mmehta on 19.07.2016.
 */
public class PreferencesData {
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    private static final String PREF_NAME = "data";
    // Context
    Context context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Constructor
    public PreferencesData(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void saveFunction(String pos)
    {
        editor.putString("position",pos);
        editor.commit();
    }
    public String getFunction(){
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
    public void setisCustomerSelected(String value)
    {
        editor.putString("is_customer_selected", value);
        editor.commit();
    }
    public String getisCustomerSelected()
    {
        return sharedPreferences.getString("is_customer_selected","");
    }
    public void saveEinzatzInfo(String str)
    {
        editor.putString("einzatz_info", str);
        editor.commit();
    }
    public String getEinzatzInfo()
    {
        return sharedPreferences.getString("einzatz_info","");
    }
    public void clearEinztatInfo(){
        sharedPreferences.edit().remove("einzatz_info").commit();
    }


    public void saveIsfirstTime(boolean flag)
    {
        editor.putBoolean("first_time",flag);
        editor.commit();
    }
    public Boolean isFirstTime(){
        return  sharedPreferences.getBoolean("first_time",true);
    }

}
