package com.example.administrator.myapplication13.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.PaymentBean;
import com.example.administrator.myapplication13.fragment.HomeFragment;
import com.example.administrator.myapplication13.fragment.OrderFragment;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivty implements IView {
    @BindView(R.id.balance)
    RadioButton balance;
    @BindView(R.id.wechat)
    RadioButton wechat;
    @BindView(R.id.alipay)
    RadioButton alipay;
    @BindView(R.id.payment)
    TextView payment;
    @BindView(R.id.back_page)
    TextView backPage;
    @BindView(R.id.see_order)
    TextView seeOrder;
    @BindView(R.id.payment_success)
    LinearLayout paymentSuccess;
    @BindView(R.id.continue_payment)
    TextView continuePayment;
    @BindView(R.id.payment_failed)
    LinearLayout paymentFailed;
    private IPresenterImpl iPresenter;
    private int payType;
    private String orderId;

    @Override
    protected void initData() {
        iPresenter = new IPresenterImpl(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        payType = intent.getIntExtra("payType", 1);
        double price = intent.getDoubleExtra("price", 0);
        //赋值
        payment.setText("余额支付" + price + "元");
        switch (payType) {
            case 1:
                balance.setBackgroundResource(R.mipmap.common_icon_finish_n);
                break;
            case 2:
                wechat.setBackgroundResource(R.mipmap.common_icon_finish_n);
                break;
            case 3:
                alipay.setBackgroundResource(R.mipmap.common_icon_finish_n);
                break;
            default:
                break;
        }


    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_payment_view;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof PaymentBean) {
            PaymentBean paymentBean = (PaymentBean) o;
            if (paymentBean == null || !paymentBean.isSuccess()) {
                paymentFailed.setVisibility(View.VISIBLE);
            } else {
                paymentSuccess.setVisibility(View.VISIBLE);
            }
            Toast.makeText(PaymentActivity.this,paymentBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }



    @OnClick({R.id.back_page, R.id.see_order, R.id.continue_payment,R.id.payment,R.id.balance,R.id.wechat,R.id.alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.payment:
                Map<String, String> params = new HashMap<>();
                params.put("orderId", orderId);
                params.put("payType", String.valueOf(payType));
                iPresenter.getRequeryData(Apis.URL_PAY_POST, params, PaymentBean.class);
                break;
            case R.id.back_page:
                paymentSuccess.setVisibility(View.GONE);
                finish();
                break;
            case R.id.see_order:
                paymentSuccess.setVisibility(View.GONE);
                finish();
                break;
            case R.id.continue_payment:
                paymentFailed.setVisibility(View.GONE);
                break;
                default:
                    break;
        }
    }
}
