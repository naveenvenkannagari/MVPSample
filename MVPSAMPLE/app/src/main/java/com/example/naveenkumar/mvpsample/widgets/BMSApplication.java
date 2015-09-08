package com.example.naveenkumar.mvpsample.widgets;

import android.app.Application;

import com.example.naveenkumar.mvpsample.network.GatewayHelper;
import com.example.naveenkumar.mvpsample.network.IGatewayHelper;

/**
 * Created by naveenkumar on 01/09/15.
 */
public class BMSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        IGatewayHelper gatewayHelper = new GatewayHelper(getApplicationContext());
        GatewayHelper.SetGatewayHelper(gatewayHelper);
    }
}

