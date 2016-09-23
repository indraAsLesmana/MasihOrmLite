package com.tutor93.tugasfrensky;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by indra on 11/09/2016.
 */
public class EmpAdapter extends ArrayAdapter<EmployeEntity> implements View.OnClickListener {

    private Context mContext;
    private int row;
    private List<EmployeEntity> listEmployeeData;
    EmployeeModel helper;
   /* ViewHolder holder;*/

    public EmpAdapter(Context context, int textViewResourceId, List<EmployeEntity> listEmployeeData) {
        super(context, textViewResourceId, listEmployeeData);
        this.mContext = context;
        this.row = textViewResourceId;
        this.listEmployeeData = listEmployeeData;

        /*initial data*/
        this.helper = new EmployeeModel(mContext);
    }


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

        final EmployeEntity obj = listEmployeeData.get(position);
        holder.name = (TextView) view.findViewById(R.id.tvname);
        holder.job = (TextView) view.findViewById(R.id.tvjobs);
        holder.age = (TextView) view.findViewById(R.id.tvage);
        holder.gender = (TextView) view.findViewById(R.id.tvgender);

        holder.btnBookmark = (Button) view.findViewById(R.id.btnBookmark);
        holder.btnEdit = (Button) view.findViewById(R.id.btnEdit);

        holder.startBookmark = (ImageView) view.findViewById(R.id.startBookmarkIcon);
        holder.profileImage = (ImageView) view.findViewById(R.id.imageProfile);

        /*button edit*/
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
           /* if(obj.getAvatar()!= null ){
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

    @Override
    public void onClick(View view) {

    }

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
