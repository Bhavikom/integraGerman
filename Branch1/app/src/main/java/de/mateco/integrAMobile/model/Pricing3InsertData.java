package de.mateco.integrAMobile.model;

/**
 * Created by BrijeshChavda on 2/28/2015.
 */
public class Pricing3InsertData {
    boolean isGenehmigungsgebiet;

    public boolean isGenehmigungsgebiet() {
        return isGenehmigungsgebiet;
    }

    public void setGenehmigungsgebiet(boolean genehmigungsgebiet) {
        isGenehmigungsgebiet = genehmigungsgebiet;
    }

    int AngebotSuffix;
    String Kontakt;
    String KundenNr;
    int Mandant;
    int Warenkorbart;
    String Hoehengruppe;
    int Einheit_Mietdauer;
    int Mietdauer;
    String Mietpreis;
    double Standtag;
    String Selbstbehalt;
    int HandlingFee;
    int ServicePauschale;
    double Versicherung;
    int WochenendeMitversichert;
    double TransportAnfahrt;
    double TransportPauschal;
    double TransportAbfahrt;
    double Beiladungspauschale;

    String Einsatzinformation;
    String Besteller;
    String Besteller_Telefon;
    String Besteller_Email;
    String Notiz, strDateList;

    public String getStrDateList() {
        return strDateList;
    }

    public void setStrDateList(String strDateList) {
        this.strDateList = strDateList;
    }

    int Ersteller;
    int KaNr;
    String AnsPartner;
    String Besteller_Anrede;
    String Besteller_Mobil;
    String Mautkilometer;
    int Winterreifenpauschale;
    int Bearbeitet;
    int Kalendertage;
    String Referenz;
    String UserID;
    String Geraetetyp;
    String jsonOfEqu;


    String branch;
    String hGRP;
    String deviceType;
    String manufacturer;

    String type;
    int md;
    double rentalPrice;
    double sb;

    String hfStatus;
    String spStatus;
    double hb;
    String best;
    String CustomerNr;

    boolean flagAnlieferung, isKann, isLieferung, isVoranmeldung, isBenachrichtgung, isRampena, isSonstige,
            isEinweisung, isSelbstfahrer;
    String strKann ="", strVoranmeldung ="", strBenachrich ="", strSonstige ="";
    String strAVO="", strAVOMobile ="";

    public String getStrAVO() {
        return strAVO;
    }

    public void setStrAVO(String strAVO) {
        this.strAVO = strAVO;
    }

    public String getStrAVOMobile() {
        return strAVOMobile;
    }

    public void setStrAVOMobile(String strAVOMobile) {
        this.strAVOMobile = strAVOMobile;
    }

    public boolean isKann() {
        return isKann;
    }

    public void setKann(boolean kann) {
        this.isKann = kann;
    }

    public boolean isLieferung() {
        return isLieferung;
    }

    public void setLieferung(boolean lieferung) {
        this.isLieferung = lieferung;
    }

    public boolean isVoranmeldung() {
        return isVoranmeldung;
    }

    public void setVoranmeldung(boolean voranmeldung) {
        this.isVoranmeldung = voranmeldung;
    }

    public boolean isBenachrichtgung() {
        return isBenachrichtgung;
    }

    public void setBenachrichtgung(boolean benachrichtgung) {
        this.isBenachrichtgung = benachrichtgung;
    }

    public boolean isRampena() {
        return isRampena;
    }

    public void setRampena(boolean rampena) {
        this.isRampena = rampena;
    }

    public boolean isSonstige() {
        return isSonstige;
    }

    public void setSonstige(boolean sonstige) {
        this.isSonstige = sonstige;
    }

    public boolean isEinweisung() {
        return isEinweisung;
    }

    public void setEinweisung(boolean einweisung) {
        this.isEinweisung = einweisung;
    }

    public boolean isSelbstfahrer() {
        return isSelbstfahrer;
    }

    public void setSelbstfahrer(boolean selbstfahrer) {
        this.isSelbstfahrer = selbstfahrer;
    }

    public String getStrKann() {
        return strKann;
    }

    public void setStrKann(String strKann) {
        this.strKann = strKann;
    }

    public String getStrVoranmeldung() {
        return strVoranmeldung;
    }

    public void setStrVoranmeldung(String strVoranmeldung) {
        this.strVoranmeldung = strVoranmeldung;
    }

