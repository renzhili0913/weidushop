package com.example.administrator.myapplication13.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.adapter.AllOrderAdapter;
import com.example.administrator.myapplication13.adapter.ReceivingGoodsAdapter;
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

public class ReceivingGoodsFragment extends BaseFragment implements IView {

    @BindView(R.id.all_recyclerview)
    XRecyclerView allRecyclerview;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private static final int STATUS = 2;
    private static final int COUNT = 5;
    private int mpage;
    private ReceivingGoodsAdapter receivingGoodsAdapter;
    private int position;
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
        receivingGoodsAdapter = new ReceivingGoodsAdapter(getActivity());
        allRecyclerview.setAdapter(receivingGoodsAdapter);
        //确认收货
        receivingGoodsAdapter.setOnClickListener(new ReceivingGoodsAdapter.Click() {
            @Override
            public void onClick(String id) {
                Map<String,String> params = new HashMap<>();
                params.put("orderId",id);
                iPresenter.putRequeryData(Apis.URL_CONFIRM_RECEIPT_PUT,params,ReceivingGoodsBean.class);
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
        return R.layout.fragment_receiving_goods_view;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof OrderShopBean) {
            OrderShopBean orderShopBean = (OrderShopBean) o;
            if (orderShopBean == null || !orderShopBean.isSuccess()) {
                Toast.makeText(getActivity(), orderShopBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                if (mpage==1) {
                    receivingGoodsAdapter.setList(orderShopBean.getOrderList());
                }else{
                    receivingGoodsAdapter.addList(orderShopBean.getOrderList());
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


}
