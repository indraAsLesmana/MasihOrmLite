package com.tutor93.tugasfrensky.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.base.ActivityInterface;
import com.tutor93.tugasfrensky.callbacks.OnActionbarListener;
import com.tutor93.tugasfrensky.fragment.BaseFragment;
import com.tutor93.tugasfrensky.fragment.dialog.AlertDialog;
import com.tutor93.tugasfrensky.util.FragmentHelper;
import com.tutor93.tugasfrensky.util.TextUtil;


/**
 * Created by frensky on 7/2/15.
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityInterface {
    public static final int TRANSITION_IN_IN = R.anim.slide_in_left;
    public static final int TRANSITION_IN_OUT = R.anim.slide_out_right;
    public static final int TRANSITION_OUT_IN = R.anim.slide_in_right;
    public static final int TRANSITION_OUT_OUT = R.anim.slide_out_left;

    private final int TRANSITION_REQUEST_CODE = 391;
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;
    private boolean showSlidingMenu = true;
    protected Context context;
    private View actionBarView;
    private FragmentHelper fragmentHelper;
    private OnActionbarListener actionbarListener;
    private TextView tvActionBarTitle;
    private ImageView leftIcon, rightIcon1, rightIcon2;
    protected boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHelper = FragmentHelper.getInstance(getSupportFragmentManager());
        context = this;
        isRunning = true;

        initSharedPreference();
        setContentView(getLayout());
        initView();
        setUICallbacks();
        setFont();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.dark_purple));
        }

    }

    private void setFont() {
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        TextUtil.getInstance(this).setFont(rootView);
    }

    private void initSharedPreference() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    public boolean isActivityRunning() {
        return isRunning;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setActionBarColor(int resColor) {
        actionBarView.setBackgroundResource(resColor);
    }

    public boolean isShowSlidingMenu() {
        return showSlidingMenu;
    }

    @Override
    public void onBackPressed() {
        int stackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (stackCount > 0) {
            try {
                getFragmentManager().popBackStack();
            } catch (IllegalStateException ignored) {

            }
        } else {
            try {
                super.onBackPressed();
            } catch (IllegalStateException ignored) {

            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    public FragmentHelper getFragmentHelper() {
        return fragmentHelper;
    }

    public void replaceFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        if (!isFinishing()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (addBackToStack) {
                ft.addToBackStack(fragment.getPageTitle());
            }
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            ft.replace(container, fragment);
            ft.commit();
        }
    }

    public void replaceFragment(int container, Fragment fragment, boolean addBackToStack) {
        if (!isFinishing()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (addBackToStack) {
                ft.addToBackStack(fragment.getTag());
            }
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            ft.replace(container, fragment);
            ft.commit();
        }
    }

    public void addFragment(int container, BaseFragment fragment, boolean addBackToStack) {
        if (!isFinishing()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (addBackToStack) {
                ft.addToBackStack(fragment.getPageTitle());
            }
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            ft.add(container, fragment);
            ft.commit();
        }
    }

    public void addFragment(int container, Fragment fragment, boolean addBackToStack) {
        if (!isFinishing()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (addBackToStack) {
                ft.addToBackStack(fragment.getTag());
            }
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            ft.add(container, fragment);
            ft.commit();
        }
    }

    public void changeActivity(Class<?> destination) {
        changeActivity(destination, false, null, 0);
    }

    public void changeActivity(Class<?> destination, int flags) {
        changeActivity(destination, false, null, flags);
    }

    public void changeActivity(Class<?> destination, boolean killActivity) {
        changeActivity(destination, killActivity, null, 0);
    }

    public void changeActivity(Class<?> destination, Bundle extra) {
        changeActivity(destination, false, extra, 0);
    }

    public void changeActivity(Class<?> destination, boolean killActivity, Bundle extra, int flags) {
        Intent intent = new Intent(context, destination);
        if (extra != null) {
            intent.putExtras(extra);
        }
        if (flags != 0) {
            intent.setFlags(flags);
        }
        startActivityForResult(intent, TRANSITION_REQUEST_CODE);
        if (killActivity) {
            finish();
        }
        overridePendingTransition(TRANSITION_IN_IN, TRANSITION_IN_OUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TRANSITION_REQUEST_CODE) {
            overridePendingTransition(TRANSITION_OUT_IN, TRANSITION_OUT_OUT);
        }
    }

    public int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public DisplayMetrics getDensityScreen(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public int getDPISize(int value, float scale) {
        return (int) (value * scale + 0.5f);
    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void showAlertDialog(String title, String content) {
        showAlertDialog(title, content, "OK");
    }

    public void showAlertDialog(String title, String content, String btn) {
        if (!isFinishing()) {
            AlertDialog dialogs = new AlertDialog();
            dialogs.setTitleandContent(title, content, btn);
            //dialogs.show(this.getSupportFragmentManager(), null);

            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(dialogs, null);
            ft.commitAllowingStateLoss();
        }
    }

    public void showAlertDialog(String title, String content, boolean capitalize) {
        if (!isFinishing()) {
            AlertDialog dialogs = new AlertDialog();
            dialogs.setTitleandContent(title, content, "OK");
            dialogs.setCapitalize(capitalize);
            // dialogs.show(this.getSupportFragmentManager(), null);

            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(dialogs, null);
            ft.commitAllowingStateLoss();
        }
    }

    public void showAlertDialog(String title, String content, String btn, AlertDialog.AlertDialogListener listener) {
        if (!isFinishing()) {
            AlertDialog dialogs = new AlertDialog();
            dialogs.setTitleandContent(title, content, btn);
            dialogs.setListener(listener);
            dialogs.setCapitalize(false);
            //dialogs.show(this.getSupportFragmentManager(), null);

            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(dialogs, null);
            ft.commitAllowingStateLoss();
        }
    }

    public String checkNullString(String string) {
        return (string == null) ? "" : string;
    }

}