    public String getStrBenachrich() {
        return strBenachrich;
    }

    public void setStrBenachrich(String strBenachrich) {
        this.strBenachrich = strBenachrich;
    }

    public String getStrSonstige() {
        return strSonstige;
    }

    public void setStrSonstige(String strSonstige) {
        this.strSonstige = strSonstige;
    }

    public String getStrstartDate() {
        return strstartDate;
    }

    public void setStrstartDate(String strstartDate) {
        this.strstartDate = strstartDate;
    }

    public String getStrendDate() {
        return strendDate;
    }

    public void setStrendDate(String strendDate) {
        this.strendDate = strendDate;
    }

    public String getStrstartTime() {
        return strstartTime;
    }

    public void setStrstartTime(String strstartTime) {
        this.strstartTime = strstartTime;
    }

    public String getStrendTime() {
        return strendTime;
    }

    public void setStrendTime(String strendTime) {
        this.strendTime = strendTime;
    }

    public int getintLadeiahrzeug() {
        return intLadefahrzeug;
    }

    public void setintLadeiahrzeug(int intLadeiahrzeug) {
        this.intLadefahrzeug = intLadeiahrzeug;
    }

    public boolean isFlagAnlieferung() {
        return flagAnlieferung;
    }

    public void setFlagAnlieferung(boolean flagAnlieferung) {
        this.flagAnlieferung = flagAnlieferung;
    }

    String strstartDate ="", strendDate ="", strstartTime ="", strendTime ="";
    int intLadefahrzeug =0;

    public Pricing3InsertData() {
    }

    public String getJsonOfEqu() {
        return jsonOfEqu;
    }

    public void setJsonOfEqu(String jsonOfEqu) {
        this.jsonOfEqu = jsonOfEqu;
    }

    public String getGeraetetyp() {
        return Geraetetyp;
    }

    public void setGeraetetyp(String geraetetyp) {
        Geraetetyp = geraetetyp;
    }

    public int getAngebotSuffix() {
        return AngebotSuffix;
    }

