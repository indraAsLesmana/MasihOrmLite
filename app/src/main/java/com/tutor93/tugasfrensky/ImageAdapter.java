package com.tutor93.tugasfrensky;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by indraaguslesmana on 9/22/16.
 */


public class ImageAdapter extends BaseAdapter {

    //coba dari database
    private EmployeeModel helper;
    private List<EmployeEntity> listEmployeeData;
    private Context mContext;
    private int row;


    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        /*this.helper = new EmployeeModel(mContext);
        listEmployeeData = helper.getAllEmployee();
        return listEmployeeData.size();
        */
        return mThumbIds.length;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;*/

        View view = convertView;
        ImageAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ImageAdapter.ViewHolder) view.getTag();
        }

        final EmployeEntity obj = listEmployeeData.get(position);
        holder.name = (TextView) view.findViewById(R.id.tvname);
        holder.job = (TextView) view.findViewById(R.id.tvjobs);
        holder.age = (TextView) view.findViewById(R.id.tvage);
        holder.gender = (TextView) view.findViewById(R.id.tvgender);

        holder.btnBookmark = (Button) view.findViewById(R.id.btnBookmark);
        holder.btnEdit = (Button) view.findViewById(R.id.btnEdit);

        holder.startBookmark = (ImageView) view.findViewById(R.id.startBookmarkIcon);
        holder.profileImage = (ImageView) view.findViewById(R.id.imageProfile);


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(mContext, Add_employee.class);
                myIntent.putExtra("id", obj.getId());
                mContext.startActivity(myIntent);
            }
        });

        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj.setBookmark(!obj.bookmark);
                helper.updateOrder(obj);
                notifyDataSetChanged();
            }
        });

        if (null != holder.name && null != obj && obj.getName().length() != 0) {
            holder.name.setText(obj.getName());
            holder.job.setText(obj.getJobs());
            holder.age.setText(Integer.toString(obj.getAge()));
            holder.gender.setText(obj.is_male() == false ? "Female" : "Male");
            holder.startBookmark.setVisibility(obj.isBookmark() ? View.VISIBLE : View.GONE);

            Bitmap bitmap = new ImageSaver(mContext).
                    setFileName(obj.getName() + ".png").
                    setDirectoryName("images").
                    load();

            if (bitmap != null){
                holder.profileImage.setImageBitmap(bitmap);
            }

//            load image coba pake glid masih error " isn't color or bitmap path"
            /*if(obj.getAvatar()!= null ){
                Glide.with(mContext).load(new File(obj.getAvatar()))
                        .placeholder(R.id.imageProfile)
                        .into(holder.profileImage);
                Log.d("nullte", " tidak null");
            }else if (obj.getAvatar() == null){
                Log.d("nullte", " nilainya null");
            }*/

        }

        return view;

    }


    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };


    public static class ViewHolder {
        public TextView name;
        public TextView job;
        public TextView age;
        public TextView gender;

        public Button btnBookmark;
        public Button btnEdit;
        public ImageView startBookmark;
        public ImageView profileImage;
    }


}
