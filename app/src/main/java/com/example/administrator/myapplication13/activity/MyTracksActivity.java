package com.example.administrator.myapplication13.activity;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.adapter.MyTracksAdapter;
import com.example.administrator.myapplication13.bean.TracksBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTracksActivity extends BaseActivty implements IView {

    @BindView(R.id.recyclerview)
    XRecyclerView recyclerview;
    private IPresenterImpl iPresenter;
    private MyTracksAdapter myTracksAdapter;
    private int mpage;
    private final int COUNT=5;
    @Override
    protected void initData() {
        iPresenter.getRequeryData(String.format(Apis.GET_URL_BROWSE_LIST,mpage,COUNT),TracksBean.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
        getRecyclerView();
    }

    private void getRecyclerView() {
        mpage=1;
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(staggeredGridLayoutManager);
        //创建适配器
        myTracksAdapter = new MyTracksAdapter(this);
        recyclerview.setAdapter(myTracksAdapter);
        recyclerview.setPullRefreshEnabled(true);
        recyclerview.setLoadingMoreEnabled(true);
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        return R.layout.activity_my_tracks_view;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof TracksBean){
            TracksBean tracksBean = (TracksBean) o;
            if (tracksBean==null||!tracksBean.isSuccess()){
                Toast.makeText(MyTracksActivity.this,tracksBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                //传值到适配器
                if (mpage==1) {
                    myTracksAdapter.setList(tracksBean.getResult());
                }else{
                    myTracksAdapter.addList(tracksBean.getResult());
                }
                mpage++;
                recyclerview.loadMoreComplete();
                recyclerview.refreshComplete();
            }
        }else if (o instanceof String){
            String s = (String) o;
            Toast.makeText(MyTracksActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }


}
