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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<OrderShopBean.OrderListBean> list;
    public AllOrderAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fragment_allorder_param_view, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
       MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
      myViewHolder.orderid.setText(list.get(i).getOrderId());
        //转换为时间格式并赋值
       String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(list.get(i).getOrderTime()));
        myViewHolder.orderTime.setText(date);
        int num=0;
        List<OrderShopBean.OrderListBean.DetailListBean> detailList = list.get(i).getDetailList();
        for (OrderShopBean.OrderListBean.DetailListBean bean:detailList
             ) {
            num+=bean.getCommodityCount();
        }
        myViewHolder.tatalPrice.setText("共"+num+"件商品,需付款"+list.get(i).getPayAmount()+"元");
       //创建套嵌的适配器展示商品
        getItewView(myViewHolder,i);
        //取消订单
        myViewHolder.cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(list.get(i).getOrderId(),i);
                }
            }
        });
        //支付
        myViewHolder.paymentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentClick!=null){
                    paymentClick.onClick(list.get(i).getOrderId(),list.get(i).getPayMethod(),list.get(i).getPayAmount());
                }
            }
        });
    }
    public void deleteOrder(int i){
        list.remove(i);
        notifyDataSetChanged();
    }
    private void getItewView(MyViewHolder myViewHolder,int i) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myViewHolder.childRecyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        AllOrderChildAdapter allOrderChildAdapter = new AllOrderChildAdapter(context);
        myViewHolder.childRecyclerview.setAdapter(allOrderChildAdapter);
        allOrderChildAdapter.setList(list.get(i).getDetailList());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderid)
        TextView orderid;
        @BindView(R.id.orderTime)
        TextView orderTime;
        @BindView(R.id.child_recyclerview)
        RecyclerView childRecyclerview;
        @BindView(R.id.tatal_price)
        TextView tatalPrice;
        @BindView(R.id.cancel_order)
        TextView cancelOrder;
        @BindView(R.id.payment_order)
        TextView paymentOrder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //去支付的接口
    PaymentClick paymentClick;
    public void setPaymentClickListener(PaymentClick paymentClick){
        this.paymentClick=paymentClick;
    }
    public interface PaymentClick{
        void onClick(String orderId,int payType,double price);
    }
    //取消订单
    Click click;
    public void setOnClickListener(Click click){
        this.click=click;
    }
    public interface Click{
        void onClick(String id,int i);
    }
}
