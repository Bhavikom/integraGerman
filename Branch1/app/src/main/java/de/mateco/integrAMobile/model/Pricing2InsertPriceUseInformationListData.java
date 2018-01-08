package de.mateco.integrAMobile.model;

/**
 * Created by BrijeshChavda on 2/28/2015.
 */
public class Pricing2InsertPriceUseInformationListData
{
    int EinsatzLand;
    String EinsatzStrasse;
    String EinsatzPLZ;
    String Einsatzort;
    String Projekt ;
    String Zusatz;
    String AVO ;
    String AVOTelefon;
    int Mautkilometer ;
    int Entfernung;
    String UserID;

    public int getEinsatzLand() {
        return EinsatzLand;
    }

    public void setEinsatzLand(int einsatzLand) {
        EinsatzLand = einsatzLand;
    }

    public String getEinsatzStrasse() {
        return EinsatzStrasse;
    }

    public void setEinsatzStrasse(String einsatzStrasse) {
        EinsatzStrasse = einsatzStrasse;
    }

    public String getEinsatzPLZ() {
        return EinsatzPLZ;
    }

    public void setEinsatzPLZ(String einsatzPLZ) {
        EinsatzPLZ = einsatzPLZ;
    }

    public String getEinsatzort() {
        return Einsatzort;
    }

    public void setEinsatzort(String einsatzort) {
        Einsatzort = einsatzort;
    }

    public String getProjekt() {
        return Projekt;
    }

    public void setProjekt(String projekt) {
        Projekt = projekt;
    }

    public String getZusatz() {
        return Zusatz;
    }

    public void setZusatz(String zusatz) {
        Zusatz = zusatz;
    }

    public String getAVO() {
        return AVO;
    }

    public void setAVO(String AVO) {
        this.AVO = AVO;
    }

    public String getAVOTelefon() {
        return AVOTelefon;
    }

    public void setAVOTelefon(String AVOTelefon) {
        this.AVOTelefon = AVOTelefon;
    }

    public int getMautkilometer() {
        return Mautkilometer;
    }

    public void setMautkilometer(int mautkilometer) {
        Mautkilometer = mautkilometer;
    }

    public int getEntfernung() {
        return Entfernung;
    }

    public void setEntfernung(int entfernung) {
        Entfernung = entfernung;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
