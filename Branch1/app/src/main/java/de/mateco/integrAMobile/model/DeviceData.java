package de.mateco.integrAMobile.model;


public class DeviceData {

    private int id;
    private int ParentId;
    private int BvoId;
    private int alternativeDevice;
    private String DeviceGroup,HeightScale,DeviceType,DeviceTypeNumber;
    private String WorkingHeight,LaeralReach,MaxLength,MaxBredth,MaxHeight,MaxWeight,BasketLoad,BoomLength;
    private String Diesel;
    private String Electrostatics;
    private String FourWheel;
    private String ChainRubber;
    private String WhiteReef;
    private String PowerLift;
    private String PowerGenerator;
    private String Others;

    public String getElectrostatics() {
        return Electrostatics;
    }

    public void setElectrostatics(String electrostatics) {
        Electrostatics = electrostatics;
    }

    public String getDiesel() {
        return Diesel;
    }

    public void setDiesel(String diesel) {
        Diesel = diesel;
    }

    public String getFourWheel() {
        return FourWheel;
    }

    public void setFourWheel(String fourWheel) {
        FourWheel = fourWheel;
    }

    public String getChainRubber() {
        return ChainRubber;
    }

    public void setChainRubber(String chainRubber) {
        ChainRubber = chainRubber;
    }

    public String getWhiteReef() {
        return WhiteReef;
    }

    public void setWhiteReef(String whiteReef) {
        WhiteReef = whiteReef;
    }

    public String getPowerLift() {
        return PowerLift;
    }

    public void setPowerLift(String powerLift) {
        PowerLift = powerLift;
    }

    public String getPowerGenerator() {
        return PowerGenerator;
    }

    public void setPowerGenerator(String powerGenerator) {
        PowerGenerator = powerGenerator;
    }

    public String getOthers() {
        return Others;
    }

    public void setOthers(String others) {
        Others = others;
    }

    public String getWorkingHeight() {
        return WorkingHeight;
    }

    public void setWorkingHeight(String workingHeight) {
        WorkingHeight = workingHeight;
    }

    public String getLaeralReach() {
        return LaeralReach;
    }

    public void setLaeralReach(String laeralReach) {
        LaeralReach = laeralReach;
    }

    public String getMaxLength() {
        return MaxLength;
    }

    public void setMaxLength(String maxLength) {
        MaxLength = maxLength;
    }

    public String getMaxBredth() {
        return MaxBredth;
    }

    public void setMaxBredth(String maxBredth) {
        MaxBredth = maxBredth;
    }

    public String getMaxHeight() {
        return MaxHeight;
    }

    public void setMaxHeight(String maxHeight) {
        MaxHeight = maxHeight;
    }

    public String getMaxWeight() {
        return MaxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        MaxWeight = maxWeight;
    }

    public String getBasketLoad() {
        return BasketLoad;
    }

    public void setBasketLoad(String basketLoad) {
        BasketLoad = basketLoad;
    }

    public String getBoomLength() {
        return BoomLength;
    }

    public void setBoomLength(String boomLength) {
        BoomLength = boomLength;
    }

    public int getAlternativeDevice() {
        return alternativeDevice;
    }

    public void setAlternativeDevice(int alternativeDevice) {
        this.alternativeDevice = alternativeDevice;
    }

    public int getBvoId() {
        return BvoId;
    }

    public void setBvoId(int bvoId) {
        BvoId = bvoId;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceGroup() {
        return DeviceGroup;
    }

    public void setDeviceGroup(String deviceGroup) {
        DeviceGroup = deviceGroup;
    }

    public String getHeightScale() {
        return HeightScale;
    }

    public void setHeightScale(String heightScale) {
        HeightScale = heightScale;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getDeviceTypeNumber() {
        return DeviceTypeNumber;
    }

    public void setDeviceTypeNumber(String deviceTypeNumber) {
        DeviceTypeNumber = deviceTypeNumber;
    }
}
