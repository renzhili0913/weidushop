package com.example.administrator.myapplication13.adapter.imagesadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication13.R;
import com.luck.picture.lib.photoview.PhotoView;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    /**图片的数据源*/
    private List<String> imgList;

    public ViewPagerAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

   /**判断当前的View 和 我们想要的Object(值为View) 是否一样;返回 true/false*/
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    /**instantiateItem()：将当前view添加到ViewGroup中，并返回当前View*/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = getItemView(R.layout.view_pager_img);
        PhotoView imageView = (PhotoView) itemView.findViewById(R.id.img_iv);
        Glide.with(context).load(imgList.get(position)).into(imageView);
        container.addView(itemView);
        return itemView;
    }

    /**destroyItem()：删除当前的View;*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private View getItemView(int layoutId) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.view_pager_img, null, false);
        return itemView;
    }
}
