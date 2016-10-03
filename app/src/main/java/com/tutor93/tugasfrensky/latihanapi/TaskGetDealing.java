package com.tutor93.tugasfrensky.latihanapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by frensky on 7/31/15.
 */
public abstract class TaskGetDealing extends AsyncTask<String, Void, Boolean> {
    private Context context;
    RestAdapter restAdapter;
    LatihanAPIResponse response;
    private ProgressDialog mProgressDialog;

    public TaskGetDealing(Context context) {
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading Data Latihan....");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        restAdapter = BaseLatihanAPI.getLatihanAPI();
        mProgressDialog.show();

        /*if (null != BaseLatihanAPI.getLatihanAPI()) {
            restAdapter = BaseLatihanAPI.getLatihanAPI();
            mProgressDialog.show();
        }*/
    }

    @Override
    protected Boolean doInBackground(String... userIds) {
        LatihanAPI methods = restAdapter.create(LatihanAPI.class);
        String param = userIds[0];
        try {
            response = methods.getDealing(param);
        } catch (RetrofitError e) {
            onDataFailed(e.getResponse().getReason());
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        if (aBoolean == true) {
            onDataReceived(response);
        } else {
            onDataFailed("failed achinving data");
        }

    }

    protected abstract void onDataReceived(LatihanAPIResponse dataResponse);

    protected abstract void onDataFailed(String message);

    /*
    * Cara manggil API ini nantinya kaya gini contoh kasusnya pada onclick button something
    *
    *
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = "0";
                TaskGetDealing getDealing = new TaskGetDealing() {
                    @Override
                    protected void onDataReceived(LatihanAPIResponse dataResponse) {

                    }

                    @Override
                    protected void onDataFailed(String message) {

                    }
                };

                getDealing.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userId);

            }
        });
    *
    *
    * */

}