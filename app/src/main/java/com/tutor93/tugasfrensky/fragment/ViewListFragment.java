package com.tutor93.tugasfrensky.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.activity.EmpAdapter;
import com.tutor93.tugasfrensky.activity.EmployeEntity;
import com.tutor93.tugasfrensky.activity.EmployeeModel;
import com.tutor93.tugasfrensky.callbacks.OnActionbarListener;

import java.util.List;

/**
 * Created by indraaguslesmana on 10/5/16.
 */

public class ViewListFragment extends BaseFragmentWithActionBar {


    //local
    private ListView listView;
    private EmpAdapter empAdapter;
    private List<EmployeEntity> list;
    private EmployeeModel helper;
    private Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //solving masalah null pake ini..
        if (getActivity() != null ){
            this.helper = new EmployeeModel(getContext());
        }

    }

    @Override
    public void initView(View view) {
        listView = (ListView) view.findViewById(R.id.fragment_listview);


    }

    @Override
    public void setUICallbacks() {

        getBaseActivity().setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRightIconClick() {
                Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRight2IconClick() {
                Toast.makeText(context, "im in touch", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSelectIconClick() {
                Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void updateUI() {
        getBaseActivity().setLeftIcon(R.drawable.more_icon);
        getBaseActivity().setRightIcon2(R.drawable.add_purple_icon);
        //masih error load data.
        setDataToAdapter_listview();
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.view_as_list);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_viewlist;
    }

    public void refreshNavBar() {
        getBaseActivity().setActionBarTitle(getPageTitle());
        updateUI();
        /*setUICallbacks();*/

    }


    private void setDataToAdapter_listview() {
        /*pake context langsung force close
        *pake getContext(), getActivity() ... running tapi nggak nampil di fragment
        * R.layout.fragment_viewlist diganti dgn getFragmentLayout ... running tapi nggak nampil di fragment
        * */
        list = helper.getAllEmployee();
        empAdapter = new EmpAdapter(getContext(), R.layout.fragment_detailgrid, list);
        listView.setAdapter(empAdapter);
    }
}
