package com.example.administrator.myapplication13.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.adapter.EstablistAddressAdapter;
import com.example.administrator.myapplication13.adapter.EstablistOrderAdapter;
import com.example.administrator.myapplication13.bean.AddressBean;
import com.example.administrator.myapplication13.bean.CartBean;
import com.example.administrator.myapplication13.bean.EstablishBean;
import com.example.administrator.myapplication13.bean.EstablishOrderBean;
import com.example.administrator.myapplication13.bean.ShopResultBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EstablishOrderActivity extends BaseActivty implements IView {

    @BindView(R.id.add_address)
    TextView addAddress;
    @BindView(R.id.realName)
    TextView realName;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.but_obtain)
    ImageView butObtain;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.total_price)
    TextView total_Price;
    @BindView(R.id.settlement)
    TextView settlement;
    @BindView(R.id.add_layout_view)
    ConstraintLayout add_layout_view;
    @BindView(R.id.add_layout)
    LinearLayout add_layout;
    @BindView(R.id.address_recyclerview)
    RecyclerView address_recyclerview;
    private EstablistOrderAdapter establistOrderAdapter;
    private int num=0;
    private double totalPrice=0;
    private IPresenterImpl iPresenter;
    private EstablistAddressAdapter establistAddressAdapter;
    private int id;
    private List<EstablishBean> shopResult;
    private boolean falg=false;
    @Override
    protected void initData() {
        shopResult=new ArrayList<>();
        //接收：
        Intent intent = getIntent();
        ArrayList<CartBean.ResultBean> checkOrder =intent.getParcelableArrayListExtra("checkOrder");
      /*  Bundle bundle = intent.getExtras();
        ArrayList<CartBean.ResultBean> checkOrder = (ArrayList<CartBean.ResultBean>) bundle.getSerializable("checkOrder");*/
        if (checkOrder!=null) {
            for (CartBean.ResultBean resultBean : checkOrder
                    ) {
                num += resultBean.getCount();
                totalPrice += resultBean.getCount() * resultBean.getPrice();
                shopResult.add(new EstablishBean(resultBean.getCommodityId(),resultBean.getCount()));
            }
        }
        total_Price.setText("共"+num+"件商品，需付款"+totalPrice+"元");
        //传值到适配器
        establistOrderAdapter.setList(checkOrder);
        getAddView();
         //查询
        iPresenter.getRequeryData(Apis.GET_URL_USER_RECYCLE_ADDRESS_LIST,AddressBean.class);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        establistOrderAdapter = new EstablistOrderAdapter(this);
        recyclerview.setAdapter(establistOrderAdapter);
        //加加减减
        establistOrderAdapter.setListener(new EstablistOrderAdapter.CallBackListener() {
            @Override
            public void callBack(List<CartBean.ResultBean> list) {
                 int countNum=0;
                 double totalPrice1=0;
                for (int i =0;i<list.size();i++){
                    totalPrice1+=(list.get(i).getCount()*list.get(i).getPrice());
                    countNum+=list.get(i).getCount();
                }
                num=countNum;
                totalPrice=totalPrice1;
                total_Price.setText("共"+countNum+"件商品，需付款"+totalPrice+"元");
            }

        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_establist_order_view;
    }


    @OnClick({R.id.add_address, R.id.but_obtain, R.id.settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_address:
                Intent intent = new Intent(EstablishOrderActivity.this,MyNewAddressActivity.class);
                startActivityForResult(intent,100);
                break;
            case R.id.but_obtain:
                if (falg){
                    address_recyclerview.setVisibility(View.VISIBLE);
                    butObtain.setBackgroundResource(R.drawable.ic_action_pull_up);
                    //iPresenter.getRequeryData(Apis.GET_URL_USER_RECYCLE_ADDRESS_LIST,AddressBean.class);
                }else {
                    address_recyclerview.setVisibility(View.GONE);
                    butObtain.setBackgroundResource(R.drawable.ic_action_drop_down);
                }
                falg=!falg;
                break;
            case R.id.settlement:
                //gson将选中商品集合转换成string
                Gson gson = new Gson();
                String s = gson.toJson(shopResult);
                Map<String,String> params = new HashMap<>();
                params.put("orderInfo",s);
                params.put("totalPrice",String.valueOf(totalPrice));
                params.put("addressId",String.valueOf(id));
                 iPresenter.getRequeryData(Apis.URL_CREATE_ORDER_POST,params,EstablishOrderBean.class);
                 Intent intent1 = getIntent();
                 setResult(200,intent1);
                 finish();
                break;
                default:
                    break;
        }
    }
    /**
     *加载布局
     *@author Administrator
     *@time 2019/1/9 0009 16:05
     */
    private void getAddView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        address_recyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        establistAddressAdapter = new EstablistAddressAdapter(this);
        address_recyclerview.setAdapter(establistAddressAdapter);
        //选择
        establistAddressAdapter.setOnUpdataListener(new EstablistAddressAdapter.Updata() {
            @Override
            public void onUpdata(AddressBean.ResultBean resultBean) {
                Log.i("TAG",resultBean.getId()+"");
                        realName.setText(resultBean.getRealName());
                        phone.setText(resultBean.getPhone());
                        address.setText(resultBean.getAddress());
                        //隐藏
                address_recyclerview.setVisibility(View.GONE);
                butObtain.setBackgroundResource(R.drawable.ic_action_drop_down);

            }
        });
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof AddressBean){
            AddressBean addressBean = (AddressBean) o;
            if (!addressBean.isSuccess()||addressBean==null){
                Toast.makeText(EstablishOrderActivity.this,addressBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                List<AddressBean.ResultBean> result = addressBean.getResult();
                if (result==null) {
                    add_layout.setVisibility(View.VISIBLE);
                    add_layout_view.setVisibility(View.GONE);
                }else {
                    for (AddressBean.ResultBean resultBean : result) {
                        if (resultBean.getWhetherDefault() == 1) {
                            add_layout.setVisibility(View.GONE);
                            add_layout_view.setVisibility(View.VISIBLE);
                            realName.setText(resultBean.getRealName());
                            phone.setText(resultBean.getPhone());
                            address.setText(resultBean.getAddress());
                            id = resultBean.getId();
                            break;
                        }
                    }
                    //传值到适配器
                    establistAddressAdapter.setList(result);
                }
            }
        }else if (o instanceof EstablishOrderBean){
            EstablishOrderBean establishOrderBean = (EstablishOrderBean) o;
           Toast.makeText(EstablishOrderActivity.this,establishOrderBean.getMessage(),Toast.LENGTH_SHORT).show();
           finish();
        }else if (o instanceof String){
            String s = (String) o;
            Toast.makeText(EstablishOrderActivity.this,s,Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==200){
            add_layout.setVisibility(View.GONE);
            add_layout_view.setVisibility(View.VISIBLE);
            iPresenter.getRequeryData(Apis.GET_URL_USER_RECYCLE_ADDRESS_LIST,AddressBean.class);

        }
    }
}
