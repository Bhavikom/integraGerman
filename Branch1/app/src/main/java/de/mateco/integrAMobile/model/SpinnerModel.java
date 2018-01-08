package de.mateco.integrAMobile.model;

/**
 * Created by mmehta on 31.05.2016.
 */
public class SpinnerModel
{

    private  String CompanyName="";
    private  String Image="";
    private  String Url="";

    /*********** Set Methods ******************/
    public void setCompanyName(String CompanyName)
    {
        this.CompanyName = CompanyName;
    }


    /*********** Get Methods ****************/
    public String getCompanyName()
    {
        return this.CompanyName;
    }


}