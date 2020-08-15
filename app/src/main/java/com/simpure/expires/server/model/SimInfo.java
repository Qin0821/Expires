package com.simpure.expires.server.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SimInfo implements Parcelable {
    /**
     * Profile 的 IMSI
     */
    private String mIMSI;
    /**
     * Profile 的 ICCID
     */
    private String mICCID;
    /**
     * 扩展字段，暂不使用
     */
    private boolean mActive;

    protected SimInfo(Parcel in) {
        mIMSI = in.readString();
        mICCID = in.readString();
        mActive = in.readByte() != 0;
    }

    public static final Creator<SimInfo> CREATOR = new Creator<SimInfo>() {
        @Override
        public SimInfo createFromParcel(Parcel in) {
            return new SimInfo(in);
        }

        @Override
        public SimInfo[] newArray(int size) {
            return new SimInfo[size];
        }
    };

    public String getIMSI() {
        return mIMSI;
    }

    public void setIMSI(String IMSI) {
        mIMSI = IMSI;
    }

    public String getICCID() {
        return mICCID;
    }

    public void setICCID(String ICCID) {
        mICCID = ICCID;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mIMSI);
        dest.writeString(mICCID);
        dest.writeByte((byte) (mActive ? 1 : 0));
    }
}
