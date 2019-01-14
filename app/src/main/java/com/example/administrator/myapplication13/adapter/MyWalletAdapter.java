package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.WalletBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWalletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<WalletBean.ResultBean.DetailListBean> list;

    public MyWalletAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<WalletBean.ResultBean.DetailListBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addList(List<WalletBean.ResultBean.DetailListBean> data) {
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_activity_wallet_price_item, viewGroup, false);
        return new MyPriceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyPriceViewHolder myPriceViewHolder= (MyPriceViewHolder) viewHolder;
        myPriceViewHolder.consumptionAmount.setText("¥"+list.get(i).getAmount());
        //转换为时间格式并赋值
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(list.get(i).getCreateTime()));
        myPriceViewHolder.consumptionData.setText(date);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyPriceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.consumption_amount)
        TextView consumptionAmount;
        @BindView(R.id.consumption_data)
        TextView consumptionData;
        public MyPriceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
