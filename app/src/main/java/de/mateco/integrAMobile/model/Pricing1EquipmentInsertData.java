package de.mateco.integrAMobile.model;

/**
 * Created by BrijeshChavda on 2/28/2015.
 */
public class Pricing1EquipmentInsertData {

    String Warenkorb;
    int Ausstattung;
    double Preis;



    public Pricing1EquipmentInsertData() {
    }


    public String getWarenkorb() {
        return Warenkorb;
    }

    public void setWarenkorb(String warenkorb) {
        Warenkorb = warenkorb;
    }

    public int getAusstattung() {
        return Ausstattung;
    }

    public void setAusstattung(int ausstattung) {
        Ausstattung = ausstattung;
    }

    public double getPreis() {
        return Preis;
    }

    public void setPreis(double preis) {
        Preis = preis;
    }
}
