package com.tutor93.tugasfrensky.latihanapi;

import android.util.Log;
import android.widget.Toast;

import com.tutor93.tugasfrensky.R;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;

/**
 * Created by frensky on 7/31/15.
 */
public class BaseLatihanAPI {

    public static RestAdapter getLatihanAPI(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://private-13a03-latihanapi.apiary-mock.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("LATIHAN_API"))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json;versions=1");
                    }
                })
                .setErrorHandler(new ErrorHandler() {
                    String errorDescription;
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        if (cause.getKind().equals(RetrofitError.Kind.NETWORK)) {
                            /*errorDescription = getString(R.string.error_no_network);*/
                            Log.d("Error", "Network Error");

                        }
                        return null;
                    }
                })
                .build();
        return restAdapter;
    }
}
