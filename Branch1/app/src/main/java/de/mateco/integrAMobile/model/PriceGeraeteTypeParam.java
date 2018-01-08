package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PriceGeraeteTypeParam implements Parcelable {
    private String Hoehengruppe, KaNr, Ausstattung1, Ausstattung2, Ausstattung3, Ausstattung4, Ausstattung5;

    public String getHoehengruppe() {
        return Hoehengruppe;
    }

    public void setHoehengruppe(String hoehengruppe) {
        Hoehengruppe = hoehengruppe;
    }

    public String getKaNr() {
        return KaNr;
    }

    public void setKaNr(String kaNr) {
        KaNr = kaNr;
    }

    public String getAusstattung1() {
        return Ausstattung1;
    }

    public void setAusstattung1(String ausstattung1) {
        Ausstattung1 = ausstattung1;
    }

    public String getAusstattung2() {
        return Ausstattung2;
    }

    public void setAusstattung2(String ausstattung2) {
        Ausstattung2 = ausstattung2;
    }

    public String getAusstattung3() {
        return Ausstattung3;
    }

    public void setAusstattung3(String ausstattung3) {
        Ausstattung3 = ausstattung3;
    }

    public String getAusstattung4() {
        return Ausstattung4;
    }

    public void setAusstattung4(String ausstattung4) {
        Ausstattung4 = ausstattung4;
    }

    public String getAusstattung5() {
        return Ausstattung5;
    }

    public void setAusstattung5(String ausstattung5) {
        Ausstattung5 = ausstattung5;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Hoehengruppe);
        dest.writeString(this.KaNr);
        dest.writeString(this.Ausstattung1);
        dest.writeString(this.Ausstattung2);
        dest.writeString(this.Ausstattung3);
        dest.writeString(this.Ausstattung4);
        dest.writeString(this.Ausstattung5);
    }

    public PriceGeraeteTypeParam() {
    }

    private PriceGeraeteTypeParam(Parcel in) {
        this.Hoehengruppe = in.readString();
        this.KaNr = in.readString();
        this.Ausstattung1 = in.readString();
        this.Ausstattung2 = in.readString();
        this.Ausstattung3 = in.readString();
        this.Ausstattung4 = in.readString();
        this.Ausstattung5 = in.readString();
    }

    public static final Parcelable.Creator<PriceGeraeteTypeParam> CREATOR = new Parcelable.Creator<PriceGeraeteTypeParam>() {
        public PriceGeraeteTypeParam createFromParcel(Parcel source) {
            return new PriceGeraeteTypeParam(source);
        }

        public PriceGeraeteTypeParam[] newArray(int size) {
            return new PriceGeraeteTypeParam[size];
        }
    };
}