    public void setAngebotSuffix(int angebotSuffix) {
        int i = angebotSuffix + 1;
        AngebotSuffix = i;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getKundenNr() {
        return KundenNr;
    }

    public void setKundenNr(String kundenNr) {
        KundenNr = kundenNr;
    }

    public int getMandant() {
        return Mandant;
    }

    public void setMandant(int mandant) {
        Mandant = mandant;
    }

    public int getWarenkorbart() {
        return Warenkorbart;
    }

    public void setWarenkorbart(int warenkorbart) {
        Warenkorbart = warenkorbart;
    }

    public String getHoehengruppe() {
        return Hoehengruppe;
    }

    public void setHoehengruppe(String hoehengruppe) {
        Hoehengruppe = hoehengruppe;
    }

    public int getEinheit_Mietdauer() {
        return Einheit_Mietdauer;
    }

    public void setEinheit_Mietdauer(int einheit_Mietdauer) {
        Einheit_Mietdauer = einheit_Mietdauer;
    }

    public int getMietdauer() {
        return Mietdauer;
    }

    public void setMietdauer(int mietdauer) {
        Mietdauer = mietdauer;
    }

    public String getMietpreis() {
        return Mietpreis;
    }

    public void setMietpreis(String mietpreis) {
        Mietpreis = mietpreis;
    }

    public double getStandtag() {
        return Standtag;
    }

    public void setStandtag(double standtag) {
        Standtag = standtag;
    }

    public String getSelbstbehalt() {
        return Selbstbehalt;
    }

    public void setSelbstbehalt(String selbstbehalt) {
        Selbstbehalt = selbstbehalt;
    }

    public int getHandlingFee() {
        return HandlingFee;
    }

    public void setHandlingFee(int handlingFee) {
        HandlingFee = handlingFee;
    }

    public int getServicePauschale() {
        return ServicePauschale;
    }

    public void setServicePauschale(int servicePauschale) {
        ServicePauschale = servicePauschale;
    }

    public double getVersicherung() {
        return Versicherung;
    }

    public void setVersicherung(double versicherung) {
        Versicherung = versicherung;
    }

    public int getWochenendeMitversichert() {
        return WochenendeMitversichert;
    }

    public void setWochenendeMitversichert(int wochenendeMitversichert) {
        WochenendeMitversichert = wochenendeMitversichert;
    }

    public double getTransportAnfahrt() {
        return TransportAnfahrt;
    }

    public void setTransportAnfahrt(double transportAnfahrt) {
        TransportAnfahrt = transportAnfahrt;
    }

    public double getTransportPauschal() {
        return TransportPauschal;
    }

    public void setTransportPauschal(double transportPauschal) {
        TransportPauschal = transportPauschal;
    }

    public double getTransportAbfahrt() {
        return TransportAbfahrt;
    }

    public void setTransportAbfahrt(double transportAbfahrt) {
        TransportAbfahrt = transportAbfahrt;
    }

    public double getBeiladungspauschale() {
        return Beiladungspauschale;
    }

    public void setBeiladungspauschale(double beiladungspauschale) {
        Beiladungspauschale = beiladungspauschale;
    }

    public String getEinsatzinformation() {
        return Einsatzinformation;
    }

    public void setEinsatzinformation(String einsatzinformation) {
        Einsatzinformation = einsatzinformation;
    }

    public String getBesteller() {
        return Besteller;
    }

    public void setBesteller(String besteller) {
        Besteller = besteller;
    }

    public String getBesteller_Telefon() {
        return Besteller_Telefon;
    }

    public void setBesteller_Telefon(String besteller_Telefon) {
        Besteller_Telefon = besteller_Telefon;
    }

    public String getBesteller_Email() {
        return Besteller_Email;
    }

    public void setBesteller_Email(String besteller_Email) {
        Besteller_Email = besteller_Email;
    }

    public String getNotiz() {
        return Notiz;
    }

    public void setNotiz(String notiz) {
        Notiz = notiz;
    }

    public int getErsteller() {
        return Ersteller;
    }

    public void setErsteller(int ersteller) {
        Ersteller = ersteller;
    }

    public int getKaNr() {
        return KaNr;
    }

    public void setKaNr(int kaNr) {
        KaNr = kaNr;
    }

    public String getAnsPartner() {
        return AnsPartner;
    }

    public void setAnsPartner(String ansPartner) {
        AnsPartner = ansPartner;
    }

    public String getBesteller_Anrede() {
        return Besteller_Anrede;
    }

    public void setBesteller_Anrede(String besteller_Anrede) {
        Besteller_Anrede = besteller_Anrede;
    }

    public String getBesteller_Mobil() {
        return Besteller_Mobil;
    }

    public void setBesteller_Mobil(String besteller_Mobil) {
        Besteller_Mobil = besteller_Mobil;
    }

    public String getMautkilometer() {
        return Mautkilometer;
    }

    public void setMautkilometer(String mautkilometer) {
        Mautkilometer = mautkilometer;
    }

    public int getWinterreifenpauschale() {
        return Winterreifenpauschale;
    }

    public void setWinterreifenpauschale(int winterreifenpauschale) {
        Winterreifenpauschale = winterreifenpauschale;
    }

    public int getBearbeitet() {
        return Bearbeitet;
    }

    public void setBearbeitet(int bearbeitet) {
        Bearbeitet = bearbeitet;
    }

    public int getKalendertage() {
        return Kalendertage;
    }

    public void setKalendertage(int kalendertage) {
        Kalendertage = kalendertage;
    }

    public String getReferenz() {
        return Referenz;
    }

    public void setReferenz(String referenz) {
        Referenz = referenz;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String gethGRP() {
        return hGRP;
    }

    public void sethGRP(String hGRP) {
        this.hGRP = hGRP;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMd() {
        return md;
    }

    public void setMd(int md) {
        this.md = md;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public double getSb() {
        return sb;
    }

    public void setSb(double sb) {
        this.sb = sb;
    }

    public String getHfStatus() {
        return hfStatus;
    }

    public void setHfStatus(String hfStatus) {
        this.hfStatus = hfStatus;
    }

    public String getSpStatus() {
        return spStatus;
    }

    public void setSpStatus(String spStatus) {
        this.spStatus = spStatus;
    }

    public double getHb() {
        return hb;
    }

    public void setHb(double hb) {
        this.hb = hb;
    }

    public String getBest() {
        return best;
    }

    public void setBest(String best) {
        this.best = best;
    }

    public String getCustomerNr() {
        return CustomerNr;
    }

    public void setCustomerNr(String customerNr) {
        this.CustomerNr = customerNr;
    }
}
