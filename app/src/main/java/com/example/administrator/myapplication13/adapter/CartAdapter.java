package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.CartBean;
import com.example.administrator.myapplication13.view.CustomViewNum;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<CartBean.ResultBean> list;

    public CartAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<CartBean.ResultBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fragment_cart_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Uri uri = Uri.parse(list.get(i).getPic());
        viewHolder.shopcarImage.setImageURI(uri);
        viewHolder.shopcarTitle.setText(list.get(i).getCommodityName());
        viewHolder.shopcarPrice.setText("¥"+list.get(i).getPrice());
        viewHolder.shopcarRadio.setChecked(list.get(i).isCheck());
        viewHolder.shopcarRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(i).setCheck(isChecked);
                if (callBackListener!=null){
                    callBackListener.callBack(list);
                }
            }
        });
        //给自定义加加减减传值
        viewHolder.shopcarCustomViewNum.setData(this,list,i);
        viewHolder.shopcarCustomViewNum.setOnCallBack(new CustomViewNum.CallBackListener() {
            @Override
            public void callback() {
                if (callBackListener!=null){
                 callBackListener.callBack(list);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_car_del)
        Button shopCarDel;
        @BindView(R.id.shopcar_radio)
        CheckBox shopcarRadio;
        @BindView(R.id.shopcar_image)
        SimpleDraweeView shopcarImage;
        @BindView(R.id.shopcar_title)
        TextView shopcarTitle;
        @BindView(R.id.shopcar_price)
        TextView shopcarPrice;
        @BindView(R.id.shopcar_custom_view_num)
        CustomViewNum shopcarCustomViewNum;
        @BindView(R.id.shopcar_relative)
        RelativeLayout shopcarRelative;
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
        void callBack(List<CartBean.ResultBean> list);
    }
}
