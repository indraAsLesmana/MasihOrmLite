package com.tutor93.tugasfrensky;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indraaguslesmana on 9/26/16.
 */

public class LatihanApiMain extends AppCompatActivity implements View.OnClickListener, AbsListView.OnScrollListener {

    private static final int SIZE = 50;
    private int lastTopValue = 0;
    private List<String> mData = new ArrayList<>();
    private ListView mListView;
//    private ImageView mIvHeader;
    private LinearLayout mIvHeader;

    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihanapi);

        mListView = (ListView) findViewById(R.id.activity_main_lv_list);

        for (int i = 0; i < SIZE; i++) {
            mData.add("AwpSpace " + i);
        }

        mAdapter = new ArrayAdapter(this, R.layout.activity_detaillistapi, R.id.request_latihanapi, mData);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.layout_list_header, mListView, false);
        mListView.addHeaderView(header, null, false);
        mListView.setAdapter(mAdapter);
        mIvHeader = (LinearLayout) header.findViewById(R.id.header_linierlayaout);
        mListView.setOnScrollListener(this);

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
