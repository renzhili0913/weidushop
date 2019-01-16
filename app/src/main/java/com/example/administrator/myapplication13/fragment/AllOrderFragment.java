package com.example.administrator.myapplication13.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.activity.EvaluateActivity;
import com.example.administrator.myapplication13.activity.PaymentActivity;
import com.example.administrator.myapplication13.adapter.AllOrderAdapter;
import com.example.administrator.myapplication13.adapter.ListAdpater;
import com.example.administrator.myapplication13.bean.DeleteOrderBean;
import com.example.administrator.myapplication13.bean.OrderShopBean;
import com.example.administrator.myapplication13.bean.ReceivingGoodsBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AllOrderFragment extends BaseFragment implements IView {
    @BindView(R.id.all_recyclerview)
    XRecyclerView allRecyclerview;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private static final int STATUS = 0;
    private static final int COUNT = 5;
    private int mpage;
   // private AllOrderAdapter allOrderAdapter;
    private int position;
    private ListAdpater allOrderAdapter;

    @Override
    protected void initData() {
       iPresenter.getRequeryData(String.format(Apis.URL_FIND_ORDER_LIST_BYSTATUS_GET, STATUS, mpage, COUNT), OrderShopBean.class);
    }

    @Override
    protected void initView(View view) {
        mpage = 1;
        iPresenter = new IPresenterImpl(this);
        unbinder = ButterKnife.bind(this, view);
        //创建布局
        getRecyclerView();
    }


    private void getRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allRecyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        //allOrderAdapter = new AllOrderAdapter(getActivity());
        allOrderAdapter = new ListAdpater(getActivity());
        allRecyclerview.setAdapter(this.allOrderAdapter);
        //取消订单
        allOrderAdapter.setOnClickListener(new ListAdpater.Click() {
            @Override
            public void onClick(String id, int i) {
                position=i;
                iPresenter.deleteRequeryData(String.format(Apis.URL_DELETE_ORDER_DELETE,id),DeleteOrderBean.class);
            }
        });
        //去支付
        allOrderAdapter.setPaymentClickListener(new ListAdpater.PaymentClick() {
            @Override
            public void onClick(String orderId, int payType, double price) {
                Intent intent = new Intent(getActivity(),PaymentActivity.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("payType",payType);
                intent.putExtra("price",price);
                //startActivity(intent);
                startActivityForResult(intent,100);
            }
        });
        //确认收货
        allOrderAdapter.setOnClickListener(new ListAdpater.ReceivingClick() {
            @Override
            public void onClick(String id) {
                Map<String,String> params = new HashMap<>();
                params.put("orderId",id);
                iPresenter.putRequeryData(Apis.URL_CONFIRM_RECEIPT_PUT,params,ReceivingGoodsBean.class);
            }
        });
        //去评价
        allOrderAdapter.setOnClickListener(new ListAdpater.EvaluateClick() {
            @Override
            public void onClick(String orderId, OrderShopBean.OrderListBean.DetailListBean detailListBean) {
                Intent intent = new Intent(getActivity(),EvaluateActivity.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("detailListBean",detailListBean);
                startActivity(intent);
            }
        });
        allRecyclerview.setPullRefreshEnabled(true);
        allRecyclerview.setLoadingMoreEnabled(true);
        allRecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mpage=1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_all_order_view;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof OrderShopBean) {
            OrderShopBean orderShopBean = (OrderShopBean) o;
            if (orderShopBean == null || !orderShopBean.isSuccess()) {
                Toast.makeText(getActivity(), orderShopBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                if (mpage==1) {
                    allOrderAdapter.setList(orderShopBean.getOrderList());
                }else{
                    allOrderAdapter.addList(orderShopBean.getOrderList());
                }
                allRecyclerview.loadMoreComplete();
                allRecyclerview.refreshComplete();
            }
        }else if(o instanceof ReceivingGoodsBean){
            ReceivingGoodsBean receivingGoodsBean= (ReceivingGoodsBean) o;
            if (receivingGoodsBean!=null&&receivingGoodsBean.isSuccess()){
                initData();
            }
            Toast.makeText(getActivity(), receivingGoodsBean.getMessage(), Toast.LENGTH_SHORT).show();

        }else if (o instanceof DeleteOrderBean) {
            DeleteOrderBean deleteOrderBean = (DeleteOrderBean) o;
            Toast.makeText(getActivity(), deleteOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
            allOrderAdapter.deleteOrder(position );
        }else if (o instanceof String){
            String s = (String) o;
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null) {
            unbinder.unbind();
        }
        iPresenter.onDetach();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==200){
            initData();
        }
    }
}
