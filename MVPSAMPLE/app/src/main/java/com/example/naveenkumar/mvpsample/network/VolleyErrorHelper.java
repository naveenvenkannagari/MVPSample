package com.example.naveenkumar.mvpsample.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;



public class VolleyErrorHelper {

    public static String GetErrorMessage(VolleyError error, Context context) {
        if (error instanceof TimeoutError) {
            return "Server Down";
        } else if (isNetworkProblem(error)) {
            return "Network Error";
        } else if (isServerProblem(error)) {
            return "Server Error";
        }
        return "Generic Error";
    }

    private static boolean isNetworkProblem(VolleyError error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    private static boolean isServerProblem(VolleyError error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    private static String handleServerError(VolleyError error, Context context) {
        NetworkResponse response = error.networkResponse;
        String errorMessage = "Server Down";
        if (response != null) {
            errorMessage = new String(error.networkResponse.data);
        }
        return errorMessage;
    }
}

