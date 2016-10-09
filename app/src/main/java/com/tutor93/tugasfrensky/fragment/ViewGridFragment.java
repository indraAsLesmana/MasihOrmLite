package com.tutor93.tugasfrensky.fragment;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.activity.EmpAdapter;
import com.tutor93.tugasfrensky.activity.EmployeEntity;
import com.tutor93.tugasfrensky.activity.EmployeeModel;

import java.util.List;

/**
 * Created by indraaguslesmana on 10/5/16.
 */

public class ViewGridFragment extends BaseFragmentWithActionBar {




    @Override
    public void initView(View view) {

    }

    @Override
    public void setUICallbacks() {

    }

    @Override
    public void updateUI() {
        getBaseActivity().setLeftIcon(R.drawable.more_icon);

    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.view_as_grid);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_viewgrid;
    }

    public void refreshNavBar(){
        getBaseActivity().setActionBarTitle(getPageTitle());
        updateUI();
    }
}
