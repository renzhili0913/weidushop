package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author YU
 * @date 2019.01.09
 * 订单页面的适配器
 */
public class ListAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_obligation=1;
    private final int TYPE_wait=2;
    private final int TYPE_remain=3;
    private final int TYPE_completed=9;
    private final int TYPE_none=-1;

    private Context context;
    private List<OrderShopBean.OrderListBean> list;
    public ListAdpater(Context context) {
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
    public void refresh(List<OrderShopBean.OrderListBean> all_list){
        list=all_list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        //获取订单状态  判断 属于哪个状态  然后确定加载哪个视图
        if(list.get(position).getOrderStatus()==TYPE_obligation){
            return TYPE_obligation;
        }else if(list.get(position).getOrderStatus()==TYPE_wait){
            return TYPE_wait;
        }else if(list.get(position).getOrderStatus()==TYPE_remain){
            return TYPE_remain;
        }else if(list.get(position).getOrderStatus()==TYPE_completed){
            return TYPE_completed;
        }else{
            return TYPE_none;
        }
    }

    public void deleteOrder(int i){
        list.remove(i);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_obligation:
                //待付款
                View obligation=LayoutInflater.from(context).inflate(R.layout.adapter_fragment_payment_param_view,viewGroup,false);
                return new ViewObligation(obligation);
            case TYPE_wait:
                //待收货
                View wait=LayoutInflater.from(context).inflate(R.layout.adapter_fragment_receiving_goods_param_view,viewGroup,false);
                return new ViewWait(wait);
            case TYPE_remain:
                //待评价
                View remain=LayoutInflater.from(context).inflate(R.layout.adapter_fragment_evaluate_param_view,viewGroup,false);
                return new ViewRemain(remain);
            case TYPE_completed:
                //已完成
                View completed=LayoutInflater.from(context).inflate(R.layout.adapter_fragment_completed_param_view,viewGroup,false);
                return new ViewCompleted(completed);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        //获取布局类型
        int itemViewType = getItemViewType(i);
       final OrderShopBean.OrderListBean result = list.get(i);
        switch (itemViewType){
            case TYPE_obligation:
                //待付款
                ViewObligation viewObligation= (ViewObligation) viewHolder;
                viewObligation.expressSn.setText(result.getOrderId());
                String date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                        new java.util.Date(result.getOrderTime()));
                viewObligation.orderTime.setText(date);
                //创建布局
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                viewObligation.childRecyclerview.setLayoutManager(linearLayoutManager);

                int num=0;
                List<OrderShopBean.OrderListBean.DetailListBean> detailList = list.get(i).getDetailList();
                for (OrderShopBean.OrderListBean.DetailListBean bean:detailList
                        ) {
                    num+=bean.getCommodityCount();
                }
                viewObligation.tatalPrice.setText("共"+num+"件商品,需付款"+list.get(i).getPayAmount()+"元");

                //创建适配器
                PaymentChildAdapter paymentChildAdapter = new PaymentChildAdapter(context);
                viewObligation.childRecyclerview.setAdapter(paymentChildAdapter);
                paymentChildAdapter.setList(list.get(i).getDetailList());

                //取消订单
                viewObligation.cancelOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (click!=null){
                            click.onClick(list.get(i).getOrderId(),i);
                        }
                    }
                });
                //支付
                viewObligation.paymentOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (paymentClick!=null){
                            paymentClick.onClick(list.get(i).getOrderId(),list.get(i).getPayMethod(),list.get(i).getPayAmount());
                        }
                    }
                });
                break;
            case TYPE_wait:
                //待收货
                ViewWait viewWait= (ViewWait) viewHolder;
                viewWait.orderId.setText(result.getOrderId());
                viewWait.expressCompName.setText(result.getExpressCompName());
                viewWait.expressSn.setText(result.getExpressSn());
                String wait_date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                        new java.util.Date(result.getOrderTime()));
                viewWait.orderTime.setText(wait_date);
                LinearLayoutManager linearLayoutManager_w=new LinearLayoutManager(context);
                linearLayoutManager_w.setOrientation(OrientationHelper.VERTICAL);
                viewWait.childRecyclerview.setLayoutManager(linearLayoutManager_w);
                //创建适配器
                ReceivingGoodsChildAdapter receivingGoodsChildAdapter = new ReceivingGoodsChildAdapter(context);
                viewWait.childRecyclerview.setAdapter(receivingGoodsChildAdapter);
                receivingGoodsChildAdapter.setList(list.get(i).getDetailList());
                //点击确认收货
                viewWait.paymentOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (receivingClick!=null){
                            receivingClick.onClick(list.get(i).getOrderId());
                        }
                    }
                });
                break;
            case TYPE_remain:
                //待评价
                ViewRemain viewRemain= (ViewRemain) viewHolder;
                viewRemain.orderid.setText(result.getOrderId());
                String remain_date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                        new java.util.Date(result.getOrderTime()));
                viewRemain.orderTime.setText(remain_date);
                LinearLayoutManager linearLayoutManager_R=new LinearLayoutManager(context);
                linearLayoutManager_R.setOrientation(OrientationHelper.VERTICAL);
                viewRemain.childRecyclerview.setLayoutManager(linearLayoutManager_R);
                //创建适配器
                EvaluateChildAdapter evaluateChildAdapter = new EvaluateChildAdapter(context);
                viewRemain.childRecyclerview.setAdapter(evaluateChildAdapter);
                evaluateChildAdapter.setList(list.get(i).getDetailList());
                //去评价
                evaluateChildAdapter.setListener(new EvaluateChildAdapter.CallBackListener() {
                    @Override
                    public void callBack(OrderShopBean.OrderListBean.DetailListBean detailListBean) {
                        if (evaluateClick!=null){
                            evaluateClick.onClick(list.get(i).getOrderId(),detailListBean);
                        }
                    }
                });
                //点击弹出删除按钮
                viewRemain.imageDelete.setOnClickListener(new View.OnClickListener() {
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
                                if(click!=null){
                                    click.onClick(result.getOrderId(),i);
                                    popWindow.dissmiss();
                                }
                            }
                        });
                    }
                });
                break;
            case TYPE_completed:
                //已完成
                ViewCompleted viewCompleted= (ViewCompleted) viewHolder;
                viewCompleted.orderid.setText(result.getOrderId());
                String completed_date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                        new java.util.Date(result.getOrderTime()));
                viewCompleted.orderTime.setText(completed_date);
                LinearLayoutManager linearLayoutManager_C=new LinearLayoutManager(context);
                linearLayoutManager_C.setOrientation(OrientationHelper.VERTICAL);
                viewCompleted.childRecyclerview.setLayoutManager(linearLayoutManager_C);
                //创建适配器
                CompletedChildAdapter completedChildAdapter = new CompletedChildAdapter(context);
                viewCompleted.childRecyclerview.setAdapter(completedChildAdapter);
                completedChildAdapter.setList(list.get(i).getDetailList());
                //点击弹出删除按钮
                viewCompleted.imageDelete.setOnClickListener(new View.OnClickListener() {
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
                                if(click!=null){
                                    click.onClick(result.getOrderId(),i);
                                    popWindow.dissmiss();
                                }
                            }
                        });
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewObligation extends RecyclerView.ViewHolder{
        //待付款控件
        @BindView(R.id.expressSn)
        TextView expressSn;
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

        public ViewObligation(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public class ViewWait extends RecyclerView.ViewHolder{
        //待收货
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

        public ViewWait(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ViewRemain extends RecyclerView.ViewHolder{
        //待评价
        @BindView(R.id.orderid)
        TextView orderid;
        @BindView(R.id.image_delete)
        ImageView imageDelete;
        @BindView(R.id.child_recyclerview)
        RecyclerView childRecyclerview;
        @BindView(R.id.orderTime)
        TextView orderTime;

        public ViewRemain(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ViewCompleted extends RecyclerView.ViewHolder{
        //已完成
        @BindView(R.id.orderid)
        TextView orderid;
        @BindView(R.id.orderTime)
        TextView orderTime;
        @BindView(R.id.child_recyclerview)
        RecyclerView childRecyclerview;
        @BindView(R.id.image_delete)
        ImageView imageDelete;

        public ViewCompleted(@NonNull View itemView) {
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
        void onClick(String id, int i);
    }
    //确认收货
    ReceivingClick receivingClick;
    public void setOnClickListener(ReceivingClick receivingClick) {
        this.receivingClick = receivingClick;
    }
    public interface ReceivingClick {
        void onClick(String id);
    }
    //去评价
    EvaluateClick evaluateClick;

    public void setOnClickListener(EvaluateClick evaluateClick) {
        this.evaluateClick = evaluateClick;
    }

    public interface EvaluateClick {
        void onClick(String orderId, OrderShopBean.OrderListBean.DetailListBean detailListBean);
    }
}
