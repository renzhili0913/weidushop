package com.example.administrator.myapplication13.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.CheckBox;

import android.widget.TextView;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.activity.ShowActivity;
import com.example.administrator.myapplication13.adapter.CartAdapter;
import com.example.administrator.myapplication13.bean.CartBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CartFragment extends BaseFragment implements IView {

    @BindView(R.id.all_election)
    CheckBox allElection;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.total_price)
    TextView total_Price;
    @BindView(R.id.settlement)
    TextView settlement;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private CartAdapter cartAdapter;
    private List<CartBean.ResultBean> data;

    @Override
    protected void initData() {
        iPresenter.getRequeryData(Apis.URL_FIND_SHOPPING_CART_GET,CartBean.class);
    }

    @Override
    protected void initView(View view) {
        iPresenter=new IPresenterImpl(this);
        unbinder = ButterKnife.bind(this, view);
        //创建布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        cartAdapter = new CartAdapter(getActivity());
        recyclerView.setAdapter(cartAdapter);
        cartAdapter.setListener(new CartAdapter.CallBackListener() {
            @Override
            public void callBack(List<CartBean.ResultBean> list) {
                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                for (int i =0;i<list.size();i++){
                        //取选中的状态
                        if (list.get(i).isCheck()){
                            totalPrice=totalPrice+(list.get(i).getCount()*list.get(i).getPrice());
                            num++;
                        }
                }
              if (num<list.size()){
                   allElection.setChecked(false);
              }else{
                  allElection.setChecked(true);
              }
                total_Price.setText("¥"+totalPrice);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cart_item;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null) {
            unbinder.unbind();
        }
    }

    @OnClick({R.id.settlement,R.id.all_election})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.all_election:
                checkSeller(allElection.isChecked());
                cartAdapter.notifyDataSetChanged();
                break;
            case R.id.settlement:

                break;
        }
    }
    private void checkSeller(boolean bool) {

        double totalPrice = 0;
            for (int i = 0; i < data.size(); i++) {
                //遍历商品，改变状态
                data.get(i).setCheck(bool);
                totalPrice = totalPrice + (data.get(i).getPrice() * data.get(i).getCount());
            }
        if (bool) {
            total_Price.setText("¥" + totalPrice);
        } else {
            total_Price.setText("¥0.00");
        }

    }
    @Override
    public void showRequeryData(Object o) {
        if (o instanceof CartBean){
            CartBean cartBean = (CartBean) o;
            if (cartBean!=null&&cartBean.isSuccess()){
                data = cartBean.getResult();
                cartAdapter.setList(data);
            }
            ((ShowActivity)getActivity()).getToast(cartBean.getMessage());
        }else if (o instanceof String){
            String s= (String) o;
            ((ShowActivity)getActivity()).getToast(s);
        }
    }
}
