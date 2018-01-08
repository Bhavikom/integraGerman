package de.mateco.integrAMobile.model;

public class Pricing1EquipmentData {
    int equipment;
    String designation;
    String heightGroup;
    int HeightMainGroup;
    String designationId;

    public Pricing1EquipmentData()
    {

    }

    public Pricing1EquipmentData(int equipment, String designation, String heightGroup, int heightMainGroup, String designationId) {
        this.equipment = equipment;
        this.designation = designation;
        this.heightGroup = heightGroup;
        HeightMainGroup = heightMainGroup;
        this.designationId = designationId;
    }

    public int getEquipment() {
        return equipment;
    }

    public void setEquipment(int equipment) {
        this.equipment = equipment;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getHeightGroup() {
        return heightGroup;
    }

    public void setHeightGroup(String heightGroup) {
        this.heightGroup = heightGroup;
    }

    public int getHeightMainGroup() {
        return HeightMainGroup;
    }

    public void setHeightMainGroup(int heightMainGroup) {
        HeightMainGroup = heightMainGroup;
    }

    public String getDesignationId() {
        return designationId;
    }

    public void setDesignationId(String designationId) {
        this.designationId = designationId;
    }
}
