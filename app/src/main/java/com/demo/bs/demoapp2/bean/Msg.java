package com.demo.bs.demoapp2.bean;

import android.os.Parcel;
import android.os.Parcelable;



public class Msg implements Parcelable {
    String id;
    String nr;
    String time;
    String fromid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nr);
        dest.writeString(this.time);
        dest.writeString(this.fromid);
    }

    public Msg() {
    }

    protected Msg(Parcel in) {
        this.id = in.readString();
        this.nr = in.readString();
        this.time = in.readString();
        this.fromid = in.readString();
    }

    public static final Parcelable.Creator<Msg> CREATOR = new Parcelable.Creator<Msg>() {
        @Override
        public Msg createFromParcel(Parcel source) {
            return new Msg(source);
        }

        @Override
        public Msg[] newArray(int size) {
            return new Msg[size];
        }
    };
}
