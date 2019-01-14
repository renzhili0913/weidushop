package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.OrderShopBean;
import com.example.zhouwei.library.CustomPopWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<OrderShopBean.OrderListBean> list;

    public EvaluateAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fragment_evaluate_param_view, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.orderid.setText(list.get(i).getOrderId());
        //转换为时间格式并赋值
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date(list.get(i).getOrderTime()));
        myViewHolder.orderTime.setText(date);
        //创建套嵌的适配器展示商品
        getItewView(myViewHolder, i);
    }

    public void deleteOrder(int i) {
        list.remove(i);
        notifyDataSetChanged();
    }

    private void getItewView(MyViewHolder myViewHolder, int i) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myViewHolder.childRecyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        EvaluateChildAdapter evaluateChildAdapter = new EvaluateChildAdapter(context);
        myViewHolder.childRecyclerview.setAdapter(evaluateChildAdapter);
        evaluateChildAdapter.setList(list.get(i).getDetailList());
        evaluateChildAdapter.setListener(new EvaluateChildAdapter.CallBackListener() {
            @Override
            public void callBack(OrderShopBean.OrderListBean.DetailListBean detailListBean) {
                if (click!=null){
                    click.onClick(list.get(i).getOrderId(),detailListBean);
                }
            }
        });

        //点击弹出删除按钮
        myViewHolder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popView=LayoutInflater.from(context).inflate(R.layout.list_oreder_pop_delete,null,false);
                final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(context)
                        .setView(popView)
                        //显示的布局，还可以通过设置一个View
                        .setFocusable(true)
                        //是否获取焦点，默认为ture
                        .setOutsideTouchable(true)
                        //是否PopupWindow 以外触摸dissmiss
                        .create()//创建PopupWindow
                        .showAsDropDown(v,-50,10);
                //显示PopupWindow
                TextView textView=popView.findViewById(R.id.text_delete);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deleteClick!=null){
                            deleteClick.onClick(list.get(i).getOrderId(),i);
                            popWindow.dissmiss();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderid)
        TextView orderid;
        @BindView(R.id.image_delete)
        ImageView imageDelete;
        @BindView(R.id.child_recyclerview)
        RecyclerView childRecyclerview;
        @BindView(R.id.orderTime)
        TextView orderTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    //去评价
    Click click;
    public void setOnClickListener(Click click) {
        this.click = click;
    }
    public interface Click {
        void onClick(String orderId, OrderShopBean.OrderListBean.DetailListBean detailListBean);
    }

    //取消订单
   DeleteClick deleteClick;
    public void setOnClickListener(DeleteClick deleteClick){
        this.deleteClick=deleteClick;
    }
    public interface DeleteClick{
        void onClick(String id, int i);
    }
}
