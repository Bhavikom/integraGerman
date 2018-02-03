package de.mateco.integrAMobile.model_logonsquare;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CustomerActivityEmployeeListItem{

	@SerializedName("SVNummer")
	@JsonField(name ="SVNummer")
	private String sVNummer;

	@SerializedName("Geschuetzt")
	@JsonField(name ="Geschuetzt")
	private String geschuetzt;

	@SerializedName("RowID")
	@JsonField(name ="RowID")
	private String rowID;

	@SerializedName("Ausgeschieden")
	@JsonField(name ="Ausgeschieden")
	private String ausgeschieden;

	@SerializedName("EMail")
	@JsonField(name ="EMail")
	private String eMail;

	@SerializedName("Anrede")
	@JsonField(name ="Anrede")
	private String anrede;

	@SerializedName("Ort")
	@JsonField(name ="Ort")
	private String ort;

	@SerializedName("Personalnummer")
	@JsonField(name ="Personalnummer")
	private String personalnummer;

	@SerializedName("Aktiv")
	@JsonField(name ="Aktiv")
	private String aktiv;

	@SerializedName("Gebiet")
	@JsonField(name ="Gebiet")
	private String gebiet;

	@SerializedName("Hierarchie")
	@JsonField(name ="Hierarchie")
	private String hierarchie;

	@SerializedName("Zeichnung")
	@JsonField(name ="Zeichnung")
	private String zeichnung;

	@SerializedName("Mobil")
	@JsonField(name ="Mobil")
	private String mobil;

	@SerializedName("Telefax")
	@JsonField(name ="Telefax")
	private String telefax;

	@SerializedName("Nachname")
	@JsonField(name ="Nachname")
	private String nachname;

	@SerializedName("Benutzername")
	@JsonField(name ="Benutzername")
	private String benutzername;

	@SerializedName("Funktion")
	@JsonField(name ="Funktion")
	private String funktion;

	@SerializedName("Mitarbeiter")
	@JsonField(name ="Mitarbeiter")
	private String mitarbeiter;

	@SerializedName("Telefon")
	@JsonField(name ="Telefon")
	private String telefon;

	@SerializedName("NL")
	@JsonField(name ="NL")
	private String nL;

	@SerializedName("Vorgesetzter")
	@JsonField(name ="Vorgesetzter")
	private String vorgesetzter;

	@SerializedName("Vorname")
	@JsonField(name ="Vorname")
	private String vorname;

	public void setSVNummer(String sVNummer){
		this.sVNummer = sVNummer;
	}

	public String getSVNummer(){
		return sVNummer;
	}

	public void setGeschuetzt(String geschuetzt){
		this.geschuetzt = geschuetzt;
	}

	public String getGeschuetzt(){
		return geschuetzt;
	}

	public void setRowID(String rowID){
		this.rowID = rowID;
	}

	public String getRowID(){
		return rowID;
	}

	public void setAusgeschieden(String ausgeschieden){
		this.ausgeschieden = ausgeschieden;
	}

	public String getAusgeschieden(){
		return ausgeschieden;
	}

	public void setEMail(String eMail){
		this.eMail = eMail;
	}

	public String getEMail(){
		return eMail;
	}

	public void setAnrede(String anrede){
		this.anrede = anrede;
	}

	public String getAnrede(){
		return anrede;
	}

	public void setOrt(String ort){
		this.ort = ort;
	}

	public String getOrt(){
		return ort;
	}

	public void setPersonalnummer(String personalnummer){
		this.personalnummer = personalnummer;
	}

	public String getPersonalnummer(){
		return personalnummer;
	}

	public void setAktiv(String aktiv){
		this.aktiv = aktiv;
	}

	public String getAktiv(){
		return aktiv;
	}

	public void setGebiet(String gebiet){
		this.gebiet = gebiet;
	}

	public String getGebiet(){
		return gebiet;
	}

	public void setHierarchie(String hierarchie){
		this.hierarchie = hierarchie;
	}

	public String getHierarchie(){
		return hierarchie;
	}

	public void setZeichnung(String zeichnung){
		this.zeichnung = zeichnung;
	}

	public String getZeichnung(){
		return zeichnung;
	}

	public void setMobil(String mobil){
		this.mobil = mobil;
	}

	public String getMobil(){
		return mobil;
	}

	public void setTelefax(String telefax){
		this.telefax = telefax;
	}

	public String getTelefax(){
		return telefax;
	}

	public void setNachname(String nachname){
		this.nachname = nachname;
	}

	public String getNachname(){
		return nachname;
	}

	public void setBenutzername(String benutzername){
		this.benutzername = benutzername;
	}

	public String getBenutzername(){
		return benutzername;
	}

	public void setFunktion(String funktion){
		this.funktion = funktion;
	}

	public String getFunktion(){
		return funktion;
	}

	public void setMitarbeiter(String mitarbeiter){
		this.mitarbeiter = mitarbeiter;
	}

	public String getMitarbeiter(){
		return mitarbeiter;
	}

	public void setTelefon(String telefon){
		this.telefon = telefon;
	}

	public String getTelefon(){
		return telefon;
	}

	public void setNL(String nL){
		this.nL = nL;
	}

	public String getNL(){
		return nL;
	}

	public void setVorgesetzter(String vorgesetzter){
		this.vorgesetzter = vorgesetzter;
	}

	public String getVorgesetzter(){
		return vorgesetzter;
	}

	public void setVorname(String vorname){
		this.vorname = vorname;
	}

	public String getVorname(){
		return vorname;
	}

	@Override
 	public String toString(){
		return 
			"CustomerActivityEmployeeListItem{" + 
			"sVNummer = '" + sVNummer + '\'' + 
			",geschuetzt = '" + geschuetzt + '\'' + 
			",rowID = '" + rowID + '\'' + 
			",ausgeschieden = '" + ausgeschieden + '\'' + 
			",eMail = '" + eMail + '\'' + 
			",anrede = '" + anrede + '\'' + 
			",ort = '" + ort + '\'' + 
			",personalnummer = '" + personalnummer + '\'' + 
			",aktiv = '" + aktiv + '\'' + 
			",gebiet = '" + gebiet + '\'' + 
			",hierarchie = '" + hierarchie + '\'' + 
			",zeichnung = '" + zeichnung + '\'' + 
			",mobil = '" + mobil + '\'' + 
			",telefax = '" + telefax + '\'' + 
			",nachname = '" + nachname + '\'' + 
			",benutzername = '" + benutzername + '\'' + 
			",funktion = '" + funktion + '\'' + 
			",mitarbeiter = '" + mitarbeiter + '\'' + 
			",telefon = '" + telefon + '\'' + 
			",nL = '" + nL + '\'' + 
			",vorgesetzter = '" + vorgesetzter + '\'' + 
			",vorname = '" + vorname + '\'' + 
			"}";
		}
}