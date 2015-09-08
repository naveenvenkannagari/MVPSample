package com.example.naveenkumar.mvpsample.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NetworkRequestQueue {

    public static final String TAG = "VolleyNetworkRequest";
    private static volatile NetworkRequestQueue mNetworkRequestQueue;
    private RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;

    private NetworkRequestQueue(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static NetworkRequestQueue GetNetworkRequestQueue(Context context) {
        if (mNetworkRequestQueue == null) {
            synchronized (NetworkRequestQueue.class) {
                if (mNetworkRequestQueue == null) {
                    mNetworkRequestQueue = new NetworkRequestQueue(context);
                }
            }
        }
        return mNetworkRequestQueue;
    }

    public <T> void AddToNetRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        mRequestQueue.add(req);
    }

    public void AddStringToRequestQueue(StringRequest request) {
        mRequestQueue.add(request);
    }

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public <T> void AddToNetRequestQueue(Request<T> req) {
        req.setTag(TAG);
        mRequestQueue.add(req);
    }

    public void CancelNetRequestQueue(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
