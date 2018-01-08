package de.mateco.integrAMobile.model;

/**
 * Created by SSoft-13 on 17-01-2017.
 */

public class PriceInfoModelClass {
    int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStrJson() {
        return strJson;
    }

    public void setStrJson(String strJson) {
        this.strJson = strJson;
    }

    String strJson="";
}
