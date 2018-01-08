package de.mateco.integrAMobile.model;

/**
 * Created by BrijeshChavda on 2/28/2015.
 */
public class Pricing3LostSaleInsertData
{
    String Warenkorb;
    int Absagegrund;
    double Preis;
    String Wettbewerber;
    String Notiz;



    public Pricing3LostSaleInsertData() {
    }

    public String getWarenkorb() {
        return Warenkorb;
    }

    public void setWarenkorb(String warenkorb) {
        Warenkorb = warenkorb;
    }

    public int getAbsagegrund() {
        return Absagegrund;
    }

    public void setAbsagegrund(int absagegrund) {
        Absagegrund = absagegrund;
    }

    public double getPreis() {
        return Preis;
    }

    public void setPreis(double preis) {
        Preis = preis;
    }

    public String getWettbewerber() {
        return Wettbewerber;
    }

    public void setWettbewerber(String wettbewerber) {
        Wettbewerber = wettbewerber;
    }

    public String getNotiz() {
        return Notiz;
    }

    public void setNotiz(String notiz) {
        Notiz = notiz;
    }
}
