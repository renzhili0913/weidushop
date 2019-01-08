package com.example.administrator.myapplication13.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.adapter.CartAdapter;
import com.example.administrator.myapplication13.bean.CartBean;

import java.util.List;


public class CustomViewNum extends LinearLayout {

    private TextView add;
    private TextView cut;
    private TextView num;
    private int nums;
    private CartAdapter cartAdapter;
    private List<CartBean.ResultBean> list;
    private int position;

    public CustomViewNum(Context context) {
        super(context);
        initView(context);
    }

    public CustomViewNum(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view=View.inflate(context,R.layout.custom_view_num,null);
        add = view.findViewById(R.id.shopcar_add);
        cut = view.findViewById(R.id.shopcar_cut);
        num = view.findViewById(R.id.shopcar_num);
        addView(view);
        //点击加号
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nums++;
                if(nums>1){
                    num.setTextColor(Color.parseColor("#666666"));
                }
                list.get(position).setCount(nums);
                num.setText(nums+"");
                if (callBackListener!=null) {
                    callBackListener.callback();
                }
                cartAdapter.notifyItemChanged(position);
            }
        });
        //点击减号
        cut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nums>1){
                    nums--;
                }
                list.get(position).setCount(nums);
                num.setText(nums+"");
                if (callBackListener!=null) {
                    callBackListener.callback();
                }
                if(nums==1){
                    cut.setTextColor(Color.parseColor("#999999"));
                }
                cartAdapter.notifyItemChanged(position);
            }
        });
    }
    public void setData(CartAdapter cartAdapter, List<CartBean.ResultBean> list, int i){
        this.list=list;
        this.cartAdapter=cartAdapter;
        position=i;
        nums=list.get(position).getCount();
        num.setText(nums+"");
        if(nums==1){
            cut.setTextColor(Color.parseColor("#999999"));
        }
    }

    private CallBackListener callBackListener;

    public void setOnCallBack(CallBackListener listener){
        callBackListener=listener;
    }
    public interface CallBackListener{
        void callback();
    }
}
