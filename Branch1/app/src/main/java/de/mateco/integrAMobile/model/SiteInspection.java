package de.mateco.integrAMobile.model;


public class SiteInspection {

    private int id;
    private String MatchCode,CustomerStrasse,CustomerPLZ,CustomerOrt;
    private String Purchaser;
    private String PurchaserTelefon;
    private String PurchaserEmail;
    private String ApvorOrt;
    private String PLZ;
    private String Ort;
    private String Strasse;
    private String Telefon;
    private String Email;
    private String InsertDate;
    private String OperationalLite;
    private String BuildingProject;
    private String Access;
    private String RegistrationReq;


    public String getPurchaserTelefon() {
        return PurchaserTelefon;
    }

    public void setPurchaserTelefon(String purchaserTelefon) {
        PurchaserTelefon = purchaserTelefon;
    }

    public String getPurchaser() {
        return Purchaser;
    }

    public void setPurchaser(String purchaser) {
        Purchaser = purchaser;
    }

    public String getPurchaserEmail() {
        return PurchaserEmail;
    }

    public void setPurchaserEmail(String purchaserEmail) {
        PurchaserEmail = purchaserEmail;
    }

    public String getStrasse() {
        return Strasse;
    }

    public void setStrasse(String strasse) {
        Strasse = strasse;
    }
    public String getMatchCode() {
        return MatchCode;
    }

    public void setMatchCode(String matchCode) {
        MatchCode = matchCode;
    }

    public String getCustomerStrasse() {
        return CustomerStrasse;
    }

    public void setCustomerStrasse(String customerStrasse) {
        CustomerStrasse = customerStrasse;
    }

    public String getCustomerPLZ() {
        return CustomerPLZ;
    }

    public void setCustomerPLZ(String customerPLZ) {
        CustomerPLZ = customerPLZ;
    }

    public String getCustomerOrt() {
        return CustomerOrt;
    }

    public void setCustomerOrt(String customerOrt) {
        CustomerOrt = customerOrt;
    }



    public String getWorkPerformed() {
        return WorkPerformed;
    }

    public void setWorkPerformed(String workPerformed) {
        WorkPerformed = workPerformed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApvorOrt() {
        return ApvorOrt;
    }

    public void setApvorOrt(String apvorOrt) {
        ApvorOrt = apvorOrt;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }



    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getInsertDate() {
        return InsertDate;
    }

    public void setInsertDate(String insertDate) {
        InsertDate = insertDate;
    }

    public String getOperationalLite() {
        return OperationalLite;
    }

    public void setOperationalLite(String operationalLite) {
        OperationalLite = operationalLite;
    }

    public String getBuildingProject() {
        return BuildingProject;
    }

    public void setBuildingProject(String buildingProject) {
        BuildingProject = buildingProject;
    }

    public String getAccess() {
        return Access;
    }

    public void setAccess(String access) {
        Access = access;
    }

    public String getRegistrationReq() {
        return RegistrationReq;
    }

    public void setRegistrationReq(String registrationReq) {
        RegistrationReq = registrationReq;
    }

    public String getKinkPointHigh() {
        return KinkPointHigh;
    }

    public void setKinkPointHigh(String kinkPointHigh) {
        KinkPointHigh = kinkPointHigh;
    }

    public String getInterferingEdges() {
        return InterferingEdges;
    }

    public void setInterferingEdges(String interferingEdges) {
        InterferingEdges = interferingEdges;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }

    private String WorkPerformed;
    private String KinkPointHigh;
    private String InterferingEdges;
    private String Other;
}
