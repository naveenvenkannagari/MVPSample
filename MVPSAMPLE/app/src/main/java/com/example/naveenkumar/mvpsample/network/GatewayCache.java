package com.example.naveenkumar.mvpsample.network;

import android.content.Context;

import com.example.naveenkumar.mvpsample.sync.gateways.BMSGateway;

import java.lang.reflect.Constructor;
import java.util.HashMap;


public class GatewayCache {
    private Context mContext;
    private HashMap<Class<? extends BMSGateway>, BMSGateway> mGatewayCache;

    public GatewayCache(Context context) {
        mContext = context;
        mGatewayCache = new HashMap<Class<? extends BMSGateway>, BMSGateway>();
    }

    public <T extends BMSGateway> T GetNetworkService(String networkServiceClassName) {
        try {
            @SuppressWarnings("unchecked")
            Class<? extends T> networkServiceClass = (Class<? extends T>) Class
                    .forName(networkServiceClassName);
            if (mGatewayCache.containsKey(networkServiceClass)) {
                return networkServiceClass.cast(mGatewayCache.get(networkServiceClass));
            }
            @SuppressWarnings("rawtypes")
            Constructor[] constructors = networkServiceClass.getDeclaredConstructors();
            for (@SuppressWarnings("rawtypes")
            Constructor constructor : constructors) {
                T networkService = networkServiceClass.cast(constructor.newInstance(mContext));
                mGatewayCache.put(networkServiceClass, networkService);
                return networkService;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
