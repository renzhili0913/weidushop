package com.example.administrator.myapplication13.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrderFragment extends BaseFragment {

    @BindView(R.id.all_order)
    ImageView allOrder;
    @BindView(R.id.pending_payment)
    ImageView pendingPayment;
    @BindView(R.id.receiving_goods)
    ImageView receivingGoods;
    @BindView(R.id.evaluate)
    ImageView evaluate;
    @BindView(R.id.completed)
    ImageView completed;
    @BindView(R.id.order_viewpager)
    NoScrollViewPager orderViewpager;
    Unbinder unbinder;
    @BindView(R.id.all_order_name)
    TextView allOrderName;
    @BindView(R.id.pending_payment_name)
    TextView pendingPaymentName;
    @BindView(R.id.receiving_goods_name)
    TextView receivingGoodsName;
    @BindView(R.id.evaluate_name)
    TextView evaluateName;
    @BindView(R.id.completed_name)
    TextView completedName;
    Unbinder unbinder1;
    private List<Fragment> list;


    @Override
    protected void initData() {
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        list = new ArrayList<>();
        list.add(new AllOrderFragment());
        list.add(new PaymentFragment());
        list.add(new ReceivingGoodsFragment());
        list.add(new EvaluateFragment());
        list.add(new CompletedFragment());
        //创建适配器
        orderViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_item;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        unbinder1.unbind();
    }

    @OnClick({R.id.all_order, R.id.pending_payment, R.id.receiving_goods, R.id.evaluate, R.id.completed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_order:
                orderViewpager.setCurrentItem(0);
                allOrderName.setTextColor(Color.parseColor("#ff0000"));
                pendingPaymentName.setTextColor(Color.parseColor("#666666"));
                receivingGoodsName.setTextColor(Color.parseColor("#666666"));
                evaluateName.setTextColor(Color.parseColor("#666666"));
                completedName.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.pending_payment:
                orderViewpager.setCurrentItem(1);
                allOrderName.setTextColor(Color.parseColor("#666666"));
                pendingPaymentName.setTextColor(Color.parseColor("#ff0000"));
                receivingGoodsName.setTextColor(Color.parseColor("#666666"));
                evaluateName.setTextColor(Color.parseColor("#666666"));
                completedName.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.receiving_goods:
                orderViewpager.setCurrentItem(2);
                allOrderName.setTextColor(Color.parseColor("#666666"));
                pendingPaymentName.setTextColor(Color.parseColor("#666666"));
                receivingGoodsName.setTextColor(Color.parseColor("#ff0000"));
                evaluateName.setTextColor(Color.parseColor("#666666"));
                completedName.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.evaluate:
                orderViewpager.setCurrentItem(3);
                allOrderName.setTextColor(Color.parseColor("#666666"));
                pendingPaymentName.setTextColor(Color.parseColor("#666666"));
                receivingGoodsName.setTextColor(Color.parseColor("#666666"));
                evaluateName.setTextColor(Color.parseColor("#ff0000"));
                completedName.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.completed:
                orderViewpager.setCurrentItem(4);
                allOrderName.setTextColor(Color.parseColor("#666666"));
                pendingPaymentName.setTextColor(Color.parseColor("#666666"));
                receivingGoodsName.setTextColor(Color.parseColor("#666666"));
                evaluateName.setTextColor(Color.parseColor("#666666"));
                completedName.setTextColor(Color.parseColor("#ff0000"));
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
