package com.tutor93.tugasfrensky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String LOG = "MainActivity";
    //define button
    Button btnGrid, btnList;
    LinearLayout gridView, listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (LinearLayout) findViewById(R.id.gridView);
        listView = (LinearLayout) findViewById(R.id.listView);

        btnGrid = (Button) findViewById(R.id.gridBtn);
        btnList = (Button) findViewById(R.id.btnList);

        btnGrid.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG, LOG + " on resume!!!!!");
    /*saya harus load database lagi  disini*/
    }

    @Override
    public void onClick(View view) {
        if (view == btnGrid) {
            listView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        } else if (view == btnList) {
            gridView.setVisibility(View.GONE);
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
