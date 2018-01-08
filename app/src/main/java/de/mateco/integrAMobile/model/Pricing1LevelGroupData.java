package de.mateco.integrAMobile.model;

public class Pricing1LevelGroupData {

    String designation;
    String heightGroup;

    public Pricing1LevelGroupData()
    {

    }

    public Pricing1LevelGroupData(String designation, String heightGroup) {
        this.designation = designation;
        this.heightGroup = heightGroup;
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
}
