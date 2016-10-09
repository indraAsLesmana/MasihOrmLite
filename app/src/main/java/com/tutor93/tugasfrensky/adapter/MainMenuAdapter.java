package com.tutor93.tugasfrensky.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tutor93.tugasfrensky.R;
/*import com.medictrust.application.APP;*/
/*import com.medictrust.application.Preference;*/
import com.tutor93.tugasfrensky.model.MainMenuModel;
import com.tutor93.tugasfrensky.util.StringUtil;
/*import com.medictrust.view.BadgeDrawable;*/

import java.util.List;

/**
 * Created by frensky on 25/01/2016.
 */
public class MainMenuAdapter extends BaseAdapter {
    private List<MainMenuModel> menuData;
    private Context context;
    private LayoutInflater inflater;
    private int selectedMenu;

    public MainMenuAdapter(Context context, List<MainMenuModel> datas) {
        this.context = context;
        this.menuData = datas;
        this.inflater = LayoutInflater.from(context);
        selectedMenu = 0;
    }

    private static class ViewHolder {
        View v;
        RelativeLayout backgrounds;
        ImageView menu_picture;
        TextView menu_name;
        TextView notifLayout;
    }

    @Override
    public int getCount() {
        return menuData.size();
    }

    @Override
    public Object getItem(int position) {
        return menuData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        ViewHolder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.menu_row_element, viewGroup, false);
            holder = new ViewHolder();
            holder.backgrounds = (RelativeLayout) v.findViewById(R.id.menu_row_view);
            holder.menu_name = (TextView) v.findViewById(R.id.menu_row_names);
            holder.menu_picture = (ImageView) v.findViewById(R.id.menu_row_icons);
            holder.notifLayout = (TextView)v.findViewById(R.id.menu_notif_layout);
            v.setTag(holder);
        }  else {
            holder = (ViewHolder) v.getTag();
        }

        MainMenuModel selectedModel = menuData.get(position);

        holder.menu_name.setText(StringUtil.capitalizeString(selectedModel.getName()));
        holder.menu_picture.setImageResource(selectedModel.getResourceDrawableSelected());

       /* if(position == selectedMenu){
            holder.backgrounds.setBackgroundColor(Color.parseColor("#22ffffff"));
        }
        else{
            holder.backgrounds.setBackgroundColor(context.getResources().getColor(R.color.dark_purple));
        }*/

        /*if(selectedModel.getName().equalsIgnoreCase(context.getResources().getString(R.string.main_menu_notifications))){
            int counter_notif = APP.getIntPref(context, Preference.NOTIF_COUNTER);
            if(counter_notif >0) {
                    float textSize = 12 * Resources.getSystem().getDisplayMetrics().scaledDensity;
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

                    holder.notifLayout.setText(spannableString);
            }
            else{
                holder.notifLayout.setText("");
            }
        }
        else{
            holder.notifLayout.setText("");
        }*/

        return v;
    }

    public void notifyDataSetChanged(int currentPosition){
        this.selectedMenu = currentPosition;
        notifyDataSetChanged();
    }
}

