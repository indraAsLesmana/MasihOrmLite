package com.tutor93.tugasfrensky.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.activity.ApiEmployeeForm;
import com.tutor93.tugasfrensky.fragment.BaseFragmentWithActionBar;
import com.tutor93.tugasfrensky.latihanapi.ApiAdapter;
import com.tutor93.tugasfrensky.latihanapi.ApiPictAdapter;
import com.tutor93.tugasfrensky.latihanapi.DealingAPIResponse;
import com.tutor93.tugasfrensky.latihanapi.LatihanAPIResponse;
import com.tutor93.tugasfrensky.latihanapi.PictureAPIResponse;
import com.tutor93.tugasfrensky.latihanapi.TaskGetDealing;

import java.util.List;

/**
 * Created by indraaguslesmana on 9/26/16.
 */

public class LatihanApiMain_fragment extends BaseFragmentWithActionBar implements View.OnClickListener, AbsListView.OnScrollListener {

    private int lastTopValue = 0;
    private ListView mListView;
    private ImageView employeImage;
    //    private ImageView mIvHeader;
    private LinearLayout mIvHeader;
    private ApiAdapter adapter;
    private ApiPictAdapter adapterPict;
    List<DealingAPIResponse> dataApi;
    List<PictureAPIResponse> dataPict;

    /*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihanapi);


    }*/




    @Override
    public void initView(View view) {
        //employeImage = (ImageView) findViewById(R.id.employerpict_api);
        mListView = (ListView) view.findViewById(R.id.activity_main_lv_list);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.layout_list_header, mListView, false);
        mListView.addHeaderView(header, null, false);

        mIvHeader = (LinearLayout) header.findViewById(R.id.header_linierlayaout);
        mListView.setOnScrollListener(this);

        getDataApi();

    }

    @Override
    public void setUICallbacks() {

    }


    @Override
    public void updateUI() {
        getBaseActivity().setActionBarTitle(getPageTitle());
        getBaseActivity().setRightIcon2(R.drawable.add_purple_icon);

    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.latihan_api);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_latihanapi;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.activity_apiedit, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_api:
                Intent intent = new Intent(getActivity(), ApiEmployeeForm.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    private void getDataApi() {
        final TaskGetDealing getDealing = new TaskGetDealing(getActivity()) {
            @Override
            protected void onDataReceived(LatihanAPIResponse dataResponse) {
                dataApi = dataResponse.getDealing_data();
                dataPict = dataResponse.getEmployee_picture();

                adapter = new ApiAdapter(getActivity(), R.layout.activity_detaillistapi, dataApi);
                //adapterPict = new ApiPictAdapter(LatihanApiMain_fragment.this, R.layout.layout_list_header, dataPict);

                mListView.setAdapter(adapter);
            }

            @Override
            protected void onDataFailed(String message) {
                Toast.makeText(getActivity(), "gagal load data API", Toast.LENGTH_SHORT).show();
            }
        };

        //executor
        getDealing.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "1");
    }

    public void refreshNavBar() {
        getBaseActivity().setActionBarTitle(getPageTitle());
        updateUI();
    }
}

