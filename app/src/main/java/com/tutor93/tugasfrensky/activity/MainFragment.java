package com.tutor93.tugasfrensky.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.adapter.MainMenuAdapter;
import com.tutor93.tugasfrensky.fragment.Dashboard_fragment;
import com.tutor93.tugasfrensky.fragment.LatihanApiMain_fragment;
import com.tutor93.tugasfrensky.fragment.ViewGridFragment;
import com.tutor93.tugasfrensky.fragment.ViewListFragment;
import com.tutor93.tugasfrensky.model.MainMenuModel;
import com.tutor93.tugasfrensky.util.FunctionUtil;

import java.util.ArrayList;

/**
 * Created by indraaguslesmana on 10/4/16.
 */

public class MainFragment extends BaseActivityWithActionBar implements AdapterView.OnItemClickListener {

    private Toolbar toolbars;
    private ViewFlipper dashboardFlipper;
    private TextView versions;

    private MainMenuAdapter adapter;
    private ListView menuList;

    private ArrayList<MainMenuModel> menuData;
    private View menuHeader;

    private Bundle bundles;
    private ViewGridFragment viewAsGridFragment;
    private ViewListFragment viewAsListFragment;
    private LatihanApiMain_fragment latihanApiMainFragment;
    private Dashboard_fragment dashboard_mainmenu;
    private int currentSelectedTab;
    final static String CURRENT_INDEX = "CURRENT_INDEX";


    final public static String VIEW_GRID = "VIEW GRID";
    final public static String VIEW_LIST = "VIEW LIST";
    final public static String DASHBOARD = "DASHBOARD";
    final public static String LATIHAN_API = "LATIHAN API";

    final public static String WELCOME = "Welcome";

    private static final String LOG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get fullscreen
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        menu = (DrawerLayout) findViewById(R.id.dashboard_drawer_layout);

        dashboardFlipper = (ViewFlipper) findViewById(R.id.dashboard_flipper);
        versions = (TextView) findViewById(R.id.dashboard_versions);
        menuList = (ListView) findViewById(R.id.dashboard_list_menu);

        menuHeader = View.inflate(this, R.layout.menu_header_layout, null);

        toolbars = (Toolbar) findViewById(R.id.dashboard_hack_toolbar);
        setSupportActionBar(toolbars);
        menu.setStatusBarBackgroundColor(this.getResources().getColor(R.color.actionbar_color));

        menuList.addHeaderView(menuHeader);

        menuData = new ArrayList<>();
        String[] resource;
        TypedArray imgs;

        resource = getResources().getStringArray(R.array.main_menu);
        imgs = getResources().obtainTypedArray(R.array.menu_images);

        for (int i = 0; i < resource.length; i++) {
            MainMenuModel model = new MainMenuModel();
            model.setName(resource[i]);
            model.setResourceDrawableSelected(imgs.getResourceId(i, -1));
            model.setResourceDrawableUnselected(imgs.getResourceId(i, -1));
            menuData.add(model);
        }
        adapter = new MainMenuAdapter(this, menuData);
        menuList.setAdapter(adapter);

        if (bundles == null) {

            viewAsGridFragment = new ViewGridFragment();
            viewAsListFragment = new ViewListFragment();
            dashboard_mainmenu = new Dashboard_fragment();
            latihanApiMainFragment = new LatihanApiMain_fragment();

            replaceFragmentwithTag(R.id.dashboard_viewaslist, viewAsListFragment, false, VIEW_LIST);
            replaceFragmentwithTag(R.id.dashboard_viewasgrid, viewAsGridFragment, false, VIEW_GRID);
            replaceFragmentwithTag(R.id.dashboard_mainmenu_view, dashboard_mainmenu, false, DASHBOARD);
            replaceFragmentwithTag(R.id.dashboard_latiahnapi, latihanApiMainFragment, false, LATIHAN_API);

        } else {
            currentSelectedTab = bundles.getInt(CURRENT_INDEX, 0);

            Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(VIEW_LIST);
            if (fragment1 instanceof ViewListFragment) {
                viewAsListFragment = (ViewListFragment) fragment1;
            }

            Fragment fragment2 = getSupportFragmentManager().findFragmentByTag(VIEW_GRID);
            if (fragment2 instanceof ViewGridFragment) {
                viewAsGridFragment = (ViewGridFragment) fragment2;
            }
            Fragment fragment3 = getSupportFragmentManager().findFragmentByTag(DASHBOARD);
            if (fragment3 instanceof Dashboard_fragment) {
                dashboard_mainmenu = (Dashboard_fragment) fragment3;
            }
            Fragment fragment4 = getSupportFragmentManager().findFragmentByTag(LATIHAN_API);
            if (fragment4 instanceof LatihanApiMain_fragment) {
                latihanApiMainFragment = (LatihanApiMain_fragment) fragment4;
            }

        }

    }


    @Override
    public void setUICallbacks() {
        menuList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

        if (i > 0) {
            i = i - 1;
        }

        int selectedIndex = i;

        String selectedItem = menuData.get(selectedIndex).getName();
            /*APP.logError("SELECTED ITEM : " + selectedItem);*/
        String[] resource = getResources().getStringArray(R.array.main_menu);

        for (int q = 0; q < resource.length; q++) {
            if (resource[q].equalsIgnoreCase(selectedItem)) {
                selectedIndex = q;
                break;
            }
        }

        if (currentSelectedTab != selectedIndex) {
            currentSelectedTab = selectedIndex;
            adapter.notifyDataSetChanged(i);
            dashboardFlipper.setDisplayedChild(currentSelectedTab);
            onSelectedTab(currentSelectedTab);
        }

        menu.closeDrawer(Gravity.LEFT);
    }


    @Override
    public int getLayout() {
        return R.layout.dashboard_layout;
    }

    @Override
    public void updateUI() {

        /*dashboardFlipper.setDisplayedChild(0);*/
      /*  dashboardFlipper.setDisplayedChild(currentSelectedTab);
        onSelectedTab(currentSelectedTab);*/
        this.setActionBarTitle(DASHBOARD);
    }


    private FragmentManager.OnBackStackChangedListener getListener() {

        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FunctionUtil.hideKeyboard(MainFragment.this);
                FragmentManager manager = getSupportFragmentManager();
                /*if (manager != null) {
                    if (dashboard.isVisible()) {
                        dashboard.onResume();
                    }
                }*/
            }
        };

        return result;
    }

    private void onSelectedTab(int i) {
        /*if (i == 0) {
            Toast.makeText(context, "im in touch LIST", Toast.LENGTH_SHORT).show();
            viewAsListFragment.refreshNavBar();
        } else if (i == 1) {
            Toast.makeText(context, "im in touch GRID", Toast.LENGTH_SHORT).show();
            viewAsGridFragment.refreshNavBar();

        }*/

        switch (i) {
            case 0:
                /*viewAsListFragment.refreshNavBar();*/
                return;
            case 1:
                viewAsListFragment.refreshNavBar();
                return;
            case 2:
                viewAsGridFragment.refreshNavBar();
                return;
            case 3:
                latihanApiMainFragment.refreshNavBar();
                return;

        }
    }

    public DrawerLayout getMenu() {
        return menu;
    }


}
