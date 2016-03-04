package com.jguix.cordova;

import android.app.Application;

import com.aviary.android.feather.sdk.IAviaryClientCredentials;

/**
 * Client authentication is required to use the Creative SDK
 * A class that extends Application and implements IAviaryClientCredentials is required
 */
public class FeatherApplication extends Application implements IAviaryClientCredentials {

    private static final String CREATIVE_SDK_CLIENT_ID = "0b1b539edf4742a984abd3291b264a53";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "90c70c9d-4af6-490f-8aff-d88f1ff90c82";

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
