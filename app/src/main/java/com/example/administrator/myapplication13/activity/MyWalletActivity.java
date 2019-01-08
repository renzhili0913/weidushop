package com.example.administrator.myapplication13.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.adapter.MyWalletAdapter;
import com.example.administrator.myapplication13.bean.WalletBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWalletActivity extends BaseActivty implements IView {
    @BindView(R.id.backgroud_iamge)
    ImageView backgroudIamge;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.my_wallet_xrecyclerview)
    XRecyclerView myWalletXrecyclerview;
    private IPresenterImpl iPresenter;
    private int mpage;
    private final int COUNT=5;
    private MyWalletAdapter myWalletAdapter;

    @Override
    protected void initData() {
        iPresenter.getRequeryData(String.format(Apis.GET_URL_USER_FIND_USER_WALLET,mpage,COUNT),WalletBean.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
        //创建xrecyclerview布局
        getXrecyclerView();
    }

    private void getXrecyclerView() {
        mpage=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myWalletXrecyclerview.setLayoutManager(linearLayoutManager);
        myWalletXrecyclerview.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        //创建适配器
        myWalletAdapter = new MyWalletAdapter(this);
        myWalletXrecyclerview.setAdapter(myWalletAdapter);
        myWalletXrecyclerview.setLoadingMoreEnabled(true);
        myWalletXrecyclerview.setPullRefreshEnabled(true);
        myWalletXrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        return R.layout.activity_my_wallet_view;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof WalletBean){
            WalletBean walletBean = (WalletBean) o;
            if (walletBean==null||!walletBean.isSuccess()){
                Toast.makeText(MyWalletActivity.this,walletBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                price.setText(walletBean.getResult().getBalance()+"");
                if (mpage==1){
                    myWalletAdapter.setList(walletBean.getResult().getDetailList());
                }else{
                    myWalletAdapter.addList(walletBean.getResult().getDetailList());
                }
                mpage++;
                myWalletXrecyclerview.refreshComplete();
                myWalletXrecyclerview.loadMoreComplete();
            }
        }else if (o instanceof String){
            String s= (String) o;
            Toast.makeText(MyWalletActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }
}
