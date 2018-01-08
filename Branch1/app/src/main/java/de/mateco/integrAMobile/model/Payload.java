package de.mateco.integrAMobile.model;

/**
 * Created by mmehta on 31.05.2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("isAnlieferung")
    @Expose
    private boolean isAnlieferung;
    @SerializedName("isBenachrichtgung")
    @Expose
    private boolean isBenachrichtgung;
    @SerializedName("isEinweisung")
    @Expose
    private boolean isEinweisung;
    @SerializedName("isKann")
    @Expose
    private boolean isKann;
    @SerializedName("isLieferung")
    @Expose
    private boolean isLieferung;
    @SerializedName("isRampena")
    @Expose
    private boolean isRampena;
    @SerializedName("isSelbstfahrer")
    @Expose
    private boolean isSelbstfahrer;
    @SerializedName("isSonstige")
    @Expose
    private boolean isSonstige;
    @SerializedName("isVoranmeldung")
    @Expose
    private boolean isVoranmeldung;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("text1")
    @Expose
    private String text1;
    @SerializedName("text2")
    @Expose
    private String text2;
    @SerializedName("text3")
    @Expose
    private String text3;
    @SerializedName("text4")
    @Expose
    private String text4;
    @SerializedName("text5")
    @Expose
    private String text5;

    /**
     *
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The __type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     *     The endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     *     The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime
     *     The endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @return
     *     The isAnlieferung
     */
    public boolean isIsAnlieferung() {
        return isAnlieferung;
    }

    /**
     *
     * @param isAnlieferung
     *     The isAnlieferung
     */
    public void setIsAnlieferung(boolean isAnlieferung) {
        this.isAnlieferung = isAnlieferung;
    }

    /**
     *
     * @return
     *     The isBenachrichtgung
     */
    public boolean isIsBenachrichtgung() {
        return isBenachrichtgung;
    }

    /**
     *
     * @param isBenachrichtgung
     *     The isBenachrichtgung
     */
    public void setIsBenachrichtgung(boolean isBenachrichtgung) {
        this.isBenachrichtgung = isBenachrichtgung;
    }

    /**
     *
     * @return
     *     The isEinweisung
     */
    public boolean isIsEinweisung() {
        return isEinweisung;
    }

    /**
     *
     * @param isEinweisung
     *     The isEinweisung
     */
    public void setIsEinweisung(boolean isEinweisung) {
        this.isEinweisung = isEinweisung;
    }

    /**
     *
     * @return
     *     The isKann
     */
    public boolean isIsKann() {
        return isKann;
    }

    /**
     *
     * @param isKann
     *     The isKann
     */
    public void setIsKann(boolean isKann) {
        this.isKann = isKann;
    }

    /**
     *
     * @return
     *     The isLieferung
     */
    public boolean isIsLieferung() {
        return isLieferung;
    }

    /**
     *
     * @param isLieferung
     *     The isLieferung
     */
    public void setIsLieferung(boolean isLieferung) {
        this.isLieferung = isLieferung;
    }

    /**
     *
     * @return
     *     The isRampena
     */
    public boolean isIsRampena() {
        return isRampena;
    }

    /**
     *
     * @param isRampena
     *     The isRampena
     */
    public void setIsRampena(boolean isRampena) {
        this.isRampena = isRampena;
    }

    /**
     *
     * @return
     *     The isSelbstfahrer
     */
    public boolean isIsSelbstfahrer() {
        return isSelbstfahrer;
    }

    /**
     *
     * @param isSelbstfahrer
     *     The isSelbstfahrer
     */
    public void setIsSelbstfahrer(boolean isSelbstfahrer) {
        this.isSelbstfahrer = isSelbstfahrer;
    }

    /**
     *
     * @return
     *     The isSonstige
     */
    public boolean isIsSonstige() {
        return isSonstige;
    }

    /**
     *
     * @param isSonstige
     *     The isSonstige
     */
    public void setIsSonstige(boolean isSonstige) {
        this.isSonstige = isSonstige;
    }

    /**
     *
     * @return
     *     The isVoranmeldung
     */
    public boolean isIsVoranmeldung() {
        return isVoranmeldung;
    }

    /**
     *
     * @param isVoranmeldung
     *     The isVoranmeldung
     */
    public void setIsVoranmeldung(boolean isVoranmeldung) {
        this.isVoranmeldung = isVoranmeldung;
    }

    /**
     *
     * @return
     *     The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     *     The startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     *     The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     *     The startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return
     *     The text1
     */
    public String getText1() {
        return text1;
    }

    /**
     *
     * @param text1
     *     The text1
     */
    public void setText1(String text1) {
        this.text1 = text1;
    }

    /**
     *
     * @return
     *     The text2
     */
    public String getText2() {
        return text2;
    }

    /**
     *
     * @param text2
     *     The text2
     */
    public void setText2(String text2) {
        this.text2 = text2;
    }

    /**
     *
     * @return
     *     The text3
     */
    public String getText3() {
        return text3;
    }

    /**
     *
     * @param text3
     *     The text3
     */
    public void setText3(String text3) {
        this.text3 = text3;
    }

    /**
     *
     * @return
     *     The text4
     */
    public String getText4() {
        return text4;
    }

    /**
     *
     * @param text4
     *     The text4
     */
    public void setText4(String text4) {
        this.text4 = text4;
    }

    /**
     *
     * @return
     *     The text5
     */
    public String getText5() {
        return text5;
    }

    /**
     *
     * @param text5
     *     The text5
     */
    public void setText5(String text5) {
        this.text5 = text5;
    }

}
