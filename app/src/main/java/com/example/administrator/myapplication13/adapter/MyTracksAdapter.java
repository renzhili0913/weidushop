package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.TracksBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTracksAdapter extends RecyclerView.Adapter<MyTracksAdapter.ViewHolder> {

    private Context context;
    private List<TracksBean.ResultBean> list;

    public MyTracksAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<TracksBean.ResultBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void addList(List<TracksBean.ResultBean> data) {
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_activity_tracks_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.image);
        viewHolder.commodityName.setText(list.get(i).getCommodityName());
        viewHolder.price.setText("¥"+list.get(i).getPrice());
        viewHolder.browseNum.setText("已浏览"+list.get(i).getBrowseNum()+"次");
        //转换为时间格式并赋值
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(list.get(i).getBrowseTime()));
        viewHolder.browseTime.setText(date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.commodityName)
        TextView commodityName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.browseNum)
        TextView browseNum;
        @BindView(R.id.browseTime)
        TextView browseTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
