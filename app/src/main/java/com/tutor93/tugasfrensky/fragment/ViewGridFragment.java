package com.tutor93.tugasfrensky.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.activity.Add_employee;
import com.tutor93.tugasfrensky.activity.EmployeEntity;
import com.tutor93.tugasfrensky.activity.EmployeeModel;
import com.tutor93.tugasfrensky.adapter.RecycleGridAdapter;
import com.tutor93.tugasfrensky.widgets.GridMarginDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indraaguslesmana on 10/5/16.
 */

public class ViewGridFragment extends BaseFragmentWithActionBar implements RecycleGridAdapter.OnGridItemSelectedListener {

    private RecyclerView lvSingle;
    private GridLayoutManager gridLayoutManager;
    private RecycleGridAdapter recycleGridAdapter;
    private EmployeeModel helper;

    private Context context;
    private ViewGroup container;
    private LayoutInflater inflater;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = context;
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewgrid, container, false);

        lvSingle = (RecyclerView) rootView.findViewById(R.id.employee_recycleview);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //solving masalah null pake ini.
        this.helper = new EmployeeModel(getBaseActivity());

        recycleGridAdapter = new RecycleGridAdapter(this, getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        lvSingle.setLayoutManager(gridLayoutManager);
        lvSingle.addItemDecoration(new GridMarginDecoration(getContext(), 0, 0, 0, 0));
        lvSingle.setAdapter(recycleGridAdapter);

        loadData();
    }

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

    @Override
    public void onGridItemClick(View v, int position) {
        /*Toast.makeText(getContext(), recycleGridAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();*/
        Intent myIntent = new Intent(getContext(), Add_employee.class);
        myIntent.putExtra("id", recycleGridAdapter.getItem(position).getId());
        getContext().startActivity(myIntent);
    }


    public void loadData() {
        List<EmployeEntity> singleList = new ArrayList<>();
        EmployeEntity single;

        singleList = helper.getAllEmployee();
        /*adapter = new EmpAdapter(this, R.layout.row2, list);
        listView.setAdapter(adapter);*/

        recycleGridAdapter.addAll(singleList);
    }
}
