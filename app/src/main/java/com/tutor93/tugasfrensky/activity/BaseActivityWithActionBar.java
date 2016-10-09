package com.tutor93.tugasfrensky.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.tutor93.tugasfrensky.R;
/*import com.tutor93.tugasfrensky.application.APP;
import com.tutor93.tugasfrensky.application.Preference;*/
import com.tutor93.tugasfrensky.base.ActivityInterface;
import com.tutor93.tugasfrensky.callbacks.OnActionBarSelectListener;
import com.tutor93.tugasfrensky.callbacks.OnActionbarListener;
import com.tutor93.tugasfrensky.fragment.dialog.AlertDialog;
import com.tutor93.tugasfrensky.fragment.BaseFragmentWithActionBar;
import com.tutor93.tugasfrensky.util.FragmentHelper;
import com.tutor93.tugasfrensky.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frensky on 25/01/2016.
 */
public abstract class BaseActivityWithActionBar extends ActionBarActivity implements ActivityInterface {

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
    public DrawerLayout menu;
    private OnActionbarListener actionbarListener;
    private OnActionBarSelectListener selectionListener;
    public LinearLayout menuLeftBtn;
    public TextView tvActionBarTitle;
    public TextView tvSelectBtn;
    public TextView tvCancelBtn;
    public TextView tvTitle2Btn;
    public ImageView leftIcon, rightIcon, rightIcon2, selectShareBtn, selectDeleteBtn;
    //private BadgeView notifBadge;
    public ViewAnimator menuAnimator;
    private boolean canBadgeShown;
    private TextView notifBadgeLayout;
    protected boolean isRunning;

    protected RelativeLayout toolbarBackground;

    protected List<AsyncTask> threadList;

    public void setShowSlidingMenu(boolean showSlidingMenu) {
        this.showSlidingMenu = showSlidingMenu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentHelper = FragmentHelper.getInstance(getSupportFragmentManager());
        context = this;
        canBadgeShown = false;

        isRunning = true;

        initSharedPreference();
        setContentView(getLayout());
        initView();
        setUICallbacks();
        showCustomActionBar();
        setFont();
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

        if (threadList != null) {
            for (AsyncTask task : threadList) {
                if (task != null) {
                    try {
                        if (task.getStatus() == AsyncTask.Status.RUNNING) {
                            task.cancel(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            threadList.clear();
        }

        isRunning = false;
    }

    public boolean isActivityRunning() {
        return isRunning;
    }

    protected void showCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actionBarView = inflater.inflate(R.layout.view_custom_actionbar, null, false);
            actionbarClickListener();

            menuAnimator = (ViewAnimator) actionBarView.findViewById(R.id.menu_animator);

            tvActionBarTitle = (TextView) actionBarView.findViewById(R.id.menu_title);
            tvSelectBtn = (TextView) actionBarView.findViewById(R.id.menu_select);
            leftIcon = (ImageView) actionBarView.findViewById(R.id.menu_icons);
            rightIcon = (ImageView) actionBarView.findViewById(R.id.menu_right_btn);
            rightIcon2 = (ImageView) actionBarView.findViewById(R.id.menu_right_btn2);
            menuLeftBtn = (LinearLayout) actionBarView.findViewById(R.id.menu_left_btn);
            toolbarBackground = (RelativeLayout) actionBarView.findViewById(R.id.toolbar_background);
            notifBadgeLayout = (TextView) actionBarView.findViewById(R.id.menu_badge_layout);

            tvCancelBtn = (TextView) actionBarView.findViewById(R.id.menu_cancel);
            selectDeleteBtn = (ImageView) actionBarView.findViewById(R.id.menu_select_delete);
            selectShareBtn = (ImageView) actionBarView.findViewById(R.id.menu_select_share);

            tvTitle2Btn = (TextView) actionBarView.findViewById(R.id.menu_selected_title);

            /*
            notifBadge = new BadgeView(this, notifBadgeLayout);
            notifBadge.setText("");
            notifBadge.setTextSize(TypedValue.COMPLEX_UNIT_SP,12.f);
            notifBadge.hide();*/

            final Animation inAnim = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            final Animation outAnim = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

            menuAnimator.setInAnimation(inAnim);
            menuAnimator.setOutAnimation(outAnim);

            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(actionBarView);

            Toolbar toolbar = (Toolbar) actionBarView.getParent();
            toolbar.setContentInsetsAbsolute(0, 0);

            actionBar.show();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.dark_purple));
            }

        }
    }


