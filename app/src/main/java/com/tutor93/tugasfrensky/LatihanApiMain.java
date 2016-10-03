package com.tutor93.tugasfrensky;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tutor93.tugasfrensky.latihanapi.ApiAdapter;
import com.tutor93.tugasfrensky.latihanapi.ApiPictAdapter;
import com.tutor93.tugasfrensky.latihanapi.BaseLatihanAPI;
import com.tutor93.tugasfrensky.latihanapi.DealAPIRequest;
import com.tutor93.tugasfrensky.latihanapi.DealingAPIResponse;
import com.tutor93.tugasfrensky.latihanapi.LatihanAPI;
import com.tutor93.tugasfrensky.latihanapi.LatihanAPIResponse;
import com.tutor93.tugasfrensky.latihanapi.PictureAPIResponse;
import com.tutor93.tugasfrensky.latihanapi.TaskGetDealing;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by indraaguslesmana on 9/26/16.
 */

public class LatihanApiMain extends AppCompatActivity implements View.OnClickListener, AbsListView.OnScrollListener {

    private int lastTopValue = 0;
    private ListView mListView;
    private ImageView employeImage;
    //    private ImageView mIvHeader;
    private LinearLayout mIvHeader;
    private ApiAdapter adapter;
    private ApiPictAdapter adapterPict;
    List<DealingAPIResponse> dataApi;
    List<PictureAPIResponse> dataPict;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihanapi);

        //employeImage = (ImageView) findViewById(R.id.employerpict_api);
        mListView = (ListView) findViewById(R.id.activity_main_lv_list);


        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.layout_list_header, mListView, false);
        mListView.addHeaderView(header, null, false);


        mIvHeader = (LinearLayout) header.findViewById(R.id.header_linierlayaout);
        mListView.setOnScrollListener(this);

        getDataApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_apiedit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_api:
                Intent intent = new Intent(this, ApiEmployeeForm.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    private void getDataApi() {
        final TaskGetDealing getDealing = new TaskGetDealing(this) {
            @Override
            protected void onDataReceived(LatihanAPIResponse dataResponse) {
                dataApi = dataResponse.getDealing_data();
                dataPict = dataResponse.getEmployee_picture();

                adapter = new ApiAdapter(LatihanApiMain.this, R.layout.activity_detaillistapi, dataApi);
                //adapterPict = new ApiPictAdapter(LatihanApiMain.this, R.layout.layout_list_header, dataPict);

                mListView.setAdapter(adapter);
            }

            @Override
            protected void onDataFailed(String message) {
                Toast.makeText(LatihanApiMain.this, "gagal load data API", Toast.LENGTH_SHORT).show();
            }
        };

        //executor
        getDealing.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "1");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Rect rect = new Rect();
        mIvHeader.getLocalVisibleRect(rect);
        if (lastTopValue != rect.top) {
            lastTopValue = rect.top;
            mIvHeader.setY((float) (rect.top / 2.0));
        }
    }
}

