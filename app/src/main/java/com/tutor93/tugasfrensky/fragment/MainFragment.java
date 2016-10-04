package com.tutor93.tugasfrensky.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tutor93.tugasfrensky.R;

/**
 * Created by indraaguslesmana on 10/4/16.
 */

public class MainFragment extends BaseFragment {

    private TextView tv_addproduct;

    @Override
    public void initView(View view) {
        tv_addproduct = (TextView) view.findViewById(R.id.tv_noproduct);

    }

    @Override
    public void setUICallbacks() {

        tv_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Ke fragment yg lain rencananya..!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void updateUI() {

    }

    @Override
    public String getPageTitle() {
        return "Coba Fragment";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_noproduct;

    }
}
