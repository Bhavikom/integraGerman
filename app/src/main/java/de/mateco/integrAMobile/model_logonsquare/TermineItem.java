package de.mateco.integrAMobile.model_logonsquare;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.greenrobot.greendao.annotation.Generated;

@JsonObject
public class TermineItem{

	@SerializedName("Matchcode")
	@JsonField(name ="Matchcode")
	private String matchcode;

	@SerializedName("Adresse")
	@JsonField(name ="Adresse")
	private String adresse;

	@SerializedName("Startzeit")
	@JsonField(name ="Startzeit")
	private String startzeit;

	@SerializedName("Ort")
	@JsonField(name ="Ort")
	private String ort;

	@SerializedName("Ansprechpartner")
	@JsonField(name ="Ansprechpartner")
	private List<String> ansprechpartner;

	@SerializedName("Notiz")
	@JsonField(name ="Notiz")
	private String notiz;

	@SerializedName("Aktivitaet")
	@JsonField(name ="Aktivitaet")
	private String aktivitaet;

	@SerializedName("Name1")
	@JsonField(name ="Name1")
	private String name1;

	@SerializedName("Strasse")
	@JsonField(name ="Strasse")
	private String strasse;

	@SerializedName("PLZ")
	@JsonField(name ="PLZ")
	private String pLZ;

	@SerializedName("Kontakt")
	@JsonField(name ="Kontakt")
	private String kontakt;

	@SerializedName("Fest")
	@JsonField(name ="Fest")
	private String fest;

	@SerializedName("MitarbeiterName")
	@JsonField(name ="MitarbeiterName")
	private List<String> mitarbeiterName;

	@SerializedName("Datum")
	@JsonField(name ="Datum")
	private String datum;

	@SerializedName("Beschreibung")
	@JsonField(name ="Beschreibung")
	private String beschreibung;

	@SerializedName("Matchcode_Projekt")
	@JsonField(name ="Matchcode_Projekt")
	private String matchcodeProjekt;

	@SerializedName("Typ")
	@JsonField(name ="Typ")
	private String typ;

	@SerializedName("Endzeit")
	@JsonField(name ="Endzeit")
	private String endzeit;

	@SerializedName("Realisiert")
	@JsonField(name ="Realisiert")
	private String realisiert;

	@SerializedName("Aktivitaetstytp")
	@JsonField(name ="Aktivitaetstytp")
	private String aktivitaetstytp;

	@SerializedName("PLZ_Ort")
	@JsonField(name ="PLZ_Ort")
	private String pLZOrt;

	@SerializedName("Thema")
	@JsonField(name ="Thema")
	private int thema;

	@SerializedName("Angebot")
	@JsonField(name ="Angebot")
	private String angebot;

	@SerializedName("Projekt")
	@JsonField(name ="Projekt")
	private String projekt;

	@SerializedName("Mitarbeiter")
	@JsonField(name ="Mitarbeiter")
	private String mitarbeiter;

	@SerializedName("PLZ_Projekt")
	@JsonField(name ="PLZ_Projekt")
	private String pLZProjekt;

	public void setMatchcode(String matchcode){
		this.matchcode = matchcode;
	}

	public String getMatchcode(){
		return matchcode;
	}

	public void setAdresse(String adresse){
		this.adresse = adresse;
	}

	public String getAdresse(){
		return adresse;
	}

	public void setStartzeit(String startzeit){
		this.startzeit = startzeit;
	}

	public String getStartzeit(){
		return startzeit;
	}

	public void setOrt(String ort){
		this.ort = ort;
	}

	public String getOrt(){
		return ort;
	}

	public void setAnsprechpartner(List<String> ansprechpartner){
		this.ansprechpartner = ansprechpartner;
	}

	public List<String> getAnsprechpartner(){
		return ansprechpartner;
	}

	public void setNotiz(String notiz){
		this.notiz = notiz;
	}

	public String getNotiz(){
		return notiz;
	}

	public void setAktivitaet(String aktivitaet){
		this.aktivitaet = aktivitaet;
	}

	public String getAktivitaet(){
		return aktivitaet;
	}

	public void setName1(String name1){
		this.name1 = name1;
	}

	public String getName1(){
		return name1;
	}

	public void setStrasse(String strasse){
		this.strasse = strasse;
	}