    public View getActionBarRightView() {
        return actionBarView.findViewById(R.id.menu_right_btn);
    }

    public View getActionBarRight2View() {
        return actionBarView.findViewById(R.id.menu_right_btn2);
    }

    private void actionbarClickListener() {
        View actionLeft = actionBarView.findViewById(R.id.menu_left_btn);
        View actionRight = actionBarView.findViewById(R.id.menu_right_btn);
        View actionRight2 = actionBarView.findViewById(R.id.menu_right_btn2);
        TextView select = (TextView) actionBarView.findViewById(R.id.menu_select);
        actionLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (actionbarListener != null) {
                    actionbarListener.onLeftIconClick();

                }
            }
        });

        actionRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (actionbarListener != null) {
                    actionbarListener.onRightIconClick();

                }
            }
        });

        actionRight2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (actionbarListener != null) {
                    actionbarListener.onRight2IconClick();

                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (actionbarListener != null) {
                    actionbarListener.onSelectIconClick();

                }
            }
        });


        View selectDeleteBtn = actionBarView.findViewById(R.id.menu_select_delete);
        View selectShareBtn = actionBarView.findViewById(R.id.menu_select_share);
        TextView cancelText = (TextView) actionBarView.findViewById(R.id.menu_cancel);

        selectDeleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectionListener != null) {
                    selectionListener.onDeletionClick();
                }
            }
        });

        selectShareBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectionListener != null) {
                    selectionListener.onSharingClick();
                }
            }
        });

        cancelText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectionListener != null) {
                    selectionListener.onCancelClick();
                }
            }
        });
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
        int stackCount = getFragmentManager().getBackStackEntryCount();
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

    public void replaceFragment(int container, BaseFragmentWithActionBar fragment, boolean addBackToStack) {

        if (!isFinishing()) {
            try {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (addBackToStack) {
                    ft.addToBackStack("");
                    //ft.addToBackStack(fragment.getPageTitle());
                }
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                ft.replace(container, fragment);
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void replaceFragmentwithTag(int container, BaseFragmentWithActionBar fragment, boolean addBackToStack, String tag) {
        if (!isFinishing()) {
            try {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (addBackToStack) {
                    ft.addToBackStack("");
                    //ft.addToBackStack(fragment.getPageTitle());
                }
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                ft.replace(container, fragment, tag);
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void replaceFragment(int container, String tagFragment, BaseFragmentWithActionBar fragment, boolean addBackToStack) {
        if (!isFinishing()) {
            try {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (addBackToStack) {
                    //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right);
                    ft.addToBackStack(tagFragment);
                }
                ft.replace(container, fragment);
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void replaceFragment(int container, android.support.v4.app.Fragment fragment, boolean addBackToStack) {
        if (!isFinishing()) {
            try {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (addBackToStack) {
                    ft.addToBackStack(fragment.getTag());
                }
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.replace(container, fragment);
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addFragment(int container, BaseFragmentWithActionBar fragment, boolean addBackToStack) {
        if (!isFinishing()) {
            try {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (addBackToStack) {
                    ft.addToBackStack(fragment.getPageTitle());
                }
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.add(container, fragment);
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addFragment(int container, String tagFragment, BaseFragmentWithActionBar fragment, boolean addBackToStack) {
        if (!isFinishing()) {
            try {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (addBackToStack) {
                    //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right);
                    ft.addToBackStack(tagFragment);
                }
                ft.add(container, fragment);
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addFragment(int container, android.support.v4.app.Fragment fragment, boolean addBackToStack) {
        if (!isFinishing()) {
            try {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (addBackToStack) {
                    ft.addToBackStack(fragment.getTag());
                }
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.add(container, fragment);
                ft.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setActionBarTitle(String title) {
       /* APP.log("SET WITH : " + title);*/
        if (tvActionBarTitle != null) {
            tvActionBarTitle.setText(title);
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

    public void changeActivity(Intent intent) {
        startActivityForResult(intent, TRANSITION_REQUEST_CODE);
        overridePendingTransition(TRANSITION_IN_IN, TRANSITION_IN_OUT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TRANSITION_REQUEST_CODE) {
            overridePendingTransition(TRANSITION_OUT_IN, TRANSITION_OUT_OUT);
        }
    }

    public void setActionbarListener(OnActionbarListener actionbarListener) {
        this.actionbarListener = actionbarListener;
    }

    public void setActionbarSelectionListener(OnActionBarSelectListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public void setLeftIcon(int drawableRes) {
        if (leftIcon != null) {
            if (drawableRes == 0) {
                leftIcon.setVisibility(View.GONE);
            } else {
                leftIcon.setVisibility(View.VISIBLE);
                leftIcon.setImageResource(drawableRes);
            }

            if (drawableRes == R.drawable.more_icon) {
                canBadgeShown = true;
                showBadgeNotif(true);
            } else {
                canBadgeShown = false;
                showBadgeNotif(false);
            }

            if (menu != null) {
                menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        }
    }

    public void showBadgeNotif(boolean isShown) {

        /*if(APP.isAccessCode(this)){
            //notifBadge.hide();
            notifBadgeLayout.setText("");
        }
        else{
            if(isShown && canBadgeShown){
                int counter_notif = APP.getIntPref(this, Preference.NOTIF_COUNTER);
                if(counter_notif >0){
                    float textSize = 9 * Resources.getSystem().getDisplayMetrics().density;
                    BadgeDrawable drawable =
                                new BadgeDrawable.Builder()
                                        .type(BadgeDrawable.TYPE_NUMBER)
                                        .number(counter_notif)
                                        .textSize(textSize)
                                        .build();

                    SpannableString spannableString =
                            new SpannableString(TextUtils.concat(
                                    drawable.toSpannable()
                            ));

                    notifBadgeLayout.setText(spannableString);
                    refreshSideMenuBar();
                }
                else{
                    notifBadgeLayout.setText("");
                    refreshSideMenuBar();
                }
            }
            else{
                notifBadgeLayout.setText("");
                refreshSideMenuBar();
            }
        }*/

    }

    protected void refreshSideMenuBar() {

    }

    public void setSelectDeleteIcon(int drawableRes) {
        if (selectDeleteBtn != null) {
            if (drawableRes == 0) {
                selectDeleteBtn.setVisibility(View.GONE);
            } else {
                selectDeleteBtn.setVisibility(View.VISIBLE);
                selectDeleteBtn.setImageResource(drawableRes);
            }
        }
    }

    public void setSelectShareIcon(int drawableRes) {
        if (selectShareBtn != null) {
            if (drawableRes == 0) {
                selectShareBtn.setVisibility(View.GONE);
            } else {
                selectShareBtn.setVisibility(View.VISIBLE);
                selectShareBtn.setImageResource(drawableRes);
            }
        }
    }

    public void setCanceltext(String text) {
        if (tvCancelBtn != null) {
            tvCancelBtn.setText(text);
        }
    }

    public void setRightIcon(int drawableRes) {
        if (rightIcon != null) {
            if (drawableRes == 0)
                rightIcon.setVisibility(View.GONE);
            else {
                rightIcon.setVisibility(View.VISIBLE);
                rightIcon.setImageResource(drawableRes);
            }

        }
    }

    public void setRightIcon2(int drawableRes) {
        if (rightIcon2 != null) {
            if (drawableRes == 0) {
                rightIcon2.setVisibility(View.GONE);
            } else {
                rightIcon2.setVisibility(View.VISIBLE);
                rightIcon2.setImageResource(drawableRes);
            }
        }
    }

    public void showSelectBtn(boolean shows) {
        if (tvSelectBtn != null) {
            if (shows)
                tvSelectBtn.setVisibility(View.VISIBLE);
            else
                tvSelectBtn.setVisibility(View.GONE);
        }
    }

    public void setSelectBtnTitle(String title) {
        if (tvSelectBtn != null) {
            tvSelectBtn.setText(title);
        }
    }

    /*public void setActionBarTitle(String title) {
        APP.log("SET WITH : " + title);
        if (tvActionBarTitle != null) {
            tvActionBarTitle.setText(title);
        }
    }
*/
    public String getActionBarTitle() {
        return tvActionBarTitle.getText().toString();
    }


    /*public void setSelectionBarTitle(String title) {
        APP.log("SET WITH : " + title);
        if (tvTitle2Btn != null) {
            tvTitle2Btn.setText(title);
        }
    }*/

    public void setDefaultActionbarIcon() {
        menuAnimator.setDisplayedChild(0);
        setLeftIcon(R.drawable.more_icon);
        setRightIcon(0);
        setRightIcon2(0);
        showSelectBtn(false);
        setSelectBtnTitle(getResources().getString(R.string.select));
    }

    public void setMenuType(int type) {
        if (type == 1) {
            if (menuAnimator.getDisplayedChild() == 0) {
                menuAnimator.showNext();
            }
        } else {
            if (menuAnimator.getDisplayedChild() == 1) {
                menuAnimator.showPrevious();
            } else {
                menuAnimator.setDisplayedChild(0);
            }
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
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void showAlertDialog(String title, String content) {
        showAlertDialog(title, content, getResources().getString(R.string.ok), false);
    }

    public void showAlertDialog(String title, String content, boolean capitalize) {
        showAlertDialog(title, content, "OK", capitalize);
    }

    public void showAlertDialog(String title, String content, String btn) {
        if (!isFinishing()) {
            AlertDialog dialogs = new AlertDialog();
            dialogs.setTitleandContent(title, content, btn);
            // dialogs.show(this.getSupportFragmentManager(), null);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(dialogs, null);
            ft.commitAllowingStateLoss();
        }
    }

    public void showAlertDialog(String title, String content, String btn, boolean capitalize) {
        if (!isFinishing()) {
            AlertDialog dialogs = new AlertDialog();
            dialogs.setTitleandContent(title, content, btn);
            dialogs.setCapitalize(capitalize);
            //dialogs.show(this.getSupportFragmentManager(), null);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(dialogs, null);
            ft.commitAllowingStateLoss();
        }
    }

    public void showAlertDialogwithListener(String title, String content, String btn, AlertDialog.AlertDialogListener listener) {
        if (!isFinishing()) {
            AlertDialog dialogs = new AlertDialog();
            dialogs.setTitleandContent(title, content, btn);
            dialogs.setListener(listener);
            //dialogs.show(this.getSupportFragmentManager(), null);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(dialogs, null);
            ft.commitAllowingStateLoss();
        }
    }


    public void showAlertDialogwithListenerNoCapital(String title, String content, String btn, AlertDialog.AlertDialogListener listener) {
        if (!isFinishing()) {
            AlertDialog dialogs = new AlertDialog();
            dialogs.setTitleandContent(title, content, btn);
            dialogs.setCapitalize(false);
            dialogs.setListener(listener);
            //dialogs.show(this.getSupportFragmentManager(), null);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(dialogs, null);
            ft.commitAllowingStateLoss();
        }
    }

    public String checkNullString(String string) {
        return (string == null) ? "" : string;
    }

    public void registerTask(AsyncTask task) {
        if (threadList == null) {
            threadList = new ArrayList<>();
        }
        threadList.add(task);
    }

    public void removeTask(AsyncTask task) {
        if (threadList != null) {
            threadList.remove(task);
        }
    }


}
