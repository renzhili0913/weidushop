package com.example.administrator.myapplication13.adapter.imagesadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.iamgesview.MainConstant;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;

    public GridViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > MainConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.grid_item, parent,false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.pic_iv);
        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            //图片路径
            String picUrl = mList.get(position);
            Glide.with(mContext).load(picUrl).into(iv);
        } else {
            //最后一个显示加号图片
            iv.setImageResource(R.mipmap.common_btn_camera_blue_n);
        }
        return convertView;
    }

}
