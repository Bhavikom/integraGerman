package de.mateco.integrAMobile.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import de.mateco.integrAMobile.model_logonsquare.CustomerContactPersonFeatureListItem;

public class ContactPersonModel
{
    private String Anrede, AnredeID, Anspartner, Ausgeschieden, Belegsprache, Email, Entscheider, EntscheiderID, Funktion, FunktionID,
            Kontakt, Mobil, Nachname, Telefax, Telefon, Titel, Vorname, Zusatzinfo;

    private ArrayList<CustomerContactPersonFeatureListItem> Merkmal = new ArrayList<>();

    public ArrayList<CustomerContactPersonFeatureListItem> getFeatureList() {
        return Merkmal;
    }

    public void setFeatureList(ArrayList<CustomerContactPersonFeatureListItem> Merkmal) {
        this.Merkmal = Merkmal;
    }

    public String getAnrede() {
        return Anrede;
    }

    public void setAnrede(String anrede) {
        Anrede = anrede;
    }

    public String getAnredeID() {
        return AnredeID;
    }

    public void setAnredeID(String anredeID) {
        AnredeID = anredeID;
    }

    public String getAnspartner() {
        return Anspartner;
    }

    public void setAnspartner(String anspartner) {
        Anspartner = anspartner;
    }

    public String getAusgeschieden() {
        return Ausgeschieden;
    }

    public void setAusgeschieden(String ausgeschieden) {
        Ausgeschieden = ausgeschieden;
    }

    public String getBelegsprache() {
        return Belegsprache;
    }

    public void setBelegsprache(String belegsprache) {
        Belegsprache = belegsprache;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEntscheider() {
        return Entscheider;
    }

    public void setEntscheider(String entscheider) {
        Entscheider = entscheider;
    }

    public String getEntscheiderID() {
        return EntscheiderID;
    }

    public void setEntscheiderID(String entscheiderID) {
        EntscheiderID = entscheiderID;
    }

    public String getFunktion() {
        return Funktion;
    }

    public void setFunktion(String funktion) {
        Funktion = funktion;
    }

    public String getFunktionID() {
        return FunktionID;
    }

    public void setFunktionID(String funktionID) {
        FunktionID = funktionID;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getMobil() {
        return Mobil;
    }

    public void setMobil(String mobil) {
        Mobil = mobil;
    }

    public String getNachname() {
        return Nachname;
    }

    public void setNachname(String nachname) {
        Nachname = nachname;
    }

    public String getTelefax() {
        return Telefax;
    }

    public void setTelefax(String telefax) {
        Telefax = telefax;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getTitel() {
        return Titel;
    }

    public void setTitel(String titel) {
        Titel = titel;
    }

    public String getVorname() {
        return Vorname;
    }

    public void setVorname(String vorname) {
        Vorname = vorname;
    }

    public String getZusatzinfo() {
        return Zusatzinfo;
    }

    public void setZusatzinfo(String zusatzinfo) {
        Zusatzinfo = zusatzinfo;
    }

    public static void extractFromJson(String jsonString, ArrayList<ContactPersonModel> contactPersons)
    {
        Gson gson = new Gson();
        Type typedValue = new TypeToken<ArrayList<ContactPersonModel>>(){}.getType();
        ArrayList<ContactPersonModel> contactPersonList = gson.fromJson(jsonString, typedValue);
        contactPersons.addAll(contactPersonList);
    }
}
