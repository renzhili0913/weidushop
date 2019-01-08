package com.example.administrator.myapplication13.fragment;


import android.view.View;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;

public class AllOrderFragment extends BaseFragment implements IView {
    private IPresenterImpl iPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        iPresenter=new IPresenterImpl(this);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_all_order_view;
    }

    @Override
    public void showRequeryData(Object o) {

    }
}
