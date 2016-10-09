package com.tutor93.tugasfrensky.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.fragment.LatihanApiMain_fragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String LOG = "MainActivity";
    //define button
    private ImageButton btnGrid, btnList;
    private ListView listView;
    private GridView gridView;
    private EmpAdapter adapter;


    private LinearLayout gridViewFrame;
    private List<EmployeEntity> list;

    EmployeeModel helper;
    boolean isFirst ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.halamandepan_gridview);
        listView = (ListView) findViewById(R.id.halamandepan_listview);

        gridViewFrame = (LinearLayout) findViewById(R.id.gridView);


        //solving masalah null pake ini.
        this.helper = new EmployeeModel(this);
        isFirst =true;



        setDataToAdapter_listview();
        setDataToAdapter_gridview();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_grid) {
                    listView.setVisibility(View.GONE);
                    gridViewFrame.setVisibility(View.VISIBLE);
                    //load data ulang
                    setDataToAdapter_gridview();
                }else{
                    gridViewFrame.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    //load data ulang
                    setDataToAdapter_listview();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(isFirst){
            isFirst = false;
            onClick(btnGrid);
        }

        setDataToAdapter_listview();
        setDataToAdapter_gridview();
        Log.d(LOG, LOG + " on resume!!!!!");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_toActivityApi:
                Intent intentApiActivity = new Intent(this, LatihanApiMain_fragment.class);
                startActivity(intentApiActivity);
                return true;

            case R.id.action_toFragment:
                Intent intent = new Intent(this, Add_employee.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onClick(View view) {
        if (view == btnGrid) {
            listView.setVisibility(View.GONE);
            gridViewFrame.setVisibility(View.VISIBLE);
            //load data ulang
            setDataToAdapter_gridview();
        } else if (view == btnList) {
            gridViewFrame.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            //load data ulang
            setDataToAdapter_listview();
        }
    }



    private void setDataToAdapter_listview() {
        list = helper.getAllEmployee();
        adapter = new EmpAdapter(this, R.layout.row2, list);
        listView.setAdapter(adapter);
    }

    private void setDataToAdapter_gridview() {
        list = helper.getAllEmployee();
        adapter = new EmpAdapter(this, R.layout.grid2, list);
        gridView.setAdapter(adapter);
    }

}
