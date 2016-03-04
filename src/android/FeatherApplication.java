package com.jguix.cordova;

import android.app.Application;

import com.aviary.android.feather.sdk.IAviaryClientCredentials;

/**
 * Client authentication is required to use the Creative SDK
 * A class that extends Application and implements IAviaryClientCredentials is required
 */
public class FeatherApplication extends Application implements IAviaryClientCredentials {

    private static final String CREATIVE_SDK_CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "YOUR_CLIENT_SECRET";

    @Override
    public String getBillingKey() {
        return ""; // Leave this blank
    }

   @Override
    public String getClientID() {
        return CREATIVE_SDK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }

}
