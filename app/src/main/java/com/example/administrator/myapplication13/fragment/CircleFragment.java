package com.example.administrator.myapplication13.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.adapter.CircleAdapter;
import com.example.administrator.myapplication13.bean.AddFabulousBean;
import com.example.administrator.myapplication13.bean.CancleFabulousBean;
import com.example.administrator.myapplication13.bean.CircleBean;
import com.example.administrator.myapplication13.bean.LoginBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CircleFragment extends BaseFragment implements IView {

    @BindView(R.id.xrecyclervier)
    XRecyclerView xrecyclervier;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private CircleAdapter circleAdapter;
    private  int mpage;
    private int userId;
    private String sessionId;
    private final int COUNT=5;
    @Override
    protected void initData() {
        iPresenter.getRequeryData(String.format(Apis.GET_URL_USER_FIND_USER_CIRCLE,userId,sessionId,mpage,COUNT),CircleBean.class);
    }

    @Override
    protected void initView(View view) {
        iPresenter=new IPresenterImpl(this);
        unbinder = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
             getLayoutRecycle();
    }

    private void getLayoutRecycle() {
        mpage=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclervier.setLayoutManager(linearLayoutManager);
        //创建适配器
        circleAdapter = new CircleAdapter(getActivity());
        xrecyclervier.setAdapter(circleAdapter);
        xrecyclervier.setLoadingMoreEnabled(true);
        xrecyclervier.setPullRefreshEnabled(true);
        xrecyclervier.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        //点赞与取消点赞
        circleAdapter.setOnClickListener(new CircleAdapter.Click() {
            @Override
            public void onClick(int i,int position,int circleId) {
                    if (i==1){
                        iPresenter.deleteRequeryData(String.format(Apis.DELETE_URL_CANCLE_CIRCLE_GREAT,circleId),CancleFabulousBean.class);
                        circleAdapter.cancleWhetherGreat(position);
                    }else if (i==2){
                        Map<String,String> params = new HashMap<>();
                        params.put("circleId",String.valueOf(circleId));
                        iPresenter.getRequeryData(Apis.POST_URL_ADD_CIRCLE_GREAT,params,AddFabulousBean.class);
                        circleAdapter.addWhetherGreat(position);
                    }

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_circle_item;
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessage(Object o){
        if (o instanceof LoginBean.ResultBean){
            LoginBean.ResultBean resultBean= (LoginBean.ResultBean) o;
            sessionId = resultBean.getSessionId();
            userId = resultBean.getUserId();
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null) {
            unbinder.unbind();
        }
        iPresenter.onDetach();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeStickyEvent(LoginBean.ResultBean.class);
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof CircleBean){
            CircleBean circleBean = (CircleBean) o;
            if (circleBean==null||!circleBean.isSuccess()){
                getToast(circleBean.getMessage());
            }else{
               if (mpage==1){
                   circleAdapter.setList(circleBean.getResult());
               }else{
                   circleAdapter.addList(circleBean.getResult());
               }
               mpage++;
               xrecyclervier.loadMoreComplete();
               xrecyclervier.refreshComplete();
            }

        }else if(o instanceof AddFabulousBean){
            AddFabulousBean addFabulousBean = (AddFabulousBean) o;
            getToast(addFabulousBean.getMessage());
        }else if (o instanceof CancleFabulousBean){
            CancleFabulousBean cancleFabulousBean = (CancleFabulousBean) o;
            getToast(cancleFabulousBean.getMessage());
        }else if (o instanceof String){
            String s = (String) o;
            getToast(s);
        }
    }
    /**
     *吐司的方法
     *@author Administrator
     *@time 2018/12/29 0029 15:03
     */
    public void getToast(String str){
        Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
    }
}
