package com.domain.smaprtpot.smartpot;

public class SmartPot {

    private static SmartPot sSmartPot;
    private String mUserToken;
    private String mStatus;
    private SmartPotApi mSmartPotApi;
    private SmartPot() {
    }

    public static SmartPot getInstance() {
        if (sSmartPot == null) {
            sSmartPot = new SmartPot();
        }
        return sSmartPot;
    }

    public String getUserToken() {
        return mUserToken;
    }

    public void setUserToken(String userToken) {
        mUserToken = userToken;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public SmartPotApi getSmartPotApi() {
        return mSmartPotApi;
    }

    public void setSmartPotApi(SmartPotApi smartPotApi) {
        mSmartPotApi = smartPotApi;
    }
}
