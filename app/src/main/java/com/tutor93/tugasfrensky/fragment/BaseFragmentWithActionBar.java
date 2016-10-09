package com.tutor93.tugasfrensky.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.activity.BaseActivityWithActionBar;
/*import com.medictrust.application.APP;*/
import com.tutor93.tugasfrensky.base.FragmentInterface;
import com.tutor93.tugasfrensky.fragment.BaseFragment;

import java.lang.ref.WeakReference;

/**
 * Created by frensky on 25/01/2016.
 */
public abstract class BaseFragmentWithActionBar extends Fragment implements FragmentInterface {
    protected Activity activity;
    protected boolean hasLoadDataFromAPI;
    private LinearLayout backButton;
    private WeakReference<Activity> wrActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        initView(view);
       /* if(view.findViewById(R.id.imbn_back)!=null){
            backButton = (LinearLayout) view.findViewById(R.id.imbn_back);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }*/
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*APP.log("CREATED  WITH : " + getPageTitle());*/
        getBaseActivity().setDefaultActionbarIcon();
        updateUI();
        setUICallbacks();
        /*getBaseActivity().setActionBarTitle("Testing");*/
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        wrActivity = new WeakReference<Activity>(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.activity  =(Activity) context;
            wrActivity = new WeakReference<Activity>((Activity) context);
        }
    }

    public BaseActivityWithActionBar getBaseActivity() {
        return ((BaseActivityWithActionBar) activity);
    }

    public Activity getWeakReferenceActivity() {
        if(wrActivity == null){
            return null;
        }
        else{
            return wrActivity.get();
        }
    }


    public void replaceFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        if(activity != null){
            if(!activity.isFinishing()){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (addBackToStack) {
                    ft.addToBackStack(fragment.getPageTitle());
                }
                //ft.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
                ft.replace(container, fragment);
                ft.commit();
            }
        }
    }

    public void replaceFragment(int container, Fragment fragment, boolean addBackToStack) {
        if(activity != null){
            if(!activity.isFinishing()){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (addBackToStack) {
                    ft.addToBackStack(null);
                }
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.replace(container, fragment);
                ft.commit();
            }
        }
    }

    public void addFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        if(activity != null){
            if(!activity.isFinishing()){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (addBackToStack) {
                    ft.addToBackStack(fragment.getPageTitle());
                }
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.add(container, fragment);
                ft.commit();
            }
        }
    }

    public void addChildFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        if(activity != null){
            if(!activity.isFinishing()){
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                if (addBackToStack) {
                    ft.addToBackStack(fragment.getPageTitle());
                }
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.add(container, fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }


    public String checkNullString(String string) {
        return (string == null) ? "" : string;
    }
}

