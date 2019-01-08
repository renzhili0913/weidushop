package com.example.administrator.myapplication13.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.AddressBean;
import com.example.administrator.myapplication13.bean.InsertAddressBean;
import com.example.administrator.myapplication13.bean.UpdateaAddressBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyNewAddressActivity extends BaseActivty implements IView {
    private static final int RESULT_COUNT = 200;
    @BindView(R.id.realName)
    EditText realName;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.detailed_address)
    EditText detailedAddress;
    @BindView(R.id.zipCode)
    EditText zipCode;
    @BindView(R.id.preservation_address)
    Button preservationAddress;
    @BindView(R.id.but_linkage)
    ImageView but_linkage;
    private IPresenterImpl iPresenter;
    private AddressBean.ResultBean resultBean;

    @Override
    protected void initData() {
        Intent intent =getIntent();
        resultBean = (AddressBean.ResultBean) intent.getSerializableExtra("resultBean");
        if (resultBean!=null) {
            realName.setText(resultBean.getRealName());
            phone.setText(resultBean.getPhone());
            String[] split = resultBean.getAddress().split("\\ ");
            address.setText(split[0]+" "+split[1]+" "+split[2]);
            for (int i =3;i<split.length;i++){
                detailedAddress.setText(split[i]);
            }
            zipCode.setText(resultBean.getZipCode());
        }


    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        iPresenter = new IPresenterImpl(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_new_address_view;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof UpdateaAddressBean){
            UpdateaAddressBean updateaAddressBean = (UpdateaAddressBean) o;
            if (updateaAddressBean.isSuccess()){
                Intent intent = new Intent();
                setResult(RESULT_COUNT,intent);
                finish();
            }
            Toast.makeText(MyNewAddressActivity.this, updateaAddressBean.getMessage(), Toast.LENGTH_SHORT).show();
        }else if (o instanceof InsertAddressBean){
            InsertAddressBean insertAddressBean = (InsertAddressBean) o;
            if (insertAddressBean.isSuccess()){
                Intent intent = new Intent();
                setResult(RESULT_COUNT,intent);
                finish();
            }
            Toast.makeText(MyNewAddressActivity.this, insertAddressBean.getMessage(), Toast.LENGTH_SHORT).show();
        }else if (o instanceof String){
            String s= (String) o;
            Toast.makeText(MyNewAddressActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({R.id.preservation_address,R.id.but_linkage})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.preservation_address:
                if (resultBean==null){
                    Map<String,String> params = new HashMap<>();
                    params.put("realName",realName.getText().toString().trim());
                    params.put("phone",phone.getText().toString().trim());
                    params.put("address",address.getText().toString().trim()+" "+detailedAddress.getText().toString().trim());
                    params.put("zipCode",zipCode.getText().toString().trim());
                    iPresenter.getRequeryData(Apis.POST_URL_USER_ADD_RECYCLE_ADDRESS_LIST, params, InsertAddressBean.class);
                }else {
                    Map<String,String> params = new HashMap<>();
                    params.put("id",String.valueOf(resultBean.getId()));
                    params.put("realName",realName.getText().toString().trim());
                    params.put("phone",phone.getText().toString().trim());
                    params.put("address",address.getText().toString().trim()+" "+detailedAddress.getText().toString().trim());
                    params.put("zipCode",zipCode.getText().toString().trim());
                    iPresenter.putRequeryData(Apis.PUT_URL_USER_CHANGE_RECYCLE_ADDRESS, params, UpdateaAddressBean.class);
                }
                break;
            case R.id.but_linkage:
                //判断输入法的隐藏状态
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    selectAddress();//调用CityPicker选取区域
                }
                break;
                default:
                    break;
        }

    }
    /**
     *获取三级联动区域
     *@author Administrator
     *@time 2019/1/8 0008 20:13
     */
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(MyNewAddressActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                address.setText(province.trim() + " " + city.trim() + " " + district.trim());
                zipCode.setText(code.trim());
            }
        });
    }
}
