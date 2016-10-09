package com.tutor93.tugasfrensky.latihanapi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tutor93.tugasfrensky.activity.ApiEmployeeForm;
import com.tutor93.tugasfrensky.R;

import java.util.List;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

/**
 * Created by indra on 11/09/2016.
 */
public class ApiAdapter extends ArrayAdapter<DealingAPIResponse> implements View.OnClickListener {

    private Context mContext;
    private int row;
    private List<DealingAPIResponse> dealingApiResponsedata;
    DealingAPIResponse helper;
    private LatihanAPI sAPIservice;
   /* ViewHolder holder;*/

    public ApiAdapter(Context context, int textViewResourceId, List<DealingAPIResponse> dealingApiResponsedata) {
        super(context, textViewResourceId, dealingApiResponsedata);
        this.mContext = context;
        this.row = textViewResourceId;
        this.dealingApiResponsedata = dealingApiResponsedata;

        /*initial data*/
        this.helper = new DealingAPIResponse();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //build AAPI service
        sAPIservice = (new RestAdapter.Builder()
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
//                            errorDescription = getString(R.string.error_no_network);
                            Log.d("Error", "Network Error");
                        }
                        return null;
                    }
                })
                .build()).create(LatihanAPI.class);


        View view = convertView;
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final DealingAPIResponse obj = dealingApiResponsedata.get(position);

        holder.name = (TextView) view.findViewById(R.id.company_latihanapi);
        holder.address = (TextView) view.findViewById(R.id.address_latihanapi);
        holder.request = (TextView) view.findViewById(R.id.request_latihanapi);
        holder.cpname = (TextView) view.findViewById(R.id.cpname_latihanapi);

        holder.call = (Button) view.findViewById(R.id.api_btncall);
        holder.edit = (Button) view.findViewById(R.id.api_btnedit);
        holder.location = (Button) view.findViewById(R.id.api_btnlocation);
        holder.delete = (Button) view.findViewById(R.id.api_btndelete);
        holder.cp_picture = (ImageView) view.findViewById(R.id.cppicture_api);


        holder.employeImage = (ImageView) view.findViewById(R.id.employerpict_api);

        Glide.with(mContext)
                .load(obj.picture)
                .into(holder.cp_picture);

        //handle button
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, obj.getContact_number(), Toast.LENGTH_SHORT).show();
                /*Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(obj.getContact_number()));
                mContext.startActivity(callIntent);*/
                Toast.makeText(mContext, obj.getPicture(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "clicked edit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ApiEmployeeForm.class);

                //intent.putExtra("id", obj.getId());
                intent.putExtra("company_name", obj.getName());
                intent.putExtra("address", obj.getAddress());
                intent.putExtra("request", obj.getRequest());
                intent.putExtra("contact_name", obj.getName());
                intent.putExtra("contact_number", obj.getContact_number());

                mContext.startActivity(intent);
            }
        });

        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "clicked Location", Toast.LENGTH_SHORT).show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sAPIservice.deleteDealing(String.valueOf(obj.getId()), new Callback<LatihanAPIResponse>() {
                    @Override
                    public void success(LatihanAPIResponse latihanAPIResponse, Response response) {
                        Toast.makeText(mContext, "Delete success..!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        if (null != holder.name && null != obj && obj.getName().length() != 0) {
            holder.name.setText(obj.getName());
            holder.address.setText(obj.getAddress());
            holder.request.setText(obj.getRequest());
            holder.cpname.setText(obj.getName());
        }

        return view;
    }

    @Override
    public void onClick(View view) {

    }

    public static class ViewHolder {

        //        int id;
        public TextView name;
        public TextView cpname;
        public TextView address;
        public TextView request;

        public Button call;
        public Button location;
        public Button delete;
        public Button edit;

        public ImageView cp_picture;

        public ImageView employeImage;

    }
}
