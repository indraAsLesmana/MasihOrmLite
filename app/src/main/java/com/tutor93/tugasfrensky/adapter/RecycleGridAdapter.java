package com.tutor93.tugasfrensky.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.activity.EmployeEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wim on 4/14/16.
 */
public class RecycleGridAdapter extends RecyclerView.Adapter<RecycleGridAdapter.SingleViewHolder> {

    private Context mContext;
    private List<EmployeEntity> obj;
    private OnGridItemSelectedListener onGridItemSelectedListener;

    public RecycleGridAdapter(OnGridItemSelectedListener onGridItemSelectedListener, Context mContext) {
        this.onGridItemSelectedListener = onGridItemSelectedListener;
        obj = new ArrayList<>();
        this.mContext = mContext;
    }

    private void add(EmployeEntity item) {
        obj.add(item);
        notifyItemInserted(obj.size() - 1);
    }

    public void addAll(List<EmployeEntity> singleList) {
        for (EmployeEntity single : singleList) {
            add(single);
        }
    }

    public void remove(EmployeEntity item) {
        int position = obj.indexOf(item);
        if (position > -1) {
            obj.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public EmployeEntity getItem(int position) {
        return obj.get(position);
    }

    @Override
    public SingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_single, parent, false);
        final SingleViewHolder singleViewHolder = new SingleViewHolder(view);

        singleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = singleViewHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (onGridItemSelectedListener != null) {
                        onGridItemSelectedListener.onGridItemClick(singleViewHolder.itemView, adapterPos);
                    }
                }
            }
        });

        return singleViewHolder;
    }

    @Override
    public void onBindViewHolder(SingleViewHolder holder, int position) {
        final EmployeEntity single = obj.get(position);

        if (null != holder.name && null != obj && single.getName().length() != 0) {
            holder.name.setText(single.getName());
            holder.job.setText(single.getJobs());
            holder.age.setText(Integer.toString(single.getAge()));
            holder.gender.setText(single.is_male() == false ? "Female" : "Male");
            holder.startBookmark.setVisibility(single.isBookmark() ? View.VISIBLE : View.GONE);

            Glide.with(mContext)
                    .load(single.getAvatar())
                    .into(holder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public class SingleViewHolder extends RecyclerView.ViewHolder {

        /*ImageView img;
        TextView title;*/

        public TextView name;
        public TextView job;
        public TextView age;
        public TextView gender;

        public Button btnBookmark;
        public Button btnEdit;
        public ImageView startBookmark;
        public ImageView profileImage;


        public SingleViewHolder(View itemView) {
            super(itemView);

            /*img = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);*/

            name = (TextView) itemView.findViewById(R.id.tvname);
            job = (TextView) itemView.findViewById(R.id.tvjobs);
            age = (TextView) itemView.findViewById(R.id.tvage);
            gender = (TextView) itemView.findViewById(R.id.tvgender);

            btnBookmark = (Button) itemView.findViewById(R.id.btnBookmark);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);

            startBookmark = (ImageView) itemView.findViewById(R.id.startBookmarkIcon);
            profileImage = (ImageView) itemView.findViewById(R.id.imageProfile);
        }

    }

    public interface OnGridItemSelectedListener {
        void onGridItemClick(View v, int position);
    }

}

