package de.mateco.integrAMobile.model;

/**
 * Created by mmehta on 31.05.2016.
 */
public class TransportDetailModel
{
    Boolean isAnlieferung;
    Boolean isKann;
    Boolean isLieferung;
    int Id;

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
    }

    public Boolean getAnlieferung()
    {
        return isAnlieferung;
    }

    public void setAnlieferung(Boolean anlieferung)
    {
        isAnlieferung = anlieferung;
    }

    public Boolean getKann()
    {
        return isKann;
    }

    public void setKann(Boolean kann)
    {
        isKann = kann;
    }

    public Boolean getLieferung()
    {
        return isLieferung;
    }

    public void setLieferung(Boolean lieferung)
    {
        isLieferung = lieferung;
    }

    public Boolean getVoranmeldung()
    {
        return isVoranmeldung;
    }

    public void setVoranmeldung(Boolean voranmeldung)
    {
        isVoranmeldung = voranmeldung;
    }

    public Boolean getBenachrichtgung()
    {
        return isBenachrichtgung;
    }

    public void setBenachrichtgung(Boolean benachrichtgung)
    {
        isBenachrichtgung = benachrichtgung;
    }

    public Boolean getRampena()
    {
        return isRampena;
    }

    public void setRampena(Boolean rampena)
    {
        isRampena = rampena;
    }

    public Boolean getSonstige()
    {
        return isSonstige;
    }

    public void setSonstige(Boolean sonstige)
    {
        isSonstige = sonstige;
    }

    public Boolean getEinweisung()
    {
        return isEinweisung;
    }

    public void setEinweisung(Boolean einweisung)
    {
        isEinweisung = einweisung;
    }

    public Boolean getSelbstfahrer()
    {
        return isSelbstfahrer;
    }

    public void setSelbstfahrer(Boolean selbstfahrer)
    {
        isSelbstfahrer = selbstfahrer;
    }

    public String getText1()
    {
        return text1;
    }

    public void setText1(String text1)
    {
        this.text1 = text1;
    }

    public String getText2()
    {
        return text2;
    }

    public void setText2(String text2)
    {
        this.text2 = text2;
    }

    public String getText3()
    {
        return text3;
    }

    public void setText3(String text3)
    {
        this.text3 = text3;
    }

    public String getText4()
    {
        return text4;
    }

    public void setText4(String text4)
    {
        this.text4 = text4;
    }

    public String getText5()
    {
        return text5;
    }

    public void setText5(String text5)
    {
        this.text5 = text5;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    Boolean isVoranmeldung;
    Boolean isBenachrichtgung;
    Boolean isRampena;
    Boolean isSonstige;
    Boolean isEinweisung;
    Boolean isSelbstfahrer;
    String text1,text2,text3,text4,text5;
    String startDate, startTime,endDate,endTime;
}
