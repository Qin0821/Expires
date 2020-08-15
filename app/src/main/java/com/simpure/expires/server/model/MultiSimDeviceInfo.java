package com.simpure.expires.server.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MultiSimDeviceInfo implements Parcelable {
    /**
     * 设备返回的结果码:
     * 1：成功
     * 0：未知错误
     * -1：用户拒绝授权
     * -2：设备未连接
     * -3：设备不支持一号多卡
     */
    private int mResultCode;
    /**
     * 设备类型：
     * 0：无效
     * 1：SIM 版
     * 2：eSIM 版
     * 3：无 Modem 版
     * 4~255：Reserved
     */
    private int mDeviceType;
    /**
     * 设备IMEI（有modem）
     */
    private String mDeviceIMEI;
    /**
     * 设备SN号（无IMEI设备返回SN号）
     */
    private String mDeviceSerialNumber;
    /**
     * 设备型号，用于区分不同设备下载不同的eSIM profile
     */
    private String mProductName;
    /**
     * eSIM卡的EID
     */
    private String mEID;
    /**
     * 设备的sim卡信息列表
     */
    private List<SimInfo> mSimInfoList;

    protected MultiSimDeviceInfo(Parcel in) {
        mResultCode = in.readInt();
        mDeviceType = in.readInt();
        mDeviceIMEI = in.readString();
        mDeviceSerialNumber = in.readString();
        mProductName = in.readString();
        mEID = in.readString();
        mSimInfoList = in.createTypedArrayList(SimInfo.CREATOR);
    }

    public static final Creator<MultiSimDeviceInfo> CREATOR = new Creator<MultiSimDeviceInfo>() {
        @Override
        public MultiSimDeviceInfo createFromParcel(Parcel in) {
            return new MultiSimDeviceInfo(in);
        }

        @Override
        public MultiSimDeviceInfo[] newArray(int size) {
            return new MultiSimDeviceInfo[size];
        }
    };

    public int getResultCode() {
        return mResultCode;
    }

    public void setResultCode(int resultCode) {
        mResultCode = resultCode;
    }

    public int getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(int deviceType) {
        mDeviceType = deviceType;
    }

    public String getDeviceIMEI() {
        return mDeviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        mDeviceIMEI = deviceIMEI;
    }

    public String getDeviceSerialNumber() {
        return mDeviceSerialNumber;
    }

    public void setDeviceSerialNumber(String deviceSerialNumber) {
        mDeviceSerialNumber = deviceSerialNumber;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getEID() {
        return mEID;
    }

    public void setEID(String EID) {
        mEID = EID;
    }

    public List<SimInfo> getSimInfoList() {
        return mSimInfoList;
    }

    public void setSimInfoList(List<SimInfo> simInfoList) {
        mSimInfoList = simInfoList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mResultCode);
        dest.writeInt(mDeviceType);
        dest.writeString(mDeviceIMEI);
        dest.writeString(mDeviceSerialNumber);
        dest.writeString(mProductName);
        dest.writeString(mEID);
        dest.writeTypedList(mSimInfoList);
    }
}
