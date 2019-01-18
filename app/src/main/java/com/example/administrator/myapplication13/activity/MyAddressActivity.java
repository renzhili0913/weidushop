package com.example.administrator.myapplication13.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.adapter.AddressAdapter;
import com.example.administrator.myapplication13.bean.AddressBean;
import com.example.administrator.myapplication13.bean.DefaultReceiveAddressBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddressActivity extends BaseActivty implements IView {
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.address_recyclerview)
    RecyclerView addressRecyclerview;
    @BindView(R.id.add_address)
    Button addAddress;
    private IPresenterImpl iPresenter;
    private AddressAdapter addressAdapter;
    private static final int RESULT_COUNT = 200;
    private static final int REQUERY_COUNT = 100;
    private int position;
    @Override
    protected void initData() {
        iPresenter.getRequeryData(Apis.GET_URL_USER_RECYCLE_ADDRESS_LIST,AddressBean.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        iPresenter = new IPresenterImpl(this);
        ButterKnife.bind(this);
        getRecyclerView();
    }

    private void getRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addressRecyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        addressAdapter = new AddressAdapter(this);
        addressRecyclerview.setAdapter(addressAdapter);
        addressAdapter.setCheckedListener(new AddressAdapter.Checked() {
            @Override
            public void ononChecked( int id,int i) {
                    position = i;
                    Map<String,String> params=new HashMap<>();
                    params.put("id",String.valueOf(id));
                    iPresenter.getRequeryData(Apis.POST_URL_USER_SET_DEFAULT_RECYCLE_ADDRESS_LIST,params,DefaultReceiveAddressBean.class);
                }


        });
        //修改地址
        addressAdapter.setOnUpdataListener(new AddressAdapter.Updata() {
            @Override
            public void onUpdata(AddressBean.ResultBean resultBean) {
                Intent intent = new Intent(MyAddressActivity.this,MyNewAddressActivity.class);
                intent.putExtra("resultBean",resultBean);
                startActivityForResult(intent,REQUERY_COUNT);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_address_view;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof AddressBean){
            AddressBean addressBean = (AddressBean) o;
            if (!addressBean.isSuccess()||addressBean==null){
                Toast.makeText(MyAddressActivity.this,addressBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                addressAdapter.setList(addressBean.getResult());
            }
        }else if (o instanceof DefaultReceiveAddressBean){
            DefaultReceiveAddressBean defaultReceiveAddressBean = (DefaultReceiveAddressBean) o;
            if (defaultReceiveAddressBean!=null&&defaultReceiveAddressBean.isSuccess()){
                addressAdapter.setAllunCheck(position);
                Log.i("TAG","===========");
            }
            Toast.makeText(MyAddressActivity.this,defaultReceiveAddressBean.getMessage(),Toast.LENGTH_SHORT).show();
        }else if (o instanceof String){
            String s = (String) o;
            Toast.makeText(MyAddressActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.complete, R.id.add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.complete:
                finish();
                break;
            case R.id.add_address:
                Intent intent = new Intent(MyAddressActivity.this,MyNewAddressActivity.class);
                startActivityForResult(intent,REQUERY_COUNT);
                break;
        }
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUERY_COUNT&&resultCode==RESULT_COUNT){
            initData();
        }
    }
}
