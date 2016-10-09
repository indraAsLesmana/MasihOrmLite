package com.tutor93.tugasfrensky.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.latihanapi.DealAPIRequest;
import com.tutor93.tugasfrensky.latihanapi.DealingAPIResponse;
import com.tutor93.tugasfrensky.latihanapi.LatihanAPI;
import com.tutor93.tugasfrensky.latihanapi.LatihanAPIResponse;
import com.tutor93.tugasfrensky.view.ButtonRegular;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;


/**
 * Created by indraaguslesmana on 9/29/16.
 */

public class ApiEmployeeForm extends BaseActivityWithActionBar implements View.OnClickListener {

    private EditText iduser, companyname, address, request, contact_name, contact_number;
    private LatihanAPI sAPIservice;
    private DealingAPIResponse dealingAPIResponse;
    private Menu menu;
    private boolean changeTitle = true;
    private ButtonRegular save_api;
    private ButtonRegular add_new;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        //check if Edit
        checkIntent();

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_apiedit, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_save_api:
                // load method save ke API disini
                editEmployee();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }*/


    @Override
    public void onClick(View v) {

    }


    @Override
    public void initView() {
        iduser = (EditText) findViewById(R.id.iduser);
        companyname = (EditText) findViewById(R.id.company_txlatihanapi);
        address = (EditText) findViewById(R.id.address_txlatihanapi);
        request = (EditText) findViewById(R.id.request_txlatihanapi);
        contact_name = (EditText) findViewById(R.id.contactname_txlatihanapi);
        contact_number = (EditText) findViewById(R.id.contactnumber_txlatihanapi);
        save_api = (ButtonRegular) findViewById(R.id.save_api);
        add_new = (ButtonRegular) findViewById(R.id.add_new_api);
    }

    @Override
    public void setUICallbacks() {
        save_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEmployee();
            }
        });

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_apieditemployee;
    }

    @Override
    public void updateUI() {
        this.setActionBarTitle(getResources().getString(R.string.latihan_api));
    }


    private void checkIntent() {

        if (!getIntent().equals(null)) {
//            Toast.makeText(this, getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();

            //iduser.setText(getIntent().getStringExtra("id"));
            companyname.setText(getIntent().getStringExtra("company_name"));
            address.setText(getIntent().getStringExtra("address"));
            request.setText(getIntent().getStringExtra("request"));
            contact_name.setText(getIntent().getStringExtra("contact_name"));
            contact_number.setText(getIntent().getStringExtra("contact_number"));
        /*    DealAPIRequest dealAPIRequest = new DealAPIRequest();
            dealAPIRequest.setName(companyname.getText());
        */
        }
    }

    private void editEmployee() {
        DealAPIRequest dealAPIRequest = new DealAPIRequest();

        dealAPIRequest.setId("1");
        dealAPIRequest.setName(companyname.getText().toString());
        dealAPIRequest.setAddress(address.getText().toString());
        dealAPIRequest.setRequest(request.getText().toString());
        dealAPIRequest.setContact_person(contact_name.getText().toString());
        dealAPIRequest.setContact_number(contact_number.getText().toString());

        sAPIservice.editDealing(dealAPIRequest, new Callback<LatihanAPIResponse>() {

            @Override
            public void success(LatihanAPIResponse latihanAPIResponse, Response response) {
                Toast.makeText(ApiEmployeeForm.this, "Edit success..!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ApiEmployeeForm.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void addEmployee() {
        DealAPIRequest dealAPIRequest = new DealAPIRequest();

        dealAPIRequest.setId("1");
        dealAPIRequest.setName(companyname.getText().toString());
        dealAPIRequest.setAddress(address.getText().toString());
        dealAPIRequest.setRequest(request.getText().toString());
        dealAPIRequest.setContact_person(contact_name.getText().toString());
        dealAPIRequest.setContact_number(contact_number.getText().toString());

        /*if(!getIntent().getStringExtra("id").equals(null)){
        }*/

        //sAPIservice.addDealing("4", dealAPIRequest);

        sAPIservice.addDealing(dealAPIRequest.getId(), dealAPIRequest, new Callback<LatihanAPIResponse>() {

            @Override
            public void success(LatihanAPIResponse latihanAPIResponse, Response response) {
                Toast.makeText(ApiEmployeeForm.this, "add success..!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ApiEmployeeForm.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void updateMenuTitles() {
        MenuItem menuName = menu.findItem(R.id.action_save_api);
        if (changeTitle) {
            menuName.setTitle("ADD");
        } else {
            menuName.setTitle("SAVE");
        }
    }*/
}
