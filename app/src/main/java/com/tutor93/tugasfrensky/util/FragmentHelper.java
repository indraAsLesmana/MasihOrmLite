package com.tutor93.tugasfrensky.util;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tutor93.tugasfrensky.fragment.BaseFragment;

/**
 * Created by frensky on 7/1/15.
 */
public class FragmentHelper {
    private FragmentManager fragmentManager;
    private static FragmentHelper fragmentHelper;

    public static FragmentHelper getInstance(FragmentManager fragmentManager) {
        if (fragmentHelper == null) {
            fragmentHelper = new FragmentHelper();
        }
        fragmentHelper.setFragmentManager(fragmentManager);
        return fragmentHelper;
    }

    public void replaceFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getPageTitle());
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(container, fragment);
        ft.commit();
    }

    public void addFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getPageTitle());
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.add(container, fragment);
        ft.commit();
    }

    public void replaceFragment(int container, Fragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getTag());
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(container, fragment);
        ft.commit();
    }

    public void addFragment(int container, Fragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getTag());
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.add(container, fragment);
        ft.commit();
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}

