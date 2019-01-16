package com.example.administrator.myapplication13.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.adapter.MyCircleAdapter;
import com.example.administrator.myapplication13.bean.DeleteCircleBean;
import com.example.administrator.myapplication13.bean.DeleteOrderBean;
import com.example.administrator.myapplication13.bean.MyCircleBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCircleActivity extends BaseActivty implements IView {
    @BindView(R.id.image_delete)
    ImageView imageDelete;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerview;
    private IPresenterImpl iPresenter;
    private int mpage;
    private final int COUNT=5;
    private MyCircleAdapter myCircleAdapter;
    private boolean falg=true;
    @Override
    protected void initData() {
        iPresenter.getRequeryData(String.format(Apis.URL_FIND_MYCIRCLE_BYID_GET,mpage,COUNT),MyCircleBean.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
        //获取布局
        getRecyclerView();
    }
    /**
     *获取recycleview布局
     *@author Administrator
     *@time 2019/1/15 0015 13:51
     */
    private void getRecyclerView() {
        mpage=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        myCircleAdapter = new MyCircleAdapter(this);
        xrecyclerview.setAdapter(myCircleAdapter);
        xrecyclerview.setLoadingMoreEnabled(true);
        xrecyclerview.setPullRefreshEnabled(true);
        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        return R.layout.activity_my_circle_view;
    }


    @OnClick(R.id.image_delete)
    public void onViewClicked() {
        if (falg){
            myCircleAdapter.setCheckbox(true);
        }else {
            myCircleAdapter.setCheckbox(false);
            List<MyCircleBean.ResultBean> list = myCircleAdapter.getList();
            String string="";
            for (int i =0;i<list.size();i++){
                if (list.get(i).isSelect()){
                    string+=list.get(i).getId()+",";
                }
            }

            //请求删除圈子数据
            if (!string.equals("")){
                String substring = string.substring(0, string.length() - 1);
                Log.i("TAG",substring);
                iPresenter.deleteRequeryData(String.format(Apis.URL_DELETE_CIRCLE_DELETE,substring),DeleteCircleBean.class);
            }

        }
        falg=!falg;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof MyCircleBean){
            MyCircleBean myCircleBean = (MyCircleBean) o;
            if (myCircleBean!=null&&myCircleBean.isSuccess()){
                //TODO 传值适配器
                if (mpage==1){
                    myCircleAdapter.setList(myCircleBean.getResult());
                }else{
                    myCircleAdapter.addList(myCircleBean.getResult());
                }
                mpage++;
                xrecyclerview.loadMoreComplete();
                xrecyclerview.refreshComplete();
            }

            Toast.makeText(MyCircleActivity.this,myCircleBean.getMessage(),Toast.LENGTH_SHORT).show();
        }else if(o instanceof DeleteCircleBean){
            DeleteCircleBean deleteCircleBean = (DeleteCircleBean) o;
           if (deleteCircleBean.isSuccess()&&deleteCircleBean!=null){
               mpage=1;
               initData();
            }
            Log.i("TAG","+++++++"+deleteCircleBean.getMessage());
            Toast.makeText(MyCircleActivity.this,deleteCircleBean.getMessage(),Toast.LENGTH_SHORT).show();
        }else if (o instanceof String){
            String s = (String) o;
            Toast.makeText(MyCircleActivity.this,s,Toast.LENGTH_SHORT).show();
            Log.i("TAG","--------"+s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