	public String getStrasse(){
		return strasse;
	}

	public void setPLZ(String pLZ){
		this.pLZ = pLZ;
	}

	public String getPLZ(){
		return pLZ;
	}

	public void setKontakt(String kontakt){
		this.kontakt = kontakt;
	}

	public String getKontakt(){
		return kontakt;
	}

	public void setFest(String fest){
		this.fest = fest;
	}

	public String getFest(){
		return fest;
	}

	public void setMitarbeiterName(List<String> mitarbeiterName){
		this.mitarbeiterName = mitarbeiterName;
	}

	public List<String> getMitarbeiterName(){
		return mitarbeiterName;
	}

	public void setDatum(String datum){
		this.datum = datum;
	}

	public String getDatum(){
		return datum;
	}

	public void setBeschreibung(String beschreibung){
		this.beschreibung = beschreibung;
	}

	public String getBeschreibung(){
		return beschreibung;
	}

	public void setMatchcodeProjekt(String matchcodeProjekt){
		this.matchcodeProjekt = matchcodeProjekt;
	}

	public String getMatchcodeProjekt(){
		return matchcodeProjekt;
	}

	public void setTyp(String typ){
		this.typ = typ;
	}

	public String getTyp(){
		return typ;
	}

	public void setEndzeit(String endzeit){
		this.endzeit = endzeit;
	}

	public String getEndzeit(){
		return endzeit;
	}

	public void setRealisiert(String realisiert){
		this.realisiert = realisiert;
	}

	public String getRealisiert(){
		return realisiert;
	}

	public void setAktivitaetstytp(String aktivitaetstytp){
		this.aktivitaetstytp = aktivitaetstytp;
	}

	public String getAktivitaetstytp(){
		return aktivitaetstytp;
	}

	public void setPLZOrt(String pLZOrt){
		this.pLZOrt = pLZOrt;
	}

	public String getPLZOrt(){
		return pLZOrt;
	}

	public void setThema(int thema){
		this.thema = thema;
	}

	public int getThema(){
		return thema;
	}

	public void setAngebot(String angebot){
		this.angebot = angebot;
	}

	public String getAngebot(){
		return angebot;
	}

	public void setProjekt(String projekt){
		this.projekt = projekt;
	}

	public String getProjekt(){
		return projekt;
	}

	public void setMitarbeiter(String mitarbeiter){
		this.mitarbeiter = mitarbeiter;
	}

	public String getMitarbeiter(){
		return mitarbeiter;
	}

	public void setPLZProjekt(String pLZProjekt){
		this.pLZProjekt = pLZProjekt;
	}

	public String getPLZProjekt(){
		return pLZProjekt;
	}

	@Override
 	public String toString(){
		return 
			"TermineItem{" + 
			"matchcode = '" + matchcode + '\'' + 
			",adresse = '" + adresse + '\'' + 
			",startzeit = '" + startzeit + '\'' + 
			",ort = '" + ort + '\'' + 
			",ansprechpartner = '" + ansprechpartner + '\'' + 
			",notiz = '" + notiz + '\'' + 
			",aktivitaet = '" + aktivitaet + '\'' + 
			",name1 = '" + name1 + '\'' + 
			",strasse = '" + strasse + '\'' + 
			",pLZ = '" + pLZ + '\'' + 
			",kontakt = '" + kontakt + '\'' + 
			",fest = '" + fest + '\'' + 
			",mitarbeiterName = '" + mitarbeiterName + '\'' + 
			",datum = '" + datum + '\'' + 
			",beschreibung = '" + beschreibung + '\'' + 
			",matchcode_Projekt = '" + matchcodeProjekt + '\'' + 
			",typ = '" + typ + '\'' + 
			",endzeit = '" + endzeit + '\'' + 
			",realisiert = '" + realisiert + '\'' + 
			",aktivitaetstytp = '" + aktivitaetstytp + '\'' + 
			",pLZ_Ort = '" + pLZOrt + '\'' + 
			",thema = '" + thema + '\'' + 
			",angebot = '" + angebot + '\'' + 
			",projekt = '" + projekt + '\'' + 
			",mitarbeiter = '" + mitarbeiter + '\'' + 
			",pLZ_Projekt = '" + pLZProjekt + '\'' + 
			"}";
		}
}