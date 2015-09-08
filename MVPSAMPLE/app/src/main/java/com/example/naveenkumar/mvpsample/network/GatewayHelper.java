package com.example.naveenkumar.mvpsample.network;

import android.content.Context;

import com.example.naveenkumar.mvpsample.sync.gateways.MovieListGateway;

public class GatewayHelper implements IGatewayHelper {

    private Context mContext;
    private GatewayCache mGatewayCache;
    public static IGatewayHelper mGateWayHelper;


    public GatewayHelper(Context context) {
        this.mContext = context;
        mGatewayCache = new GatewayCache(context);
    }

    public static void SetGatewayHelper(IGatewayHelper gatewayHelper){
        mGateWayHelper = gatewayHelper;
    }

    @Override
    public void FireMovieListRequest(int limit) {
        MovieListGateway movieListService = mGatewayCache.GetNetworkService(MovieListGateway.class.getName());
        movieListService.doMovieListRequest(limit);
    }


}
