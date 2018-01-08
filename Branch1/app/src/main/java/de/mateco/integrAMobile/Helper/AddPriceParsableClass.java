package de.mateco.integrAMobile.Helper;

/**
 * Created by mmehta on 01.06.2016.
 */
import android.os.Parcel;
import android.os.Parcelable;

public class AddPriceParsableClass implements Parcelable
{
    public String flagAnlieferung;
    public String flagKann;
    public String flagLieferung;
    public String flagVoranmeldung;
    public  String flagBenachrichtgung;
    public  String flagRampena;
    public String flagSonstige;
    public String flagEinweisung;
    public  String flagSelbstfahrer;
    public String strKann ="", strVoranmeldung ="", strBenachrich ="", strSonstige ="",str5="";
    public String strStartDate ="", strEndDate ="", strStartTime ="", strEndtime ="";
    public int intSpinneritem=0;
    protected AddPriceParsableClass(Parcel in)
    {
        this.flagAnlieferung= in.readString();
        this.flagKann= in.readString();
        this.flagLieferung= in.readString();
        this.flagVoranmeldung= in.readString();
        this.flagBenachrichtgung= in.readString();
        this.flagRampena= in.readString();
        this.flagSonstige= in.readString();
        this.flagEinweisung= in.readString();
        this.flagSelbstfahrer= in.readString();

        this.strKann=in.readString();
        this.strVoranmeldung=in.readString();
        this.strBenachrich=in.readString();
        this.strSonstige=in.readString();
        this.strStartDate=in.readString();
        this.strEndDate=in.readString();
        this.strStartTime=in.readString();
        this.strEndtime=in.readString();
        this.intSpinneritem= Integer.parseInt(in.readString());
    }
    public AddPriceParsableClass(){

    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(String.valueOf(flagAnlieferung));
        dest.writeString(String.valueOf(flagKann));
        dest.writeString(String.valueOf(flagLieferung));
        dest.writeString(String.valueOf(flagVoranmeldung));
        dest.writeString(String.valueOf(flagBenachrichtgung));
        dest.writeString(String.valueOf(flagRampena));
        dest.writeString(String.valueOf(flagSonstige));
        dest.writeString(String.valueOf(flagEinweisung));
        dest.writeString(String.valueOf(flagSelbstfahrer));

        dest.writeString(strKann);
        dest.writeString(strVoranmeldung);
        dest.writeString(strBenachrich);
        dest.writeString(strSonstige);

        dest.writeString(String.valueOf(intSpinneritem));
        dest.writeString(strStartDate);
        dest.writeString(strEndDate);
        dest.writeString(strStartTime);
        dest.writeString(strEndtime);


    }
    public AddPriceParsableClass(String isAnlieferung,String isKann,String isLieferung,String isVoranmeldung
            ,String isBenachrichtgung,String isRampena,String isSonstige,String isEinweisung,
                                 String isSelbstfahrer,String kann,String Voranmeldung,  String Benachrich,String Sonstige,
                                 int Ladefahrzeug){
        this.flagAnlieferung=isAnlieferung;
        this.flagKann=isKann;
        this.flagLieferung=isLieferung;
        this.flagVoranmeldung=isVoranmeldung;
        this.flagBenachrichtgung=isBenachrichtgung;
        this.flagRampena=isRampena;
        this.flagSonstige=isSonstige;
        this.flagEinweisung=isEinweisung;
        this.flagSelbstfahrer=isSelbstfahrer;

        this.strKann=kann;
        this.strVoranmeldung=Voranmeldung;
        this.strBenachrich=Benachrich;
        this.strSonstige=Sonstige;

        this.intSpinneritem=Ladefahrzeug;

    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<AddPriceParsableClass> CREATOR = new Creator<AddPriceParsableClass>()
    {
        @Override
        public AddPriceParsableClass createFromParcel(Parcel in)
        {
            return new AddPriceParsableClass(in);
        }

        @Override
        public AddPriceParsableClass[] newArray(int size)
        {
            return new AddPriceParsableClass[size];
        }
    };
}