package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.ShopBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFashionAdapter extends RecyclerView.Adapter<HomeFashionAdapter.ViewHolder> {

    private Context context;
    private List<ShopBean.ResultBean.MlssBean.CommodityListBeanXX> list;

    public HomeFashionAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<ShopBean.ResultBean.MlssBean.CommodityListBeanXX> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fragment_home_fashion_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Uri uri = Uri.parse(list.get(i).getMasterPic());
        viewHolder.masterPic.setImageURI(uri);
        //Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.masterPic);
        viewHolder.commodityName.setText(list.get(i).getCommodityName());
        viewHolder.price.setText("¥"+list.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.masterPic)
        SimpleDraweeView masterPic;
        @BindView(R.id.commodityName)
        TextView commodityName;
        @BindView(R.id.price)
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
