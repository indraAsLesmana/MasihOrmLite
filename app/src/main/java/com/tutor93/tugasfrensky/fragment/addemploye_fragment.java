package com.tutor93.tugasfrensky.fragment;

import android.view.View;

import com.tutor93.tugasfrensky.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

/**
 * Created by indraaguslesmana on 10/9/16.
 */

public class addemploye_fragment extends BaseFragmentWithActionBar implements
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener {


    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setUICallbacks() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.add_employee);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.addemloyee_fragment;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
}
