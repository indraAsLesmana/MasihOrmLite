package com.tutor93.tugasfrensky.latihanapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tutor93.tugasfrensky.R;

import java.util.List;

/**
 * Created by indraaguslesmana on 9/28/16.
 */

public class ApiPictAdapter extends ArrayAdapter<PictureAPIResponse> implements View.OnClickListener {

    private Context mContext;
    private int row;
    private List<PictureAPIResponse> pictureAPIResponses;
    PictureAPIResponse helper;

    public ApiPictAdapter(Context context, int textViewResourceId, List<PictureAPIResponse> pictApiRes) {
        super(context, textViewResourceId, pictApiRes);

        this.mContext = context;
        this.row = textViewResourceId;
        this.pictureAPIResponses = pictApiRes;

        /*initial data*/
        this.helper = new PictureAPIResponse();

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final PictureAPIResponse  obj = new PictureAPIResponse();

        holder.employePict = (ImageView) view.findViewById(R.id.employerpict_api);
        holder.btn_next = (ImageView) view.findViewById(R.id.btn_nextemployee);
        holder.btn_next = (ImageView) view.findViewById(R.id.btn_previusemployee);

        //test
        Glide.with(mContext)
                .load(obj.getPicture())
                .into(holder.employePict);

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public static class ViewHolder {
        public ImageView employePict;
        public ImageView btn_next;
        public ImageView btn_previos;
    }
}
