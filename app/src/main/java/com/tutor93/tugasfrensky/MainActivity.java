package com.tutor93.tugasfrensky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String LOG = "MainActivity";
    //define button
    Button btnGrid, btnList;
    ListView listView;
    GridView gridView;
    EmpAdapter adapter;

    LinearLayout gridViewFrame;
    List<EmployeEntity> list;

    EmployeeModel helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.halamandepan_gridview);
        listView = (ListView) findViewById(R.id.halamandepan_listview);

        gridViewFrame = (LinearLayout) findViewById(R.id.gridView);


        btnGrid = (Button) findViewById(R.id.gridBtn);
        btnList = (Button) findViewById(R.id.btnList);

        btnGrid.setOnClickListener(this);
        btnList.setOnClickListener(this);

        //solving masalah null pake ini.
        this.helper = new EmployeeModel(this);

        /*langsung diklik ketika, onCreate
        * */

        btnGrid.performClick();
//        setDataToAdapter_listview();
        setDataToAdapter_gridview();

        /*//coba gridview
        GridView gridview = (GridView) findViewById(R.id.halamandepan_gridview);  
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }


    @Override
    protected void onResume() {
        super.onResume();
//        setDataToAdapter_listview();
        setDataToAdapter_gridview();
        Log.d(LOG, LOG + " on resume!!!!!");

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

    @Override
    public void onClick(View view) {
        if (view == btnGrid) {
            listView.setVisibility(View.GONE);
            gridViewFrame.setVisibility(View.VISIBLE);
        } else if (view == btnList) {
            gridViewFrame.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
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
            case R.id.action_favorite:
                Intent intent = new Intent(this, Add_employee.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
