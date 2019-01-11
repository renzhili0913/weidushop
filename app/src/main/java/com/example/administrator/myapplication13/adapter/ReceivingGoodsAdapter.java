package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.OrderShopBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceivingGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<OrderShopBean.OrderListBean> list;

    public ReceivingGoodsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<OrderShopBean.OrderListBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addList(List<OrderShopBean.OrderListBean> data) {
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fragment_receiving_goods_param_view, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.orderId.setText(list.get(i).getOrderId());
        //转换为时间格式并赋值
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date(list.get(i).getOrderTime()));
        myViewHolder.orderTime.setText(date);
        myViewHolder.expressCompName.setText(list.get(i).getExpressCompName());
        myViewHolder.expressSn.setText(list.get(i).getExpressSn());
        //创建套嵌的适配器展示商品
        getItewView(myViewHolder, i);
        myViewHolder.paymentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(list.get(i).getOrderId());
                }
            }
        });

    }

    private void getItewView(MyViewHolder myViewHolder, int i) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myViewHolder.childRecyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        ReceivingGoodsChildAdapter receivingGoodsChildAdapter = new ReceivingGoodsChildAdapter(context);
        myViewHolder.childRecyclerview.setAdapter(receivingGoodsChildAdapter);
        receivingGoodsChildAdapter.setList(list.get(i).getDetailList());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderId)
        TextView orderId;
        @BindView(R.id.orderTime)
        TextView orderTime;
        @BindView(R.id.child_recyclerview)
        RecyclerView childRecyclerview;
        @BindView(R.id.expressCompName)
        TextView expressCompName;
        @BindView(R.id.expressSn)
        TextView expressSn;
        @BindView(R.id.payment_order)
        TextView paymentOrder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    Click click;

    public void setOnClickListener(Click click) {
        this.click = click;
    }

    public interface Click {
        void onClick(String id);
    }
}
