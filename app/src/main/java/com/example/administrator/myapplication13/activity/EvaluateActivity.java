package com.example.administrator.myapplication13.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.CommentBean;
import com.example.administrator.myapplication13.bean.OrderShopBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluateActivity extends BaseActivty implements IView {

    @BindView(R.id.shopcar_image)
    SimpleDraweeView shopcarImage;
    @BindView(R.id.shopcar_title)
    TextView shopcarTitle;
    @BindView(R.id.shopcar_price)
    TextView shopcarPrice;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.synchronization_circle)
    RadioButton synchronizationCircle;
    @BindView(R.id.publish)
    TextView publish;
    private String orderId;
    private OrderShopBean.OrderListBean.DetailListBean detailListBean;
    private IPresenterImpl iPresenter;
    @Override
    protected void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        detailListBean = (OrderShopBean.OrderListBean.DetailListBean) intent.getSerializableExtra("detailListBean");
        String[] split = detailListBean.getCommodityPic().split("\\,");
        Uri uri = Uri.parse(split[0]);
        shopcarImage.setImageURI(uri);
        shopcarTitle.setText(detailListBean.getCommodityName());
        shopcarPrice.setText(detailListBean.getCommodityPrice()+"");

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_evaluate_view;
    }


    @OnClick(R.id.publish)
    public void onViewClicked() {
        String trim = content.getText().toString().trim();
        if (trim.equals("")){
            Toast.makeText(EvaluateActivity.this,"输入评论",Toast.LENGTH_SHORT).show();
        }else{
            Map<String,String> params = new HashMap<>();
            params.put("orderId",orderId);
            params.put("content",trim);
            params.put("commodityId",String.valueOf(detailListBean.getCommodityId()));
            iPresenter.getRequeryData(Apis.URL_ADD_COMMODITY_COMMENT_LIST_POST,params,CommentBean.class);
        }

    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof CommentBean){
            CommentBean commentBean = (CommentBean) o;
            Toast.makeText(EvaluateActivity.this,commentBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
