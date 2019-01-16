package com.example.administrator.myapplication13.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutResId()!=0) {
            return inflater.inflate(getLayoutResId(), container, false);
        }
        View view = getLayoutView();
        if (view!=null){
            return view;
        }
        return null;
    }

    protected int getLayoutResId() {
        return 0;
    }

    protected View getLayoutView() {
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化
        initView(view);
        //添加数据
        initData();

        /*if (view!=null) {
            setupUI(view);
        }*/
    }

    protected abstract void initData();

    protected abstract void initView(View view);



}
