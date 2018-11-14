package com.demo.bs.demoapp2.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TM implements Parcelable {
    String id;
    String wt;
    String a1;
    String a2;
    String a3;
    String a4;
    String da;
    String km;
    String nd;
    String zsd;

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public String getZsd() {
        return zsd;
    }

    public void setZsd(String zsd) {
        this.zsd = zsd;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWt() {
        return wt;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.wt);
        dest.writeString(this.a1);
        dest.writeString(this.a2);
        dest.writeString(this.a3);
        dest.writeString(this.a4);
        dest.writeString(this.da);
        dest.writeString(this.km);
        dest.writeString(this.nd);
        dest.writeString(this.zsd);
    }

    public TM() {
    }

    protected TM(Parcel in) {
        this.id = in.readString();
        this.wt = in.readString();
        this.a1 = in.readString();
        this.a2 = in.readString();
        this.a3 = in.readString();
        this.a4 = in.readString();
        this.da = in.readString();
        this.km = in.readString();
        this.nd = in.readString();
        this.zsd = in.readString();
    }

    public static final Parcelable.Creator<TM> CREATOR = new Parcelable.Creator<TM>() {
        @Override
        public TM createFromParcel(Parcel source) {
            return new TM(source);
        }

        @Override
        public TM[] newArray(int size) {
            return new TM[size];
        }
    };
}
