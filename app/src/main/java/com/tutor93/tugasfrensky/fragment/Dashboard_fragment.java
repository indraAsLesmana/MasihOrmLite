package com.tutor93.tugasfrensky.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.activity.MainFragment;
import com.tutor93.tugasfrensky.callbacks.OnActionbarListener;

/**
 * Created by indraaguslesmana on 10/6/16.
 */

public class Dashboard_fragment extends BaseFragmentWithActionBar implements View.OnClickListener {

    private Button toEmployee, toProductview, toEmloyeeSalesinfo, toEmployeeProject, logout;
    private MainFragment activity;
    private DrawerLayout menu;
    private MainFragment mainFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = (MainFragment) getBaseActivity();
        super.onCreate(savedInstanceState);

        updateUI();
    }

    @Override
    public void initView(View view) {
        toEmployee = (Button) view.findViewById(R.id.to_employeview);
        toProductview = (Button) view.findViewById(R.id.to_productview);
        /*toEmloyeeSalesinfo = (Button) view.findViewById(R.id.toemployeesalesinformation);
        toEmployeeProject = (Button) view.findViewById(R.id.toemployeeprojectinfomation);
        logout = (Button) view.findViewById(R.id.logout);*/

    }

    @Override
    public void setUICallbacks() {
        toEmployee.setOnClickListener(this);
        toProductview.setOnClickListener(this);
       /* toProductview.setOnClickListener(this);
        toEmloyeeSalesinfo.setOnClickListener(this);
        toEmployeeProject.setOnClickListener(this);
        logout.setOnClickListener(this);
*/

        getBaseActivity().setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                Toast.makeText(activity, "on left cliked", Toast.LENGTH_SHORT).show();
                menu = getBaseActivity().menu;
                if (menu != null) {
                    if (menu.isDrawerOpen(GravityCompat.START)) {
                        menu.closeDrawer(Gravity.LEFT);
                        Toast.makeText(activity, "im close", Toast.LENGTH_SHORT).show();
                    } else {
                        menu.openDrawer(Gravity.LEFT);
                        Toast.makeText(activity, "im open", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onRightIconClick() {

            }

            @Override
            public void onRight2IconClick() {

            }

            @Override
            public void onSelectIconClick() {

            }
        });
    }

    @Override
    public void updateUI() {
        //getBaseActivity().setLeftIcon(R.drawable.more_icon);
        getBaseActivity().setActionBarTitle(getPageTitle());
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.dashboard);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_dasboard;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        /*switch (v){
            case toEmployee:
                intent = new Intent(getContext(), MainFragment.class);

        }*/
        AddProduct_fragment addProduct_fragment = new AddProduct_fragment();

        if (v == toEmployee) {
            intent = new Intent(getContext(), MainFragment.class);
            startActivity(intent);
        } else if (v == toProductview) {
            /*Toast.makeText(activity, "hit", Toast.LENGTH_SHORT).show();
            intent = new Intent(getContext(), AddProduct_fragment.class);
            startActivity(intent);*/

            AddProduct_fragment nextFrag = new AddProduct_fragment();
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.dashboard_mainmenu_view, nextFrag, "add product")
                    .addToBackStack(null)
                    .commit();
        }

    }


}
