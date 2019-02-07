package com.domain.smaprtpot.smartpot.model;

import com.google.gson.annotations.SerializedName;

public class Pot {
    @SerializedName("deviceToken")
    private String mDeviceToken;
    @SerializedName("tempC")
    private Integer mTemperatureC;//температура воздуха
    @SerializedName("sm")
    private Integer mSm;//влажность почвы
    @SerializedName("sleepMode")
    private Integer mSleepMode;
    @SerializedName("wateringPeriod")
    private Integer mWateringPeriod;//период полива
    @SerializedName("thresholdSM")
    private Integer mThresholdSM;//минимальная влажность почвы
    @SerializedName("name")
    private String mNameOfPot;

    public String getNameOfPot() {
        return mNameOfPot;
    }

    public String getDeviceToken() {
        return mDeviceToken;
    }

    public Integer getTempC() {
        return mTemperatureC;
    }

    public Integer getSm() {
        return mSm;
    }

    public Integer getWat() {
        return mWateringPeriod;
    }

    public Integer getThresholdSM() {
        return mThresholdSM;
    }

    public Integer getSleepMode() {
        return mSleepMode;
    }

    public Integer getWateringPeriod() {
        return mWateringPeriod;
    }

    public void setDeviceToken(String deviceToken) {
        mDeviceToken = deviceToken;
    }

    public void setTemperatureC(Integer temperatureC) {
        mTemperatureC = temperatureC;
    }

    public void setSm(Integer sm) {
        mSm = sm;
    }

    public void setSleepMode(Integer sleepMode) {
        mSleepMode = sleepMode;
    }

    public void setWateringPeriod(Integer wateringPeriod) {
        mWateringPeriod = wateringPeriod;
    }

    public void setThresholdSM(Integer thresholdSM) {
        mThresholdSM = thresholdSM;
    }

    public void setNameOfPot(String nameOfPot) {
        mNameOfPot = nameOfPot;
    }

    @Override
    public String toString() {
        return "DeviceToken: " + mDeviceToken + "\n"
                + "Name: " + mNameOfPot + "\n"
                + "tempC: " + mTemperatureC + "\n"
                + "sm: " + mSm + "\n"
                + "wateringPeriod: " + mWateringPeriod + "\n"
                + "thresholdSM: " + mThresholdSM + "\n";
    }
}

