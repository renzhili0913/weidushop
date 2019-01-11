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

public class EvaluateChildAdapter extends RecyclerView.Adapter<EvaluateChildAdapter.ViewHolder> {

    private Context context;
    private List<OrderShopBean.OrderListBean.DetailListBean> list;

    public EvaluateChildAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fragment_evaluate_child_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String replace = list.get(i).getCommodityPic().split("\\,")[0].replace("https", "http");
        Uri uri = Uri.parse(replace);
        viewHolder.shopcarImage.setImageURI(uri);
        viewHolder.shopcarTitle.setText(list.get(i).getCommodityName());
        viewHolder.shopcarPrice.setText("¥"+list.get(i).getCommodityPrice());
        viewHolder.evaluate_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackListener!=null){
                    callBackListener.callBack(list.get(i));
                }
            }
        });
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
        @BindView(R.id.evaluate_order)
        TextView evaluate_order;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //声明接口
    CallBackListener callBackListener;
    public void setListener(CallBackListener callBackListener){
        this.callBackListener=callBackListener;
    }
    public interface CallBackListener{
        void callBack(OrderShopBean.OrderListBean.DetailListBean detailListBean);
    }
}
