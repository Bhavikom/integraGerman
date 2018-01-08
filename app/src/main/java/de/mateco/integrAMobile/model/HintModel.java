package de.mateco.integrAMobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by S Soft on 06-Sep-17.
 */

public class HintModel{
    String Hint="";

    public String getHint() {
        return Hint;
    }

    public void setHint(String hint) {
        Hint = hint;
    }
    public HintModel(){

    }
    public HintModel(String name){
        this.Hint=name;
    }
   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Hint);
    }
    private HintModel(Parcel in) {
        this.Hint = in.readString();
    }
    public static final Creator<HintModel> CREATOR = new Creator<HintModel>() {
        public HintModel createFromParcel(Parcel source) {
            return new HintModel(source);
        }

        public HintModel[] newArray(int size) {
            return new HintModel[size];
        }
    };*/
}
