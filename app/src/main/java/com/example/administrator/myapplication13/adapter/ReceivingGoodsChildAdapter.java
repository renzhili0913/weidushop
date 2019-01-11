package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.OrderShopBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceivingGoodsChildAdapter extends RecyclerView.Adapter<ReceivingGoodsChildAdapter.ViewHolder> {

    private Context context;
    private List<OrderShopBean.OrderListBean.DetailListBean> list;

    public ReceivingGoodsChildAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<OrderShopBean.OrderListBean.DetailListBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fragment_receiving_goods_child_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String replace = list.get(i).getCommodityPic().split("\\,")[0].replace("https", "http");
        Uri uri = Uri.parse(replace);
        viewHolder.shopcarImage.setImageURI(uri);
        viewHolder.shopcarTitle.setText(list.get(i).getCommodityName());
        viewHolder.shopcarPrice.setText("¥"+list.get(i).getCommodityPrice());
        viewHolder.shopcarCustomViewNum.setText(list.get(i).getCommodityCount()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shopcar_image)
        SimpleDraweeView shopcarImage;
        @BindView(R.id.shopcar_title)
        TextView shopcarTitle;
        @BindView(R.id.shopcar_price)
        TextView shopcarPrice;
        @BindView(R.id.shopcar_custom_view_num)
        TextView shopcarCustomViewNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
